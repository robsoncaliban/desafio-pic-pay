package com.robson.desafiopicpay.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByEmail(String email);

}
