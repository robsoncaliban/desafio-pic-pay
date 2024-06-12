package com.robson.desafiopicpay.dtos.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransacaoRequestDTO(
    @NotNull(message = "Insira um id de origem") 
    Long idOrigem, 
    @NotNull(message = "Insira a senha do usuario") 
    @Size(min = 6, max = 6, message = "Deve ter 6 caracteres")
    String senhaOrigem,
    @NotNull(message = "Insira um id de destino") 
    Long idDestino, 
    @NotNull(message = "Insira um valor") 
    String valor
) {}
