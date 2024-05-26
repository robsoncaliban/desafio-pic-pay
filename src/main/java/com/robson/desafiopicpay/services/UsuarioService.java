package com.robson.desafiopicpay.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;

import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;
import com.robson.desafiopicpay.services.utils.UsuarioMapper;


@Service
public class UsuarioService{
    private UsuarioRepository repository;
    private UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper  = mapper;
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public Usuario findById(Long id){
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(id);
    }

    public List<Transacao> findTransacoesRecebidasByUserId(Long id){
        Usuario usuario = findById(id);
        return usuario.getHistoricoDeTransacaosRecebidas();
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(email);
    }

    public Usuario updateById(Long id, UsuarioUpdateRequestDTO usuarioUpdate){
        Usuario usuario = findById(id);
        mapper.updateUsuarioFromDto(usuarioUpdate, usuario);
        return repository.save(usuario);
    }

}
