
package com.conduent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conduent.app.timezone.utility.TimeZoneConv;

/**
 * To run the service
 * 
 * @author urvashic
 *
 */

@SpringBootApplication
@Configuration
//@ComponentScan({"com.conduent.tpms.iag"})
public class IAGApplication {
	
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}

	public static void main(String[] args) {
		SpringApplication.run(IAGApplication.class, args);
	}
}