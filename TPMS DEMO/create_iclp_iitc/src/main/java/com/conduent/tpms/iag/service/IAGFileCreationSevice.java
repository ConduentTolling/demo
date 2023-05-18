package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.FileNameDto;
import com.conduent.tpms.iag.dto.ICLPHeaderInfoVO;
import com.conduent.tpms.iag.dto.IITCHeaderInfo;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.model.VVehicle;
import com.conduent.tpms.iag.service.impl.ICLPFileCreationServiceImpl;
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
	
	protected ICLPHeaderInfoVO ICLPheaderinfo = new ICLPHeaderInfoVO();

	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;

	@Autowired
	public ICLPFileCreationServiceImpl ICLPFileCreationServiceImpl;
	
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
	public String parentDest;
	public String fileName;
	public Boolean tpmsStatusFlag = true;
	public List<VVehicle> filedetails;
	protected List<VVehicle> plateinfolist = new ArrayList<VVehicle>();
	protected List<VVehicle> vehicleinfolist = new ArrayList<VVehicle>();
	
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
	/*
	 * To initialize basic MTAG file properties
	 */
	public void initialize() {
	}
	
	public String fileCreationTemplate(int agencyId, String fileType, String genType) {
		try {
			validateFileEntities(genType, agencyId, fileType);
			initialize();
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			//Fetch list of tag records
			//tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			//footer query etc for future
			
			//Add the variable here
			try {
				tpmsStatusFlag = validateTPMSStatus();
				log.info("tpmsStatusFlag::{}",tpmsStatusFlag);
			}catch(Exception e) {
				log.error("Error while Fetching tpmsStatusFlag..{}",e.getMessage());
			}
			//Read the file
			List<File> itagiclpFileList = getExistingFiles();
			if (itagiclpFileList.isEmpty()) {
				log.info("File is not created yet......");
			}
			
			for(File file : itagiclpFileList) 
			{
				if(fileType.equals(ICLPConstants.ICLP) || fileType == ICLPConstants.ICLP) 
				{
					if(file.getName().contains(ICLPConstants.ITAGICLP)) {
						readExistingFileforICLP(file);
					}else {
						log.info("Wrong ITAGICLP file type ..{}",file.getName());
					}
				}
				else
				{
					log.info("Wrong file type ..{}",fileType);
					throw new InvalidFileFormatException("Invalid file format type: "+fileType);
				}
				file.delete();
			}
			processFileCreation(plateinfolist);
			return "Job executed successfully";
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Job execution failed Exception Occured for invalid file generation type: {}",e);
			e.printStackTrace();
			return e.getMessage();
		}catch (InvalidFileFormatException e) {
			log.info("Job execution failed Exception Occured for invalid file format type: {}",e);
			e.printStackTrace();
			return e.getMessage();
		}
		catch (Exception e) {
			log.info("Job execution failed Exception Occured:{}",e);
			e.printStackTrace();
			return e.getMessage();
		}
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
	public void processFileCreation(List<VVehicle> info)throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file crreation..", fileType);
			switch (fileType) {
			
			case ICLPConstants.ICLP :
				ICLPFileCreationServiceImpl.createICLPFile(info);
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
	
	//New started

	public void validateFileEntities(String genType, int agencyId, String fileType) throws InvalidFileGenerationTypeException {
		
		if((genType.equals(ICLPConstants.FILE_GEN_TYPE_FULL) || genType.equals(ICLPConstants.FILE_GEN_TYPE_INCR))
				&& agencyId == ICLPConstants.NYSTA_AGENCY_ID_NUM && (fileType.equals(ICLPConstants.ICLP) || fileType == ICLPConstants.ICLP)){
			log.info("Valid file generation type: "+genType+" and agency id "+agencyId+" and fileType "+fileType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType+" or agency id "+agencyId+" and fileType "+fileType);
		}

		
	}
	
	
	//call from 3 sections name, header, detail
	@SuppressWarnings("null")
	public String buildFileSection(List<MappingInfoDto> mappings, VVehicle tagDevice) {
		
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

	
	public String doFieldMapping(MappingInfoDto fMapping, VVehicle tagDevice) {
		String value = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
			
		case ICLPConstants.H_VERSION:
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
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			break;
		
		case ICLPConstants.H_FROM_AGENCY_ID:
			value = ICLPConstants.HOME_AGENCY_ID;
			break;
			
		case ICLPConstants.D_LIC_STATE:
			value = doPadding(fMapping, tagDevice.getPlate_state());
			break;
			
		case ICLPConstants.D_LIC_NUMBER:
			String plateNo = tagDevice.getPlate_number().substring(0, Math.min(tagDevice.getPlate_number().length(), fMapping.getFieldLength()));
			value = doPadding(fMapping, plateNo);
			break;
			
		case ICLPConstants.D_LIC_TYPE:
			value = doPadding(fMapping,tagDevice.getPlate_type());
			break;
			
		case ICLPConstants.D_AGENCY_ID:
			value = tagDevice.getDevicePrefix();//coming all 0 need to check substring
			break;
			
		case ICLPConstants.D_TAG_SERIAL_NUMBER_ID:
			value = doPadding(fMapping,tagDevice.getSerialNo());//1 less leading 0
			break;

		case ICLPConstants.D_LIC_EFFECTIVE_FROM:
			if(tagDevice.getLicEffectiveFrom()!= null) {
				value = sdf.format(tagDevice.getLicEffectiveFrom());	
			}else {
				value = "********************";
			}
			break;


		case ICLPConstants.D_LIC_EFFECTIVE_TO:
			if(tagDevice.getLicEffectiveTo()!= null) {
				value = sdf.format(tagDevice.getLicEffectiveTo());	
			}else {
				value = "********************";
			}
			break;

		case ICLPConstants.D_LIC_HOME_AGENCY:
			value = doPadding(fMapping,tagDevice.getLicHomeAgency());
			break;

		case ICLPConstants.D_LIC_ACCOUNT_NO:
			value = doPadding(fMapping,tagDevice.getAccountNo());
			break;
			
		case ICLPConstants.D_LIC_VIN:
			value = doPadding(fMapping,tagDevice.getVin());
			break;
			
		case ICLPConstants.D_TLIC_GUARANTEED:
			value = doPadding(fMapping,tagDevice.getLicGuaranteed());
			break;
		}
	//}
		return value;
		
	}	
	
	/**
	 * 
	 * @param tagDeviceObj
	 */
	public void updateRecordCounter(VVehicle tagDeviceObj){
		
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
	
	public List<File> getExistingFiles() throws IOException {
		List<File> fileList= fileUtil.getAllfilesFromSrcDirectory(configVariable.getFileSrcPath().toString());
		return fileList;
	}
	
	public void readExistingFileforICLP(File file) throws FileNotFoundException,IOException,Exception {
		
	}
	
	public boolean validateTPMSStatus() {
		
		String invalidStatusFlag = iagDao.getInvalidStatusFlag();
		if(invalidStatusFlag.equalsIgnoreCase("Y")) {
			return true;
		}else {
			return false;
		}
	}
	
}
