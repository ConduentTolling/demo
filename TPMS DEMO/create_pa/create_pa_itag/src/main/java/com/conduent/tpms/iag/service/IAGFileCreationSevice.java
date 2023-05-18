package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.FileNameDto;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.impl.PAFileCreationServiceImpl;
import com.conduent.tpms.iag.utility.FileUtil;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;

/**
 * 
 * Interface for IAG file creation process
 * 
 * @author taniyan
 *
 */
public class IAGFileCreationSevice {

	private static final Logger log = LoggerFactory.getLogger(IAGFileCreationSevice.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	protected SummaryFileCreationService summaryFileCreationService;
	
	protected FileHeaderDto headerinfo = new FileHeaderDto();

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;

	@Autowired
	public PAFileCreationServiceImpl PaFilcereationserviceImpl;
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	private FileNameDto fileNameVO;
	
	private FileUtil fileUtil = new FileUtil();
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	
	protected List<TAGDevice> tagDeviceList = new ArrayList<>();
	private List<IncrTagStatusRecord> deviceNoList = new ArrayList<>();
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest;
	
	//Record Counters
	protected long totalRecords = 0;
	protected long tsGood = 0;
	protected long tsLow = 0;
	protected long tsInvalid = 0;
	protected long tsLost = 0;
	
	protected long goodCountNysba = 0;
	protected long lowCountNysba = 0;
	protected long zeroCountNysba = 0;
	protected long lostCountNysba = 0;
	protected long skipCount = 0;
	protected long zipFileCount = 0;
	
	public FileCreationParameters getConfigurationMapping() {
		return configurationMapping;
	}
	public void setConfigurationMapping(FileCreationParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}

	public FileCreationParameters getFileCreationParameter() {
		return fileCreationParameter;
	}

	public void setFileCreationParameter(FileCreationParameters fileCreationParameter) {
		this.fileCreationParameter = fileCreationParameter;
	}
	
	public boolean isHederPresentInFile() {
		return isHederPresentInFile;
	}
	public void setIsHederPresentInFile(boolean isHederPresentInFile) {
		this.isHederPresentInFile = isHederPresentInFile;
	}
	public boolean isDetailsPresentInFile() {
		return isDetailsPresentInFile;
	}
	public void setIsDetailsPresentInFile(boolean isDetailsPresentInFile) {
		this.isDetailsPresentInFile = isDetailsPresentInFile;
	}
	public boolean isTrailerPresentInFile() {
		return isTrailerPresentInFile;
	}
	public void setIsTrailerPresentInFile(boolean isTrailerPresentInFile) {
		this.isTrailerPresentInFile = isTrailerPresentInFile;
	}
	/*
	 * To initialize basic MTAG file properties
	 */
	public void initialize() {
	}
	
	public String fileCreationTemplate(int agencyId, String fileType, String genType) {
		try {
			validateFileEntities(agencyId, fileType, genType);
			initialize();
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			//collecting device no from T_INCR_TAG_STATUS_ALLSORTED
			deviceNoList = iagDao.getDeviceNoFromTInrTagStatusAllSorted(genType);
			
			//Fetch list of tag records
			//tagDeviceList = iagDao.getDeviceInfofromTTagStatusAllSorted(agencyId, genType);
			tagDeviceList = iagDao.getDevieListFromTTagAllSorted1(agencyId, genType,deviceNoList);
			boolean isFileCreated = processFileCreation(tagDeviceList);
			if (!isFileCreated) {
				throw new Exception("File Creation Failed");
			}
		} catch (Exception e) {
			log.info("Exception occured. {}", e.getMessage());
			e.printStackTrace();
			return "Job execution failed: " + e.getMessage();
		} finally {
			deviceNoList.clear();
			tagDeviceList.clear();
		}
		return "Job executed successfully";
	}

	private void loadOutputConfigurationMapping(int agencyId, String fileType, String genType) {
		getFileCreationParameter().setFileType(fileType);
		getFileCreationParameter().setAgencyId(agencyId);
		getFileCreationParameter().setGenType(genType);
		setConfigurationMapping(iagDao.getMappingConfigurationDetails(getFileCreationParameter()));
		
	}

	/**
	 * Start process of file creation based on file format.
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	public boolean processFileCreation(List<TAGDevice> info)throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file crreation..", fileType);
			boolean isFileCreated = false;
			switch (fileType) {
			
			case Constants.ATGU:
				isFileCreated = PaFilcereationserviceImpl.createPAFile(info);
				break;
				
			default:
				throw new InvalidFileFormatException("Invalid file format type: "+fileType);
				//break;
			}
			return isFileCreated;
		}catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: \n");
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			log.info("Exception Occured: \n");
			e.printStackTrace();
			return false;
		}
	}
	
	//New started

	public void validateFileEntities(int agencyId, String fileType, String genType) throws InvalidFileGenerationTypeException {
		
		if(genType.equals(Constants.FILE_GEN_TYPE_INCR)) {
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
		
		if(fileType.equals(Constants.ATGU)) {
			log.info("Valid file type: "+fileType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file type: "+fileType);
		}
		
		if(agencyId == 3) {
			log.info("Valid agency Id: "+agencyId);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid agency Id: "+agencyId);
		}
	}
	
	//call from 3 sections name, header, detail
	@SuppressWarnings("null")
	public String buildFileSection(List<MappingInfoDto> mappings, TAGDevice tagDevice) {
		
		StringBuilder tempField = new StringBuilder();
		//processStartOfLine(fileSection);
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice));
		}
		System.out.println(tempField.toString());
		
		return tempField.toString();

	}

	
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		String value = "";
		switch (fMapping.getFieldName()) {
		
		case Constants.F_FROM_AGENCY_ID:
			value = Constants.HOME_AGENCY_ID;
			break;
		
		case Constants.F_UNERSCORE:
			value = fMapping.getDefaultValue();
			break;
		
		case Constants.F_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		
		case Constants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
		
		case Constants.H_FILE_TYPE:
			value = fMapping.getDefaultValue();
			break;
		
		case Constants.H_VERSION:
			value = fMapping.getDefaultValue();
			break;
		
		case Constants.H_FROM_AGENCY_ID:
			value = Constants.HOME_AGENCY_ID;
			break;
		
		case Constants.H_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			break;
			
		case Constants.H_PREV_FILE_DATE_TIME:
			//String lastFileDownloadTS = iagDao.getLastDwnldTS();
			//LocalDateTime previousFileDateTime = LocalDateTime.parse(lastFileDownloadTS, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			//value = previousFileDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			value = iagDao.getLastDwnldTS();
			break;
		
		case Constants.H_RECORD_COUNT:
			value = doPadding(fMapping, String.valueOf(totalRecords));
			recordCountMap.put(Constants.TOTAL, value);
			break;
			
//		case Constants.H_COUNT_STAT1:
//			value = doPadding(fMapping, String.valueOf(tsGood));
//			recordCountMap.put(Constants.GOOD, value);
//			break;
//			
//		case Constants.H_COUNT_STAT2:
//			value = doPadding(fMapping, String.valueOf(tsLow));
//			recordCountMap.put(Constants.LOW_BAL, value);
//			break;
//			
//		case Constants.H_COUNT_STAT3:
//			value = doPadding(fMapping, String.valueOf(tsInvalid));
//			recordCountMap.put(Constants.INVALID, value);
//			break;
//			
//		case Constants.H_COUNT_STAT4:
//			value = doPadding(fMapping, String.valueOf(tsLost));
//			recordCountMap.put(Constants.LOST, value);
//			break;
			
		case Constants.D_TAG_AGENCY_ID:
			value = doPadding(fMapping, tagDevice.getDevicePrefix());
			break;
			
		case Constants.D_TAG_SERIAL_NUMBER_ID:
			value = doPadding(fMapping, tagDevice.getSerialNo());
			break;
			
		case Constants.D_TAG_STATUS:
			value = doPadding(fMapping, String.valueOf(tagDevice.getItagTagStatus()));
			break;
			
		case Constants.D_TAG_ACC_INFO:
			value = buildHexadecimalForItagInfo(tagDevice.getItagInfo());
			break;
			
		case Constants.D_TAG_HOME_AGENCY:
			value = doPadding(fMapping, masterDataCache.getAgencyById(tagDevice.getTagOwningAgency()).getFilePrefix());
			break;
			
		case Constants.D_TAG_AC_TYPE_IND:
			value = doPadding(fMapping, tagDevice.getAccountTypeCd());
			break;
			
		case Constants.D_TAG_ACCOUNT_NO:
			value = doPadding(fMapping, tagDevice.getAccountNo());
			break;
			
		case Constants.D_TAG_PROTOCOL:
			value = doPadding(fMapping, tagDevice.getTagProtocol());
			break;
		
		case Constants.D_TAG_TYPE:
			value = doPadding(fMapping, tagDevice.getTagType());
			break;
			
		case Constants.D_TAG_MOUNT:
			value = doPadding(fMapping, tagDevice.getTagMount());
			break;
			
		case Constants.D_TAG_CLASS:
			value = doPadding(fMapping, tagDevice.getTagClass());
			break;
			
		}
			return value;
		
		}	
	/**
	 * 
	 * @param tagDeviceObj
	 */
	public void updateRecordCounter(TAGDevice tagDeviceObj){
		
	}
	
	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
	public boolean overwriteFileHeader(java.io.File file, List<MappingInfoDto> headerProperties) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public String doPadding(MappingInfoDto fMapping, String value) {
		String str = "";	
		if(value == null || value == "null" || value.isEmpty()) {
			str = fMapping.getDefaultValue();
		}else if("L".equals(fMapping.getJustification())) {
			str = StringUtils.leftPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		}else if("R".equals(fMapping.getJustification())) {
			str = StringUtils.rightPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		}else if(value.length() == fMapping.getFieldLength()) {
			return value;
		}
		return str;
	}
	
	public void readExistingFileforICLP(File file) throws FileNotFoundException,IOException,Exception {
		
	}
	
	/**
	 * 
	 * @param itagInfo
	 * @return Hexadecimal string equivalent for itagInfo
	 */
	private String buildHexadecimalForItagInfo(int itagInfo) {
		String hexaValue = Integer.toHexString(itagInfo); 
		return StringUtils.leftPad(String.valueOf(hexaValue), Constants.SIX, Constants.ZERO_CHAR);

	}
	
}
