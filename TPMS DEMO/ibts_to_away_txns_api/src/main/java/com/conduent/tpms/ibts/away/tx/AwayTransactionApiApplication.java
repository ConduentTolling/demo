package com.conduent.tpms.ibts.away.tx;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Springboot Application for ICTX creation process.
 * 
 * @author deepeshb
 *
 */
@SpringBootApplication
@ComponentScan({"com.conduent.*"})
public class AwayTransactionApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AwayTransactionApiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// TODO: Start Process
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
	}
}
