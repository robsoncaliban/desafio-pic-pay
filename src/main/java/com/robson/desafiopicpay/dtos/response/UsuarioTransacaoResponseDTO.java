package com.robson.desafiopicpay.dtos.response;


import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;

public record UsuarioTransacaoResponseDTO(
    Long id,
    TipoUsuario tipo,
    String nomeCompleto,
    String email
    ) {

    public UsuarioTransacaoResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getTipo(), usuario.getNomeCompleto(), usuario.getEmail());
    }
    
}
