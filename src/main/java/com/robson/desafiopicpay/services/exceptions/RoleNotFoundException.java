package com.robson.desafiopicpay.services.exceptions;

public class RoleNotFoundException extends RuntimeException{
    
    public RoleNotFoundException() {
        super("Role não encontrada");
    }
}
