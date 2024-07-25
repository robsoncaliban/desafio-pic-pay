package com.robson.desafiopicpay.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.robson.desafiopicpay.controllers.UsuarioController;
import com.robson.desafiopicpay.dtos.request.UsuarioRequestDTO;

@Configuration
public class TestConfig implements CommandLineRunner{

    private UsuarioController controller;
    

    public TestConfig(UsuarioController controller) {
        this.controller = controller;
    }


    @Override
    public void run(String... args)  {
        String senhaGeral = "123456";
        String saldoGeral = "500.0";

        UsuarioRequestDTO comum01 = new UsuarioRequestDTO("Alfredo Silva", "alfredo@gmail.com", senhaGeral, saldoGeral, "00738700053");
        UsuarioRequestDTO comum02 = new UsuarioRequestDTO("Robson Batista", "robsoncaliban@gmail.com", senhaGeral, saldoGeral, "51757917020");
        UsuarioRequestDTO comum03 = new UsuarioRequestDTO("Mateus Camelo", "mateus@gmail.com", senhaGeral, saldoGeral, "24482587087");

        UsuarioRequestDTO logista01 = new UsuarioRequestDTO("Jorge Silva", "jorge@gmail.com", senhaGeral, saldoGeral, "65331218000174");
        UsuarioRequestDTO logista02 = new UsuarioRequestDTO("Tiago Silva", "tiago@gmail.com", senhaGeral, saldoGeral, "34164996000192");
        UsuarioRequestDTO logista03 = new UsuarioRequestDTO("Gilmar Silva", "gilmar@gmail.com", senhaGeral, saldoGeral, "29849202000194");
        

        controller.insert(comum01);
        controller.insert(comum02);
        controller.insert(comum03);
        controller.insert(logista01);
        controller.insert(logista02);
        controller.insert(logista03);

        
    }


}
