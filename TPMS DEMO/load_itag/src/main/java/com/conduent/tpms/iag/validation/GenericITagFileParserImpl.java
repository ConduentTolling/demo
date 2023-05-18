package com.conduent.tpms.iag.validation;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ItagConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ITAGDetailInfoVO;
import com.conduent.tpms.iag.dto.ITAGHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.exception.RecordThresholdLimitExceeded;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.utility.Convertor;
import com.google.common.io.Files;

@Component
public class GenericITagFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(GenericITagFileParserImpl.class);

	private FileNameDetailVO fileNameVO;
	private ITAGDetailInfoVO detailVO;
	private ITAGHeaderInfoVO headerVO;
	protected Integer fileCount = 0;
	protected String filePrefix; 
	private String fileStatus;
	private FileUtil fileutil = new FileUtil();
	protected List<AgencyInfoVO> validAgencyInfoList;
	protected int count_stat1 = 0;
	protected int count_stat2 = 0;
	protected int count_stat3 = 0;
	protected int count_stat4 = 0;
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	private List<ITAGDetailInfoVO> detailVOList;
	/**
	 * Initialize the ITAG file properties
	 */
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;
	
	/*
	 * @Autowired private static NamedParameterJdbcTemplate namedJdbcTemplate;
	 */
	
	@Override
	public void initialize() {

		log.info("Initializing ITAG file..");
		setFileType(ItagConstants.ITAG);
		setFileFormat(ItagConstants.FIXED);
	//	setAgencyId(ItagConstants.MTA_FILE_PREFIX); 
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);

		fileNameVO = new FileNameDetailVO();
		detailVO = new ITAGDetailInfoVO();
		headerVO = new ITAGHeaderInfoVO();
		detailVOList = new ArrayList<ITAGDetailInfoVO>();
	
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ItagConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new ITAGDetailInfoVO();
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException {

		switch (fMapping.getFieldName()) {
		case ItagConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case ItagConstants.F_FILE_DATE_TIME:
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case ItagConstants.F_FILE_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case ItagConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		case ItagConstants.H_VERSION:
			headerVO.setVersion(value);
			break;
		case ItagConstants.H_FROM_AGENCY_ID:
			headerVO.setFromAgencyId(value);
			break;
		case ItagConstants.H_DATETIME:
			LocalDateTime hdrdateTime= genericValidation.getFormattedDateTimeNew(value);
			if(customDateValidationNew(hdrdateTime)) {
				headerVO.setFiledatetime(hdrdateTime);
			}
			break;
		case ItagConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case ItagConstants.D_TAG_AGENCY_ID:
			detailVO.setTagAgencyId(value);
			break;
		case ItagConstants.D_TAG_SERIAL_NUMBER:
			detailVO.setTagSerialNumber(value);
			break;
		case ItagConstants.D_TAG_STATUS:
			detailVO.setTagStatus(value);
			break;
		case ItagConstants.D_TAG_ACCT_INFO:
		    detailVO.setTagAccntInfo(value);
		    break;
		case ItagConstants.D_TAG_HOME_AGENCY:
		    detailVO.setTagHomeAgency(value);
		    break;
		case ItagConstants.D_TAG_AC_TYPE_IND:
			boolean valCheckTypeIND = validate(value);
			if(valCheckTypeIND) {
				detailVO.setTagACTypeIND(null);
			}else {
				detailVO.setTagACTypeIND(value);
			}
			 break;
		case ItagConstants.D_TAG_ACCOUNT_NO:
			boolean valCheck = validate(value);
			if(valCheck) {
				detailVO.setTagAccntno(null);		
			}
			else {
				String valueaccno= removeLeadingZeroes(value);
				detailVO.setTagAccntno(valueaccno);
			}
			 break;
		case ItagConstants.D_TAG_PROTOCOL:
			boolean valCheckProtocol = validate(value);
			if(valCheckProtocol) {
				detailVO.setTagProtocol(null);
			}
			else {
				detailVO.setTagProtocol(value.trim());
			}
			 break;
		case ItagConstants.D_TAG_TYPE:
			boolean valCheckType = validate(value);
			if(valCheckType) {
				detailVO.setTagType(null);
			}
			else {
				detailVO.setTagType(value);
			}
			 break;
		case ItagConstants.D_TAG_MOUNT:
			boolean valCheckMount =validate(value);
			if(valCheckMount) {
				detailVO.setTagMount(null);
			}else {
				detailVO.setTagMount(value);
			}
			 break;
		case ItagConstants.D_TAG_CLASS:
			boolean valCheckClass = validate(value);
			if(valCheckClass) {
				detailVO.setTagClass(null);
			}
			else {
				String valuetag= removeLeadingZeroes(value);
				detailVO.setTagClass(valuetag);
			}
			 break;

		default:

		}
		log.debug("Mapping done for value: {} with field: {}", value, fMapping);
	}

	
	public static String removeLeadingZeroes(String str) {
	      String strPattern = "^0+(?!$)";
	      str = str.replaceAll(strPattern, "");
	      return str;
	   }
	
	public boolean validate(String value)
    {
        String matcher_String = "\\s{"+value.length()+"}|[*]{"+value.length()+"}";
        if(value.matches(matcher_String)) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	
	
	public boolean customDateValidation(LocalDate value) throws InvlidFileHeaderException {

		if (!fileNameVO.getFileDateTime().toLocalDate().equals(value)) {
			log.info("Header Date{} is not matching with Header Date", value);
			throw new InvlidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
		}
	
	
	public boolean customDateValidationNew(LocalDateTime value) throws InvlidFileHeaderException {

		if (!fileNameVO.getFileDateTime().equals(value)) {
			log.info("Header Date{} is not matching with Header Date", value);
			throw new InvlidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
		}
	
	public boolean customTimeValidation(String value) throws InvlidFileHeaderException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String time = formatter.format(fileNameVO.getFileDateTime().toLocalTime());
		if (!time.equals(value)) {
			log.info("Header Time{} is not matching with Header Time", value);
			throw new InvlidFileHeaderException("Time mismatch for File and Header");
		}
		return true;
		}

	
	@Override
	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException,DateTimeParseException {

		super.validateAndProcessFileName(fileName);

		log.info("Checking file process status {}.. ", fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);
		
		filePrefix = fileName.substring(0, 4);
		
		if(filePrefix != ItagConstants.MTA_FILE_PREFIX && !filePrefix.equals(ItagConstants.MTA_FILE_PREFIX)) 
		{
			if (validateFileAgencyforAway(fileName)) 
			{
				log.info("{} is valid for away agency.", fileName);
				
				if (fileDetails == null) 
				{	log.info("{} is not processed earlier.", fileName);
					insertFileDetailsIntoCheckpoint();
				} 
				else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) 
				{
					log.info("{} is already processed.", fileName);
					saveFileToWorkingDir(fileName);
					throw new FileAlreadyProcessedException("File already processed");
				}
			} 
			else {
				log.info("Invalid file prefix in file name:{} for away agency id: {}", fileName, filePrefix);
				throw new InvalidFileNameException("Invalid file prefix in file name: "+fileName);
			}
		}
		else 
		{
			if (validateFileAgencyforHome(fileName)) 
			{
				log.info("{} is valid for home agency.", fileName);
				
				if (fileDetails == null) 
				{	log.info("{} is not processed earlier.", fileName);
					insertFileDetailsIntoCheckpoint();
				} 
				else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) 
				{
					log.info("{} is already processed.", fileName);
					saveFileToWorkingDir(fileName);
					throw new FileAlreadyProcessedException("File already processed");
				}
			} 
			else {
				log.info("Invalid file prefix in file name:{} for home agency id: {}", fileName, filePrefix);
				throw new InvalidFileNameException("Invalid file prefix in file name: "+fileName);
			}
			
		}
		
	}
	
	@Override
	public void finishFileProcess(int agencySequence) throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException, RecordThresholdLimitExceeded {
	
		if (filePrefix != ItagConstants.MTA_FILE_PREFIX && !filePrefix.equals(ItagConstants.MTA_FILE_PREFIX))
		{
			finishFileProcessforAway(agencySequence);
		}
		else 
		{
			finishFileProcessforHome();
		}
		
	}

	public void finishFileProcessforAway(int agencySequence) throws InvalidRecordException, InvlidFileHeaderException, FileAlreadyProcessedException,RecordThresholdLimitExceeded {
		
		fileNameVO.setFileName(getFileName());
		
		log.info("Fetching last successfully processed file name...");
		String lastSuccFileName = tQatpMappingDao.getLastSuccesfulProcessedFile(fileNameVO.getFromAgencyId());
		
		if (lastSuccFileName.isEmpty()) {
			
			log.info("Last file processed successfully: {}", lastSuccFileName);

			insertBatchAndUpdateCheckpoint();
		} 
		else if (!lastSuccFileName.isEmpty()) {

			String lastProcFileDate = lastSuccFileName.substring(5, 19);

			if (!ifOldProcessedFileIsLatest(fileNameVO.getFileDateTime(), lastProcFileDate)) {

				File awaytagOldFile = laodAndPrepareLastFile(lastSuccFileName,
						getConfigurationMapping().getFileInfoDto().getWorkingDir());
				log.info("Loaded last successful file {} from working directory",awaytagOldFile);
				
				validateThresholdLimit(awaytagOldFile);
				
				transactionDao.preparFileAndUploadToExtern(getNewFile(), awaytagOldFile, agencySequence);
				//updateDeviceTagDetails();
			}else {
				log.info("File {} is older then the last processed file {}.",fileNameVO.getFileName(),lastSuccFileName);
				updateFileDetailsInCheckPoint();
				throw new FileAlreadyProcessedException(fileNameVO.getFileName());
			}
		}
		saveFileToWorkingDir(fileNameVO.getFileName());
		
		
	}
	
	public void finishFileProcessforHome() throws InvalidRecordException, InvlidFileHeaderException, FileAlreadyProcessedException {
		
		fileNameVO.setFileName(getFileName());
		
		log.info("Fetching last successfully processed file name...");
		String lastSuccFileName = tQatpMappingDao.getLastSuccesfulProcessedFile(fileNameVO.getFromAgencyId());
		
		if (lastSuccFileName.isEmpty()) {
			
			log.info("Last file processed successfully: {}", lastSuccFileName);

			log.debug("File: {} is coming for the first time for Agency. Insert device details and update status of this file in T_LANE_TX_CHECKPOINT table."
					, getFileName());
			
			insertBatchAndUpdateCheckpoint();
		} 
		                                                         
		else if (!lastSuccFileName.isEmpty()) {

			log.debug("File: {} already exist for this Agency. " , lastSuccFileName);

			String lastProcFileDate = lastSuccFileName.substring(5, 19);

			if (!ifOldProcessedFileIsLatest(fileNameVO.getFileDateTime(), lastProcFileDate)) {

				File awaytagOldFile = laodAndPrepareLastFile(lastSuccFileName,
						getConfigurationMapping().getFileInfoDto().getWorkingDir());
				log.info("Loaded last successful file {} from working directory",awaytagOldFile);
				
				//validateThresholdLimit(awaytagOldFile);
				
				transactionDao.preparFileAndUploadToExtern(getNewFile(), awaytagOldFile, 1);                 
				//updateDeviceTagDetails();                                                                   
			}else {
				log.info("File {} is older then the last processed file {}.",fileNameVO.getFileName(),lastSuccFileName);
				updateFileDetailsInCheckPoint();
				throw new FileAlreadyProcessedException(fileNameVO.getFileName());
			}
		}
		
		saveFileToWorkingDir(fileNameVO.getFileName());
	}
	
	public void saveFileToWorkingDir(String workingfilename) {
		log.info("Load a copy of new file {} into working directory...", workingfilename);
		/*
		 * Environment path
		 */
		String fileUri = getConfigurationMapping().getFileInfoDto().getWorkingDir().concat("//").concat(workingfilename);
		 
		/*
		 * Local path
		 */
		/*
		 * String fileUri =
		 * getConfigurationMapping().getFileInfoDto().getWorkingDir().concat("\\").
		 * concat(workingfilename);
		 */
		 
		File workingFile = new File(fileUri);
		
		File temp = workingFile;
        if (temp.exists()) {
            temp.delete();
        }
		try {
			fileutil.copyFileUsingApacheCommonsIO(getNewFile(), workingFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void validateThresholdLimit(File lastSuccFile) throws InvlidFileHeaderException , RecordThresholdLimitExceeded
	{
		int currentFileHeaderCount = Integer.parseInt(headerVO.getRecordCount());
		log.info("The record count in currently processed file is: {}", currentFileHeaderCount);
		int lastFileHeaderCount = 0;
		if (lastSuccFile.exists()) 
		{
				try{
					FileReader fileReader = new FileReader(lastSuccFile);
					LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
					
					while (lineNumberReader.getLineNumber() == 0) {
						String header = lineNumberReader.readLine();
						lastFileHeaderCount = Integer.valueOf(header.substring(36, 46));
						log.info("The record count in last processed file is: {}", lastFileHeaderCount);
					}
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			
			//int thresholdLimit = ItagConstants.THRESHOLD_LIMIT;
			int thresholdLimit = tQatpMappingDao.getThresholdValue("ITAGTHRESHOLD");
			int thresholdLimitPercent = tQatpMappingDao.getThresholdValue("ITAGTHRESHOLDPERCENT");
			Double currentFileHeaderCountd = Double.valueOf(currentFileHeaderCount);
			Double lastFileHeaderCountd = Double.valueOf(lastFileHeaderCount);
			Double diff = Math.abs(currentFileHeaderCountd - lastFileHeaderCountd);
			int diffpercent = (int) ((diff * 100) / lastFileHeaderCountd) ;
			
			log.info("diff is : {}", diff);
			log.info("diffpercent is : {}", diffpercent);
			log.info("Threshold Limit is : {}", thresholdLimit);
			log.info("Threshold Limit Percent is : {}", thresholdLimitPercent);
			
			if ( (Math.abs(currentFileHeaderCount - lastFileHeaderCount) > thresholdLimit) ||  diffpercent > thresholdLimitPercent  ) 
			{
				if ((Math.abs(currentFileHeaderCount - lastFileHeaderCount) > thresholdLimit))
				{
					log.info("File record count difference exceeded threshold limit:" + thresholdLimit);
					throw new RecordThresholdLimitExceeded("File record count difference exceeded threshold limit:" + thresholdLimit);
				}
				
				else if (diffpercent > thresholdLimitPercent)
				{
					log.info("File record count difference exceeded threshold limit percentage:" + thresholdLimitPercent);
					throw new RecordThresholdLimitExceeded("File record count difference exceeded threshold limit percentage:" + thresholdLimitPercent);
				}
								
				//throw new InvlidFileHeaderException("File record count difference exceeded threshold limit:" + thresholdLimit);
			}
		}
	}

	public File laodAndPrepareLastFile(String fileName, String fileLocation) {
		log.info("Load a last successful file {} from working directory...",fileName);
		
		/*
		 * Local path
		 */
		// String fileUri = fileLocation.concat("\\").concat(fileName).concat(".ITAG");
		
		/*
		 * Environment path
		 */
		String fileUri = fileLocation.concat("//").concat(fileName);
		File file = new File(fileUri);
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			 String line;
			 try {
				while ((line = br.readLine()) != null) {
					 log.info(line);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return file;
	}

	public boolean ifOldProcessedFileIsLatest(LocalDateTime newFileDateTime, String lastSuccFileDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime lastProcdateTime = LocalDateTime.parse(lastSuccFileDate, formatter);

		if (lastProcdateTime.isAfter(newFileDateTime)) {

			log.info("File {} is not latest ..",getFileName());
			return true;
		} else {
			log.info("File {} is latest ..",getFileName());
			return false;
		}
	}

	@Override
	public void processEndOfLine(String fileSection) {
		
		
	}
	
	List<ITAGDetailInfoVO> detailVOListTemp;
	public void insertBatchAndUpdateCheckpoint() throws InvalidRecordException{
		
		log.info("Initial detailVOList size::: {}",detailVOList.size());
		ITAGDetailInfoVO[] records = new ITAGDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);

		Integer counter = 1;
		recordCounter=1;
		Integer preProcessedRecordCount = 0;
		ITAGDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<ITAGDetailInfoVO>();
		if (fileDetails != null) {
			preProcessedRecordCount = fileDetails.getProcessedCount();
		}
		int i = 0;
		do {
			try {
				for (i = 0; i < records.length; i++) {
					prevline = (records[i]);
					
					if ((preProcessedRecordCount == 0 || recordCounter > preProcessedRecordCount)) {
							detailVOListTemp.add(prevline);
						
						if (counter == ItagConstants.BATCH_RECORD_COUNT) {
							saveRecords(fileDetails);
							fileDetails.setProcessStatus(FileProcessStatus.IN_PROGRESS);
							fileDetails.setProcessedCount(recordCounter);
							fileDetails.setSuccessCount(recordCounter);
							tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
							log.info("In counter loop laneTxCheckPoint details " + fileDetails.toString());
							detailVOListTemp.clear();
							counter = 0;
						}
						counter++;
					}
					recordCounter++;
				}
			} catch (Exception ex) {
				log.info("Exception {}", ex.getMessage());
			}
			if (records.length == i) {
				records = null;
			}
		} while (records != null);
		if(fileDetails.getFileCount() == preProcessedRecordCount) {
			detailVOListTemp.clear();
		}
		saveRecords(fileDetails);
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount(recordCounter-1);
		fileDetails.setSuccessCount(recordCounter-1);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
		log.info("In save loop laneTxCheckPoint details " + fileDetails.toString());
		detailVOList.clear();

	}

	@Override
	public void saveRecords(FileDetails laneTxCheckPoint) {
		insertRecords();
		laneTxCheckPoint.setFileName(getFileName());
		laneTxCheckPoint.setFileType(fileNameVO.getFileType().trim());
		laneTxCheckPoint.setFileCount(Integer.parseInt(headerVO.getRecordCount()));
		laneTxCheckPoint.setUpdateTs(timeZoneConv.currentTime());
		laneTxCheckPoint.setFileDateTime(fileNameVO.getFileDateTime());
		laneTxCheckPoint.setProcessName(fileNameVO.getFromAgencyId());

	}
	
	public void insertRecords() {
		
		if (filePrefix != ItagConstants.MTA_FILE_PREFIX && !filePrefix.equals(ItagConstants.MTA_FILE_PREFIX)) 
		{ 
			if(detailVOListTemp!=null && !detailVOListTemp.isEmpty() && detailVOListTemp.size() > 0) {
				insertDeviceTagDetailsforAway(detailVOListTemp);
				detailVOListTemp.clear();
			}
		}
		else
		{
			if(detailVOListTemp!=null && !detailVOListTemp.isEmpty() && detailVOListTemp.size() > 0) {
				insertDeviceTagDetailsforHome(detailVOListTemp);
				detailVOListTemp.clear();
			}
		}
	}
	
	public void insertDeviceTagDetailsforHome(List<ITAGDetailInfoVO> detailVOList){
		
		List<TagDeviceDetails> tagDeviceDetailsList = new ArrayList<TagDeviceDetails>();

		for (ITAGDetailInfoVO detailVOObj : detailVOList) {

			TagDeviceDetails tagDeviceDetails = new TagDeviceDetails();
			
			tagDeviceDetails.setDeviceNo(detailVOObj.getTagAgencyId().concat(detailVOObj.getTagSerialNumber()));
			//tagDeviceDetails.setTagHomeAgency(filePrefix); 
			//tagDeviceDetails.setFileAgencyId(fileNameVO.getFromAgencyId());
			tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyidHome(filePrefix));
			tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyidHome(filePrefix));
			tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
			tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
			tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
			tagDeviceDetails.setTagType(detailVOObj.getTagType());
			tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
			tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
			tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
			tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
			tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
			tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
		    tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
			tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
			tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
			tagDeviceDetails.setRecord(detailVOObj.getLine());



			tagDeviceDetailsList.add(tagDeviceDetails);
		}
		int[] totalRecords = tQatpMappingDao.insertTagDeviceDetailsforHome(tagDeviceDetailsList);

		log.debug("Inserted device tag count: {}", totalRecords);
		setFileStatus(ItagConstants.PROCESS_STATUS_COMPLETE);
		
	}

	public void insertDeviceTagDetailsforAway(List<ITAGDetailInfoVO> detailVOList) {

		List<TagDeviceDetails> tagDeviceDetailsList = new ArrayList<TagDeviceDetails>();

		for (ITAGDetailInfoVO detailVOObj : detailVOList) {

			TagDeviceDetails tagDeviceDetails = new TagDeviceDetails();
			
			
			
		    tagDeviceDetails.setDeviceNo(detailVOObj.getTagAgencyId().concat(detailVOObj.getTagSerialNumber()));
			tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyid(filePrefix)); 
			tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyid(filePrefix));
			tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
			tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
			tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
			tagDeviceDetails.setTagType(detailVOObj.getTagType());
			tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
			tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
			tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
			tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
			tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
			tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
			tagDeviceDetails.setInvalidAddressId(0);
		    tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
			tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
			tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
            tagDeviceDetails.setRecord(detailVOObj.getLine());
			
			//tagDeviceDetails.setTagAcctInfo(detailVOObj.getTagAccntInfo());
			
			tagDeviceDetailsList.add(tagDeviceDetails);
		}
		int[] totalRecords = tQatpMappingDao.insertTagDeviceDetailsforAway(tagDeviceDetailsList);

		log.debug("Inserted device tag count: {}", totalRecords);
		setFileStatus(ItagConstants.PROCESS_STATUS_COMPLETE);
		
	}
	
	
	
	/*
	 * public static String getAgencyid(String fileprefix) {
	 * 
	 * int agencyid=0; MapSqlParameterSource paramSource = new
	 * MapSqlParameterSource(); paramSource.addValue("param_name", fileprefix);
	 * 
	 * String queryForprocessparam =
	 * LoadJpaQueries.getQueryById("SELECT_AGENCYID_FROM_T_AGENCY");
	 * 
	 * try {
	 * 
	 * agencyid= namedJdbcTemplate.queryForObject(queryForprocessparam, paramSource,
	 * Integer.class);
	 * 
	 * } catch (EmptyResultDataAccessException empty) { empty.printStackTrace(); }
	 * 
	 * String agencyID= String.valueOf(agencyid);
	 * 
	 * return agencyID;
	 * 
	 * }
	 */
	
	public void updateDeviceTagDetails(String newBucket , String oldBucket) {                                                 

		log.info("Get Updated records from external table {} and {} ..",newBucket,oldBucket);
		//List<ITAGDetailInfoVO> detailVOList = tQatpMappingDao.getDiffRecordsFromExtTables();  
		List<ITAGDetailInfoVO> detailVOList = tQatpMappingDao.getDiffRecordsFromExtTables_ITAG(newBucket,oldBucket);

		log.info("Get list of Device numbers from new external table..");
		List<String> newDeviceList = tQatpMappingDao.getDataFromNewExternTable(newBucket);     

		log.info("Get list of Device numbers from old external table..");
		List<String> oldDeviceList = tQatpMappingDao.getDataFromOldExternTable(oldBucket);  

		for (ITAGDetailInfoVO detailVOObj : detailVOList) {
			TagDeviceDetails tagDeviceDetails = new TagDeviceDetails();
			 String record= detailVOObj.toStringnew();
			log.debug("Preparing device tag details to update..");
			List<TagDeviceDetails> tagDeviceDetailsList = new ArrayList<TagDeviceDetails>();

			String deviceNo = detailVOObj.getTagAgencyId().concat(detailVOObj.getTagSerialNumber());
			
			if(!filePrefix.equals(ItagConstants.MTA_FILE_PREFIX) && filePrefix != ItagConstants.MTA_FILE_PREFIX)
			{
				
				
	
			          boolean valTagACTypeIND =	validate(detailVOObj.getTagACTypeIND());
			             if(valTagACTypeIND) {
			                     detailVOObj.setTagACTypeIND(null);
			                  }
			             else {
				                detailVOObj.setTagACTypeIND(detailVOObj.getTagACTypeIND());
			                  }
				
			             boolean valTagAccNO =	validate(detailVOObj.getTagAccntno());
			             if(valTagAccNO) {
			                     detailVOObj.setTagAccntno(null);
			                  }
			             else {
			            	    String valueaccno= removeLeadingZeroes(detailVOObj.getTagAccntno());
				                detailVOObj.setTagAccntno(valueaccno);
			                  }
			             boolean valTagProtocol =	validate(detailVOObj.getTagProtocol());
			             if(valTagProtocol) {
			                     detailVOObj.setTagProtocol(null);
			                  }
			             else {
				                detailVOObj.setTagProtocol(detailVOObj.getTagProtocol().trim());
			                  }
			             boolean valTagType =	validate(detailVOObj.getTagType());
			             if(valTagType) {
			                     detailVOObj.setTagType(null);
			                  }
			             else {
				                detailVOObj.setTagType(detailVOObj.getTagType());
			                  }
			             boolean valTagMount =	validate(detailVOObj.getTagMount());
			             if(valTagMount) {
			                     detailVOObj.setTagMount(null);
			                  }
			             else {
				                detailVOObj.setTagMount(detailVOObj.getTagMount());
			                  }
			             boolean valTagClass = validate(detailVOObj.getTagClass());
			             if(valTagClass) {
			            	 detailVOObj.setTagClass(null);
			             }
			             else {
			            	 String valuetag= removeLeadingZeroes(detailVOObj.getTagClass());
			            	 detailVOObj.setTagClass(valuetag);
			             }
				
				
				LocalDate endDate = tQatpMappingDao.getIfDeviceExistForFuture(deviceNo);    
				log.debug("End date for device no. {} is: {}",deviceNo ,endDate );
				
				if (newDeviceList.contains(deviceNo) && oldDeviceList.contains(deviceNo)) {
	
					switch (detailVOObj.getFiletype()) {
					case "NEW":
						tagDeviceDetails.setDeviceNo(deviceNo);
						tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyid(filePrefix));
						tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyid(filePrefix));
						tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
						tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
						tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
						tagDeviceDetails.setTagType(detailVOObj.getTagType());
						tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
						tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
						tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
						tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
						tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
						tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
						tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
						tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
						tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
						//tagDeviceDetails.setRecord(detailVOObj.toStringnew());
						tagDeviceDetails.setRecord(record);
						
					
						//tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
						
						//if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) 
						//{
							tQatpMappingDao.updateDuplicateTagDeviceDetails(tagDeviceDetails, endDate);
							// new
							tagDeviceDetailsList.add(tagDeviceDetails);

							tQatpMappingDao.insertTagDeviceDetailsforAway(tagDeviceDetailsList);
							tagDeviceDetailsList.clear();

						//} 
						
						//else 
						//{
							//tQatpMappingDao.updateTagDeviceDetails(tagDeviceDetails);
						//}
						
						log.info("deviceNo {} updated.", deviceNo);
						
						break;
	
					case "OLD":
						log.info("Skipping the old data for DeviceNo {}", deviceNo);
						break;
					}
				} 
				else if (newDeviceList.contains(deviceNo) && !oldDeviceList.contains(deviceNo) ) {
					
					//if(endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) 
					//{
	
					
					/*
					 * tagDeviceDetails.setDeviceNo(deviceNo);
					 * tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
					 * tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					 * tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
					 * tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
					 * //tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
					 * tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyid(filePrefix));
					 * tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					 * tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
					 * tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
					 * tagDeviceDetails.setTagType(detailVOObj.getTagType());
					 * tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
					 * tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
					 * tagDeviceDetails.setRecord(detailVOObj.toStringnew());
					 * tQatpMappingDao.updateDuplicateTagDeviceDetails(tagDeviceDetails,endDate);
					 * 
					 * log.info("deviceNo {} already exist. End date extended..", deviceNo);
					 */
						
					//}
					//else 
					//{
						tagDeviceDetails.setDeviceNo(deviceNo);
						tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
						tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
						//tagDeviceDetails.setInvalidAddressId(0);
						tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
						tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
						tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
						tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyid(filePrefix));
						tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
						tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
						//tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
						tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyid(filePrefix));
						tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
						tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
						tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
						tagDeviceDetails.setTagType(detailVOObj.getTagType());
						tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
						tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
						//tagDeviceDetails.setRecord(detailVOObj.toStringnew());
						tagDeviceDetails.setRecord(record);
						
						tagDeviceDetailsList.add(tagDeviceDetails);
					
		
						tQatpMappingDao.insertTagDeviceDetailsforAway(tagDeviceDetailsList);
						tagDeviceDetailsList.clear();
						
						log.info("deviceNo {} added.", deviceNo);
						
				//} 
				}else if(!(newDeviceList.contains(deviceNo)) && oldDeviceList.contains(deviceNo))
				{
					tagDeviceDetails.setDeviceNo(deviceNo);
					tagDeviceDetails.setEndDate(fileNameVO.getFileDateTime().toLocalDate());
					tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
					tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
					//tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
					tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyid(filePrefix));
					tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
					tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
					tagDeviceDetails.setTagType(detailVOObj.getTagType());
					tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
					tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
					//tagDeviceDetails.setRecord(detailVOObj.toStringnew());
					tagDeviceDetails.setRecord(record);
					tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					//if(endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) 
					//{
						tQatpMappingDao.updateDuplicateTagDeviceDateForOld(tagDeviceDetails,endDate);
					//}
					//else 
					//{
					//tQatpMappingDao.updateTagDeviceDetails(tagDeviceDetails);
					//}
					log.info("deviceNo {} expired.", deviceNo);
				}
		}
		else if(filePrefix.equals(ItagConstants.MTA_FILE_PREFIX) || filePrefix == ItagConstants.MTA_FILE_PREFIX) 
		{
			boolean valTagACTypeIND =	validate(detailVOObj.getTagACTypeIND());
            if(valTagACTypeIND) {
                    detailVOObj.setTagACTypeIND(null);
                 }
            else {
	                detailVOObj.setTagACTypeIND(detailVOObj.getTagACTypeIND());
                 }
	
            boolean valTagAccNO =	validate(detailVOObj.getTagAccntno());
            if(valTagAccNO) {
                    detailVOObj.setTagAccntno(null);
                 }
            else {
            	String accval= removeLeadingZeroes(detailVOObj.getTagAccntno());
	                detailVOObj.setTagAccntno(accval);
                 }
            boolean valTagProtocol =	validate(detailVOObj.getTagProtocol());
            if(valTagProtocol) {
                    detailVOObj.setTagProtocol(null);
                 }
            else {
	                detailVOObj.setTagProtocol(detailVOObj.getTagProtocol().trim());
                 }
            boolean valTagType =	validate(detailVOObj.getTagType());
            if(valTagType) {
                    detailVOObj.setTagType(null);
                 }
            else {
	                detailVOObj.setTagType(detailVOObj.getTagType());
                 }
            boolean valTagMount =	validate(detailVOObj.getTagMount());
            if(valTagMount) {
                    detailVOObj.setTagMount(null);
                 }
            else {
	                detailVOObj.setTagMount(detailVOObj.getTagMount());
                 }
            boolean valTagClass = validate(detailVOObj.getTagClass());
            if(valTagClass) {
           	 detailVOObj.setTagClass(null);
            }
            else {
           	 String valuetag= removeLeadingZeroes(detailVOObj.getTagClass());
           	 detailVOObj.setTagClass(valuetag);
            }
            
            
			LocalDate endDate = tQatpMappingDao.getIfDeviceExistForFutureforHome(deviceNo);    
			log.debug("End date for device no. {} is: {}",deviceNo ,endDate );
			

			if (newDeviceList.contains(deviceNo) && oldDeviceList.contains(deviceNo)) {

				switch (detailVOObj.getFiletype()) {
				case "NEW":
					tagDeviceDetails.setDeviceNo(deviceNo);
					//tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
					tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyidHome(filePrefix));
					tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyidHome(filePrefix));
					tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
					tagDeviceDetails.setTagProtocol( detailVOObj.getTagProtocol());
					tagDeviceDetails.setTagType(detailVOObj.getTagType());
					tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
					tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
					tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
					tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
					tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
					tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
					tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
					tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					tagDeviceDetails.setRecord(record);
					
					
					
					
					
					
				
					
					//if(endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) 
					//{
					tQatpMappingDao.updateDuplicateTagDeviceDetailsforHome(tagDeviceDetails,endDate);   
					tagDeviceDetailsList.add(tagDeviceDetails);
					
					tQatpMappingDao.insertTagDeviceDetailsforHome(tagDeviceDetailsList);
					tagDeviceDetailsList.clear();
					//}else {
						//tQatpMappingDao.updateTagDeviceDetailsforHome(tagDeviceDetails);        
					//}
					log.info("deviceNo {} updated.", deviceNo);
					
					break;

				case "OLD":
					log.info("Skipping the old data for DeviceNo {}", deviceNo);
					break;
				}
			} 
			else if (newDeviceList.contains(deviceNo) && !oldDeviceList.contains(deviceNo) ) {
				
//				if(endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
//
//					tagDeviceDetails.setDeviceNo(deviceNo);
//					tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
//					tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
//					tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
//					tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
//					tagDeviceDetails.setTagHomeAgency(detailVOObj.getTagHomeAgency());
//					tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
//					tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
//					tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
//					tagDeviceDetails.setTagType(detailVOObj.getTagType());
//					tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
//					tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
//					tQatpMappingDao.updateDuplicateTagDeviceDetailsforHome(tagDeviceDetails,endDate);
//					
//					log.info("deviceNo {} already exist. End date extended..", deviceNo);
//					}
				//else {
					tagDeviceDetails.setDeviceNo(deviceNo);
					tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyidHome(filePrefix));
					tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyidHome(filePrefix));
					tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
					tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
					tagDeviceDetails.setTagType(detailVOObj.getTagType());	
					tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
					tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
					tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
					tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
					tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
					tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
					//tagDeviceDetails.setInvalidAddressId(0);
					tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
					tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					tagDeviceDetails.setRecord(record);
					
					tagDeviceDetailsList.add(tagDeviceDetails);
	
					tQatpMappingDao.insertTagDeviceDetailsforHome(tagDeviceDetailsList);
					tagDeviceDetailsList.clear();
					
					log.info("deviceNo {} added.", deviceNo);
					
			//} 
			}else if(!(newDeviceList.contains(deviceNo)) && oldDeviceList.contains(deviceNo))
			{
				tagDeviceDetails.setDeviceNo(deviceNo);
				tagDeviceDetails.setTagHomeAgency(tQatpMappingDao.getAgencyidHome(filePrefix));
				tagDeviceDetails.setFileAgencyId(tQatpMappingDao.getAgencyidHome(filePrefix));
				tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
				tagDeviceDetails.setTagACTypeIND(detailVOObj.getTagACTypeIND());
				tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
				tagDeviceDetails.setTagType(detailVOObj.getTagType());
				tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
				tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
				tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
				tagDeviceDetails.setEndDate(fileNameVO.getFileDateTime().toLocalDate());
				tagDeviceDetails.setIAGTagStatus(detailVOObj.getTagStatus());
				tagDeviceDetails.setInfileInd(ItagConstants.INFILE_IND);
				tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
				tagDeviceDetails.setLastFileTS(fileNameVO.getFileDateTime());
				tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				tagDeviceDetails.setRecord(record);
				
				//if(endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
					//tQatpMappingDao.updateDuplicateTagDeviceDetailsforHome(tagDeviceDetails,endDate);
				//}
				//else {
				tQatpMappingDao.updateTagDeviceDetailsforHome(tagDeviceDetails);
				//}
				log.info("deviceNo {} expired.", deviceNo);
			}
		}
				
		}
		
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ItagConstants.ITAG);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount((int) fileDetails.getFileCount());
		fileDetails.setSuccessCount((int) fileDetails.getFileCount());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	@Override
	public void processDetailLine(String line) {

	    detailVO.setLine(line);
		detailVOList.add(detailVO);
	}

	@Override
	public void insertFileDetailsIntoCheckpoint() {

		log.debug("Inserting file details into lane_checkpoint table..");

		fileDetails = new FileDetails();
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ItagConstants.ITAG);
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 4));
		fileDetails.setProcessId((long) ItagConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ItagConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setSerialNumber((long) ItagConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setProcessedCount(ItagConstants.ZERO);
		fileDetails.setSuccessCount(ItagConstants.ZERO);
		fileDetails.setExceptionCount(ItagConstants.ZERO);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}

	public void updateFileDetailsInCheckPoint() {

		log.debug("Updating file {} details into checkpoint table with status C.", fileNameVO.getFileName());

		fileDetails.setFileName(fileNameVO.getFileName().trim());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setFileCount(recordCount.get(fileNameVO.getFileName()).intValue());
		fileDetails.setProcessedCount(recordCount.get(fileNameVO.getFileName()).intValue());
		fileDetails.setSuccessCount(recordCount.get(fileNameVO.getFileName()).intValue());
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException {
		//if (recordCount != Convertor.toLong(headerVO.getRecordCount())) {
		if (recordCount.get(fileName).longValue() != Convertor.toLong(headerVO.getRecordCount()).longValue()) {
			log.info("Record count mismatch from number of records in "+getFileName());
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}
	
	
	@Override
	public void ValidateCountStat() throws InvalidRecordCountException {
		
		try {
			if(count_stat1 != Integer.valueOf(headerVO.getCountstat1())) {
				log.info("Count1 does not match... {} and {}",count_stat1,headerVO.getCountstat1());
				throw new InvalidRecordCountException("COUNT_STAT1 does not match.....");
			}
			if(count_stat2 != Integer.valueOf(headerVO.getCountstat2())) {
				log.info("Count2 does not match... {} and {}",count_stat2,headerVO.getCountstat2());
				throw new InvalidRecordCountException("COUNT_STAT2 does not match...");
			}
			if(count_stat3 != Integer.valueOf(headerVO.getCountstat3())) {
				log.info("Count3 does not match... {} and {}",count_stat3,headerVO.getCountstat3());
				throw new InvalidRecordCountException("COUNT_STAT3 does not match...");
			}
			if(count_stat4 != Integer.valueOf(headerVO.getCountstat4())) {
				log.info("Count4 does not match... {} and {}",count_stat4,headerVO.getCountstat4());
				throw new InvalidRecordCountException("COUNT_STAT4 does not match...");
			}
		}finally {
			count_stat1 = 0;
			count_stat2 = 0;
			count_stat3 = 0;
			count_stat4 = 0;
		}
		
		log.info("counts:: {} {} {} {}",  count_stat1, count_stat2, count_stat3, count_stat4);
	}

	
	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		log.info("Preparing Ack file metadata...");

		AckFileWrapper ackObj = new AckFileWrapper();
		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(ItagConstants.MTA_FILE_PREFIX)
				.append(ItagConstants.UNDER_SCORE_CHAR).append(fileName.replace('.', '_'))
				.append(ItagConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ItagConstants.VERSION).append(ItagConstants.MTA_FILE_PREFIX).append(fileName.substring(0, 4))
				.append(StringUtils.rightPad(fileName, 50, "")).append(genericValidation.getTodayTimeStampAck().concat("Z"))
				.append(ackStatusCode.getCode()).append("\n");

		
		log.debug("Ack file name: {}", sbFileContent.toString());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);

		log.debug("Ack file content: {}", sbFileContent.toString());
		if (ackStatusCode.equals(AckStatusCode.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT ) || ackStatusCode.equals(AckStatusCode.THRESHOLD_EXCEED )) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(ItagConstants.ITAG);
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(ItagConstants.MTA_FILE_PREFIX);
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
//		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss"); 
		//ackFileInfoDto.setAckFileTime(fileTime);
		LocalDateTime acktime =timeZoneConv.currentTime();  
		String ackfileTime = genericValidation.getTodayTimestamp(acktime, "HH:mm:ss");
		//ackFileInfoDto.setAckFileTime(timeZoneConv.currentTime().toLocalTime().toString()); 
		ackFileInfoDto.setAckFileTime(ackfileTime);
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}

	public boolean validateFileAgencyforAway(String fileName) throws InvalidFileNameException {

		filePrefix = fileName.substring(0, 4);
		Boolean devicePresent = false;
		
		log.info("Getting valid list for away agency..");
		validAgencyInfoList = masterDataCache.getagencyInfoVOListFromMasterCache();
		
		log.info("Checking if file prefix {} is valid for agency.. ", filePrefix);
		Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream().filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();
		
		if (agencyInfoVO.isPresent()) {
			agencyInfoVO.get().setFileProcessingStatus(ItagConstants.YES);
			log.info("Valid prefix for file: {}", fileName);
			devicePresent = true;
		}
		return devicePresent;
	}
	
	public boolean validateFileAgencyforHome(String fileName) throws InvalidFileNameException {

		filePrefix = fileName.substring(0, 4);
		Boolean devicePresent = false;
		
		log.info("Getting valid list for home agency..");
		validAgencyInfoList = masterDataCache.getAgencyInfoVOListHome();
		
		log.info("Checking if file prefix {} is valid for agency.. ", filePrefix);
		Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream().filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();
		
		if (agencyInfoVO.isPresent()) {
			agencyInfoVO.get().setFileProcessingStatus(ItagConstants.YES);
			log.info("Valid prefix for file: {}", fileName);
			devicePresent = true;
		}
		return devicePresent;
	}
	

	public boolean validateTagPrefix(String devicetag) {
		
		Boolean isDevicePrefixValid = false;
		
		String devicePrefix = devicetag.substring(0, 3);
		if(devicePrefix.equals(filePrefix)) {
			isDevicePrefixValid=true;
		}
		return isDevicePrefixValid;
	}
	
	/*
	@Override
	public void getMissingFilesForAgency() {

		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
				.filter(p -> p.getFileProcessingStatus() == ItagConstants.NO).collect(Collectors.toList());

		log.info("Missing files list : " + missingFiles);

	}
	*/
	
	
	
	@Override
	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if(fileExtension.equalsIgnoreCase(ItagConstants.ITAG)) {
			log.info("File type is valid for file {} ",file.getName());
		}else {
			throw new InvalidFileTypeException("Invalid file type for file "+ file.getName() );
		}
	}
	
	/*
	@Override
    public void getMissingFilesForAgency() {

        List<AgencyInfoVO> missingFiles;
        try {
            missingFiles = validAgencyInfoList.stream()
                    .filter(p -> p.getFileProcessingStatus() == ItagConstants.NO).collect(Collectors.toList());
            
            File destFile = new File(getConfigurationMapping().getFileWorking,"MissingFileExcel.xlsx");
            System.out.println("Destination file: "+destFile);
            if (destFile.exists()) {
                destFile.delete();
            }
            //Workbook object
            XSSFWorkbook workbook = new XSSFWorkbook();
            
            //Spreadsheet object
            XSSFSheet spreadsheet = workbook.createSheet("Missing_files"); 
       
            //Creating a row object
            XSSFRow row;
      
            //This data needs to be written (Object[])
            Map<String, Object[]> agencyData = new TreeMap<String, Object[]>;
                
            int rowId=0;
            agencyData.put(String.valueOf(rowId),new Object[] {"AGENCY Id", "AGENCY NAME", "AGENCY SHORT NAME", "CONSORTIUM", "DEVICE PREFIX", "IS HOME AGENCY", "FILE PREFIX" });
            for(AgencyInfoVO agency: missingFiles) {
                rowId++;
                agencyData.put(String.valueOf(rowId), new Object[] {agency.getAgencyId(),agency.getAgencyName(),agency.getAgencyShortName(), agency.getConsortium(),agency.getDevicePrefix(), agency.getIsHomeAgency(), agency.getFilePrefix()});
            }
            
            Set<String> keyid = agencyData.keySet();
            int SNO = 0;
            
            //Writing the data into the sheets...
            for (String key : keyid) {
                row = spreadsheet.createRow(SNO);
                Object[] objectArr = agencyData.get(key);
                int cellid = 0;
      
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid);
                    cell.setCellValue(String.valueOf(obj));
                    cellid++;
                }
                SNO++;
            }
            
            //Writing the workbook into the file...
            FileOutputStream out = new FileOutputStream(new File("D:\\WorkingDirectory\\MissingFileExcel.xlsx"));  //destFile
            workbook.write(out);
            out.close();
        log.info("Missing agencies are listed in file: {}", destFile.getAbsoluteFile() );
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
