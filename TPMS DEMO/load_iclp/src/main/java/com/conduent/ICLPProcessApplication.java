package com.conduent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conduent.app.timezone.utility.TimeZoneConv;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class ICLPProcessApplication  {
	
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ICLPProcessApplication.class, args);
	}
}
