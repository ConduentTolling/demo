package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.service.SummaryFileCreationService;

@Service
public class SummaryFileCreationServiceImpl extends SummaryFileCreationService{
	
	@Autowired
	TimeZoneConv timeZoneConv;

	private static final Logger log = LoggerFactory.getLogger(SummaryFileCreationServiceImpl.class);
	
	
	public void buildSummaryFile(Map<String,String> recordCountMap, String inputFileType, String destPath) {
		//Build summary file name
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(Constants.UNDERSCORE).concat(fileCreationTimestamp).concat(Constants.DOT).concat(Constants.STAT));
		File file = new File(destPath, fileName.toString());
		
		//Fetching values from record counter map
		String TAG_SORTED_ALL = recordCountMap.get(Constants.TAG_SORTED_ALL)==null?Constants.DEFAULT_COUNT:removeLeadingZeroes(recordCountMap.get(Constants.TAG_SORTED_ALL));
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TOTAL);
		String GOOD = recordCountMap.get(Constants.GOOD)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.GOOD); 
		String LOW_BAL = recordCountMap.get(Constants.LOW_BAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOW_BAL); 
		String ZERO = recordCountMap.get(Constants.INVALID)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.INVALID); 
		String TAGFROMDB = recordCountMap.get(Constants.TAG_FROM_DATABASE) ==null? Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TAG_FROM_DATABASE);		/*
		 * String LOST =
		 * recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.LOST); //String STOLEN =
		 * recordCountMap.get(Constants.STOLEN)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.STOLEN); String NYSBA_GOOD =
		 * recordCountMap.get(Constants.NYSBA_GOOD)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.NYSBA_GOOD); String NYSBA_LOW =
		 * recordCountMap.get(Constants.NYSBA_LOW)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.NYSBA_LOW); String NYSBA_ZERO =
		 * recordCountMap.get(Constants.NYSBA_ZERO)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.NYSBA_ZERO); String NYSBA_LOST =
		 * recordCountMap.get(Constants.NYSBA_LOST)==null?Constants.DEFAULT_COUNT:
		 * recordCountMap.get(Constants.NYSBA_LOST);
		 */
		
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_COUNT:removeLeadingZeroes(recordCountMap.get(Constants.SKIP)); 
		
		int recordtowrite= Integer.parseInt(TAGFROMDB)- Integer.parseInt(SKIP);
		
		int awayfilemerge = Integer.parseInt(TOTAL)- recordtowrite;
		
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of Tag status files created at "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))+" on "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+"\r\n");
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("---------------------------------------------------------------------------------------- \r\n");
			  filewriterSummaryNew.write("\r\n");
			 // filewriterSummaryNew.write(TAG_SORTED_ALL+" Total tags in the TAG_SORTED_ALL table and merged away files\r\n");
			 // filewriterSummaryNew.write("\r\n");
			 // filewriterSummaryNew.write(TAGFROMDB+" Tags were read from the database.\r\n");
			  //filewriterSummaryNew.write(GOOD+" Tags were in Good balance status.\r\n");
			 // filewriterSummaryNew.write(LOW_BAL+" Tags were in Low balance status.\r\n");
			 // filewriterSummaryNew.write(ZERO+" Tags were in Zero status.\r\n");
			  //filewriterSummaryNew.write(LOST+" NYSTA tags were in Lost status.\r\n");
			 // filewriterSummaryNew.write("\r\n");
			  //filewriterSummaryNew.write(NYSBA_GOOD+" NYSBA tags were in Good status.\r\n");
			  //filewriterSummaryNew.write(NYSBA_LOW+" NYSBA tags were in Low status.\r\n");
			  //filewriterSummaryNew.write(NYSBA_ZERO+" NYSBA tags were in Zero status.\r\n");
			  //filewriterSummaryNew.write(NYSBA_LOST+" NYSBA tags were in Lost status.\r\n");
			  //filewriterSummaryNew.write("\r\n");
			 // filewriterSummaryNew.write(SKIP+" tags did not fit into any of these statuses.\r\n");
			  //filewriterSummaryNew.write("\r\n");
			 // filewriterSummaryNew.write(TOTAL+" total tags.\r\n");
			  
			  filewriterSummaryNew.write("Total number of device read from database: " + TAGFROMDB +"\r\n");
			  filewriterSummaryNew.write("Total number of device qualify for writing to file: " + recordtowrite+"\r\n");
			  filewriterSummaryNew.write("Total number of device not-qualify for writing to file: " +SKIP+"\r\n");
			  filewriterSummaryNew.write("Tags in Good balance status: " +GOOD+ "\r\n");
			  filewriterSummaryNew.write("Tags in Low balance status: " +LOW_BAL+"\r\n");
			  filewriterSummaryNew.write("Tags in Zero status: " +ZERO+"\r\n");
			  filewriterSummaryNew.write("Total number of device from away merge file: "+ awayfilemerge +"\r\n");
			  filewriterSummaryNew.write("Total number of device written in file: " +TOTAL+"\r\n");
			  
			  
			  
			  log.info("Content appended to file {}", file.getName());
			 // log.info("Records counts are TAG_SORTED_ALL:{}, TOTAL:{}, LOST:{}, ZERO:{}, LOW_BAL:{}, GOOD:{}, SKIP:{}",TAG_SORTED_ALL, TOTAL, LOST, ZERO, LOW_BAL, GOOD, SKIP);
			  //log.info("Records counts are NYSBA_GOOD:{}, NYSBA_LOW:{}, NYSBA_ZERO:{}, NYSBA_LOST:{}",NYSBA_GOOD, NYSBA_LOW, NYSBA_ZERO, NYSBA_LOST);
			  
			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }

	}
	
	
	public static String removeLeadingZeroes(String str) {
	      String strPattern = "^0+(?!$)";
	      str = str.replaceAll(strPattern, "");
	      return str;
	   }
}