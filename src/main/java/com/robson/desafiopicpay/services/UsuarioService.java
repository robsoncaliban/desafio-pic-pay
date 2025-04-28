package com.robson.desafiopicpay.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;


@Service
public class UsuarioService{
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Usuario> findAll(Pageable page){
        return usuarioRepository.findAll(page);
    }

    public Usuario findById(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(id);
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(email);
    }
    
    public Usuario findByCpfCnpj(String cpfCnpj){
        Optional<Usuario> usuario = usuarioRepository.findByCpfOuCnpj(cpfCnpj);
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UserNotFoundException(cpfCnpj);
    }

}
