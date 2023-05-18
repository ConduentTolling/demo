package com.conduent.tpms.icrx;

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

import com.conduent.tpms.icrx.service.IcrxProcessService;

/**
 * Springboot Application for ICRX creation process.
 * 
 * @author urvashic
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.conduent.*"})
public class IcrxApplication implements CommandLineRunner {

	@Autowired
	IcrxProcessService icrxProcessService;
	
	public static void main(String[] args) {
		SpringApplication.run(IcrxApplication.class, args);
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
