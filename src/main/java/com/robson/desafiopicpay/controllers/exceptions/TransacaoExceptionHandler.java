package com.robson.desafiopicpay.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.robson.desafiopicpay.services.exceptions.TransactionForbiddenException;
import com.robson.desafiopicpay.services.exceptions.TransactionNotCompletedException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TransacaoExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(TransactionNotCompletedException.class)
    private ResponseEntity<StandardError> transactionNotCompletd(TransactionNotCompletedException e, HttpServletRequest request){
        String erro = "Bad request";
        Integer status = HttpStatus.BAD_REQUEST.value();
        StandardError sError = new StandardError(Instant.now(), status, erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(sError);
    }

    @ExceptionHandler(TransactionForbiddenException.class)
    private ResponseEntity<StandardError> transactionForbiddenException(TransactionForbiddenException e, HttpServletRequest request){
        String erro = "Bad request";
        Integer status = HttpStatus.BAD_REQUEST.value();
        StandardError sError = new StandardError(Instant.now(), status, erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(sError);
    }
}