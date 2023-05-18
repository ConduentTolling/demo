package com.conduent.tpms.iag.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
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
		log.info("Initializing ITAG file properties..");
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
	}
	
	@Override
	public void createTagFile(List<TAGDevice> tagDeviceList, boolean ispeba) throws IOException {
		try {
			log.info("Starting ITAG file creation process..");
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
				
				
				if(ispeba) {
					recordCountMap.put(Constants.ispeba, "true");
				}else {
					recordCountMap.put(Constants.ispeba,"false");
				}
				fileName = buildFileSection(nameProperties, null);
				log.info("Built file name as {}",fileName);
				
				//String tempFilename = fileName.replace("ITAG","TEMP");
				
				//File file = createNewFile(tempFilename, tagFileDest);
				
				File file = createNewFile(fileName, tagFileDest);
				
				log.info("Built temporary tag status file as {}",file.getAbsolutePath());
				FileWriter filewriter = new FileWriter(file, true); 
				
				log.info("Populating TS file name {} for Statistics..",fileName);
				recordCountMap.put(Constants.FILENAME, fileName); 
				recordCountMap.put(Constants.FILEDATE, fileName.substring(5,13));
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
						recordCountMap.put(Constants.TAG_FROM_DATABASE, String.valueOf(tagDeviceList.size()));
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
					writeContentToFile(filewriter, trailer);
					log.info("Added trailer {} to the file..",trailer);
				}
				
					filewriter.flush();
					filewriter.close();
					overwriteFileHeader(file, headerProperties);
					//fileRenameOperation(tempFilename, tagFileDest, true, Constants.ITAG);
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFile(recordCountMap, Constants.ITAG, statFileDest);
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
			
			good=0;
			low_bal=0;
			invalid=0;
		}
	}
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		/*
		 * if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_GOOD) {
		 * goodCountNysta++;
		 * 
		 * } if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOW) {
		 * lowCountNysta++; } if (tagDeviceObj.getNystaTagStatus() ==
		 * Constants.NTS_ZERO) { zeroCountNysta++; } if
		 * (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOST) { lostCountNysta++;
		 * } if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_GOOD) {
		 * goodCountNysba++; } if(tagDeviceObj.getNysbaTagStatus() ==
		 * Constants.NBTS_LOW) { lowCountNysba++;
		 * 
		 * }if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_ZERO) {
		 * zeroCountNysba++;
		 * 
		 * }if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_LOST) {
		 * lostCountNysba++; }
		 */
		
		
		if(tagDeviceObj.getItagTagStatus() == Constants.TS_VALID) {
			good++;
		}else if(tagDeviceObj.getItagTagStatus() ==Constants.TS_LOW) {
			low_bal++;
		}else if(tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID) {
			invalid++;
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
		
		int serialnm= iagDao.getserialnornge();
		if (masterDataCache.validateHomeDevice(StringUtils.leftPad(tagDeviceObj.getDevicePrefix(), 4, "0"))) {
			if (validationUtils.onlyDigits(tagDeviceObj.getDeviceNo())) {
				if (validationUtils.validateTagStatus(tagDeviceObj.getNystaTagStatus())) {
					if (validationUtils.validateTagStatus(tagDeviceObj.getNysbaTagStatus())) {
						if(Long.parseLong(tagDeviceObj.getSerialNo()) < serialnm) { //Integer.parseInt(tagDeviceObj.getSerialNo())
							isValidRecord = true;
						}
						//isValidRecord = true;
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
		recordCountMap.put(Constants.GOOD, String.valueOf(good));
		recordCountMap.put(Constants.LOW_BAL, String.valueOf(low_bal));
		recordCountMap.put(Constants.INVALID, String.valueOf(invalid));
		recordCountMap.put(Constants.TOTAL, String.valueOf(totalRecords));
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
			
			int good=0;
			int lbal=0;
			int invalid=0;
			int zero=0;
			
			
			try {
				FileReader fr = new FileReader(awayFile);
				BufferedReader br = new BufferedReader(fr);
				String line;
	
				String header = br.readLine(); //consume first line and ignore
				ITAGHeaderInfoVO itagHeaderInfoVO = getHeaderForFile(header);
				itagHeaderInfoVO.setMergeFileName(awayFile.getName());
				while((line = br.readLine()) != null)
				{
					TAGDevice tagDevice = new TAGDevice();
					tagDevice.setIsaway(true);
					tagDevice.setAgencyId(Integer.valueOf(line.substring(0, 4))); 
					tagDevice.setSerialNo(line.substring(4, 14));
					//tagDevice.setTagOwningAgency(Integer.valueOf(line.substring(0, 4))); 
					tagDevice.setTagOwningAgency(Integer.valueOf(line.substring(21, 25)));
					tagDevice.setDevicePrefix(line.substring(0, 4));
					//tagDevice.setNystaTagStatus(Integer.valueOf(line.substring(14, 15)));
					//tagDevice.setNysbaTagStatus(Integer.valueOf(line.substring(14, 15)));
					//tagDevice.setMtagPlanStr(buildPlanSting(line.substring(14, 20)));
					tagDevice.setItagTagStatus(Integer.valueOf(line.substring(14,15)));
					tagDevice.setTagAccntInfo(line.substring(15,21));
					//tagDevice.setMtagPlanStr(buildPlanSting(line.substring(15, 21)));
					
					String record = buildFileSection(detailRecordProperties, tagDevice);	
					writeContentToFile(filewriter, record);
					
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
		//String dateVal = header.substring(16,20) + header.substring(21,23) + header.substring(24,26);
		//itagHeaderInfoVO.setFileDate(validationUtils.getFormattedDate(dateVal,"yyyyMMdd"));
		//itagHeaderInfoVO.setFileTime(header.substring(28,36)); 
		
		itagHeaderInfoVO.setFileDate(validationUtils.getFormattedDate(header.substring(16,26).replace("-", ""),"yyyyMMdd"));
		itagHeaderInfoVO.setFileTime(header.substring(27, 35));
		
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
			}
			if(!fileCompleted) {
			log.info("Exception while renaming the temp file: ");
			throw new FileNotFoundException();
			}
		log.info("File reenamed successfully: {}",fileCompleted);
		return fileCompleted;
	}
	
	@Override
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice) {
		String value = "";
		log.debug("Mapping the field: {}",fMapping.getFieldName());
		switch (fMapping.getFieldName()) {
		case Constants.F_TAG_HOME_AGENCY:
			value = Constants.HOME_AGENCY_ID; ///0001
			break;	
		case Constants.F_UNDER_SCORE:
			value = fMapping.getDefaultValue();
			break;
		case Constants.F_FILE_DATE_TIME:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;		
		case Constants.F_DOT:
			value = Constants.DOT;
			break;
		case Constants.F_FILE_EXTENSION:
			value = fMapping.getDefaultValue();
			break;
			

		case Constants.H_FILE_TYPE:
			value = fMapping.getDefaultValue();
			break;
		case Constants.H_FROM_AGENCY:
			//value = Constants.HOME_AGENCY_ID; ///0001
			
			if(recordCountMap.get(Constants.ispeba).equals("true")) {
				value= Constants.HOME_AGENCY_ID_FORPEBA;
			}else {
				value=Constants.HOME_AGENCY_ID;
			}
			break;
		case Constants.H_FILE_DATE_TIME:
			value = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			break;
		case Constants.H_RECORD_COUNT:
			value = doPadding(fMapping, String.valueOf(totalRecords));
		
			break;
			
		case Constants.D_TAG_AGENCY_ID:
			value = doPadding(fMapping, tagDevice.getDevicePrefix());
			break;
		case Constants.D_TAG_SERIAL_NO:  //record SN
			value = doPadding(fMapping, tagDevice.getSerialNo());
			break;	
			
		case Constants.D_TAG_STATUS:
			value = doPadding(fMapping, Integer. toString(tagDevice.getItagTagStatus()));
			break;
		case Constants.D_TAG_ACCT_INFO: 
				//value = doPadding(fMapping, Integer.toHexString(tagDevice.getMtagPlanStr()));
				//value = buildHexadecimalForItagInfo(tagDevice.getItagInfo());
				
				 if(tagDevice.isIsaway()) {
					  String tagacctinfo = tagDevice.getTagAccntInfo();
				      value= doPadding(fMapping,tagacctinfo); 
				  }else {
					  int itaginfo=tagDevice.getItagInfo(); 
					  String acctitaginfo= Integer.toHexString(itaginfo); 
					  value=doPadding(fMapping,acctitaginfo); 
					  //value=doPadding(fMapping,fMapping.getDefaultValue());
					  }
			break;
		}
		
		log.info("Mapping done for the field: {} as value: {}",fMapping.getFieldName(), value);
		return value;
		
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
	
}
