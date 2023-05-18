package com.conduent.parking.posting.utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
	
	/**
	 * Converts 2022-04-11 to 11-APR-22 format
	 * @param date
	 * @return
	 */
	public static String converDateTo(String date) {
		String newDate = null;
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-YY");
			Date convertedDate = format1.parse(date);
			newDate = format2. format(convertedDate);
			System. out. println(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
}