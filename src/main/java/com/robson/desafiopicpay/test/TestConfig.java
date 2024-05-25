package com.robson.desafiopicpay.test;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.robson.desafiopicpay.dtos.request.UsuarioComumRequestDTO;
import com.robson.desafiopicpay.entities.command.UsuarioComum;
import com.robson.desafiopicpay.entities.usuarios.Usuario;
import com.robson.desafiopicpay.repositories.UsuarioRepository;

@Configuration
public class TestConfig implements CommandLineRunner{

    private UsuarioRepository usuarioRepository;
    

    public TestConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public void run(String... args)  {
        UsuarioComumRequestDTO dtoComum = new UsuarioComumRequestDTO("Alfredo Silva", "alfredo@gmail.com", "22223", 9.0 ,"00738700053" );
        UsuarioComumRequestDTO dtoLogista = new UsuarioComumRequestDTO("Luciano Henrrique", "robsoncaiban@gmail.com", "555585", 1550.30, "51757917020");
        // 26193723000148
        Usuario u1 = new UsuarioComum(dtoLogista);
        Usuario u2 = new UsuarioComum(dtoComum);

        usuarioRepository.saveAll(Arrays.asList(u1,u2));
    }


}
