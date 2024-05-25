package com.robson.desafiopicpay.services.exceptions;

public class TransactionForbiddenException extends RuntimeException {

    public TransactionForbiddenException(Object id) {
        super("Usuario proibido de efetuar transação. id: " +id);
    }
}
