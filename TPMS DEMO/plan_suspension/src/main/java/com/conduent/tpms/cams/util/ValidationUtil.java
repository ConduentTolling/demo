package com.conduent.tpms.cams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conduent.tpms.cams.service.impl.PlanSuspensionServiceImpl;

@Component
public class ValidationUtil {
	
	final static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
	
	/**
	 * Validates if input contains only digits
	 * @param str
	 * @return
	 */
	
	public static boolean isDateValid(String dateString) {
		
		if(dateString != null && dateString.matches("[0-9]{2}[/][0-9]{2}[/]{1}[0-9]{4}"))
		{
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		try {
		Date d1=sdf.parse(dateString);
		if(d1!=null) {
			return true;
		}
		} catch (ParseException e) {
			System.out.println("Invalid Date");
			return false;
		}
		}
		else
			System.out.println("Invalid Date");
		    return false;
		}
	}



