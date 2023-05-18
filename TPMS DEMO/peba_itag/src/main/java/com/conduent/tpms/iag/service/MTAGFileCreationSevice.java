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
import org.springframework.web.client.RestTemplate;

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
import com.conduent.tpms.iag.service.impl.MTAGFileCreationServiceImpl;
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
public class MTAGFileCreationSevice {

	private static final Logger log = LoggerFactory.getLogger(MTAGFileCreationSevice.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;
	
	@Autowired
	public MTAGFileCreationServiceImpl nystaTAGCreationServiceImpl;
	
	@Autowired
	public FileUtilityClass fileUtilityClass;
	
	@Autowired
	IAGDao iagDao;
	
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	
	private List<TAGDevice> tagDeviceList = new ArrayList<>();
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
	
	protected long good =0;
	protected long low_bal=0;
	protected long invalid =0;
	
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
	public String fileCreationTemplate(int agencyId, String fileType, String genType, boolean ispeba) {
		try {
			log.info("Validating file generation type..");
			validateFileEntities(genType);
			validateAgency(agencyId);
			validateFileType(fileType);
			log.info("Initializing..");
			initialize();
			log.info("Loading configuration..");
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			//Fetch list of tag records
			tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			//footer query etc for future
			return processFileCreation(tagDeviceList,ispeba);
		//	return "Job executed successfully";
			
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Job execution failed Exception Occured for invalid file generation type: {}",e);
			e.printStackTrace();
			return "Job execution failed for agencyId:"+agencyId+", fileType:"+fileType+", genType:"+genType+" ::"+e.getMessage();
		}catch (InvalidFileFormatException e) {
			log.info("Job execution failed Exception Occured for invalid file format type: {}",e);
			e.printStackTrace();
			return "Job execution failed for agencyId:"+agencyId+", fileType:"+fileType+", genType:"+genType+" ::"+e.getMessage();
		}
		catch (Exception e) {
			log.info("Job execution failed Exception Occured:{}",e.getMessage());
			e.printStackTrace();
			return "Job execution failed for agencyId:"+agencyId+", fileType:"+fileType+", genType:"+genType+" ::"+e.getMessage();
		}
	}

	private void validateFileType(String fileType) throws InvalidFileGenerationTypeException {
		// TODO Auto-generated method stub
		
		log.info("File fileType {}",fileType);
		if(fileType.equals(Constants.FILE_TYPE_ITAG)) {
			log.info("Valid file Type {}"+fileType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid filetype: "+fileType);
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
	public String processFileCreation(List<TAGDevice> tagDeviceList, boolean ispeba) throws InvalidFileFormatException {
		try {
			String fileType = getFileCreationParameter().getFileType();
			log.info("Start processing {} file creation..", fileType);
			switch (fileType) {
			/*
			 * case Constants.ITAG: // itagCreationService.createItagFile(agencyId,
			 * fileFormat, genType); break;
			 */
			case Constants.ITAG:
				nystaTAGCreationServiceImpl.createTagFile(tagDeviceList, ispeba);
				break;	
			default:
				throw new InvalidFileFormatException("Invalid file format type: "+fileType);
			}
		}catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: \n");
			e.printStackTrace();
			return "Job execution failed :"+e.getMessage();
		}catch (Exception e) {
			log.info("Exception Occured: \n");
			e.printStackTrace();
			return "Job execution failed :"+e.getMessage();
		}
		return "Job executed successfully";
	}
	
	/**
	 * 
	 * @param genType
	 * @throws InvalidFileGenerationTypeException
	 */
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
		log.info("File generation type is: {} ", genType);
		if(genType.equals(Constants.FILE_GEN_TYPE_FULL) || genType.equals(Constants.FILE_GEN_TYPE_INCR)) {
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
		
	}
	

	/**
	 * 
	 * @param genType
	 * @throws InvalidFileGenerationTypeException
	 */
	public void validateAgency(int agencyId) throws InvalidFileGenerationTypeException {
		log.info("File agencyId is: {} ", agencyId);
		if(agencyId==Constants.PEBA_AGENCY_ID || agencyId==Constants.TIBA_AGENCY_ID) {
			log.info("Valid file agencyId: "+agencyId);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file agencyId: "+agencyId);
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
		return "";
	}	
	
	
	public File createNewFile(String tempFilename, String tagFileDest) {
		Path destDir = new File(tagFileDest, tempFilename).toPath();
		
		 File temp = destDir.toFile();
           if (temp.exists()) {
               temp.delete();
           }
		File file = new File(tagFileDest, tempFilename);
		return file;
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
				oldLine = sc.nextLine();
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine() + "\n");
			}

			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			
			log.info("Replacing MTAG file {} header",file.getName());
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
		}else if(value.length()==fMapping.getFieldLength()) 
		{
			str=value;
		}
		return str;
	}
	
	/**
	 * 
	 * @param tagDeviceList
	 * @throws IOException
	 */
	public void createTagFile(List<TAGDevice> tagDeviceList,boolean ispeba) throws IOException
	{
		// TODO Auto-generated method stub
	}
	
	public void writeContentToFile(FileWriter filewriter, String record ) {
		try {
			if(!record.equalsIgnoreCase("") && !record.equalsIgnoreCase(" ")) {
				filewriter.write(record + "\r");
				log.info("Record written to file successfully: {}",record);
			}else {
				log.info("Record is blank: {}",record);
			}
			//error recovery
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
	public File getLatestFileFromXFER(Agency agency) {
		String latestItagFilenameFromTXFER = iagDao.getLastSuccessfullFile(agency);
		log.info("File from xfer: {}",latestItagFilenameFromTXFER);
		log.info("Fetching file {} from PROC location: {}",latestItagFilenameFromTXFER, configVariable.getItagFileDest());
		File lastProcFile= fileUtilityClass.getFileFromDirectory(configVariable.getItagFileDest(), latestItagFilenameFromTXFER);
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
		Optional<File> mostRecentFile = Arrays.stream(parentFolder.toFile().listFiles())
			        .filter(f -> f.isFile() && f.getName().contains(Constants.ITAG) && f.getName().startsWith(agency.getFilePrefix()))
			        .max((f1, f2) -> Long.compare(f1.lastModified(),
			                f2.lastModified()));
		if(mostRecentFile.isPresent()){
			log.info("File from PROC: {}",mostRecentFile.get().getName());
				return mostRecentFile.get();
		}else {
			return null;
		}
	}

}
