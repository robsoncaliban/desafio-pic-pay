package com.robson.desafiopicpay.entities.usuarios;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

@Entity(name = "usuario_comum")
@DiscriminatorValue("C")
public class UsuarioComum extends Usuario {
    
    @CPF(message = "CPF inválido")
    private String cpf;
    

    public UsuarioComum() {
    }
    public UsuarioComum(@Email(message = "email inválido") String email, String senha, String nomeCompleto,
            @CPF(message = "CPF inválido") String cpf) {
        super(email, senha, nomeCompleto);
        this.cpf = cpf;
    }

    

    public void efetuarTransicao() {
        // TODO implementar isso
        throw new UnsupportedOperationException("Unimplemented method 'efetuarTransicao'");
    }




    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
}
