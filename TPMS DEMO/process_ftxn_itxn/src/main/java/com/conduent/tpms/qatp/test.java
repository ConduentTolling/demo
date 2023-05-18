package com.conduent.tpms.qatp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import com.conduent.tpms.qatp.constants.CorrConstants;
import java.util.Date;
import java.util.Locale;

import com.conduent.tpms.qatp.utility.Convertor;

public class test 
{
	
	public static void main(String[] args) throws ParseException 
	{
		check1();
	}

	private static void check1() 
	{
		String value = "131_008_20220608054028.INTX";
		System.out.println(value.substring(8,16));

		
	}

	private static void check() throws ParseException 
	{
		String etcTagPgmStatus = "00AB57091395";
		if(etcTagPgmStatus.matches("[0-9]{12}"))
		{
			System.out.println("Valid");
		}
		else
		{
			System.out.println("InValid");
		}

	}
	
	


}
