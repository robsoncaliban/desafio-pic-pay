package com.robson.desafiopicpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.usuarios.UsuarioLogista;

public interface UsuarioLogistaRepository extends JpaRepository<UsuarioLogista, Long> {
    UsuarioLogista findByCnpj(String cnpj);
    
}
