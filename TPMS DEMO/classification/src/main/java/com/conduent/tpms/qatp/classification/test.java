package com.conduent.tpms.qatp.classification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

	public static void main(String[] args) throws ParseException {
		check();
	}

	private static void check1() 
	{
		SimpleDateFormat fromUser = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat myFormat = new SimpleDateFormat("hhmmss");

		try 
		{
			String inputString="15:30:12";
			String reformattedStr = myFormat.format(fromUser.parse(inputString));
			System.out.println(reformattedStr);
		} catch (ParseException e) {
		e.printStackTrace();
		}
		
	}

	private static void check() throws ParseException {
		
		String date="2022-09-03T05:49:19-04:00";
		System.out.println(date.substring(11,19));
	}
}
