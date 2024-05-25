package com.robson.desafiopicpay.services.exceptions;

public class DuplicateDataException extends RuntimeException{
    
    public DuplicateDataException(String credencial) {
        super("Credencial : '" +credencial + "' já esta cadastrada");
    }
}
