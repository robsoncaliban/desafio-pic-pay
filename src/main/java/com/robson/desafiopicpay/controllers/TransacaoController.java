package com.robson.desafiopicpay.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.robson.desafiopicpay.dtos.request.TransacaoRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoEnviadaResponseDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoInsertResponseDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoRecebidaResponseDTO;
import com.robson.desafiopicpay.entities.Transacao;
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

    @GetMapping(value = "/recebidas/{idUsuario}")
    public ResponseEntity<List<TransacaoRecebidaResponseDTO>> findTransacoesRecebidasByUserId(
        @PathVariable Long idUsuario,
        @PageableDefault(page = 0, size = 5) Pageable page){

        List<Transacao> list = service.findTransacoesRecebidasByUserId(idUsuario, page).getContent();
        List<TransacaoRecebidaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoRecebidaResponseDTO transacaoRecebidaResponse =  new TransacaoRecebidaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }

    @GetMapping(value = "/enviadas/{idUsuario}")
    public ResponseEntity<List<TransacaoEnviadaResponseDTO>> findTransacoesEnviadasByUserId(
        @PathVariable Long idUsuario,
        @PageableDefault(page = 0, size = 5) Pageable page){

        List<Transacao> list = service.findTransacoesEnviadasByUserId(idUsuario, page).getContent();
        List<TransacaoEnviadaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoEnviadaResponseDTO transacaoRecebidaResponse = new TransacaoEnviadaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }

}
