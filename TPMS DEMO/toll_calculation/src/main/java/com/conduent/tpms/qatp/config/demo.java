package com.conduent.tpms.qatp.config;

import java.time.LocalDate;

public class demo {

	public static void main(String[] args) 
	{
		
	        LocalDate date = LocalDate.of(2022, 12, 29);	    
	        System.out.println("Day is : " + date.getDayOfWeek().toString());
	        System.out.println("Day in number is : " + date.getDayOfWeek().getValue());
	      	
	}

}
