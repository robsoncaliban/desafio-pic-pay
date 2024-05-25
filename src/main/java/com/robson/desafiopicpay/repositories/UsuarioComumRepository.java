package com.robson.desafiopicpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.command.UsuarioComum;

public interface UsuarioComumRepository extends JpaRepository<UsuarioComum, Long>{
    UsuarioComum findByCpf(String cpf);
}
