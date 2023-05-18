package com.conduent.tpms.iag.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.ITAGHeaderInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.MtagFileHeaderDto;
import com.conduent.tpms.iag.model.Agency;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.MTAGFileCreationSevice;
import com.conduent.tpms.iag.service.SummaryFileCreationService;


/**
 * 
 * MTAG Implementation for IAG file creation process
 * 
 * @author urvashic
 *
 */

@Service
public class MTAGFileCreationServiceImpl extends MTAGFileCreationSevice {
	private static final Logger log = LoggerFactory.getLogger(MTAGFileCreationServiceImpl.class);

	String tagFileDest;
	String statFileDest;
	
	MtagFileHeaderDto mtagFileHeaderDto = new MtagFileHeaderDto();
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	SummaryFileCreationService summaryFileCreationService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	File file;
	private long tagSortedAllRecords = 0;

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
	
	@Override
	public void initialize() {
		log.info("Initializing MTAG file properties..");
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
	}
	
	@Override
	public void createTagFile(List<TAGDevice> tagDeviceList) throws IOException {
		try {
			log.info("Starting TAGN file creation process..");
			tagFileDest = parentDest.concat("//").concat(Constants.TAG_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			
			//tagFileDest = "D:\\directory\\ParentDirectory\\TAG_FILES"; // for LOCAL DO NOT COMMIT 
			//statFileDest = "D:\\directory\\ParentDirectory\\STAT_FILES";  // for LOCAL DO NOT COMMIT 

			log.info("Processing device list.. ");
			if (!tagDeviceList.isEmpty()) {
				
				log.info("Total records fethced from DB : {}",tagDeviceList.size());
				tagSortedAllRecords = tagSortedAllRecords+tagDeviceList.size();
				log.info("Total record Counter updated to:{}",tagSortedAllRecords);
				
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				log.info("Fetched list of Name mapping properties..");
				log.debug("Name mapping properties:{}",nameProperties.toString());
				
				fileName = buildFileSection(nameProperties, null);
				log.info("Built file name as {}",fileName);
				

				String tempFilename = fileName.replace("TAGN","TEMP");			
				File file = createNewFile(tempFilename, tagFileDest);
				
				log.info("Built temporary tag status file as {}",file.getAbsolutePath());
				FileWriter filewriter = new FileWriter(file, true); 
				
				log.info("Populating TS file name {} for Statistics..",fileName);
				recordCountMap.put(Constants.FILENAME, fileName); 
				recordCountMap.put(Constants.FILEDATE, fileName.substring(0,8));
				recordCountMap.put(Constants.DEVICE_PREFIX, Constants.HOME_AGENCY_ID); 
				
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				log.info("Fetched list of Header mapping properties..");
				log.debug("Header mapping properties:{}",headerProperties.toString());
				String header = buildFileSection(headerProperties, null);
				log.info("Built header: {}",header);

				if(header != null) {
					writeContentToFile(filewriter, header);
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
						log.info(tagDevice.toString());
						String record = buildFileSection(detailRecordProperties, tagDevice);	
						writeContentToFile(filewriter, record);
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
						buildAwayTagRecordFromITAGFileAndWriteTS(awaytagFile, filewriter, detailRecordProperties); //rename n get header
					}
				}
				log.info("Processing trailer for file..");
				if(isTrailerPresentInFile()) {
					List<MappingInfoDto> trailerProperties = getConfigurationMapping().getTrailerMappingInfoDto();
					String trailer = buildFileSection(trailerProperties, null);
					writeContentToFileFooter(filewriter, trailer);
					//writeContentToFile(filewriter, trailer);
					log.info("Added trailer {} to the file..",trailer);
				}
				
					filewriter.flush();
					filewriter.close();
					overwriteFileHeader(file, headerProperties);

					fileRenameOperation(tempFilename, tagFileDest, true, Constants.TAGN);
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFile(recordCountMap, Constants.TAGN, statFileDest);
			}
			log.info(tagDeviceList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			log.info("File creation completed:{}",fileName);
			tagSortedAllRecords = 0;
			goodCountNysta = 0;
			lowCountNysta = 0;
			zeroCountNysta = 0;
			lostCountNysta = 0;
			
			goodCountNysba = 0;
			lowCountNysba = 0;
			zeroCountNysba = 0;
			lostCountNysba = 0;
			skipCount = 0;
			totalRecords = 0;
		}
	}
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_GOOD) {
			goodCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOW) {
			lowCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_ZERO) {
			zeroCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOST) {
			lostCountNysta++;
		}
		if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_GOOD) {
			goodCountNysba++;
		}
		if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_LOW) {
			lowCountNysba++;
			
		}if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_ZERO) {
			zeroCountNysba++;
			
		}if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_LOST) {
			lostCountNysba++;
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
		// Validate device prefix

		if (masterDataCache.validateHomeDevice(StringUtils.leftPad(tagDeviceObj.getDevicePrefix(), 4, "0"))) {
			if (validationUtils.onlyDigits(tagDeviceObj.getDeviceNo())) {
				if (validationUtils.validateTagStatus(tagDeviceObj.getNystaTagStatus())) {
					if (validationUtils.validateTagStatus(tagDeviceObj.getNysbaTagStatus())) {
						isValidRecord = true;
					}
				}
			}
		}
		return isValidRecord;
	}

	/**
	 * Get latest file for each agency
	 * @param agency
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private File getLatestItagFile(Agency agency) throws IOException {
				//configVariable
			File latestItagFileFromTXFER = getLatestFileFromXFER(agency);
			File latestItagFileFromProcDir = getLatestFileFromPROC(agency);
			
			File latestFileForAgency = null ;
			
			if(latestItagFileFromTXFER != null && latestItagFileFromProcDir != null ) {
				log.info("Comapring both files {} and {} ", latestItagFileFromTXFER.getName(), latestItagFileFromProcDir.getName());
				latestFileForAgency = latestProcessedFile(latestItagFileFromTXFER, latestItagFileFromProcDir, agency);
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
						insertLatestFileIntoDB(latestFileForAgency, agency);
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
					insertLatestFileIntoDB(latestFileForAgency, agency);
				}
			}
			
			log.info("Latest file for agency {} is {}",agency.getFilePrefix(),latestFileForAgency);
			
			return latestFileForAgency;
	}
	
	//to be called for each latest file
	private void insertLatestFileIntoDB(File latestItagFileFromProcDir, Agency agency) {
		InterAgencyFileXferDto interAgencyFileXferDto = new InterAgencyFileXferDto();
		if(latestItagFileFromProcDir != null)
		{
			try {
			String fileName = latestItagFileFromProcDir.getName();
			interAgencyFileXferDto.setFileExtension(Constants.ITAG);
			interAgencyFileXferDto.setFromDevicePrefix(agency.getFilePrefix()); // home = N, loop for each agency latest
			interAgencyFileXferDto.setToDevicePrefix((Constants.HOME_AGENCY_ID)); // MTA 008 use home = Y
			interAgencyFileXferDto.setFileDate(validationUtils.getFormattedDate(fileName.substring(5,13), "yyyyMMdd"));
			interAgencyFileXferDto.setFileTimeString(fileName.substring(12-14)+":"+fileName.substring(14-16)+":"+fileName.substring(16-18));
			interAgencyFileXferDto.setRecordCount(fileWriteOperation.getRecordCountFromFile(latestItagFileFromProcDir));
			interAgencyFileXferDto.setXferType(Constants.I); //IAG
			interAgencyFileXferDto.setProcessStatus(Constants.CMPL);   //Complete
			interAgencyFileXferDto.setUpdateTs(timeZoneConv.currentTime()); 
			interAgencyFileXferDto.setFileName(latestItagFileFromProcDir.getName()); 
			
			iagDao.insertIntoInterAgencyFileXFERTable(interAgencyFileXferDto);
			}catch(Exception ex) {
				log.info("Exception in insertLatestFileIntoDB {}",ex.getMessage());
				ex.printStackTrace();
			}
		}
		
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
	
	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(Constants.TAG_SORTED_ALL, StringUtils.leftPad(String.valueOf(tagSortedAllRecords), Constants.EIGHT, Constants.DEFAULT_ZERO));
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
		// updating count for summary file
		// NYSTA
		recordCountMap.put(Constants.GOOD, validationUtils.customFormatString(Constants.DEFAULT_COUNT, goodCountNysta));
		recordCountMap.put(Constants.LOW_BAL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lowCountNysta));
		recordCountMap.put(Constants.INVALID, validationUtils.customFormatString(Constants.DEFAULT_COUNT, zeroCountNysta));
		recordCountMap.put(Constants.LOST, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lostCountNysta));
		// NYSBA
		recordCountMap.put(Constants.NYSBA_GOOD, validationUtils.customFormatString(Constants.DEFAULT_COUNT, goodCountNysba));
		recordCountMap.put(Constants.NYSBA_LOW, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lowCountNysba));
		recordCountMap.put(Constants.NYSBA_ZERO, validationUtils.customFormatString(Constants.DEFAULT_COUNT, zeroCountNysba));
		recordCountMap.put(Constants.NYSBA_LOST, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lostCountNysba));
	}
	
	/**
	 * 
	 * @param awayFile
	 * @param filewriter
	 * @param detailRecordProperties
	 * @return
	 */
	private boolean buildAwayTagRecordFromITAGFileAndWriteTS(File awayFile, FileWriter filewriter, List<MappingInfoDto> detailRecordProperties) {
		
		if(awayFile != null) {
			long count = fileWriteOperation.getRecordCountFromFile(awayFile);
			log.info("Total records in tag file {} are {}",awayFile, count);
			tagSortedAllRecords = tagSortedAllRecords + count; 
			try {
				FileReader fr = new FileReader(awayFile);
				BufferedReader br = new BufferedReader(fr);
				String line;
	
				String header = br.readLine(); //consume first line and ignore
				ITAGHeaderInfoVO itagHeaderInfoVO = getHeaderForFile(header);
				itagHeaderInfoVO.setMergeFileName(awayFile.getName());
				Map<String,String> recordCountMapITAGFile = new HashMap<>();
				int goodCountFile = 0;
				int lowCountFile = 0;
				int zeroCountFile = 0;
				int lostCountFile = 0;
				int totalCountFile = 0;
				while((line = br.readLine()) != null)
				{
					
					TAGDevice tagDevice = new TAGDevice();
					tagDevice.setAgencyId(Integer.valueOf(line.substring(0, 4))); 
					tagDevice.setSerialNo(line.substring(4, 14));
					tagDevice.setDevicePrefix(line.substring(0, 4));
					tagDevice.setNystaTagStatus(Integer.valueOf(line.substring(14, 15)));
					tagDevice.setNysbaTagStatus(Integer.valueOf(line.substring(14, 15)));
					tagDevice.setMtagPlanStr(buildPlanSting(line.substring(15, 21)));
					tagDevice.setTagOwningAgency(Integer.valueOf(line.substring(21, 25))); 
					
					//Integer acctTypeId = Optional.ofNullable(masterDataCache.getCodeByDisplayVal(line.substring(25, 26)).getCodeId()).orElse(0);
					Integer acctTypeId = 0;
					try {
						/*
						if(Private): 01   B consider else 0
						if(Commercial): 02
						if(Business): 03
						But we are getting in itag file as below from load all sorted Query SELECT_ETC_ACC_INFO from t_codes
						'TAG_AC_TYPE_IND',1,'BUSINESS','B'
						'TAG_AC_TYPE_IND',2,'PRIVATE','P'
						'TAG_AC_TYPE_IND',4,'COMMERCIAL','B'
						//acctTypeId = masterDataCache.getCodeByDisplayVal(line.substring(25, 26)).getCodeId(); //Correct Logic
						*/
						String accTypeCd = line.substring(25, 26);
						if(accTypeCd.equalsIgnoreCase("P")) {
							acctTypeId = 1;
						}else if(accTypeCd.equalsIgnoreCase("B")) {
							acctTypeId = 3;
						}else {
							acctTypeId = 0;
						}
						
					}catch (NullPointerException e) {
						log.info("Code not available in table for : {}", line.substring(25, 26));
						acctTypeId = 0;
					}
					log.info("acctTypeId: {}", acctTypeId);
					tagDevice.setAccountType(acctTypeId.toString());
					//
					
					int statusValITAGFile = Integer.valueOf(line.substring(14, 15));
					if (statusValITAGFile == Constants.NTS_GOOD) {
						goodCountFile++;
					}
					if (statusValITAGFile == Constants.NTS_LOW) {
						lowCountFile++;
					}
					if (statusValITAGFile == Constants.NTS_ZERO) {
						zeroCountFile++;
					}
					if (statusValITAGFile == Constants.NTS_LOST) {
						lostCountFile++;
					}
					
					if(statusValITAGFile > 0 && statusValITAGFile < 4) {
					String record = buildFileSection(detailRecordProperties, tagDevice);	
					writeContentToFile(filewriter, record);
					updateRecordCounter(tagDevice);
					totalCountFile++;
					}else {
						skipCount++;
						log.info("Record not added Status not matching: {}",tagDevice.getNysbaTagStatus());
						log.info("Record not added to file: {}", tagDevice.toString());
					}
				}
				
				recordCountMapITAGFile.put(Constants.GOOD, validationUtils.customFormatString(Constants.DEFAULT_COUNT, goodCountFile));
				recordCountMapITAGFile.put(Constants.LOW_BAL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lowCountFile));
				recordCountMapITAGFile.put(Constants.ZERO, validationUtils.customFormatString(Constants.DEFAULT_COUNT, zeroCountFile));
				recordCountMapITAGFile.put(Constants.LOST, validationUtils.customFormatString(Constants.DEFAULT_COUNT, lostCountFile));
				recordCountMapITAGFile.put(Constants.PROCESSED, validationUtils.customFormatString(Constants.DEFAULT_COUNT, totalCountFile));
		
				
				iagDao.insertIntotTagstatusStatistics(itagHeaderInfoVO, recordCountMap, recordCountMapITAGFile);
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param tagAcctInfo
	 * @return
	 */
	private int buildPlanSting(String tagAcctInfo) {
	System.out.println(tagAcctInfo);
	int plan_str = Integer.parseInt(Integer.toHexString(Integer.valueOf(tagAcctInfo)),16);
	int mtag_plan_str = 0;
	if ((plan_str & 32) > 0) //need boolean condition here
	{
	mtag_plan_str = mtag_plan_str + 16*16*8;
	}
	if ((plan_str & 64) > 0)  //need boolean condition here
	{
	mtag_plan_str = mtag_plan_str + 16*8;
	}
	if ((plan_str & 128) > 0)
	{
		mtag_plan_str = mtag_plan_str + 16*16*1;
	}
	if ((plan_str & 256) > 0)
	{
		mtag_plan_str = mtag_plan_str + 16*16*2;
	}
	
	String hexaString = StringUtils.leftPad(Integer.toHexString(mtag_plan_str), 8, "0"); //hexastring = mtag_plan_str [8char hex]

	if ("00000000".equals(hexaString))
	{
	return Integer.parseInt(hexaString,16);
	}
	else
	return Integer.parseInt(hexaString,16);
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
		String dateVal = header.substring(16,20) + header.substring(21,23) + header.substring(24,26);
		itagHeaderInfoVO.setFileDate(validationUtils.getFormattedDate(dateVal,"yyyyMMdd"));
		itagHeaderInfoVO.setFileTime(header.substring(28,36)); 
		itagHeaderInfoVO.setRecordCount(header.substring(36,46));
		//itagHeaderInfoVO.setValidCount1(header.substring(29,37));
		//itagHeaderInfoVO.setLowCount2(header.substring(37,45));
		//itagHeaderInfoVO.setInvalidCount3(header.substring(45,53));
		//itagHeaderInfoVO.setLostCount4(header.substring(53,61));
		log.info("itagHeaderInfoVO:{}",itagHeaderInfoVO.toString());
		return itagHeaderInfoVO;
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
			log.info("Rename file {} to type {} at location {}",tempFileName,fileType, tagFileDest);
			File file = new File(tagFileDest, tempFileName);
			if (fileCompletionStatus) {
				File renamefile = new File(tagFileDest, tempFileName.replace("TEMP", fileType));
				log.info("Renaming file {} as new file {}",tagFileDest, renamefile);
				fileCompleted = fileWriteOperation.renameFile(file, renamefile);
				/*//code for GZIP 
				try {
					compressGzip(renamefile.getPath());
				} catch (IOException e) {
					log.info("Exception while GZIP file: ");
					fileCompleted = false;
					e.printStackTrace();
				}
				*/
			}
			if(!fileCompleted) {
			log.info("Exception while renaming the temp file: ");
			throw new FileNotFoundException();
			}
		log.info("File reenamed successfully: {}",fileCompleted);
		return fileCompleted;
	}
	

	 public boolean compressGzip(String tagFileDest) throws IOException {
		boolean fileZipped = false;
		 Path target = Paths.get(tagFileDest);
		 Path source = Paths.get(tagFileDest);
		log.info("GZIP file  at location {}", tagFileDest);
	        try (GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(target.toFile()));
	             FileInputStream fis = new FileInputStream(source.toFile())) {

	            // copy file
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = fis.read(buffer)) > 0) {
	                gos.write(buffer, 0, len);
	            }

	        }
	        
	        if(!fileZipped) {
				log.info("Exception while GZIP file: ");
				throw new FileNotFoundException();
				}
	        
	        return fileZipped;

	    }
	
	@Override
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		String value = "";
		log.debug("Mapping the field: {}",fMapping.getFieldName());
		switch (fMapping.getFieldName()) {
		case Constants.F_FILE_DATE:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMdd"));
			break;
		case Constants.F_FILE_SERIAL:
			value = Constants.MTAG_FILE_SERIAL_NO;
			break;
		case Constants.F_DOT:
			value = Constants.DOT;
			break;
		case Constants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_RECORD_TYPE_CODE:
			value = Constants.MTAG_H_RECORD_TYPE_CODE;
			break;
		case Constants.H_RECORD_COUNT:
			value = doPadding(fMapping, String.valueOf(totalRecords));
			recordCountMap.put(Constants.TOTAL, value);
			break;
		case Constants.H_FILE_DATE_TIME:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		case Constants.H_FILE_SERIAL:
			value = Constants.MTAG_FILE_SERIAL_NO;
			break;
		case Constants.D_RECORD_TYPE_START:
			value = fMapping.getDefaultValue();
			break;
		case Constants.D_GROUP_ID:
			value = fMapping.getDefaultValue();
			break;
		case Constants.D_DEVICE_PREFIX:  //record device prefix
			value = doPadding(fMapping, tagDevice.getDevicePrefix());
			break;
		case Constants.D_SERIAL_NO:  //record SN
			value = doPadding(fMapping, tagDevice.getSerialNo());
			break;	
		case Constants.D_ACCOUNT_TYPE:			
			value = doPadding(fMapping, tagDevice.getAccountType());
			break;
		case Constants.D_NYSTA_TAG_STATUS:
			value = String.valueOf(tagDevice.getNystaTagStatus());
			break; 
		case Constants.D_LOW_LIMIT:
			value = doPadding(fMapping, Constants.D_LOW_LIMIT_VAL);
			//value = Constants.D_LOW_LIMIT_VAL;
			break;			
		case Constants.D_HIGH_LIMIT:
			value = doPadding(fMapping, Constants.D_HIGH_LIMIT_VAL);
			//value = Constants.D_HIGH_LIMIT_VAL;
			break;
		case Constants.D_THWY_ACCT:
			value = doPadding(fMapping, tagDevice.getThwyAcct());
			break;	
		case Constants.D_ETC_PLAN_CODES:			
			value = doPadding(fMapping, Integer.toHexString(tagDevice.getMtagPlanStr()));
			break;
		case Constants.D_NYSBA_TAG_STATUS:
			value = String.valueOf(tagDevice.getNysbaTagStatus());
			break;
		case Constants.D_RECORD_TYPE_END:
			value = fMapping.getDefaultValue();
			break;	
		case Constants.D_TAG_HOME_AGENCY:
			value = doPadding(fMapping, String.valueOf(tagDevice.getTagOwningAgency()));
			break;			
		case Constants.T_END_RECORD_CODE:
			value = fMapping.getDefaultValue();
			break;	
		}
		
		log.info("Mapping done for the field: {} as value: {}",fMapping.getFieldName(), value);
		return value;
		
	}	
}
