package com.robson.desafiopicpay.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.UsuarioLogistaRequestDTO;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;
import com.robson.desafiopicpay.repositories.UsuarioLogistaRepository;
import com.robson.desafiopicpay.services.chain.TratadorCadastro;
import com.robson.desafiopicpay.services.chain.TratadorCnpj;
import com.robson.desafiopicpay.services.chain.TratadorEmail;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

@Service
public class UsuarioLogistaService {
    private UsuarioLogistaRepository repository;
    private UsuarioService usuarioService;

    public UsuarioLogistaService(UsuarioLogistaRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<UsuarioLogista> findAll(){
        return repository.findAll();
    }

    public UsuarioLogista findById(Long id){
        Optional<UsuarioLogista> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(id);
    }

    public UsuarioLogista insert(UsuarioLogistaRequestDTO usuario){
        UsuarioLogista usuarioLogista = new UsuarioLogista(usuario);
        TratadorCadastro tratadorEmail = new TratadorEmail(usuarioService);
        TratadorCadastro tratador = new TratadorCnpj(this);
        tratadorEmail.setProximoTratador(tratador);
        tratadorEmail.tratarRequisicao(usuarioLogista);
        usuarioLogista = repository.save(usuarioLogista);
        return usuarioLogista;
    }

    public Usuario findByCnpj(String cnpj) {
        Optional<UsuarioLogista> usuario = repository.findByCnpj(cnpj);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(cnpj);
    }
}
