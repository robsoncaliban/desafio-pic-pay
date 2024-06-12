package com.robson.desafiopicpay.dtos.response;


import org.springframework.hateoas.Link;

import com.robson.desafiopicpay.entities.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nomeCompleto,
    String email,
    Link link
) {
    public UsuarioResponseDTO(Usuario usuario, Link link) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail(), link);
    }
}
