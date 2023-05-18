package com.demo;

import org.apache.commons.io.FilenameUtils;

public class testdemo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String headerITAGLine ="ITAG"
				+ "016"
				+ "20200713"
				+ "210431"
				+ "04035138"
				+ "02195151"
				+ "00110689"
				+ "00179457"
				+ "01549841";
		System.out.println("header length "+headerITAGLine.length());
		String fileType =headerITAGLine.substring(0, 4);
		System.out.println("fileType :" +fileType);
		String fromAgencyId= headerITAGLine.substring(4, 7);
		System.out.println("fromAgencyId :" +fromAgencyId);
		String fileDate = headerITAGLine.substring(7,15);
		System.out.println("fileDate :" +fileDate);
		String fileTime =headerITAGLine.substring(15, 21);
		System.out.println("fileTime :" +fileTime);
		String recordCount= headerITAGLine.substring(21, 29);
		System.out.println("recordCount :" +recordCount);
		String countStat1=headerITAGLine.substring(29, 37);
		System.out.println("countstat1 :" +countStat1);
		String countStat2=headerITAGLine.substring(37, 45);
		System.out.println("countstat2 :" +countStat2);
		String countStat3=headerITAGLine.substring(45, 53);
		System.out.println("countstat3 :" +countStat3);
		String countStat4=headerITAGLine.substring(53, 61);
		System.out.println("countstat4 :" +countStat4);
		
		System.out.println("File name"+FilenameUtils.getBaseName("itg.opi"));
	}

}
