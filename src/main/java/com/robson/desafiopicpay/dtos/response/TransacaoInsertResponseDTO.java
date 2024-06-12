package com.robson.desafiopicpay.dtos.response;

import java.math.BigDecimal;
import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.enums.StatusTransacao;

public record TransacaoInsertResponseDTO(
    Long id, 
    StatusTransacao estado,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant data, 
    BigDecimal valor, 
    UsuarioTransacaoResponseDTO origem, 
    UsuarioTransacaoResponseDTO destino
) {
    public TransacaoInsertResponseDTO(Transacao transacao) {
        this(transacao.getId(), transacao.getStatus(), transacao.getData(), transacao.getValor(),
            new UsuarioTransacaoResponseDTO(transacao.getOrigem().getDono()),
            new UsuarioTransacaoResponseDTO(transacao.getDestino().getDono()));
    }
}
