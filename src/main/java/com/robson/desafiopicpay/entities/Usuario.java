package com.robson.desafiopicpay.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;

@MappedSuperclass
public abstract class Usuario {

    @Id
    @Email
    private String email;
    private String senha;
    private String nomeCompleto;
}
