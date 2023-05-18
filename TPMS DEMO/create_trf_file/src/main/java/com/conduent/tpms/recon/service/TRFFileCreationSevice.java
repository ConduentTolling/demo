package com.conduent.tpms.recon.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
import com.conduent.tpms.recon.constants.Constants;
import com.conduent.tpms.recon.dao.ReconDao;
import com.conduent.tpms.recon.dto.FileCreationParameters;
import com.conduent.tpms.recon.dto.FileHeaderDto;
import com.conduent.tpms.recon.dto.MappingInfoDto;
import com.conduent.tpms.recon.dto.ReconciliationDto;
import com.conduent.tpms.recon.dto.XferFileInfoDto;
import com.conduent.tpms.recon.exception.InvalidFileFormatException;
import com.conduent.tpms.recon.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.recon.model.TAGDevice;
import com.conduent.tpms.recon.service.impl.TRFFileCreationServiceImpl;
import com.conduent.tpms.recon.utility.FileWriteOperation;
import com.conduent.tpms.recon.utility.MasterDataCache;
import com.conduent.tpms.recon.utility.TRFValidationUtils;

/**
 * 
 * @author shrikantm3
 *
 */
public class TRFFileCreationSevice {

	private static final Logger log = LoggerFactory.getLogger(TRFFileCreationSevice.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	protected SummaryFileCreationService summaryFileCreationService;
	
	protected FileHeaderDto headerinfo = new FileHeaderDto();

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public TRFValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;

	@Autowired
	public TRFFileCreationServiceImpl ITGUFilcereationserviceImpl;
	
	@Autowired
	ReconDao reconDao;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	//private FileNameDto fileNameVO;
	
	//private FileUtil fileUtil = new FileUtil();
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	
	protected List<TAGDevice> tagDeviceList = new ArrayList<>();
	//private List<IncrTagStatusRecord> deviceNoList = new ArrayList<>();
	private List<XferFileInfoDto> xferFileList = new ArrayList<>();
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest;
	
	//Record Counters
	protected long totalRecords = 0;
	protected long intermidiateCount = 0;
	protected long tsGood = 0;
	protected long tsLow = 0;
	protected long tsInvalid = 0;
	protected long tsLost = 0;
	
	protected long goodCountNysba = 0;
	protected long lowCountNysba = 0;
	protected long zeroCountNysba = 0;
	protected long lostCountNysba = 0;
	protected long skipCount = 0;
	
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
	
	public void fileCreationTemplate(int agencyId, String fileType, String genType) {
		try {
			validateFileEntities(genType);
			initialize();
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			log.info("Start processing {} file creation..", fileType);	
			//reconList = iagDao.getReconData();
			LocalDateTime updateTs = reconDao.getUpdateTs();
			log.info("### update_ts ### {}", updateTs.toString());
			xferFileList = reconDao.getXferFileInfo(updateTs);
			processFileCreation(xferFileList, updateTs);
			
		} catch (Exception e) {
			log.info("Exception occured...",e);
			e.printStackTrace();
		}
	}

	private void loadOutputConfigurationMapping(int agencyId, String fileType, String genType) {
		getFileCreationParameter().setFileType(fileType);
		getFileCreationParameter().setAgencyId(agencyId);
		getFileCreationParameter().setGenType(genType);
		setConfigurationMapping(reconDao.getMappingConfigurationDetails(getFileCreationParameter()));
		
	}

	/**
	 * Start process of file creation based on file format.
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	
	public void processFileCreation(List<XferFileInfoDto> info, LocalDateTime updateTs)throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file crreation..", fileType);
			switch (fileType) {
			
			case Constants.TRF :
				ITGUFilcereationserviceImpl.createTRFFile(info, updateTs);
				break;
				
			default:
				throw new InvalidFileFormatException("Invalid file format type: "+fileType);
			}
		}catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: \n");
			e.printStackTrace();
		}catch (Exception e) {
			log.info("Exception Occured: \n");
			e.printStackTrace();
		}
	}
	
	//New started

	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
		
		if(genType.equals(Constants.FIXED)) {
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
	}
	
	
	// code for reconciliation.. call from 3 sections name, header, detail
	@SuppressWarnings("null")
	public String buildFileSection1(List<MappingInfoDto> mappings, ReconciliationDto reconciliation) {
		
		StringBuilder tempField = new StringBuilder();
		//processStartOfLine(fileSection);
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), reconciliation));
		}
		System.out.println(tempField.toString());
		
		return tempField.toString();

	}

	
	// code for reconciliation
	public String doFieldMapping(MappingInfoDto fMapping, ReconciliationDto reconciliation) {
		String value = "";
		switch (fMapping.getFieldName()) {
		
		case Constants.F_FILE_DATE_TIME:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		
		case Constants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
		
		case Constants.H_RECORD_TYPE_CODE:
			value = fMapping.getDefaultValue();
			break;
				
		case Constants.H_RECORD_COUNT:
			value = doPadding(fMapping, String.valueOf(totalRecords));
			recordCountMap.put(Constants.TOTAL, value);
			break;
		
		case Constants.H_FILE_DATE_TIME:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
			
		case Constants.H_FILE_SERIAL:
			int seqNo = reconDao.getFileSequence();
			String fileSerialNo = String.format("%02d", seqNo);
			value = fileSerialNo;
			break;
			
		case Constants.D_RECORD_TYPE_CODE:
			value = fMapping.getDefaultValue();
			break;
			
		case Constants.D_TXN_SERIAL_NUMBER:
			value = doPadding(fMapping, reconciliation.getTxExternRefNo().substring(4));
			break;
			
		case Constants.D_DEBIT_CREDIT:
			value = reconciliation.getDebitCredit();
			break;
			
		case Constants.D_POSTED_FARE:
			Double postedFare= reconciliation.getPostedFareAmount();
			postedFare = postedFare*100;
			int postedFareAmount = postedFare.intValue();
			String postedAmount = String.format("%05d", postedFareAmount);
			value = postedAmount;
			break;
			
		case Constants.D_VIOLATION_FEE:
			//String str2= reconciliation.getPostedFareAmount();
			String str2= "5.65";
			String violationFee  = str2.replaceAll("[.]", "");
			value = doPadding(fMapping, violationFee);
			break;
			
		case Constants.D_REVENUE_AGENCY_CODE:
			
			String devicePrefix = reconDao.getDevicePrefix(reconciliation.getAccountAgencyId());
			value = doPadding(fMapping, devicePrefix);
			break;
			
		case Constants.D_RECON_CODE:
			value = doPadding(fMapping, reconciliation.getReconStatusInd().toString());
			break;
			
		case Constants.D_RECON_SUB_CODE:
			value = doPadding(fMapping, reconciliation.getReconSubCodeInd().toString());
			break;
			
		case Constants.D_REVENUE_DATE:
			value = reconciliation.getRevenueDate().format(DateTimeFormatter.ofPattern("YYYYMMdd"));
			break;
			
		case Constants.D_POSTED_DATE:
			
			//String time = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("HHmmss"));
			String time ="000000";
			log.info("Time Format: {}", time);
			value = reconciliation.getPostedDate().format(DateTimeFormatter.ofPattern("YYYYMMdd"));
			value = value.concat(time);
			log.info("Posted DateTime Format: {}", value);
			break;
			
		case Constants.D_TRX_SERIAL_NO:
			//value = reconciliation.getTxnSerialNumber();
			value = "999999999999";
			break;
			
		case Constants.D_PLATE_COUNTRY:
			String plateCountry = reconciliation.getPlateCountry();
			if(plateCountry==null)
			{
				plateCountry = " ";
			value = StringUtils.leftPad(plateCountry, 3, " ");
			}else {
				value = StringUtils.leftPad(plateCountry, 3, " ");
			}
			break;
			
		case Constants.D_PLATE_STATE:
			String plateState = reconciliation.getPlateState();
			if(plateState==null)
			{
				plateState = " ";
			value = StringUtils.leftPad(plateState, 2, " ");
			}else {
				value = StringUtils.leftPad(plateState, 2, " ");
			}
			break;
			
		case Constants.D_PLATE_TYPE:
			value = doPadding(fMapping, reconciliation.getPlateType().toString());
			break;
			
		case Constants.D_PLATE_NUMBER:
			
			String plateNumber = reconciliation.getPlateNumber();
			if(plateNumber==null) {
				plateNumber = " ";
			value = StringUtils.leftPad(plateNumber, 10, " ");
			}else {
				value = StringUtils.leftPad(plateNumber, 10, " ");
			}
			break;
			
		case Constants.T_RECORD_TYPE_CODE:
			value = fMapping.getDefaultValue();
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
