package com.conduent.tpms.qatp.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
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
			catch (Exception e) {

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
			catch (Exception e) {
				e.printStackTrace();
			}
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
}
