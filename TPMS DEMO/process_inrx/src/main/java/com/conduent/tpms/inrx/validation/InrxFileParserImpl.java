package com.conduent.tpms.inrx.validation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.inrx.constants.AckStatusCode;
import com.conduent.tpms.inrx.constants.FileProcessStatus;
import com.conduent.tpms.inrx.constants.INRXConstants;
import com.conduent.tpms.inrx.dto.AccountTollPostDTO;
import com.conduent.tpms.inrx.dto.AckFileInfoDto;
import com.conduent.tpms.inrx.dto.AckFileWrapper;
import com.conduent.tpms.inrx.dto.AgencyTxPendingDto;
import com.conduent.tpms.inrx.dto.AwayAgencyDto;
import com.conduent.tpms.inrx.dto.FileNameDetailVO;
import com.conduent.tpms.inrx.dto.INRXHeaderInfoVO;
import com.conduent.tpms.inrx.dto.INRXQueueDto;
import com.conduent.tpms.inrx.dto.INRXDetailInfoVO;
import com.conduent.tpms.inrx.dto.MappingInfoDto;
import com.conduent.tpms.inrx.dto.TollPostResponseDTO;
import com.conduent.tpms.inrx.exception.EmptyLineException;
import com.conduent.tpms.inrx.exception.FileAlreadyProcessedException;
import com.conduent.tpms.inrx.exception.InvalidFileDetailException;
import com.conduent.tpms.inrx.exception.InvalidFileNameException;
import com.conduent.tpms.inrx.exception.InvalidFileTypeException;
import com.conduent.tpms.inrx.exception.InvalidRecordCountException;
import com.conduent.tpms.inrx.exception.InvalidRecordException;
import com.conduent.tpms.inrx.exception.InvlidFileHeaderException;
import com.conduent.tpms.inrx.model.AgencyInfoVO;
import com.conduent.tpms.inrx.model.ConfigVariable;
import com.conduent.tpms.inrx.model.FileDetails;
import com.conduent.tpms.inrx.model.PlanPolicyVO;
import com.conduent.tpms.inrx.model.SystemAccountVO;
import com.conduent.tpms.inrx.model.TCodesVO;
import com.conduent.tpms.inrx.model.TranDetail;
import com.conduent.tpms.inrx.model.ZipCode;
import com.conduent.tpms.inrx.utility.Convertor;
import com.conduent.tpms.inrx.utility.LocalDateAdapter;
import com.conduent.tpms.inrx.utility.LocalDateTimeAdapter;
import com.conduent.tpms.inrx.utility.MasterDataCache;
import com.conduent.tpms.inrx.utility.MessagePublisherImpl;
import com.conduent.tpms.inrx.utility.OffsetDateTimeConverter;
import com.conduent.tpms.inrx.utility.OssStreamClient;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

@Component
public class InrxFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(InrxFileParserImpl.class);
	private static final LocalDate fileDate = null;
	private OssStreamClient ibtsQueueClient = null;
	private Gson gson = null;
	private FileNameDetailVO fileNameVO;
	private INRXDetailInfoVO detailVO;
	private INRXHeaderInfoVO headerVO;
	protected Integer fileCount = 0;
	protected String filePrefix;
	protected String filePrefix1;
	protected List<AgencyInfoVO> validAgencyInfoList;
	protected int skipcounter = 0;
	protected int successCounter = 0;
	protected List<INRXDetailInfoVO> detailVOList;
	protected List<ZipCode> zipCodeList;

	protected List<PlanPolicyVO> planPolicyList; // added by shrikant
	protected List<TCodesVO> tCodeVOList; // added by shrikant

	private String zipCodeNew;

	public String getZipCodeNew() {
		return zipCodeNew;
	}

	public void setZipCodeNew(String zipCodeNew) {
		this.zipCodeNew = zipCodeNew;
	}

	@Autowired
	private ConfigVariable configVariable;

	@Autowired
	private MessagePublisherImpl publisher;

	@Autowired
	MasterDataCache masterDataCache;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Initialize the IXRC file properties
	 */

	@Override
	public void initialize(String processFileType) {

		log.info("Initializing fileType======================>" + processFileType);
		if (processFileType.equalsIgnoreCase("inrx")) {
			setFileType(INRXConstants.INRX);
			log.info("============> initialize INRX File");
		}
		if (processFileType.equalsIgnoreCase("irxn")) {
			setFileType(INRXConstants.IRXN);
			log.info("============> initialize IRXN File");
		}
		// setFileType(INRXConstants.INRX);
		setFileFormat(INRXConstants.FIXED);
		setAgencyId(INRXConstants.HOME_AGENCY_ID);
		setToAgencyId(INRXConstants.NYSTA_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);

		fileNameVO = new FileNameDetailVO();
		detailVO = new INRXDetailInfoVO();
		headerVO = new INRXHeaderInfoVO();

		detailVOList = new ArrayList<INRXDetailInfoVO>();
		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
		// zipCodeList = masterDataCache.getZipCodeList();
		skipcounter = 0;
		successCounter = 0;

		planPolicyList = masterDataCache.getPlanPolicyList();
		tCodeVOList = masterDataCache.gettCodeVOList();

		
//		try {
//			log.info("Starting IBTS Stream initialization..");
//			ibtsQueueClient = new OssStreamClient(configVariable, configVariable.getIbtsQueue(), null);
//		} catch (IOException e) {
//			log.error("IBTS Stream initialization failed with message:: {}",e.getMessage());
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (INRXConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new INRXDetailInfoVO();
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException {
		//headerVO.set

		switch (fMapping.getFieldName()) {
		
		case INRXConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
			
		case INRXConstants.F_HOME_AGENCY_ID:
			if (validatedNumber(value)) {
				fileNameVO.setFromAgencyId(value);
			} else {
				throw new InvalidFileNameException("File Name is invalide bz of F_Home_AGENCY_ID");
			}

			break;
		case INRXConstants.F_HOST_AGENCY_ID:
			if (validatedNumber(value)) {
				fileNameVO.setToAgencyId(value);
			} else {
				throw new InvalidFileNameException("File Name is invalide bz of F_HOST_AGENCY_ID");
			}
			break;
		case INRXConstants.F_UNDERSCORE:
			if (value.equals("_")) {
				fileNameVO.setUnderscore(value);
			} else {
				throw new InvalidFileNameException("File name is not correct..");
			}
			break;
		//case ICRXConstants.F_FILE_DATE_TIME:
		case INRXConstants.F_DATE:
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case INRXConstants.F_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		
		case INRXConstants.H_HOME_AGENCY_ID:
			if (validateAgencyMatch(value)) {
				headerVO.setFromAgencyId(value);
			} else {
				throw new InvlidFileHeaderException("Header HOME_AGENCY id mismatch..");
			}
			break;
		case INRXConstants.H_HOST_AGENCY_ID:
			if (validateToAgencyMatch(value)) {
				headerVO.setToAgencyId(value);
			} else {
				throw new InvlidFileHeaderException("Header HOST_AGENCY id mismatch..");
			}
			break;
		case INRXConstants.H_FILE_DATE:
			LocalDate date = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
			if (customDateValidation(date)) {
				headerVO.setFileDate(date);
			}
			break;
		// headerVO.setFileDate(fileDate);
		// break;
		case INRXConstants.H_FILE_TIME:
			headerVO.setFileTime(value);
			break;
		case INRXConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case INRXConstants.H_INTX_FILE_NUM:
			headerVO.setIntxFileNum(value);
			break;
		// Added ITXC header field for IRXC
		case INRXConstants.H_ITXN_FILE_NUM:
			headerVO.setItxnFileNum(value);//Niranjan setIctxFileNum
			headerVO.setIntxFileNum(value);
			break;
		case INRXConstants.D_ETC_TRX_SERIAL_NUM:
			detailVO.setEtcTrxSerialNum(value);
			break;
		case INRXConstants.D_ETC_POST_STATUS:
			detailVO.setEtcPostStatus(value);
			break;
		case INRXConstants.D_ETC_POST_PLAN:

			// detailVO.setEtcPostPlan(value);
			if (validateETCPostPlan(value)) {
				detailVO.setEtcPostPlan(value);
			} else {
				throw new InvalidRecordException("Details ETC_POST_PLAN id mismatch..");
			}
			break;
		case INRXConstants.D_ETC_DEBIT_CREDIT:

			if (validateETCDebitCredit(value)) {
				detailVO.setEtcDebitCard(value);
			} else {
				throw new InvalidRecordException("Details D_ETC_DEBIT_CREDIT value is invalid..");
			}

			break;
		case INRXConstants.D_ETC_OWED_AMOUNT:
			// detailVO.setEtcOwedAmount(value);

			if (validatedNumber(value) && value.length() <=7) {
				detailVO.setEtcOwedAmount(value);
			} else {
				throw new InvalidRecordException("Details D_ETC_OWED_AMOUNT value is invalid..");
			}

			break;
		default:

		}
		log.debug("Mapping done for value: {} with field: {}", value, fMapping);
	}

	private boolean validateETCDebitCredit(String dbitCreditValue) throws InvalidRecordException {
		if (dbitCreditValue.equals("+")) {
			return true;
		} else if (dbitCreditValue.equals("-")) {
			return true;
		} else if (dbitCreditValue.equals(" ")) {
			return true;
		}

		return false;

	}

	private boolean validatedNumber(String toAgency) throws InvalidFileNameException {
		String pattern = "[0-9]+";
		if (toAgency.matches(pattern)) {
			return true;
		}

		return false;

	}

	private boolean validateETCPostPlan(String etcPostPlan) throws InvalidRecordException {

		List<String> list = new ArrayList<String>();
		list.add("00002");
		list.add("00003");
		list.add("00004");
		list.add("00005");
		list.add("00006");
		list.add("00007");
		list.add("00008");
		list.add("00009");
		list.add("00010");
		list.add("00011");
		list.add("     ");

		if (list.contains(etcPostPlan)) {
			return true;
		} else {
			log.info("EtcPostPlan id {} mismatch with file EtcPostPlan id {}.", etcPostPlan, list);
			return false;
		}
	}

	private boolean validateAgencyMatch(String agencyId) throws InvlidFileHeaderException {
		if (filePrefix.equals(agencyId)) {
			return true;
		} else {
			log.info("Agency id {} mismatch with file agency id {}.", agencyId, filePrefix);
			return false;
		}
	}

	private boolean validateToAgencyMatch(String agencyId) throws InvlidFileHeaderException {
		if (filePrefix1.equals(agencyId)) {
			return true;
		} else {
			log.info("Agency id {} mismatch with file agency id {}.", agencyId, filePrefix1);
			return false;
		}
	}

	public boolean customDateValidation(LocalDate value) throws InvlidFileHeaderException {

		if (!fileNameVO.getFileDateTime().toLocalDate().equals(value)) {
			log.info("Header Date {} is not matching with File Date", value);
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
			EmptyLineException, InvalidRecordException, DateTimeParseException, InvalidFileDetailException {

		super.validateAndProcessFileName(fileName);
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName);

		if (validateFileAgency(fileName)) {
			log.info("{} is valid for home agency.", fileName);

			if (fileDetails == null) {
				log.info("{} is not processed earlier.", fileName);
				insertFileDetailsIntoCheckpoint(fileName);
			} else if (fileDetails.getProcessStatus() == FileProcessStatus.COMPLETE) {
				log.info("{} is already processed.", fileName);
				throw new FileAlreadyProcessedException("File already processed");
			}
		} else {
			log.info("Invalid file prefix in file name:{} for home agency id: {}", fileName,
					INRXConstants.HOME_AGENCY_ID);
			throw new InvalidFileNameException("Invalid file prefix in file name: " + fileName);
		}
	}

	@Override
	public void finishFileProcess()
			throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException {

		log.debug("Inserted into finish process....");

		fileNameVO.setFileName(getFileName());
		String fileType = getFileType();
		log.info("finishFileProcess fileType::{}",fileType);
		log.info("Going inside validate and add record method...");
		validateAdnAddRecords(detailVOList,fileType);
		log.info("End of validate and add record method...");

		updateFileDetailsIntoCheckpoint();

		insertFileDetailsIntoStatistics();

		// update statistics

		// set staticts pojo,
		// call update statistics()

		detailVOList.clear();
		successCounter = 0;
		skipcounter = 0;

	}

	@Override
	public void updateFileDetailsIntoCheckpoint() {

		log.debug("Updating file details into lane_checkpoint table..");

		fileDetails.setFileName(getFileName());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setFileCount(Integer.parseInt(headerVO.getRecordCount()));
		//fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(fileNameVO.getFromAgencyId());
		fileDetails.setProcessStatus(FileProcessStatus.COMPLETE);
		fileDetails.setProcessedCount(successCounter);
		fileDetails.setSuccessCount(successCounter);
		fileDetails.setExceptionCount(skipcounter);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		log.info("Update_TS timezone update into t_lane_checkpoint table : "+timeZoneConv.currentTime());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	

	public void validateAdnAddRecords(List<INRXDetailInfoVO> detailVOList2, String fileType) {

		// ETC_POST_PLAN form ICD document
		Map<String, String> map = new HashMap<>();

		map.put("00002", "PASI");
		map.put("00003", "PAAB");
		map.put("00004", "PACP");
		map.put("00005", "PANRPA");
		map.put("00006", "NYSBA");
		map.put("00007", "NY12");
		map.put("00008", "TZC");
		map.put("00009", "TZPL");
		map.put("00010", "RR");
		map.put("00011", "SIR");
		// map.put("00012", "");//FOR NJ IGNORE HERE
		// map.put("00013", "");//FOR NJ IGNORE HERE
		// map.put("00014", "");//FOR NJ IGNORE HERE

		for (INRXDetailInfoVO obj : detailVOList2) {
			String etcTrxNUm = obj.getEtcTrxSerialNum();
			long laneTxId = Long.parseLong(etcTrxNUm);
			log.info("IlaneTxId:===============>" + laneTxId);
			AgencyTxPendingDto pendingAgency = tQatpMappingDao.getlaneTxIdFromAgencyTxPending(laneTxId);
			log.info("Pending Agency List..{}", pendingAgency);

			
		if (pendingAgency!=null) {
			
			String etcPostPlan = obj.getEtcPostPlan();
			String etcPostStatus = obj.getEtcPostStatus();

			String fromAgencyId = fileNameVO.getFromAgencyId();
			Integer paramConfig = Integer.parseInt(fromAgencyId);
			log.info("From Agency ID [Integer]:===============>" + paramConfig);
			SystemAccountVO systemAccountVO = masterDataCache.getParamValue(paramConfig);

			String paramValue = systemAccountVO.getParamValue();
			long etcAccountId = Long.parseLong(paramValue);
			log.info("etcAccountId is printed..:===============> " + etcAccountId);
			long etcAccountIdParsing = etcAccountId / 10;
			log.info("Etc Account Id after parsing value : {}", etcAccountIdParsing);
			pendingAgency.setEtcAccountId(etcAccountIdParsing);
			log.info("etcAccountId after parsing value is set:===============> {}", pendingAgency.getEtcAccountId());

			/*
			 * String agencyId = tQatpMappingDao.getAccountAgencyId(etcAccountIdParsing);
			 * Integer accountAgencyId = Integer.parseInt(agencyId);
			 * log.info("accountAgencyId:===============>" + agencyId);
			 * pendingAgency.setAccountAgencyId(accountAgencyId);
			 */

			AgencyInfoVO agencyInfo = masterDataCache.getAccountAgencyId(fromAgencyId);
			log.info("File_Prefix to get AccountAgencyId:===============>" + agencyInfo.getFilePrefix());
			pendingAgency.setAccountAgencyId(agencyInfo.getAgencyId());
			log.info("AgencyAccountId :===============> {}", pendingAgency.getAccountAgencyId());

			
			String amount = obj.getEtcOwedAmount();
			double owedAmount = Double.parseDouble(amount);
			owedAmount = owedAmount / 100;
			log.info("owedAmount:===============>" + owedAmount);
			pendingAgency.setPostedFareAmount(owedAmount);

			log.info("etcPostStatus:===============>" + etcPostStatus);
			TCodesVO tCodId = masterDataCache.getTcodeId(etcPostStatus);
			log.info("get T_CODE_ID:===============>" + tCodId.getCodeId());
			pendingAgency.setTxStatus(tCodId.getCodeId());

			if (etcPostPlan != null) {
				etcPostPlan = etcPostPlan.replaceAll("\\s+", "");
			}

			if ((etcPostStatus.equals(INRXConstants.POST) || etcPostStatus.equals(INRXConstants.PSNT)
					|| etcPostStatus.equals(INRXConstants.PPST) || etcPostStatus.equals(INRXConstants.NPST))
					&& (etcPostPlan == null || etcPostPlan.equals(""))) {
				pendingAgency.setPlanTypeId(INRXConstants.STANDARD_PLAN);
				log.info(
						"setPlanTypeId to AgencyTxPendingDto if etcPostStatus is [POST,PSNT,PPST,NPST] then pass standers plan[1]:=======>"
								+ pendingAgency.getPlanTypeId());
			}

			if (map.containsKey(etcPostPlan)) {
				String plan = map.get(etcPostPlan);
				log.info("Plan in Map:===============>" + plan);
				PlanPolicyVO planType = masterDataCache.getPlanId(plan);
				log.info("PLAN_TYPE:===============>" + planType.getPlanType());
				pendingAgency.setPlanTypeId(planType.getPlanType());
				log.info("setPlanTypeId to AgencyTxPendingDto======>" + pendingAgency.getPlanTypeId());
			} else {
				log.info("correct Plan Type is not selected...	");
			}
			AccountTollPostDTO toll = new AccountTollPostDTO();
			AccountTollPostDTO.init(toll, pendingAgency);

			gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();

			TollPostResponseDTO postResponseDTO = tollPosting(gson.toJson(toll));
			log.info("Toll gson Request:===============>" + gson.toJson(toll).toString());
			log.info("Toll Response:===============>" + postResponseDTO.toString());

			// Update in transaction table
			TranDetail tranDetails = TollPostResponseDTO.getTranDetail(postResponseDTO);
			if (fileType.equalsIgnoreCase("irxn")) {
				tranDetails.setPostedFareAmount(toll.getPostedFareAmount());
				tQatpMappingDao.updateTranDetailPostedAmt(tranDetails);
			}else {
				tQatpMappingDao.updateTranDetail(tranDetails);
			}
			log.info("T_TRAN Table update done.");

			// Insert response to t_away_agency_tx table
			masterDataCache.getRevenueStatusByPlaza(pendingAgency.getPlazaId());

			AwayAgencyDto awayAgency = TollPostResponseDTO.getAwayAgencyDetail(postResponseDTO, toll, masterDataCache);
			
			//ICRX
			if (fileType.equalsIgnoreCase("irxn")) {
				tQatpMappingDao.updateAwayAgency(awayAgency);
				log.info("STATUS UPDATE INTO T_AWAY_AGENCY_TX TABLE.");
				
			}else {
				tQatpMappingDao.insertAwayAgency(awayAgency);
				log.info("DATA INSERT INTO T_AWAY_AGENCY_TX TABLE.");
			}
			

			// pass record to ibts_queue
			if (pendingAgency.getSourceSystem() == 1) {
				log.info("Inside IBTS Queue code because SourceSystem value is 1");
				INRXQueueDto inrxQueueDto = TollPostResponseDTO.getINRXQueueDetails(postResponseDTO, toll);
				log.info("icrxQueueDto - object passing to INRX Queue ==> " + inrxQueueDto.toString());

				List<PutMessagesDetailsEntry> messages = new LinkedList<>();

				byte[] msg = gson.toJson(inrxQueueDto).getBytes();
				messages.add(PutMessagesDetailsEntry.builder().key(inrxQueueDto.getLaneTxId().toString().getBytes())
						.value(msg).build());

				publisher.publishMessages(messages, configVariable.getIbtsQueue());
			}
			/*
			 * if(ibtsQueueClient.publishMessage(String.valueOf(icrxQueueDto.getLaneTxId()),
			 * gson.toJson(icrxQueueDto).getBytes())) {
			 * log.info("Message Published..."+icrxQueueDto); }else {
			 * log.info("Message Published Failed..."); }
			 */

			// Delete record from T_AGENCY_TX_PENDING table
			 tQatpMappingDao.deleteAgencyTxPending(awayAgency.getLaneTxId());
			 
			 //updating t_ia_file_stats table with INRX file name and staus flag
			 tQatpMappingDao.updateINTXFileDetails(headerVO.getIntxFileNum(),fileDetails);
			 successCounter++;
				
			}else {
				log.info("Getting duplicate LaneTxId or something mismatch in record");
			}
			
		}
		skipcounter = Integer.parseInt(headerVO.getRecordCount()) - successCounter;

	}

	public TollPostResponseDTO tollPosting(String accountTollPostDTO) {
		// String url =
		// "https://image-dev-tollingbos.services.conduent.com/fpms/tpmsIntegration/accountToll";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO, headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollPostingUri(), entity,
					String.class);

			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				log.info("Got response {}", jsonObject);
				Gson gson = new Gson();
				return gson.fromJson(jsonObject.getAsJsonObject("result"), TollPostResponseDTO.class);
			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				log.info("Got response {}", jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error: Exception {} while toll posting {}", e.getMessage(), accountTollPostDTO);
		}
		return null;
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

			int thresholdLimit = INRXConstants.THRESHOLD_LIMIT;
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
	public void insertFileDetailsIntoCheckpoint(String fileName) {

		log.debug("Inserting file details into lane_checkpoint table..");

		fileDetails = new FileDetails();
		fileDetails.setFileName(getFileName());
		if (fileName.contains("INRX")) {
			fileDetails.setFileType(INRXConstants.INRX);
		}
		if (fileName.contains("IRXN")) {
			fileDetails.setFileType(INRXConstants.IRXN);
		}
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 3));
		fileDetails.setProcessId((long) INRXConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) INRXConstants.ZERO);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setSerialNumber((long) INRXConstants.ZERO);
		fileDetails.setProcessedCount(INRXConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setSuccessCount(INRXConstants.ZERO);
		fileDetails.setExceptionCount(INRXConstants.ZERO);
		//fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		log.info("Update_TS timezone insert into t_lane_checkpoint table : "+timeZoneConv.currentTime());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}

	List<INRXDetailInfoVO> detailVOListTemp;

	synchronized public void insertBatchAndUpdateCheckpoint() {

		INRXDetailInfoVO[] records = new INRXDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);
		int recordCounter = 1;
		Integer counter = 1;
		Integer preProcessedRecordCount = 0;
		INRXDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<INRXDetailInfoVO>();
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
						if (counter == INRXConstants.BATCH_RECORD_COUNT) {

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
		detailVOList = new ArrayList<INRXDetailInfoVO>();
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
		sbFileName.append(INRXConstants.HOST_AGENCY_ID).append(INRXConstants.UNDER_SCORE_CHAR)
				.append(fileName.replace('.', '_')).append(INRXConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(INRXConstants.HOST_AGENCY_ID)
				.append(INRXConstants.HOME_AGENCY_ID).append(StringUtils.rightPad(fileName, 50, ""))
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
		log.info("ACK file status code {} set in T_IA_ACK_STATUS", ackStatusCode.getCode());
		if (fileNameVO.getFileType() == null) {
			ackFileInfoDto.setTrxFileName(file.getName());
			String FileName=file.getName();
			String fileExtension = FileName.substring(23, 27);
			String fromAgencyId = FileName.substring(4, 7);
			System.out.println("fromAgencyId ===>"+fromAgencyId);
			System.out.println("fileExtension ===>"+fileExtension);
			ackFileInfoDto.setFileType(fileExtension);
			ackFileInfoDto.setFromAgency(fromAgencyId);
			ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
			ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
			ackFileInfoDto.setAckFileDate(LocalDate.now());
			String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss");
			ackFileInfoDto.setAckFileTime(fileTime);
			ackObj.setAckFileInfoDto(ackFileInfoDto);
			return ackObj;
		}
		if (fileNameVO.getFileType().contains("INRX")) {
			ackFileInfoDto.setFileType(INRXConstants.INRX);
			log.info("set INRX fileType in T_IA_ACK_STATUS");
		}
		if (fileNameVO.getFileType().contains("IRXN")) {
			ackFileInfoDto.setFileType(INRXConstants.IRXN);
			log.info("set IRXN fileType in T_IA_ACK_STATUS");
		}
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(fileNameVO.getToAgencyId());
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		if(headerVO!=null && headerVO.getIntxFileNum()!=null) {
		//need dao call to get AtpFileId
		ackFileInfoDto.setAtpFileId(tQatpMappingDao.getATPFileId(headerVO.getIntxFileNum()));
		log.info("set ATP fileID "+ackFileInfoDto.getAtpFileId());
		}
		
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
				.filter(p -> p.getFileProcessingStatus() == INRXConstants.NO).collect(Collectors.toList());
		log.info("Missing files list : " + missingFiles);
	}

	/*
	 * public void getMissingFilesForAgency1() { List<AgencyInfoVO> missingFiles =
	 * validAgencyInfoList.stream() .filter(p -> p.getFileProcessingStatus() ==
	 * ICRXConstants.NO).collect(Collectors.toList());
	 * 
	 * log.info("Missing files list : " + missingFiles);
	 * 
	 * }
	 */

	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {

		if (fileName != null) {

			filePrefix = fileName.substring(0, 3);
			filePrefix1 = fileName.substring(4, 7);
			Boolean devicePresent = false;
			//Boolean devicePresent = true;
			Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();

			if (agencyInfoVO.isPresent()) {
				agencyInfoVO.get().setFileProcessingStatus(INRXConstants.YES);
				log.info("Valid prefix for file: {}", fileName);
				devicePresent = true;
			}
//			String test="131";
//			if (test==filePrefix) {
//				agencyInfoVO.get().setFileProcessingStatus(INRXConstants.YES);
//				log.info("Valid prefix for file: {}", fileName);
//				devicePresent = true;
//			}
			return devicePresent;
		} else {
			throw new InvalidFileNameException("File name is empty");
		}
	}

	@Override
	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (!fileExtension.equalsIgnoreCase(INRXConstants.INRX)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}

	@Override
	public void CheckIRXNValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (!fileExtension.equalsIgnoreCase(INRXConstants.IRXN)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}

}
