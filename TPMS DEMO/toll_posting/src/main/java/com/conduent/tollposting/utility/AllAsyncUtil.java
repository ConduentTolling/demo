package com.conduent.tollposting.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.tollposting.constant.AccountType;
import com.conduent.tollposting.constant.ConfigVariable;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.constant.PlanType;
import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.dao.QatpStatisticsDao;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.dto.AccountTollPostDTO;
import com.conduent.tollposting.dto.EtcTransaction;
import com.conduent.tollposting.dto.FailureMessageDto;
import com.conduent.tollposting.dto.IBTSDataDto;
import com.conduent.tollposting.dto.MaxTollDataDto;
import com.conduent.tollposting.dto.QatpStatistics;
import com.conduent.tollposting.dto.TollPostResponseDTO;
import com.conduent.tollposting.exception.PrePostingLevelExeption;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.model.Device;
import com.conduent.tollposting.model.FpmsAccount;
import com.conduent.tollposting.model.IcrxRecon;
import com.conduent.tollposting.model.Lane;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.model.Plaza;
import com.conduent.tollposting.model.TollPostLimit;
import com.conduent.tollposting.model.TranDetail;
import com.conduent.tollposting.oss.OssStreamClient;
import com.conduent.tollposting.service.FailureHandlerService;
import com.conduent.tollposting.service.IAccountInfoService;
import com.conduent.tollposting.service.IAccountPlanService;
import com.conduent.tollposting.service.IDeviceService;
import com.conduent.tollposting.service.IFpmsAccountService;
import com.conduent.tollposting.service.IIcrxReconService;
import com.conduent.tollposting.service.ITollExceptionService;
import com.conduent.tollposting.service.ITranDetailService;
import com.conduent.tollposting.serviceimpl.CommuterPlanService;
import com.conduent.tollposting.serviceimpl.PlanSelection;
import com.conduent.tollposting.serviceimpl.TollCalculation;
import com.conduent.tollposting.serviceimpl.TollException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

@Service
public class AllAsyncUtil {

	private static final Logger logger = LoggerFactory.getLogger(AllAsyncUtil.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigVariable configVariable;

	@Autowired
	private ITranDetailService tranDetailService;

	@Autowired
	private TollCalculation tollCalculation;

	@Autowired
	private IFpmsAccountService fpmsAccountService;

	@Autowired
	private CommuterPlanService commuterPlanService;

	@Autowired
	private IIcrxReconService icrxReconService;
	
	@Autowired
	private QatpStatisticsDao qatpStatisticsDao;

	@Autowired
	private ITollExceptionService tollExceptionService;

	@Autowired
	private MasterCache masterCache;

	@Autowired
	private IAccountInfoService accountInfoService;

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private PlanSelection planSelection;

	@Autowired
	private IAccountPlanService accountPlanService;

	@Autowired
	private TodIdGeneratorUtil todIdGeneratorUtil;

	@Autowired
	private Map<String, String> myTollPostingContext;

	@Autowired
	private FailureHandlerService failureHandlerService;
	
	@Autowired
	private ValidateTxnUtil validateTxnUtil;

	public String podName = null;
	
	private TransactionTemplate transactionTemplate;
	
	//semaphore added for taking lock sequentially
	private final Semaphore semaphore;
	
	public AllAsyncUtil() {
		semaphore = new Semaphore(1);
	}

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@PostConstruct
	public void init() {
		podName = myTollPostingContext.get("podName") != null ? myTollPostingContext.get("podName") : "defaultPod";
		 Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null.");
		 this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TollPostResponseDTO tollPosting(String accountTollPostDTO, AccountTollPostDTO tollPost,
			EtcTransaction etcTransaction, OssStreamClient ibtsClientQueue, Gson gsonNew,
			OssStreamClient failureQueueClient) {
		TollPostResponseDTO postResponseDTO = null;
		try {
			logger.debug("## {} HOSTNAME: {} in AllSync.tollPosting..", Thread.currentThread().getName(),
					podName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(gsonNew.toJson(tollPost), headers);
			ResponseEntity<String> result;
			LocalDateTime from = LocalDateTime.now();

			if (tollPost.getTxTypeInd().equals("I") && tollPost.getTxSubtypeInd().equals("A")) {
				logger.info("Posting in FPMS correction api for laneTxId {}.", tollPost.getLaneTxId());
				result = restTemplate.postForEntity(configVariable.getIagCorrUri(), entity, String.class);
			} else {
				logger.info("Posting in FPMS for laneTxId {}.", tollPost.getLaneTxId());
				result = restTemplate.postForEntity(configVariable.getTollPostingUri(), entity, String.class);
			}
			logger.debug("## Time Taken for {} HOSTNAME: {} in FPMS API postForEntity call: {} ms",
					Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			logger.info("After posting result: {}", result);
			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response for laneTxId {}. Response: {}", tollPost.getLaneTxId(), jsonObject);

				JsonElement element = jsonObject.get("result");
				if (!(element instanceof JsonNull)) {
					Gson gson = new Gson();
					postResponseDTO = gson.fromJson(jsonObject.getAsJsonObject("result"), TollPostResponseDTO.class);
					if (postResponseDTO != null && tollPost != null && etcTransaction != null) {
						//afterposting call
						txnAfterPosting(tollPost, postResponseDTO, etcTransaction, ibtsClientQueue, gsonNew, failureQueueClient);
					}
				} else {
					logger.info("Error: Etc account id {} does not exist.", tollPost.getEtcAccountId());
				}
			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response {}", jsonObject);
			}

			// 2 comment upto
		} catch (Exception e) {
			logger.error("Exception caught during toll-posting txn: {}",tollPost);
			logger.error("Exception message for {}: {}",tollPost.getLaneTxId(), e.getMessage());
			FailureMessageDto failureMessageDto = new FailureMessageDto();
			failureMessageDto.setLaneTxId(tollPost.getLaneTxId());
			ArrayList<Object> objlist = new ArrayList<Object>();
			objlist.add(etcTransaction);
			objlist.add(tollPost);
			objlist.add(postResponseDTO);
			failureMessageDto.setPayload(objlist);
			failureMessageDto.setKey(String.valueOf(tollPost.getLaneTxId()));
			failureMessageDto.setKeyType(Constants.LANE_TX_ID);
			StackTraceElement[] elements = e.getStackTrace();
			failureMessageDto.setFailureMsg(elements[0].toString());
			failureMessageDto.setFailureTimestamp(OffsetDateTime.now());
			failureMessageDto.setOriginQueueName("ATP");

			if (e.getStackTrace().toString().contains("afterPosting") || postResponseDTO != null) {
				failureMessageDto.setFailureState(Constants.AFTERPOSTING);
			} else {
				failureMessageDto.setFailureState(Constants.TOLLPOSTING);
			}

			if (e instanceof RuntimeException) {
				if (e instanceof ServiceUnavailable || e instanceof InternalServerError
						|| e instanceof RestClientException) {
					failureMessageDto.setFailureType("ClientServiceException");
					failureMessageDto.setFailureSubtype(Constants.RECOVERABLE);
					failureMessageDto.setErrorCode(500);
				} else {
					failureMessageDto.setFailureType("RuntimeException");
					failureMessageDto.setFailureSubtype(Constants.NONRECOVERABLE);
				}
			}
			failureHandlerService.persistFailureEvent(failureMessageDto, failureQueueClient, gsonNew);
		}
		return postResponseDTO;
	}

	public void txnAfterPosting(AccountTollPostDTO tollPost, TollPostResponseDTO postResponseDTO,
			EtcTransaction etcTransaction, OssStreamClient ibtsClientQueue, Gson gsonNew,
			OssStreamClient failureQueueClient) throws Exception{
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try { 
					logger.debug("AllAsyncUtil {} Started the transaction ", Thread.currentThread().getName());
					afterPosting(tollPost, postResponseDTO, etcTransaction, ibtsClientQueue, gsonNew, failureQueueClient);
				} catch (Exception e) {
					status.setRollbackOnly();
					logger.error("Txns rolled back for laneTxId {}, exception: {}",tollPost.getLaneTxId(), e.getMessage());
					
					logger.error("Exception caught during after-posting txn: {}",tollPost);
					logger.error("Exception message for {}: {}",tollPost.getLaneTxId(), e.getMessage());
					FailureMessageDto failureMessageDto = new FailureMessageDto();
					failureMessageDto.setLaneTxId(tollPost.getLaneTxId());
					ArrayList<Object> objlist = new ArrayList<Object>();
					objlist.add(etcTransaction);
					objlist.add(tollPost);
					objlist.add(postResponseDTO);
					failureMessageDto.setPayload(objlist);
					failureMessageDto.setKey(String.valueOf(tollPost.getLaneTxId()));
					failureMessageDto.setKeyType(Constants.LANE_TX_ID);
					StackTraceElement[] elements = e.getStackTrace();
					failureMessageDto.setFailureMsg(elements[0].toString());
					failureMessageDto.setFailureTimestamp(OffsetDateTime.now());
					failureMessageDto.setOriginQueueName("ATP");

					if (e.getStackTrace().toString().contains("afterPosting") || postResponseDTO != null) {
						failureMessageDto.setFailureState(Constants.AFTERPOSTING);
					} else {
						failureMessageDto.setFailureState(Constants.TOLLPOSTING);
					}

					if (e instanceof RuntimeException) {
						if (e instanceof ServiceUnavailable || e instanceof InternalServerError
								|| e instanceof RestClientException) {
							failureMessageDto.setFailureType("ClientServiceException");
							failureMessageDto.setFailureSubtype(Constants.RECOVERABLE);
							failureMessageDto.setErrorCode(500);
						} else {
							failureMessageDto.setFailureType("RuntimeException");
							failureMessageDto.setFailureSubtype(Constants.NONRECOVERABLE);
						}
					}
					failureHandlerService.persistFailureEvent(failureMessageDto, failureQueueClient, gsonNew);
				
				}
			}
			});
		
	}
	
	public void afterPosting(AccountTollPostDTO tollPost, TollPostResponseDTO postResponseDTO,
			EtcTransaction etcTransaction, OssStreamClient ibtsClientQueue, Gson gsonNew,
			OssStreamClient failureQueueClient) throws Exception 
	{
		TollException tollException = new TollException();
		logger.info("Setting TX_STATUS for tx_type I,P,C");
		tollCalculation.exceptionValidation(tollPost, postResponseDTO, tollException);
		
		
		LocalDateTime afterPS = LocalDateTime.now();
		/** Update T_TRANS_TABLE **/
		TranDetail tranDetail = TollPostResponseDTO.getTranDetail(postResponseDTO);
		tranDetail.setPostedFareAmount(tollPost.getPostedFareAmount());
		tranDetail.setFullFareAmount(etcTransaction.getFullFareAmount());
		tranDetail.setPlanType(tollPost.getPlanTypeId());
		tranDetail.setEtcAccountId(tollPost.getEtcAccountId());
		tranDetail.setExtFileId(etcTransaction.getExternFileId());
		tranDetail.setTxType(etcTransaction.getTxTypeInd());
		tranDetail.setAccountType(etcTransaction.getAccountType());
		//tranDetail.setAccAgencyId(etcTransaction.getAccountAgencyId());	
		tranDetail.setAccAgencyId(tollPost.getAccountAgencyId());			//added this due to bug 26900
		tranDetail.setPlateState(etcTransaction.getPlateState());
		tranDetail.setPlateNumber(etcTransaction.getPlateNumber());
		tranDetail.setAtpFileId(Integer.valueOf(etcTransaction.getAtpFileId()));

		LocalDateTime from = LocalDateTime.now();
		tranDetailService.updateTranDetail(tranDetail);
		logger.debug("##SQL Time Taken for {} HOSTNAME: {} in update TranDetail: {} ms",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
		logger.info("updated T_TRAN_DETAIL for lane_tx_id {} ",tollPost.getLaneTxId());

		
		if (postResponseDTO.getTxStatus() > 25) {
			logger.info("Preparing TollException beacause txStatus is greater than 25");
			BeanUtils.copyProperties(postResponseDTO, tollException);
			tollException
					.setPostedDate(DateUtils.parseLocalDate(postResponseDTO.getPostedDate(), Constants.dateFormator));
			tollException.setTxDate(tollPost.getTxDate());
			tollException.setTxTimeStamp(tollPost.getTxTimestamp().toLocalDateTime());
			tollException.setRevenueDate(tollPost.getRevenueDate());
			tollException.setDepositId(postResponseDTO.getDepositId());
//			logger.info("excuting exception logic for {} ", tollException);
//			tollCalculation.exceptionValidation(tollPost, postResponseDTO, tollException);
			
			logger.info("Inserting into T_TOLL_EXCEPTION {} ", tollException);
			LocalDateTime fromTime3 = LocalDateTime.now();
			tollExceptionService.saveTollException(tollException);
			logger.info("Inserted record in T_TOLL_EXCEPTION Table for lane_tx_id {}",tollException.getLaneTxId());
			
			logger.debug("##SQL Time Taken for {} HOSTNAME: {} in insert exception in T_TOLL_EXCEPTION: {} ms",
					Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime3, LocalDateTime.now()));
		} 
		else if (postResponseDTO.getTxStatus() < 25) {
			AccountPlan plan = tollPost.getAccountInfoDTO().getSelectedPlan();
			if (plan != null && plan.getCommuteFlag() != null) //&& plan.getCommuteFlag().equals("T"))
			{
				logger.info("{} : Selected plan {} for laneTxId {} is commuter excuting cummuter logic",Thread.currentThread().getName(),
						 tollPost.getPlanTypeId(), tollPost.getLaneTxId());
				//Time for locking
				semaphore.acquire();
				logger.info("Semaphore lock acquired by thread: {}",Thread.currentThread().getName());
				commuterPlanService.CommuterPlanActions(tollPost.getAccountInfoDTO().getSelectedPlanPolicy(),
							tollPost.getAccountInfoDTO().getSelectedPlan(), tollPost);
				
				semaphore.release();
				logger.info("Semaphore lock release by thread: {}",Thread.currentThread().getName());
			}
		}

		/** **/
		if (tollPost.getTxTypeInd().equals("I")) {
			logger.info("txType is I for laneTxId {} inserting data in IcrxRecon", tollPost.getLaneTxId());
			IcrxRecon icrxRecon = new IcrxRecon();
			icrxRecon.setAccountAgenyId(tollPost.getAccountAgencyId());
			icrxRecon.setExternFileId(tollPost.getExternFileId());
			icrxRecon.setLaneTxId(tollPost.getLaneTxId());
			icrxRecon.setPlazaAgencyId(tollPost.getPlazaAgencyId());
			icrxRecon.setPostedDate(tollPost.getPostedDate());
			icrxRecon.setTxExternRefNo(tollPost.getTxExternRefNo());
			icrxRecon.setTxModSeq(tollPost.getTxModSeq());
			icrxRecon.setTxStatus(TxStatus.getByCode(postResponseDTO.getTxStatus()));
			LocalDateTime fromTime6 = LocalDateTime.now();
			icrxReconService.saveIcrxRecon(icrxRecon);
			logger.info("Inserted records in T_ICRX_RECON table for lane_tx_id {}",icrxRecon.getLaneTxId());
			logger.debug("##SQL Time Taken for {} HOSTNAME: {} in insert icrxReconService record: {} ms",
					Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime6, LocalDateTime.now()));
		}

		/** Check for residential plan **/
		logger.info("Checking residential plan for laneTxId {} ", tollPost.getLaneTxId());
		LocalDateTime fromTime6 = LocalDateTime.now();
		Long sysAccount = tollCalculation.systemAccountSelection(tollPost);
		logger.debug("##SQL Time Taken for {} HOSTNAME: {} in systemAccount selection: {} ms",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(fromTime6, LocalDateTime.now()));
		
		if (sysAccount != null) {
			logger.info("Got system account {} for laneTxId {} ", sysAccount, tollPost.getLaneTxId());
			LocalDateTime fromTime7 = LocalDateTime.now();
			FpmsAccount fpmsAccount = fpmsAccountService.getFpmsAccount(sysAccount);
			logger.debug("##SQL Time Taken for {} HOSTNAME: {} in getting account balance from FPMS.T_FPMS_ACCOUNT: {} ms",
					Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime7, LocalDateTime.now()));
			logger.info("Fpms account info {} recieved for laneTxId {} ", fpmsAccount, tollPost.getLaneTxId());
			
			if (fpmsAccount != null && fpmsAccount.getCurrentBalance() > tollPost.getPostedFareAmount()) 
			{
				AccountTollPostDTO tollPost1 = new AccountTollPostDTO();
				BeanUtils.copyProperties(tollPost, tollPost1);
				tollPost1.setExpectedRevenueAmount(tollPost1.getExpectedRevenueAmount() * -1);
				tollPost1.setPostedFareAmount(tollPost1.getPostedFareAmount() * -1);
				// :TODO postObj1.pre_txn_balance = postObj1.pre_txn_balance -
				// postObj1.posted_fare_amount
				// tollPost1.setTxStatus(TxStatus.TX_STATUS_NTOL.getCode());
				tollPost1.setTxModSeq(tollPost1.getTxTypeInd().equals("X") ? 5 : 2);

				logger.info("posting FPMS response1 to ibtsClientQueue..");
				logger.info("posting FPMS Residential 1 {} for laneTxId {}", gsonNew.toJson(tollPost1),
						tollPost.getLaneTxId());
				postResponseDTO = tollPosting(gsonNew.toJson(tollPost1), null, null, ibtsClientQueue, gsonNew,
						failureQueueClient);
				logger.info("got response Residential 1 TollPostResponseDTO {} for laneTxId {}", postResponseDTO,
						tollPost.getLaneTxId());

				
				AccountTollPostDTO tollPost2 = new AccountTollPostDTO();
				BeanUtils.copyProperties(tollPost, tollPost2);
				tollPost2.setEtcAccountId(sysAccount);
				tollPost2.setTxModSeq(tollPost1.getTxTypeInd().equals("X") ? 4 : 1);

				logger.info("posting FPMS response2 to ibtsClientQueue {}");
				logger.info("posting FPMS Residential 1 {} for laneTxId {}", gsonNew.toJson(tollPost2),
						tollPost.getLaneTxId());
				postResponseDTO = tollPosting(gsonNew.toJson(tollPost2), null, null, ibtsClientQueue, gsonNew,
						failureQueueClient);
				logger.info("got response Residential 2 TollPostResponseDTO {} for laneTxId {}", postResponseDTO,
						tollPost.getLaneTxId());
			} else {
				logger.info("No account balance found for residential plan & system account found for laneTxId {} ",
						tollPost.getLaneTxId());
			}
		} else {
			logger.info("No residential plan & sysytem account found for laneTxId {} ", tollPost.getLaneTxId());
		}
		
		/** Update EZ on ATPP and R* on ATPR in q_statistics **/
		int resTxStatus = Integer.valueOf(postResponseDTO.getTxStatus());
		if (resTxStatus>=26 && resTxStatus <= 69 && tollPost.getTxTypeInd().equals("E") && tollPost.getTxSubtypeInd().equals("Z"))		
		{		//changed due to bug 21793
//		if (resTxStatus == TxStatus.TX_STATUS_DUPL.getCode() || resTxStatus == TxStatus.TX_STATUS_XLANE.getCode() ||  resTxStatus == TxStatus.TX_STATUS_POACHING.getCode() 
//				|| resTxStatus == TxStatus.TX_STATUS_INVACCLSP.getCode() || resTxStatus == TxStatus.TX_STATUS_INVACRVKF.getCode() || resTxStatus == TxStatus.TX_STATUS_INVACCLOS.getCode()){
//			
			logger.info("Updating data in t_qatp_statistics for laneTxId {}", tollPost.getLaneTxId());
			LocalDateTime fromTime = LocalDateTime.now();
			List<QatpStatistics> qStatsList = qatpStatisticsDao.getStatsRecords(tollPost.getExternFileId());
			logger.debug("##SQL Time Taken in getting statistics {} HOSTNAME: {}: {} ms",	Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		
			
			fromTime = LocalDateTime.now();
			qatpStatisticsDao.updateStatisticsRecord(qStatsList);
			logger.debug("##SQL Time Taken in update statistics {} HOSTNAME: {}: {} ms",	Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		
			logger.info("Updated ATPP and ATPR record count for ExternFileId {}",tollPost.getExternFileId());
			
			fromTime = LocalDateTime.now();
			qatpStatisticsDao.updateTranDetailRecord(qStatsList,tollPost.getLaneTxId());
			
			logger.debug("##SQL Time Taken in update TranDetailRecord after statistics {} HOSTNAME: {}: {} ms",	Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
			
			logger.info("Updated ATP_FILE_ID in t_tran_detail for laneTxId {}",tollPost.getLaneTxId());
			
		}
		LocalDateTime fromTime7 = LocalDateTime.now();
		logger.debug("##SQL Time Taken for {} HOSTNAME: {} in getFpmsAccount: {} ms",
				Thread.currentThread().getName(), podName,
				ChronoUnit.MILLIS.between(fromTime7, LocalDateTime.now()));
		
		/** Send response to IBTS service **/
		if (etcTransaction.getSourceSystem().intValue() == 1) {
			logger.info("Sending result to IBTS {} ", postResponseDTO);
			IBTSDataDto ibtsData = new IBTSDataDto();
			BeanUtils.copyProperties(postResponseDTO, ibtsData);
			ibtsData.setTollRevenueType(tollPost.getTollRevenueType());
			ibtsData.setPlanTypeId(tollPost.getPlanTypeId());
			ibtsData.setReconDate(tollPost.getReconDate());
			ibtsData.setExpectedRevenueAmount(tollPost.getExpectedRevenueAmount());
			ibtsData.setPostedFareAmount(tollPost.getPostedFareAmount());
			logger.info("IBTSDataDto : {}", gsonNew.toJson(ibtsData));
			LocalDateTime fromTime5 = LocalDateTime.now();
			ibtsClientQueue.publishMessage(String.valueOf(ibtsData.getLaneTxId()), gsonNew.toJson(ibtsData).getBytes());
			logger.debug("##OCI Time Taken for {} HOSTNAME: {} in publishMessage to ibtsQueueClient: {} ms",
					Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime5, LocalDateTime.now()));
			logger.info("Message published to {} with laneTxId {}",ibtsClientQueue, ibtsData.getLaneTxId());
			logger.info("Message: {}",ibtsData);
			
		}
		
		logger.debug("##SQL all {} HOSTNAME: {} in After posting completion time: {} ms",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(afterPS, LocalDateTime.now()));
	}

	
	/**
	 * 
	 * @param etcTransaction
	 * @param tollPost
	 * @param failureQueueClient
	 * @param gsonNew
	 * @throws PrePostingLevelExeption 
	 */
	public void prePostinglogic(EtcTransaction etcTransaction, AccountTollPostDTO tollPost,
			OssStreamClient failureQueueClient, Gson gsonNew) throws PrePostingLevelExeption {
		try {
			logger.info("Start processing for {}", etcTransaction.getLaneId());
			//EtcTransaction etc = new EtcTransaction();
			//BeanUtils.copyProperties(etcTransaction, etc);
			if (!etcTransaction.validateRejectionTransaction(etcTransaction,masterCache)) {
				AccountTollPostDTO.init(etcTransaction, tollPost);
				Util.setRevenueDate(tollPost, masterCache);
				logger.info("EtcTransaction validation failed for laneTxId {} calling exception TxStatus {}",
						tollPost.getLaneTxId(), tollPost.getTxStatus());
			} else {
				AccountTollPostDTO.init(etcTransaction, tollPost);
				Util.setRevenueDate(tollPost, masterCache);

				validateTxnUtil.checkInvalidPlaza(tollPost);
			
				validateTxnUtil.checkInvalidTag(tollPost);

				validateTxnUtil.checkInvalidAccount(tollPost);

				/** Get device information from T_DEVICE **/
				LocalDateTime from = LocalDateTime.now();
				
				
				Device device = deviceService.getDeviceByDeviceNo(etcTransaction.getDeviceNo(),
						tollPost.getTxTimestamp().toLocalDateTime());
				logger.debug("##SQL Time Taken for {} in getDeviceByDeviceNo: {} ms",
						Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));

				/** If not found get device information from T_H_DEVICE **/
				if (device == null) {
					LocalDateTime fromtime = LocalDateTime.now();
					device = deviceService.getHDeviceByDeviceNo(etcTransaction.getDeviceNo(),
							tollPost.getTxTimestamp().toLocalDateTime());
					logger.debug("##SQL Time Taken for {} HOSTNAME: {} in getHDeviceByDeviceNo: {} ms",
							Thread.currentThread().getName(), ChronoUnit.MILLIS.between(fromtime, LocalDateTime.now()));
				}
				if (device == null) {
					if (!tollPost.getTxTypeInd().equals("V") && !tollPost.getTxTypeInd().equals("I")) {
						logger.info("Device not found for laneTxId {} calling exception TxStatus {}",
								tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVTAG);
						tollPost.setTxStatus(TxStatus.TX_STATUS_INVTAG.getCode());
						return;
					} else if (tollPost.getTxTypeInd().equals("V")
							&& !Util.isValidPlateNumber(tollPost.getPlateNumber())) {
						logger.info(
								"Type is V & device not found & plate number is not valid for laneTxId {} calling exception TxStatus {}",
								tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVTAG);
						tollPost.setTxStatus(TxStatus.TX_STATUS_INVTAG.getCode());
						return;
					} else if (tollPost.getTxTypeInd().equals("V")) {
						logger.info("Type is V Device not found for laneTxId {} but plateNumber is valid.. ",
								tollPost.getLaneTxId());
					}else if(tollPost.getTxTypeInd().equals("I"))		//added this condition for bug 26481
					{														
						logger.info("Device for I type of trnx not found for laneTxId {}", tollPost.getLaneTxId());
						tollPost.setTxStatus(TxStatus.TX_STATUS_RINV.getCode());
						return;
					} else {
						logger.info("Device not found for laneTxId {}", tollPost.getLaneTxId());
						return;
					}
				} else {
					logger.info("Got device info for laneTxId {} Device {} ", tollPost.getLaneTxId(), device);
				}
				TxStatus txStatus = device != null
						? device.validateDeviceStatus(tollPost.getTxTimestamp().toLocalDateTime())
						: null;
				if (txStatus != null) {
					if (tollPost.getTxTypeInd().equals("V") && Util.isValidPlateNumber(tollPost.getPlateNumber())) {
						logger.info("TxType V Device status is not valid for laneTxId {} but plateNumber is valid.. ",
								tollPost.getLaneTxId());
					} else if (tollPost.getTxTypeInd().equals("V")
							&& !Util.isValidPlateNumber(tollPost.getPlateNumber())) {
						logger.info(
								"TxType V Device status is not valid for laneTxId {} & plateNumber is also not valid so reject with TxStatus {}",
								tollPost.getLaneTxId(), txStatus.getName());
						tollPost.setTxStatus(txStatus.getCode());			//added this condition for bug 29447
						return;
					} else {
						logger.info("Device status is not valid for laneTxId {} calling exception TxStatus {} ",
								tollPost.getLaneTxId(), txStatus.getName());
						tollPost.setTxStatus(txStatus.getCode());
						return;
					}
				}
				
				/*********************************Get Account Info*********************************************/
				LocalDateTime fromtime10 = LocalDateTime.now();
				AccountInfoDTO accountInfo = accountInfoService.getAccountInfo(tollPost.getEtcAccountId(),
						tollPost.getAccountType(),tollPost.getTxDate());
				logger.debug("##SQL Time Taken for {} in get accountInfo: {} ms", Thread.currentThread().getName(),
						ChronoUnit.MILLIS.between(fromtime10, LocalDateTime.now()));
				
				if (accountInfo == null) {
					logger.info("Account not found for laneTxId {} calling exception TxStatus {}",
							tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVACC);
					tollPost.setTxStatus(TxStatus.TX_STATUS_INVACC.getCode());
					return;
				} else {
					logger.info("Got acount info for laneTxId {} account {}", tollPost.getLaneTxId(), accountInfo);
					tollPost.setAccountType(accountInfo.getAccountType());
					tollPost.setAccountAgencyId(accountInfo.getAgencyId());
					tollPost.setAccountInfoDTO(accountInfo);
				}
				txStatus = AccountInfoDTO.checkInvalidTxStatus(accountInfo);
				if (txStatus != null) {
					logger.info("Account is not valid for laneTxId {} calling exception TxStatus {}",
							tollPost.getLaneTxId(), txStatus.getName());
					tollPost.setTxStatus(txStatus.getCode());
					return;
				}
				logger.info("checking txStatus for laneTxId {}", tollPost.getLaneTxId());
				tollPost.setTxStatus(accountInfo.getAcctFinStatus(),
						accountInfo.getRebillPayType() != null ? Integer.parseInt(accountInfo.getRebillPayType())
								: null);
				logger.info("txStatus {} for laneTxId {}",tollPost.getTxStatus(), tollPost.getLaneTxId());
				if (!(tollPost.getTxTypeInd().equals("I") && tollPost.getTxSubtypeInd().equals("A"))
						&& !(tollPost.getTxTypeInd().equals("I") && tollPost.getTxSubtypeInd().equals("R"))
						&& !(tollPost.getTxTypeInd().equals("O") && tollPost.getTxSubtypeInd().equals("A"))
						&& !(tollPost.getTxTypeInd().equals("O") && tollPost.getTxSubtypeInd().equals("R"))
						&& !(tollPost.getTxTypeInd().equals("X") && tollPost.getTxSubtypeInd().equals("A"))
						&& !(tollPost.getTxTypeInd().equals("X") && tollPost.getTxSubtypeInd().equals("R"))) {
					logger.info("Checking beyond condition for laneTxId {} plazaAgencyId {} TxStatus {}",
							tollPost.getLaneTxId(), tollPost.getPlazaAgencyId(), tollPost.getTxStatus());
					LocalDate postDate = LocalDate.now();
					Long txAge = ChronoUnit.DAYS.between(postDate, tollPost.getTxDate());
					logger.info("Transaction age for laneTxId {} is {}", tollPost.getLaneTxId(), txAge);
					TollPostLimit tollPostLimit = masterCache.getTollPostLimit(tollPost.getPlazaAgencyId(),
							tollPost.getTxStatus());

					if ((tollPostLimit != null && (txAge * -1) > tollPostLimit.getAllowedDays()) || txAge > 0) 
					{
							logger.info("Transaction is Beyond for laneTxId {} calling exception TxStatus {}",
									tollPost.getLaneTxId(), TxStatus.TX_STATUS_BEYOND);
							tollPost.setTxStatus(TxStatus.TX_STATUS_BEYOND.getCode());
							return;
					}
				}
				
				//To reject 3 and 4 tag status from history 
				if (tollPost.getTxTypeInd().equals("I")
						|| accountInfo.getCurrentBalance().doubleValue() < Constants.minAccountBal.doubleValue()) {
					String deviceNo=null;
					if(tollPost.getDeviceNo().length()==11)
					{
						String deviceNo14 = tollPost.getDeviceNo().replaceFirst("0", "00");
						deviceNo = deviceNo14.substring(0, 4).concat("00").concat(deviceNo14.substring(4,12));
					}
					else
					{
						deviceNo = tollPost.getDeviceNo();
					}
					Device haDevice = deviceService.getHADeviceByDeviceNo(deviceNo);
					if (haDevice != null && (haDevice.getIagTagStatus() == 3L || haDevice.getIagTagStatus() == 4L)) // :
																													// TODO
					{
						tollPost.setTxStatus(TxStatus.TX_STATUS_INSU.getCode());
						return;
					}
				}

				//reject if toll_amount exceeds amount of process parameter
				if(etcTransaction.getTollAmount()!=null && etcTransaction.getTollAmount() > masterCache.getTollAmountValue())
				{
					logger.info("Toll Amount is greater than threshold toll amount value");
					tollPost.setTxStatus(TxStatus.TX_STATUS_RJTA.getCode());
					return;
				}
				
				/** Get active plan list **/
				List<AccountPlan> planList = accountPlanService
						.getAccountPlanByEtcAccountId(accountInfo.getEtcAccountId(), tollPost.getTxDate());
				accountInfo.setAccountPlanList(planList);
				if (planList != null && !planList.isEmpty()) {
					logger.info("Got {} accountPlans for laneTxId {} and etcAccountId {}", planList.size(),
							tollPost.getLaneTxId(), accountInfo.getEtcAccountId());
				} else {
					logger.info("No Account Plan list found for laneTxId {} and etcAccountId {}", tollPost.getLaneTxId(),
							accountInfo.getEtcAccountId());
				}
				if (tollPost.getTxTypeInd().equals("I") && tollPost.getTxSubtypeInd().equals("A")) {
					String todId = todIdGeneratorUtil.todId();
					logger.info("todId generated: {}", todId);
					tollPost.setTodId(todId);
					tollPost.setCorrectionAmount(etcTransaction.getEtcFareAmount());
					if(tollPost.getDebitCredit() != null && tollPost.getDebitCredit().equalsIgnoreCase("-")) {
						Double corrAmt = 0.00;
						//If creditDebit field is negative then corr amount is requested to be -ve by fpms
						if(tollPost.getCorrectionAmount() > 0) {
							corrAmt = tollPost.getCorrectionAmount()*-1;
							tollPost.setCorrectionAmount(corrAmt);
						}
					}
					tollPost.setAgencyId(etcTransaction.getPlazaAgencyId());
				}
				/**
				 * Bug 73396
				 * For txn IA, II and subtype Reversal no plan selection and toll calculation required
				**/
				if (tollPost.getTxTypeInd().equals("I") || tollPost.getTxSubtypeInd().equals("R")) {
					logger.info("No plan selection required for txType I and reversal. laneTxId {}.", tollPost.getLaneTxId());
					return;
				} 
				Lane lane = masterCache.getLane(tollPost.getPlazaId(), tollPost.getLaneId());
				Plaza plaza = masterCache.getPlaza(tollPost.getPlazaId());
				if (tollPost.getPlazaAgencyId().equals(Constants.PA_AGENCY_ID) && !tollPost.getTxTypeInd().equals("X")
						&& !tollPost.getTxSubtypeInd().equals("A")) {
					logger.info("Plaza Agency Id is PA for laneTxId {}. calling paPlanSelection logic",
							tollPost.getLaneTxId());
					planSelection.planSelectionPAAgency(etcTransaction, accountInfo, tollPost);
				}
				else if ((lane != null && lane.getCalculateTollAmount() != null	&& (lane.getCalculateTollAmount().equals("Y")||lane.getCalculateTollAmount().equals("T")))
						|| (lane.getCalculateTollAmount() == null && plaza != null && plaza.getCalculateTollAmount() != null
								&& (plaza.getCalculateTollAmount().equals("Y")||plaza.getCalculateTollAmount().equals("T")))) 
					{
					logger.info("Lane calculateToll flag is true for laneTxId {}. calling planSelection logic",
							tollPost.getLaneTxId());
					planSelection.planSelectionLogic(etcTransaction, accountInfo, tollPost);
				} else if (lane == null || lane.getCalculateTollAmount() == null
						|| !(lane.getCalculateTollAmount().equals("Y")||lane.getCalculateTollAmount().equals("T"))) {
					logger.info("Lane calculateToll flag is false for laneTxId {}", tollPost.getLaneTxId());

					if (etcTransaction.getSourceSystem() == 1) {
						if (tollPost.getAccountType().equals(AccountType.BUSINESS.getCode())
								|| tollPost.getAccountType().equals(AccountType.COMMERCIAL.getCode())) {
							tollPost.setPlanTypeId(PlanType.BUSINESS.getCode());
						} else if (tollPost.getAccountType().equals(AccountType.PRIVATE.getCode())
								|| tollPost.getAccountType().equals(AccountType.NONREGVIDEO.getCode())) {
							tollPost.setPlanTypeId(PlanType.STANDARD.getCode());
						} else if (tollPost.getAccountType().equals(AccountType.PVIDEOUNREG.getCode())
								|| tollPost.getAccountType().equals(AccountType.BVIDEOUNREG.getCode())) {
							tollPost.setPlanTypeId(PlanType.VIDEOUNREG.getCode());
						} else if (tollPost.getAccountType().intValue() == AccountType.STVA.getCode()) {
							tollPost.setPlanTypeId(PlanType.STVA.getCode()); // :TODO STVA_PLAN
						}
					}
				}
				AccountPlan plan = accountInfo.getSelectedPlan();
				if (plan != null && !tollPost.getPlazaAgencyId().equals(Constants.PA_AGENCY_ID)) {
					logger.info("Selected best plan for laneTxId {} is {}", tollPost.getLaneTxId(), plan);
					logger.info("Changing planId and postedFare amount for laneTxId {}", tollPost.getLaneTxId());
					tollPost.setPlanTypeId(plan.getPlanType());
					plan.setTripPerTrx(1);
					if (Constants.VIDEO_FARE_PLAN_LIST.contains(plan.getPlanType())) {
						if (tollPost.getActualExtraAxles() != null && tollPost.getActualExtraAxles() > 0) {
							tollPost.setPostedFareAmount(tollPost.getPostedFareAmount());			//calculation part is done in parsing
						} else {
							tollPost.setPostedFareAmount(plan.getFullFareAmount());
						}
					} else if (tollPost.getIsDiscountable().equals("T")) {
						if (tollPost.getActualExtraAxles() != null && tollPost.getActualExtraAxles() > 0) {
							tollPost.setPostedFareAmount(
									plan.getDiscountAmount() + (accountInfo.getSelectedPlan().getExtraAxleCharge()
											* tollPost.getActualExtraAxles()));
						} else {
							logger.info("Discountable true for laneTxId {} set discountAmount", tollPost.getLaneTxId());
							tollPost.setPostedFareAmount(plan.getDiscountAmount());
						}
					} else {
						logger.info("Discountable false for laneTxId {} set full fare amount", tollPost.getLaneTxId());
						tollPost.setPostedFareAmount(plan.getFullFareAmount());
					}
				} else if(plan == null)
					{
					logger.info("Selected best plan for laneTxId {} is NULL setting default plan {}",
							tollPost.getLaneTxId(), tollPost.getPlanTypeId());
					AccountPlan accountPlan = new AccountPlan();
					//set default plan Type
					planSelection.selectDefaultPlan(etcTransaction, null, accountInfo, tollPost);
					accountPlan.setPlanType(accountInfo.getSelectedPlanType());
//					accountPlan.setPlanType(accountInfo.getSelectedPlanType() != null ? accountInfo.getSelectedPlanType()
//									: tollPost.getPlanTypeId());
					PlanPolicy planPolicy = masterCache.getPlanPolicy(accountPlan.getPlanType());
					accountInfo.setSelectedPlan(accountPlan);
					accountInfo.setSelectedPlanPolicy(planPolicy);
					tollPost.setPlanTypeId(accountPlan.getPlanType());
					logger.info("Selected best plan for laneTxId {} setting planType: {}", tollPost.getLaneTxId(),
							accountPlan.getPlanType());
					logger.info("Selected best plan for laneTxId {} setting planPolicy: {}", tollPost.getLaneTxId(),
							planPolicy);
					accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T") ? "T" : "F");
					accountPlan.setTripPerTrx(1);
					accountPlan.setApdId("0");
					accountPlan.setDiscountAmount(tollPost.getPostedFareAmount());
					accountPlan.setFullFareAmount(etcTransaction.getFullFareAmount());
					accountPlan.setEtcAccountId(tollPost.getEtcAccountId());
					
					//setting posted fare for PA with V type Trnx
					AccountPlan selectedPlan = accountInfo.getSelectedPlan();
					if(selectedPlan!=null && tollPost.getPlazaAgencyId().equals(Constants.PA_AGENCY_ID) 
						&& tollPost.getTxTypeInd().equals("V"))
					{
						setPostedFareForPA(selectedPlan,etcTransaction,tollPost);
					}
				} 
				else if(plan!=null && tollPost.getPlazaAgencyId().equals(Constants.PA_AGENCY_ID) 
						&& tollPost.getTxTypeInd().equals("V"))
				{
					if(plan.getPlanType().equals(PlanType.PASI.getCode()))
					{
						tollPost.setPostedFareAmount(etcTransaction.getDiscountedAmount());
					}
					else if (plan.getPlanType().equals(PlanType.PAGREEN.getCode()))
					{
						tollPost.setPostedFareAmount(etcTransaction.getDiscountedAmount2());
					}
					else if(plan.getPlanType().equals(PlanType.STANDARD.getCode()) || plan.getPlanType().equals(PlanType.BUSINESS.getCode()))
					{
						tollPost.setPostedFareAmount(etcTransaction.getEtcFareAmount());
					}
					else if(plan.getPlanType().equals(PlanType.VIDEOUNREG.getCode()))
					{
						tollPost.setPostedFareAmount(etcTransaction.getVideoFareAmount());
						tollPost.setTxStatus(10);
						tollPost.setAccountAgencyId(60);
					}
				}
				
				//commented due to bug 21683
//				if (!tollPost.getTxTypeInd().equals("I") && !tollPost.getTxTypeInd().equals("P")) {
//					if (tollPost.getActualExtraAxles() > 0) {		//nysba condition removed for 17347
//						tollPost.setExpectedRevenueAmount(
//								tollPost.getExpectedRevenueAmount() + (tollPost.getActualExtraAxles()
//										* accountInfo.getSelectedPlan().getExtraAxleCharge()));
//						tollPost.setPostedFareAmount(tollPost.getPostedFareAmount() + (tollPost.getPostedFareAmount()
//								* accountInfo.getSelectedPlan().getExtraAxleCharge()));
//					}
//				}

			}
		} 
		catch (Exception e) {
			logger.error("Exception caught during pre-posting txn: {}",tollPost);
			logger.error("Exception message for {}: {}",tollPost.getLaneTxId(), e.getMessage());
			FailureMessageDto failureMessageDto = new FailureMessageDto();
			failureMessageDto.setLaneTxId(tollPost.getLaneTxId());
			ArrayList<Object> objlist = new ArrayList<Object>();
			objlist.add(etcTransaction);
			objlist.add(tollPost);
			failureMessageDto.setPayload(objlist);
			failureMessageDto.setKey(String.valueOf(tollPost.getLaneTxId()));
			failureMessageDto.setKeyType(Constants.LANE_TX_ID);
			StackTraceElement[] elements = e.getStackTrace();
			failureMessageDto.setFailureMsg(elements[0].toString());
			failureMessageDto.setFailureState(Constants.PREPOSTING);
			// failureMessageDto.setErrorCode();
			failureMessageDto.setFailureTimestamp(OffsetDateTime.now());
			failureMessageDto.setOriginQueueName("ATP");
			if (e instanceof RuntimeException) {
				if (e instanceof ServiceUnavailable || e instanceof InternalServerError || e instanceof HttpClientErrorException) {
					failureMessageDto.setFailureType("ClientServiceException");
					failureMessageDto.setFailureSubtype("RECOVERABLE");
					failureMessageDto.setErrorCode(500);
				} else {
					failureMessageDto.setFailureType("RuntimeException");
					failureMessageDto.setFailureSubtype("NONRECOVERABLE");
				}
			}
			failureHandlerService.persistFailureEvent(failureMessageDto, failureQueueClient, gsonNew);
			logger.error("Exception occurred in pre posting logic..");
			throw new PrePostingLevelExeption("Exception occurred in pre posting logic..");
		}
	}

	private void setPostedFareForPA(AccountPlan plan, EtcTransaction etcTransaction, AccountTollPostDTO tollPost) 
	{
		if(plan.getPlanType().equals(PlanType.PASI.getCode()))
		{
			tollPost.setPostedFareAmount(etcTransaction.getDiscountedAmount());
		}
		else if (plan.getPlanType().equals(PlanType.PAGREEN.getCode()))
		{
			tollPost.setPostedFareAmount(etcTransaction.getDiscountedAmount2());
		}
		else if(plan.getPlanType().equals(PlanType.STANDARD.getCode()) || plan.getPlanType().equals(PlanType.BUSINESS.getCode()))
		{
			tollPost.setPostedFareAmount(etcTransaction.getEtcFareAmount());
		}
		else if(plan.getPlanType().equals(PlanType.VIDEOUNREG.getCode()))
		{
			tollPost.setPostedFareAmount(etcTransaction.getVideoFareAmount());
			tollPost.setTxStatus(10);
			tollPost.setAccountAgencyId(60);
		}
	}

	private Double getMaxTollAmount(AccountTollPostDTO tollPostObj) {

		try {
			logger.debug("##SQL {} HOSTNAME: {} in AllSync.getMaxTollAmount..", Thread.currentThread().getName(),
					podName);
			MaxTollDataDto maxTollDataDto = new MaxTollDataDto();
			maxTollDataDto.setEtcAccountId(null);
			maxTollDataDto.setLaneTxId(tollPostObj.getLaneTxId());
			maxTollDataDto.setEntryPlazaId((tollPostObj.getEntryPlazaId() != null) && (tollPostObj.getEntryPlazaId() != 0) ? tollPostObj.getEntryPlazaId() : null);
			maxTollDataDto.setTollRevenueType(tollPostObj.getTollRevenueType());
			maxTollDataDto.setcLass(tollPostObj.getActualClass());
			maxTollDataDto.setPlanId(tollPostObj.getPlanTypeId());
			maxTollDataDto.setLaneId(tollPostObj.getLaneId());
			maxTollDataDto.setPlazaAgencyId(tollPostObj.getPlazaAgencyId());
			maxTollDataDto.setTxTimestamp(tollPostObj.getTxTimestamp().toString().replace("T", " "));
			maxTollDataDto.setPlazaId(tollPostObj.getPlazaId());
			maxTollDataDto.setDataSource("TPMS");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			logger.info("Setting data for maxToll: {}", maxTollDataDto);
			Gson gson1 = new Gson();
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(maxTollDataDto), headers);

			LocalDateTime from = LocalDateTime.now();
			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getMaxTollUri(), entity,
					String.class);
			logger.debug("##API Time Taken for {} HOSTNAME: {} in AllSync.getMaxTollAmount: {} ms",
					Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			logger.info("MaxToll result: {}", result);
			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got maxToll response for laneTxId {}. Response: {}", maxTollDataDto.getLaneTxId(),
						jsonObject);

				JsonElement element = jsonObject.get("maxTollAmount");
				if (!(element instanceof JsonNull)) {
					logger.info("Max toll amount fetched: {}",element.getAsDouble());
					return element.getAsDouble();
				} else {
					logger.info("Error: {}", jsonObject.get("message"));
				}
			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response {}", jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error: Exception {} in maxTollApi", e.getMessage());
		}
		return 0.0;

	}

	@Async
	public void processTollPosting(EtcTransaction etcTransaction, OssStreamClient ibtsQueueClient, Gson gson,
			OssStreamClient failureQueueClient) throws PrePostingLevelExeption {
		logger.debug("MDC logfile: ", MDC.get("logFileName"));
		AccountTollPostDTO tollPost = new AccountTollPostDTO();
		LocalDateTime fromT2 = LocalDateTime.now();

		prePostinglogic(etcTransaction, tollPost, failureQueueClient, gson);
		
		logger.debug("2##.Time Taken for {} HOSTNAME: {} in run.prePostinglogic: {} ms",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(fromT2, LocalDateTime.now()));

		if (tollPost.getTxStatus().intValue() > 25 && tollPost.getTxStatus().intValue() < 499) 
		{
			logger.info("TxStatus {} is in 26-499 range for laneTxId {} posting with system account",
					tollPost.getTxStatus(), tollPost.getLaneTxId());
			if(tollPost.getTxTypeInd().equals("I"))			//added condition due to bug 25994
			{
				long etcAccountId = masterCache.getSystemAccountICTX(etcTransaction.getPlazaAgencyId());
				tollPost.setEtcAccountId(etcAccountId / 10);
				tollPost.setAccountAgencyId(etcTransaction.getPlazaAgencyId());
			}
			else
			{
				tollPost.setEtcAccountId(masterCache.getSystemAccount(etcTransaction.getPlazaAgencyId()));
				tollPost.setAccountAgencyId(etcTransaction.getPlazaAgencyId());
			}
		}
		if ((tollPost.getTxTypeInd().equals("U") || tollPost.getTxTypeInd().equals("V"))
				&& tollPost.getTxSubtypeInd().equals("X") && tollPost.getTollSystemType().equals("X")) // isMedianFare=X
		{
			logger.info("TxType is wrong for laneTxId {} calling exception API with txStatus {}",
					etcTransaction.getLaneId(), TxStatus.TX_STATUS_UNM_DISCARD);
			etcTransaction.setFareType(Constants.MAX_FARE_IND);
			Double amount = getMaxTollAmount(tollPost);
			tollPost.setPostedFareAmount(amount);
		}
		
		//PA Truck Discount Plan
		List<Integer> actualClassValues = Arrays.asList(2,3,4,5,6);
		if(tollPost.getPlazaAgencyId()==Constants.PA_AGENCY_ID && tollPost.getPlanTypeId().intValue()==PlanType.BUSINESS.getCode() && actualClassValues.contains(tollPost.getActualClass().intValue())) {
			logger.info("Checking PA truck discount for BUSINESS plan for laneTxId {} plan_type {} ",tollPost.getLaneTxId(),tollPost.getPlanTypeId());
			tollPost.getAccountInfoDTO().getSelectedPlan().setPlanType(400);
			tollPost.setTollRevenueType(8);
			tollCalculation.tollScheduleApi(tollPost.getAccountInfoDTO(), tollPost, tollPost.getAccountInfoDTO().getSelectedPlan());
			if(tollPost.getAccountInfoDTO().getSelectedPlanType()==PlanType.BUSINESS.getCode()) {
				tollPost.setTollRevenueType(etcTransaction.getTollRevenueType());
			}
		}
		
		//MTA Truck Discount Plan
		List<Integer> actualClassValuesMTA = Arrays.asList(4,6,7,8,12,13,17,18,19,20);
		if(tollPost.getPlazaAgencyId()==Constants.MTA_AGENCY_ID && tollPost.getPlanTypeId().intValue()==PlanType.BUSINESS.getCode() && actualClassValuesMTA.contains(tollPost.getActualClass().intValue())) {
			logger.info("Checking MTA truck discount for BUSINESS plan for laneTxId {} plan_type {} ",tollPost.getLaneTxId(),tollPost.getPlanTypeId());
			tollPost.getAccountInfoDTO().getSelectedPlan().setPlanType(401);
			tollPost.setTollRevenueType(8);
			tollCalculation.tollScheduleApi(tollPost.getAccountInfoDTO(), tollPost, tollPost.getAccountInfoDTO().getSelectedPlan());
			if(tollPost.getAccountInfoDTO().getSelectedPlanType()==PlanType.BUSINESS.getCode()) {
				tollPost.setTollRevenueType(etcTransaction.getTollRevenueType());
			}
		}

		logger.info("{} Posting to FPMS {}", podName, gson.toJson(tollPost));
		LocalDateTime fromT3 = LocalDateTime.now();
		tollPosting(gson.toJson(tollPost), tollPost, etcTransaction, ibtsQueueClient, gson, failureQueueClient);
		logger.debug("3##.Time Taken for {} HOSTNAME: {} in run.tollPosting: {} ms",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(fromT3, LocalDateTime.now()));
		
		logger.debug("## AllAsync Time Taken for {} HOSTNAME: {} in allAsync.processTollPosting: {} ms :currentTimeStamp: {}",
				Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(fromT2, LocalDateTime.now()), LocalDateTime.now());
	
		logger.info("Tollposting completed for laneTxId: {}", etcTransaction.getLaneTxId());

	}

}
