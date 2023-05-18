package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
import com.conduent.tpms.iag.dao.FTAGIncrementDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileHeaderDto;
import com.conduent.tpms.iag.dto.FileNameDto;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.FTAGDevice;
import com.conduent.tpms.iag.service.FTAGIncrementService;
import com.conduent.tpms.iag.service.SummaryFileCreationService;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;

@Service
@Primary
public class FTAGIncrementServiceImpl implements FTAGIncrementService {

	private static final Logger log = LoggerFactory.getLogger(FTAGIncrementServiceImpl.class);

	@Autowired
	FTAGIncrementDao iagDao;

	@Autowired
	FileWriteOperation fileWriteOperation;

	@Autowired
	SummaryFileCreationService summaryFileCreationService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	public MasterDataCache masterDataCache;

	@Autowired
	FTAGIncrementServiceImpl ftagIncrementService;

	@Autowired
	public IAGValidationUtils validationUtils;

	@Autowired
	TimeZoneConv timeZoneConv;

	private FileCreationParameters fileCreationParameter = new FileCreationParameters();

	public FileCreationParameters getFileCreationParameter() {
		return fileCreationParameter;
	}

	public void setFileCreationParameter(FileCreationParameters fileCreationParameter) {
		this.fileCreationParameter = fileCreationParameter;
	}

	private FileCreationParameters configurationMapping;

	public FileCreationParameters getConfigurationMapping() {
		return configurationMapping;
	}

	public void setConfigurationMapping(FileCreationParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}

	public FileHeaderDto fileHeader = new FileHeaderDto();
	public FileNameDto fileName = new FileNameDto();
	protected List<FTAGDevice> tagDeviceList = new ArrayList<>();
	protected List<IncrTagStatusRecord> deviceNoList = new ArrayList<>();
	protected Map<String, String> recordCountMap = new HashMap<>();
	private boolean rowWrittenToFile = true;
	protected String ftagFileDest;
	protected String statFileDest;
	protected File file;

	protected long validCount = 0;
	protected long lowCount = 0;
	protected long invalidCount = 0;
	protected long lostCount = 0;
	protected long skipCount = 0;

	private String fileType;

	private String parentDest;

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
	public boolean createFtagFile(int agencyId, String fileFormat, String genType) {

		// ftagFileDest = configVariable.getFileDestPath();
		// statFileDest = configVariable.getStatFileDestPath();

		ftagFileDest = parentDest.concat("//").concat(Constants.IFGU_FILES); /// QaOKEFSS/QA/TOLLING/TPMS/PHASE2/IAG/PARENT_DIRECTORY/FTAG
		// FTAG_FILES
		statFileDest = parentDest.concat("//").concat(Constants.IFGU_STAT_FILES);

		boolean isFileWritten = false;

		log.info("ftagFileDest: {}", ftagFileDest);
		log.info("statFileDest: {}", statFileDest);

		try {
			// collecting device no from T_INCR_TAG_STATUS_ALLSORTED
			deviceNoList = iagDao.getDeviceNoFromTInrTagStatusAllSorted(genType);

			tagDeviceList = iagDao.getDeviceListFromTTagAllSorted(agencyId, genType, deviceNoList);
			recordCountMap.put(Constants.T_INCR_TAG_STATUS_ALLSORTED,
					validationUtils.customFormatString(Constants.DEFAULT_COUNT, tagDeviceList.size()));// important line

			if (!tagDeviceList.isEmpty()) {
				// Building file name
				String fileCreationTimestamp = timeZoneConv.currentTime()
						.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				fileName.setFromAgencyId(Constants.HOME_AGENCY_ID);
				fileName.setFileDateTime(fileCreationTimestamp);

				StringBuilder name = new StringBuilder();
				name.append(fileName.getFromAgencyId()).append(Constants.UNDERSCORE).append(fileName.getFileDateTime())
						.append(Constants.DOT).append(Constants.IFGU);
				log.info("File name: {}", name);

				// Setting file header DTO
				String prev_fileCreationTimestamp = iagDao.getLastDwnldTS();
				fileHeader.setFileType(Constants.IFGU);
				fileHeader.setFromAgencyId(Constants.HOME_AGENCY_ID);
				fileHeader.setFileDateTime(fileCreationTimestamp);
				fileHeader.setPrevfileDateTime(prev_fileCreationTimestamp);
				fileHeader.setRecordCount(Constants.DEFAULT_COUNT);
				fileHeader.setCountStat1(Constants.DEFAULT_COUNT);
				fileHeader.setCountStat2(Constants.DEFAULT_COUNT);
				fileHeader.setCountStat3(Constants.DEFAULT_COUNT);
				fileHeader.setCountStat4(Constants.DEFAULT_COUNT);

				// Building FTAG file header
				StringBuilder header = new StringBuilder();
				header.append(Constants.IFGU).append(fileHeader.getFromAgencyId()).append(fileHeader.getFileDateTime())
						.append(fileHeader.getPrevfileDateTime()).append(fileHeader.getRecordCount())
						.append(fileHeader.getCountStat1()).append(fileHeader.getCountStat2())
						.append(fileHeader.getCountStat3()).append(fileHeader.getCountStat4());

				// Write data to FTAG file
				isFileWritten = writeContentToFile(name.toString(), header.toString(), ftagFileDest, tagDeviceList);

				// Overwriting header of FTAG file
				if (isFileWritten) {
					overwriteFileHeader(fileHeader, file);
					summaryFileCreationService.buildSummaryFile(recordCountMap, Constants.IFGU, statFileDest,
							file.getName());

				}

			}
			log.info(tagDeviceList.toString());
		} finally {
			validCount = 0;
			lowCount = 0;
			invalidCount = 0;
			lostCount = 0;
			skipCount = 0;//
			deviceNoList.clear();
			tagDeviceList.clear();
		}
		return isFileWritten;

	}

	/**
	 * 
	 * @param fileName
	 * @param fileHeaderStr
	 * @param fileDestPath
	 * @param tagRecords
	 * @return true/false
	 * @throws IOException
	 */
	private boolean writeContentToFile(String fileName, String fileHeaderStr, String fileDestPath,
			List<FTAGDevice> tagRecords) {

		File file = new File(fileDestPath, fileName);
		/*
		 * try { if(file.createNewFile()) {
		 * System.out.println("File "+file.getName()+" got created"); return true; }else
		 * { System.out.println("File "+file.getName()+" creation failed" ); return
		 * false; } } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		boolean writtenFlag = false;

		/*
		 * if(file.exists()) {
		 * 
		 * }
		 */

		try (FileWriter filewriterFtag = new FileWriter(file, true)) {

			log.info("Writing header to FTAG file: {}", fileHeaderStr);
			filewriterFtag.write(fileHeaderStr + "\n");

			for (FTAGDevice FTAGDeviceObj : tagRecords) {
				writtenFlag = false;
				boolean isValidRecord = validateFtagRecord(FTAGDeviceObj);
				log.info("Record {} is valid? : {}", FTAGDeviceObj.toString(), isValidRecord);

				StringBuilder row = new StringBuilder();
				row.append(FTAGDeviceObj.getDeviceNo()).append(FTAGDeviceObj.getFtagTagStatus())
						.append(buildHexadecimalForFtagInfo(FTAGDeviceObj.getFtagInfo()));

				if (row.length() == Constants.IFGU_REC_LENGTH && isValidRecord) {
					filewriterFtag.write(row.toString() + "\n");
					writtenFlag = true;
					log.info("Record added to file: {}", row.toString());
				} else {
					skipCount++;
					log.info("Record not added to file: {}", FTAGDeviceObj.toString());
				}
				// File record counters
				if (writtenFlag) {
					if (FTAGDeviceObj.getFtagTagStatus() == Constants.TS_VALID) {
						validCount++;
					}
					if (FTAGDeviceObj.getFtagTagStatus() == Constants.TS_LOW) {
						lowCount++;
					}
					if (FTAGDeviceObj.getFtagTagStatus() == Constants.TS_INVALID) {
						invalidCount++;
					}
					if (FTAGDeviceObj.getFtagTagStatus() == Constants.TS_LOST) {
						lostCount++;
					}
				}
			}

			setFile(file);
			log.info("Total records skipped to write in FTAG file: {}", skipCount);
		} catch (FileNotFoundException e) {
			rowWrittenToFile = false;
			log.error("File does not exists in the directory: {}, Exception: {}", file.getName(), e);
			return false;
		} catch (IOException e) {
			rowWrittenToFile = false;
			log.error("File write operation failed: {}, Exception: {}", file.getName(), e);
			return false;
		} catch (Exception e) {
			rowWrittenToFile = false;
			log.error("Exception occurred:");
			e.printStackTrace();
		} finally {

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
		fileHeaderDto.setCountStat1(validationUtils.customFormatString(Constants.DEFAULT_COUNT, validCount));
		fileHeaderDto.setCountStat2(validationUtils.customFormatString(Constants.DEFAULT_COUNT, lowCount));
		fileHeaderDto.setCountStat3(validationUtils.customFormatString(Constants.DEFAULT_COUNT, invalidCount));
		fileHeaderDto.setCountStat4(validationUtils.customFormatString(Constants.DEFAULT_COUNT, lostCount));
		fileHeaderDto.setRecordCount(validationUtils.customFormatString(Constants.DEFAULT_COUNT,
				fileWriteOperation.getRecordCountFromFile(file.getName(), file.getAbsolutePath())));

		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(Constants.GOOD, fileHeaderDto.getCountStat1());
		recordCountMap.put(Constants.LOW_BAL, fileHeaderDto.getCountStat2());
		recordCountMap.put(Constants.INVALID, fileHeaderDto.getCountStat3());
		recordCountMap.put(Constants.LOST, fileHeaderDto.getCountStat4());
		recordCountMap.put(Constants.TOTAL, fileHeaderDto.getRecordCount());
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT, skipCount));

		// Building new header with latest record count in file
		StringBuilder newheader = new StringBuilder();
		newheader.append(Constants.IFGU).append(fileHeader.getFromAgencyId()).append(fileHeader.getFileDateTime())
				.append(fileHeader.getPrevfileDateTime()).append(fileHeader.getRecordCount())
				.append(fileHeader.getCountStat1()).append(fileHeader.getCountStat2())
				.append(fileHeader.getCountStat3()).append(fileHeader.getCountStat4());

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
			log.info("Replacing FTAG file {} header", file.getName());
			log.info("Old header: {}", oldLine);
			log.info("New header: {}", newLine);
			fileContents = fileContents.replaceAll(oldLine, newLine);//
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
	 * @param fTAGDeviceObj
	 * @return true/false
	 */
	private boolean validateFtagRecord(FTAGDevice fTAGDeviceObj) {
		boolean isValidRecord = false;
		// Validate device prefix
		if (masterDataCache.validateHomeDevice(fTAGDeviceObj.getDevicePrefix())) {
			if (validationUtils.onlyDigits(fTAGDeviceObj.getDeviceNo())) {
				if (validationUtils.validateTagStatus(fTAGDeviceObj.getFtagTagStatus())) {
					isValidRecord = true;
				}
			}
		}
		return isValidRecord;
	}

	/**
	 * 
	 * @param ftagInfo
	 * @return Hexadecimal string equivalent for ftagInfo
	 */
	private String buildHexadecimalForFtagInfo(int ftagInfo) {
		String hexaValue = Integer.toHexString(ftagInfo);
		return validationUtils.customFormatString(Constants.TAG_ACCOUNT_INFO, Long.valueOf(hexaValue));
	}

	/**
	 * * Start process of file creation based on file format.
	 * 
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean processFileCreation(int agencyId, String fileFormat, String genType) {
		try {
			validateFileEntitiesandAgencyId(genType, agencyId);
			loadOutputConfigurationMapping(agencyId, fileFormat, genType);
			parentDest = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			log.info("Start processing {} file crreation..", fileFormat);
			if (Constants.IFGU.equals(fileFormat)) {
				if (createFtagFile(agencyId, fileFormat, genType)) {
					return true;
				}
			} else {
				throw new InvalidFileFormatException("Invalid file format type: " + fileFormat);
			}
		} catch (InvalidFileGenerationTypeException e) {
			log.info("Exception Occured for invalid file generation type: {}", e);
			e.printStackTrace();

		} catch (InvalidFileFormatException e) {
			log.info("Exception Occured for invalid file format type: {}", e);
			e.printStackTrace();

		} catch (Exception e) {
			log.info("Exception Occured:{}", e);
			e.printStackTrace();

		}
		return false;

//		if(createFtagFile(agencyId, fileFormat, genType)==true) {
//			return true;
//		}else {
//			return false;
//		}
	}

	private void validateFileEntitiesandAgencyId(String genType, int agencyId)
			throws InvalidFileGenerationTypeException {

		if ((genType.equals(Constants.FILE_GEN_TYPE_INCR)) && agencyId == Constants.MTA_AGENCY_ID) {
			log.info("Valid file generation type : {} and agency id : {} : ", genType, agencyId);
		} else {
			throw new InvalidFileGenerationTypeException(
					"Invalid generation type " + genType + " or agency id " + agencyId);
		}

	}

	private void loadOutputConfigurationMapping(int agencyId, String fileType, String genType) {
		getFileCreationParameter().setFileType(fileType);
		getFileCreationParameter().setAgencyId(agencyId);
		getFileCreationParameter().setGenType(genType);
		setConfigurationMapping(iagDao.getMappingConfigurationDetails(getFileCreationParameter()));

	}

}
