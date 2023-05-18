
package com.conduent;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;

/**
 * To run the service
 * 
 * @author taniyan
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
	
	/**
	 * 
	 * @param builder
	 * @return RestTemplate
	 */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
    }
     

}
