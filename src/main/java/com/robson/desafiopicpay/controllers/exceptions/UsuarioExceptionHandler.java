package com.robson.desafiopicpay.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.robson.desafiopicpay.services.exceptions.AuthenticationFailureException;
import com.robson.desafiopicpay.services.exceptions.DuplicateDataException;
import com.robson.desafiopicpay.services.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UsuarioExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateDataException.class)
    private ResponseEntity<StandardError> dadoJaCadastradoHandler(DuplicateDataException e, HttpServletRequest request){
        String erro = "Conflict";
        Integer status = HttpStatus.CONFLICT.value();
        StandardError sError = new StandardError(Instant.now(),status,erro,e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(sError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<StandardError> usuarioNaoEncontradoHandler(UserNotFoundException e, HttpServletRequest request){
        String erro = "Not found";
        Integer status = HttpStatus.NOT_FOUND.value();
        StandardError sError = new StandardError(Instant.now(), status, erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(sError);
    }

    @ExceptionHandler(AuthenticationFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<StandardError> erroAutenticacao(AuthenticationFailureException e, HttpServletRequest request){
        String erro = "Bad request";
        Integer status = HttpStatus.BAD_REQUEST.value();
        StandardError sError = new StandardError(Instant.now(), status, erro, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(sError);
    }
}
