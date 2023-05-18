package com.conduent.tpms.qatp.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Convertor 
{
	public static Long toLong(String str)
	{
		try
		{
			return Long.valueOf(str);
		}catch(Exception ex){ 	}
		return null;
	}
	
	public static boolean isAlphaNumeric(String s) 
	{
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

	public static Integer toInteger(String str)
	{
		try
		{
			return Integer.valueOf(str);
		}catch(Exception ex){ 
			//ex.printStackTrace();
		}
		return 0;
	}
	
	public static Integer toInteger(long str)
	{
		try
		{
			return (int)str;  
		}catch(Exception ex){ 
			//ex.printStackTrace();
		}
		return null;
	}
	public static Double toDouble(Long value)
	{
		try
		{
			return Double.valueOf(value);
		}catch(Exception ex){ 	}
		return null;
	}
	public static Double toDouble(String value)
	{
		try
		{
			return Double.valueOf(value);
		}catch(Exception ex){ 	}
		return null;
	}
	
	public static Double toDouble(int value)
	{
		try
		{
			return Double.valueOf(value);
		}catch(Exception ex){ 	}
		return null;
	}
	
	public static Double toDouble(double value)
	{
		try
		{
			return Double.valueOf(value);
		}catch(Exception ex){ 	}
		return null;
	}

	public LocalDate getFormattedDate(String dateValue,String formatType) {

		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

			return LocalDate.parse(dateValue, formatter);
		}
		catch(Exception ex)
		{

		}
		return null;

	}
	public static boolean isEmpty(final String value)
	{
		return value == null || value.isEmpty();
	}
}
