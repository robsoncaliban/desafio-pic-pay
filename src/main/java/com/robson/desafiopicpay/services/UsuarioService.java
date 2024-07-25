package com.robson.desafiopicpay.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCpfCnpj;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;
import com.robson.desafiopicpay.services.exceptions.AuthenticationFailureException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;
import com.robson.desafiopicpay.services.utils.BeanUtilsIgnoreNull;


@Service
public class UsuarioService{
    private UsuarioRepository repository;
    private PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Usuario insert(UsuarioRequestDTO usuario){
        Usuario novoUsuario = new Usuario(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(this);
        TratadorCadastro tratador = new TratadorCpfCnpj(this);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(novoUsuario);
        novoUsuario.setSenha(encoder.encode(usuario.senha()));
        novoUsuario = repository.save(novoUsuario);
        return novoUsuario;
    }

    public Page<Usuario> findAll(Pageable page){
        return repository.findAll(page);
    }

    public Usuario findById(Long id){
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(id);
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(email);
    }
    
    public Usuario findByCpfCnpj(String cpfCnpj){
        Optional<Usuario> usuario = repository.findByCpfOuCnpj(cpfCnpj);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(cpfCnpj);
    }

    public Usuario updateById(Long id, UsuarioUpdateRequestDTO usuarioUpdate){
        Usuario usuario = findById(id);
        String senhaCriptografada = (usuarioUpdate.senha() != null) ? encoder.encode(usuarioUpdate.senha()) : null;
        UsuarioUpdateRequestDTO update = new UsuarioUpdateRequestDTO(usuarioUpdate.nomeCompleto(), senhaCriptografada);
        BeanUtilsIgnoreNull.copyProperties(update, usuario);
        return repository.save(usuario);
    }

    public boolean autenticarUsuario(Long id, String senha){
        Usuario usuario = findById(id);
        if(!encoder.matches(senha, usuario.getSenha())){
            throw new AuthenticationFailureException(id);
        }
        return true;
    }

}
