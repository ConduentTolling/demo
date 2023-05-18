package com.conduent.tpms.unmatched;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class test 
{
	public static void main(String[] args) throws ParseException 
	{
		check3();
	}

	private static void check3() 
	{
		double terminalPlaza1Info = 1.2;
		double terminalPlaza2Info = 2.31;
		double terminalPlaza3Info = 2.32;
		
		if ( terminalPlaza1Info > terminalPlaza2Info && terminalPlaza1Info > terminalPlaza3Info )
		{
			System.out.println("Largest number is ::"+ terminalPlaza1Info);
	     }
		else if ( terminalPlaza2Info > terminalPlaza1Info && terminalPlaza2Info> terminalPlaza3Info )
	      
	      {
			System.out.println("Largest number is ::"+ terminalPlaza2Info);
	      }
	      else if ( terminalPlaza3Info > terminalPlaza1Info && terminalPlaza3Info > terminalPlaza2Info )
	      {
	    	  System.out.println("Largest number is ::"+ terminalPlaza3Info);
	      }
		
	}

	public static void check() throws ParseException
	{
		LocalDateTime time = LocalDateTime.now();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyddMM HH:mm:ssSSS");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyddMM HHmmssSS");
        Date convertedDate = format1.parse(time.toString());
        String convertedDate2 = format2. format(convertedDate).toUpperCase();
        System. out. println(convertedDate2);
	}
	
	public static void check2() throws ParseException
	{
		  Date dt = new Date();
	      SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
	      System.out.println("Time: "+dateFormat.format(dt));
	}
}
