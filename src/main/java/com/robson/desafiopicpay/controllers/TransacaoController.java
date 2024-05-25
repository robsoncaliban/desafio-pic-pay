package com.robson.desafiopicpay.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.dtos.request.TransacaoRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoInsertResponseDTO;
import com.robson.desafiopicpay.services.TransacaoService;


import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    private TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransacaoInsertResponseDTO> efetuarTransacao(@RequestBody @Valid TransacaoRequestDTO transacaoDTO){
        TransacaoInsertResponseDTO transacaoResponse = service.efetuarTransacao(transacaoDTO);
        return ResponseEntity.ok().body(transacaoResponse);
    }

}
