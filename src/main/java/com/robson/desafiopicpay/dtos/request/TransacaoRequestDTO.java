package com.robson.desafiopicpay.dtos.request;

import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(
    @NotNull(message = "Insira um id de origem") Long idOrigem, 
    @NotNull(message = "Insira um id de destino") Long idDestino, 
    @NotNull(message = "Insira um valor") double valor
) {}
