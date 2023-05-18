package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ICTXDetailInfoVO;
import com.conduent.tpms.iag.dto.ICTXHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.ZipCode;
import com.conduent.tpms.iag.utility.Convertor;
import com.google.common.io.Files;

@Component
public class IctxFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(IctxFileParserImpl.class);
	private static final LocalDate fileDate = null;
	private   FileNameDetailVO fileNameVO;
	private  ICTXDetailInfoVO detailVO ;
	private  ICTXHeaderInfoVO headerVO ;
	
	
	protected Integer fileCount = 0;
	protected String filePrefix;
	protected List<AgencyInfoVO> validAgencyInfoList;
	protected int skipcounter = 0;
	protected int successCounter = 0;
	protected List<ICTXDetailInfoVO> detailVOList;
	protected List<ZipCode> zipCodeList;

	private String zipCodeNew;

	public String getZipCodeNew() {
		return zipCodeNew;
	}

	public void setZipCodeNew(String zipCodeNew) {
		this.zipCodeNew = zipCodeNew;
	}
	
//	public IctxFileParserImpl(){
//		log.info("Initializing ICTX file..");
//		setFileType(ICTXConstants.ICTX);
//		setFileFormat(ICTXConstants.FIXED);
//		setAgencyId(ICTXConstants.HOME_AGENCY_ID);
//		setToAgencyId(ICTXConstants.NYSTA_AGENCY_ID);
//		setIsHederPresentInFile(true);
//		setIsDetailsPresentInFile(true);
//		setIsTrailerPresentInFile(false);
//		fileNameVO = new FileNameDetailVO();
//		detailVO = new ICTXDetailInfoVO();
//		headerVO = new ICTXHeaderInfoVO();
//
//		detailVOList = new ArrayList<ICTXDetailInfoVO>();
//		
//		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
////		zipCodeList = masterDataCache.getZipCodeList();
//		skipcounter = 0;
//		successCounter = 0;
//	}

	/**
	 * Initialize the IITC file properties
	 */
	@Override
	public void initialize() {

		log.info("Initializing ICTX file..");
		setFileType(ICTXConstants.ICTX);
		setFileFormat(ICTXConstants.FIXED);
		setAgencyId(ICTXConstants.HOME_AGENCY_ID);
		setToAgencyId(ICTXConstants.NYSTA_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
		fileNameVO = new FileNameDetailVO();
		detailVO = new ICTXDetailInfoVO();
		headerVO = new ICTXHeaderInfoVO();

		detailVOList = new ArrayList<ICTXDetailInfoVO>();
		
		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
//		zipCodeList = masterDataCache.getZipCodeList();
		skipcounter = 0;
		successCounter = 0;
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ICTXConstants.DETAIL.equalsIgnoreCase(fileSection)) {
//			detailVO = new ICTXDetailInfoVO();
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException {

		switch (fMapping.getFieldName()) {
		case ICTXConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case ICTXConstants.F_TO_AGENCY_ID:
			fileNameVO.setToAgencyId(value);
			break;
		case ICTXConstants.F_FILE_DATE_TIME:
		case ICTXConstants.F_DATE:			
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case ICTXConstants.F_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case ICTXConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		case ICTXConstants.H_FROM_AGENCY_ID:
			if (validateAgencyMatch(value)) {
				headerVO.setFromAgencyId(value);
			} else {
				throw new InvlidFileHeaderException("Header agency id mismatch..");
			}
			break;
		case ICTXConstants.H_TO_AGENCY_ID:
			headerVO.setToAgencyId(value);;
			break;
		case ICTXConstants.H_FILE_DATE:
			headerVO.setFileDate(fileDate);
			break;
		case ICTXConstants.H_FILE_TIME:
			headerVO.setFileTime(value);
			break;
		case ICTXConstants.H_RECORD_COUNT:
				headerVO.setRecordCount(value);
			break;
		case ICTXConstants.H_ICTX_FILE_NUM:
			headerVO.setIctxFileNum(value);
			break;
		
			
		case ICTXConstants.D_ETC_TRX_SERIAL_NUM:
				detailVO.setEtcTrxSerialNum(value);
			break;
		case ICTXConstants.D_ETC_REVENUE_DATE:
			detailVO.setEtcRevenuDate(value);
			break;
		case ICTXConstants.D_ETC_FAC_AGENCY:
			detailVO.setEtcFacAgency(value);
			break;
		case ICTXConstants.D_ETC_TRX_TYPE:
			detailVO.setEtcTrxType(value);
			break;
		case ICTXConstants.D_ETC_ENTRY_DATE:
			detailVO.setEtcEntryDate(value);
			break;
		case ICTXConstants.D_ETC_ENTRY_TIME:
			detailVO.setEtcEntryTime(value);
			break;
		case ICTXConstants.D_ETC_ENTRY_PLAZA:
			detailVO.setEtcEntryPlaza(value);
			break;
		case ICTXConstants.D_ETC_ENTRY_LANE:
			detailVO.setEtcEntryLane(value);
			break;
		case ICTXConstants.D_ETC_TAG_AGENCY:
			detailVO.setEtcTagAgency(value);
			break;
		case ICTXConstants.D_ETC_TAG_SERIAL_NUMBER:
			detailVO.setEtcTagSerialNumber(value);
			break;
		case ICTXConstants.D_ETC_READ_PERFORMANCE:
			detailVO.setEtcReadPerformance(value);
			break;
		case ICTXConstants.D_ETC_WRITE_PERF:
			detailVO.setEtcWritePref(value);;
			break;
		case ICTXConstants.D_ETC_TAG_PGM_STATUS:
			detailVO.setEtcTagPgmStatus(value);
			break;
		case ICTXConstants.D_ETC_LANE_MODE:
			detailVO.setEtcLaneMode(value);
			break;
		case ICTXConstants.D_ETC_VALIDATION_STATUS:
			detailVO.setEtcValidationStatus(value);
			break;
		case ICTXConstants.D_ETC_LIC_STATE:
			detailVO.setEtcLicState(value);
			break;
		case ICTXConstants.D_ETC_LIC_NUMBER:
			detailVO.setEtcLicNumber(value);
			break;
		case ICTXConstants.D_ETC_CLASS_CHARGED:
			detailVO.setEtcClassCharged(value);
			break;
		case ICTXConstants.D_ETC_ACTUAL_AXLES:
				detailVO.setEtcActualAxles(value);
				break;
		case ICTXConstants.D_ETC_EXIT_SPEED:
			detailVO.setEtcExitSpeed(value);
			break;
		case ICTXConstants.D_ETC_OVER_SPEED:
			detailVO.setEtcOverSpeed(value);
			break;
		case ICTXConstants.D_ETC_EXIT_DATE:
			detailVO.setEtcExitDate(value);
			break;
		case ICTXConstants.D_ETC_EXIT_TIME:
			detailVO.setEtcExitTime(value);
			break;
		case ICTXConstants.D_ETC_EXIT_PLAZA:
			detailVO.setEtcExitPlaza(value);
			break;
		case ICTXConstants.D_ETC_EXIT_LANE:
			detailVO.setEtcExitLane(value);
			break;
		case ICTXConstants.D_ETC_DEBIT_CREDIT:
			detailVO.setEtcDebitCredit(value);
			break;
		case ICTXConstants.D_ETC_TOLL_AMOUNT:
			detailVO.setEtcTollAmount(value);
			break;

		default:

		}
		log.debug("Mapping done for value: {} with field: {}", value, fMapping);
	}

	private boolean validateAgencyMatch(String agencyId) throws InvlidFileHeaderException {
		if (filePrefix.equals(agencyId)) {
			return true;
		} else {
			log.info("Agency id {} mismatch with file agency id {}.", agencyId, filePrefix);
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
			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException,
			EmptyLineException, InvalidRecordException, DateTimeParseException {

		super.validateAndProcessFileName(fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);

		if (validateFileAgency(fileName)) {
			log.info("{} is valid for home agency.", fileName);

			if (fileDetails == null) {
				log.info("{} is not processed earlier.", fileName);
				insertFileDetailsIntoCheckpoint();
			} else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) {
				log.info("{} is already processed.", fileName);
				throw new FileAlreadyProcessedException("File already processed");
			}
		} else {
			log.info("Invalid file prefix in file name:{} for home agency id: {}", fileName,
					ICTXConstants.HOME_AGENCY_ID);
			throw new InvalidFileNameException("Invalid file prefix in file name: " + fileName);
		}
	}

	@Override
	public void finishFileProcess()
			throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException {

		log.debug("Inserted into finish process....");

		fileNameVO.setFileName(getFileName());

		validateAdnAddRecords(detailVOList);
		
		updateFileDetailsIntoCheckpoint();
		
		insertFileDetailsIntoStatistics();
		
		//update statistics 
		
		//set staticts pojo, 
		//call update statistics()
		
		detailVOList.clear();
		successCounter = 0;

	}
	
	@Override
	public void updateFileDetailsIntoCheckpoint() {
		
		log.debug("Updating file details into lane_checkpoint table..");

		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setFileCount(Integer.parseInt(headerVO.getRecordCount()));
		fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount(successCounter);
		fileDetails.setSuccessCount(successCounter);
		fileDetails.setExceptionCount(skipcounter);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
		
	}

	/*@Override
	public void insertFileDetailsIntoStatistics() {
		
		log.debug("Inserting file details into Statistics table..");
		QatpStatistics qatpStatistics = new QatpStatistics();
		//qatpStatistics.setAtpFileId(fileDetails.getFileName());
		qatpStatistics.setFileType(fileDetails.getFileType());
		qatpStatistics.setFileName(fileDetails.getFileName());
		qatpStatistics.setInsertLocalDate(LocalDate.now());
		qatpStatistics.setInsertTime(LocalDateTime.now());
		qatpStatistics.setRecordCount(fileDetails.getFileCount());
		//qatpStatistics.setAmount(amount);
		qatpStatistics.setIsProcessed((long) 1);
		qatpStatistics.setProcessLocalDate(LocalDate.now());
		qatpStatistics.setProcessTime(LocalDateTime.now());
		qatpStatistics.setProcessRecCount(String.valueOf(fileDetails.getFileCount()));
		qatpStatistics.setUpLocalDateTs(LocalDateTime.now());
		//qatpStatistics.setXferControlId(fileDetails);
		tQatpMappingDao.insertQatpStatistics(qatpStatistics);		
		
	}


/*	private boolean checkRecordExistInAddress(CustomerAddressRecord record) {

		boolean isExist = false;

		CustomerAddressRecord tableRecord = tQatpMappingDao.checkifrecordexists(record);
		if(tableRecord != null) {
			isExist = true;
		}
	
		return isExist;
	}*/

	

	private void validateAdnAddRecords(List<ICTXDetailInfoVO> detailVOList2) {
		// TODO Auto-generated method stub
		
	}

	public void validateThresholdLimit(File lastSuccFile) throws InvlidFileHeaderException {
		int currentFileHeaderCount = Integer.parseInt(headerVO.getRecordCount());
		log.info("The record count in currently processed file is: {}", currentFileHeaderCount);
		int lastFileHeaderCount = 0;
		if (lastSuccFile.exists()) {
			try {
				FileReader fileReader = new FileReader(lastSuccFile);
				LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

				while (lineNumberReader.getLineNumber() == 0) {
					String header = lineNumberReader.readLine();
					lastFileHeaderCount = Integer.valueOf(header.substring(21, 29));
					log.info("The record count in last processed file is: {}", lastFileHeaderCount);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			int thresholdLimit = ICTXConstants.THRESHOLD_LIMIT;
			if (Math.abs(currentFileHeaderCount - lastFileHeaderCount) > thresholdLimit) {
				log.info("File record count difference exceeded threshold limit:" + thresholdLimit);
				throw new InvlidFileHeaderException(
						"File record count difference exceeded threshold limit:" + thresholdLimit);
			}
		}
	}

	public boolean ifOldProcessedFileIsLatest(LocalDateTime newFileDateTime, String lastSuccFileDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime lastProcdateTime = LocalDateTime.parse(lastSuccFileDate, formatter);

		if (lastProcdateTime.isAfter(newFileDateTime)) {
			log.info("File {} is not latest ..", getFileName());
			return true;
		} else {
			log.info("File {} is latest ..", getFileName());
			return false;
		}
	}

	@Override
	public void processDetailLine() {
		detailVOList.add(detailVO);
	}

	@Override
	public void insertFileDetailsIntoCheckpoint() {

		log.debug("Inserting file details into lane_checkpoint table..");

		fileDetails = new FileDetails();
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ICTXConstants.ICTX);
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 3));
		fileDetails.setProcessId((long) ICTXConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ICTXConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setSerialNumber((long) ICTXConstants.ZERO);
		fileDetails.setProcessedCount(ICTXConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setSuccessCount(ICTXConstants.ZERO);
		fileDetails.setExceptionCount(ICTXConstants.ZERO);
		fileDetails.setUpdateTs(LocalDateTime.now());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}
	
	
	List<ICTXDetailInfoVO> detailVOListTemp;

	synchronized public void insertBatchAndUpdateCheckpoint() {

		ICTXDetailInfoVO[] records = new ICTXDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);
		int recordCounter = 1;
		Integer counter = 1;
		Integer preProcessedRecordCount = 0;
		ICTXDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<ICTXDetailInfoVO>();
		if (fileDetails != null) {
			preProcessedRecordCount = fileDetails.getProcessedCount();
			// log.info("process count of the last file {}.", file);
		}
		int i = 0;
		do {
			try {
				for (i = 0; i < records.length; i++) {
					prevline = (records[i]);

					if (preProcessedRecordCount == 0 || recordCounter > preProcessedRecordCount) {
						detailVOListTemp.add(prevline);
						if (counter == ICTXConstants.BATCH_RECORD_COUNT) {

							saveRecords(fileDetails);
							fileDetails.setProcessStatus(FileProcessStatus.IN_PROGRESS);
							fileDetails.setProcessedCount(preProcessedRecordCount);
							fileDetails.setSuccessCount(recordCounter);
							tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

							log.info("Current Thread name: {}, updated records: {} ", Thread.currentThread().getName(),
									counter);
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
		if (fileDetails.getFileCount() == preProcessedRecordCount) {
			detailVOListTemp.clear();
		}
		saveRecords(fileDetails);
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount(recordCounter - 1);
		fileDetails.setSuccessCount(recordCounter - 1);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
		detailVOList = new ArrayList<ICTXDetailInfoVO>();
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
		fileDetails.setUpdateTs(LocalDateTime.now());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	@Override
	public void validateRecordCount(String fileName) throws InvalidRecordCountException {
		log.info("Header record count: {}", headerVO.getRecordCount());
		log.info("Total record count from file: {}", recordCount);
//		if (recordCount.longValue() != Convertor.toLong(headerVO.getRecordCount()).longValue()) {
		if (recordCount.get(fileName).longValue() != Convertor.toLong(headerVO.getRecordCount()).longValue()) {
			log.info("Record count long vaalue : " + recordCount.get(fileName).longValue() +" headerVO-RecordCount long vaalue : "+Convertor.toLong(headerVO.getRecordCount()).longValue());
			log.info("Record count mismatch from number of records in " + getFileName());
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}

	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		log.info("Preparing Ack file metadata...");

		AckFileWrapper ackObj = new AckFileWrapper();
		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(ICTXConstants.TO_AGENCY_ID).append(ICTXConstants.UNDER_SCORE_CHAR)
				.append(fileName.replace('.', '_')).append(ICTXConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ICTXConstants.TO_AGENCY_ID)
		.append(ICTXConstants.HOME_AGENCY_ID).append(StringUtils.rightPad(fileName, 50, ""))
				.append(genericValidation.getTodayTimestamp()).append(ackStatusCode.getCode()).append("\n");

		log.debug("Ack file name: {}", sbFileContent.toString());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);

		log.debug("Ack file content: {}", sbFileContent.toString());
		if (ackStatusCode.equals(AckStatusCode.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(ICTXConstants.ICTX);
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(fileNameVO.getToAgencyId());
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}


	public boolean validateTagPrefix(String devicetag) {

		Boolean isDevicePrefixValid = false;
		String devicePrefix = devicetag.substring(0, 3);
		if (devicePrefix.equals(filePrefix)) {
			isDevicePrefixValid = true;
		}
		return isDevicePrefixValid;
	}

	@Override
	public void getMissingFilesForAgency() {

		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
				.filter(p -> p.getFileProcessingStatus() == ICTXConstants.NO).collect(Collectors.toList());
		log.info("Missing files list : " + missingFiles);
	}

	public void getMissingFilesForAgency1() {
		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
				.filter(p -> p.getFileProcessingStatus() == ICTXConstants.NO).collect(Collectors.toList());

		log.info("Missing files list : " + missingFiles);

	}

	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {

		if (fileName != null) {

			filePrefix = fileName.substring(0, 3);
			Boolean devicePresent = false;
			Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();

			if (agencyInfoVO.isPresent()) {
				agencyInfoVO.get().setFileProcessingStatus(ICTXConstants.YES);
				log.info("Valid prefix for file: {}", fileName);
				devicePresent = true;
			}
			return devicePresent;
		} else {
			throw new InvalidFileNameException("File name is empty");
		}
	}

	@Override
	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (!fileExtension.equalsIgnoreCase(ICTXConstants.ICTX)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}

}
