package com.onerb.apiattornatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Person Api Attornatus", version = "1", description = "API developed for Attornatus testing"))
public class ApiAttornatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAttornatusApplication.class, args);
	}

}
