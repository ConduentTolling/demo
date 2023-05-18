package com.conduent.tpms.iag.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
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
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ItguConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ITGUDetailInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.dto.ITGUHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.FullFileDateTimeMismatchException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.utility.Convertor;
import com.google.common.io.Files;

@Component
public class GenericITguFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(GenericITguFileParserImpl.class);

	private FileNameDetailVO fileNameVO;
	private ITGUDetailInfoVO detailVO;
	private ITGUHeaderInfoVO headerVO;
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

	private List<ITGUDetailInfoVO> detailVOList;
	/**
	 * Initialize the ITAG file properties
	 */
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;
	
	@Override
	public void initialize() {

		log.info("Initializing ITAG file..");
		setFileType(ItguConstants.ITGU);
		setFileFormat(ItguConstants.FIXED);
	//	setAgencyId(ItagConstants.MTA_FILE_PREFIX); 
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);

		fileNameVO = new FileNameDetailVO();
		detailVO = new ITGUDetailInfoVO();
		headerVO = new ITGUHeaderInfoVO();
		detailVOList = new ArrayList<ITGUDetailInfoVO>();
	
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ItguConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new ITGUDetailInfoVO();
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, FullFileDateTimeMismatchException, ParseException {

		switch (fMapping.getFieldName()) {
		case ItguConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case ItguConstants.F_FILE_DATE_TIME:
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case ItguConstants.F_FILE_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case ItguConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		case ItguConstants.H_VERSION:
			headerVO.setVersion(value);
			break;
		case ItguConstants.H_FROM_AGENCY_ID:
			headerVO.setFromAgencyId(value);
			break;
		case ItguConstants.H_FILE_DATE_TIME:
			LocalDateTime hdrdateTime= genericValidation.getFormattedDateTimeNew(value);
			if(customDateValidationNew(hdrdateTime)) {
				headerVO.setFiledatetime(hdrdateTime);
			}
			break;
		case ItguConstants.H_PREV_FILE_DATE_TIME:
			LocalDateTime hdrdateTime1= genericValidation.getFormattedDateTimeNew(value);
			InterAgencyFileXferDto info = tQatpMappingDao.getFullFileDetails();
			
			if(hdrdateTime1.toString().substring(0,10).equals(info.getFileDate().toString()) && 
					hdrdateTime1.toString().substring(11,19).equals(info.getFileTimeString()))
			{
				headerVO.setPrevfiledatetime(hdrdateTime1);
			}
			else
			{
				log.info("Full File Date/Time {} does not match with current file's PREV_FILE_DATE_TIME field {}",(info.getFileDate()+info.getFileTimeString()),hdrdateTime1);
				throw new FullFileDateTimeMismatchException("Full File Date/Time mismatch");
			}
			break;
		case ItguConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case ItguConstants.D_TAG_AGENCY_ID:
			detailVO.setTagAgencyId(value);
			break;
		case ItguConstants.D_TAG_SERIAL_NUMBER:
			detailVO.setTagSerialNumber(value);
			break;
		case ItguConstants.D_TAG_STATUS:
			detailVO.setTagStatus(value);
			break;
		case ItguConstants.D_TAG_ACCT_INFO:
		    detailVO.setTagAccntInfo(value);
		    break;
		case ItguConstants.D_TAG_HOME_AGENCY:
		    detailVO.setTagHomeAgency(value);
		    break;
		case ItguConstants.D_TAG_AC_TYPE_IND:
			detailVO.setTagACTypeIND(value);
			 break;
		case ItguConstants.D_TAG_ACCOUNT_NO:
			String valueacct=removeLeadingZeroes(value);
			detailVO.setTagAccntno(valueacct);
			
			 break;
		case ItguConstants.D_TAG_PROTOCOL:
			detailVO.setTagProtocol(value.trim());
			 break;
		case ItguConstants.D_TAG_TYPE:
			detailVO.setTagType(value);
			 break;
		case ItguConstants.D_TAG_MOUNT:
			detailVO.setTagMount(value);
			 break;
		case ItguConstants.D_TAG_CLASS:
			String valuetag= removeLeadingZeroes(value);
			detailVO.setTagClass(valuetag);
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
			log.info("Header Date{} is not matching with File Date", value);
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
			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException,DateTimeParseException, FullFileDateTimeMismatchException, ParseException {

		super.validateAndProcessFileName(fileName);

		log.info("Checking file process status {}.. ", fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);
		
		filePrefix = fileName.substring(0, 4);
		
		if(filePrefix != ItguConstants.MTA_FILE_PREFIX && !filePrefix.equals(ItguConstants.MTA_FILE_PREFIX)) 
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
			log.info("Invalid file prefix in file name:{} for away agency id: {}", fileName, filePrefix);
			throw new InvalidFileNameException("Invalid file prefix in file name: "+fileName);
		}
			
		
	}
	
	@Override
	public void finishFileProcess() throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException 
	{
		fileNameVO.setFileName(getFileName());
		
		insertBatchAndUpdateCheckpoint();
		
		saveFileToWorkingDir(fileNameVO.getFileName());
	}

	
	public void saveFileToWorkingDir(String workingfilename) {
		log.info("Load a copy of new file {} into working directory...", workingfilename);
		String fileUri = getConfigurationMapping().getFileInfoDto().getWorkingDir().concat("//").concat(workingfilename);
		 
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
	
	List<ITGUDetailInfoVO> detailVOListTemp;
	public void insertBatchAndUpdateCheckpoint() throws InvalidRecordException{
		
		log.info("Initial detailVOList size::: {}",detailVOList.size());
		ITGUDetailInfoVO[] records = new ITGUDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);

		Integer counter = 1;
		recordCounter=1;
		Integer preProcessedRecordCount = 0;
		ITGUDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<ITGUDetailInfoVO>();
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
						
						if (counter == ItguConstants.BATCH_RECORD_COUNT) {
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
	
	public void insertRecords() 
	{
		if(detailVOListTemp!=null && !detailVOListTemp.isEmpty() && detailVOListTemp.size() > 0) 
		{
			insertDeviceTagDetails(detailVOListTemp);
			detailVOListTemp.clear();
		}
		
	}

	public void insertDeviceTagDetails(List<ITGUDetailInfoVO> detailVOList) {

		List<TagDeviceDetails> tagDeviceDetailsList = new ArrayList<TagDeviceDetails>();
		TagDeviceDetails tagDeviceDetails = new TagDeviceDetails();

		//check if device_no is already present in T_OA_DEVICES Table
		//If yes..then update else insert
		for (ITGUDetailInfoVO detailVOObj : detailVOList) 
		{
			String deviceNo = detailVOObj.getTagAgencyId().concat(detailVOObj.getTagSerialNumber());
			LocalDate enddate = tQatpMappingDao.getenddate(deviceNo);
			
			TagDeviceDetails existingDeviceNoInfo = tQatpMappingDao.checkDeviceInfoInToaDeviceTable(deviceNo);
			
			if(existingDeviceNoInfo!=null)
			{
				String existingDeviceNo = existingDeviceNoInfo.getDeviceNo();
				
				//compare fields and if one also field is different then update
				//detailVOObj.getTagHomeAgency().equals(existingDeviceNoInfo.getTagHomeAgency()
				if( 
						tQatpMappingDao.getAgencyId(filePrefix).equals(existingDeviceNoInfo.getTagHomeAgency()) &&
						(  (detailVOObj.getTagACTypeIND().equals(existingDeviceNoInfo.getTagAcTypeInd())) || (detailVOObj.getTagACTypeIND().equals("*")) )&&
						(  (detailVOObj.getTagProtocol().equals(existingDeviceNoInfo.getTagProtocol())) || (detailVOObj.getTagProtocol().equals("***")) )&&
						(  (detailVOObj.getTagType().equals(existingDeviceNoInfo.getTagType())) || (detailVOObj.getTagType().equals("*"))  ) &&
						(  (detailVOObj.getTagMount().equals(existingDeviceNoInfo.getTagMount()))  || (detailVOObj.getTagMount().equals("*")) ) &&
						(  (detailVOObj.getTagClass().equals(existingDeviceNoInfo.getTagClass()))  || (detailVOObj.getTagClass().equals("****")) ) &&
						detailVOObj.getTagStatus().equals(existingDeviceNoInfo.getIagTagStatus())&&
					  //(   (detailVOObj.getTagAccntno().replace("0","").equals(existingDeviceNoInfo.getTagAccountNo().replace("0","")))  || (detailVOObj.getTagAccntno().equals("**************************************************"))  ) 
						(   (detailVOObj.getTagAccntno().equals(existingDeviceNoInfo.getTagAccountNo()))  || (detailVOObj.getTagAccntno().equals("**************************************************"))  )  
				 )
				{
					log.info("Existing info for device_no {} is same as current info",existingDeviceNo);
				}
				else
				{
					//int totalRecords = tQatpMappingDao.updateToaDeviceTable(detailVOObj,xferControl.getXferControlId());
					
					String agencyId = tQatpMappingDao.getAgencyId(filePrefix);
					//TAG_AC_TYPE_IND, TAG_PROTOCOL, TAG_TYPE, TAG_MOUNT, TAG_CLASS
				    tagDeviceDetails.setDeviceNo(deviceNo);
					tagDeviceDetails.setTagHomeAgency(agencyId); 
					tagDeviceDetails.setFileAgencyId(agencyId);
					
					if(detailVOObj.getTagAccntno().equals("**************************************************")) {
						tagDeviceDetails.setTagAccountNo(null);
					}else {
						//tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno().replace("0",""));
						tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					}
					if(detailVOObj.getTagACTypeIND().equals("*"))
					{
						tagDeviceDetails.setTagAcTypeInd(null);
					}
					else
					{
						tagDeviceDetails.setTagAcTypeInd(detailVOObj.getTagACTypeIND());
					}
					
					if(detailVOObj.getTagProtocol().equals("***"))
					{
						tagDeviceDetails.setTagProtocol(null);
					}
					else
					{
						tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
					}
					
					if(detailVOObj.getTagType().equals("*"))
					{
						tagDeviceDetails.setTagType(null);
					}
					else
					{
						tagDeviceDetails.setTagType(detailVOObj.getTagType());
					}
					
					if(detailVOObj.getTagMount().equals("*"))
					{
						tagDeviceDetails.setTagMount(null);
					}
					else
					{
						tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
					}
					
					if(detailVOObj.getTagClass().equals("****"))
					{
						tagDeviceDetails.setTagClass(null);
					}
					else
					{
						tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
					}
					tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
					tagDeviceDetails.setIagTagStatus(detailVOObj.getTagStatus());
					tagDeviceDetails.setInfileInd(ItguConstants.INFILE_IND);
					tagDeviceDetails.setInvalidAddressId(0);
				    tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
					tagDeviceDetails.setLastFileTs(fileNameVO.getFileDateTime());
					tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					tagDeviceDetails.setRecord(detailVOObj.getLine());
					
					int updtrecord =tQatpMappingDao.updateenddate(tagDeviceDetails, enddate);
					log.info("Updated {} rows of T_OA_DEVICES Table", updtrecord);
					
					tagDeviceDetailsList.add(tagDeviceDetails);
					int[] totalRecords = tQatpMappingDao.insertTagDeviceDetailsforAway(tagDeviceDetailsList);
					log.info("Inserted {} records in T_OA_DEVICES for device no {}", totalRecords,deviceNo);
					tagDeviceDetailsList.clear();
				}
				
			}
			else
			{
				String agencyId = tQatpMappingDao.getAgencyId(filePrefix);
				//TAG_AC_TYPE_IND, TAG_PROTOCOL, TAG_TYPE, TAG_MOUNT, TAG_CLASS
			    tagDeviceDetails.setDeviceNo(deviceNo);
				tagDeviceDetails.setTagHomeAgency(agencyId); 
				tagDeviceDetails.setFileAgencyId(agencyId);
				//tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno().replace("0",""));
				
				if(detailVOObj.getTagAccntno().equals("**************************************************")) {
					tagDeviceDetails.setTagAccountNo(null);
				}else {
					//tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno().replace("0",""));
					tagDeviceDetails.setTagAccountNo(detailVOObj.getTagAccntno());
					
				}
				
				
				if(detailVOObj.getTagACTypeIND().equals("*"))
				{
					tagDeviceDetails.setTagAcTypeInd(null);
				}
				else
				{
					tagDeviceDetails.setTagAcTypeInd(detailVOObj.getTagACTypeIND());
				}
				
				if(detailVOObj.getTagProtocol().equals("***"))
				{
					tagDeviceDetails.setTagProtocol(null);
				}
				else
				{
					tagDeviceDetails.setTagProtocol(detailVOObj.getTagProtocol());
				}
				
				if(detailVOObj.getTagType().equals("*"))
				{
					tagDeviceDetails.setTagType(null);
				}
				else
				{
					tagDeviceDetails.setTagType(detailVOObj.getTagType());
				}
				
				if(detailVOObj.getTagMount().equals("*"))
				{
					tagDeviceDetails.setTagMount(null);
				}
				else
				{
					tagDeviceDetails.setTagMount(detailVOObj.getTagMount());
				}
				
				if(detailVOObj.getTagClass().equals("****"))
				{
					tagDeviceDetails.setTagClass(null);
				}
				else
				{
					tagDeviceDetails.setTagClass(detailVOObj.getTagClass());
				}
				tagDeviceDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
				tagDeviceDetails.setEndDate(LocalDate.of(2099, 12, 31));
				tagDeviceDetails.setIagTagStatus(detailVOObj.getTagStatus());
				tagDeviceDetails.setInfileInd(ItguConstants.INFILE_IND);
				tagDeviceDetails.setInvalidAddressId(0);
			    tagDeviceDetails.setXferControlID(xferControl.getXferControlId());
				tagDeviceDetails.setLastFileTs(fileNameVO.getFileDateTime());
				tagDeviceDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				tagDeviceDetails.setRecord(detailVOObj.getLine());
			
				tagDeviceDetailsList.add(tagDeviceDetails);
				int[] totalRecords = tQatpMappingDao.insertTagDeviceDetailsforAway(tagDeviceDetailsList);
				
				log.info("Inserted {} records in T_OA_DEVICES for device no {}", totalRecords,deviceNo);
				tagDeviceDetailsList.clear();
				
			}
		}
		setFileStatus(ItguConstants.PROCESS_STATUS_COMPLETE);
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
		fileDetails.setFileType(ItguConstants.ITGU);
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 4));
		fileDetails.setProcessId((long) ItguConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ItguConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setSerialNumber((long) ItguConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setProcessedCount(ItguConstants.ZERO);
		fileDetails.setSuccessCount(ItguConstants.ZERO);
		fileDetails.setExceptionCount(ItguConstants.ZERO);
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
		sbFileName.append(ItguConstants.MTA_FILE_PREFIX)
				.append(ItguConstants.UNDER_SCORE_CHAR).append(fileName.replace('.', '_'))
				.append(ItguConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ItguConstants.VERSION).append(ItguConstants.MTA_FILE_PREFIX).append(fileName.substring(0, 4))
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
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT) || 
				ackStatusCode.equals(AckStatusCode.FULL_FILE_DATE_TIME_MISMATCH)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(ItguConstants.ITGU);
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(ItguConstants.MTA_FILE_PREFIX);
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss");
		//ackFileInfoDto.setAckFileTime(fileTime);
		LocalDateTime acktime =timeZoneConv.currentTime();
		String ackfileTime = genericValidation.getTodayTimestamp(acktime, "hh:mm:ss");
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
			agencyInfoVO.get().setFileProcessingStatus(ItguConstants.YES);
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
			agencyInfoVO.get().setFileProcessingStatus(ItguConstants.YES);
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
		if(fileExtension.equalsIgnoreCase(ItguConstants.ITGU)) {
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
