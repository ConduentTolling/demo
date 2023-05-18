package com.conduent.tpms.iag;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringEscapeUtils;

public class Test {

	public static void main(String[] args) {
		//if ("9".equalsIgnoreCase(StringEscapeUtils.unescapeJava(validValue))) {
		String value = new String("0x02".getBytes(StandardCharsets.US_ASCII));
		//String value1 = System.Text.Encoding.ASCII.GetBytes("\u0002");
		System.out.println(value);
		
		} 
	}

