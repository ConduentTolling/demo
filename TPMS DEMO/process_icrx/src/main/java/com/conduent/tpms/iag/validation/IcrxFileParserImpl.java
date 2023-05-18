package com.conduent.tpms.iag.validation;

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
import java.util.Arrays;
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
import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICRXConstants;
import com.conduent.tpms.iag.dto.AccountTollPostDTO;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.AgencyTxPendingDto;
import com.conduent.tpms.iag.dto.AwayAgencyDto;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ICRXDetailInfoVO;
import com.conduent.tpms.iag.dto.ICRXHeaderInfoVO;
import com.conduent.tpms.iag.dto.ICRXQueueDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TollPostResponseDTO;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileDetailException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.exception.TransactionFileDataMismatch;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.PlanPolicyVO;
import com.conduent.tpms.iag.model.SystemAccountVO;
import com.conduent.tpms.iag.model.TCodesVO;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.ZipCode;
import com.conduent.tpms.iag.utility.Convertor;
import com.conduent.tpms.iag.utility.LocalDateAdapter;
import com.conduent.tpms.iag.utility.LocalDateTimeAdapter;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.conduent.tpms.iag.utility.MessagePublisherImpl;
import com.conduent.tpms.iag.utility.OffsetDateTimeConverter;
import com.conduent.tpms.iag.utility.OssStreamClient;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

@Component
public class IcrxFileParserImpl extends FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(IcrxFileParserImpl.class);
	private static final LocalDate fileDate = null;
	private OssStreamClient ibtsQueueClient = null;
	private Gson gson = null;
	private FileNameDetailVO fileNameVO;
	private ICRXDetailInfoVO detailVO;
	private ICRXHeaderInfoVO headerVO;
	protected Integer fileCount = 0;
	protected String filePrefix;
	protected String filePrefix1;
	protected List<AgencyInfoVO> validAgencyInfoList;
	protected int skipcounter = 0;
	protected int successCounter = 0;
	protected List<ICRXDetailInfoVO> detailVOList;
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
		if (processFileType.equalsIgnoreCase("icrx")) {
			setFileType(ICRXConstants.ICRX);
			log.info("============> initialize ICRX File");
		}
		if (processFileType.equalsIgnoreCase("irxc")) {
			setFileType(ICRXConstants.IRXC);
			log.info("============> initialize IRXC File");
		}
		// setFileType(ICRXConstants.ICRX);
		setFileFormat(ICRXConstants.FIXED);
		setAgencyId(ICRXConstants.HOME_AGENCY_ID);
		setToAgencyId(ICRXConstants.NYSTA_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);

		fileNameVO = new FileNameDetailVO();
		detailVO = new ICRXDetailInfoVO();
		headerVO = new ICRXHeaderInfoVO();

		detailVOList = new ArrayList<ICRXDetailInfoVO>();
		validAgencyInfoList = masterDataCache.getAgencyInfoVOList();
		// zipCodeList = masterDataCache.getZipCodeList();
		skipcounter = 0;
		successCounter = 0;

		planPolicyList = masterDataCache.getPlanPolicyList();
		tCodeVOList = masterDataCache.gettCodeVOList();

		
		try {
			log.info("Starting IBTS Stream initialization..");
			ibtsQueueClient = new OssStreamClient(configVariable, configVariable.getIbtsQueue(), null);
		} catch (IOException e) {
			log.error("IBTS Stream initialization failed with message:: {}",e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (ICRXConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new ICRXDetailInfoVO();
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException, FileAlreadyProcessedException {
		//headerVO.set

		switch (fMapping.getFieldName()) {
		
		case ICRXConstants.H_FILE_TYPE:
			headerVO.setFileType(value);
			break;
		
		case ICRXConstants.H_VERSION:
			headerVO.setVersion(value);
			break;	
			
		case ICRXConstants.F_FROM_AGENCY_ID:
			if (validatedNumber(value)) {
				fileNameVO.setFromAgencyId(value);
			} else {
				throw new InvalidFileNameException("File Name is invalide bz of F_From_AGENCY_ID");
			}

			break;
		case ICRXConstants.F_TO_AGENCY_ID:
			if (validatedNumber(value)) {
				fileNameVO.setToAgencyId(value);
			} else {
				throw new InvalidFileNameException("File Name is invalide bz of F_TO_AGENCY_ID");
			}
			break;
		case ICRXConstants.F_UNDERSCORE:
			if (value.equals("_")) {
				fileNameVO.setUnderscore(value);
			} else {
				throw new InvalidFileNameException("File name is not correct..");
			}
			break;
		//case ICRXConstants.F_FILE_DATE_TIME:
		case ICRXConstants.F_DATE:
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case ICRXConstants.F_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		
		case ICRXConstants.H_FROM_AGENCY_ID:
			if (validateAgencyMatch(value)) {
				headerVO.setFromAgencyId(value);
			} else {
				throw new InvlidFileHeaderException("Header FROM_AGENCY id mismatch..");
			}
			break;
		case ICRXConstants.H_TO_AGENCY_ID:
			if (validateToAgencyMatch(value)) {
				headerVO.setToAgencyId(value);
			} else {
				throw new InvlidFileHeaderException("Header TO_AGENCY id mismatch..");
			}
			break;
			
		case ICRXConstants.H_FILE_DATE_TIME:
			String latestVal = value.replaceAll("T", " ");
			latestVal = value.replaceAll("Z", "");
			LocalDateTime dateTime = LocalDateTime.parse(latestVal);
			//Need to remove these loggers while deploying
			log.info("dateTime {}.", dateTime);
			log.info("dateTime.toLocalDate() {}.", dateTime.toLocalDate());
			log.info("dateTime.toLocalTime() {}.", dateTime.toLocalTime());
			if (customDateValidation(dateTime.toLocalDate())) {
				headerVO.setFileDate(dateTime.toLocalDate());
				headerVO.setFileTime(dateTime.toLocalTime().toString());
				headerVO.setFileDateTime(dateTime);
			}
			break;
		/* New Version dosent have separate date time
		 * case ICRXConstants.H_FILE_DATE:
			LocalDate date = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
			if (customDateValidation(date)) {
				headerVO.setFileDate(date);
			}
			break;
		// headerVO.setFileDate(fileDate);
		// break;
		case ICRXConstants.H_FILE_TIME:
			headerVO.setFileTime(value);
			break;
			*/
		case ICRXConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case ICRXConstants.H_ICTX_FILE_NUM:
			headerVO.setIctxFileNum(value);
			//call dto to check duplicate File on ictx file num
			Boolean isCompleteFilePresent = tQatpMappingDao.checkIfFileProcessedAlreadyBy(value,FileProcessStatus.COMPLETE);
			if(isCompleteFilePresent) {
				throw new FileAlreadyProcessedException("File already processed");
			}
			break;
		// Added ITXC header field for IRXC
		case ICRXConstants.H_ITXC_FILE_NUM:
			headerVO.setItxcFileNum(value);//Niranjan setIctxFileNum
			headerVO.setIctxFileNum(value);
			break;
		case ICRXConstants.D_ETC_TRX_SERIAL_NUM:
			detailVO.setEtcTrxSerialNum(value);
			break;
		case ICRXConstants.D_ETC_POST_STATUS:
			detailVO.setEtcPostStatus(value);
			break;
		case ICRXConstants.D_ETC_POST_PLAN:

			// detailVO.setEtcPostPlan(value);
			if (validateETCPostPlan(value)) {
				detailVO.setEtcPostPlan(value);
			} else {
				throw new InvalidRecordException("Details ETC_POST_PLAN id mismatch..");
			}
			break;
		case ICRXConstants.D_ETC_DEBIT_CREDIT:

			if (validateETCDebitCredit(value)) {
				detailVO.setEtcDebitCard(value);
			} else {
				throw new InvalidRecordException("Details D_ETC_DEBIT_CREDIT value is invalid..");
			}

			break;
		case ICRXConstants.D_ETC_OWED_AMOUNT:
			// detailVO.setEtcOwedAmount(value);

			if (validatedNumber(value) && value.length() <= 9) {
				detailVO.setEtcOwedAmount(value);
			} else {
				throw new InvalidRecordException("Details D_ETC_OWED_AMOUNT value is invalid..");
			}

			break;
			
		case ICRXConstants.D_ETC_DUP_SERIAL_NUM:
			// detailVO.setEtcOwedAmount(value);
			if (validatedNumber(value) && value.length() <= 20) {
				detailVO.setEtcDupSerialNo(value);
			} else {
				throw new InvalidRecordException("Details D_ETC_DUP_SERIAL_NUM value is invalid..");
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
		fileDetails = tQatpMappingDao.checkIfFileProcessedAlready(fileName.trim());

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
					ICRXConstants.HOME_AGENCY_ID);
			throw new InvalidFileNameException("Invalid file prefix in file name: " + fileName);
		}
	}

	@Override
	public void finishFileProcess()
			throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException, TransactionFileDataMismatch {

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
		fileDetails.setSerialNumber(Long.parseLong(headerVO.getIctxFileNum()));
		fileDetails.setProcessedCount(successCounter);
		fileDetails.setSuccessCount(successCounter);
		fileDetails.setExceptionCount(skipcounter);
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		log.info("Update_TS timezone update into t_lane_checkpoint table : "+timeZoneConv.currentTime());
		log.info("Update_TS fileDetails update into t_lane_checkpoint fileDetails : "+fileDetails.toString());
		tQatpMappingDao.updateFileIntoCheckpoint(fileDetails);

	}

	/*
	 * @Override public void insertFileDetailsIntoStatistics() {
	 * 
	 * log.debug("Inserting file details into Statistics table.."); QatpStatistics
	 * qatpStatistics = new QatpStatistics();
	 * //qatpStatistics.setAtpFileId(fileDetails.getFileName());
	 * qatpStatistics.setFileType(fileDetails.getFileType());
	 * qatpStatistics.setFileName(fileDetails.getFileName());
	 * qatpStatistics.setInsertLocalDate(LocalDate.now());
	 * qatpStatistics.setInsertTime(LocalDateTime.now());
	 * qatpStatistics.setRecordCount(fileDetails.getFileCount());
	 * //qatpStatistics.setAmount(amount); qatpStatistics.setIsProcessed((long) 1);
	 * qatpStatistics.setProcessLocalDate(LocalDate.now());
	 * qatpStatistics.setProcessTime(LocalDateTime.now());
	 * qatpStatistics.setProcessRecCount(String.valueOf(fileDetails.getFileCount()))
	 * ; qatpStatistics.setUpLocalDateTs(LocalDateTime.now());
	 * //qatpStatistics.setXferControlId(fileDetails);
	 * tQatpMappingDao.insertQatpStatistics(qatpStatistics);
	 * 
	 * }
	 * 
	 * 
	 * /* private boolean checkRecordExistInAddress(CustomerAddressRecord record) {
	 * 
	 * boolean isExist = false;
	 * 
	 * CustomerAddressRecord tableRecord =
	 * tQatpMappingDao.checkifrecordexists(record); if(tableRecord != null) {
	 * isExist = true; }
	 * 
	 * return isExist; }
	 */

	public void validateAdnAddRecords(List<ICRXDetailInfoVO> detailVOList2, String fileType) throws TransactionFileDataMismatch {

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
		
		List<AgencyTxPendingDto> pendingAgencyList = new ArrayList<AgencyTxPendingDto>();
		int ictxRecordCount = tQatpMappingDao.getICTXRecordCountId(headerVO.getIctxFileNum());
		int icrxRecordCount = Integer.parseInt(headerVO.getRecordCount());
		if(ictxRecordCount==icrxRecordCount) {
			for (ICRXDetailInfoVO obj : detailVOList2) {
				try {
					String etcTrxNUm = obj.getEtcTrxSerialNum();

					long laneTxId = Long.parseLong(etcTrxNUm);
					log.info("IlaneTxId:===============>" + laneTxId);
					AgencyTxPendingDto pendingAgency = tQatpMappingDao.getlaneTxIdFromAgencyTxPending(laneTxId);
					log.info("Pending Agency List..{}", pendingAgency);
					if (pendingAgency!=null) {
						String etcPostPlan = obj.getEtcPostPlan();
						String etcPostStatus = obj.getEtcPostStatus();
						try {
							Long duplSerialNum = Long.parseLong(obj.getEtcDupSerialNo());
							pendingAgency.setDuplSerialNum(duplSerialNum);
						}catch(Exception ex) {
							log.info("Error while fetching DuplSerialNum..{}", ex.getMessage());
						}

						String fromAgencyId = fileNameVO.getFromAgencyId();
						Integer paramConfig = Integer.parseInt(fromAgencyId);
						SystemAccountVO systemAccountVO = null;
						if(fromAgencyId.equalsIgnoreCase(ICRXConstants.HUB_AGENCY_ID)) {
							log.info("From pendingAgency.getAccountAgencyId() [Integer]:===============>" + pendingAgency.getAccountAgencyId());
							systemAccountVO = masterDataCache.getParamValueByAgencyId(pendingAgency.getAccountAgencyId());
						}else {
							log.info("From Agency ID [Integer]:===============>" + paramConfig);
							systemAccountVO = masterDataCache.getParamValue(paramConfig);
						}
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

						if(fromAgencyId.equalsIgnoreCase(ICRXConstants.HUB_AGENCY_ID)) {
							AgencyInfoVO agencyInfo = masterDataCache.getAccountAgencyByAgencyId(pendingAgency.getAccountAgencyId());
							log.info("pendingAgency.getAccountAgencyId() to get AccountAgencyId:===============>" + agencyInfo.getFilePrefix());
							pendingAgency.setAccountAgencyId(agencyInfo.getAgencyId());
							log.info("AgencyAccountId :===============> {}", pendingAgency.getAccountAgencyId());
						}else {
						AgencyInfoVO agencyInfo = masterDataCache.getAccountAgencyId(fromAgencyId);
						log.info("File_Prefix to get AccountAgencyId:===============>" + agencyInfo.getFilePrefix());
						pendingAgency.setAccountAgencyId(agencyInfo.getAgencyId());
						log.info("AgencyAccountId :===============> {}", pendingAgency.getAccountAgencyId());
						}

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

						List<String> etcPostStatusList = new ArrayList<String>();
						etcPostStatusList.addAll(Arrays.asList(ICRXConstants.POST,ICRXConstants.PSNT,ICRXConstants.PPST,ICRXConstants.NPST,
								ICRXConstants.INSU,ICRXConstants.RJPL,ICRXConstants.OLD1,ICRXConstants.OLD2,ICRXConstants.ACCB,ICRXConstants.RINV,
								ICRXConstants.TAGB,ICRXConstants.RJDP,ICRXConstants.RJTA));

						if ((etcPostStatusList.contains(etcPostStatus))
								&& (etcPostPlan == null || etcPostPlan.equals(""))) {
							pendingAgency.setPlanTypeId(ICRXConstants.STANDARD_PLAN);
							log.info(
									"setPlanTypeId to AgencyTxPendingDto if etcPostStatus is in "+etcPostStatusList.toString()+" then pass standers plan[1]:=======>"
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

						//
						AccountTollPostDTO toll = new AccountTollPostDTO();
						AccountTollPostDTO.init(toll, pendingAgency);

						pendingAgency.setTollPostDTO(toll);


						
						//pendingAgency.setTollPostResponseDTO(postResponseDTO);

						pendingAgencyList.add(pendingAgency);

					}else if(pendingAgency==null && !(fileDetails.getProcessStatus().getCode().equalsIgnoreCase(FileProcessStatus.IN_PROGRESS.getCode()))) {
						throw new TransactionFileDataMismatch("Getting duplicate LaneTxId or something mismatch in record:"+laneTxId);
					}
					else {
						log.info("File Status is in Progress...	");
					}
				}catch (Exception e) {
					throw new TransactionFileDataMismatch("File Data Mismatch Exception"+e.getMessage());
				}
			}
			if(pendingAgencyList.size()<1) {
				throw new TransactionFileDataMismatch("No Valid Record Present in File");
			}
		}else {
			log.info("ICTX Record count===============>" +ictxRecordCount);
			log.info("ICRX Record count===============>" +icrxRecordCount);
			throw new TransactionFileDataMismatch("ICTX and ICRX Record count dosen't match");
		}

		for (AgencyTxPendingDto pendingAgency : pendingAgencyList) {

			
			AccountTollPostDTO toll =pendingAgency.getTollPostDTO();
			
			gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();
			log.info("Toll gson Request:===============>" + gson.toJson(toll).toString());

			TollPostResponseDTO postResponseDTO = tollPosting(gson.toJson(toll));
			log.info("Toll Response:===============>" + postResponseDTO.toString());
			
			//TollPostResponseDTO postResponseDTO = pendingAgency.getTollPostResponseDTO();

			// Update in transaction table
			TranDetail tranDetails = TollPostResponseDTO.getTranDetail(postResponseDTO);
			if (fileType.equalsIgnoreCase("irxc")) {
				tranDetails.setPostedFareAmount(toll.getPostedFareAmount());
				tQatpMappingDao.updateTranDetailPostedAmt(tranDetails);
			}else {
				//tQatpMappingDao.updateTranDetail(tranDetails);
				tQatpMappingDao.updateTranDetailWithFields(tranDetails,toll);
			}
			log.info("T_TRAN Table update done.");

			// Insert response to t_away_agency_tx table
			masterDataCache.getRevenueStatusByPlaza(pendingAgency.getPlazaId());

			AwayAgencyDto awayAgency = TollPostResponseDTO.getAwayAgencyDetail(postResponseDTO, toll, masterDataCache);
			
			//ICRX
			if (fileType.equalsIgnoreCase("irxc")) {
				tQatpMappingDao.updateAwayAgency(awayAgency);
				log.info("STATUS UPDATE INTO T_AWAY_AGENCY_TX TABLE.");
				
			}else {
				tQatpMappingDao.insertAwayAgency(awayAgency);
				log.info("DATA INSERT INTO T_AWAY_AGENCY_TX TABLE.");
			}
			

			// pass record to ibts_queue
			//AC5: Able to update corresponding T_VIOL_TX record with information from the ICRX record (only for VIDEO transactions).
			if (pendingAgency.getSourceSystem() == 1) {
				log.info("Inside IBTS Queue code because SourceSystem value is 1");
				ICRXQueueDto icrxQueueDto = TollPostResponseDTO.getICRXQueueDetails(postResponseDTO, toll);
				log.info("icrxQueueDto - object passing to ICRX Queue ==> " + icrxQueueDto.toString());

				List<PutMessagesDetailsEntry> messages = new LinkedList<>();
				try {
				byte[] msg = gson.toJson(icrxQueueDto).getBytes();
				messages.add(PutMessagesDetailsEntry.builder().key(icrxQueueDto.getLaneTxId().toString().getBytes())
						.value(msg).build());
				
				
				publisher.publishMessages(messages, configVariable.getIbtsQueue());
				}catch(Exception ex) {
					log.info("Exception in Publish to IBTS Queue:" +ex.getMessage());
					log.error("Exception in Publish to IBTS Queue:" +ex.getMessage());
				}
			}
			/*
			 * if(ibtsQueueClient.publishMessage(String.valueOf(icrxQueueDto.getLaneTxId()),
			 * gson.toJson(icrxQueueDto).getBytes())) {
			 * log.info("Message Published..."+icrxQueueDto); }else {
			 * log.info("Message Published Failed..."); }
			 */

			// Delete record from T_AGENCY_TX_PENDING table
			 tQatpMappingDao.deleteAgencyTxPending(awayAgency.getLaneTxId());
			 
			 //updating t_ia_file_stats table with ICRX file name and staus flag
			 tQatpMappingDao.updateICTXFileDetails(headerVO.getIctxFileNum(),fileDetails);
			 
			 successCounter++;	
			 //Need this inserted every time to check the latest updated LaneTx Id
			 updateFileDetailsInCheckPointWithLaneTxId(awayAgency.getLaneTxId(),successCounter);
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

			int thresholdLimit = ICRXConstants.THRESHOLD_LIMIT;
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
		if (fileName.contains("ICRX")) {
			fileDetails.setFileType(ICRXConstants.ICRX);
		}
		if (fileName.contains("IRXC")) {
			fileDetails.setFileType(ICRXConstants.IRXC);
		}
		fileDetails.setFileDateTime(fileNameVO.getFileDateTime());
		fileDetails.setProcessName(getFileName().substring(0, 4));
		fileDetails.setProcessId((long) ICRXConstants.ZERO);
		fileDetails.setProcessStatus(FileProcessStatus.START);
		fileDetails.setLaneTxId((long) ICRXConstants.ZERO);
		//fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setTxDate(LocalDateTime.now().toLocalDate());
		fileDetails.setSerialNumber((long) ICRXConstants.ZERO);
		fileDetails.setProcessedCount(ICRXConstants.ZERO);
		fileDetails.setFileCount(recordCount.get(getFileName()));
		fileDetails.setSuccessCount(ICRXConstants.ZERO);
		fileDetails.setExceptionCount(ICRXConstants.ZERO);
		//fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setUpdateTs(timeZoneConv.currentTime());
		log.info("Update_TS timezone insert into t_lane_checkpoint table : "+timeZoneConv.currentTime());
		tQatpMappingDao.insertFileDetails(fileDetails);

	}

	List<ICRXDetailInfoVO> detailVOListTemp;

	synchronized public void insertBatchAndUpdateCheckpoint() {

		ICRXDetailInfoVO[] records = new ICRXDetailInfoVO[detailVOList.size()];
		detailVOList.toArray(records);
		int recordCounter = 1;
		Integer counter = 1;
		Integer preProcessedRecordCount = 0;
		ICRXDetailInfoVO prevline = null;
		detailVOListTemp = new ArrayList<ICRXDetailInfoVO>();
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
						if (counter == ICRXConstants.BATCH_RECORD_COUNT) {

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
		detailVOList = new ArrayList<ICRXDetailInfoVO>();
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
	
	public void updateFileDetailsInCheckPointWithLaneTxId(Long LaneTxId, int sucessCount) {

		log.info("Updating file {} details into checkpoint table with status P.", fileNameVO.toString());
		log.info("Updating file {} details into checkpoint table with status P.", LaneTxId);

		fileDetails.setFileName(fileNameVO.getFileName().trim());
		fileDetails.setFileType(fileNameVO.getFileType().trim());
		fileDetails.setProcessStatus(FileProcessStatus.IN_PROGRESS);
		fileDetails.setTxDate(fileNameVO.getFileDateTime().toLocalDate());
		fileDetails.setFileCount(recordCount.get(fileNameVO.getFileName()).intValue());
		fileDetails.setProcessedCount(recordCount.get(fileNameVO.getFileName()).intValue());
		fileDetails.setSuccessCount(sucessCount);
		fileDetails.setUpdateTs(LocalDateTime.now());
		fileDetails.setLaneTxId(LaneTxId);
		tQatpMappingDao.updateFileIntoCheckpointLaneTxId(fileDetails);

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
		sbFileName.append(ICRXConstants.TO_AGENCY_ID).append(ICRXConstants.UNDER_SCORE_CHAR)
				.append(fileName.replace('.', '_')).append(ICRXConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();
		String icrxFileFromAgency = fileName.substring(0, 4);
		String icrxFileToAgency = fileName.substring(5, 9);

		//The from and to would reverse in case of ack file
		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(ICRXConstants.VERSION).append(StringUtils.rightPad(icrxFileToAgency, 4))
				.append(StringUtils.rightPad(icrxFileFromAgency, 4)).append(StringUtils.rightPad(fileName, 50, ""))
				.append(genericValidation.getTodayTimestampNew()).append(ackStatusCode.getCode()).append("\n");

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
		String ictxFileName = "";
		if(!ackStatusCode.equals(AckStatusCode.FILE_ALREADY_PROCESSED)) {
			ictxFileName = tQatpMappingDao.getICTXFileName(headerVO.getIctxFileNum());
		}
		log.info("ACK file status code {} set in T_IA_ACK_STATUS", ackStatusCode.getCode());
		if (fileNameVO.getFileType() == null) {
			//Fetch ICTX name
			ackFileInfoDto.setTrxFileName(ictxFileName);
			ackFileInfoDto.setReconFileName(file.getName());
			String FileName=file.getName();
			String fileExtension = FileName.substring(25, 29);
			String fromAgencyId = FileName.substring(5, 9);
			System.out.println("fromAgencyId ===>"+fromAgencyId);
			System.out.println("fileExtension ===>"+fileExtension);
			ackFileInfoDto.setFileType(fileExtension);
			ackFileInfoDto.setFromAgency(fromAgencyId);
			ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
			ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
			ackFileInfoDto.setAckFileDate(LocalDate.now());
			String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "HH:mm:ss");
			log.info("fileTime ===>"+fileTime);
			ackFileInfoDto.setAckFileTime(fileTime);
			ackObj.setAckFileInfoDto(ackFileInfoDto);
			return ackObj;
		}
		if (fileNameVO.getFileType().contains("ICRX")) {
			ackFileInfoDto.setFileType(ICRXConstants.ICRX);
			log.info("set ICRX fileType in T_IA_ACK_STATUS");
		}
		if (fileNameVO.getFileType().contains("IRXC")) {
			ackFileInfoDto.setFileType(ICRXConstants.IRXC);
			log.info("set IRXC fileType in T_IA_ACK_STATUS");
		}
		//Fetch ICTX name
		ackFileInfoDto.setTrxFileName(ictxFileName);
		ackFileInfoDto.setReconFileName(file.getName());
		ackFileInfoDto.setFromAgency(fileNameVO.getToAgencyId());
		ackFileInfoDto.setToAgency(fileNameVO.getFromAgencyId());
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime = genericValidation.getTodayTimestamp(LocalDateTime.now(), "HH:mm:ss");
		log.info("fileTime ===>"+fileTime);
		ackFileInfoDto.setAckFileTime(fileTime);
		if(headerVO!=null && headerVO.getIctxFileNum()!=null) {
		//need dao call to get AtpFileId
		ackFileInfoDto.setAtpFileId(tQatpMappingDao.getATPFileId(headerVO.getIctxFileNum()));
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
				.filter(p -> p.getFileProcessingStatus() == ICRXConstants.NO).collect(Collectors.toList());
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

			filePrefix = fileName.substring(0, 4);
			filePrefix1 = fileName.substring(5, 9);
			Boolean devicePresent = false;
			Optional<AgencyInfoVO> agencyInfoVO = validAgencyInfoList.stream()
					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();

			if (agencyInfoVO.isPresent()) {
				agencyInfoVO.get().setFileProcessingStatus(ICRXConstants.YES);
				log.info("Valid prefix for file: {}", fileName);
				devicePresent = true;
			}
			if(filePrefix.equalsIgnoreCase(ICRXConstants.HUB_AGENCY_ID)) {
				log.info("Valid HUB prefix for file: {}", fileName);
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
		if (!fileExtension.equalsIgnoreCase(ICRXConstants.ICRX)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}

	@Override
	public void CheckIRXCValidFileType(File file) throws InvalidFileTypeException {
		String fileExtension = Files.getFileExtension(file.getName());
		if (!fileExtension.equalsIgnoreCase(ICRXConstants.IRXC)) {
			log.info("File extension is invalid for file {} ", file.getName());
			throw new InvalidFileTypeException("Invalid file extension");
		}
	}

}
