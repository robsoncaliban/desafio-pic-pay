package com.robson.desafiopicpay.dtos.request;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioComumRequestDTO(
    @NotBlank(message = "Insira o nome do usuario") @Size(min = 7, message = "Deve ter mais de 7 carateres")
    String nomeCompleto, 
    @NotBlank(message = "Insira o email do usuario") @Email(message = "Formato de email inválido") 
    String email, 
    @NotBlank(message = "Insira uma senha") @Size(min = 4, message = "Deve ter mais de 4 carateres")
    String senha,
    @NotNull(message = "Insira um saldo inicial")
    double saldo, 
    @NotBlank(message = "Insira um cpf") @CPF(message = "Formato de CPF inválido")
    String cpf
) {}
