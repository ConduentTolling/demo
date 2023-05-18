package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.Agency;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.impl.TSFileCreationServiceImpl;
import com.conduent.tpms.iag.utility.FileUtilityClass;
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
	
	@Autowired
	public FileUtilityClass fileUtilityClass; 
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected String fileGenType;
	
	private List<TAGDevice> tagDeviceList = new ArrayList<>();
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest ;
	
	//Record Counters
	protected long totalRecords = 0;
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
			tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			createTagFile(tagDeviceList);
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Exception Occured InvalidFileGenerationTypeException: \n");
			e.printStackTrace();
		} catch (Exception e) {
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
/*	public void processFileCreation(List<TAGDevice> tagDeviceList) throws InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file creation..", fileType);
				tsFileCreationServiceImpl.createTagFile(tagDeviceList);
		}catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: \n");
			e.printStackTrace();
		}catch (Exception e) {
			log.info("Exception Occured: \n");
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * 
	 * @param genType
	 * @throws InvalidFileGenerationTypeException
	 */
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
		log.info("File generation type is: {} ", genType);
		if(genType.equals(Constants.FILE_GEN_TYPE_FULL) || genType.equals(Constants.FILE_GEN_TYPE_INCR)) {
			setFileGenType(genType);
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
		
	}
	
	/**
	 * 
	 * @param mappings
	 * @param tagDevice
	 * @return
	 */
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

	/**
	 * 
	 * @param fMapping
	 * @param tagDevice
	 * @return
	 */
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		return "";
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
			sc1.close();
			
			log.info("Replacing TAG file {} header",file.getName());
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
	 * @throws Exception 
	 */
	public void createTagFile(List<TAGDevice> tagDeviceList) throws IOException, Exception
	{
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 * @param filewriter
	 * @param record
	 */
	public void writeContentToFile(FileWriter filewriter, String record ) {
		try {
			filewriter.write(record);
			log.info("Record written to file successfully: {}",record);
		} catch (IOException e) {
			log.error("Exception occurred while writing record {}",record);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param agency
	 * @return
	 */
	@SuppressWarnings("unused")
	public File getLatestFileFromXFER(Agency agency) {
		String latestItagFilenameFromTXFER = iagDao.getLastSuccessfullFile(agency);
		log.info("File from xfer: {}",latestItagFilenameFromTXFER);
		log.info("Fetching file {} from PROC location: {}",latestItagFilenameFromTXFER,configVariable.getItagFileDest());
		File lastProcFile = fileUtilityClass.getFileFromDirectory(configVariable.getItagFileDest(), latestItagFilenameFromTXFER);
		if(lastProcFile != null) {
		log.info("File present in PROC dir: {}", lastProcFile.getAbsolutePath());
		}else {
		log.info("File is not present in PROC dir: {}", latestItagFilenameFromTXFER);
		}
		return lastProcFile;
	}
	
	/**
	 * 
	 * @param agency
	 * @return
	 */
	public File getLatestFileFromPROC(Agency agency) {
		Path parentFolder = Paths.get(configVariable.getItagFileDest());
		try {
			Optional<File> mostRecentFile = Arrays.stream(parentFolder.toFile().listFiles())
				        .filter(f -> f.isFile() && f.getName().contains(Constants.ITAG) && f.getName().startsWith(agency.getFilePrefix()))
				        .max((f1, f2) -> Long.compare(f1.lastModified(),
				                f2.lastModified()));
			if(mostRecentFile.isPresent()){
				log.info("File from PROC: {} ", mostRecentFile.get().getName());
					return mostRecentFile.get();
			}else {
				log.info("File not present in PROC for agency prefix ",agency.getFilePrefix());
				return null;
			}
		} catch (Exception e) {
			log.info("Exception caught while fetching file from PROC dir..");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param tempFileName
	 * @param tagFileDest
	 * @param fileCompletionStatus
	 * @param fileType
	 * @return
	 * @throws FileNotFoundException 
	 */
	public boolean fileRenameOperation(String tempFileName, String tagFileDest, Boolean fileCompletionStatus, String fileType) throws FileNotFoundException {
		boolean fileCompleted = false;
		File renamefile = null;
		log.info("Rename file {} to type {} at location {}",tempFileName,fileType, tagFileDest);
		File file = new File(tagFileDest, tempFileName);
		if (fileCompletionStatus) {
			renamefile = new File(tagFileDest, tempFileName.replace("TEMP", fileType));
			fileCompleted = fileWriteOperation.renameFile(file, renamefile);
		}
		else if(fileCompleted)  {
			log.info("File renamed to file:{}", renamefile.getAbsolutePath());
		}
	log.info("File renamed successfully: {}",fileCompleted);
	return fileCompleted;
		
	}

}
