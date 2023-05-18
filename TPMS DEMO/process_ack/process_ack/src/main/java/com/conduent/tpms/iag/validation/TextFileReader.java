package com.conduent.tpms.iag.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.springframework.stereotype.Component;



@Component
public class TextFileReader {

	private BufferedReader reader;
	String prviousLine = null;
	String currentLine =  null;
	
	
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	public void openFile(File file) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public String getlineBreak(File file2) throws IOException {
		FileReader fr = new FileReader(file2);
        String line1 = "";
        int c;
        while ((c = fr.read()) >= 0) {
            if(c == '\r' || c =='\n') {
                line1 += (char)c;
            }else {
                line1 = "empty";
            }   
        }
        fr.close();
        if(line1.contains(new String(Character.toString((char) 10)))){
        	line1="";
        	return line1+=(char)'\r';
        }else {
		return line1;
   
        }
}

	
	public void closeFile() throws IOException {
		if(reader != null ) {
			reader.close();
		}
		
	}
	
}
