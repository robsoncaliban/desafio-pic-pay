package com.robson.desafiopicpay.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.UsuarioComumRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioComumRepository;
import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCpf;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

@Service
public class UsuarioComumService {
    private UsuarioComumRepository repository;
    private UsuarioService usuarioService;
    

    public UsuarioComumService(UsuarioComumRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<UsuarioComum> findAll(){
        return repository.findAll();
    }

    public UsuarioComum findById(Long id){
        Optional<UsuarioComum> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(id);
    }

    public List<Transacao> findTransacoesEnviadasByUserId(Long id){
        UsuarioComum usuario = findById(id);
        return usuario.getHistoricoDeTransacoesEnviadas();
    }

    public UsuarioComum insert(UsuarioComumRequestDTO usuario){
        UsuarioComum usuarioComum = new UsuarioComum(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(usuarioService);
        TratadorCadastro tratador = new TratadorCpf(this);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(usuarioComum);
        usuarioComum = repository.save(usuarioComum);
        return usuarioComum;
    }

    public Usuario findByCpf(String cpf) {
        Optional<UsuarioComum> usuario = repository.findByCpf(cpf);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(cpf);
    }
}
