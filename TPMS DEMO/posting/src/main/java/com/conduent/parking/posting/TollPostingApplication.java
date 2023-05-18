package com.conduent.parking.posting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.conduent.parking.posting.constant.ConfigVariable;

@SpringBootApplication
@ComponentScan(basePackages = "com.conduent.*")
@EnableAsync
@EnableTransactionManagement
public class TollPostingApplication 
{
	@Autowired
	static ConfigVariable configVariable;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(TollPostingApplication.class, args);
	}
}
