package com.conduent.tpms.intx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.conduent.app.timezone.utility.TimeZoneConv;

@SpringBootApplication
@ComponentScan({"com.conduent.*"})
public class CreateIntxApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateIntxApplication.class, args);
	}
	
	@Bean("timeZoneConv")
    public TimeZoneConv getCurrentDateAndTime() {
        return new TimeZoneConv();
    }

}
