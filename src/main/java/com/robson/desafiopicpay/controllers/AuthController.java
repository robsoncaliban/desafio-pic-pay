package com.robson.desafiopicpay.controllers;

import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.controllers.exceptions.StandardError;
import com.robson.desafiopicpay.dtos.request.LoginRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;
import com.robson.desafiopicpay.dtos.request.UsuarioUpdateRequestDTO;
import com.robson.desafiopicpay.dtos.response.TokenResponseDTO;
import com.robson.desafiopicpay.dtos.response.UsuarioResponseDTO;
import com.robson.desafiopicpay.entities.UserDetailsImpl;
import com.robson.desafiopicpay.entities.Usuario;
import com.robson.desafiopicpay.services.AuthenticationService;
import com.robson.desafiopicpay.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Gestão de Autenticação")
@RestController
@RequestMapping(value = "/auth")
@CrossOrigin("*")
public class AuthController {
    
    private AuthenticationService authenticationService;
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AuthController(AuthenticationService authenticationService, TokenService tokenService,
            AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Cria um usuário", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Usuário adicionado"),
        @ApiResponse(responseCode = "400",description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "409",description = "Conflito de credenciais",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/register")
    public ResponseEntity<UsuarioResponseDTO> insert(@RequestBody @Valid UsuarioRequestDTO usuario) {
        Usuario usuarioComum = authenticationService.insert(usuario); 
        Link link = linkTo(methodOn(UsuarioController.class).findById(usuarioComum.getId())).withSelfRel();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(usuarioComum, link);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO login){
        var usernameToken = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var authentication = authenticationManager.authenticate(usernameToken);
        UserDetailsImpl usuario = (UserDetailsImpl) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario.getUsername());
        return ResponseEntity.ok().body(new TokenResponseDTO(token));
    }

    @Operation(summary = "Atualiza nome e/ou senha de um usuário buscado por id", method = "PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "400", description = "Parametros inválidos")
    })
    @PatchMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponseDTO> updateById(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO update){
        Usuario usuario = authenticationService.updateById(id, update);
        Link link = linkTo(methodOn(UsuarioController.class).findById(usuario.getId())).withSelfRel();
        return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario, link));
    }  
}
