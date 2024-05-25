package com.robson.desafiopicpay.services.exceptions;

public class TransactionNotCompletedException extends RuntimeException {

    public TransactionNotCompletedException(String mensagem) {
        super("Transação não efetuada: " + mensagem);
    }
}
