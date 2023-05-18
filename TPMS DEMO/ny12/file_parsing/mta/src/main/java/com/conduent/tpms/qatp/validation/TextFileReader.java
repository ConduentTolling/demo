package com.conduent.tpms.qatp.validation;

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

	private File file;
	private BufferedReader reader;
	private Scanner scanner;
	String prviousLine = null;
	String currentLine =  null;
	
	
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
//	public String readLine(String startChar) {
//		String paragraph = scanner.next();
//		return paragraph != null ? paragraph.replace(startChar, "") : null;
//	}
	
	public void openFile(File file) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public String getlineBreak(File file2) throws IOException {
		FileReader fr = new FileReader(file2);
        String line1 = "";
        String line2 = "";
        int c;
        while ((c = fr.read()) >= 0) {
            if(c == '\r' || c =='\n') {
                line1 += (char)c;
            }else {
                line1 = "empty";
            }
        }
        fr.close();
        if(line1.contains(new String(Character.toString((char) 13))) || line1.contains(new String(Character.toString((char) 10)))){
        	line1="";
        	return line1+=(char)'\r';
        }else {
		return line1;
   
        }
}
		

//	public void openFile(File file,String endChar) throws FileNotFoundException{
//		scanner = new java.util.Scanner(file);
//		scanner.useDelimiter(endChar);
//	}
	
	public void closeFile() throws IOException {
		if(reader != null ) {
			reader.close();
			file = null;
		}
		
//		if(scanner != null) {
//			scanner.close();
//			file = null;
//		}
	}

	public long getRecordCount(File file2) {
		int count=0;
		int count1 = 0;
		try {
            
             String STX = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			Scanner scanner = new java.util.Scanner(file2);
			while(scanner.hasNext())
			{
				 String paragraph = scanner.next();
				if(paragraph.contains(STX)) {
					count++;
				}
				
			}
			scanner.close();
			count1 = getRecordCountForETX(file2);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count1>count) {
			return count1;
		}
		else if(count1<count) {
			return count;
		}
		return count;
	}
	
	public int getRecordCountForETX(File file2) {
		int count=0;
		try {
            
             String ETX = new String("\u0003".getBytes(StandardCharsets.US_ASCII));
			Scanner scanner = new java.util.Scanner(file2);
			while(scanner.hasNext())
			{
				 String paragraph = scanner.next();
				if(paragraph.contains(ETX)) {
					count++;
				}
				
			}
			scanner.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	
		public void skipRecords(int recordCount) {
			for(int i=0;i<recordCount; i++) {
			readLine();
			}
			
	}
	
}
