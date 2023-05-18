package com.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class detailsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*
		String detailITAGLine ="016000001014000000";
		System.out.println("details length :" +detailITAGLine.length());
		
		String tagAgencyId=detailITAGLine.substring(0,3);
		System.out.println("Tag agency id :" +tagAgencyId);
		
		String tagSerialNumber = detailITAGLine.substring(3,11);
		System.out.println("tag serial number "+tagSerialNumber);
		
		String tagStatus= detailITAGLine.substring(11, 12);
		System.out.println("tag status "+tagStatus);
		*/
		
		/*String filename="016_20200713210431.ITAG";
		System.out.println("filename length "+filename.length());
		String tagfromAgencyId=filename.substring(0,3);
		System.out.println("Tag tagfromAgencyId id :" +tagfromAgencyId);
		
		String underscore=filename.substring(3,4);
		System.out.println("underscore :" +underscore);
		
		String datetimeFile=filename.substring(4,18);
		System.out.println("datetimeFile :" +datetimeFile);
		
		String extension=filename.substring(18,23);
		System.out.println("extension :" +extension);*/
		
		
			System.out.println(getTodayTimestamp());
		
		
		
	}

	public static String getTodayTimestamp() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        String formatDateTime = LocalDateTime.now().format(formatter);
	        return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");
	        
	}
	
}
