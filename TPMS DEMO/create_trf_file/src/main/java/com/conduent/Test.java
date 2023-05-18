package com.conduent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
//		 int i= 2;
//		String abc = String.format("%02d", i);
//		
//		String xyzz =" ";
//		System.out.println("abc ===>"+abc);
//		System.out.println( StringUtils.leftPad(xyzz, 5, " ") );
//		Double postedFare = 5.65;
//		postedFare = postedFare*100;
//		int integer = postedFare.intValue();
//		String xyz = String.format("%05d", integer);
//		System.out.println( "postedFare1  "+ postedFare);
//		System.out.println( "postedFare2  "+ integer);
//		System.out.println( "postedFare3  "+ xyz);
//		//System.out.println(String.format("%04d", postedFare));
//		String debitCredit = checkPositiveNegative(postedFare);
//		
//		System.out.println( "Debit-Credit Flag "+ debitCredit);
		
		String updateTs = "2022-10-14 16:02:00.18";
		//LocalDateTime ld = "2022-10-15T04:35:38.149";
		check(updateTs );
	}

	private static void check(String updateTs) throws ParseException {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss.SS");
        Date convertedDate = format1.parse(updateTs);
        String convertedDate2 = format2. format(convertedDate).toUpperCase();
        System. out. println(convertedDate2);
		
	}

	private static String checkPositiveNegative(double postedFare) {
		// TODO Auto-generated method stub
		if(postedFare>0) {
			return "+";
		}else {
			return "-";
		}
	}

	
	

}
