package com.robson.desafiopicpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Desafio Pic-Pay", version = "1", description = "API desenvolvida para fazer transações entre usuarios"))
public class DesafioPicPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicPayApplication.class, args);
	}
	
}
