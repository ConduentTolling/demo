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
		String TAG_SORTED_ALL = recordCountMap.get(Constants.TAG_SORTED_ALL)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.TAG_SORTED_ALL);
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.TOTAL);
		String VALID = recordCountMap.get(Constants.VALID)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.VALID); 
		String INVENTORY = recordCountMap.get(Constants.INVENTORY)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.INVENTORY); 
		String INVALID = recordCountMap.get(Constants.INVALID)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.INVALID); 
		String LOST = recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.LOST);
		String STOLEN = recordCountMap.get(Constants.STOLEN)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.STOLEN);
		String REPLENISH = recordCountMap.get(Constants.REPLENISH)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.REPLENISH);
		String MINBAL = recordCountMap.get(Constants.MINBAL)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.MINBAL);
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_ZERO:recordCountMap.get(Constants.SKIP);		
		
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  filewriterSummaryNew.write("----------------------------------------------------------------\r\n");
			  content.append("Statistics of MTA_TSI file "+filename+"\r\nCreated on: "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("----------------------------------------------------------------\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Total number of records added to the file : " + TOTAL);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Total tags read from TAG_STATUS_ALLSORTED : " + TAG_SORTED_ALL);
			  filewriterSummaryNew.write("\r\n\r\n");
			  filewriterSummaryNew.write("Tags were in VALID status                 : " + VALID);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in INVENTORY status             : " + INVENTORY);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in INVALID status               : " + INVALID);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in LOST status                  : " + LOST);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in STOLEN status                : " + STOLEN);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in REPLENISH status             : " + REPLENISH);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in MINIMUM balance status       : " + MINBAL);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Number of Tags skipped                    : " + SKIP);
			  filewriterSummaryNew.write("\r\n\r\n");
			  filewriterSummaryNew.write("----------------------------------------------------------------\r\n");
			  log.info("Content appended to file {}", file.getName());
			  log.info("Records counts are TAG_SORTED_ALL:{}, TOTAL:{}, LOST:{}, STOLEN:{}, VALID:{}, INVENTORY:{}, INVALID:{}, REPLENISH:{}, MINBAL:{}, SKIP:{}",TAG_SORTED_ALL, TOTAL, LOST, STOLEN, VALID, INVENTORY, INVALID, REPLENISH, MINBAL, SKIP);

			} catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }		
		
		
	}
	
}