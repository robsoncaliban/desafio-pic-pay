package com.robson.desafiopicpay.entities.usuarios;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

@Entity(name = "usuario_logista")
@DiscriminatorValue("L")
public class UsuarioLogista extends Usuario {

    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    
    public UsuarioLogista() {
    }

    public UsuarioLogista(@Email(message = "email inválido") String email, String senha, String nomeCompleto,
            @CNPJ(message = "CNPJ inválido") String cnpj) {
        super(email, senha, nomeCompleto);
        this.cnpj = cnpj;
    }

    public String getCNPJ() {
        return cnpj;
    }

    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
    }

    
}
