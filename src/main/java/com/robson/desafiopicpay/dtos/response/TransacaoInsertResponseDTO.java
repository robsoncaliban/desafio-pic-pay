package com.robson.desafiopicpay.dtos.response;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.services.enums.EstadoTransacao;

public record TransacaoInsertResponseDTO(
    
    Long id, 
    EstadoTransacao estado,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant data, 
    double valor, 
    UsuarioResponseDTO origem, 
    UsuarioResponseDTO destino
) {
    public TransacaoInsertResponseDTO(Transacao transacao) {
        this(transacao.getId(), transacao.getEstado(), transacao.getData(), transacao.getValor(),
            new UsuarioResponseDTO(transacao.getOrigem(), null),
            new UsuarioResponseDTO(transacao.getDestino(), null));
    }
}
