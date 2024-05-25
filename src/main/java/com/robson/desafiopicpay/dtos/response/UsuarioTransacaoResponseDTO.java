package com.robson.desafiopicpay.dtos.response;


import com.robson.desafiopicpay.entities.usuarios.Usuario;

public record UsuarioTransacaoResponseDTO(
    Long id,
    String nomeCompleto,
    String email
    ) {

    public UsuarioTransacaoResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail());
    }
    
}
