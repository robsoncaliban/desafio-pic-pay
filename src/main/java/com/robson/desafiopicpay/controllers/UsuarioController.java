package com.robson.desafiopicpay.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;

import com.robson.desafiopicpay.dtos.response.TransacaoRecebidaResponseDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.entities.Transacao;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    
    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<Usuario> usuarios = service.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            Link link = linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel();
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario, link);
            response.add(responseDTO);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id){
        Usuario usuario = service.findById(id);
        Link link = linkTo(methodOn(UsuarioController.class).findAll()).withRel("usuarios");
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario, link);
        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping(value = "/{id}/transacoes-recebidas")
    public ResponseEntity<List<TransacaoRecebidaResponseDTO>> findTransacoesRecebidasByUserId(@PathVariable Long id){
        List<Transacao> list = service.findTransacoesRecebidasByUserId(id);
        List<TransacaoRecebidaResponseDTO> responseDTOs = new ArrayList<>();
        for (Transacao transacao : list) {
            TransacaoRecebidaResponseDTO transacaoRecebidaResponse =  new TransacaoRecebidaResponseDTO(transacao);
            responseDTOs.add(transacaoRecebidaResponse);
        }
        return ResponseEntity.ok().body(responseDTOs);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateBYId(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO update){
        Usuario usuario = service.updateById(id, update);
        Link link = linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel();
        return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario, link));
    }  
}
