package com.robson.desafiopicpay.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.robson.desafiopicpay.controllers.exceptions.StandardError;
import com.robson.desafiopicpay.dtos.request.TransacaoRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoEnviadaResponseDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoInsertResponseDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoRecebidaResponseDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.services.TransacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Gestão de Transações")
@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
    private TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @Operation(summary = "Efetua uma transação", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transação efetuada com sucesso",
         content = @Content(mediaType = "application/json", 
         schema = @Schema(implementation = TransacaoInsertResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransacaoInsertResponseDTO> efetuarTransacao(@RequestBody @Valid TransacaoRequestDTO transacaoDTO){
        TransacaoInsertResponseDTO transacaoResponse = service.efetuarTransacao(transacaoDTO);
        return ResponseEntity.ok().body(transacaoResponse);
    }

    @Operation(summary = "Busca transações recebidas pelo ID do usuário", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso", 
         content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = TransacaoRecebidaResponseDTO.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
         content = {@Content(mediaType = "application/json",
         schema = @Schema(implementation = StandardError.class))}),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping(value = "/recebidas/{idUsuario}")
    public ResponseEntity<List<TransacaoRecebidaResponseDTO>> findTransacoesRecebidasByUserId(
        @PathVariable Long idUsuario,
        @ParameterObject @PageableDefault(page = 0, size = 5) Pageable page){

        List<Transacao> list = service.findTransacoesRecebidasByUserId(idUsuario, page).getContent();
        List<TransacaoRecebidaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoRecebidaResponseDTO transacaoRecebidaResponse =  new TransacaoRecebidaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }

    @Operation(summary = "Busca transações enviadas pelo ID do usuário", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso", 
         content = {@Content(mediaType = "application/json",
         schema = @Schema(implementation = TransacaoEnviadaResponseDTO.class))}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
         content = {@Content(mediaType = "application/json",
         schema = @Schema(implementation = StandardError.class))}),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping(value = "/enviadas/{idUsuario}")
    public ResponseEntity<List<TransacaoEnviadaResponseDTO>> findTransacoesEnviadasByUserId(
        @PathVariable Long idUsuario,
        @ParameterObject @PageableDefault(page = 0, size = 5) Pageable page){

        List<Transacao> list = service.findTransacoesEnviadasByUserId(idUsuario, page).getContent();
        List<TransacaoEnviadaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoEnviadaResponseDTO transacaoRecebidaResponse = new TransacaoEnviadaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }

}
