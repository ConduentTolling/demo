package com.conduent.tpms.iag.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.ITAGHeaderInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TagFileHeaderDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.Agency;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.SummaryFileCreationService;
import com.conduent.tpms.iag.service.TSFileCreationService;
import com.conduent.tpms.iag.utility.FileUtilityClass;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/**
 * 
 * TS Implementation for IAG file creation process
 * 
 * @author urvashic
 *
 */

@Service
@Primary
public class TSFileCreationServiceImpl implements TSFileCreationService {
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	private static final Logger log = LoggerFactory.getLogger(TSFileCreationServiceImpl.class);

	String tagFileDest;
	String statFileDest;
	
	private long tagSortedAllRecords = 0;
	private long validCount = 0;
	private long minCount = 0;
	private long stolenCount = 0;
	private long lostCount = 0;
	private long invalidCount = 0;
	private long inventoryCount = 0;
	private long replenishCount = 0;
	//Record Counters
	protected long totalRecords = 0;
	protected long skipCount = 0;
	protected long sequenceCount = 0;
	protected long dbtags=0;
	
	private List<TAGDevice> tagDeviceList = new ArrayList<>();
	public FileHeaderDto fileHeader = new FileHeaderDto();
	private FileCreationParameters configurationMapping;
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	public Map<String,String> recordCountMap = new HashMap<>();
	public String fileName;
	public String parentDest ;
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected String fileGenType;
	
	TagFileHeaderDto tagFileHeaderDto = new TagFileHeaderDto();
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	SummaryFileCreationService summaryFileCreationService;
	
	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;
	
	@Autowired
	public FileUtilityClass fileUtilityClass; 
	
	
	File file;

	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
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
	
/*	public void initialize() {
		log.info("Initializing TS file properties..");
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
	}
*/	
	/**
	 * 
	 * @param agencyId
	 * @param fileType
	 * @param genType
	 */
	public String fileCreationTemplate(int agencyId, String fileType, String genType, boolean isRFK) {
		try {
			log.info("Validating file generation type..");
			validateFileEntities(genType);
			validateAgencyId(agencyId);
			validateFileType(fileType);
			log.info("Loading configuration..");
			loadOutputConfigurationMapping(agencyId, fileType, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			createTagFile(tagDeviceList,isRFK);
			return "Job executed successfully";
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Exception Occured InvalidFileGenerationTypeException: \n");
			e.printStackTrace();
			return "Job execution failed"+" "+e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "Job execution failed";
		}
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
				createTagFile(tagDeviceList);
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
	
	
  public String buildFileSectionnew(List<MappingInfoDto> mappings, TAGDevice tagDevice,CSVWriter c) {
		
		StringBuilder tempField = new StringBuilder();
		//CSVWriter c = new CSVWriter(filewriter);
		String str[] =null;
		String stq= null;
		String srt[]=new String[mappings.size()] ;
		String valstr=null;
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			
			valstr=doFieldMapping(mappingInfoDto.get(), tagDevice);
			
			srt[i-1]=valstr;
				
			
			//tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice)).append(" ");
			//str=tempField.toString().split(" ",-1);
			
			
			
			
		}
		c.writeNext(srt,false);
		//c.writeNext(str,false);
		log.debug(tempField.toString());
		
		return tempField.toString();
	}

  public String buildFileSectionnewTrailer(List<MappingInfoDto> mappings, TAGDevice tagDevice,CSVWriter c) {
		
		StringBuilder tempField = new StringBuilder();
		
		String str[] =null;
		String stq= null;
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice)).append(" ");
			str=tempField.toString().split(" ",-1);
			
			
		}
		c.writeNext(str,false);
		
		log.debug(tempField.toString());
		
		return tempField.toString();
	}
  
  
  public String buildFileSectionnew(List<MappingInfoDto> mappings, TAGDevice tagDevice) {
		
		StringBuilder tempField = new StringBuilder();
		//CSVWriter c = new CSVWriter(filewriter);
		String str[] =null;
		String stq= null;
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice)).append(",");
			
			
		}
		
		log.debug(tempField.toString());
		
		return tempField.toString();
	}
	
	@Override
	public void createTagFile(List<TAGDevice> tagDeviceList, boolean isRFK) throws IOException, Exception {
		try {
			log.info("Starting TS file creation process..");
			tagFileDest = parentDest.concat("//").concat(Constants.TAG_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			
			log.info("Total records fethced from DB : {}",tagDeviceList.size());
			tagSortedAllRecords = tagSortedAllRecords+tagDeviceList.size();
			log.info("Total record Counter updated to:{}",tagSortedAllRecords);
			
			
			if(isRFK) {
				recordCountMap.put(Constants.isRFK,"true");
			}else {
				recordCountMap.put(Constants.isRFK,"false");
			}
			
			//File content object
			StringBuilder fileContent = new StringBuilder();
			
			log.info("Processing device list.. ");
			if (!tagDeviceList.isEmpty() && tagDeviceList != null) {
				
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				log.info("Fetched list of Name mapping properties..");
				log.debug("Name mapping properties:{}",nameProperties.toString());
				
				fileName = buildFileSection(nameProperties, null);
				log.info("Built file name as {}",fileName);
				
				String tempFilename = fileName.replace("TS","TEMP");
				//File file = new File(tagFileDest, tempFilename);
				File file = new File(tagFileDest, fileName);
				log.info("Built temporary tag status file as {}",file.getAbsolutePath());
				FileWriter filewriter = new FileWriter(file, true); 
				CSVWriter c= new CSVWriter(filewriter);
				
				log.info("Populating TS file name {} for Statistics..",fileName);
				recordCountMap.put(Constants.FILENAME, fileName); // not giving with location due to size in db
				recordCountMap.put(Constants.FILEDATE, fileName.substring(4,12));
				recordCountMap.put(Constants.DEVICE_PREFIX, Constants.HOME_AGENCY_ID); 
				
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				log.info("Fetched list of Header mapping properties..");
				log.debug("Header mapping properties:{}",headerProperties.toString());
				//String header = buildFileSection(headerProperties, null);// for new one
				String header =buildFileSectionnew(headerProperties, null, c);
				log.info("Built header: {}",header);

				if(header != null) {
					fileContent.append(header);
				//	writeContentToFile(filewriter, header);
					log.info("Written header: {} to  file: {}",header, file.getAbsoluteFile());
				}
				
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				log.info("Fetched list of Detail record mapping properties..");
				log.debug("Detail record mapping properties:{}",detailRecordProperties.toString());
				
				log.info("Writing detail records for each object in tagDeviceList.. ");
				for (TAGDevice tagDevice : tagDeviceList) {
					boolean isValidRecord = validateTagRecord(tagDevice);	
					log.info("Record {} is valid? : {}",tagDevice.getDeviceNo(), isValidRecord);
					if(isValidRecord) {
						sequenceCount++;
						//String record = buildFileSection(detailRecordProperties, tagDevice);// for new one
						buildFileSectionnew(detailRecordProperties, tagDevice,c);
						recordCountMap.put(Constants.TAG_FROM_DATABASE, String.valueOf(tagDeviceList.size()));
						//fileContent.append(record);//for new one
						//writeContentToFile(filewriter, record);
						updateRecordCounter(tagDevice);
						}else {
						skipCount++;
						log.info("Record not added to file: {}", tagDevice.toString());
					}
				}
				log.info("Processing away agency files..");
				for (Agency agency : masterDataCache.getAwayAgencyList()) {
					File awaytagFile = getLatestItagFile(agency);
					if(awaytagFile != null) {
						log.info("Got latest file {} for agency prefix {}", awaytagFile.getName(), agency.getFilePrefix());
						recordCountMap.put(Constants.DEVICE_PREFIX, agency.getFilePrefix());
						StringBuilder awayContent = buildAwayTagRecordFromITAGFileAndWriteTS(awaytagFile, filewriter, detailRecordProperties); 
						fileContent.append(awayContent.toString());
					}
				}
				
				setIsTrailerPresentInFile(true);
				
				if(isTrailerPresentInFile()) {
					List<MappingInfoDto> trailerProperties = getConfigurationMapping().getTrailerMappingInfoDto();
					//String trailer = buildFileSection(trailerProperties, null);// for new one
					
					buildFileSectionnew(trailerProperties, null, c);
					//buildFileSectionnewTrailer(trailerProperties, null, c);
					//fileContent.append(trailer); //for new one
					//writeContentToFile(filewriter, trailer);
					log.info("Added trailer to the file..");
				} 
				
				//writeContentToFile(filewriter, fileContent.toString()); as of now
				filewriter.flush();
				filewriter.close();
				overwriteFileHeader(file, headerProperties,c); //as of now
				//c.flush();
				//c.close();
				//fileRenameOperation(tempFilename, tagFileDest, true, Constants.TS);
				populateRecordCountInMap();
				summaryFileCreationService.buildSummaryFileTS(recordCountMap, Constants.TS, statFileDest);
				//c.close();
			    
			}
			log.info(tagDeviceList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			log.info("File creation completed..");
			tagSortedAllRecords = 0;
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
			recordCountMap.clear();
			
		}
	}
	
	
	/**
	 * 
	 * @param awayFile
	 * @param filewriter
	 * @param detailRecordProperties
	 * @return
	 */
	private StringBuilder buildAwayTagRecordFromITAGFileAndWriteTS(File awayFile, FileWriter filewriter, List<MappingInfoDto> detailRecordProperties) {
		StringBuilder awayContent = new StringBuilder();
		CSVWriter c = new CSVWriter(filewriter);
		if(awayFile != null) {
			long count = fileWriteOperation.getRecordCountFromFile(awayFile);
			log.info("Total records in tag file {} are {}",awayFile, count);
			tagSortedAllRecords = tagSortedAllRecords + count;
			int good=0;
			int lbal=0;
			int invalid=0;
			int zero=0;
			
			try {
				FileReader fr = new FileReader(awayFile);
				BufferedReader br = new BufferedReader(fr);
				String line;
	
				String header = br.readLine(); 
				ITAGHeaderInfoVO itagHeaderInfoVO = getHeaderForFile(header);
				itagHeaderInfoVO.setMergeFileName(awayFile.getName());
				
				while((line = br.readLine()) != null)
				{
					TAGDevice tagDevice = new TAGDevice();
					tagDevice.setAccountType(0);
					//tagDevice.setTagOwningAgency(Integer.valueOf(line.substring(0, 4)));
					
					/*
					 * tagDevice.setTagOwningAgency(Long.valueOf(line.substring(0, 4)));
					 * tagDevice.setDevicePrefix(line.substring(0, 4));
					 * tagDevice.setSerialNo(Long.valueOf(line.substring(4, 14)));
					 * tagDevice.setItagTagStatus(Integer.valueOf(line.substring(14, 15)));
					 * tagDevice.setIagCodedClass(000);
					 * 
					 * tagDevice.setTagClass(line.substring(81,85));
					 * tagDevice.setTagType(line.substring(79,80));
					 * tagDevice.setTagAccntInfo(line.substring(15,21));
					 * tagDevice.setAccountTypeCd(line.substring(25,26));
					 * tagDevice.setAccountNo(line.substring(26,76));
					 * tagDevice.setTagProtocol(line.substring(76,79));
					 * tagDevice.setTagMount(line.substring(80,81));
					 */
					 
					tagDevice.setIsaway(true);
					tagDevice.setItagTagStatus(Integer.valueOf(line.substring(14, 15)));
					tagDevice.setDevicePrefix(line.substring(0, 4));
					tagDevice.setSerialNo(Long.valueOf(line.substring(4, 14)));
					tagDevice.setTagClass(line.substring(81,85));
					tagDevice.setTagType(line.substring(79,80));
					tagDevice.setAccountNo(line.substring(26,76));
					tagDevice.setAccountTypeCd(line.substring(25,26));
					tagDevice.setTagOwningAgency(Long.valueOf(line.substring(21, 25)));
					tagDevice.setTagProtocol(line.substring(76,79));
					tagDevice.setTagMount(line.substring(80,81));
					tagDevice.setTagAccntInfo(line.substring(15,21));
					 
					
					tagDevice.setMtaCtrlStr(buildTSControlString(tagDevice.getItagTagStatus()));
					tagDevice.setRioCtrlStr(buildTSControlString(invalid));
					sequenceCount++;
					//String record = buildFileSection(detailRecordProperties, tagDevice); //earlier vala
				    String record = buildFileSectionnew(detailRecordProperties, tagDevice,  c);
					awayContent.append(record);
				//	writeContentToFile(filewriter, record);
					
					switch(tagDevice.getItagTagStatus()) {
					case 1: 
						good++ ;
						break;
					case 2: 
						lbal++;
						break;
					case 3:
						zero++;
						break;
					case 4:
						invalid++;
						break;
					
					}
					
					
					updateRecordCounter(tagDevice);
					//sequenceCount++;
				}
				
				
				  itagHeaderInfoVO.setValidCount1(String.valueOf(good));
				  itagHeaderInfoVO.setLowCount2(String.valueOf(lbal));
				  itagHeaderInfoVO.setZeroCount(String.valueOf(zero));
				  itagHeaderInfoVO.setInvalidCount3(String.valueOf(invalid));
				 
				
				iagDao.insertIntotTagstatusStatistics(itagHeaderInfoVO, recordCountMap);
				fr.close();
				}
			catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return awayContent;
	}
	
	/**
	 * 
	 * @param header
	 * @return
	 */
	private ITAGHeaderInfoVO getHeaderForFile(String header) {
		ITAGHeaderInfoVO itagHeaderInfoVO = new ITAGHeaderInfoVO();
		itagHeaderInfoVO.setFileType(header.substring(0, 4));
		itagHeaderInfoVO.setFromAgencyId(header.substring(12, 16));
		itagHeaderInfoVO.setFileDate(validationUtils.getFormattedDate(header.substring(16,26).replace("-", ""),"yyyyMMdd"));
		itagHeaderInfoVO.setFileTime(header.substring(27, 35));
		itagHeaderInfoVO.setRecordCount(header.substring(36,46));
		itagHeaderInfoVO.setValidCount1(String.valueOf(0));
		/*
		 * itagHeaderInfoVO.setLowCount2(header.substring(37,45));
		 * itagHeaderInfoVO.setInvalidCount3(header.substring(45,53));
		 * itagHeaderInfoVO.setLostCount4(header.substring(53,61));
		 */
		itagHeaderInfoVO.setLowCount2(String.valueOf(0));
		itagHeaderInfoVO.setInvalidCount3(String.valueOf(0));
		itagHeaderInfoVO.setLostCount4(String.valueOf(0));
		
		
		return itagHeaderInfoVO;
	}
	
	/**
	 * 
	 * @param itagTagStatus
	 * @return
	 */
	private String buildTSControlString(int itagTagStatus) {
		String mtaTsControlString = null;
			switch (itagTagStatus) {
			case 1:
				mtaTsControlString = "000VC0";
				break;
			case 2:
				mtaTsControlString = "000MC0";
				break;	
			default:
				mtaTsControlString = "IW0RC0";
				break;
			}
		return mtaTsControlString;
	}
	
	/**
	 * Get latest file for each agency
	 * @param agency
	 * @return
	 * @throws IOException
	 */
	private File getLatestItagFile(Agency agency) throws IOException {
				//configVariable
			File latestItagFileFromTXFER = getLatestFileFromXFER(agency);
			File latestItagFileFromProcDir = getLatestFileFromPROC(agency);
			
			File latestFileForAgency = null ;
			
			if(latestItagFileFromTXFER != null && latestItagFileFromProcDir != null ) {
				log.info("Comapring both files {} and {} ", latestItagFileFromTXFER.getName(), latestItagFileFromProcDir.getName());
				latestFileForAgency = latestProcessedFile(latestItagFileFromTXFER, latestItagFileFromProcDir, agency);
				log.info("Latest file for agency: {} is: {}", latestFileForAgency, agency.getFilePrefix());
				
			if(latestFileForAgency.equals(latestItagFileFromProcDir)) {
				//checking if file already exists
				log.info("Checking if file {} already exists in TPMS.T_INTER_AGENCY_FILE_XFER",latestFileForAgency.getName());
				String ExistingFileName = iagDao.checkIfFileAlreadyExists(latestFileForAgency);
				
				if(ExistingFileName != null)
				{
					log.info("Updating timestamp for already existing file name....");
					iagDao.updateTSForExistingFileName(latestFileForAgency.getName());
				}
				else
				{
					log.info("Inserting file {} in TPMS.T_INTER_AGENCY_FILE_XFER", latestFileForAgency.getName());
					insertLAtestFileIntoDB(latestFileForAgency, agency);
				}
			}
			}else if (latestItagFileFromTXFER == null && latestItagFileFromProcDir == null) {
				log.info("Existing files not found in XFER table and Processed directory ..");
				latestFileForAgency = null;
			}else if (latestItagFileFromTXFER != null  && latestItagFileFromProcDir == null) {
				log.info("Only {} found in XFER table ..", latestItagFileFromTXFER.getName());
				latestFileForAgency = latestItagFileFromTXFER;
			}else if (latestItagFileFromTXFER == null && latestItagFileFromProcDir != null ) {
				log.info("Only {} found in PROC directory ..", latestItagFileFromProcDir.getName());
				latestFileForAgency = latestItagFileFromProcDir;
				
				//checking if file already exists
				log.info("Checking if file {} already exists in TPMS.T_INTER_AGENCY_FILE_XFER",latestFileForAgency.getName());
				String ExistingFileName = iagDao.checkIfFileAlreadyExists(latestFileForAgency);
				if(ExistingFileName != null)
				{
					log.info("Updating timestamp for already existing file name....");
					iagDao.updateTSForExistingFileName(latestFileForAgency.getName());
				}
				else
				{
					log.info("Inserting file {} in TPMS.T_INTER_AGENCY_FILE_XFER", latestFileForAgency.getName());
					insertLAtestFileIntoDB(latestFileForAgency, agency);
				}
			}
			log.info("Latest file for agency {} is {}",agency.getFilePrefix(),latestFileForAgency);
			
			return latestFileForAgency;
	}
	
	/**
	 * 
	 * @param latestItagFileFromTXFER
	 * @param latestItagFileFromProcDir
	 * @param agency
	 * @return
	 */
	private File latestProcessedFile(File latestItagFileFromTXFER, File latestItagFileFromProcDir, Agency agency) {
		
		String lastSuccTS = latestItagFileFromTXFER.getName().substring(5,19);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime lastSuccdateTime = timeZoneConv.currentTime().parse(lastSuccTS, formatter);
		
		String lastProcTS = latestItagFileFromProcDir.getName().substring(5,19);
		LocalDateTime lastProcdateTime = timeZoneConv.currentTime().parse(lastProcTS, formatter);
		
		if (lastProcdateTime.isAfter(lastSuccdateTime)) {
			log.info("File {} is latest ..", latestItagFileFromProcDir.getAbsoluteFile());
			return latestItagFileFromProcDir;
		} else {
			log.info("File {} is latest ..", latestItagFileFromTXFER.getAbsoluteFile());
			return latestItagFileFromTXFER;
		}
		
	}
	
	//to be called for each latest file
	private void insertLAtestFileIntoDB(File latestItagFileFromProcDir, Agency agency) {
		InterAgencyFileXferDto interAgencyFileXferDto = new InterAgencyFileXferDto();
		if(latestItagFileFromProcDir != null && latestItagFileFromProcDir.exists())
		{
			String fileName = latestItagFileFromProcDir.getName();
			interAgencyFileXferDto.setFileExtension(Constants.ITAG);
			interAgencyFileXferDto.setFromDevicePrefix(agency.getFilePrefix()); // home = N, loop for each agency latest
			interAgencyFileXferDto.setToDevicePrefix(Constants.HOME_AGENCY_ID); // MTA 008 use home = Y
			interAgencyFileXferDto.setFileDate(validationUtils.getFormattedDate(fileName.substring(5,13), "yyyyMMdd"));
			interAgencyFileXferDto.setFileTimeString(fileName.substring(13,19));
			interAgencyFileXferDto.setRecordCount(fileWriteOperation.getRecordCountFromFile(latestItagFileFromProcDir));
			interAgencyFileXferDto.setXferType(Constants.I); //IAG
			interAgencyFileXferDto.setProcessStatus(Constants.CMPL);   //Complete
			interAgencyFileXferDto.setUpdateTs(timeZoneConv.currentTime()); 
			interAgencyFileXferDto.setFileName(latestItagFileFromProcDir.getName()); 
			
			iagDao.insertIntoInterAgencyFileXFERTable(interAgencyFileXferDto);
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
			//value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));yyyyMMddHHmmss
			DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime lt= LocalDateTime.now(ZoneId.of("America/New_York"));
			if(recordCountMap.get(Constants.isRFK).equals("true")) {
				//value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat())).concat(Constants.RFK);
				value=lt.format(dft).toString().concat(Constants.RFK);
			}else {
				//value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
				value=lt.format(dft).toString();
			}
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
			value = doPadding(fMapping,fMapping.getDefaultValue());
			break;
		case Constants.H_HUB_ID_NAME:
			//value=Constants.H_HUB_ID;
			value=fMapping.getDefaultValue();
			break;
		case Constants.H_AGENCY_ID:
			value = fMapping.getDefaultValue();
			break;	
		/*
		 * case Constants.H_PLAZA_ID: value = fMapping.getDefaultValue(); break;
		 */
		case Constants.H_DOWNLOAD_TYPE:
			value = getFileGenType().equals(Constants.FILE_GEN_TYPE_FULL)?Constants.FILE_DWNL_COMPL:Constants.FILE_DWNL_INCR;
			break;
		case Constants.H_LAST_DOWNLOAD_TS:
			if(recordCountMap.get(Constants.isRFK).equals("true")) {
				
				String lastFileDownloadTS = iagDao.getLastDwnldTSRFK();
				value = lastFileDownloadTS;
			}else {
				String lastFileDownloadTS = iagDao.getLastDwnldTS();
				value = lastFileDownloadTS;
			}
			break;
		case Constants.H_CURRENT_TS:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;	
		case Constants.H_STOLEN_TAG_COUNT:
			log.info("Stolen count: {}",stolenCount);
			recordCountMap.put(Constants.STOLEN, doPadding(fMapping,String.valueOf(stolenCount)));
			//value = doPadding(fMapping, Long.toHexString(stolenCount)); 
			value = doPadding(fMapping, String.valueOf(stolenCount));
			break;	
		case Constants.H_LOST_TAG_COUNT:
			log.info("Lost count: {}",lostCount);
			recordCountMap.put(Constants.LOST, doPadding(fMapping,String.valueOf(lostCount)));
			//value = doPadding(fMapping, Long.toHexString(lostCount));
			value = doPadding(fMapping, String.valueOf(lostCount));
			break;
		case Constants.H_INVALID_TAG_COUNT:
			log.info("Invalid count: {}",invalidCount);
			recordCountMap.put(Constants.INVALID, doPadding(fMapping,String.valueOf(invalidCount)));
			//value = doPadding(fMapping, Long.toHexString(invalidCount));
			value = doPadding(fMapping,String.valueOf(invalidCount));
			break;	
		case Constants.H_INVENTORY_DEVICE_COUNT:
			log.info("Inventory count: {}",inventoryCount);
			recordCountMap.put(Constants.INVENTORY, doPadding(fMapping,String.valueOf(inventoryCount)));
			//value = doPadding(fMapping, Long.toHexString(inventoryCount));
			value = doPadding(fMapping, String.valueOf(inventoryCount));
			break; 
		case Constants.H_REPLENISH_TAG_COUNT:
			log.info("Negative balance count: {}",replenishCount);
			recordCountMap.put(Constants.REPLENISH, doPadding(fMapping,String.valueOf(replenishCount)));
			//value = doPadding(fMapping, Long.toHexString(replenishCount));
			value = doPadding(fMapping, String.valueOf(replenishCount));
			break;
		case Constants.H_MINUS_BAL_TAG_COUNT:
			log.info("Minimum balance count: {}",minCount);
			recordCountMap.put(Constants.MINIMUM, doPadding(fMapping,String.valueOf(minCount)));
			//value = doPadding(fMapping, Long.toHexString(minCount));
			value = doPadding(fMapping, String.valueOf(minCount));
			break;	
		case Constants.H_VALID_TAG_COUNT:
			log.info("Valid balance count: {}",validCount);
			recordCountMap.put(Constants.VALID, doPadding(fMapping,String.valueOf(validCount)));
			//value = doPadding(fMapping, Long.toHexString(validCount));
			value = doPadding(fMapping, String.valueOf(validCount));
			break;
		case Constants.H_RECORD_COUNT:
			log.info("Total record count: {}",totalRecords);
			recordCountMap.put(Constants.TOTAL, doPadding(fMapping,String.valueOf(totalRecords)));
			//value = doPadding(fMapping, Long.toHexString(totalRecords));
			value = doPadding(fMapping, String.valueOf(totalRecords));
			break;
		case Constants.H_PREV_FILE_NAME:
			if(recordCountMap.get(Constants.isRFK).equals("true")) {
				String prevfile= iagDao.getPrevfileRFK();
				value = prevfile;
			}else {
				String prevfile= iagDao.getPrevfile();
				value = prevfile;			
			}
			
			break;
		case Constants.D_RECORD_TYPE_START:
			//value = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			//defaultvalue_old=\u0002
			//value=Constants.REC_TYPE;
			value=fMapping.getDefaultValue();
			break;	
		case Constants.D_RECORD_NUM:
			//value = doPadding(fMapping, Long.toHexString(sequenceCount)); 
			value = doPadding(fMapping, String.valueOf(sequenceCount)); 
			break;
		case Constants.D_TAG_AGENCY_ID:
			value =  doPadding(fMapping, tagDevice.getDevicePrefix().substring(2,4)); 
			break;
		case Constants.D_SERIAL_NO:
			value = doPadding(fMapping, Long.toHexString(tagDevice.getSerialNo()));
			value = doPadding(fMapping, String.valueOf(tagDevice.getSerialNo()));
			break;
		case Constants.D_TAG_IAG_CLASS:
			//value = doPadding(fMapping, Long.toHexString(tagDevice.getIagCodedClass()));
			
			/*
			 * boolean tagclass =tagDevice.getTagClass().matches("\\*+"); if(tagclass) {
			 * value=""; } else { value=tagDevice.getTagClass(); }
			 */
			value=doPadding(fMapping, (tagDevice.getTagClass()));
			break;
		case Constants.D_REV__TYPE:			
			//value = fMapping.getDefaultValue();
			//value=null;
			//value="";
			value=doPadding(fMapping,fMapping.getDefaultValue());
			//value="R";
			break;
		case Constants.D_TAG_TYPE:
			/*
			 * boolean tagtype =tagDevice.getTagType().matches("\\*+"); if(tagtype) {
			 * value=""; }else { value=tagDevice.getTagType(); }
			 */
			value=doPadding(fMapping,tagDevice.getTagType());
			break;
		/*
		 * case Constants.D_CONTROL_INFORMATION: value = doPadding(fMapping,
		 * tagDevice.getMtaCtrlStr()); break;
		 */	
		case Constants.D_CI1_TAG_STATUS:
			if(recordCountMap.get(Constants.isRFK).equals("true")) {
				value=doPadding(fMapping,tagDevice.getRioCtrlStr().substring(0,1));
			}else {
				value=doPadding(fMapping, tagDevice.getMtaCtrlStr().substring(0,1));
			}
			
			break;
		case Constants.D_CI2_VES:
			value=doPadding(fMapping, tagDevice.getMtaCtrlStr().substring(1,2));
			break;
		case Constants.D_CI3_FUTURE_USE:
			value=doPadding(fMapping,fMapping.getDefaultValue());
			break;
		case Constants.D_CI4_ACCOUNT_DEVICE_STATUS:
			value=doPadding(fMapping,tagDevice.getMtaCtrlStr().substring(3,4));
			break;
		case Constants.D_CI5_DISCOUNT_PLAN_FLAG_ORT:
			value=doPadding(fMapping,tagDevice.getMtaCtrlStr().substring(4,5));
			break;
		case Constants.D_CI6_DISCOUNT_PLAN_FLAG_CBD:
			value=doPadding(fMapping,fMapping.getDefaultValue());
			break;
		case Constants.D_TAG_ACCOUNT_NO:
			/*
			 * boolean accntno=tagDevice.getAccountNo().matches("\\*+"); if(accntno) {
			 * value=""; }else { if(tagDevice.getItagTagStatus()==1) {
			 * value=doPadding(fMapping,tagDevice.getAccountNo()); }else { value=""; } }
			 */
			/*
			 * if(tagDevice.isIsaway()) {
			 * value=doPadding(fMapping,tagDevice.getAccountNo()); }else {
			 * if(tagDevice.getItagTagStatus()==Constants.TS_VALID) {
			 * value=doPadding(fMapping,tagDevice.getAccountNo()); }else {
			 * value=doPadding(fMapping,fMapping.getDefaultValue()); } }
			 */
			value=doPadding(fMapping,tagDevice.getAccountNo());
			break;
		case Constants.D_TAG_AC_TYPE_IND:
			/*
			 * boolean tagactypeind = tagDevice.getAccountTypeCd().matches("\\*+");
			 * if(tagactypeind) { value=""; }else { value=tagDevice.getAccountTypeCd(); }
			 */
		   value=doPadding(fMapping,tagDevice.getAccountTypeCd());
			break;
		case Constants.D_TAG_HOME_AGENCY:
			//int taghomeagency = tagDevice.getTagOwningAgency();
			//String taghomeagencyval= Integer.toString(taghomeagency);
			//value=doPadding(fMapping,taghomeagencyval);
			if (tagDevice.isIsaway()){
		      value=doPadding(fMapping,Long.toString(tagDevice.getTagOwningAgency()));
			}else {
				value=doPadding(fMapping, masterDataCache.getAgencyById(tagDevice.getTagOwningAgency()).getFilePrefix());
			}
			break;
		case Constants.D_TAG_PROTOCOL:
			/*
			 * boolean tagprotocol= tagDevice.getTagProtocol().matches("\\*+");
			 * if(tagprotocol) { value=""; }else {
			 * value=doPadding(fMapping,tagDevice.getTagProtocol()); } break;
			 */
			value=doPadding(fMapping,tagDevice.getTagProtocol());
			break;
		case Constants.D_TAG_MOUNT:
			/*
			 * boolean tagmount=tagDevice.getTagMount().matches("\\*+"); if(tagmount) {
			 * value=""; }else { value=tagDevice.getTagMount(); }
			 */
			value=doPadding(fMapping,tagDevice.getTagMount());
			break;
		case Constants.D_TAG_ACCT_INFO:
			
			
			  if(tagDevice.isIsaway()) {
				  String tagacctinfo = tagDevice.getTagAccntInfo();
			      value= doPadding(fMapping,tagacctinfo); 
			  }else {
				  //int itaginfo=tagDevice.getItagInfo(); 
				  //String acctitaginfo= Integer.toHexString(itaginfo); value=doPadding(fMapping,acctitaginfo); 
				  value=doPadding(fMapping,fMapping.getDefaultValue());
				  }
			break;
		case Constants.D_FILLER_FIELD_1:
			//value="";
			//value="F1";
			value=doPadding(fMapping,fMapping.getDefaultValue());
			break;
		case Constants.D_FILLER_FIELD_2:
			//value="";
			//value="F2";
			value=doPadding(fMapping,fMapping.getDefaultValue());
			break;
		/*
		 * case Constants.D_RECORD_TYPE_END: value = new
		 * String("\u0003".getBytes(StandardCharsets.US_ASCII)); break;
		 */
		case Constants.T_RECORD_TYPE:
			//value = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			//defaultvalue_old=\u0002
			//value=Constants.T_REC_TYPE;
			value=fMapping.getDefaultValue();
			break;	
		}
		log.info("Mapping done for the field: {} as value: {}",fMapping.getFieldName(), value);
		return value;
		
	}	
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		//tagDeviceObj.getItagTagStatus() == Constants.TS_LOST
		if (tagDeviceObj.getDeviceStatus() ==Constants.LOSTSTATUS) {
			lostCount++; // stolenCount++;
		}
		else if(tagDeviceObj.getDeviceStatus() == Constants.STOLENSTATUS) {
			stolenCount++;
		}
		//tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID
		else if (tagDeviceObj.getDeviceStatus() == Constants.DAMAGED || tagDeviceObj.getDeviceStatus() == Constants.RETURNED) { 
			invalidCount++;
		}
		else if (tagDeviceObj.getDeviceStatus() == Constants.DS_INVENTORY) {
			inventoryCount++;
		}
		//tagDeviceObj.getFinancialStatus() == Constants.FS_ZERO
		else if (tagDeviceObj.getItagTagStatus() == Constants.TAGREPLINISH) {
			replenishCount++;
		}
		//tagDeviceObj.getFinancialStatus() == Constants.FS_LOW || tagDeviceObj.getItagTagStatus() == Constants.TS_LOW
		else if(tagDeviceObj.getItagTagStatus() == Constants.TS_LOW ) {
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
		recordCountMap.put(Constants.TAG_SORTED_ALL, StringUtils.leftPad(String.valueOf(tagSortedAllRecords), Constants.SEVEN, Constants.DEFAULT_ZERO));
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
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
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
	public boolean overwriteFileHeader(java.io.File file, List<MappingInfoDto> headerProperties,CSVWriter c) {

		boolean headerUpdatedInFile = false;
				
		log.info("Building new header with latest record counts ..");
		String newLine = buildFileSection(headerProperties, null); // earlier vala
		String newline2 = buildFileSectionnew(headerProperties, null);
		//String newLine22= newline2.substring(0,140);//117
		String arrnewdr[] = null;
		String lastarry[]= {"E"};
		arrnewdr = newline2.split(",");
		
		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				//oldLine = sc.nextLine().substring(0,101); // earlier vala
				//oldLine =sc.nextLine().substring(0, 115);
				oldLine =sc.nextLine();
			}
			
			/*
			 * Scanner sc1 = new Scanner(new File(filePath)); while (sc1.hasNextLine()) {
			 * buffer.append(sc1.nextLine()); }
			 */
			//String fileContents = buffer.toString();
			
			
			//log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			//sc1.close();
			
			log.info("Replacing TAG file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newline2);
			
			
			//fileContents = fileContents.replaceAll(oldLine, newLine22);
			
			//FileWriter writer = new FileWriter(filePath);
			
			FileReader fileread = new FileReader(filePath);
			
			int cf=fileread.read();
			System.out.println(cf);
			
			CSVReader csvreader = new CSVReader(fileread);
			
			//FileWriter writer = new FileWriter(filePath,true);
			//CSVWriter c1 = new CSVWriter(writer);
			
			
			//String str1[]=null;
			//StringBuffer fileContents1= new StringBuffer();
				
		    //fileContents1=buffer;
				
			//fileContents1.append(" ");
			//str1=fileContents.split(" ");
			//CSVWriter c= new CSVWriter(writer);
		   // c.writeNext(str1,false);
			
			
			
			
			
			
			
			
            List<String[]> strrdfile = csvreader.readAll();
          
           // System.out.println("peint"+strrdfile);
      
		    String arrg[]=strrdfile.set(0, arrnewdr);
		    strrdfile.remove(strrdfile.size()-1);
		    //String atuyt[]= strrdfile.get(strrdfile.size()-1);
		    
		    //atuyt.toString().replaceAll("(\r\n | \r)", "");
		    
		   //strrdfile.set(strrdfile.size()-1,atuyt);
		    //strrdfile.toString().replaceAll("\r\n", "");
			/*
			 * for(String[] stf:strrdfile) {
			 * 
			 * stf[strrdfile.size()-1].replace("(\\r\\n | \\r )","");
			 * 
			 * }
			 */
		   // strrdfile.replaceAll("\r","");
			System.out.println(arrg);
			  
	
			//Object arrtary[]= new Object[strrdfile.size()]; 
	
			  
			
			//String []naya=Arrays.stream(arrtary).map(Object::toString).toArray(String[]::new);
			  
			  FileWriter writer = new FileWriter(filePath);
			  CSVWriter c1= new CSVWriter(writer);
			  
			  
			  
			  
			//c.writeNext(naya,false);
			c1.writeAll(strrdfile, false);
			
			FileWriter writer1 = new FileWriter(filePath,true);
			writer1.write("E".replaceAll("\r\n", ""));
			
			//csvreader.close();
			//c.close();
			c1.close();
			writer1.close();
			
			//log.debug("new data: {}", fileContents);
			//writer.append(fileContents);
			//writer.flush();
			//writer.close();
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
		if(value == null || value == "null" || value.isEmpty() || value.matches("\\*+")) {
			//str = fMapping.getDefaultValue();
			str="";
		}else if("L".equals(fMapping.getJustification())) {
			str = StringUtils.leftPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		}else if("R".equals(fMapping.getJustification())) {
			str = StringUtils.rightPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		}else if(value.length()==fMapping.getFieldLength()) {
			str=value;
		}
		return str;
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
		//if (fileCompletionStatus) 
		//{
			renamefile = new File(tagFileDest, tempFileName.replace("TEMP", fileType));
			//fileCompleted = fileWriteOperation.renameFile(file, renamefile);
		   String p =file.getAbsoluteFile().getPath();
		   String p2= renamefile.getAbsoluteFile().getPath();
		   Path p1 = Paths.get(p);
		   String f=renamefile.getAbsoluteFile().getName();
			try {
				Path pw= Files.move(p1,p1.resolveSibling(p2));
				
				
				boolean chekc= pw.endsWith(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		//else if(fileCompleted) 
		//{
			//log.info("File renamed to file:{}", renamefile.getAbsolutePath());
		//}
	//log.info("File renamed successfully: {}",fileCompleted);
	return fileCompleted;
		
	}
	
	/**
	 * 
	 * @param genType
	 * @throws InvalidFileGenerationTypeException
	 */
	@Override
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException {
		log.info("File generation type is: {} ", genType);
		if(genType.equals(Constants.FILE_GEN_TYPE_FULL) || genType.equals(Constants.FILE_GEN_TYPE_INCR)) {
			setFileGenType(genType);
			log.info("Valid file generation type: "+genType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file generation type: "+genType);
		}
	}

	@Override
	public void validateAgencyId(int agencyId) throws InvalidFileGenerationTypeException {
		// TODO Auto-generated method stub
		log.info("File agencyId {}", agencyId);
		if(agencyId==Constants.MTA_TS_AGENCY_ID) {
			log.info("Valid file agencyId: "+agencyId);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid file agencyId: "+agencyId);
		}
	}

	@Override
	public void validateFileType(String fileType) throws InvalidFileGenerationTypeException {
		// TODO Auto-generated method stub
		log.info("File fileType {}",fileType);
		if(fileType.equals(Constants.FILE_Type)) {
			log.info("Valid file Type {}"+fileType);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid filetype: "+fileType);
		}
		
	}

}
