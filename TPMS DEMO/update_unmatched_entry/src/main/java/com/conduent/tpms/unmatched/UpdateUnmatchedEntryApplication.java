package com.conduent.tpms.unmatched;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.conduent.app.timezone.utility.TimeZoneConv;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Validated
@EnableSwagger2
@EnableWebMvc
@SpringBootApplication(scanBasePackages = { "com.conduent.*" })
public class UpdateUnmatchedEntryApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(UpdateUnmatchedEntryApplication.class, args);

	}
	
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
	

}
