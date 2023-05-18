package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;
import com.conduent.tpms.iag.utility.TextFileReader;

@Service
@Component
public class ITGUFileCreationServiceImpl extends IAGFileCreationSevice {
	
	private static final Logger log = LoggerFactory.getLogger(ITGUFileCreationServiceImpl.class);
	
	String tagFileDest;
	String statFileDest;
	
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	private TextFileReader textFileReader;
	
	String deviceno;

	List<String> listofdeviceno = new ArrayList<String>();
	
	List<String> taginfo = new ArrayList<String>();
	
	private int devicestatus;
	
	File file;

	/**
	 * 
	 * @return File
	 */
	public File getFile() {
		return file;
	}
	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public void initialize() {
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
	}
	
	//@Override
	public void createITGUFile(List<TAGDevice> info) throws IOException {
		try {
			tagFileDest = parentDest.concat("//").concat(Constants.ITGU_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			recordCountMap.put(Constants.TAG_SORTED_ALL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, info.size()));
			if (!info.isEmpty()) {
				// Creating file 
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				fileName = buildFileSection(nameProperties, null);
				File file = new File(tagFileDest, fileName);
				FileWriter filewriter = new FileWriter(file);
				
				//Building TAG file header
				//After writing file
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				String header = buildFileSection(headerProperties, null);

				log.info("Writing header to ITGU file: {}",header);
				filewriter.write(header + "\n");

				//Build details
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				for (TAGDevice tagDevice : info) {
				boolean isValidRecord = validateTagRecord(tagDevice);	
				if(isValidRecord) {
					String record = buildFileSection(detailRecordProperties,tagDevice);	
					writeContentToFile(filewriter, record);
					updateRecordCounter(tagDevice);
					log.info("Records added to file is...{}",totalRecords);
					}
				else {
					skipCount++;
					log.info("Record not added to file: {}", tagDevice.toString());
				}
				
			}
					headerinfo.setRecordCount(String.valueOf(totalRecords));
					log.info("Total Record added ..{}",headerinfo.getRecordCount());
					filewriter.flush();
					filewriter.close();
				    //Overwriting header of TAG file
					overwriteFileHeader(file, headerProperties);
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFile(recordCountMap,Constants.ITGU, statFileDest,fileName);
			}
		}finally {
			skipCount = 0;
			totalRecords = 0;
			tsGood = 0;
			tsLow = 0;
			tsInvalid = 0;
			tsLost = 0;
			info.clear();
			tagDeviceList.clear();
			
			}
	}
	
	private void writeContentToFile(FileWriter filewriter, String record ) {
		
		try {
			filewriter.write(record + "\r");
			//error recovery
		} catch (IOException e) {
			log.error("Exception occurred while writing record {}",record);
			e.printStackTrace();
		}
		log.info("Record added to file: {}", record);
		
	}
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getItagTagStatus() == Constants.TS_GOOD) {
			tsGood++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOW) {
			tsLow++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID) {
			tsInvalid++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOST) {
			tsLost++;
		}
		
		totalRecords++;
	}

	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
	@Override
	public boolean overwriteFileHeader(File file, List<MappingInfoDto> headerProperties) {
		boolean headerUpdatedInFile = false;
				
		//Building new header with latest record count in file
		String newLine = buildFileSection(headerProperties, null);
		
		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				oldLine = sc.nextLine();
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine() + "\n");
			}

			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			//String newLine = header.toString();
			log.info("Replacing ITGU file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newLine);
			fileContents = fileContents.replaceAll(oldLine, newLine);
			FileWriter writer = new FileWriter(filePath);
			log.debug("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
			headerUpdatedInFile = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headerUpdatedInFile;
	}
	
	/**
	 * 
	 * @param TAGDeviceObj
	 * @return true/false
	 */
	
	private boolean validateTagRecord(TAGDevice tagDeviceObj) {
		boolean isValidRecord = false;
		// Validate device prefix
		
		if (masterDataCache.validateHomeDevice(tagDeviceObj.getDevicePrefix())) {
			if (validationUtils.onlyDigits(tagDeviceObj.getDeviceNo())) {
				if (validationUtils.validateTagStatus(tagDeviceObj.getItagTagStatus())) {
						isValidRecord = true;
				}
			}
		}
		return isValidRecord;
	}
	

 	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(Constants.TOTAL, headerinfo.getRecordCount()); 
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
	}
}
