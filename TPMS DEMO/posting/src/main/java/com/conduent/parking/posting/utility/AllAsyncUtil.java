package com.conduent.parking.posting.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.parking.posting.constant.ConfigVariable;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.constant.TxStatus;
import com.conduent.parking.posting.dto.AccountInfoDTO;
import com.conduent.parking.posting.dto.AccountTollPostDTO;
import com.conduent.parking.posting.dto.FailureMessageDto;
import com.conduent.parking.posting.dto.ParkingTransaction;
import com.conduent.parking.posting.dto.TollPostResponseDTO;
import com.conduent.parking.posting.model.Device;
import com.conduent.parking.posting.model.FpmsAccount;
import com.conduent.parking.posting.model.TollPostLimit;
import com.conduent.parking.posting.model.TranDetail;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.conduent.parking.posting.service.FailureHandlerService;
import com.conduent.parking.posting.service.IAccountInfoService;
import com.conduent.parking.posting.service.IDeviceService;
import com.conduent.parking.posting.service.IFpmsAccountService;
import com.conduent.parking.posting.service.ITollExceptionService;
import com.conduent.parking.posting.service.ITranDetailService;
import com.conduent.parking.posting.serviceimpl.TollCalculation;
import com.conduent.parking.posting.serviceimpl.TollException;
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
	private ITollExceptionService tollExceptionService;

	@Autowired
	private MasterCache masterCache;

	@Autowired
	private IAccountInfoService accountInfoService;

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private Map<String, String> myTollPostingContext;

	@Autowired
	private FailureHandlerService failureHandlerService;

	public String podName = null;
	
	private TransactionTemplate transactionTemplate;
	
	public AllAsyncUtil() {
		super();
	}

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@PostConstruct
	public void init() {
		podName = myTollPostingContext.get("podName") != null ? myTollPostingContext.get("podName") : "defaultPod";
		 Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null.");
		 this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TollPostResponseDTO tollPosting(AccountTollPostDTO tollPost,	ParkingTransaction etcTransaction, Gson gsonNew,
			OssStreamClient failureQueueClient) 
	{
		TollPostResponseDTO postResponseDTO = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(gsonNew.toJson(tollPost), headers);
			
			logger.info("Posting parking txn in FPMS for laneTxId {}.", tollPost.getLaneTxId());
			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollPostingUri(), entity, String.class);

			logger.info("After posting result: {}", result);
			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response for laneTxId {}. Response: {}", tollPost.getLaneTxId(), jsonObject);

				JsonElement element = jsonObject.get("result");
				if (!(element instanceof JsonNull)) {
					Gson gson = new Gson();
					postResponseDTO = gson.fromJson(jsonObject.getAsJsonObject("result"), TollPostResponseDTO.class);
					if (postResponseDTO != null && tollPost != null && etcTransaction != null) 
					{
						txnAfterPosting(tollPost, postResponseDTO, etcTransaction, gsonNew, failureQueueClient);
					}
				} else {
					logger.error("Etc account id {} does not exist.", tollPost.getEtcAccountId());
				}
			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response {}", jsonObject);
			}

		} catch (Exception e) {
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
			failureMessageDto.setOriginQueueName("PARKING");

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
			logger.info("Error: Exception {} while toll posting {}", e.getMessage(), tollPost);
		}
		return null;
	}

	public void tollPostingAgainSystem(AccountTollPostDTO tollPost, Gson gsonNew, OssStreamClient failureQueueClient) 
	{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(gsonNew.toJson(tollPost), headers);
			ResponseEntity<String> result;
			logger.info("Posting parking txn in FPMS for laneTxId {}.", tollPost.getLaneTxId());
			
			result = restTemplate.postForEntity(configVariable.getTollPostingUri(), entity, String.class);
			logger.info("After system account posting result: {}", result);
	}
	
	public void txnAfterPosting(AccountTollPostDTO tollPost, TollPostResponseDTO postResponseDTO,
			ParkingTransaction etcTransaction, Gson gsonNew,
			OssStreamClient failureQueueClient) {
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					afterPosting(tollPost, postResponseDTO, etcTransaction, gsonNew, failureQueueClient);
				} catch (Exception e) {
					logger.error("Exception caught in DB operations: {}",e.getMessage());
					status.setRollbackOnly();
					logger.error("******Rolled back the DB operations******");

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
					failureMessageDto.setFailureState(Constants.AFTERPOSTING);
					failureMessageDto.setFailureTimestamp(OffsetDateTime.now());
					failureMessageDto.setOriginQueueName("PARKING");
					if (e instanceof RuntimeException) {
						if (e instanceof ServiceUnavailable || e instanceof InternalServerError) {
							failureMessageDto.setFailureType("ClientServiceException");
							failureMessageDto.setFailureSubtype("RECOVERABLE");
							failureMessageDto.setErrorCode(500);
						} else {
							failureMessageDto.setFailureType("RuntimeException");
							failureMessageDto.setFailureSubtype("NONRECOVERABLE");
						}
					}
					failureHandlerService.persistFailureEvent(failureMessageDto, failureQueueClient, gsonNew);
					logger.error("******published {} in failure queue******", etcTransaction.getLaneTxId());
				
				}
			}
		});
		
	}
	
	public void afterPosting(AccountTollPostDTO tollPost, TollPostResponseDTO postResponseDTO,
			ParkingTransaction etcTransaction, Gson gsonNew,
			OssStreamClient failureQueueClient) throws Exception 
	{
		
		/** Update T_TRANS_TABLE **/
		TranDetail tranDetail = TollPostResponseDTO.getTranDetail(postResponseDTO);
		tranDetail.setPostedFareAmount(tollPost.getPostedFareAmount());
		tranDetail.setPlanType(tollPost.getPlanTypeId());
		tranDetail.setEtcAccountId(etcTransaction.getEtcAccountId());
		tranDetail.setExtFileId(etcTransaction.getExternFileId());
		tranDetail.setTxType(etcTransaction.getTxTypeInd());
		tranDetail.setAccountType(etcTransaction.getAccountType());
		tranDetail.setAccAgencyId(etcTransaction.getAccountAgencyId());
		tranDetail.setPlateState(etcTransaction.getPlateState());
		tranDetail.setPlateNumber(etcTransaction.getPlateNumber());

		tranDetailService.updateTranDetail(tranDetail);
		logger.info("update T_TRANS_DETAIL for {} success", tranDetail);

		if (postResponseDTO.getTxStatus() > 25) {
			logger.info("Preparing TollException beacause txStatus is greater than 25");
			TollException tollException = new TollException();
			BeanUtils.copyProperties(postResponseDTO, tollException);
			tollException.setPostedDate(DateUtils.parseLocalDate(postResponseDTO.getPostedDate(), Constants.dateFormator));
			tollException.setTxDate(tollPost.getTxDate());
			tollException.setTxTimeStamp(tollPost.getTxTimestamp().toLocalDateTime());
			tollException.setRevenueDate(tollPost.getRevenueDate());
			logger.info("excuting exception logic for {} ", tollException);
			LocalDateTime fromTime3 = LocalDateTime.now();
			tollCalculation.exceptionValidation(tollPost, postResponseDTO, tollException);
			logger.info("## Time Taken for thread {} HOSTNAME: {} in AllSync.exceptionValidation: {} ms",
					Thread.currentThread().getName(), podName,
					ChronoUnit.MILLIS.between(fromTime3, LocalDateTime.now()));
			logger.info("Inserting into T_TOLL_EXCEPTION {} ", tollException);
			tollExceptionService.saveTollException(tollException);
		} 

		/** Check for system account **/
		logger.info("Checking system account for laneTxId {} ", tollPost.getLaneTxId());
		Long sysAccount = tollCalculation.systemAccountSelection(tollPost);
		
		if (sysAccount != null) {
			logger.info("Got system account {} for laneTxId {} ", sysAccount, tollPost.getLaneTxId());
			FpmsAccount fpmsAccount = fpmsAccountService.getFpmsAccount(sysAccount);
			logger.info("Fpms account info {} recieved for laneTxId {} ", fpmsAccount, tollPost.getLaneTxId());
			
			if (fpmsAccount != null && (fpmsAccount.getCurrentBalance() > tollPost.getPostedFareAmount())) {
				AccountTollPostDTO tollPost1 = new AccountTollPostDTO();
				BeanUtils.copyProperties(tollPost, tollPost1);
				tollPost1.setExpectedRevenueAmount(tollPost1.getExpectedRevenueAmount() * -1);
				tollPost1.setPostedFareAmount(tollPost1.getPostedFareAmount() * -1);
				tollPost1.setTxModSeq(tollPost1.getTxTypeInd().equals("X") ? 5 : 2);

				logger.info("posting FPMS Residential 1 {} for laneTxId {}", gsonNew.toJson(tollPost1),
						tollPost.getLaneTxId());
				tollPostingAgainSystem(tollPost1, gsonNew,	failureQueueClient);
				
				logger.info("got response Residential 1 TollPostResponseDTO {} for laneTxId {}", postResponseDTO,
						tollPost.getLaneTxId());

				AccountTollPostDTO tollPost2 = new AccountTollPostDTO();
				BeanUtils.copyProperties(tollPost, tollPost2);
				tollPost2.setEtcAccountId(sysAccount);
				tollPost2.setTxModSeq(tollPost1.getTxTypeInd().equals("X") ? 4 : 1);

				logger.info("posting FPMS Residential 1 {} for laneTxId {}", gsonNew.toJson(tollPost2),
						tollPost.getLaneTxId());
				tollPostingAgainSystem(tollPost2, gsonNew,	failureQueueClient);
			
				logger.info("got response Residential 2 TollPostResponseDTO {} for laneTxId {}", postResponseDTO,
						tollPost.getLaneTxId());
			} else {
				logger.info("No account balance found for residential plan & sysytem account found for laneTxId {} ",
						tollPost.getLaneTxId());
			}
		} else {
			logger.info("No residential plan & sysytem account found for laneTxId {} ", tollPost.getLaneTxId());
		}
		
	}

	public void prePostinglogic(ParkingTransaction etcTransaction, AccountTollPostDTO tollPost,
			OssStreamClient failureQueueClient, Gson gsonNew) {
		try {
			logger.info("Start processing for {}", etcTransaction.getLaneId());
			
			if (!etcTransaction.validateEtcTransaction(etcTransaction, masterCache)) {
				AccountTollPostDTO.init(etcTransaction, tollPost);
				Util.setRevenueDate(tollPost, masterCache);
				logger.info("Parking transaction validation failed for laneTxId {} calling exception TxStatus {}",
						tollPost.getLaneTxId(), tollPost.getTxStatus());
			} else {
				AccountTollPostDTO.init(etcTransaction, tollPost);
				Util.setRevenueDate(tollPost, masterCache);

				if (tollPost.getPlazaId() == null || tollPost.getLaneId() == null
						|| tollPost.getPlazaId().intValue() == 0 || tollPost.getLaneId().intValue() == 0) {
					logger.info("Invalid plazaId & laneId for laneTxId {} calling exception TxStatus {}",
							tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVPLAZA);
					tollPost.setTxStatus(TxStatus.TX_STATUS_INVPLAZA.getCode());
					return;
				}

				/** Get device information from T_DEVICE **/
				Device device = deviceService.getDeviceByDeviceNo(etcTransaction.getDeviceNo(),
						tollPost.getTxTimestamp().toLocalDateTime());

				/** If not found get device information from T_H_DEVICE **/
				if (device == null) {
					device = deviceService.getHDeviceByDeviceNo(etcTransaction.getDeviceNo(),
							tollPost.getTxTimestamp().toLocalDateTime());
				}
				if (device == null) {
						logger.info("Device not found for laneTxId {}", tollPost.getLaneTxId());
						return;
				} 
				else {
					logger.info("Got device info for laneTxId {} Device {} ", tollPost.getLaneTxId(), device);
				}
				
				TxStatus txStatus = device != null
						? device.validateDeviceStatus(tollPost.getTxTimestamp().toLocalDateTime())
						: null;
				if (txStatus != null) {
						logger.info("Device status is not valid for laneTxId {} calling exception TxStatus {} ",
								tollPost.getLaneTxId(), txStatus.getName());
						tollPost.setTxStatus(txStatus.getCode());
						return;
				}
				AccountInfoDTO accountInfo = accountInfoService.getAccountInfo(tollPost.getEtcAccountId(),
						tollPost.getAccountType());
				if (accountInfo == null) {
					logger.info("Acount not found for laneTxId {} calling exception TxStatus {}",
							tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVACC);
					tollPost.setTxStatus(TxStatus.TX_STATUS_INVACC.getCode());
					return;
				} else {
					logger.info("Got acount info for laneTxId {} account {}", tollPost.getLaneTxId(), accountInfo);
					tollPost.setAccountType(accountInfo.getAccountType());
					tollPost.setAccountAgencyId(accountInfo.getAgencyId());
					tollPost.setAccountInfoDTO(accountInfo);
				}
				txStatus = AccountInfoDTO.validateStatus(accountInfo);
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

					if ((tollPostLimit != null && (txAge * -1) > tollPostLimit.getAllowedDays()) || txAge > 0) {
						logger.info("Transaction is Beyond for laneTxId {} calling exception TxStatus {}",
								tollPost.getLaneTxId(), TxStatus.TX_STATUS_BEYOND);
						tollPost.setTxStatus(TxStatus.TX_STATUS_BEYOND.getCode());
						return;
					}
				}
				if (tollPost.getTxTypeInd().contains("CP") && tollPost.getTxTypeInd().equals("Z")) {
					tollPost.setTxStatus(TxStatus.TX_STATUS_PSNT.getCode());
				}
			}
		} catch (Exception e) {
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
			failureMessageDto.setOriginQueueName("PARKING");
			if (e instanceof RuntimeException) {
				if (e instanceof ServiceUnavailable || e instanceof InternalServerError) {
					failureMessageDto.setFailureType("ClientServiceException");
					failureMessageDto.setFailureSubtype("RECOVERABLE");
					failureMessageDto.setErrorCode(500);
				} else {
					failureMessageDto.setFailureType("RuntimeException");
					failureMessageDto.setFailureSubtype("NONRECOVERABLE");
				}
			}
			failureHandlerService.persistFailureEvent(failureMessageDto, failureQueueClient, gsonNew);
			logger.error("Exception {}  while prePosting {}", e.getMessage(), etcTransaction);
		}
	}

	@Async
	public void processTollPosting(ParkingTransaction etcTransaction, Gson gson,
			OssStreamClient failureQueueClient) {
		
		AccountTollPostDTO tollPost = new AccountTollPostDTO();
		prePostinglogic(etcTransaction, tollPost, failureQueueClient, gson);

		if (tollPost.getTxStatus().intValue() > 25 && tollPost.getTxStatus().intValue() < 499) {
			logger.info("TxStatus {} is in 26-499 range for laneTxId {} posting with system accont",
					tollPost.getTxStatus(), tollPost.getLaneTxId());
		//	tollPost.setEtcAccountId(masterCache.getSystemAccount("REJ_SYSTEM_ACCOUNT"));
			tollPost.setEtcAccountId(masterCache.getSystemAccount(tollPost.getAccountAgencyId()));
			logger.info("SYSTEM_ACCOUNT ID set {}",tollPost.getEtcAccountId());
			tollPost.setAccountAgencyId(1);
		}

		logger.info("{} Posting to FPMS {}", podName, gson.toJson(tollPost));
		tollPosting(tollPost, etcTransaction, gson, failureQueueClient);
		
		logger.info("Tollposting completed for laneTxId: {}", etcTransaction.getLaneTxId());

	}

}
