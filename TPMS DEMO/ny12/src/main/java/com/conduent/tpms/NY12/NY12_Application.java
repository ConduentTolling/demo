package com.conduent.tpms.NY12;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.conduent.tpms.NY12.config.LoadJpaApplicationContext;
import com.conduent.tpms.NY12.config.MyConfigProperties;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication(scanBasePackages = { "com.conduent.tpms.NY12", "com.conduent.app.timezone.utility.TimeZoneConv"})
public class NY12_Application {
	
	
	@Bean
	public ApplicationContext loadJpaApplicationContext() {
		return new LoadJpaApplicationContext().getApplicationContext();
	}

	@Bean(name="myConfigProperties")
	public MyConfigProperties myConfigProperties() {
		return new MyConfigProperties();
	};
	
	public static void main(String[] args) {
		SpringApplication.run(NY12_Application.class, args);
	}
    
}
