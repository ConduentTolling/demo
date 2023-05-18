package com.conduent.tpms.inserttable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import com.conduent.app.timezone.utility.TimeZoneConv;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@OpenAPIDefinition
@Validated
@SpringBootApplication(scanBasePackages = {"com.conduent.*"} )
@EnableSwagger2
public class InsertData25AApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(InsertData25AApplication.class, args);

	}
	
	@Bean("timezoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	

}
