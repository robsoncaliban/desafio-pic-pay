package com.robson.desafiopicpay.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioGetByIdResponseDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<UsuarioResponseDTO>> findAll(
        @PageableDefault(page = 0, size = 10) Pageable page) {

        List<Usuario> usuarios = service.findAll(page).getContent();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            Link link = linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel();
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuario, link);
            response.add(responseDTO);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioGetByIdResponseDTO> findById(@PathVariable Long id){
        Usuario usuario = service.findById(id);
        Pageable pageable = PageRequest.of(0, 10);
        Link link = linkTo(methodOn(UsuarioController.class).findAll(pageable)).withRel("usuarios");
        List<Link> historicoDeTransacoes = criarLinksTransacao(usuario.getId());
        UsuarioGetByIdResponseDTO responseDTO = new UsuarioGetByIdResponseDTO(usuario, link, historicoDeTransacoes);
        return ResponseEntity.ok().body(responseDTO);
    }
    
    private List<Link> criarLinksTransacao(Long id){
        Link transacoesEnviadas = linkTo(methodOn(TransacaoController.class)
        .findTransacoesEnviadasByUserId(id, null))
        .withRel("transacoesEnviadas");
        Link transacoesRecebidas = linkTo(methodOn(TransacaoController.class)
        .findTransacoesRecebidasByUserId(id, null))
        .withRel("transacoesRecebidas");
        return new ArrayList<>(Arrays.asList(transacoesEnviadas,transacoesRecebidas));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> insert(@RequestBody @Valid UsuarioRequestDTO usuario) {
        Usuario usuarioComum = service.insert(usuario); 
        Link link = linkTo(methodOn(UsuarioController.class).findById(usuarioComum.getId())).withSelfRel();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioComum, link);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateById(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO update){
        Usuario usuario = service.updateById(id, update);
        Link link = linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel();
        return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario, link));
    }  
}
