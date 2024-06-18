package com.robson.desafiopicpay.dtos.response;


import org.springframework.hateoas.Link;

import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;

public record UsuarioResponseDTO(
    Long id,
    TipoUsuario tipo,
    String nomeCompleto,
    String email,
    Link link
) {
    public UsuarioResponseDTO(Usuario usuario, Link link) {
        this(usuario.getId(), usuario.getTipo(), usuario.getNomeCompleto(), usuario.getEmail(), link);
    }
}
