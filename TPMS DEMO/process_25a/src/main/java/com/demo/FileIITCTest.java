package com.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.conduent.tpms.process25a.utility.Convertor;


public class FileIITCTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		System.out.println("ACK 008016016_20200715210431.IITC                           2021051616322402\r\n".length());
		//System.out.println(regexValidation("HALL JR", "[a-zA-Z \\s]+$"));
		//System.out.println(regexValidation("ROBERT", "[a-zA-Z \\s]+$"));
	//	System.out.println(regexValidation("1907-DINEEN DR.", "[a-zA-Z. \\s-]+$"));
		//System.out.println(regexValidation("A", "[A-Za-z\\s]{1}"));
		
		String input = "üller";
//		String regex = "[a-zA-Z0-9. \\\\s \\' \\# \\& \\` \\-]+$";
	//	String regex = "([A-Z]{1}[a-zA-Z0-9. \\\\s \\' \\# \\& \\` \\- \\@]+$)" ;
	//	String regex = "[a-zA-Z \\\\s $&+,:;=?@#|'/\\<>.^*()%!`-]+$";   //final
		//String regex = "[a-zA-Z \\s]{0,1}";
		String regex = "^\\p{L}+$";

		Boolean result = regexValidation(input.trim(), regex);

		System.out.println(result);
	}
	
	public static Boolean regexValidation(String inputValue, String regex) {
		System.out.println(inputValue);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputValue.trim());
		return m.matches();
		}
/*
 * [a-zA-Z \\s]+$  removed for MI
 */
}
