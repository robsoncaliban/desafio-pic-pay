package com.robson.desafiopicpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;

public interface UsuarioLogistaRepository extends JpaRepository<UsuarioLogista, Long> {
    Optional<UsuarioLogista> findByCnpj(String cnpj);
    
}
