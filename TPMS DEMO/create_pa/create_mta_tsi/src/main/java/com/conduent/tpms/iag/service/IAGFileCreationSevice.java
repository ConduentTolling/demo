package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.impl.TSFileCreationServiceImpl;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;

/**
 * 
 * Interface for IAG file creation process
 * 
 * @author urvashic
 *
 */
public class IAGFileCreationSevice {

	private static final Logger log = LoggerFactory.getLogger(IAGFileCreationSevice.class);

	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;
	
	@Autowired
	public TSFileCreationServiceImpl tsFileCreationServiceImpl;
	
	@Autowired
	IAGDao iagDao;
	
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected String fileGenType;
	
	protected List<TAGDevice> tagDeviceList = new ArrayList<>();
	private List<IncrTagStatusRecord> deviceNoList = new ArrayList<>();
	private TAGDevice deviceInfo;
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest ;
	
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
	protected long sequenceCount = 0;
	
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
	public String getFileGenType() {
		return fileGenType;
	}
	public void setFileGenType(String fileGenType) {
		this.fileGenType = fileGenType;
	}
	
	/*
	 * To initialize basic TAG file properties
	 */
	public void initialize() {
	}
	
	/**
	 * 
	 * @param agencyId
	 * @param fileType
	 * @param genType
	 */
	public void fileCreationTemplate(int agencyId, String fileType, String genType) {
		try {
			log.info("Validating file generation type..");
			validateFileEntities(genType);
			log.info("Initializing..");
			initialize();
			log.info("Loading configuration..");
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			
			//collecting device no from T_INCR_TAG_STATUS_ALLSORTED
			deviceNoList = iagDao.getDeviceNoFromTInrTagStatusAllSorted(genType);
			
			for(IncrTagStatusRecord deviceinfo : deviceNoList) 
			{
				deviceInfo = iagDao.getDevieListFromTTagAllSorted1(agencyId, genType,deviceinfo);
				tagDeviceList.add(deviceInfo);
			}
			log.info("Device Info taken....{}",tagDeviceList);
			
			//footer query etc for future
			processFileCreation(tagDeviceList);
			
			
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Exception Occured InvalidFileGenerationTypeException: \n");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param agencyId
	 * @param fileType
	 * @param genType
	 */
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
	public void processFileCreation(List<TAGDevice> tagDeviceList) throws InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file creation..", fileType);
			switch (fileType) {
			
			case Constants.TSI:
				tsFileCreationServiceImpl.createMTATSIFile(tagDeviceList);
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
	
	/**
	 * 
	 * @param genType
	 * @throws InvalidFileGenerationTypeException
	 */
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
		log.info("File generation type is: {} ", genType);
		if(genType.equals(Constants.FILE_GEN_TYPE_INCR)) {
			setFileGenType(genType);
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
		
	}
	
	public String buildFileSection(List<MappingInfoDto> mappings, TAGDevice tagDevice) {
		
		StringBuilder tempField = new StringBuilder();
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice));
		}
		log.debug(tempField.toString());
		
		return tempField.toString();

	}

	
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		
		return null;
	}	
	
	/**
	 * 
	 * @param tagDeviceObj
	 */
	public void updateRecordCounter(TAGDevice tagDeviceObj){
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
	public boolean overwriteFileHeader(java.io.File file, List<MappingInfoDto> headerProperties) {

		boolean headerUpdatedInFile = false;
				
		log.info("Building new header with latest record counts ..");
		String newLine = buildFileSection(headerProperties, null);
		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				oldLine = sc.nextLine().substring(0,100);
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine());
			}

			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			
			log.info("Replacing MTA TSI file {} header",file.getName());
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
	 * @param fMapping
	 * @param value
	 * @return
	 */
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
	
	/**
	 * 
	 * @param tagDeviceList
	 * @throws IOException
	 */
	public void createMTATSIFile(List<TAGDevice> tagDeviceList) throws IOException
	{
		// TODO Auto-generated method stub
	}
	
	public void writeContentToFile(FileWriter filewriter, String record ) {
		try {
			filewriter.write(record);
			log.info("Record written to file successfully: {}",record);
			//error recovery
		} catch (IOException e) {
			log.error("Exception occurred while writing record {}",record);
			e.printStackTrace();
		}
	}
	
	
}
