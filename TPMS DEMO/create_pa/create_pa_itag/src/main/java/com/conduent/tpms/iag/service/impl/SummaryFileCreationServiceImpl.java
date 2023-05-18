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
	
	public void buildSummaryFile(Map<String,String> recordCountMap, String inputFileType, String destPath,String filename,File zipfilename,Long zipfilecount) {
		//Build summary file name
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(Constants.UNDERSCORE).concat(fileCreationTimestamp).concat(Constants.DOT).concat(Constants.STAT));
		File file = new File(destPath, fileName.toString());
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TOTAL);
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.SKIP); 
		String LOST = recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOST);
		String INVALID = recordCountMap.get(Constants.INVALID)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.INVALID); 
		String LOW_BAL = recordCountMap.get(Constants.LOW_BAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOW_BAL); 
		String GOOD = recordCountMap.get(Constants.GOOD)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.GOOD); 
		String nameofZipFile = zipfilename.getName();
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  content.append("Statistics of PA file "+nameofZipFile+" created on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Number of device skipped                      : "+SKIP);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Total number of records added to the file     : "+TOTAL);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in valid balance status             : "+GOOD);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in low balance balance status       : "+LOW_BAL);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in invalid status                   : "+INVALID);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Tags were in lost/stolen status               : "+LOST);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Number of files zip file contain              : "+zipfilecount);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Zip Filename                                  : "+nameofZipFile);
			  filewriterSummaryNew.write("\r\n");
			  log.info("Content appended to file {}", file.getName());
			  log.info("Records counts are  TOTAL:{}, LOST:{}, INVALID:{}, LOW_BAL:{}, GOOD:{}, SKIP:{}",TOTAL, LOST, INVALID, LOW_BAL, GOOD, SKIP);
			}catch (Exception e) {
				log.info("Exception occured while writing to STAT file.. ");
				e.printStackTrace();
		   }

	}
	
}