package com.robson.desafiopicpay.dtos.request;

import org.hibernate.validator.constraints.br.CNPJ;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioLogistaRequestDTO(
    @NotBlank(message = "Insira o nome do usuario") @Size(min = 7, message = "Deve ter mais de 7 carateres")
    String nomeCompleto, 
    @NotBlank(message = "Insira o email do usuario") @Email(message = "Formato de email inválido") 
    String email, 
    @NotBlank(message = "Insira uma senha") @Size(min = 4, message = "Deve ter mais de 4 carateres")
    String senha,
    @NotNull(message = "Insira um saldo inicial")
    double saldo, 
    @NotBlank(message = "Insira um cnpj") @CNPJ(message = "Formato de CNPJ inválido")
    String cnpj) {
}
