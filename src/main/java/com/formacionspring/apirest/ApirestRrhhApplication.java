package com.formacionspring.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="API RRHH",version="1.0",description="Crud completo de RRHH"))
public class ApirestRrhhApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApirestRrhhApplication.class, args);
	}

}
