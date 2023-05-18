package com.conduent.tpms.recon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class TestDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.out.println(String.format("%-[L]s", "7673").replace(' ', ' '));
		System.out.println(StringUtils.leftPad("7673", 12, " "));
		System.out.println(StringUtils.rightPad("7673", 12, " "));
		
		String s1 = "Hi";

		String s2 = new String("Hi");

		String s3 = "Hi";

System.out.println(s1 == s2); //false
System.out.println(s1.equals(s2)); //true

System.out.println(s1 == s3); //true
System.out.println(s1.equals(s3)); //true

String file;
file = new String("abc.csv");

String fileCreationTimestamp = LocalDateTime.now()
.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
System.out.println(fileCreationTimestamp);

System.out.println(Integer.toHexString(64));
		 

	}

}
