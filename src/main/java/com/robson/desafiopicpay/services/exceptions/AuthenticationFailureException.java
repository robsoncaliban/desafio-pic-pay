package com.robson.desafiopicpay.services.exceptions;

public class AuthenticationFailureException extends RuntimeException {

    public AuthenticationFailureException(Long id) {
        super("Autenticação do usuario : id " + id+ " falhou");
    }
    
}
