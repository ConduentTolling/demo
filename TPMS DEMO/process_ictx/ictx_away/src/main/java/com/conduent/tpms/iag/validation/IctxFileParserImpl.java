package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dto.AccountApiInfoDto;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.CustomerInfoDto;
import com.conduent.tpms.iag.dto.IctxItxcNameVO;
import com.conduent.tpms.iag.dto.IctxItxcDetailInfoVO;
import com.conduent.tpms.iag.dto.IctxItxcHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TCodesVO;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.GapInSeqNumException;
import com.conduent.tpms.iag.exception.ICTXFileNumDuplicationException;
import com.conduent.tpms.iag.exception.InvalidFileDetailException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvalidFileHeaderException;
import com.conduent.tpms.iag.model.AccountInfo;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.IagFileStatistics;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.VehicleClass;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.Convertor;
import com.conduent.tpms.iag.utility.DateUtils;
import com.conduent.tpms.iag.utility.LocalDateAdapter;
import com.conduent.tpms.iag.utility.LocalDateTimeAdapter;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.conduent.tpms.iag.utility.MessagePublisher;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Component
public class IctxFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(IctxFileParserImpl.class);
	private static final LocalDate fileDate = null;
	private IctxItxcDetailInfoVO detailVO;
	private IctxItxcHeaderInfoVO headerVO;
	protected Integer fileCount = 0;
	protected String filePrefix;
	protected List<AgencyInfoVO> validAgencyInfoList;
	protected int skipcounter = 0;
	protected int successCounter = 0;
	TranDetail tranDetail = null;
//	protected List<ZipCode> zipCodeList;
	protected List<TCodesVO> tCodeVOList;
//	private List<LaneTxReconPolicy> laneTxReconPolicyList;
	private List<VehicleClass> vehicleClassList;
//	private List<laneIdExtLaneInfo> laneIdExtLaneList;


	/**
	 * Initialize the file properties
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

		fileNameVO = new IctxItxcNameVO();
		detailVO = new IctxItxcDetailInfoVO();
		headerVO = new IctxItxcHeaderInfoVO();

		detailVOList = new ArrayList<IctxItxcDetailInfoVO>();
		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
		skipcounter = 0;
		successCounter = 0;
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ICTXConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new IctxItxcDetailInfoVO();
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvalidFileHeaderException, DateTimeParseException, InvalidRecordException, ICTXFileNumDuplicationException {

		switch (fMapping.getFieldName()) {
		case ICTXConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case ICTXConstants.F_TO_AGENCY_ID:
			fileNameVO.setToAgencyId(value);
			break;
		case ICTXConstants.F_FILE_DATE_TIME:
		case ICTXConstants.F_DATE:
			try {
				fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			} catch (DateTimeParseException ex) {
				throw new InvalidFileNameException("Invalid FileDateTime: " + ex.getMessage());
			}
			break;
		case ICTXConstants.F_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case ICTXConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		case ICTXConstants.H_FROM_AGENCY_ID:
			if (validateAgencyMatch(value, fileNameVO.getFromAgencyId())) {
				headerVO.setFromAgencyId(value);
			} else {
				throw new InvalidFileHeaderException("H_FROM_AGENCY_ID mismatch..");
			}
			break;
		case ICTXConstants.H_TO_AGENCY_ID:
			if (validateAgencyMatch(value, fileNameVO.getToAgencyId())) {
				headerVO.setToAgencyId(value);
			} else {
				throw new InvalidFileHeaderException("H_TO_AGENCY_ID mismatch..");
			}
			break;
		case ICTXConstants.H_FILE_DATE:
			LocalDate date = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
			if(customDateValidation(date)) {
			headerVO.setFileDate(date);
			}
			break;
		case ICTXConstants.H_FILE_TIME:
			if (customTimeValidation(value)) {
				headerVO.setFileTime(value);
			}
			break;
		case ICTXConstants.H_FILE_DATE_TIME:
			try {
				LocalDateTime dateTime = genericValidation.getFormattedDateTimeNew(value);
				/*   TBD -> (F_FILE_DATE_TIME valid and H_FILE_DATE_TIME is future date -> ACK 3 or ACK 8)
				LocalDateTime todaydate = LocalDateTime.now();
				if (dateTime.isAfter(todaydate)) {
					throw new InvalidFileHeaderException("Header File DateTime cannot be: " + dateTime);
				}*/
				if(customDateTimeValidation(dateTime)) {
					headerVO.setFileDateTime(dateTime);
				}
			} catch (DateTimeParseException ex) {
				throw new InvalidFileHeaderException("Invalid Header FileDateTime: " + ex.getMessage());
			}
			break;
		case ICTXConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case ICTXConstants.H_ICTX_FILE_NUM:
			if(!tQatpMappingDao.checkForDuplicateFileNum(value)) {
				headerVO.setIctxFileNum(value);
			} else {
				throw new ICTXFileNumDuplicationException("ICTX_FILE_NUM has been already used");
			}
			break;
		case ICTXConstants.D_ETC_TRX_SERIAL_NUM:
			detailVO.setEtcTrxSerialNum(value);
			break;
		case ICTXConstants.D_ETC_REVENUE_DATE:
			detailVO.setEtcRevenuDate(value);
			break;
		case ICTXConstants.D_ETC_FAC_AGENCY:
			if (validateAgencyMatch(value, headerVO.getFromAgencyId())) {
			detailVO.setEtcFacAgency(value);
			} else {
				throw new InvalidRecordException("D_ETC_FAC_AGENCY mismatch..");
			}
			break;
		case ICTXConstants.D_ETC_TRX_TYPE:
			detailVO.setEtcTrxType(value);
			break;
		case ICTXConstants.D_ETC_ENTRY_DATE_TIME:
			if (genericValidation.buildDateTime(value, fMapping.getValidationValue())) {
				detailVO.setEtcEntryDateTime(value);
			} else {
				log.info("D_ETC_ENTRY_DATE_TIME Invalid {}",value);
				throw new InvalidRecordException("D_ETC_ENTRY_DATE_TIME Invalid: " + value);
			}
			break;
		case ICTXConstants.D_ETC_ENTRY_DATE:
			if(genericValidation.buildDate(value, fMapping.getValidationValue())) {
			detailVO.setEtcEntryDate(value);
			}else {
				log.info("D_ETC_ENTRY_DATE Invalid {}",value);
				throw new DateTimeParseException("D_ETC_ENTRY_DATE Invalid {}",value, 0);
			}
			break;
		case ICTXConstants.D_ETC_ENTRY_TIME:
			if(genericValidation.buildTime(value,fMapping.getValidationValue())) {
				detailVO.setEtcEntryTime(value);
			}else {
				log.info("D_ETC_ENTRY_TIME Invalid {}",value);
				throw new DateTimeParseException("D_ETC_ENTRY_TIME Invalid {}",value, 0);
			}
			break;
		case ICTXConstants.D_ETC_ENTRY_PLAZA:
			//detailVO.setEtcEntryPlaza(value);
			Integer plazaAgencyId = masterDataCache.getAgencyIdByDevicePrefix(detailVO.getEtcFacAgency());
			if("***************".equals(value) || masterDataCache.isValidPlaza(value, plazaAgencyId)) {
				detailVO.setEtcEntryPlaza(value);
			} else {
				throw new InvalidRecordException("D_ETC_ENTRY_PLAZA Invalid: " + value);
			}
			break;
		case ICTXConstants.D_ETC_ENTRY_LANE:
			/* If not present insert in MASTER.T_LANE
			 * hence removing validation
			if(!detailVO.getEtcTrxType().equals("B") && masterDataCache.validateAwayAgencyEntPlazaLane(value,detailVO.getEtcEntryPlaza())) {
				detailVO.setEtcEntryLane(value);
			}else if(detailVO.getEtcTrxType().equals("B")){
				detailVO.setEtcEntryLane(value); 
			}else {
				throw new InvalidRecordException("Invalid Entry lane and Plaza {}"+ value);
			}*/
			detailVO.setEtcEntryLane(value);
			break;
		case ICTXConstants.D_ETC_TAG_AGENCY:
			if(masterDataCache.validateHomeDevice(value)) {
				detailVO.setEtcTagAgency(value);
			}else {
				log.info("D_ETC_TAG_AGENCY Invalid {}",value);	
				throw new InvalidRecordException("D_ETC_TAG_AGENCY mismatch..");	
			}
			break;
		case ICTXConstants.D_ETC_TAG_SERIAL_NUMBER:
			detailVO.setEtcTagSerialNumber(value);
			break;
		case ICTXConstants.D_ETC_READ_PERFORMANCE:
			detailVO.setEtcReadPerformance(value);
			break;
		case ICTXConstants.D_ETC_WRITE_PERF: 
			detailVO.setEtcWritePref(value);
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
		case ICTXConstants.D_ETC_LIC_TYPE:
			detailVO.setEtcLicType(value);
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
		case ICTXConstants.D_ETC_EXIT_DATE_TIME:
			if (genericValidation.buildDateTime(value, fMapping.getValidationValue()) && (!value.startsWith("*"))) {
				detailVO.setEtcExitDateTime(value);
			} else {
				log.info("D_ETC_EXIT_DATE_TIME Invalid {}",value);
				throw new InvalidRecordException("D_ETC_EXIT_DATE_TIME Invalid: " + value);
			}
			break;
		case ICTXConstants.D_ETC_EXIT_DATE:
			detailVO.setEtcExitDate(value);
			break;
		case ICTXConstants.D_ETC_EXIT_TIME:
			detailVO.setEtcExitTime(value);
			break;
		case ICTXConstants.D_ETC_EXIT_PLAZA:
			//detailVO.setEtcExitPlaza(value);
			plazaAgencyId = masterDataCache.getAgencyIdByDevicePrefix(detailVO.getEtcFacAgency());
			if (masterDataCache.isValidPlaza(value, plazaAgencyId)) {
				detailVO.setEtcExitPlaza(value);
			} else {
				throw new InvalidRecordException("D_ETC_EXIT_PLAZA Invalid: " + value);
			}
			break;
		case ICTXConstants.D_ETC_EXIT_LANE:
			/* If not present insert in MASTER.T_LANE
			 * hence removing validation
			if(masterDataCache.validateAwayAgencyExtPlazaLane(value,detailVO.getEtcExitPlaza())) {
				detailVO.setEtcExitLane(value);
			}else {
				throw new InvalidRecordException("Invalid Exit lane and Plaza {}"+ value);
			}*/
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

	private boolean validateAgencyMatch(String agencyId, String fileAgencyID)  { 
		if (fileAgencyID.equals(agencyId)) {
			return true;
		} else {
			log.info("Agency id {} mismatch with file agency id {}.", agencyId, fileAgencyID);
			return false;
		}
	}

	public boolean customDateValidation(LocalDate value) throws InvalidFileHeaderException {

		if (!fileNameVO.getFileDateTime().toLocalDate().equals(value)) {
			log.info("Header Date{} is not matching with Header Date", value);
			throw new InvalidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
	}
	
	public boolean customDateTimeValidation(LocalDateTime value) throws InvalidFileHeaderException {

		if (!fileNameVO.getFileDateTime().equals(value)) {
			log.info("Header DateTime {} is not matching with File DateTime {}", value, fileNameVO.getFileDateTime());
			throw new InvalidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
	}

	public boolean customTimeValidation(String value) throws InvalidFileHeaderException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String time = formatter.format(fileNameVO.getFileDateTime().toLocalTime());
		if (!time.equals(value)) {
			log.info("Header Time{} is not matching with Header Time", value);
			throw new InvalidFileHeaderException("Time mismatch for File and Header");
		}
		return true;
	}

	@Override
	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvalidFileHeaderException, IOException, FileAlreadyProcessedException,
			EmptyLineException, InvalidRecordException, DateTimeParseException, InvalidFileDetailException, ICTXFileNumDuplicationException {

		super.validateAndProcessFileName(fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);

		if (super.validateFileAgency(fileName)) {
			log.info("{} is valid for home agency.", fileName);
			if (fileDetails == null) {
				log.info("{} is not processed earlier.", fileName);
				insertFileDetailsIntoCheckpoint();
				insertQATPStatisticsData(xferControl);
//				insertBatchAndUpdateCheckpoint();
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

	private void insertIAGStatisticsData(XferControl xferControl, FileDetails fileDetails) {
		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		try {
		iagFileStatistics.setXferControlId(xferControl.getXferControlId());
		iagFileStatistics.setInputFileName(xferControl.getXferFileName());
		iagFileStatistics.setInputRecCount(fileDetails.getFileCount());
		iagFileStatistics.setFromAgency(fileDetails.getFileName().substring(0,4));
		iagFileStatistics.setToAgency(fileDetails.getFileName().substring(5,9));
		iagFileStatistics.setFileType(headerVO.getFileType());
		Long atpFileId = transDetailDao.checkAtpFileInStatistics("ECTX",xferControl.getXferControlId());
		iagFileStatistics.setAtpFileId(atpFileId);
		//set file_seq_no here
		iagFileStatistics.setFileNumber(Optional.ofNullable(headerVO.getIctxFileNum()).orElse(""));
		String date = fileDetails.getFileName().substring(10,18);
		log.info("File date: {}",date);
		if(date != null) {
		iagFileStatistics.setFileDate(DateUtils.getDateYYYYMMDD(date, "dd-MMM-yy"));
		}
		log.info("Inserting into IagFileStats..");
		tQatpStatisticsDao.insertIagFileStats(iagFileStatistics);
		} catch (ParseException e) {
			log.error("Exception while inserting {} into T_IA_FILE_STATS",xferControl.getXferFileName());
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertQATPStatisticsData(XferControl xferControl) {
		tQatpStatisticsDao.insertIntoQATPStatistics(xferControl);
	}

	@Override
	public void finishFileProcess()	throws InvalidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException, ICTXFileNumDuplicationException, GapInSeqNumException {

		log.debug("Entered into finish process....");

		fileNameVO.setFileName(getFileName());
//		validateAdnAddRecords(detailVOList);
	
		/*
		 * List counterList = new ArrayList<>(); counterList.add(rejctCount);
		 * counterList.add(postCount);
		 */
		
		// Get Current max ICTX File_Seq_Number
		long latestFileSeqNum = tQatpMappingDao.getLastSuccesfulProcessedFileSeqNum();
		
		updateTranDetails(headerVO);
		//setTransDetail(tranDetail);
		insertBatchAndUpdateCheckpoint();
		updateFileDetailsIntoCheckpoint(0l, fileNameVO, headerVO);
		long recCount = Integer.parseInt(headerVO.getRecordCount());
		updateQATPStatisticsData(xferControl,getPostCount(),getRejctCount(),recCount,getPostTollAmt(),getRejctTollAmt());
		insertIAGStatisticsData(xferControl,fileDetails);
		detailVOList.clear();
		successCounter = 0;
		
		// check gap in ICTX_FILE_NUM sequence number
		if (latestFileSeqNum == 0) {
			log.info("No record found with File Type ICTX");
		} else {
			long currFileSeqNum = Long.parseLong(headerVO.getIctxFileNum());
			// duplicate check already done
			if (Math.abs(latestFileSeqNum - currFileSeqNum) == 1) {
				log.info("Valid ICTX_FILE_NUM sequence number +1 -1");
			} else if (Math.abs(latestFileSeqNum - currFileSeqNum) > 1) {
				log.info("Gap In Sequence Number for ICTX_FILE_NUM: {}", currFileSeqNum);
				throw new GapInSeqNumException("Gap In Sequence Number");
			}
		}
	}

	@Override
	public void updateQATPStatisticsData(XferControl xferControl, long postCount, long rejctCount, long recCount, Double postTollAmt, Double rejctTollAmt) {
		tQatpStatisticsDao.updateIntoQATPStatistics(xferControl, postCount, rejctCount, recCount, postTollAmt, rejctTollAmt);
		
	}
	
	public void updateIAFileStatsData(List<TranDetail> tranDetailList,XferControl xferControl) {
		iaStatsDao.updateIaFileStats(tranDetailList,xferControl);
		
	}
	
	

/*	public TranDetail updateTranDetails() {
		List<TranDetail> tranDetailList = getTranDetail(detailVOList, xferControl, fileNameVO, masterDataCache);
		log.info("Inserting detail records into T_TRAN_DETAIL table..");
		saveTransDetail(tranDetailList);
		
		if(tranDetailList != null)
		{
			
			//Pushing into queue
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();
			log.info("Pushing tranDetailList into ATP queue..");
			messagePublisher.publishMessagesList(tranDetailList, gson);
			}
		return tranDetail;
	}
*/
	public void updateFileDetailsIntoCheckpoint(Long laneTxId, IctxItxcNameVO fileNameVO, IctxItxcHeaderInfoVO headerVO) {

		log.info("LaneTXID======================> " + laneTxId);
		log.debug("Updating file details into lane_checkpoint table..");

		fileDetails.setFileName(getFileName());
		//fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setFileType(fileNameVO.getFileType());
		fileDetails.setFileCount(Integer.parseInt(headerVO.getRecordCount()));
		fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
//		fileDetails.setProcessedCount(detailVOList.size());

		fileDetails.setLaneTxId(laneTxId);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}


	public void validateThresholdLimit(File lastSuccFile) throws InvalidFileHeaderException {
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
				throw new InvalidFileHeaderException(
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
		fileDetails.setProcessName(getFileName().substring(0, 4));
		fileDetails.setProcessId((long) ICTXConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ICTXConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate()); //2020-08-23
		fileDetails.setSerialNumber((long) ICTXConstants.ZERO);
		fileDetails.setSerialNumber((long) ICTXConstants.ZERO);
		fileDetails.setProcessedCount(ICTXConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setSuccessCount(ICTXConstants.ZERO);
		fileDetails.setExceptionCount(ICTXConstants.ZERO);
		fileDetails.setUpdateTs(LocalDateTime.now());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}

	List<IctxItxcDetailInfoVO> detailVOListTemp;

	synchronized public void insertBatchAndUpdateCheckpoint() {

		IctxItxcDetailInfoVO[] records = new IctxItxcDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);
		int recordCounter = 1;
		Integer counter = 1;
		Integer preProcessedRecordCount = 0;
		IctxItxcDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<IctxItxcDetailInfoVO>();
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

//							saveRecords(fileDetails);
							fileDetails.setProcessStatus(FileProcessStatus.IN_PROGRESS);
							fileDetails.setProcessedCount(preProcessedRecordCount);
							fileDetails.setSuccessCount(recordCounter);
//							tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

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
//		saveRecords(fileDetails);
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount(recordCounter - 1);
		fileDetails.setSuccessCount(recordCounter - 1);
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);
		detailVOList = new ArrayList<IctxItxcDetailInfoVO>();
	}

//	public void saveRecords(FileDetails laneTxCheckPoint) {
//		// TODO Auto-generated method stub
//		updateFileIntoCheckpoint(laneTxCheckPoint);
//	}
	public void updateFileDetailsInCheckPoint() {

		log.debug("Updating file {} details into checkpoint table with status C.", fileNameVO.getFileName());

		fileDetails.setFileName(fileNameVO.getFileName().trim());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setProcessedCount(successCounter);
		fileDetails.setSuccessCount(successCounter);
		fileDetails.setUpdateTs(LocalDateTime.now());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException {
		log.info("Header record count: {}", headerVO.getRecordCount());
		log.info("Total record count from file: {}", recordCount);
		// if (recordCount != Convertor.toLong(headerVO.getRecordCount())) {
		if (recordCount.get(fileName).longValue() != Convertor.toLong(headerVO.getRecordCount()).longValue()) {
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

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ICTXConstants.VERSION).append(ICTXConstants.TO_AGENCY_ID)
				.append(fileName.substring(0, 4)).append(StringUtils.rightPad(fileName, 50, ""))
				.append(genericValidation.getTodayTimestampNew()).append(ackStatusCode.getCode()).append("\n");

		log.debug("Ack file name: {}", sbFileContent.toString());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		log.info("Ack file content: {}", sbFileContent.toString());
		String objStatusCode = ackStatusCode.getCode();
		log.info("Ack file StatusCode: {}", objStatusCode);
		if (objStatusCode.equalsIgnoreCase(AckStatusCode.SUCCESS.getCode())) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (objStatusCode.equalsIgnoreCase(AckStatusCode.HEADER_FAIL.getCode()) || objStatusCode.equalsIgnoreCase(AckStatusCode.DETAIL_FAIL.getCode())
				|| objStatusCode.equalsIgnoreCase(AckStatusCode.INVALID_RECORD_COUNT.getCode())) {
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
		if (tranDetail != null) {
			ackFileInfoDto.setAtpFileId(tranDetail.getAtpFileId());
		}
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "HHmmss");
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

//	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {
//
//		if (fileName != null) {
//			filePrefix = fileName.substring(0, 3);
//			Boolean devicePresent = false;
//			Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
//					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();
//
//			if (agencyInfoVO.isPresent()) {
//				agencyInfoVO.get().setFileProcessingStatus(ICTXConstants.YES);
//				log.info("Valid prefix for file: {}", fileName);
//				devicePresent = true;
//			}
//			return devicePresent;
//		} else {
//			throw new InvalidFileNameException("File name is empty");
//		}
//	}

	@Override
	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (!fileExtension.equalsIgnoreCase(ICTXConstants.ICTX)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}


		

	
	

}
