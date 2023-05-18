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
		String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")); 
		StringBuilder fileName = new StringBuilder(inputFileType.concat(Constants.UNDERSCORE).concat(fileCreationTimestamp).concat(Constants.DOT).concat(Constants.STAT));
		File file = new File(destPath, fileName.toString());
		String TOTAL = recordCountMap.get(Constants.TOTAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TOTAL);
		String SKIP = recordCountMap.get(Constants.SKIP)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.SKIP); 
		String LOST = recordCountMap.get(Constants.LOST)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOST);
		String ZERO = recordCountMap.get(Constants.ZERO)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.ZERO); 
		String LOW_BAL = recordCountMap.get(Constants.LOW_BAL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.LOW_BAL); 
		String GOOD = recordCountMap.get(Constants.GOOD)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.GOOD);
		String TAG_SORTED_ALL = recordCountMap.get(Constants.TAG_SORTED_ALL)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TAG_SORTED_ALL);
		String TOTAL_MERGED_TAGS = recordCountMap.get(Constants.TOTAL_MERGED_TAGS)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.TOTAL_MERGED_TAGS);
		String ZIP_FILE_COUNT = recordCountMap.get(Constants.ZIP_FILE_COUNT)==null?Constants.DEFAULT_COUNT:recordCountMap.get(Constants.ZIP_FILE_COUNT);
		String nameofZipFile = zipfilename.getName();
		
		//Write content to Summary file
		try (FileWriter filewriterSummaryNew = new FileWriter(file, true)) {
			  log.info("File created: {}",file.getAbsoluteFile());
			  StringBuilder content = new StringBuilder();
			  filewriterSummaryNew.write("----------------------------------------------------------------------\r\n");
			  content.append("Statistics of PA full file "+nameofZipFile+"\r\nCreated on : "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" "+timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
			  filewriterSummaryNew.write(content.toString());
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("----------------------------------------------------------------------\r\n");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL + " Total number of records added to the file.");
			  filewriterSummaryNew.write("\r\n\r\n");
			  filewriterSummaryNew.write(TAG_SORTED_ALL + " Total tags fetched from T_TAG_STATUS_ALLSORTED table.");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(TOTAL_MERGED_TAGS + " Total tags Merged from Latest ITAG files.");
			  filewriterSummaryNew.write("\r\n\r\n");
			  filewriterSummaryNew.write(SKIP + " Number of devices skipped.");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(GOOD + " Tags were in GOOD balance status.");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(LOW_BAL + " Tags were in LOW balance status.");
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write(ZERO + " Tags were in ZERO balance status.");
			  //filewriterSummaryNew.write("\r\n");
			  //filewriterSummaryNew.write(LOST + " Tags were in LOST/STOLEN status.");
			  filewriterSummaryNew.write("\r\n\r\n");
			  filewriterSummaryNew.write("----------------------------------------------------------------------\r\n");
			  filewriterSummaryNew.write("Number of files zip file contain  : " + ZIP_FILE_COUNT);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("Zip File Name                     : " + nameofZipFile);
			  filewriterSummaryNew.write("\r\n");
			  filewriterSummaryNew.write("----------------------------------------------------------------------\r\n");
			  log.info("Content appended to file {}", file.getName());
			  log.info("Records counts are  TOTAL:{}, LOST:{}, ZERO:{}, LOW_BAL:{}, GOOD:{}, SKIP:{}, TAG_SORTED:{}, MERGED: {}",TOTAL, LOST, ZERO, LOW_BAL, GOOD, SKIP, TAG_SORTED_ALL, TOTAL_MERGED_TAGS);
			}catch (Exception e) {
				log.error("Exception occured while writing to STAT file.. {}", e.getMessage());
				e.printStackTrace();
		   }

	}
	
}