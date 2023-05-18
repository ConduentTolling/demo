
package com.conduent.tpms.qatp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conduent.app.timezone.utility.TimeZoneConv;

@SpringBootApplication(scanBasePackages = {"com.conduent.*"} )
@Configuration
public class QatpApplication {

	public static void main(String[] args) {
		SpringApplication.run(QatpApplication.class, args);
	}
	
     
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
	return new TimeZoneConv();
	}
}
