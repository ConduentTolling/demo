package com.conduent.tollposting.utility;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils
{
	public static final String	PATTERN_DATE		= "YY/MM/dd";
	public static final String	PATTERN_TIME		= "HH:mm:ss";
	public static final String	PATTERN_TIME_SHORT  = "HH:mm";
	public static final String	PATTERN_DATE_US		= "MM-dd-YYYY";
	public static final String  PATTERN_DATE_TIME	= "MM-dd-YYYY HH:mm:ss";
	public static final String  REPORT_DATE_FORMAT	= "YYYY-MM-dd HH:mm:ss";
	public static final String  QUERY_DATE_TIME		= PATTERN_DATE + " " + PATTERN_TIME;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;


	public static Date parseDate(final String value, final String pattern) 
	{
		if (!Convertor.isEmpty(value)) 
		{
			try 
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				return simpleDateFormat.parse(value);
			}
			catch (Exception e)
			{

			}
		}
		return null;
	}
	
	public static LocalDate parseLocalDate(final String value, final String pattern) 
	{
		if (!Convertor.isEmpty(value)) 
		{
			try 
			{
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
				return LocalDate.parse(value, dateFormatter);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static LocalDateTime parseLocalDateTime(final String value, final String pattern) 
	{
		if (!Convertor.isEmpty(value)) 
		{
			try 
			{
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
				return LocalDateTime.parse(value, dateFormatter);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String parseLocalDate(final LocalDate value, final String pattern) 
	{
		
			try 
			{
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
				return value.format(dateFormatter);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public static Timestamp getTimeStamp(final String value, final String pattern) 
	{
		if (!Convertor.isEmpty(value)) 
		{
			try 
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
				Date parsedDate = dateFormat.parse(value);
				return new java.sql.Timestamp(parsedDate.getTime());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static Boolean timeValidation(String inputValue, String validValue) {
		boolean isValid=false;
		if (!StringUtils.isBlank(inputValue)) {
			switch (validValue) {
			case "HHMMSS":
				if (inputValue.length() != 6)
				{
					return false;
				}
				try 
				{
					int hr = Integer.parseInt(inputValue.substring(0, 2));
					int min = Integer.parseInt(inputValue.substring(2, 4));
					int sec = Integer.parseInt(inputValue.substring(4, 6));
					if((hr >=0 && hr<24) && (min >=0 && min<=59) && (sec >=0 && sec<=59) )
					{
						isValid= true;
					}
					else
					{
						isValid= false;
					}

				} catch (NumberFormatException e) 
				{
					isValid= false;
				} 
				break;
			case "HHMMSSTT":
				if (inputValue.length() != 8)
				{
					isValid=false;
				}
				else
				{
					isValid=true;
					try 
					{
						int hr = Integer.parseInt(inputValue.substring(0, 2));
						int min = Integer.parseInt(inputValue.substring(2, 4));
						int sec = Integer.parseInt(inputValue.substring(4, 6));
						int tt = Integer.parseInt(inputValue.substring(6, 8));
						if((hr >=0 && hr<24) && (min >=0 && min<=59) && (sec >=0 && sec<=59) && (tt >=0 && sec<=99) )
						{
							isValid= true;
						}
						else
						{
							isValid= false;
						}

					} catch (NumberFormatException e) 
					{
						isValid= false;
					} 
				}
				break;
			default:
				isValid=false;
			}
		}
		return isValid;
	}
	public static Boolean dateValidation(String inputValue, String validValue) {
		// TO DO: Handle Number format Exception if string
		if (!StringUtils.isBlank(inputValue)) {
			//Below remark is not fixed , if we want to add another format in future
			switch(validValue) {
			case "YYYYMMDD":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;
			case "YYYYMMDDHHMMSS":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;

			default:
				return false;
			}
		}
		return false;
	}
	
	public static Object convertToDateFormat(String date) throws java.text.ParseException 
	{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
		Date convertedDate = format1.parse(date);
		String convertedDate2 = format2. format(convertedDate).toUpperCase();
		System. out. println(convertedDate2);
		return convertedDate2;
		}


	
}