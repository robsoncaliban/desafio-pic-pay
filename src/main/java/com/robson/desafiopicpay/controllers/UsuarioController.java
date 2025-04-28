package com.robson.desafiopicpay.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.controllers.exceptions.StandardError;
import com.robson.desafiopicpay.dtos.response.UsuarioGetByIdResponseDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Gestão de Usuários")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    
    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    
    @GetMapping
    @Operation(summary = "Busca todos os usuários cadastrados", method = "GET")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", 
         content = @Content(mediaType = "application/json", 
         schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<List<UsuarioResponseDTO>> findAll(
        @ParameterObject @PageableDefault(page = 0, size = 10) Pageable page) {

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
    @Operation(summary = "Busca um usuário pelo id", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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

}
