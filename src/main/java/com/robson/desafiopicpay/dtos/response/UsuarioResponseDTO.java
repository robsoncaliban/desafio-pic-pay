package com.robson.desafiopicpay.dtos.response;

import org.springframework.hateoas.Link;

import com.robson.desafiopicpay.entities.usuarios.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nomeCompleto,
    String email,
    double saldo,
    Link link
) {
    public UsuarioResponseDTO(Usuario usuario, Link link) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSaldo(), link);
    }
}
