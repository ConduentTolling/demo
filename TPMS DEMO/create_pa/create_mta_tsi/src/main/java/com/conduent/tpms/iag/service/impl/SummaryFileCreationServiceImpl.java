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

	private static final Logger log = LoggerFactory.getLogger(SummaryFileCreationServiceImpl.class);
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Override
	public void buildSummaryFileTS(Map<String,String> recordCountMap, String inputFileType, String destPath,String filename) {
		
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(Constants.UNDERSCORE).concat(fileCreationTimestamp).concat(Constants.DOT).concat(Constants.STAT));
		File file = new File(destPath, fileName.toString());
		
		//Fetching values from record counter map
		String TAG_SORTED_ALL = recordCountMap.get(Constants.TAG_SORTED_ALL)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.TAG_SORTED_ALL);
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.TOTAL);
		String VALID = recordCountMap.get(Constants.VALID)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.VALID); 
		String INVENTORY = recordCountMap.get(Constants.INVENTORY)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.INVENTORY); 
		String INVALID = recordCountMap.get(Constants.INVALID)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.INVALID); 
		String LOST = recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.LOST);
		String REPLENISH = recordCountMap.get(Constants.REPLENISH)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.REPLENISH);
		String MINIMUM = recordCountMap.get(Constants.MINIMUM)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.MINIMUM);
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_COUNT_TS:recordCountMap.get(Constants.SKIP);		
		
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of MTA_TSI file "+filename+" created on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("---------------------------------------------------------------------------------------- \r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TAG_SORTED_ALL+" Total tags in the TAG_SORTED_ALL\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL+" tags were read from the database.\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(VALID+" tags were in VALID status.\r\n");
			  filewriterSummaryNew.write(INVENTORY+" tags were in INVENTORY status.\r\n");
			  filewriterSummaryNew.write(INVALID+" tags were in INVALID status.\r\n");
			  filewriterSummaryNew.write(LOST+" tags were in Lost status.\r\n");
			  filewriterSummaryNew.write(REPLENISH+" tags were in Negative balance status.\r\n");
			  filewriterSummaryNew.write(MINIMUM+" tags were in Minimum balance status.\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(SKIP+" tags did not fit into any of these statuses.\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL+" total tags added.\r\n");
			  log.info("Content appended to file {}", file.getName());
			  log.info("Records counts are TAG_SORTED_ALL:{}, TOTAL:{}, LOST:{}, VALID:{}, INVENTORY:{}, INVALID:{}, REPLENISH:{}, MINIMUM:{}, SKIP:{}",TAG_SORTED_ALL, TOTAL, LOST, VALID, INVENTORY, INVALID, REPLENISH, MINIMUM, SKIP);

			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }		
		
		
	}
	
}