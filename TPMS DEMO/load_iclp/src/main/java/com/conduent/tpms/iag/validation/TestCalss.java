package com.conduent.tpms.iag.validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class TestCalss {

	public void run() throws IOException {
	//	FileParserImpl parser = new GenericICLPFileParserImpl();
	//	parser.fileParseTemplate();
	}
	

    public static void main(String[] args) {

        String input = "34AA34";
        String regex = "[A-Z0-9A-Z]{6,10}";
        // String regex = "[1-9|*]|11";

        Boolean result = regexValidation(input, regex);
        System.out.println(result);
    }

 

    public static Boolean regexValidation(String inputValue, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputValue.trim());
        return m.matches();

}
}