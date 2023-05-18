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
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.constants.IITCConstants;
import com.conduent.tpms.iag.service.SummaryFileCreationService;

@Service
public class SummaryFileCreationServiceImpl extends SummaryFileCreationService{

	private static final Logger log = LoggerFactory.getLogger(SummaryFileCreationServiceImpl.class);

	@Autowired
	TimeZoneConv timeZoneConv;
	
	public void buildSummaryFile(Map<String,String> recordCountMap, String inputFileType, String destPath,String filename) {
		//Build summary file name
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(ICLPConstants.UNDERSCORE).concat(fileCreationTimestamp).concat(ICLPConstants.DOT).concat(ICLPConstants.STAT));
		File file = new File(destPath, fileName.toString());
		String TOTAL = recordCountMap.get(ICLPConstants.TOTAL)==null?ICLPConstants.DEFAULT_COUNT:recordCountMap.get(ICLPConstants.TOTAL);
		String SKIP = recordCountMap.get(ICLPConstants.SKIP)==null?ICLPConstants.DEFAULT_COUNT:recordCountMap.get(ICLPConstants.SKIP); 
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of ICLP file "+filename+" created on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Number of device skipped:"+SKIP);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Total number of records added to the file:"+TOTAL);
			  log.info("Content appended to file {}", file.getName());
			  
			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }

	}
	
	public void buildSummaryFileforIITC(Map<String,String> recordCountMap, String inputFileType, String destPath,String filename) {
		//Build summary file name
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(IITCConstants.UNDERSCORE).concat(fileCreationTimestamp).concat(IITCConstants.DOT).concat(IITCConstants.STAT));
		File file = new File(destPath, fileName.toString());
		String TOTAL = recordCountMap.get(IITCConstants.TOTAL)==null?IITCConstants.DEFAULT_COUNT:recordCountMap.get(IITCConstants.TOTAL);
		String SKIP = recordCountMap.get(IITCConstants.SKIP)==null?IITCConstants.DEFAULT_COUNT:recordCountMap.get(IITCConstants.SKIP); 
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of IITC file "+filename+" created on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Number of device skipped:"+SKIP);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Total number of records added to the file:"+TOTAL);
			  log.info("Content appended to file {}", file.getName());
			  
			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }

	}
	
}