package com.robson.desafiopicpay.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.robson.desafiopicpay.entities.UserDetailsImpl;
import com.robson.desafiopicpay.entities.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UsuarioService usuarioService;


    public UserDetailsServiceImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioService.findByEmail(email); 
        return new UserDetailsImpl(usuario);
    }
    
}
