package com.conduent.tpms.iag;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.service.IagService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.conduent.*"})
public class IagApplication {
	
	@Autowired
	IagService iagService;

	public static void main(String[] args) {
		SpringApplication.run(IagApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder)
	{ 
	    return builder
	            .setConnectTimeout(Duration.ofMillis(100000))
	            .setReadTimeout(Duration.ofMillis(100000))
	            .build();
	}

}
