package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private long minbalCount = 0;
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
	public boolean createMTATSIFile(List<TAGDevice> tagDeviceList, boolean isRFK) throws IOException {
		try {
			log.info("Starting MTA_TSI file creation process..");
			tagFileDest = parentDest.concat("//").concat(Constants.MTA_TSI_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			
			log.info("Total records fethced from DB : {}",tagDeviceList.size());
			//recordCountMap.put(Constants.TAG_SORTED_ALL, StringUtils.leftPad(String.valueOf(tagDeviceList.size()), Constants.TEN, Constants.DEFAULT_ZERO));
			recordCountMap.put(Constants.TAG_SORTED_ALL, String.valueOf(tagDeviceList.size()));
			
			if (isRFK) {
				recordCountMap.put(Constants.isRFK, "true");
			} else {
				recordCountMap.put(Constants.isRFK, "false");
			}
			
			StringBuilder fileContent = new StringBuilder();
			
			log.info("Processing device list.. ");
			if (!tagDeviceList.isEmpty()) {
				
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				fileName = buildFileSection(nameProperties, null);
				log.info("File Name: {} File Location: {}", fileName, tagFileDest);
				File file = new File(tagFileDest, fileName);
				FileWriter filewriter = new FileWriter(file);
				log.info("Created file : {}",file.getAbsoluteFile());
				
				// Build header
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				//String header = buildFileSection(headerProperties, null);
				String header = buildFileSectionCSV(headerProperties, null);
				
				// If TS Full File not present do not create TSI File
				if (recordCountMap.get(Constants.PREV_FILE_NAME).isEmpty()) {
					log.error("TS Full File Not Found. Stopping execution.");
					filewriter.flush();
					filewriter.close();
					file.delete();
					return false;
				}
				
				//fileContent.append(header);
				filewriter.write(header + "\n");
				log.info("Written header: {} to  file: {}",header, file.getAbsoluteFile());
				
				// Build details
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				
				for (TAGDevice tagDevice : tagDeviceList) {
					boolean isValidRecord = validateTagRecord(tagDevice);	
					log.info("Record {} is valid? : {}",tagDevice.getDeviceNo(), isValidRecord);
					if(isValidRecord) {
						sequenceCount++;
						//String record = buildFileSection(detailRecordProperties, tagDevice);
						String record = buildFileSectionCSV(detailRecordProperties, tagDevice);
						//fileContent.append(record);
						writeContentToFile(filewriter, record);
						updateRecordCounter(tagDevice);
						}else {
						skipCount++;
						log.info("Record not added to file: {}", tagDevice.toString());
					}
				}
				
				if (isTrailerPresentInFile()) {
					List<MappingInfoDto> trailerProperties = getConfigurationMapping().getTrailerMappingInfoDto();
					//String trailer = buildFileSection(trailerProperties, null);
					String trailer = buildFileSectionCSV(trailerProperties, null);
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
				return true;
			}
			log.info(tagDeviceList.toString());
			log.error("No Data Found in T_TAG_STATUS_ALLSORTED table.");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			validCount = 0;
			minbalCount = 0;
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
		case Constants.F_FROM_AGENCY_ID:
			value = doPadding(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.F_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			if (recordCountMap.get(Constants.isRFK).equals("true")) {
				value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat())).concat(Constants.R);
			} else {
				value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
			}
			break;
		case Constants.F_DOT:
			//value = Constants.DOT;
			value = doPadding(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.F_FILE_EXTENSION:
			value = doPadding(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.H_REC_TYPE:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.H_SERVICE_CENTER:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.H_HUB_ID:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.H_AGENCY_ID:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
//		case Constants.H_PLAZA_ID:
//			value = fMapping.getDefaultValue();
//			break;
		case Constants.H_DWNLD_TYPE:
			value = getFileGenType().equals(Constants.FILE_GEN_TYPE_FULL) ? Constants.FILE_DWNL_COMPL : Constants.FILE_DWNL_INCR;
			break;
		case Constants.H_LAST_TS:
			if (recordCountMap.get(Constants.isRFK).equals("true")) {
				value = iagDao.getLastDwnldTS(fMapping.getFormat(), true);
				if (!value.isEmpty()) {
					recordCountMap.put(Constants.PREV_FILE_NAME, Constants.HOME_AGENCY_ID.concat(value).concat(Constants.R).concat(Constants.DOT).concat(Constants.TS));
				}
			} else {
				value = iagDao.getLastDwnldTS(fMapping.getFormat(), false);
				if (!value.isEmpty()) {
					recordCountMap.put(Constants.PREV_FILE_NAME, Constants.HOME_AGENCY_ID.concat(value).concat(Constants.DOT).concat(Constants.TS));
				}
			}
			if (value.isEmpty()) {
				recordCountMap.put(Constants.PREV_FILE_NAME, "");
			}
			break;
		case Constants.H_CURRENT_TS:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
			break;	
		case Constants.H_STOLEN:
			log.info("Stolen count: {}",stolenCount);
			value = doPaddingCSV(fMapping, String.valueOf(stolenCount));
			break;	
		case Constants.H_LOST:
			log.info("Lost count: {}",lostCount);
			value = doPaddingCSV(fMapping, String.valueOf(lostCount));
			break;
		case Constants.H_INVALID:
			log.info("Invalid count: {}",invalidCount);
			value = doPaddingCSV(fMapping, String.valueOf(invalidCount));
			break;	
		case Constants.H_INVENTORY:
			log.info("Inventory count: {}",inventoryCount);
			value = doPaddingCSV(fMapping, String.valueOf(inventoryCount));
			break; 
		case Constants.H_REPLENISH:
			log.info("Negative balance count: {}",replenishCount);
			value = doPaddingCSV(fMapping, String.valueOf(replenishCount));
			break;
		case Constants.H_MINBAL:
			log.info("Min balance count: {}",minbalCount);
			value = doPaddingCSV(fMapping, String.valueOf(minbalCount));
			break;	
		case Constants.H_VALID:
			log.info("Valid balance count: {}",validCount);
			value = doPaddingCSV(fMapping, String.valueOf(validCount));
			break;
		case Constants.H_TOTAL:
			log.info("Total record count: {}",totalRecords);
			value = doPaddingCSV(fMapping, String.valueOf(totalRecords));
			break;
		case Constants.H_PREV_FILE_NAME:
			value = recordCountMap.get(Constants.PREV_FILE_NAME);
			break;	
		case Constants.D_REC_TYPE:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;	
		case Constants.D_REC_NUM:
			value = doPaddingCSV(fMapping, String.valueOf(sequenceCount));
			break;
		case Constants.D_TAG_AGENCY_ID:
			value = doPaddingCSV(fMapping, tagDevice.getDevicePrefix());
			break;
		case Constants.D_TAG_SERIAL_NUM:
			value = doPaddingCSV(fMapping, String.valueOf(tagDevice.getSerialNo()));
			break;
		case Constants.D_TAG_CLASS:
			//value = doPadding(fMapping, String.valueOf(tagDevice.getIagCodedClass()));
			value = doPaddingCSV(fMapping, tagDevice.getTagClass());
			break;
		case Constants.D_REV_TYPE:			
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.D_TAG_TYPE:
			value = doPaddingCSV(fMapping, tagDevice.getTagType());
			break;
		case Constants.D_CI1_TAG_STATUS:			
			//value = doPadding(fMapping, tagDevice.getMtaCtrlStr()); 
			if (recordCountMap.get(Constants.isRFK).equals("true")) {
				value = doPaddingCSV(fMapping, tagDevice.getRioCtrlStr().substring(0,1));
			} else {
				value = doPaddingCSV(fMapping, tagDevice.getMtaCtrlStr().substring(0,1));
			}
			break;
		case Constants.D_CI2_VES:			
			value = doPaddingCSV(fMapping, tagDevice.getMtaCtrlStr().substring(1,2));
			break;
		case Constants.D_CI3_FUTURE_USE:			
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.D_CI4_ACCOUNT_DEVICE_STATUS:			
			value = doPaddingCSV(fMapping, tagDevice.getMtaCtrlStr().substring(3,4));
			break;
		case Constants.D_CI5_DISCOUNT_PLAN_FLAG_ORT:			
			value = doPaddingCSV(fMapping, tagDevice.getMtaCtrlStr().substring(4,5));
			break;
		case Constants.D_CI6_DISCOUNT_PLAN_FLAG_CBD:			
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.D_TAG_ACCOUNT_NO:
			value = doPaddingCSV(fMapping, tagDevice.getAccountNo());
			break;
		case Constants.D_TAG_AC_TYPE_IND:
			value = doPaddingCSV(fMapping, tagDevice.getAccountTypeCd());
			break;
		case Constants.D_TAG_HOME_AGENCY:
			value = doPaddingCSV(fMapping, masterDataCache.getAgencyById(tagDevice.getTagOwningAgency()).getFilePrefix());
			break;
		case Constants.D_TAG_PROTOCOL:
			value = doPaddingCSV(fMapping, tagDevice.getTagProtocol());
			break;
		case Constants.D_TAG_MOUNT:
			value = doPaddingCSV(fMapping, tagDevice.getTagMount());
			break;
		case Constants.D_TAG_ACCT_INFO:
			//value = doPaddingCSV(fMapping, buildHexadecimalForItagInfo(tagDevice.getItagInfo()));
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.D_FILLER_FIELD1:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;
		case Constants.D_FILLER_FIELD2:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;	
		case Constants.T_REC_TYPE:
			value = doPaddingCSV(fMapping, fMapping.getDefaultValue());
			break;	
		}

		log.info("Mapping done for the field: {} as value: {}",fMapping.getFieldName(), value);
		return value;
		
	}	
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getItagTagStatus() == Constants.TS_GOOD) {
			validCount++;
		}
		else if(tagDeviceObj.getItagTagStatus() == Constants.TS_LOW) {
			minbalCount++;
		}
		else if (tagDeviceObj.getItagTagStatus() == Constants.TS_ZERO) {
			replenishCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_INVENTORY) {
			inventoryCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_LOST) { 
			lostCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_STOLEN) {
			stolenCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_RETURNED 
				|| tagDeviceObj.getDeviceStatus() == Constants.DS_DAMAGED) {
			invalidCount++;
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
		// recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT, skipCount));
		recordCountMap.put(Constants.SKIP, String.valueOf(skipCount));
		recordCountMap.put(Constants.TOTAL, String.valueOf((totalRecords - skipCount)));
		recordCountMap.put(Constants.VALID, String.valueOf(validCount));
		recordCountMap.put(Constants.STOLEN, String.valueOf(stolenCount));
		recordCountMap.put(Constants.LOST, String.valueOf(lostCount));
		recordCountMap.put(Constants.INVALID, String.valueOf(invalidCount));
		recordCountMap.put(Constants.INVENTORY, String.valueOf(inventoryCount));
		recordCountMap.put(Constants.REPLENISH, String.valueOf(replenishCount));
		recordCountMap.put(Constants.MINBAL, String.valueOf(minbalCount));
	}
	
}
