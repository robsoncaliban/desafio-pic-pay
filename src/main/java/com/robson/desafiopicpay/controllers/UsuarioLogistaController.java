package com.robson.desafiopicpay.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.dtos.UsuarioDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioLogistaRequestDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.services.UsuarioLogistaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping(value = "/usuarios-logistas")
public class UsuarioLogistaController {
    private UsuarioLogistaService service;

   public UsuarioLogistaController(UsuarioLogistaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<UsuarioDTO> list = service.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        for (UsuarioDTO usuarioDTO : list) {
            Link link = linkTo(methodOn(UsuarioController.class).findById(usuarioDTO.usuario().getId())).withSelfRel();
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioDTO.usuario(), link);
            response.add(responseDTO);
        }
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> insertLogista(@RequestBody @Valid UsuarioLogistaRequestDTO usuario) {
        UsuarioDTO usuarioDTO = service.insert(usuario);
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioDTO.usuario(), null);
        return ResponseEntity.ok().body( responseDTO);
    }

}
