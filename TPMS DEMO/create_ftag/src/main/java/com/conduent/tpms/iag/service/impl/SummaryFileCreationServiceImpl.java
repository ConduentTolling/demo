package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileWriter;
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
	
	
	public void buildSummaryFile(Map<String,String> recordCountMap, String inputFileType, String destPath,String fileNAme) {
		//Build summary file name
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		StringBuilder fileName = new StringBuilder(inputFileType.concat(Constants.UNDERSCORE).concat(fileCreationTimestamp).concat(Constants.DOT).concat(Constants.STAT));
		File file = new File(destPath, fileName.toString());
		
		//Fetching values from record counter map
		String TAG_SORTED_ALL = recordCountMap.get(Constants.TAG_SORTED_ALL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TAG_SORTED_ALL);
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TOTAL);
		String LOST = recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOST);
		String INVALID = recordCountMap.get(Constants.INVALID)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.INVALID); 
		String LOW_BAL = recordCountMap.get(Constants.LOW_BAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOW_BAL); 
		String GOOD = recordCountMap.get(Constants.GOOD)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.GOOD); 
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.SKIP); 
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of FTag file "+fileNAme+" created on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("---------------------------------------------------------------------------------------- \r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TAG_SORTED_ALL+" Total tags in the TAG_SORTED_ALL\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL+" FTAG tags were read from the database.\r\n");
			  filewriterSummaryNew.write(LOST+" tags were in lost status.\r\n");
			  filewriterSummaryNew.write(INVALID+" tags were in invalid status.\r\n");
			  filewriterSummaryNew.write(LOW_BAL+" tags were in low balance status.\r\n");
			  filewriterSummaryNew.write(GOOD+" tags were in good balance status.\r\n");
			  filewriterSummaryNew.write(SKIP+" tags did not fit into any of these statuses.\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL+" total FTAG tags.\r\n");
			  log.info("Content appended to file {}", file.getName());
			  log.info("Records counts are TAG_SORTED_ALL:{}, TOTAL:{}, LOST:{}, INVALID:{}, LOW_BAL:{}, GOOD:{}, SKIP:{}",TAG_SORTED_ALL, TOTAL, LOST, INVALID, LOW_BAL, GOOD, SKIP);
			  
			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }

	}
	
}