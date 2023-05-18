package com.conduent.tpms.qatp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import com.conduent.tpms.qatp.constants.Constants;

public class test 
{
	
	public static void main(String[] args) throws ParseException 
	{
		check1();
	}

	private static void check1() 
	{
		String value = "ABB";
		String matcher_String = "\\s{"+value.length()+"}|[*|0| ]{"+value.length()+"}";
		if(value.matches(matcher_String))
		{
			System.out.println("Valid");
		}
		else
		{
			System.out.println("InValid");
		}
		
	}

	private static void check() throws ParseException 
	{
		OffsetDateTime time = OffsetDateTime.parse("2022-06-08T03:02:15.650");
		
		System.out.println(time.format(DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSZ")));

	}


}
