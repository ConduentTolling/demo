package com.conduent.tpms.iag.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ICLPDetailInfoVO;
import com.conduent.tpms.iag.dto.ICLPHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.BucketFullException;
import com.conduent.tpms.iag.exception.CustomException;
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
import com.conduent.tpms.iag.model.LicPlateDetails;
import com.conduent.tpms.iag.utility.Convertor;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.google.common.io.Files;

import io.vavr.collection.Iterator;

@Component
public class GenericICLPFileParserImpl extends FileParserImpl {

	@Autowired
	TimeZoneConv timeZoneConv;

	@Autowired(required = true)
	protected TQatpMappingDao tQatpMappingDao;

	private static final Logger log = LoggerFactory.getLogger(GenericICLPFileParserImpl.class);

	private FileNameDetailVO fileNameVO;
	private ICLPDetailInfoVO detailVO;
	private ICLPHeaderInfoVO headerVO;
	protected Integer fileCount = 0;
	private String fileStatus;
	List<LicPlateDetails> licPlateDetailsList = new ArrayList<LicPlateDetails>();
	private FileUtil fileutil = new FileUtil();

	protected Integer exceptionCount=0;
	
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	volatile private List<ICLPDetailInfoVO> detailVOList;

	protected List<AgencyInfoVO> validAgencyInfoList;

	protected List<String> validStateList;

	MasterDataCache masterDataCache;

	/**
	 * Initialize the ICLP file properties
	 */
	@Override
	public void initialize() {

		log.info("Initializing ICLP file..");
		setFileFormat(ICLPConstants.FIXED);
		setFileType(ICLPConstants.ICLP);
		// setAgencyId(ICLPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);

		fileNameVO = new FileNameDetailVO();
		detailVO = new ICLPDetailInfoVO();
		headerVO = new ICLPHeaderInfoVO();
		detailVOList = new ArrayList<ICLPDetailInfoVO>();
		validAgencyInfoList = new ArrayList<AgencyInfoVO>();

	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ICLPConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new ICLPDetailInfoVO();
		}

	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvlidFileHeaderException, InvalidFileNameException {

		log.debug("Mapping started for value: {} with field: {}", value, fMapping);

		switch (fMapping.getFieldName()) {
		case ICLPConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case ICLPConstants.F_FILE_DATE_TIME:
			try {
				fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			} catch (Exception e) {
				throw new InvalidFileNameException("Invalid file Date value {}" + value);
			}
			break;
		case ICLPConstants.F_FILE_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case ICLPConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		case ICLPConstants.H_VERSION:
			headerVO.setVersion(value);
			break;
		case ICLPConstants.H_FROM_AGENCY_ID:
			headerVO.setFromAgencyId(value);
			break;
		case ICLPConstants.H_DATETIME:
			LocalDateTime hdrdateTime = genericValidation.getFormattedDateTimeNew(value);
			if (customDateValidationNew(hdrdateTime)) {
				headerVO.setFiledatetime(hdrdateTime);
			}
			break;
		case ICLPConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case ICLPConstants.D_LIC_STATE:
			detailVO.setLicState(value);
			break;
		case ICLPConstants.D_LIC_NUMBER:
			detailVO.setLicNumber(value.trim());
			break;
		case ICLPConstants.D_LIC_TYPE:
			boolean valLicType = validate(value);
			if (valLicType) {
				detailVO.setLicType(null);
			} else {
				detailVO.setLicType(value.trim());
			}
			break;
		case ICLPConstants.D_TAG_AGENCY_ID:
			detailVO.setTagAgencyId(value);
			break;
		case ICLPConstants.D_TAG_SERIAL_NUMBER:
			detailVO.setTagSerialNumber(value);
			break;
		case ICLPConstants.D_LIC_EFFECTIVE_FROM:
			boolean valeffectivefrom = validate(value);
			if (valeffectivefrom) {
				detailVO.setLicEffectiveFrom(fileNameVO.getFileDateTime());
			} else {
				LocalDateTime effDate = genericValidation.getFormattedDateTimeNew(value);
				detailVO.setLicEffectiveFrom(effDate);
			}
			break;
		case ICLPConstants.D_LIC_EFFECTIVE_TO:
			boolean valeffectiveto = validate(value);
			if (valeffectiveto) {
				// LocalDate dt2= LocalDate.of(2099, 12, 31);
				// LocalDateTime liceffto= dt2.atStartOfDay();

				LocalDateTime dt4 = LocalDateTime.of(2099, Month.DECEMBER, 31, 00, 00, 00, 123456789);
				String dt5 = dt4.toString().substring(0, 19).concat("Z");
				LocalDateTime liceffto = genericValidation.getFormattedDateTimeNew(dt5);
				detailVO.setLicEffectiveTo(liceffto);
			} else {
				LocalDateTime effToDate = genericValidation.getFormattedDateTimeNew(value);
				detailVO.setLicEffectiveTo(effToDate);
			}
			break;
		case ICLPConstants.D_LIC_HOME_AGENCY:
			detailVO.setLicHomeAgency(value);
			break;
		case ICLPConstants.D_LIC_ACCOUNT_NO:
			boolean licaccountno = validate(value);
			if (licaccountno) {
				detailVO.setLicAccntNo(null);
			} else {
				String valueacctno= removeLeadingZeroes(value);
				detailVO.setLicAccntNo(valueacctno);
			}
			break;
		case ICLPConstants.D_LIC_VIN:
			boolean licvin = validate(value);
			if (licvin) {
				detailVO.setLicVin(null);
			} else {
				detailVO.setLicVin(value);
			}
			break;
		case ICLPConstants.D_LIC_GUARANTEED:
			detailVO.setLicGuaranteed(value);
			break;

		default:

		}
		log.debug("Mapping done for value: {} with field: {}", value, fMapping);
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
			throw new InvlidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
	}

	public boolean validate(String value) {
		String matcher_String = "\\s{" + value.length() + "}|[*]{" + value.length() + "}";
		if (value.matches(matcher_String)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String removeLeadingZeroes(String str) {
	      String strPattern = "^0+(?!$)";
	      str = str.replaceAll(strPattern, "");
	      return str;
	   }

	@Override
	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvlidFileHeaderException,
			IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException {

		super.validateAndProcessFileName(fileName);

		log.info("Checking if {} is already processed.. ", fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);

		if (validateFileAgency(fileName)) {
			log.info("{} is valid for home agency.", fileName);

			if (fileDetails == null) {
				log.info("{} is not processed earlier.", fileName);
				insertFileDetailsIntoCheckpoint();
			} else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) {
				log.info("{} is already processed.", fileName);
				saveFileToWorkingDir(fileName);
				throw new FileAlreadyProcessedException("File already processed");
			}
		} else {
			log.info("Invalid file prefix in file name:{}", fileName);
			throw new InvalidFileNameException("Invalid file prefix in file name.");
		}
	}

	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {

		String filePrefix = fileName.substring(0, 4);
		Boolean devicePresent = false;
		validAgencyInfoList = tQatpMappingDao.getAgencyDetails();

		Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
				.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();
		if (agencyInfoVO.isPresent()) {
			agencyInfoVO.get().setFileProcessingStatus("Y");
			log.info("Valid prefix for file: {}", fileName);
			devicePresent = true;
		}
		return devicePresent;
	}

	public boolean validateLicState(String state) {
		Boolean stateval = false;

		validStateList = tQatpMappingDao.getLicState();

		Optional<String> statevalid = validStateList.stream().filter(s -> s.equals(state)).findAny();
		if (statevalid.isPresent()) {
			stateval = true;
		}

		return stateval;
	}

	@Override
	public void insertFileDetailsIntoCheckpoint() {

		log.debug("Inserting file details into lane_checkpoint table..");

		fileDetails = new FileDetails();
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ICLPConstants.ICLP);
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 4));
		fileDetails.setProcessId((long) ICLPConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ICLPConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setSerialNumber((long) ICLPConstants.ZERO);
		fileDetails.setProcessedCount(ICLPConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setSuccessCount(ICLPConstants.ZERO);
		fileDetails.setExceptionCount(ICLPConstants.ZERO);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}

	@Override
	public void getMissingFilesForAgency() {

		List<AgencyInfoVO> missingFiles = validAgencyInfoList.stream()
				.filter(p -> p.getFileProcessingStatus() == ICLPConstants.NO).collect(Collectors.toList());

		ListIterator<AgencyInfoVO> itr = missingFiles.listIterator();
		List<String> printdetails = new ArrayList<>();
		while (itr.hasNext()) {
			AgencyInfoVO agencyinfo = itr.next();
			String fileprefix = agencyinfo.getFilePrefix();
			String agencyname = agencyinfo.getAgencyName();
			String printdetail = fileprefix.concat(" ").concat(agencyname);
			printdetails.add(printdetail);
		}

		String missingfile = printdetails.toString().replace("[", "").replace("]", "");
		log.info("Missing files list : " + missingFiles);

		StringBuilder sbFileName = new StringBuilder();
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyyMMdd");
		String missingfileDate = localDate.format(formatDate);
		sbFileName.append(ICLPConstants.ICLP).append(ICLPConstants.UNDER_SCORE_CHAR).append(missingfileDate)
				.append(ICLPConstants.UNDER_SCORE_CHAR).append(ICLPConstants.MISSING_FILE)
				.append(ICLPConstants.MISSING_FILE_EXT);
		try {
			createMissingFile(sbFileName.toString(), missingfile);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createMissingFile(String missingFileName, String missingfile) throws IOException, CustomException {
		log.debug("Creating Missing  file {}", missingFileName);

		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), missingFileName);
		if (destFile.exists()) {
			destFile.delete();
		}

		File missingFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), missingFileName);
		try (FileOutputStream fos = new FileOutputStream(missingFile)) {
			fos.write(missingfile.getBytes());
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

	}

	// @Override
	public void finishFileProcess(int agencySequence) throws InvlidFileHeaderException, FileAlreadyProcessedException,
			IOException, BucketFullException, InvalidFileNameException, EmptyLineException, InvalidRecordException, RecordThresholdLimitExceeded {

		fileNameVO.setFileName(getFileName());
		log.info("Fetching last successfully processed file name...");
		String lastSuccFileName = tQatpMappingDao.getLastSuccesfulProcessedFile(fileNameVO.getFromAgencyId());

		if (lastSuccFileName.isEmpty()) {

			log.debug("File: {} is coming for the first time for Agency. "
					+ "Insert device details and update status of this file in T_LANE_TX_CHECKPOINT table."
					+ getFileName());

			insertBatchAndUpdateCheckpoint();
		} else if (!lastSuccFileName.isEmpty()) {
			log.debug("File: {} already exist for this Agency. ", lastSuccFileName);

			String lastProcFileDate = lastSuccFileName.substring(5, 19);

			if (!ifOldProcessedFileIsLatest(fileNameVO.getFileDateTime(), lastProcFileDate)) {

				log.info("Load a last successful file from working directory...");
				File awaytagOldFile = laodAndPrepareLastFile(lastSuccFileName,
						getConfigurationMapping().getFileInfoDto().getWorkingDir());

				validateThresholdLimit(awaytagOldFile);

				transactionDao.preparFileAndUploadToExtern(getNewFile(), awaytagOldFile, agencySequence);

				/*
				 * try { transactionDao.preparFileAndUploadToExtern1(getNewFile(),
				 * awaytagOldFile); } catch (BucketFullException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch (Exception e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } //
				 * updateDeviceTagDetails_();
				 */
			} else {
				log.info("File {} is older then the last processed file {}.", fileNameVO.getFileName(),
						lastSuccFileName);
				updateFileDetailsInCheckPoint();
				throw new FileAlreadyProcessedException(fileNameVO.getFileName());
			}
		}
		saveFileToWorkingDir(fileNameVO.getFileName());
	}

	List<ICLPDetailInfoVO> detailVOListTemp;

	synchronized public void insertBatchAndUpdateCheckpoint() {

		ICLPDetailInfoVO[] records = new ICLPDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);
		int recordCounter = 1;
		Integer counter = 1;
		Integer preProcessedRecordCount = 0;
		ICLPDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<ICLPDetailInfoVO>();
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
						if (counter == ICLPConstants.BATCH_RECORD_COUNT) {

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
		//recordCount.get(getFileName())
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		//fileDetails.setProcessedCount(recordCounter - 1);
		fileDetails.setProcessedCount((int)(long)recordCount.get(getFileName()));
		fileDetails.setSuccessCount(recordCounter - 1);
		Long expcount = recordCount.get(getFileName()) - ((recordCounter - 1));
		fileDetails.setExceptionCount(expcount);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
		detailVOList = new ArrayList<ICLPDetailInfoVO>();
	}

	public void updateFileDetailsInCheckPoint() {

		log.debug("Updating file details into lane_checkpoint table..");

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

	public void saveFileToWorkingDir(String workingfilename) throws IOException {
		log.info("Load a copy of new file {} into working directory...", workingfilename);

		/*
		 * Environment path
		 */
		String fileUri = getConfigurationMapping().getFileInfoDto().getWorkingDir().concat("//")
				.concat(workingfilename);

		/*
		 * Local path
		 */
		// String fileUri =
		// getConfigurationMapping().getFileInfoDto().getWorkingDir().concat("\\")
		// .concat(workingfilename);

		File workingFile = new File(fileUri);

		File temp = workingFile;
		if (temp.exists()) {
			temp.delete();
		}
		fileutil.copyFileUsingApacheCommonsIO(getNewFile(), workingFile);

	}

	public File laodAndPrepareLastFile(String fileName, String fileLocation) {

		log.info("Getting file from Working dir..");

		/*
		 * Local path
		 */
		// String fileUri = fileLocation.concat("\\").concat(fileName);

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

	public void updateDeviceTagDetails_Iclp(String newAwaytagBucket, String oldAwaytagBucket) {

		log.info("Get Updated records from external tables..");
		List<ICLPDetailInfoVO> detailVOUpdatedRecordList = tQatpMappingDao
				.getDiffRecordsFromExtTables_ICLP(newAwaytagBucket, oldAwaytagBucket);

		log.info("Get list of Device numbers from new external table..");
		List<ICLPDetailInfoVO> newDeviceList = tQatpMappingDao.getDataFromExternTable1(newAwaytagBucket);

		log.info("Get list of Device numbers from new external table..");
		List<ICLPDetailInfoVO> oldDeviceList = tQatpMappingDao.getDataFromExternTable1(oldAwaytagBucket);

		for (ICLPDetailInfoVO detailVOObj : detailVOUpdatedRecordList) {

			LicPlateDetails licPlateDetails = new LicPlateDetails();
			List<LicPlateDetails> licPlateDetailsListToAdd = new ArrayList<LicPlateDetails>();

			String deviceNo = detailVOObj.getDeviceNo();
			LocalDate endDate = tQatpMappingDao.getIfDeviceExistForFuture(detailVOObj);
			log.debug("End date for device no. {} is: {}", deviceNo, endDate);

			boolean isPresentinNewList = presentInDeviceList(newDeviceList, detailVOObj);
			boolean isPresentinOldList = presentInDeviceList(oldDeviceList, detailVOObj);

			if (isPresentinNewList && isPresentinOldList) {

				switch (detailVOObj.getFiletype()) {
				case "NEW":
					licPlateDetails.setDeviceNo(deviceNo);
					licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
					licPlateDetails.setEndDate(LocalDate.of(2099, 12, 31));
					licPlateDetails.setPlateState(detailVOObj.getLicState());
					licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());

					/*
					 * licPlateDetails.setPlateType(
					 * detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ?
					 * ICLPConstants.ZERO_VALUE : Integer.parseInt(detailVOObj.getLicType()));
					 */

					if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
						tQatpMappingDao.updateDuplicateLicPlateDetails(licPlateDetails, endDate);
						log.info("deviceNo {} updated.", deviceNo);
					} else {
						licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
						licPlateDetailsListToAdd.add(licPlateDetails);

						int[] totalRecords = tQatpMappingDao.insertPlateDetails(licPlateDetailsListToAdd);
						licPlateDetailsListToAdd.clear();
						log.info("deviceNo {} added.", deviceNo);
					}
					break;
				case "OLD":
					log.info("Skipping the old data for DeviceNo {}", deviceNo);
					break;
				}
			} else if (isPresentinNewList && !isPresentinOldList) {
				licPlateDetails.setDeviceNo(deviceNo);
				licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
				licPlateDetails.setEndDate(LocalDate.of(2099, 12, 31));
				licPlateDetails.setPlateState(detailVOObj.getLicState());
				licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());

				/*
				 * licPlateDetails.setPlateType(
				 * detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ?
				 * ICLPConstants.ZERO_VALUE : Integer.parseInt(detailVOObj.getLicType()));
				 */

				if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
					tQatpMappingDao.updateDuplicateLicPlateDetails(licPlateDetails, endDate);
					log.info("deviceNo {} updated.", deviceNo);
				} else {
					licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					licPlateDetailsListToAdd.add(licPlateDetails);
					int[] totalRecords = tQatpMappingDao.insertPlateDetails(licPlateDetailsListToAdd);
					licPlateDetailsListToAdd.clear();
				}
			} else if (!isPresentinNewList && isPresentinOldList) {

				licPlateDetails.setDeviceNo(deviceNo);
				licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
				licPlateDetails.setEndDate(fileNameVO.getFileDateTime().toLocalDate());
				licPlateDetails.setPlateState(detailVOObj.getLicState());
				licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());

				/*
				 * licPlateDetails.setPlateType(
				 * detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ?
				 * ICLPConstants.ZERO_VALUE : Integer.parseInt(detailVOObj.getLicType()));
				 */

				if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
					tQatpMappingDao.updateDuplicateLicPlateDetails(licPlateDetails, endDate);
				} else {
					tQatpMappingDao.updateLicPlateDetails(licPlateDetails);
				}
				log.info("deviceNo {} expired.", deviceNo);
			}
		}
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ICLPConstants.ICLP);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount((int) fileDetails.getFileCount());
		fileDetails.setSuccessCount((int) fileDetails.getFileCount());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	/*
	 * In case of old records check again in new table if it exist then skip if not
	 * exist then update end date as file date new records update of insert
	 */
	public void updateDeviceTagDetails(String newBucket, String oldBucket)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {

		log.info("Get Updated records from external tables..");
		List<ICLPDetailInfoVO> detailVOUpdatedRecordList = tQatpMappingDao.getDiffRecordsFromExtTables_ICLP(newBucket,oldBucket);
		List<ICLPDetailInfoVO> detailVOUpdatedRecordListValid = new ArrayList<>();

		// Iterator <ICLPDetailInfoVO>itr = (Iterator<ICLPDetailInfoVO>)
		// detailVOUpdatedRecordList.iterator();
		ListIterator<ICLPDetailInfoVO> itr = detailVOUpdatedRecordList.listIterator();
		while (itr.hasNext()) {

			ICLPDetailInfoVO detailvoinfo = itr.next();
			String line = detailvoinfo.toStringnew();

			parseAndValidateNew(line, getConfigurationMapping().getDetailRecMappingInfo());
			if (isIsrecordValid()) {
				detailVOUpdatedRecordListValid.add(detailvoinfo);
			}

		}

		

		LocalDateTime dt6 = LocalDateTime.now();
		LocalDate dt7 = dt6.toLocalDate();
		
		 LocalDateTime dt4= LocalDateTime.of(2099, Month.DECEMBER, 31, 00, 00, 01,123456789); 
		  String dt5= dt4.toString().substring(0, 19).concat("Z");
		  LocalDateTime liceffto= genericValidation.getFormattedDateTimeNew(dt5);

		log.info("Get list of Device numbers from new external table..");
		List<ICLPDetailInfoVO> newDeviceList = tQatpMappingDao.getDataFromNewExternTable(newBucket);

		log.info("Get list of Device numbers from new external table..");
		List<ICLPDetailInfoVO> oldDeviceList = tQatpMappingDao.getDataFromOldExternTable(oldBucket);

		for (ICLPDetailInfoVO detailVOObj : detailVOUpdatedRecordListValid) {
			
			String record= detailVOObj.record();

			LicPlateDetails licPlateDetails = new LicPlateDetails();
			List<LicPlateDetails> licPlateDetailsListToAdd = new ArrayList<LicPlateDetails>();

			String deviceNo = detailVOObj.getDeviceNo();
			LocalDate endDate = tQatpMappingDao.getIfDeviceExistForFuture(detailVOObj);
			log.debug("End date for device no. {} is: {}", deviceNo, endDate);

			boolean isPresentinNewList = presentInDeviceList(newDeviceList, detailVOObj);
			boolean isPresentinOldList = presentInDeviceList(oldDeviceList, detailVOObj);

			boolean lictype = validate(detailVOObj.getLicType());
			if (lictype) {
				detailVOObj.setLicType(null);
			} else {
				detailVOObj.setLicType(detailVOObj.getLicType().trim());
			}

			/*
			 * boolean licefffrom = validate(detailVOObj.getLicEffectiveFrom().toString());
			 * if(licefffrom) {
			 * detailVOObj.setLicEffectiveFrom(fileNameVO.getFileDateTime()); }else {
			 * detailVOObj.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom()); }
			 */

			/*
			 * boolean liceffto = validate(detailVOObj.getLicEffectiveTo().toString());
			 * if(liceffto) { LocalDateTime dt4= LocalDateTime.of(2099, Month.DECEMBER, 31,
			 * 00, 00, 00, 123456789); String dt5= dt4.toString().substring(0,
			 * 19).concat("Z"); LocalDateTime licefftod=
			 * genericValidation.getFormattedDateTimeNew(dt5);
			 * detailVO.setLicEffectiveTo(licefftod);
			 * 
			 * }else { detailVOObj.setLicEffectiveTo(detailVOObj.getLicEffectiveTo()); }
			 */

			if (detailVOObj.getLicEffectiveFrom().toLocalDate().isEqual(dt7)) {
				detailVOObj.setLicEffectiveFrom(fileNameVO.getFileDateTime());
			} else {
				detailVOObj.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom());
			}

			
			  if(detailVOObj.getLicEffectiveTo().toLocalDate().isEqual(dt7)) {
			  
				  detailVOObj.setLicEffectiveTo(liceffto); 
			  System.out.println("in if");
			  }else {
			  detailVOObj.setLicEffectiveTo(detailVOObj.getLicEffectiveTo()); 
			  }
			 

			boolean licaccuntno = validate(detailVOObj.getLicAccntNo());
			if (licaccuntno) {
				detailVOObj.setLicAccntNo(null);
			} else {
				String valueacctno= removeLeadingZeroes(detailVOObj.getLicAccntNo());
				detailVOObj.setLicAccntNo(valueacctno);
			}

			boolean licvin = validate(detailVOObj.getLicVin());
			if (licvin) {
				detailVOObj.setLicVin(null);
			} else {
				detailVOObj.setLicVin(detailVOObj.getLicVin());
			}
			if (isPresentinNewList && isPresentinOldList) {

				switch (detailVOObj.getFiletype()) {
				case "NEW":
					licPlateDetails.setDeviceNo(deviceNo);
					licPlateDetails.setPlateState(detailVOObj.getLicState());
					licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());
					if (detailVOObj.getLicType() == null) {
						licPlateDetails.setPlateType(detailVOObj.getLicType());
					} else {
						licPlateDetails.setPlateType(detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE)? ICLPConstants.ZERO_VALUE
								: detailVOObj.getLicType());
					}

					// licPlateDetails.setPlateType(detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE)
					// ? ICLPConstants.ZERO_VALUE : detailVOObj.getLicType());
					// licPlateDetails.setPlateType(detailVOObj.getLicType());
					//licPlateDetails.setLicHomeAgency(detailVOObj.getLicHomeAgency());
					licPlateDetails.setLicHomeAgency(tQatpMappingDao.getAgencyid(detailVOObj.getLicHomeAgency()));
					licPlateDetails.setLicAccntNo(detailVOObj.getLicAccntNo());
					licPlateDetails.setLicVin(detailVOObj.getLicVin());
					licPlateDetails.setLicGuaranteed(detailVOObj.getLicGuaranteed());
					licPlateDetails.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom());
					licPlateDetails.setLicEffectiveTo(detailVOObj.getLicEffectiveTo());
					licPlateDetails.setEndDate(LocalDate.of(2099, 12, 31));
					licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
					licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
					//licPlateDetails.setRecord(detailVOObj.recordfromtb());
					licPlateDetails.setRecord(record);
					licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					licPlateDetails.setXferControlId(xferControl.getXferControlId());
					
					/*
					 * licPlateDetails.setPlateType(
					 * detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ?
					 * ICLPConstants.ZERO_VALUE : Integer.parseInt(detailVOObj.getLicType()));
					 */

					//if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
						tQatpMappingDao.updateDuplicateLicPlateDetails(licPlateDetails, endDate);
						log.info("deviceNo {} updated.", deviceNo);
					//} 
				//  else {
						
						licPlateDetailsListToAdd.add(licPlateDetails);

						int[] totalRecords = tQatpMappingDao.insertPlateDetails(licPlateDetailsListToAdd);
						licPlateDetailsListToAdd.clear();
						log.info("deviceNo {} added.", deviceNo);
				//	}
					break;
				case "OLD":
					log.info("Skipping the old data for DeviceNo {}", deviceNo);
					break;
				}
			} else if (isPresentinNewList && !isPresentinOldList) {
				licPlateDetails.setDeviceNo(deviceNo);
				licPlateDetails.setPlateState(detailVOObj.getLicState());
				licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());
				if (detailVOObj.getLicType() == null) {
					licPlateDetails.setPlateType(detailVOObj.getLicType());
				} else {
					licPlateDetails.setPlateType(
							detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ? ICLPConstants.ZERO_VALUE
									: detailVOObj.getLicType());
				}

				// licPlateDetails.setPlateType(detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE)
				// ? ICLPConstants.ZERO_VALUE : detailVOObj.getLicType());
				// licPlateDetails.setPlateType(detailVOObj.getLicType());
				//licPlateDetails.setLicHomeAgency(detailVOObj.getLicHomeAgency());
				licPlateDetails.setLicHomeAgency(tQatpMappingDao.getAgencyid(detailVOObj.getLicHomeAgency()));
				licPlateDetails.setLicAccntNo(detailVOObj.getLicAccntNo());
				licPlateDetails.setLicVin(detailVOObj.getLicVin());
				licPlateDetails.setLicGuaranteed(detailVOObj.getLicGuaranteed());
				licPlateDetails.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom());
				licPlateDetails.setLicEffectiveTo(detailVOObj.getLicEffectiveTo());
				licPlateDetails.setEndDate(LocalDate.of(2099, 12, 31));
				licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
				licPlateDetails.setRecord(record);

				//if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
					//tQatpMappingDao.updateDuplicateLicPlateDetails(licPlateDetails, endDate);
					//log.info("deviceNo {} updated.", deviceNo);
				//} 
			    //else {
					licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
					licPlateDetails.setXferControlId(xferControl.getXferControlId());
					licPlateDetailsListToAdd.add(licPlateDetails);
					int[] totalRecords = tQatpMappingDao.insertPlateDetails(licPlateDetailsListToAdd);
					licPlateDetailsListToAdd.clear();
				    // }
			} else if (!isPresentinNewList && isPresentinOldList) {

				licPlateDetails.setDeviceNo(deviceNo);
				licPlateDetails.setPlateState(detailVOObj.getLicState());
				licPlateDetails.setPlateNumber(detailVOObj.getLicNumber().trim());
				if (detailVOObj.getLicType() == null) {
					licPlateDetails.setPlateType(detailVOObj.getLicType());
				} else {
					licPlateDetails.setPlateType(
							detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ? ICLPConstants.ZERO_VALUE
									: detailVOObj.getLicType());
				}

				// licPlateDetails.setPlateType(detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE)
				// ? ICLPConstants.ZERO_VALUE : detailVOObj.getLicType());
				// licPlateDetails.setPlateType(detailVOObj.getLicType());
				//licPlateDetails.setLicHomeAgency(detailVOObj.getLicHomeAgency());
				licPlateDetails.setLicHomeAgency(tQatpMappingDao.getAgencyid(detailVOObj.getLicHomeAgency()));
				licPlateDetails.setLicAccntNo(detailVOObj.getLicAccntNo());
				licPlateDetails.setLicVin(detailVOObj.getLicVin());
				licPlateDetails.setLicGuaranteed(detailVOObj.getLicGuaranteed());
				licPlateDetails.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom());
				licPlateDetails.setLicEffectiveTo(detailVOObj.getLicEffectiveTo());
				licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
				licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
				licPlateDetails.setEndDate(fileNameVO.getFileDateTime().toLocalDate());
				licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
				licPlateDetails.setRecord(record);

				//if (endDate != null && endDate.isAfter(fileNameVO.getFileDateTime().toLocalDate())) {
					tQatpMappingDao.updateDuplicateLicPlateDetailsForOld(licPlateDetails, endDate);
				//} 
			  //else {
					//tQatpMappingDao.updateLicPlateDetails(licPlateDetails);
				//}
				log.info("deviceNo {} expired.", deviceNo);
			}
		}
		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(ICLPConstants.ICLP);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount((int) fileDetails.getFileCount());
		Integer sucesscount =(int)fileDetails.getFileCount() - exceptionCount ;
		fileDetails.setSuccessCount(sucesscount);
		//fileDetails.setSuccessCount((int) fileDetails.getFileCount());
		fileDetails.setExceptionCount(exceptionCount);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	private boolean presentInDeviceList(List<ICLPDetailInfoVO> deviceList, ICLPDetailInfoVO detailVOObj) {
		boolean ispresent = false;

		for (ICLPDetailInfoVO detailInfoVO : deviceList) {

			String objDeviceNo = detailInfoVO.getDeviceNo();
			String objLicNo = detailInfoVO.getLicNumber().trim();
			String objLicState = detailInfoVO.getLicState();
			String objLicType = detailInfoVO.getLicType();
			String objaccountno = detailInfoVO.getLicAccntNo();

			if (objDeviceNo.equals(detailVOObj.getDeviceNo()) && objLicNo.equals(detailVOObj.getLicNumber().trim())
					&& objLicState.equals(detailVOObj.getLicState()) && objLicType.equals(detailVOObj.getLicType())
					&& objaccountno.equals(detailVOObj.getLicAccntNo())) {
				ispresent = true;
			}
		}
		return ispresent;

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
	public void processEndOfLine(String fileSection) {

	}

	public void insertLicPlateDetails(List<ICLPDetailInfoVO> licDetailVOList) {

		log.debug("Inserting License plate details..");
		List<LicPlateDetails> tagdeviceDetailsList = new ArrayList<LicPlateDetails>();

		for (ICLPDetailInfoVO detailVOObj : licDetailVOList) {

			LicPlateDetails licPlateDetails = new LicPlateDetails();

			licPlateDetails.setDeviceNo(detailVOObj.getTagAgencyId().concat(detailVOObj.getTagSerialNumber()));
			licPlateDetails.setPlateState(detailVOObj.getLicState());
			licPlateDetails.setPlateNumber(detailVOObj.getLicNumber());

			if (detailVOObj.getLicType() == null) {
				licPlateDetails.setPlateType(detailVOObj.getLicType());
			} else {
				licPlateDetails.setPlateType(
						detailVOObj.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ? ICLPConstants.ZERO_VALUE
								: detailVOObj.getLicType());
			}

			//licPlateDetails.setLicHomeAgency(detailVOObj.getLicHomeAgency());
			licPlateDetails.setLicHomeAgency(tQatpMappingDao.getAgencyid(detailVOObj.getLicHomeAgency()));
			licPlateDetails.setLicAccntNo(detailVOObj.getLicAccntNo());
			licPlateDetails.setLicVin(detailVOObj.getLicVin());
			licPlateDetails.setLicGuaranteed(detailVOObj.getLicGuaranteed());
			licPlateDetails.setLicEffectiveFrom(detailVOObj.getLicEffectiveFrom());
			licPlateDetails.setLicEffectiveTo(detailVOObj.getLicEffectiveTo());
			licPlateDetails.setStartDate(fileNameVO.getFileDateTime().toLocalDate());
			licPlateDetails.setEndDate(LocalDate.of(2099, 12, 31));
			licPlateDetails.setXferControlId(xferControl.getXferControlId());
			licPlateDetails.setUpdateDeviceTs(timeZoneConv.currentTime());
			licPlateDetails.setLastFileTS(fileNameVO.getFileDateTime());
            licPlateDetails.setRecord(detailVOObj.getLine());
            
			tagdeviceDetailsList.add(licPlateDetails);
		}

		int[] totalRecords = tQatpMappingDao.insertPlateDetails(tagdeviceDetailsList);
		log.debug("Inserted device tag count: {}", totalRecords.length);
		setFileStatus(ICLPConstants.PROCESS_STATUS_COMPLETE);
	}

	@Override
	public void processDetailLine(String line) {
		/*
		 * if(detailVO.isIsrecordvalid()) { detailVOList.add(detailVO); }
		 */
		if (isIsrecordValid()) {
			detailVO.setLine(line);
			detailVOList.add(detailVO);
		}else {
			exceptionCount++;
			
		}

	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException {
		// (recordCount != Convertor.toLong(headerVO.getRecordCount())) {
		if (recordCount.get(fileName).longValue() != Convertor.toLong(headerVO.getRecordCount()).longValue()) {
			log.info("Header record count mismatch from number of records in file {}", getFileName());
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}

	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		log.info("Preparing Ack file metadata...");

		AckFileWrapper ackObj = new AckFileWrapper();
		StringBuilder sbFileName = new StringBuilder();

		sbFileName.append(ICLPConstants.MTA_FILE_PREFIX).append(ICLPConstants.UNDER_SCORE_CHAR)
				.append(fileName.replace('.', '_')).append(ICLPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ICLPConstants.VERSION)
				.append(ICLPConstants.MTA_FILE_PREFIX).append(fileName.substring(0, 4))
				.append(StringUtils.rightPad(fileName, 50, ""))
				.append(genericValidation.getTodayTimeStampAck().concat("Z")).append(ackStatusCode.getCode())
				.append("\n");

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);

		if (ackStatusCode.equals(AckStatusCode.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT) || ackStatusCode.equals(AckStatusCode.THRESHOLD_EXCEED)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(ICLPConstants.ICLP);
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(ICLPConstants.MTA_FILE_PREFIX);
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		// String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(),
		// "hhmmss");
		// ackFileInfoDto.setAckFileTime(fileTime);
		LocalDateTime acktime = timeZoneConv.currentTime();
		String ackfileTime = genericValidation.getTodayTimestamp(acktime, "HH:mm:ss");
		ackFileInfoDto.setAckFileTime(ackfileTime);
		// ackFileInfoDto.setAckFileTime(timeZoneConv.currentTime());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;

	}

	public boolean validateDevicePrefix(String devicePrefix) throws InvalidFileNameException {
		ArrayList<String> list = new ArrayList<>();
		boolean isValidAgency = false;

		List<AgencyInfoVO> agencyInfoVOList = tQatpMappingDao.getAgencyDetails();
		for (AgencyInfoVO agencyInfoVO : agencyInfoVOList) {
			list.add(agencyInfoVO.getDevicePrefix());
		}
		if (list.contains(devicePrefix)) {
			isValidAgency = true;
		}
		return isValidAgency;
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

		log.info("laneTxCheckPoint details " + laneTxCheckPoint.toString());

	}

	public void insertRecords() {
		if (detailVOListTemp != null && !detailVOListTemp.isEmpty() && detailVOListTemp.size() > 0) {
			insertLicPlateDetails(detailVOListTemp);
			detailVOListTemp.clear();
		}
	}

	public void validateThresholdLimit(File lastSuccFile) throws InvlidFileHeaderException, RecordThresholdLimitExceeded {
		int currentFileHeaderCount = Integer.parseInt(headerVO.getRecordCount());
		log.info("The record count in currently processed file is: {}", currentFileHeaderCount);
		int lastFileHeaderCount = 0;
		if (lastSuccFile.exists()) {
			try {
				FileReader fileReader = new FileReader(lastSuccFile);
				LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
				while (lineNumberReader.getLineNumber() == 0) {
					String header = lineNumberReader.readLine();
					lastFileHeaderCount = Integer.valueOf(header.substring(36, 46));
					log.info("The record count in last processed file is: {}", lastFileHeaderCount);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// int thresholdLimit = ICLPConstants.THRESHOLD_LIMIT;

			int thresholdLimit = tQatpMappingDao.getThresholdValue("ICLPTHRESHOLD");
			int thresholdLimitPercent = tQatpMappingDao.getThresholdValue("ICLPTHRESHOLDPERCENT");
			Double currentFileHeaderCountd = Double.valueOf(currentFileHeaderCount);
			Double lastFileHeaderCountd = Double.valueOf(lastFileHeaderCount);
			Double diff = Math.abs(currentFileHeaderCountd - lastFileHeaderCountd);
			int diffpercent = (int) ((diff * 100)/lastFileHeaderCountd);
			// int diff = Math.abs (currentFileHeaderCount - lastFileHeaderCount);
			// int thresholdpercent = (diff/lastFileHeaderCount ) * 100 ;

			if ((Math.abs(currentFileHeaderCount - lastFileHeaderCount) > thresholdLimit) || diffpercent > thresholdLimitPercent) 
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
				
				
				
				
				//log.info("File record count difference exceeded threshold limit, unexceptional growth/sink");
				throw new InvlidFileHeaderException("File record count difference exceeded threshold limit, unexceptional growth/sink");
			}
		}
	}

	@Override
	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (fileExtension.equalsIgnoreCase(ICLPConstants.ICLP)) {
			log.info("File type is valid for file {} ", file.getName());
		} else {
			throw new InvalidFileTypeException("Invalid file type for file " + file.getName());
		}
	}

	public void fileParseTemplate(File file, int agencySequence) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.fileParseTemplate(file, agencySequence);
	}

}
