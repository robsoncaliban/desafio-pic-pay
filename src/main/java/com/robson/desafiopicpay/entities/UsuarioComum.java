package com.robson.desafiopicpay.entities;

import org.hibernate.validator.constraints.br.CPF;

public class UsuarioComum extends Usuario {
    @CPF
    private String CPF;

    public void efetuarTransicao() {
        // TODO implementar isso
        throw new UnsupportedOperationException("Unimplemented method 'efetuarTransicao'");
    }
}
