package com.robson.desafiopicpay.services.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(Object credencial) {
        super("Usuario com a credencial: " + credencial + " não encontrado");
    }


}
