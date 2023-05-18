package com.conduent.tpms.iag;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Test 
{
	public static void main(String[] args) 
	{
		//check();
		check2();
		//calculate();
		//print();
		//calculateamount();
	}
	
	public static void print()
	{
		double usageamount = 150.23;
		System.out.println("Amount:"+usageamount);
	}
	
	public static void check()
	{
		Integer usageamount = 180;
		Integer minusage = 100;
		Integer maxusage = 999;
		if(usageamount >= minusage && usageamount <= maxusage)
		{
			System.out.println("Correct");
		}
		else
		{
			System.out.println("Incorrect");
		}
	}
	public static void check2()
	{
		Integer tripmade = 25;
		Integer minusage = 20;
		Integer maxusage = 50;
		if(tripmade >= minusage && tripmade <= maxusage)
		{
			Date date = new Date();
			
			System.out.println(LocalDateTime.now());
		}
		else
		{
			System.out.println("Incorrect");
		}
	}
	
	public static void calculateamount()
	{
		int tripleft = 2;
		double amount = 12.9;
		double amountnFareValue = tripleft * amount;
		System.out.println("Amount:"+amountnFareValue);
	}
	
	
	public static void calculate()
	{
		Integer usageamount = 150;
		Integer benefitValue = 23;
		double amountPercentValue = (usageamount * benefitValue) / 100;
		
		System.out.println("Amount:"+amountPercentValue);
	}
}
