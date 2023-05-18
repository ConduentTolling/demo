//package com.conduent.tpms.iag.validation;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.LineNumberReader;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.conduent.tpms.iag.constants.AckStatusCode;
//import com.conduent.tpms.iag.constants.FileProcessStatus;
//import com.conduent.tpms.iag.constants.IITCConstants;
//import com.conduent.tpms.iag.dto.AckFileInfoDto;
//import com.conduent.tpms.iag.dto.AckFileWrapper;
//import com.conduent.tpms.iag.dto.FileNameDetailVO;
//import com.conduent.tpms.iag.dto.IITCDetailInfoVO;
//import com.conduent.tpms.iag.dto.IITCHeaderInfoVO;
//import com.conduent.tpms.iag.dto.MappingInfoDto;
//import com.conduent.tpms.iag.exception.EmptyLineException;
//import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
//import com.conduent.tpms.iag.exception.InvalidFileNameException;
//import com.conduent.tpms.iag.exception.InvalidFileTypeException;
//import com.conduent.tpms.iag.exception.InvalidRecordCountException;
//import com.conduent.tpms.iag.exception.InvalidRecordException;
//import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
//import com.conduent.tpms.iag.model.AgencyInfoVO;
//import com.conduent.tpms.iag.model.CustomerAddressRecord;
//import com.conduent.tpms.iag.model.FileDetails;
//import com.conduent.tpms.iag.model.TagDeviceDetails;
//import com.conduent.tpms.iag.model.ZipCode;
//import com.conduent.tpms.iag.utility.Convertor;
//import com.google.common.io.Files;
//
//@Component
//public class InvalidTagFileParserImpl extends FileParserImpl {
//
//	private static final Logger log = LoggerFactory.getLogger(InvalidTagFileParserImpl.class);
//	private FileNameDetailVO fileNameVO;
//	private IITCDetailInfoVO detailVO;
//	private IITCHeaderInfoVO headerVO;
//	protected Integer fileCount = 0;
//	protected String filePrefix;
//	protected List<AgencyInfoVO> validAgencyInfoList;
//	protected List<CustomerAddressRecord> customerAddressRecordList;
//	protected int skipcounter = 0;
//	protected int successCounter = 0;
//	protected List<IITCDetailInfoVO> detailVOList;
//	protected List<ZipCode> zipCodeList;
//
//	private String zipCodeNew;
//
//	public String getZipCodeNew() {
//		return zipCodeNew;
//	}
//
//	public void setZipCodeNew(String zipCodeNew) {
//		this.zipCodeNew = zipCodeNew;
//	}
//
//	/**
//	 * Initialize the IITC file properties
//	 */
//	@Override
//	public void initialize() {
//
//		log.info("Initializing IITC file..");
//		setFileType(IITCConstants.IITC);
//		setFileFormat(IITCConstants.FIXED);
//		setAgencyId(IITCConstants.HOME_AGENCY_ID);
//		setIsHederPresentInFile(true);
//		setIsDetailsPresentInFile(true);
//		setIsTrailerPresentInFile(false);
//
//		fileNameVO = new FileNameDetailVO();
//		detailVO = new IITCDetailInfoVO();
//		headerVO = new IITCHeaderInfoVO();
//
//		detailVOList = new ArrayList<IITCDetailInfoVO>();
//		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
//		zipCodeList = masterDataCache.getZipCodeList();
//		skipcounter = 0;
//		successCounter = 0;
//	}
//
//
//	/**
//	 * Process start of Line
//	 * 
//	 */
//	@Override
//	public void processStartOfLine(String fileSection) {
//		log.info("Process start of line....");
//		if (IITCConstants.DETAIL.equalsIgnoreCase(fileSection)) {
//			detailVO = new IITCDetailInfoVO();
//		}
//	}
//	
//
//	/**
//	 * Validation for file,header,detail
//	 * 
//	 */
//	@Override
//	public void doFieldMapping(String value, MappingInfoDto fMapping)
//			throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException {
//
//		switch (fMapping.getFieldName()) {
//		case IITCConstants.F_FROM_AGENCY_ID:
//			fileNameVO.setFromAgencyId(value);
//			break;
//		case IITCConstants.F_FILE_DATE_TIME:
//			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
//			break;
//		case IITCConstants.F_FILE_EXTENSION:
//			fileNameVO.setFileType(value.replace(".", ""));
//			break;
//		case IITCConstants.H_FILE_TYPE:
//			headerVO.setFileType(value);
//			break;
//		case IITCConstants.H_FROM_AGENCY_ID:
//			if (validateAgencyMatch(value)) {
//				headerVO.setFromAgencyId(value);
//			} else {
//				throw new InvlidFileHeaderException("Header agency id mismatch..");
//			}
//			break;
//		case IITCConstants.H_FILE_DATE:
//			LocalDate date = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
//			if (customDateValidation(date)) {
//				headerVO.setFileDate(date);
//			}
//			break;
//		case IITCConstants.H_FILE_TIME:
//			if (customTimeValidation(value)) {
//				headerVO.setFileTime(value);
//			}
//			break;
//		case IITCConstants.H_RECORD_COUNT:
//			headerVO.setRecordCount(value);
//			break;
//		case IITCConstants.D_CUST_TAG_AGENCY_ID:
//			detailVO.setCustTagAgencyId(value);
//			break;
//		case IITCConstants.D_CUST_TAG_SERIAL:
//			detailVO.setCustTagSerial(value);
//			break;
//		case IITCConstants.D_CUST_LAST_NAME:
//			detailVO.setCustLastName(value);
//			break;
//		case IITCConstants.D_CUST_FIRST_NAME:
//			detailVO.setCustFirstName(value);
//			break;
//		case IITCConstants.D_CUST_MIDDLE_NAME:
//			detailVO.setCustMi(value);
//			break;
//		case IITCConstants.D_CUST_COMPANY:
//			detailVO.setCustCompany(value);
//			break;
//		case IITCConstants.D_CUST_ADDR_1:
//			detailVO.setCustAddr1(value);
//			break;
//		case IITCConstants.D_CUST_ADDR_2:
//			detailVO.setCustAddr2(value);
//			break;
//		case IITCConstants.D_CUST_CITY:
//			detailVO.setCustCity(value);
//			break;
//		case IITCConstants.D_CUST_STATE:
//			detailVO.setCustState(value);
//			break;
//		case IITCConstants.D_CUST_ZIP:
//			detailVO.setCustZip(value);
//			break;
//
//		default:
//
//		}
//		log.debug("Mapping done for value: {} with field: {}", value, fMapping);
//	}
//
//
//	/**
//	 * Agency Id validation
//	 * @return true if match
//	 */
//	private boolean validateAgencyMatch(String agencyId) throws InvlidFileHeaderException {
//		if (filePrefix.equals(agencyId)) {
//			return true;
//		} else {
//			log.info("Agency id {} mismatch with file agency id {}.", agencyId, filePrefix);
//			return false;
//		}
//	}
//
//
//	/**
//	 * Customer date validation
//	 * @return true if matches
//	 */
//	public boolean customDateValidation(LocalDate value) throws InvlidFileHeaderException {
//
//		if (!fileNameVO.getFileDateTime().toLocalDate().equals(value)) {
//			log.info("Header Date{} is not matching with Header Date", value);
//			throw new InvlidFileHeaderException("Date mismatch for File and Header");
//		}
//		return true;
//	}
//
//
//	/**
//	 * Customer date validation
//	 * @return true if matches
//	 */
//	public boolean customTimeValidation(String value) throws InvlidFileHeaderException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
//		String time = formatter.format(fileNameVO.getFileDateTime().toLocalTime());
//		if (!time.equals(value)) {
//			log.info("Header Time{} is not matching with Header Time", value);
//			throw new InvlidFileHeaderException("Time mismatch for File and Header");
//		}
//		return true;
//	}
//	
//	
//	/**
//	 * Validation for file process
//	 * @throws exception of file already processed
//	 * @throws exception if Invalid file name
//	 */
//	@Override
//	public void validateAndProcessFileName(String fileName)
//			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException,
//			EmptyLineException, InvalidRecordException, DateTimeParseException {
//
//		super.validateAndProcessFileName(fileName);
//		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);
//
//		if (validateFileAgency(fileName)) {
//			log.info("{} is valid for home agency.", fileName);
//
//			if (fileDetails == null) {
//				log.info("{} is not processed earlier.", fileName);
//				insertFileDetailsIntoCheckpoint();
//			} else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) {
//				log.info("{} is already processed.", fileName);
//				throw new FileAlreadyProcessedException("File already processed");
//			}
//		} else {
//			log.info("Invalid file prefix in file name:{} for home agency id: {}", fileName,
//					IITCConstants.HOME_AGENCY_ID);
//			throw new InvalidFileNameException("Invalid file prefix in file name: " + fileName);
//		}
//	}
//
//	/**
//	 * Finish Process
//	 * sets the data which we get from validate and add records
//	 */
//	@Override
//	public void finishFileProcess()
//			throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException {
//
//		log.debug("Inserted into finish process.......");
//
//		fileNameVO.setFileName(getFileName());
//
//		validateAndAddRecords(detailVOList);
//
//		fileDetails.setFileName(getFileName());
//		fileDetails.setFileType(fileNameVO.getFileType().trim());
//		fileDetails.setFileCount(Integer.parseInt(headerVO.getRecordCount()));
//		fileDetails.setUpdateTs(LocalDateTime.now());
//		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
//		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
//		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
//		fileDetails.setProcessedCount(successCounter);
//		fileDetails.setSuccessCount(successCounter);
//		fileDetails.setExceptionCount(skipcounter);
//		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
//		detailVOList.clear();
//		successCounter = 0;
//		skipcounter = 0;
//	}
//
//	/**
//	 * Check for various validations
//	 * If the validations are true adds the record in table
//	 * If false skips the record
//	 */
//	public void validateAndAddRecords(List<IITCDetailInfoVO> detailVOList2) throws InvalidRecordException {
//
//		log.info("Checking for validations.....");
//		
//		List<CustomerAddressRecord> CustomerAddressRecordList = new ArrayList<CustomerAddressRecord>();
//		for (IITCDetailInfoVO iitcDetailInfoVO : detailVOList2) {
//			
//			CustomerAddressRecord customerAddressRecord = insertCustAddressDetails(iitcDetailInfoVO);
//			CustomerAddressRecordList.add(customerAddressRecord);
//		}
//		
//		List<CustomerAddressRecord> detailVOListTemp = new ArrayList<CustomerAddressRecord>();
//
//		for (CustomerAddressRecord record : CustomerAddressRecordList) {
//			
//			
//			if (record.getState().isEmpty() && record.getZipCode().isEmpty()) 
//			{
//				skipcounter++;
//				log.info("Skip Count...." + skipcounter);
//			} 
//			else if (record.getStreet1().isEmpty()) 
//			{
//				skipcounter++;
//				log.info("Skip Count...." + skipcounter);
//			} 
//			else if (record.getLastName().isEmpty() && record.getFirstName().isEmpty() && record.getCompanyName().isEmpty()) 
//			{
//				skipcounter++;
//				log.info("Skip Count......" + skipcounter);
//			} 
//			else if(record.getZipCode() == null){
//				
//				skipcounter++;
//				log.info("Skip Count...." + skipcounter);
//			}
//			else if (!record.getZipCode().isEmpty() && validatZipPresent(record)) 
//			{
//				CustomerAddressRecord CustomerAddressRecord = tQatpMappingDao.checkifrecordexists(record);
//				String device_no = record.getCustTagAgencyId().concat(record.getCustTagSerial());
//				if (CustomerAddressRecord != null) {
//					int address_id = CustomerAddressRecord.getInvalidAddressId();
//					log.info("address_id::" + address_id);
//
//					tQatpMappingDao.updateInvalidAddressIdinDevices(device_no, address_id);
//					successCounter++;
//				} 
//				else if (CustomerAddressRecord == null) {
//					TagDeviceDetails tagDeviceDetails = tQatpMappingDao.checkRecordExistInDevice(device_no);
//					if (tagDeviceDetails != null) {
//						detailVOListTemp.add(record);
//					} 
//					else 
//					{
//						skipcounter++;
//						log.info("Valid device number does not exists in device table {}", device_no);
//						log.info("Skip Count...." + skipcounter);
//					}
//				}
//			} 
//			else 
//			{
//				log.info("Skipping the record for customer name {}", record.getLastName());
//				skipcounter++;
//			}
//		
//		
//		if(detailVOListTemp != null) {
//			if (detailVOListTemp.size() > 0) {
//				int[] totalRecords = tQatpMappingDao.insertInvalidCustomerRecord(detailVOListTemp);
//				
//				CustomerAddressRecord CustomerAddressRecord = tQatpMappingDao.checkifrecordexists(record);
//				String device_no = record.getCustTagAgencyId().concat(record.getCustTagSerial());
//				int address_id = CustomerAddressRecord.getInvalidAddressId();
//				
//				tQatpMappingDao.updateInvalidAddressIdinDevices(device_no, address_id);
//				successCounter = successCounter + totalRecords.length;
//				log.info("Total records inserted in address table : {}", totalRecords);
//			}
//		}
//		detailVOListTemp.clear();
//	}
//}
//	
//	/**
//	 * Zip code validation
//	 * @return if zip code is present
//	 */
//	private boolean validatZipPresent(CustomerAddressRecord custm) {
//		boolean isZipPresent = false;
//		if(custm.getZipCode() != null) {
//			for (ZipCode zipcode : zipCodeList) {
//				if (zipcode.getZipcode() != null) {
//					if (zipcode.getZipcode().equals(custm.getZipCode())) {
//						isZipPresent = true;
//					}
//	
//				}
//			}
//		}
//		return isZipPresent;
//	}
//	
//	/**
//	 * Validate Threshold Limit
//	 * @throws inavlid header exception if file count exceeds the threshold limit
//	 */
//	public void validateThresholdLimit(File lastSuccFile) throws InvlidFileHeaderException {
//		int currentFileHeaderCount = Integer.parseInt(headerVO.getRecordCount());
//		log.info("The record count in currently processed file is: {}", currentFileHeaderCount);
//		int lastFileHeaderCount = 0;
//		if (lastSuccFile.exists()) {
//			try {
//				FileReader fileReader = new FileReader(lastSuccFile);
//				LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
//
//				while (lineNumberReader.getLineNumber() == 0) {
//					String header = lineNumberReader.readLine();
//					lastFileHeaderCount = Integer.valueOf(header.substring(21, 29));
//					log.info("The record count in last processed file is: {}", lastFileHeaderCount);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			int thresholdLimit = IITCConstants.THRESHOLD_LIMIT;
//			if (Math.abs(currentFileHeaderCount - lastFileHeaderCount) > thresholdLimit) {
//				log.info("File record count difference exceeded threshold limit:" + thresholdLimit);
//				throw new InvlidFileHeaderException(
//						"File record count difference exceeded threshold limit:" + thresholdLimit);
//			}
//		}
//	}
//
//	/**
//	 * Old processed file is latest on not
//	 * @return true if not latest
//	 * @return false is latest
//	 */
//	public boolean ifOldProcessedFileIsLatest(LocalDateTime newFileDateTime, String lastSuccFileDate) {
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//		LocalDateTime lastProcdateTime = LocalDateTime.parse(lastSuccFileDate, formatter);
//
//		if (lastProcdateTime.isAfter(newFileDateTime)) {
//			log.info("File {} is not latest ..", getFileName());
//			return true;
//		} else {
//			log.info("File {} is latest ..", getFileName());
//			return false;
//		}
//	}
//
//	@Override
//	public void processEndOfLine(String fileSection) {
//
//	}
//	
//	/**
//	 * Inserting customer information in address table
//	 * @return customer address if all the conditions are matched
//	 */
//	private CustomerAddressRecord insertCustAddressDetails(IITCDetailInfoVO detailVOObj) {
//
//		log.debug("Checking customer address details..");
//
//			CustomerAddressRecord customerAddressRecord = new CustomerAddressRecord();
//			
//			if(!detailVOObj.getCustFirstName().isEmpty()) {
//			customerAddressRecord.setFirstName(detailVOObj.getCustFirstName());
//			}else {
//				customerAddressRecord.setFirstName(IITCConstants.NULL);
//			}
//
//			if(!detailVOObj.getCustTagAgencyId().isEmpty()) {
//				customerAddressRecord.setCustTagAgencyId(detailVOObj.getCustTagAgencyId());
//			}
//			if(!detailVOObj.getCustTagSerial().isEmpty()) {
//				customerAddressRecord.setCustTagSerial(detailVOObj.getCustTagSerial());
//			}
//			
//			if (!detailVOObj.getCustLastName().isEmpty()) {
//				customerAddressRecord.setLastName(detailVOObj.getCustLastName());
//			} else if (!detailVOObj.getCustFirstName().isEmpty()) {
//				customerAddressRecord.setLastName(detailVOObj.getCustFirstName());
//				log.info("Last name taken is : {}", customerAddressRecord.getLastName());
//				customerAddressRecord.setFirstName(IITCConstants.NULL);
//			} else if (!detailVOObj.getCustCompany().isEmpty() && detailVOObj.getCustFirstName().isEmpty()) {
//				customerAddressRecord.setLastName(detailVOObj.getCustCompany());
//				log.info("Last name taken is :{}", customerAddressRecord.getLastName());
//			}
//			
//			if(!detailVOObj.getCustMi().isEmpty()) {
//				customerAddressRecord.setMiddleInitial(detailVOObj.getCustMi());
//			}else {
//					customerAddressRecord.setMiddleInitial(" ");
//			}
//			
//			if(!detailVOObj.getCustCompany().isEmpty()) {
//			customerAddressRecord.setCompanyName(detailVOObj.getCustCompany());
//			}else {
//				customerAddressRecord.setCompanyName(IITCConstants.NULL);
//			}
//			
//			if(!detailVOObj.getCustAddr2().isEmpty() &&  detailVOObj.getCustAddr1().isEmpty()) 
//			{
//				customerAddressRecord.setStreet1(detailVOObj.getCustAddr2());
//				detailVOObj.setCustAddr2(IITCConstants.NULL);
//			}else if(!detailVOObj.getCustAddr1().isEmpty()) 
//			{
//				customerAddressRecord.setStreet1(detailVOObj.getCustAddr1());
//			}else if(detailVOObj.getCustAddr1().isEmpty()) 
//			{
//				customerAddressRecord.setStreet1(detailVOObj.getCustAddr1());
//			}
//			
//			if(!detailVOObj.getCustAddr2().isEmpty()) {
//				customerAddressRecord.setStreet2(detailVOObj.getCustAddr2());
//				}else {
//					customerAddressRecord.setStreet2(IITCConstants.NULL);
//			}
//			
//			if(!detailVOObj.getCustCity().isEmpty()) {
//				customerAddressRecord.setCity(detailVOObj.getCustCity());
//				}else {
//					customerAddressRecord.setCity(IITCConstants.NULL);
//			}
//			
//			if(!detailVOObj.getCustState().isEmpty()) {
//				customerAddressRecord.setState(detailVOObj.getCustState());
//				}else {
//					customerAddressRecord.setState(IITCConstants.NULL);
//			}
//			
//			customerAddressRecord.setUpdateTs(LocalDateTime.now());
//			if (detailVOObj.getCustZip() != null) {
//
//				if (detailVOObj.getCustZip().length() == 10) {
//					customerAddressRecord.setZipCode(detailVOObj.getCustZip().substring(0, 6));
//					customerAddressRecord.setZipPlus4(detailVOObj.getCustZip().substring(6, 10));
//				}
//				if (detailVOObj.getCustZip().length() == 9) {
//					customerAddressRecord.setZipCode(detailVOObj.getCustZip().substring(0, 5));
//					customerAddressRecord.setZipPlus4(detailVOObj.getCustZip().substring(5, 9));
//				}
//				if (detailVOObj.getCustZip().length() == 5) {
//					customerAddressRecord.setZipCode(detailVOObj.getCustZip());
//					customerAddressRecord.setZipPlus4(IITCConstants.NULL);
//				}
//				if (detailVOObj.getCustZip().length() == 6) {
//					customerAddressRecord.setZipCode(detailVOObj.getCustZip());
//					customerAddressRecord.setZipPlus4(IITCConstants.NULL);
//				}
//				if(detailVOObj.getCustZip().length() <= 4) {
//					log.info("Invalid Zip Code in Record.....");
//				}
//
//			}
//			
//		return customerAddressRecord;
//	}
//	
//	/**
//	 * Process Detail
//	 * Adding the details in list
//	 */
//	@Override
//	public void processDetailLine() {
//		detailVOList.add(detailVO);
//	}
//
//	/**
//	 * Inserting file in checkpoint table
//	 */
//	@Override
//	public void insertFileDetailsIntoCheckpoint() {
//
//		log.debug("Inserting file details into lane_checkpoint table..");
//
//		fileDetails = new FileDetails();
//		fileDetails.setFileName(getFileName());
//		fileDetails.setFileType(IITCConstants.IITC);
//		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
//		fileDetails.setProcessName(getFileName().substring(0, 3));
//		fileDetails.setProcessId((long) IITCConstants.ZERO);
//		fileDetails.setProcessStatus(FileProcessStatus.START);
//		fileDetails.setLaneTxId((long) IITCConstants.ZERO);
//		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
//		fileDetails.setSerialNumber((long) IITCConstants.ZERO);
//		fileDetails.setProcessedCount(IITCConstants.ZERO);
//		fileDetails.setFileCount(recordCount);
//		fileDetails.setSuccessCount(IITCConstants.ZERO);
//		fileDetails.setExceptionCount(IITCConstants.ZERO);
//		fileDetails.setUpdateTs(LocalDateTime.now());
//		tQatpMappingDao.insertFileDetails(fileDetails);
//
//	}
//
//	/**
//	 * updating checkpoint table
//	 * 
//	 */
//	public void updateFileDetailsInCheckPoint() {
//
//		log.debug("Updating file {} details into checkpoint table with status C.", fileNameVO.getFileName());
//
//		fileDetails.setFileName(fileNameVO.getFileName().trim());
//		fileDetails.setFileType(fileNameVO.getFileType().trim());
//		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
//		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
//		fileDetails.setFileCount(recordCount.intValue());
//		fileDetails.setProcessedCount(recordCount.intValue());
//		fileDetails.setSuccessCount(recordCount.intValue());
//		fileDetails.setUpdateTs(LocalDateTime.now());
//		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
//
//	}
//
//	/**
//	 * Record count validation
//	 *@throws invalid record exception if not matches
//	 */
//	@Override
//	public void validateRecordCount() throws InvalidRecordCountException {
//		log.info("Header record count: {}", headerVO.getRecordCount());
//		log.info("Total record count from file: {}", recordCount);
//		if (recordCount != Convertor.toLong(headerVO.getRecordCount())) {
//			log.info("Record count mismatch from number of records in " + getFileName());
//			throw new InvalidRecordCountException("Invalid Record Count");
//		}
//	}
//	
//	/**
//	 * Ack preparation
//	 * @return ack status
//	 */
//	@Override
//	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
//		log.info("Preparing Ack file metadata...");
//
//		AckFileWrapper ackObj = new AckFileWrapper();
//		StringBuilder sbFileName = new StringBuilder();
//		sbFileName.append(IITCConstants.HOME_AGENCY_ID).append(IITCConstants.UNDER_SCORE_CHAR)
//				.append(fileName.replace('.', '_')).append(IITCConstants.ACK_EXT);
//
//		StringBuilder sbFileContent = new StringBuilder();
//
//		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(IITCConstants.HOME_AGENCY_ID)
//				.append(fileName.substring(0, 3)).append(StringUtils.rightPad(fileName, 50, ""))
//				.append(genericValidation.getTodayTimestamp()).append(ackStatusCode.getCode()).append("\n");
//
//		log.debug("Ack file name: {}", sbFileContent.toString());
//
//		ackObj.setAckFileName(sbFileName.toString());
//		ackObj.setSbFileContent(sbFileContent.toString());
//		ackObj.setFile(file);
//
//		log.debug("Ack file content: {}", sbFileContent.toString());
//		if (ackStatusCode.equals(AckStatusCode.SUCCESS)) {
//			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
//		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
//				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT)) {
//			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//		}
//
//		/* Prepare AckStatus table */
//		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
//		ackFileInfoDto.setAckFileName(sbFileName.toString());
//		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
//		ackFileInfoDto.setFileType(IITCConstants.IITC);
//		ackFileInfoDto.setTrxFileName(file.getName());
//		ackFileInfoDto.setFromAgency(getAgencyId());
//		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
//		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
//		ackFileInfoDto.setAckFileDate(LocalDate.now());
//		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss");
//		ackFileInfoDto.setAckFileTime(fileTime);
//		ackObj.setAckFileInfoDto(ackFileInfoDto);
//		return ackObj;
//	}
//
//	public boolean validateTagPrefix(String devicetag) {
//
//		Boolean isDevicePrefixValid = false;
//		String devicePrefix = devicetag.substring(0, 3);
//		if (devicePrefix.equals(filePrefix)) {
//			isDevicePrefixValid = true;
//		}
//		return isDevicePrefixValid;
//	}
//
//	/**
//	 * Missing files for agency
//	 * @return missing files list
//	 */
//	@Override
//	public void getMissingFilesForAgency() {
//
//		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
//				.filter(p -> p.getFileProcessingStatus() == IITCConstants.NO).collect(Collectors.toList());
//		log.info("Missing files list : " + missingFiles);
//	}
//
//	public void getMissingFilesForAgency1() {
//		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
//				.filter(p -> p.getFileProcessingStatus() == IITCConstants.NO).collect(Collectors.toList());
//
//		log.info("Missing files list : " + missingFiles);
//
//	}
//
//	/**
//	 * File agecny validation
//	 * @return device present or not
//	 */
//	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {
//
//		if (fileName != null) {
//
//			filePrefix = fileName.substring(0, 3);
//			Boolean devicePresent = false;
//			Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
//					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();
//
//			if (agencyInfoVO.isPresent()) {
//				agencyInfoVO.get().setFileProcessingStatus(IITCConstants.YES);
//				log.info("Valid prefix for file: {}", fileName);
//				devicePresent = true;
//			}
//			return devicePresent;
//		} else {
//			throw new InvalidFileNameException("File name is empty");
//		}
//	}
//
//	/**
//	 * File Type validation
//	 * @throws exception if doesn't matches
//	 */
//	@Override
//	public void CheckValidFileType(File file) throws InvalidFileTypeException {
//		String fileExtension = Files.getFileExtension(file.getName());
//		if (!fileExtension.equalsIgnoreCase(IITCConstants.IITC)) {
//			log.info("File extension is invalid for file {} ", file.getName());
//			throw new InvalidFileTypeException("Invalid file extension");
//		}
//	}
//
//}
