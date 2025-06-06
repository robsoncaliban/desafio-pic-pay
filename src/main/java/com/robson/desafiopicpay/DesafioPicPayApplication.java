package com.robson.desafiopicpay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Desafio Pic-Pay", version = "1", description = "API desenvolvida para fazer transações entre usuarios"))
public class DesafioPicPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicPayApplication.class, args);
	}
}
