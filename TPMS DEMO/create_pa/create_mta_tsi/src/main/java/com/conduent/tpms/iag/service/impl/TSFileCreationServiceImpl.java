package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TagFileHeaderDto;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;
import com.conduent.tpms.iag.service.SummaryFileCreationService;


/**
 * 
 * TS Implementation for IAG file creation process
 * 
 * @author urvashic
 *
 */

@Service
@Primary
public class TSFileCreationServiceImpl extends IAGFileCreationSevice {
	private static final Logger log = LoggerFactory.getLogger(TSFileCreationServiceImpl.class);

	String tagFileDest;
	String statFileDest;
	
	private long validCount = 0;
	private long minCount = 0;
	private long stolenCount = 0;
	private long lostCount = 0;
	private long invalidCount = 0;
	private long inventoryCount = 0;
	private long replenishCount = 0;
	
	TagFileHeaderDto tagFileHeaderDto = new TagFileHeaderDto();
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	SummaryFileCreationService summaryFileCreationService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
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
		log.info("Initializing TS file properties..");
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
	}
	
	@Override
	public void createMTATSIFile(List<TAGDevice> tagDeviceList) throws IOException {
		try {
			log.info("Starting MTA_TSI file creation process..");
			tagFileDest = parentDest.concat("//").concat(Constants.MTA_TSI_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			
			log.info("Total records fethced from DB : {}",tagDeviceList.size());
			recordCountMap.put(Constants.TAG_SORTED_ALL, StringUtils.leftPad(String.valueOf(tagDeviceList.size()), Constants.SEVEN, Constants.DEFAULT_ZERO));
			
			StringBuilder fileContent = new StringBuilder();
			
			log.info("Processing device list.. ");
			if (!tagDeviceList.isEmpty()) {
				
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				fileName = buildFileSection(nameProperties, null);
				log.info("File Name: {} File Location: {}", fileName, tagFileDest);
				File file = new File(tagFileDest, fileName);
				FileWriter filewriter = new FileWriter(file);
				log.info("Created file : {}",file.getAbsoluteFile());
				
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				String header = buildFileSection(headerProperties, null);
				fileContent.append(header);
				//filewriter.write(header + "\n");
				log.info("Written header: {} to  file: {}",header, file.getAbsoluteFile());
				
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				
				for (TAGDevice tagDevice : tagDeviceList) {
					boolean isValidRecord = validateTagRecord(tagDevice);	
					log.info("Record {} is valid? : {}",tagDevice.getDeviceNo(), isValidRecord);
					if(isValidRecord) {
						sequenceCount++;
						String record = buildFileSection(detailRecordProperties, tagDevice);
						fileContent.append(record);
						//writeContentToFile(filewriter, record);
						updateRecordCounter(tagDevice);
						}else {
						skipCount++;
						log.info("Record not added to file: {}", tagDevice.toString());
					}
				}
				if(isTrailerPresentInFile()) 
				{
					List<MappingInfoDto> trailerProperties = getConfigurationMapping().getTrailerMappingInfoDto();
					String trailer = buildFileSection(trailerProperties, null);
					//writeContentToFile(filewriter, trailer);
					fileContent.append(trailer);
					log.info("Added trailer to the file..");
				}
					writeContentToFile(filewriter, fileContent.toString());
					filewriter.flush();
					filewriter.close();
					overwriteFileHeader(file, headerProperties);
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFileTS(recordCountMap, Constants.TS, statFileDest,fileName);
			}
			log.info(tagDeviceList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			validCount = 0;
			minCount = 0;
			stolenCount = 0;
			lostCount = 0;
			invalidCount = 0;
			inventoryCount = 0;
			replenishCount = 0;
			skipCount = 0;
			totalRecords = 0;
			sequenceCount =0;
			tagDeviceList.clear();
		}
	}
	
	@Override
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		String value = "";
		log.debug("Mapping the field: {}",fMapping.getFieldName());
		if(tagDevice != null) {
		log.debug("Incoming tag device {}",tagDevice.toString());
		}
		switch (fMapping.getFieldName()) {
		case Constants.F_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		case Constants.F_FILE_SERIAL:
			value = fMapping.getDefaultValue();
			break;
		case Constants.F_DOT:
			value = Constants.DOT;
			break;
		case Constants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_RECORD_TYPE_CODE:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_SERVICE_CENTER:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_AGENCY_ID:
			value = fMapping.getDefaultValue();
			break;	
		case Constants.H_PLAZA_ID:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_DOWNLOAD_TYPE:
			value = getFileGenType().equals(Constants.FILE_GEN_TYPE_FULL)?Constants.FILE_DWNL_COMPL:Constants.FILE_DWNL_INCR;
			break;
		case Constants.H_LAST_DOWNLOAD_TS:
			String lastFileDownloadTS = iagDao.getLastDwnldTS();
			value = lastFileDownloadTS;
			break;
		case Constants.H_CURRENT_TS:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;	
		case Constants.H_STOLEN_TAG_COUNT:
			log.info("Stolen count: {}",stolenCount);
			recordCountMap.put(Constants.STOLEN, doPadding(fMapping,String.valueOf(stolenCount)));
			value = doPadding(fMapping, Long.toHexString(stolenCount)); 
			break;	
		case Constants.H_LOST_TAG_COUNT:
			log.info("Lost count: {}",lostCount);
			recordCountMap.put(Constants.LOST, doPadding(fMapping,String.valueOf(lostCount)));
			value = doPadding(fMapping, Long.toHexString(lostCount));
			break;
		case Constants.H_INVALID_TAG_COUNT:
			log.info("Invalid count: {}",invalidCount);
			recordCountMap.put(Constants.INVALID, doPadding(fMapping,String.valueOf(invalidCount)));
			value = doPadding(fMapping, Long.toHexString(invalidCount));
			break;	
		case Constants.H_INVENTORY_DEVICE_COUNT:
			log.info("Inventory count: {}",inventoryCount);
			recordCountMap.put(Constants.INVENTORY, doPadding(fMapping,String.valueOf(inventoryCount)));
			value = doPadding(fMapping, Long.toHexString(inventoryCount));
			break; 
		case Constants.H_REPLENISH_TAG_COUNT:
			log.info("Negative balance count: {}",replenishCount);
			recordCountMap.put(Constants.REPLENISH, doPadding(fMapping,String.valueOf(replenishCount)));
			value = doPadding(fMapping, Long.toHexString(replenishCount));
			break;
		case Constants.H_MINUS_BAL_TAG_COUNT:
			log.info("Minimum balance count: {}",minCount);
			recordCountMap.put(Constants.MINIMUM, doPadding(fMapping,String.valueOf(minCount)));
			value = doPadding(fMapping, Long.toHexString(minCount));
			break;	
		case Constants.H_VALID_TAG_COUNT:
			log.info("Valid balance count: {}",validCount);
			recordCountMap.put(Constants.VALID, doPadding(fMapping,String.valueOf(validCount)));
			value = doPadding(fMapping, Long.toHexString(validCount));
			break;
		case Constants.H_RECORD_COUNT:
			log.info("Total record count: {}",totalRecords);
			recordCountMap.put(Constants.TOTAL, doPadding(fMapping,String.valueOf(totalRecords)));
			value = doPadding(fMapping, Long.toHexString(totalRecords));
			break;
		case Constants.D_RECORD_TYPE_START:
			value = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			break;	
		case Constants.D_RECORD_NUM:
			value = doPadding(fMapping, Long.toHexString(sequenceCount)); 
			break;
		case Constants.D_TAG_AGENCY_ID:
			String devicePrefix = tagDevice.getDeviceNo().substring(1,3); 
			value =  doPadding(fMapping,devicePrefix);
			break;
		case Constants.D_SERIAL_NO:
			value = doPadding(fMapping, Long.toHexString(tagDevice.getSerialNo()));
			break;
		case Constants.D_TAG_IAG_CLASS:
			value = doPadding(fMapping, Long.toHexString(tagDevice.getIagCodedClass()));
			break;
		case Constants.D_REV__TYPE:			
			value = fMapping.getDefaultValue();
			break;
		case Constants.D_CONTROL_INFORMATION:			
			value = doPadding(fMapping, tagDevice.getMtaCtrlStr()); 
			break;	
		case Constants.D_RECORD_TYPE_END:
			value = new String("\u0003".getBytes(StandardCharsets.US_ASCII));
			break;	
		case Constants.T_RECORD_TYPE:
			value = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			break;	
		}

		log.info("Mapping done for the field: {} as value: {}",fMapping.getFieldName(), value);
		return value;
		
	}	
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOST) {
			//stolenCount++; 
			lostCount++;
		}
		else if (tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID) {
			invalidCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_INVENTORY) {
			inventoryCount++;
		}
		else if (tagDeviceObj.getFinancialStatus() == Constants.FS_ZERO) {
			replenishCount++;
		}
		else if(tagDeviceObj.getFinancialStatus() == Constants.FS_LOW) {
			minCount++;
		}
		else if (tagDeviceObj.getItagTagStatus() == Constants.TS_VALID) {
			validCount++;
		}
		totalRecords++;
	}

	/**
	 * 
	 * @param TAGDeviceObj
	 * @return true/false
	 */
	private boolean validateTagRecord(TAGDevice tagDeviceObj) {
		boolean isValidRecord = false;
		// Validate device 
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
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
	}
	
}
