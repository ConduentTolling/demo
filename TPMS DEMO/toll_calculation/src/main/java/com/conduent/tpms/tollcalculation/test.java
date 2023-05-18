package com.conduent.tpms.tollcalculation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

public class test 
{
	public static void main(String[] args) throws ParseException 
	{
		check();
	}

	private static final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	public static void check() throws ParseException
	{
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yy");
        Date convertedDate = format1.parse("18-NOV-99");
        String convertedDate2 = format2. format(convertedDate).toUpperCase();
        System. out. println(convertedDate2);

	}
	
	public static void check2() throws ParseException
	{
		  Date dt = new Date();
	      SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
	      System.out.println("Time: "+dateFormat.format(dt));
	}
	
	public static void check3()
	{
		String timeStamp = "2022-05-11 18:54:12.650-04:00";
		String time = timeStamp.substring(11,19);
		System. out. println(time);
	}
	
	public static void check4()
	{
		String timeStamp = "26-MAR-22 02.37.00.0 PM";
		String format = "[0-9]{2}[-][A-Z]{3}[-][0-9]{2} [0-9]{2}[.][0-9]{2}[.][0-9]{2}[.][0-9]{1,9} ([AP]{1}[M]{1})";
		if(timeStamp.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
		
	}
	
	public static void check9()
	{
		String timeStamp = "31-Mar-22";
		String format = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{2}$";
		//^([0-9]|[1-2][0-9]|3[0-1])$
		if(timeStamp.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
		
	}
	
	
	public static void check10()
	{
		String timeStamp = "12.59.15";
		String format = "^(?:(?:([01]?\\d|2[0-3]).)?([0-5]?\\d).)?([0-5]?\\d)$";
		if(timeStamp.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
		
	}
	
	public static void check7()
	{
		String timeStamp = "02:37:00 am";
		String format ="(1[012]|[1-9]):[0-5][0-9](\\s)::[0-5][0-9](\\s)?(?i)(am|pm)";
		if(timeStamp.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
		
	}
	
	
	
	public static void check5() throws ParseException
	{
		String txDate = "2022-04-14";
		String format = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}";
		if(txDate.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
	}
	
	public static void check6()
	{
		String txDate = "2022-01-01";
		String format = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		if(txDate.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
	}
	
	public static void check8()
	{
		String entryPlazaId = "10";
		String format = "[0-9]{1,2}";
		if(entryPlazaId.matches(format))
		{
			System. out. println("Matched");
		}
		else
		{
			System. out. println("UnMatched");
		}
	}
	
	public static void check11()
	{
		String input = "2022-12-30";
		
		try {
		        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		         df.setLenient(false);
		         df.parse(input);
		         System. out. println("Matched");
		        } catch (ParseException e) {
		        	System. out. println("UnMatched");
		        }
	}
}
