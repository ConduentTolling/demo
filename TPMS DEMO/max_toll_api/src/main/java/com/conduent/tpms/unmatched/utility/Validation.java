package com.conduent.tpms.unmatched.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validation 
{
	private static final Logger val_log = LoggerFactory.getLogger(Validation.class);
	
	public boolean dateValidation(String txDate)
	{
		//2022-04-14
		String format = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		if(txDate.matches(format))
		{
			return true;
		}
		return false;
		
	}
	
	public boolean dateinTimestampValidation(String dateinTimestamp)
	{
		//26-MAR-22
		//String format = "[0-9]{2}[-][A-Z]{3}[-][0-9]{2} [0-9]{2}[.][0-9]{2}[.][0-9]{2}[.][0-9]{1,9} ([AP]{1}[M]{1})";
		String format = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\-\\d{2}$";
		if(dateinTimestamp.matches(format))
		{
			return true;
		}
		return false;
		
	}
	
	public boolean timeValidation(String time)
	{
		//02.37.00
		String format = "^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$";
		//String format = "^(?.(?.([01]?\\d|2[0-3]).)?([0-5]?\\d).)?([0-5]?\\d)$";
		if(time.matches(format))
		{
			return true;
		}
		return false;
		
	}
	
	public boolean numberFormatValidation(String value)
	{
		//1,15
		String format = "[0-9]{1,20}";
		if(value.matches(format))
		{
			return true;
		}
		return false;
	}
	
	public boolean alphabetFormatValidation(String value)
	{
		// C, X, B, P
		String format = "^[A-Z]*$";
		if(value.matches(format))
		{
			return true;
		}
		return false;
	}
	
	public String timeFormatCorrection(String time)
	{
		//04.44.22
        val_log.info("Input Time is : {}", time);
        String correctedTime = time.replace(".",":");
        val_log.info("Corrected Time is : {}", correctedTime);
		return correctedTime; //04:44:22	
	}
	
			
}
