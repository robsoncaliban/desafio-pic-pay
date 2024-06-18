package com.robson.desafiopicpay.dtos.request;


import com.robson.desafiopicpay.validation.constraint.CpfCnpj;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
    @NotBlank(message = "Insira o nome do usuario") @Size(min = 7, message = "Deve ter mais de 7 carateres")
    String nomeCompleto, 
    @NotBlank(message = "Insira o email do usuario") @Email(message = "Formato de email inválido") 
    String email, 
    @NotBlank(message = "Insira uma senha")
    @Size(min = 6, max = 6,  message = "Deve ter mais de 6 carateres")
    String senha,
    @NotBlank(message = "Insira um saldo inicial")
    String saldo, 
    @NotBlank(message = "Insira um cpf ou cnpj") 
    @CpfCnpj
    String cpfCnpj
) {}
