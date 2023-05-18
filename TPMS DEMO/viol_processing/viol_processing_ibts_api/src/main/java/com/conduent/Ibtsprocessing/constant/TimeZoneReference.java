package com.conduent.Ibtsprocessing.constant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.conduent.app.timezone.utility.TimeZoneConv;

@Configuration
@ComponentScan("com.conduent.app")
public class TimeZoneReference {

	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
}
