package com.robson.desafiopicpay.entities;

import org.hibernate.validator.constraints.br.CNPJ;

public class UsuarioLogista extends Usuario {

    @CNPJ
    private String CNPJ;
}
