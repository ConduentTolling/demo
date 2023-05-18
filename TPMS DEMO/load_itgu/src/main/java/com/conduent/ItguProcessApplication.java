package com.conduent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.conduent.app.timezone.utility.TimeZoneConv;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
//@ComponentScan("com.conduent.app")
public class ItguProcessApplication implements CommandLineRunner {
		
	/*
	 * @Autowired GenericITagFileParserImpl genericITagFileParserImpl;
	 */

	
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(ItguProcessApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * genericITagFileParserImpl.fileParseTemplate(); }
	 */

}
