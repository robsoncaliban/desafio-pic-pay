package com.robson.desafiopicpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.command.UsuarioComum;

public interface UsuarioComumRepository extends JpaRepository<UsuarioComum, Long>{
    Optional<UsuarioComum> findByCpf(String cpf);
}
