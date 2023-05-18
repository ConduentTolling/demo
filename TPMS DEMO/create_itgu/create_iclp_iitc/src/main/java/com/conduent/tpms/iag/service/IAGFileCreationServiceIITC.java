package com.conduent.tpms.iag.service;

/**
 * 
 * Interface for IAG file creation process
 * 
 * @author taniyan
 *
 */

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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.constants.IITCConstants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.FileNameDto;
import com.conduent.tpms.iag.dto.IITCHeaderInfo;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.model.VAddressDto;
import com.conduent.tpms.iag.model.VCustomerNameDto;
import com.conduent.tpms.iag.model.VVehicle;
import com.conduent.tpms.iag.service.impl.IITCFileCreationServiceImpl;
import com.conduent.tpms.iag.utility.FileUtil;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;

public class IAGFileCreationServiceIITC {
	private static final Logger log = LoggerFactory.getLogger(IAGFileCreationServiceIITC.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	protected IITCHeaderInfo IITCheaderinfo = new IITCHeaderInfo();
	
	@Autowired
	protected SummaryFileCreationService summaryFileCreationService;

	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;
	
	@Autowired
	public IITCFileCreationServiceImpl IITCFileCreationServiceImpl;
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
private FileNameDto fileNameVO;
	
	private FileUtil fileUtil = new FileUtil();
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	
	private List<TAGDevice> tagDeviceList = new ArrayList<>();
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest;
	public List<VVehicle> filedetails;
	protected List<VVehicle> plateinfolist = new ArrayList<VVehicle>();
	protected VCustomerNameDto customernameinfo = new VCustomerNameDto();
	protected VAddressDto addressinfo;
	protected List<VAddressDto> listofinfo = new ArrayList<VAddressDto>();
	protected VAddressDto addressdto = new VAddressDto();
	protected VAddressDto companyName;
	
	//Record Counters
	protected long totalRecords = 0;
	protected long goodCountNysta = 0;
	protected long lowCountNysta = 0;
	protected long zeroCountNysta = 0;
	protected long lostCountNysta = 0;
	
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
	
	public void initialize() {
	}
	
	public void fileCreationTemplate(int agencyId, String fileType, String genType) {
		try {
			validateFileEntities(genType);
			initialize();
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			//Fetch list of tag records
			//tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			//footer query etc for future
			
			//Read the file
			List<File> itagiclpFileList = getExistingFiles();
			if (itagiclpFileList.isEmpty()) {
				log.info("File is not created yet......");
			}
			
			for(File file : itagiclpFileList) 
			{
				if(fileType.equals(IITCConstants.IITC) || fileType == IITCConstants.IITC) 
				{
					readExistingFileforIITC(file);
				}
				else
				{
					log.info("Wrong file type ..{}",fileType);
					throw new InvalidFileFormatException("Invalid file format type: "+fileType);
				}
			}
			processFileCreation(listofinfo);
			
		} catch (Exception e) {
			log.info("Exception occured...",e);
			e.printStackTrace();
		}
	}
	
	
	public void processFileCreation(List<VAddressDto> info)throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file crreation..", fileType);
			switch (fileType) {
			
			case IITCConstants.IITC :
				IITCFileCreationServiceImpl.createIITCFile(info);
				break;
				
			default:
				throw new InvalidFileFormatException("Invalid file format type: "+fileType);
				//break;
			}
		}catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: \n");
			e.printStackTrace();
		}catch (Exception e) {
			log.info("Exception Occured: \n");
			e.printStackTrace();
		}
	}
	
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
			
			if(genType.equals(ICLPConstants.FILE_GEN_TYPE_FULL) || genType.equals(ICLPConstants.FILE_GEN_TYPE_INCR)) {
				log.info("Valid file generation type: "+genType);
			}else {
				throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
			}
		}

	private void loadOutputConfigurationMapping(int agencyId, String fileType, String genType) {
		getFileCreationParameter().setFileType(fileType);
		getFileCreationParameter().setAgencyId(agencyId);
		getFileCreationParameter().setGenType(genType);
		setConfigurationMapping(iagDao.getMappingConfigurationDetails(getFileCreationParameter()));
		
	}
	
	public void readExistingFileforIITC(File file) throws FileNotFoundException,IOException,Exception {
			
		}

	public List<File> getExistingFiles() throws IOException {
		List<File> fileList= fileUtil.getAllfilesFromSrcDirectory(configVariable.getFileSrcPath().toString());
		return fileList;
	}
	
	@SuppressWarnings("null")
	public String buildFileSection(List<MappingInfoDto> mappings, VAddressDto tagDevice) {
		
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
	
	public String doFieldMapping(MappingInfoDto fMapping, VAddressDto tagDevice) {
		String value = "";
		switch (fMapping.getFieldName()) {
		
		case ICLPConstants.F_FROM_AGENCY_ID:
			value = ICLPConstants.HOME_AGENCY_ID;
			break;
		
		case ICLPConstants.F_UNERSCORE:
			value = fMapping.getDefaultValue();
			break;
		
		case ICLPConstants.F_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		
		case ICLPConstants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
		
		case ICLPConstants.H_FILE_TYPE:
			value = fMapping.getDefaultValue();
			break;
		
		case ICLPConstants.H_RECORD_COUNT:
			value = doPadding(fMapping, String.valueOf(totalRecords));
			recordCountMap.put(ICLPConstants.TOTAL, value);
			break;
		
		case ICLPConstants.H_FILE_DATE_TIME:
			//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		
		case ICLPConstants.H_FROM_AGENCY_ID:
			value = ICLPConstants.HOME_AGENCY_ID;
			break;
			
		case IITCConstants.D_CUST_TAG_SERIAL_ID:
			value = doPadding(fMapping, tagDevice.getDeviceNo());
			break;
			
		case IITCConstants.D_CUST_LAST_NAME:
			value = doPadding(fMapping, tagDevice.getLastName());
			break;
			
		case IITCConstants.D_CUST_FIRST_NAME:
			value = doPadding(fMapping, tagDevice.getFirstName());
			break;
			
		case IITCConstants.D_CUST_MIDDLE_NAME:
			value = doPadding(fMapping,tagDevice.getMiddleInitial());
			break;
			
		case IITCConstants.D_CUST_COMPANY:
			value = doPadding(fMapping,tagDevice.getCompanyName());
			break;
			
		case IITCConstants.D_CUST_ADDR_1:
			value = doPadding(fMapping,tagDevice.getStreet1());
			break;
			
		case IITCConstants.D_CUST_ADDR_2:
			value = doPadding(fMapping,tagDevice.getStreet2());
			break;
			
		case IITCConstants.D_CUST_CITY:
			value = doPadding(fMapping, tagDevice.getCity());
			break;
			
		case IITCConstants.D_CUST_STATE:
			value = doPadding(fMapping,tagDevice.getState());
			break;
			
		case IITCConstants.D_CUST_ZIP:
			value = doPadding(fMapping, tagDevice.getZipcode());
			break;
		}
		return value;
		
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
	public boolean overwriteFileHeader(File file, List<MappingInfoDto> headerProperties) {
		// TODO Auto-generated method stub
		return false;
	}
}
