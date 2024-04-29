package com.robson.desafiopicpay.resources;

import org.springframework.web.bind.annotation.RestController;

import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/logistas")
public class UsuarioLogistaResource {
    
    @GetMapping
    public ResponseEntity<UsuarioLogista> findAll(){
        UsuarioLogista u = new UsuarioLogista("baaaa", "2222", "Robson Batista", "00148" );
        return ResponseEntity.ok().body(u);
    }
}
