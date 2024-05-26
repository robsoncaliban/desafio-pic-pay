package com.robson.desafiopicpay.dtos.request;

import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(
    @NotNull(message = "Insira um id de origem") Long idOrigem, 
    @NotNull(message = "Insira a senha do usuario") String senhaOrigem,
    @NotNull(message = "Insira um id de destino") Long idDestino, 
    @NotNull(message = "Insira um valor") double valor
) {}
