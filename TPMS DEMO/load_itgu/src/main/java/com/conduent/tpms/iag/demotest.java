package com.conduent.tpms.iag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.app.timezone.utility.TimeZoneConv;

public class demotest {
	
	@Autowired
	static
	TimeZoneConv timeZoneConv;

	public static void main(String[] args) 
	{
		example();
		
	}
	
	
	public static void example()
	{
		System.out.println("Time"+timeZoneConv.currentTime());
		System.out.println("Time"+LocalDateTime.now());
	}
}
