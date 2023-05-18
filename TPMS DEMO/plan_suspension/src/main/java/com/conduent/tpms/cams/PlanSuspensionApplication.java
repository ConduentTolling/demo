package com.conduent.tpms.cams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@OpenAPIDefinition
@Validated
@SpringBootApplication
@EnableSwagger2
public class PlanSuspensionApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(PlanSuspensionApplication.class, args);

	}
}
