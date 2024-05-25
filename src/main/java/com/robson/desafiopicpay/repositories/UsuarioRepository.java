package com.robson.desafiopicpay.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.desafiopicpay.entities.usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Usuario findByEmail(String email);

}
