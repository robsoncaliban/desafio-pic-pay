package com.robson.desafiopicpay.dtos.response;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.Link;

import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.entities.enums.TipoUsuario;

public record UsuarioGetByIdResponseDTO(
    Long id,
    TipoUsuario tipo,
    String nomeCompleto,
    String email,
    BigDecimal saldoNaConta,
    Link link,
    List<Link> historicoDeTransacoes
) {
    public UsuarioGetByIdResponseDTO(Usuario usuario, Link link, List<Link> historicoDeTransacoes) {
        this(usuario.getId(), usuario.getTipo(), usuario.getNomeCompleto(), usuario.getEmail(), usuario.getConta().getSaldo(), link, historicoDeTransacoes);
    }
}
