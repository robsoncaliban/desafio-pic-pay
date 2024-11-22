package com.robson.desafiopicpay.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record EmailRequestDTO(
    @NotBlank String emailDestino, 
    @NotBlank String assunto, 
    @NotBlank String mensagemHtml) {
    
}
