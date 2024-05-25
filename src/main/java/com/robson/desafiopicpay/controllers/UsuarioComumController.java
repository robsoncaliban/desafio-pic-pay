package com.robson.desafiopicpay.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.dtos.UsuarioDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioComumRequestDTO;
import com.robson.desafiopicpay.dtos.response.TransacaoEnviadaResponseDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.services.UsuarioComumService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios-comuns")
public class UsuarioComumController {
    
    private UsuarioComumService service;

    public UsuarioComumController(UsuarioComumService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<UsuarioDTO> list = service.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        if(!list.isEmpty()){
            for (UsuarioDTO usuarioDTO : list) {
                Link link = linkTo(methodOn(UsuarioController.class).findById(usuarioDTO.usuario().getId())).withSelfRel();
                UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioDTO.usuario(), link);
                response.add(responseDTO);
            }
        }
        
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> insertComum(@RequestBody @Valid UsuarioComumRequestDTO usuario) {
        UsuarioDTO usuarioDTO = service.insert(usuario);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioDTO.usuario(), null);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{id}/transacoes-enviadas")
    public ResponseEntity<List<TransacaoEnviadaResponseDTO>> findTransacoesEnviadasByUserId(@PathVariable Long id){
        List<Transacao> list = service.findTransacoesEnviadasByUserId(id);
        List<TransacaoEnviadaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoEnviadaResponseDTO transacaoRecebidaResponse = new TransacaoEnviadaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }
}
