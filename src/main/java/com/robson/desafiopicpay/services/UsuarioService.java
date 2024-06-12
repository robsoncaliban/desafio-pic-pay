package com.robson.desafiopicpay.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCpfCnpj;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;

import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;
import com.robson.desafiopicpay.services.utils.BeanUtilsIgnoreNull;


@Service
public class UsuarioService{
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario insert(UsuarioRequestDTO usuario){
        Usuario novoUsuario = new Usuario(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(this);
        TratadorCadastro tratador = new TratadorCpfCnpj(this);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(novoUsuario);
        novoUsuario = repository.save(novoUsuario);
        return novoUsuario;
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public List<Transacao> findTransacoesEnviadasByUserId(Long id){
        Usuario usuario = findById(id);
        return usuario.getConta().getTransacoesEnviadas();
    }

    public List<Transacao> findTransacoesRecebidasByUserId(Long id){
        Usuario usuario = findById(id);
        return usuario.getConta().getTransacoesRecebidas();
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
        BeanUtilsIgnoreNull.copyProperties(usuarioUpdate, usuario);
        return repository.save(usuario);
    }

}
