package com.robson.desafiopicpay.dtos.request;

import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequestDTO(
    @Size(min = 5)
    String nomeCompleto, 
    @Size(min = 6, max = 6)
    String senha
) {

}
