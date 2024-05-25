package com.robson.desafiopicpay.services.exceptions;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(Object id) {
        super("id " + id + " não encontrado");
    }


}
