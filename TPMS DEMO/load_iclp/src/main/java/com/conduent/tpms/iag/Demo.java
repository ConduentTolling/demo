package com.conduent.tpms.iag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
	
	public static void main(String[] args) {
		
		Pattern p = Pattern.compile("[A-Z0-9A-Z]{6,10}");//. represents single character
		Matcher m = p.matcher("LITORAB");
		boolean b = m.matches();
		System.out.println(b);

		
	}
	
}
