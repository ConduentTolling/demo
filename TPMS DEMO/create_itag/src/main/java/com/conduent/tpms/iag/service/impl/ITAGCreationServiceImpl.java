package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.FileNameDto;
import com.conduent.tpms.iag.dto.ITAGICLPHeaderDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.ITAGDevice;
import com.conduent.tpms.iag.service.ITAGCreationService;
import com.conduent.tpms.iag.service.ITAGICLPCreationService;
import com.conduent.tpms.iag.service.SummaryFileCreationService;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;

/**
 * 
 * @author urvashic
 *
 */
@Service
@Primary
public class ITAGCreationServiceImpl implements ITAGCreationService{

	private static final Logger log = LoggerFactory.getLogger(ITAGCreationServiceImpl.class);

	@Autowired
	IAGDao iagDao;

	@Autowired
	FileWriteOperation fileWriteOperation;
	
	@Autowired
	ITAGICLPCreationService itagIclpCreationService;
	
	@Autowired
	SummaryFileCreationService summaryFileCreationService;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	public ConfigVariable configVariable;

	@Autowired
	public MasterDataCache masterDataCache;
	
	@Autowired
	ITAGCreationServiceImpl itagCreationService;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	public FileHeaderDto fileHeader = new FileHeaderDto();
	public FileNameDto fileName = new FileNameDto();
	protected List<ITAGDevice> tagDeviceList = new ArrayList<>();
	protected ITAGICLPHeaderDto itagiclpHeaderDto;
	protected File itagiclpfile;
	protected Map<String,String> recordCountMap = new HashMap<>();
	private boolean rowWrittenToFile = true;
	protected String itagFileDest;
	protected String itagIclpFileDest;
	protected String statFileDest;
	protected File file;
	
	protected long validCount = 0;
	protected long lowCount = 0;
	protected long invalidCount = 0;
	protected long lostCount = 0;
	protected long skipCount = 0;
	

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

	/**
	 * Initialize basic properties properties
	 */
	public void initialize() {
		rowWrittenToFile = true;
	}

	/**
	 * 
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	@Override
	public void createItagFile(int agencyId, String fileFormat, String genType) {

		itagFileDest = configVariable.getFileDestPath();
		itagIclpFileDest = configVariable.getItagiclpFileDestPath();
		statFileDest = configVariable.getStatFileDestPath();

		log.info("itagFileDest: {}", itagFileDest);
		log.info("itagIclpFileDest: {}", itagIclpFileDest);
		log.info("statFileDest: {}", statFileDest);
		
		try {
			tagDeviceList = iagDao.getDevieListFromTTagAllSorted(agencyId, genType);
			recordCountMap.put(Constants.TAG_SORTED_ALL,
					validationUtils.customFormatString(Constants.DEFAULT_COUNT, tagDeviceList.size()));
			if (!tagDeviceList.isEmpty()) {
				// Building file name
				String fileCreationTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
				String fileNameTimestamp = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				
				//String fileCreationTimestamp = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ").format(timeZoneConv.currentTime());
				fileName.setFromAgencyId(Constants.HOME_AGENCY_ID);
				fileName.setFileDateTime(fileNameTimestamp);

				StringBuilder name = new StringBuilder();
				name.append(fileName.getFromAgencyId()).append(Constants.UNDERSCORE).append(fileName.getFileDateTime())
						.append(Constants.DOT).append(Constants.ITAG);
				log.info("File name: {}", name);

				// Setting file header DTO
				fileHeader.setFileType(Constants.ITAG);
				fileHeader.setVersion(Constants.VERSION);
				fileHeader.setFromAgencyId(Constants.HOME_AGENCY_ID);
				fileHeader.setFileDateTime(fileCreationTimestamp);
				fileHeader.setRecordCount(Constants.DEFAULT_COUNT);

				
				
				//Building ITAG file header
				StringBuilder header = new StringBuilder();
				header.append(Constants.ITAG).append(fileHeader.getVersion()).append(fileHeader.getFromAgencyId())
						.append(fileHeader.getFileDateTime()).append(fileHeader.getRecordCount());

				//Write data to ITAG file
				boolean isFileWritten = writeContentToFile(name.toString(), header.toString(), itagFileDest,
						tagDeviceList);

				// Overwriting header of ITAG file
				if (isFileWritten) {
					//check why not record updating in header
					overwriteFileHeader(fileHeader, file);
					itagIclpCreationService.overwriteFileHeader(itagiclpHeaderDto, itagiclpfile);
					summaryFileCreationService.buildSummaryFile(recordCountMap, Constants.ITAG, statFileDest);

				}
			}
			log.info(tagDeviceList.toString());
		} finally {
			validCount = 0;
			lowCount = 0;
			invalidCount = 0;
			lostCount = 0;
			skipCount = 0;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param fileHeaderStr
	 * @param fileDestPath
	 * @param tagRecords
	 * @return true/false
	 */
	private boolean writeContentToFile(String fileName, String fileHeaderStr, String fileDestPath,
			List<ITAGDevice> tagRecords) {

		File file = new File(fileDestPath, fileName);
		boolean writtenFlag = false;

		String tmpFile = fileName.substring(Constants.ZERO,Constants.ITAG_FILE_DATE_END).concat(Constants.DOT).concat(Constants.ITAGICLP);
		itagiclpfile = new File(itagIclpFileDest, tmpFile);
		
		itagiclpHeaderDto = new ITAGICLPHeaderDto();
		itagiclpHeaderDto.setAgencyId(Constants.HOME_AGENCY_ID);
		itagiclpHeaderDto.setFileDateTime(fileName.substring(4,Constants.ITAG_FILE_DATE_END));
		String itagiclpHeader = Constants.ITAGICLP.concat(Constants.HOME_AGENCY_ID).concat(Constants.DEFAULT_COUNT).concat(fileName.substring(Constants.ITAG_FILE_DATE_START,Constants.ITAG_FILE_DATE_END));
		
		
		try (FileWriter filewriterItag = new FileWriter(file, true); FileWriter filewriterItagIclp = new FileWriter(itagiclpfile, true)) {

			log.info("Writing header to ITAG file: {}",fileHeaderStr);
			filewriterItag.write(fileHeaderStr + "\n");
			
			log.info("Writing header to ITAGICLP file: {}",itagiclpHeader);
			filewriterItagIclp.write(itagiclpHeader + "\n");
			
			for (ITAGDevice ITAGDeviceObj : tagRecords) {
				writtenFlag = false;
				boolean isValidRecord = validateItagRecord(ITAGDeviceObj);
				log.info("Record {} is valid? : {}",ITAGDeviceObj.toString(),isValidRecord);
				try {
				StringBuilder row = new StringBuilder();
				row.append(addPadding(ITAGDeviceObj.getDevicePrefix(),Constants.ITAG_AGENCY_ID_LENGTH))
								.append(addPadding(ITAGDeviceObj.getSerialNo(),Constants.ITAG_SERIAL_NO_LENGTH))
								.append(ITAGDeviceObj.getItagTagStatus())
								.append(buildHexadecimalForItagInfo(ITAGDeviceObj.getItagInfo()))
								.append(addPadding(masterDataCache.getAgencyById(ITAGDeviceObj.getTagOwningAgency()).getFilePrefix(),Constants.ITAG_AGENCY_ID_LENGTH))//what should be this
								.append(ITAGDeviceObj.getAccountTypeCd())
								.append(addPadding(ITAGDeviceObj.getAccountNo(),Constants.ITAG_ACCOUNT_NO_LENGTH))
								.append(addPadding(ITAGDeviceObj.getTagProtocol(),Constants.ITAG_PROTOCOL))
								.append(ITAGDeviceObj.getTagType())
								.append(ITAGDeviceObj.getTagMount())
								.append(addPadding(ITAGDeviceObj.getTagClass(),Constants.ITAG_CLASS));
				
				
				log.info("row to be appended length{}", row.toString().length());
				
				if (row.length() == Constants.ITAG_REC_LENGTH && isValidRecord) {
					filewriterItag.write(row.toString() + "\n");
					writtenFlag = true;
					log.info("Record added to file: {}", row.toString());
				} else {
					skipCount++;
					log.info("Record not added to file: {}", ITAGDeviceObj.toString());
				}
				// File record counters
				if (writtenFlag) {
					if (ITAGDeviceObj.getItagTagStatus() == Constants.TS_VALID) {
						validCount++;
					}else if (ITAGDeviceObj.getItagTagStatus() == Constants.TS_LOW) {
						lowCount++;
					}else {
						invalidCount++;
					}
					/*
					 * // (ITAGDeviceObj.getItagTagStatus() == Constants.TS_INVALID) 
					 * if (ITAGDeviceObj.getItagTagStatus() == Constants.TS_LOST) { lostCount++; }
					 */
					log.info("Write record to ITAGICLP file..");
					itagIclpCreationService.writeContentToItagIclpFile(ITAGDeviceObj, itagiclpfile, filewriterItagIclp);
				}
				}catch (Exception e) {
					skipCount++;
					log.info("Record not added to file: {}", ITAGDeviceObj.toString());
					log.error("Record not added to file: Exception occurred:"+e.getMessage());
				}
			}
			log.info("Closing file writer for ITAGICLP file..");
			filewriterItag.flush();
			filewriterItag.close();
			setFile(file);
			log.info("Total records skipped to write in ITAG file: {}", skipCount);
		} catch (FileNotFoundException e) {
			rowWrittenToFile = false;
			log.error("File does not exists in the directory: {}, Exception: {}", file.getName(), e);
			return false;
		} catch (IOException e) {
			rowWrittenToFile = false;
			log.error("File write operation failed: {}, Exception: {}", file.getName(), e);
			return false;
		} catch (Exception e) {
			log.error("Exception occurred:"+e.getMessage());		
		} 
		return rowWrittenToFile;
	}

	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @return
	 */
	private boolean overwriteFileHeader(FileHeaderDto fileHeaderDto, File file) {
		log.info("Setting counters to fileHeaderDto..");
		fileHeaderDto.setRecordCount(validationUtils.customFormatString(Constants.DEFAULT_COUNT,
				fileWriteOperation.getRecordCountFromFile(file.getName(), file.getAbsolutePath())));
		
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(Constants.GOOD, fileHeaderDto.getCountStat1());
		recordCountMap.put(Constants.LOW_BAL, fileHeaderDto.getCountStat2());
		recordCountMap.put(Constants.INVALID, fileHeaderDto.getCountStat3());
		recordCountMap.put(Constants.LOST, fileHeaderDto.getCountStat4());
		recordCountMap.put(Constants.TOTAL, fileHeaderDto.getRecordCount());
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
		
		//Building new header with latest record count in file
		StringBuilder newheader = new StringBuilder();
		newheader.append(Constants.ITAG).append(fileHeader.getVersion()).append(fileHeader.getFromAgencyId())
		.append(fileHeader.getFileDateTime()).append(fileHeader.getRecordCount());


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
			String newLine = newheader.toString();
			log.info("Replacing ITAG file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newLine);
			fileContents = fileContents.replaceAll(oldLine, newLine);
			FileWriter writer = new FileWriter(filePath);
			log.debug("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowWrittenToFile;
	}

	/**
	 * 
	 * @param iTAGDeviceObj
	 * @return true/false
	 */
	private boolean validateItagRecord(ITAGDevice iTAGDeviceObj) {
		boolean isValidRecord = false;
		// Validate device prefix
		try {
			if (masterDataCache.validateHomeDevice(addPadding(iTAGDeviceObj.getDevicePrefix(),Constants.ITAG_AGENCY_ID_LENGTH))) {
				if (validationUtils.onlyDigits(iTAGDeviceObj.getDeviceNo())) {
					if (validationUtils.validateTagStatus(iTAGDeviceObj.getItagTagStatus())) {
						isValidRecord = true;
					}
				}
			}
		} catch (InvalidFileFormatException e) {
			log.error("invalid File Format "+e.getMessage());
		}
		return isValidRecord;
	}
	
	/**
	 * 
	 * @param itagInfo
	 * @return Hexadecimal string equivalent for itagInfo
	 * @throws InvalidFileFormatException 
	 */
	private String buildHexadecimalForItagInfo(int itagInfo) throws InvalidFileFormatException {
		String hexaValue = Integer.toHexString(itagInfo); 
		/*
		if(hexaValue.length()==6) {
			return hexaValue; 
		}else {
			throw new InvalidFileFormatException("Invalid itagInfo: "+itagInfo+":hexaValue:"+
					hexaValue); 
		}
		*/
		if(itagInfo==0) {
			return "000000";
		}
		
		return validationUtils.customFormatString(Constants.TAG_ACCOUNT_INFO, Long.valueOf(hexaValue));
	}
	
	private String addPadding(String accountNo, int length) throws InvalidFileFormatException {
		//Added padding
		if(accountNo.length()>length) {
			return accountNo.substring(0, length-1);
		}else if(accountNo.length()==length){
			return accountNo;
		}else {
			return String.format("%0"+(length-accountNo.length())+"d%s", 0, accountNo);
		}

	}

	
	/**
	 * * Start process of file creation based on file format.
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 * @return 
	 */
	@Override
	public String processFileCreation(int agencyId, String fileFormat, String genType) {
		try {
			validateFileEntitiesandAgencyId(genType, agencyId);
			log.info("Start processing {} file crreation..", fileFormat);
			if(Constants.ITAG.equals(fileFormat)) {
				createItagFile(agencyId, fileFormat, genType);
				return "Job executed";
			}else {
				throw new InvalidFileFormatException("Invalid file format type: "+fileFormat);
			}
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


	private void validateFileEntitiesandAgencyId(String genType , int agencyId) throws InvalidFileGenerationTypeException {
		
		if((genType.equals(Constants.FILE_GEN_TYPE_FULL) || genType.equals(Constants.FILE_GEN_TYPE_INCR)) 
				&& agencyId == Constants.MTA_AGENCY_ID ) {
			log.info("Valid file generation type : {} and agency id : {} : ",genType,agencyId);
		}else {
			throw new InvalidFileGenerationTypeException("Invalid generation type "+genType+" or agency id "+agencyId);
		}
		
	}

}
