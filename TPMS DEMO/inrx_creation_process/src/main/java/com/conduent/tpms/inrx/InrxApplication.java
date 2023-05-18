package com.conduent.tpms.inrx;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.inrx.service.InrxProcessService;

/**
 * Springboot Application for ICRX creation process.
 * 
 * @author petetid
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.conduent.*"})
public class InrxApplication implements CommandLineRunner {

	@Autowired
	InrxProcessService inrxProcessService;
	
	public static void main(String[] args) {
		SpringApplication.run(InrxApplication.class, args);
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
