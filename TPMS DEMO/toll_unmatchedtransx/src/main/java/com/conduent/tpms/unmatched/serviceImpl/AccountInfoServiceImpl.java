package com.conduent.tpms.unmatched.serviceImpl;


import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.AccountInfoDao;
import com.conduent.tpms.unmatched.dao.TCodeDao;
import com.conduent.tpms.unmatched.dao.TSpeedThresholdDao;
import com.conduent.tpms.unmatched.dto.AccountApiInfoDto;
import com.conduent.tpms.unmatched.dto.AccountInfoDto;
import com.conduent.tpms.unmatched.dto.CustomerInfoDto;
import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.model.AccountInfo;
import com.conduent.tpms.unmatched.model.Agency;
import com.conduent.tpms.unmatched.model.ConfigVariable;
import com.conduent.tpms.unmatched.model.DeviceAway;
import com.conduent.tpms.unmatched.model.DeviceStatus;
import com.conduent.tpms.unmatched.model.ProcessParameter;
import com.conduent.tpms.unmatched.model.SpeedThreshold;
import com.conduent.tpms.unmatched.model.TCode;
import com.conduent.tpms.unmatched.service.AccountInfoService;
import com.conduent.tpms.unmatched.service.SpeedProcessingService;
import com.conduent.tpms.unmatched.utility.UnmatchedBusinessRuleHelper;
import com.conduent.tpms.unmatched.utility.MasterDataCache;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Account Info Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

	/*
	 * @Autowired private TCodeDao tCodeDao;
	 * 
	 * @Autowired private TSpeedThresholdDao tSpeedThresholdDao;
	 * 
	 * @Autowired private AccountInfoDao accountInfoDao;
	 * 
	 * @Autowired private MasterDataCache masterDataCache;
	 * 
	 * @Autowired private ConfigVariable configVariable;
	 */
	@Autowired
	UnmatchedBusinessRuleHelper classificationBusinessRuleHelper;
	
	@Autowired
	private TSpeedThresholdDao tSpeedThresholdDao;

	@Autowired
	private TCodeDao tCodeDao;
	
	@Autowired
	private ConfigVariable configVariable;
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Autowired
	private MasterDataCache masterDataCache;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SpeedProcessingService speedProcessingService;


	/*
	 * @Autowired private SpeedProcessingService speedProcessingService;
	 * 
	 * @Autowired ClassificationBusinessRuleHelper classificationBusinessRuleHelper;
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

	/**
	 * Get Account Information
	 */
	public TransactionDetailDto getAccountInformation(TransactionDto tempTxDto) {
		if (tempTxDto == null)
			return null;
		if (tempTxDto.getDeviceNo() != null) {
			return getAcctByTag(tempTxDto);
		} else if (!StringUtils.isBlank(tempTxDto.getPlateNumber())
				&& !("**********".equalsIgnoreCase(tempTxDto.getPlateNumber()))) {
			return getAcctByPlate(tempTxDto);
		} 
		//code commented as per unmatched story requirement
		else {
			/*
			 * logger.
			 * info("Tx Type V and Tx Sub Type F based on invalid device and license plate for lane Tx Id:{}"
			 * , tempTxDto.getLaneTxId()); tempTxDto.setTxType(UnmatchedConstant.V);
			 * tempTxDto.setTxSubType(UnmatchedConstant.F);
			 * tempTxDto.setReciprocityTrx(UnmatchedConstant.F);
			 * 
			 * String aetFlag=tempTxDto.getAetFlag(); if(aetFlag!=null &&
			 * aetFlag.equals("Y")) {
			 * tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
			 * }else {
			 * tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION); }
			 */
			AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
			return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
		}
		
	}

	/**
	 * Get Account Information By Plate.
	 * 
	 * @param TransactionDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto getAcctByPlate(TransactionDto tempTxDto) {
		return null;
		// TODO Auto-generated method stub
	}

	/**
	 * Get Away Device Info
	 * 
	 * @param deviceNumber
	 * @param txTimeStamp
	 * @return DeviceAway
	 */
	private DeviceAway findDeviceAway(String deviceNumber, LocalDateTime txTimeStamp) {
		return accountInfoDao.findDeviceAway(deviceNumber, txTimeStamp);
	}

	/**
	 * Get Account Information By Device
	 * 
	 * @param tempTxDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto getAcctByTag(TransactionDto tempTxDto) {
		AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
		TransactionDetailDto transactionDetailDto = null;
		logger.info("Entered Get Account By Tag for laneTxId {}", tempTxDto.getLaneTxId());
		if (tempTxDto.getDeviceNo() != null) {
			Agency agency = null;
			agency = masterDataCache.getAgency(tempTxDto.getDeviceNo().substring(0, 3));
			logger.info("Agency: {}", agency);
			Long agencyId = agency != null ? agency.getAgencyId() : 0L;
			logger.info("Agency Id: {}", agencyId);

			// if invalidAgencyId
			if (agencyId == 0L) {
				// Invalid Agency
				//below code comment as per unmatched story requirement
				//transactionDetailDto = processInvalidAgencyTx(tempTxDto, tempAccountInfoDto);
				logger.info("Invalid Agency Id for laneTxId {}", tempTxDto.getLaneTxId());
			} else if (agencyId >= 0) {
				// Valid Agency
				transactionDetailDto = processValidAgencyTx(tempTxDto, tempAccountInfoDto, agency, agencyId);
			}
		}

		logger.info("TransactionDetailDto: {}", transactionDetailDto);
		return transactionDetailDto;
	}

	/**
	 * Process valid agency tx
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @param Agency
	 * @param Long
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processValidAgencyTx(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto,
			Agency agency, Long agencyId) {
		logger.info("Process valid Agency tx for laneTxId {}", tempTxDto.getLaneTxId());
		String homeIssuedDev = agency.getIsHomeAgency() != null ? agency.getIsHomeAgency() : null;
		if (!StringUtils.isBlank(homeIssuedDev) && UnmatchedConstant.Y.equalsIgnoreCase(homeIssuedDev)) { // changes constant name
			tempAccountInfoDto.setHomeIssuedDev(homeIssuedDev);
			return processHomeDevice(tempTxDto, tempAccountInfoDto, agencyId);
		} else {
			return processAwayAndViolationDevice(tempTxDto, agencyId, tempAccountInfoDto);
		}
	}

	/**
	 * Process Home Device Transaction
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @param Long
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processHomeDevice(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto,
			Long agencyId) {

		// HomeBusiness logic
		boolean awayViolationCheckFlag = false;
		tempTxDto.setReciprocityTrx(UnmatchedConstant.F);

		DeviceStatus deviceStatus = null;
		deviceStatus = getDeviceStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp() !=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		//deviceStatus = getDeviceStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp() !=null? Timestamp.valueOf(tempTxDto.getTxTimeStamp()):null);
		
		
		logger.info("Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());

		if (deviceStatus == null) {
			deviceStatus = getHDeviceStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
			logger.info("H Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());
		}

		if (deviceStatus != null) {
			//tempTxDto.setTxType(UnmatchedConstant.E);
			tempTxDto.setTxSubType(UnmatchedConstant.T);
			tempTxDto.setEtcAccountId(deviceStatus.getEtcAccountId());
			tempAccountInfoDto.setDeviceStatus(deviceStatus.getDeviceStatus());
			tempAccountInfoDto.setIsUnregistered(deviceStatus.getRetailTagStatus());
			tempAccountInfoDto.setIsPrevalid(deviceStatus.getIsPrevalidated());
			tempAccountInfoDto.setHomeAgencyDev(1);
			tempAccountInfoDto.setTagFound(1);
			tempAccountInfoDto.setStatus(UnmatchedConstant.Y);
		} else {
			awayViolationCheckFlag = true;
		}

		if (!awayViolationCheckFlag) {
			ProcessParameter tempProcessParameter = getProcessParameter(
					UnmatchedConstant.QATP_RETAIL_TAG_CHECK, agencyId);

			checkUnregisteredDevice(tempTxDto, tempAccountInfoDto, deviceStatus, tempProcessParameter);

			Long tempEtcAccountId = tempTxDto.getEtcAccountId() != null ? tempTxDto.getEtcAccountId() : 0L;
			if (tempEtcAccountId != 0) {
				logger.info("Etc Account Id exists for lane Tx Id:{}", tempTxDto.getLaneTxId());
				AccountInfo tempAccountInfo = getAccountInfo(tempEtcAccountId,tempTxDto.getTxDate());
				logger.info("AccountInfo Result:{} for lane Tx Id:{}", tempAccountInfo, tempTxDto.getLaneTxId());
				if (tempAccountInfo != null) {
					setAccountInfoParam(tempAccountInfoDto, tempAccountInfo, tempTxDto);
				//	getSpeedStatus(tempTxDto, tempAccountInfoDto);
				//	return processSpeedLimitForAccount(tempTxDto, tempAccountInfoDto);
				} else {
					awayViolationCheckFlag = true;
				}

			} else {
				awayViolationCheckFlag = true;
			}
		}

		if (awayViolationCheckFlag) {
			return processAwayAndViolationDevice(tempTxDto, agencyId, tempAccountInfoDto);
		}

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Set AccountInfo param
	 * 
	 * @param AccountInfoDto
	 * @param AccountInfo
	 * @param TransactionDto
	 */
	private void setAccountInfoParam(AccountInfoDto tempAccountInfoDto, AccountInfo tempAccountInfo,
			TransactionDto tempTxDto) {
		if (tempAccountInfo.getAgencyId() != null) {
			tempTxDto.setAccAgencyId(tempAccountInfo.getAgencyId());
		}
		if (tempAccountInfo.getAccountTotalBalance() != null) {
			tempAccountInfoDto.setAcctBalance(tempAccountInfo.getAccountTotalBalance());
		}
		if (tempAccountInfo.getCurrentBalance() != null) {
			tempAccountInfoDto.setCurrentBalance(tempAccountInfo.getCurrentBalance());
		}
		if (tempAccountInfo.getPostPaidBalance() != null) {
			tempAccountInfoDto.setPostPaidBalance(tempAccountInfo.getPostPaidBalance());
		}
		if (tempAccountInfo.getPostPaidStatus() != null) {
			tempAccountInfoDto.setPostPaidStatus(tempAccountInfo.getPostPaidStatus());
		}
		if (tempAccountInfo.getPrimaryRebillPayType() != null) {
			tempAccountInfoDto.setRebillType(tempAccountInfo.getPrimaryRebillPayType());
		}
		if (tempAccountInfo.getAcctActStatus() != null) {
			tempAccountInfoDto.setActStatus(tempAccountInfo.getAcctActStatus());
		}
		if (tempAccountInfo.getAcctFinStatus() != null) {
			tempAccountInfoDto.setFinStatus(tempAccountInfo.getAcctFinStatus());
		}
		if (tempAccountInfo.getAccountType() != null) {
			tempAccountInfoDto.setAcctType(tempAccountInfo.getAccountType());
			// Added below in demo for reflecting value in T_TRAN_DETAIL
			tempTxDto.setAccountType(tempAccountInfo.getAccountType());
		}
	}

	/**
	 * Get Speed Status
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 */
	private void getSpeedStatus(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		TCode tempTCode = null;
		if (UnmatchedConstant.PRIVATE.equals(tempAccountInfoDto.getAcctType())) {
			// Query Q6
			tempTCode = tCodeDao.getAccountSpeedStatus(tempTxDto.getEtcAccountId(), tempTxDto.getTxDate());
			logger.info("Account Speed Status Result:{} for lane Tx Id:{}", tempTCode, tempTxDto.getLaneTxId());
		} else {
			// Query Q7
			tempTCode = tCodeDao.getDeviceSpeedStatus(tempTxDto.getEtcAccountId(), tempTxDto.getDeviceNo(),
					tempTxDto.getTxDate());
			logger.info("Device Speed Status Result:{} for lane Tx Id:{}", tempTCode, tempTxDto.getLaneTxId());
		}

		if (tempTCode != null) {
			tempAccountInfoDto.setSpeedStatus(tempTCode.getCodeId());
		}
	}

	/**
	 * Check Unregistered Device
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @param DeviceStatus
	 * @param ProcessParameter
	 */
	private void checkUnregisteredDevice(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto,
			DeviceStatus deviceStatus, ProcessParameter tempProcessParameter) {
		if (tempProcessParameter != null
				&& UnmatchedConstant.Y.equalsIgnoreCase(tempProcessParameter.getParamValue())
				&& deviceStatus != null
				&& UnmatchedConstant.RET_TAG_UNREG.equals(deviceStatus.getRetailTagStatus())) {

			DeviceStatus tempHaDeviceStatus = getHATagStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxDate());
			logger.info("HA Device Status Result: {} for lane Tx Id:{}", tempHaDeviceStatus, tempTxDto.getLaneTxId());
			Long iagDeviceStatus = tempHaDeviceStatus != null ? tempHaDeviceStatus.getIagDeviceStatus() : null;
			if (iagDeviceStatus != null && (3 == iagDeviceStatus || 4 == iagDeviceStatus)) {
				tempAccountInfoDto.setIsUnregistered(UnmatchedConstant.RET_TAG_UNREG_1.intValue());
			}
		}
	}

	/**
	 * Speed Limit for Account
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processSpeedLimitForAccount(TransactionDto tempTxDto,
			AccountInfoDto tempAccountInfoDto) {
		// Query Q9
		SpeedThreshold speedThreshold = tSpeedThresholdDao.getSpeedLimitForLane(tempTxDto.getPlazaAgencyId(),
				tempTxDto.getLaneId(), tempTxDto.getAccountType());
		logger.info("Speed Threshold Result for Lane:{} for lane Tx Id:{}", speedThreshold, tempTxDto.getLaneTxId());

		if (speedThreshold != null && speedThreshold.getLowerSpeedThreshold() != null) {
			tempAccountInfoDto.setSpeedLimit(speedThreshold.getLowerSpeedThreshold());
		} else {
			// Query Q10
			Stopwatch stopwatch = Stopwatch.createStarted();
			speedThreshold = tSpeedThresholdDao.getSpeedLimitForAgency(tempTxDto.getPlazaAgencyId(),
					tempTxDto.getAccountType());
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Time taken to select data from TPMS.T_SPEED_THRESHOLD Shrikant ==> {}ms", millis);
			logger.info("Speed Threshold Result for Agency:{} for lane Tx Id:{}", speedThreshold,
					tempTxDto.getLaneTxId());
			if (speedThreshold != null && speedThreshold.getLowerSpeedThreshold() != null) {
				tempAccountInfoDto.setSpeedLimit(speedThreshold.getLowerSpeedThreshold());
			} else {
				tempAccountInfoDto.setSpeedLimit(UnmatchedConstant.DEFAULT_SPEED_LIMIT_THRESHOLD);
				logger.info("Set Speed Threshold to Defaul Value for lane Tx Id:{}", tempTxDto.getLaneTxId());
			}
		}
		return speedProcessingService.processSpeedBusinessRule(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Process Invalid Agency Transaction
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processInvalidAgencyTx(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		logger.info("Invalid Agency Id for laneTxId {}", tempTxDto.getLaneTxId());
		tempTxDto.setTxType(UnmatchedConstant.V);
		tempTxDto.setTxSubType(UnmatchedConstant.F);
		tempAccountInfoDto.setInvalidAgencyPfx(1);
		
		
		String aetFlag = tempTxDto.getAetFlag();
		if (aetFlag != null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
		} else {
			tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);
		}
		 
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Process Violation Transaction
	 * 
	 * @param TransactionDto
	 * @param agencyId
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processViolationTx(TransactionDto tempTxDto, Long agencyId,
			AccountInfoDto tempAccountInfoDto) {
		logger.info("Entered violation tx processing for laneTxId {}", tempTxDto.getLaneTxId());
		tempAccountInfoDto.setHomeAgencyDev(0);
		tempTxDto.setReciprocityTrx("F");
		tempTxDto.setTxType(UnmatchedConstant.V);
		tempTxDto.setTxSubType(UnmatchedConstant.F);
		tempAccountInfoDto.setTagFound(0);
		
		String aetFlag=tempTxDto.getAetFlag();
		if(aetFlag!=null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
		}else {
			tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
		}

		// Violation
		if (UnmatchedConstant.V.equalsIgnoreCase(tempTxDto.getTxType())) {
			tempTxDto.setTxSubType(UnmatchedConstant.F);
		}
		if (UnmatchedConstant.I.equalsIgnoreCase(tempTxDto.getTxType())
				|| UnmatchedConstant.V.equalsIgnoreCase(tempTxDto.getTxSubType())) {
			tempTxDto.setAccAgencyId(agencyId.intValue());
		}
		// Handle Violation flow here
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Process Away and Violation Transaction
	 * 
	 * @param TransactionDto
	 * @param agencyId
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processAwayAndViolationDevice(TransactionDto tempTxDto, Long agencyId,
			AccountInfoDto tempAccountInfoDto) {
		logger.info("Entered away tx processing for laneTxId {}", tempTxDto.getLaneTxId());
		// Away
		/**
		 * if iagTagStatus in (1,2) then set (O&T) else set (V&F)
		 */
		DeviceAway temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		logger.info("Device Away Result: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		if (temDeviceAway != null) {
			if (UnmatchedConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(temDeviceAway.getIagTagStatus())) {
				logger.info(
						"Tx Type O and Tx Sub Type T based on valid or low balance IAG status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(UnmatchedConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				tempTxDto.setTxType(UnmatchedConstant.O);
				tempTxDto.setTxSubType(UnmatchedConstant.T);
				//awayClassMismatchCheck(tempTxDto);
				
				//  comment below code as per Unmatched story requirement
			} /*
				 * else if (UnmatchedConstant.IAG_STATUS_FOR_INVALID_AND_ZERO
				 * 
				 * .contains(temDeviceAway.getIagTagStatus())) { logger.info(
				 * "Tx Type V and Tx Sub Type F based on invalid or zero IAG status validation  for lane Tx Id:{}"
				 * , tempTxDto.getLaneTxId()); tempTxDto.setReciprocityTrx(UnmatchedConstant.F);
				 * tempAccountInfoDto.setTagFound(1);
				 * tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				 * tempTxDto.setTxType(UnmatchedConstant.V);
				 * tempTxDto.setTxSubType(UnmatchedConstant.F);
				 * 
				 * String aetFlag=tempTxDto.getAetFlag(); if(aetFlag!=null &&
				 * aetFlag.equals("Y")) {
				 * tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
				 * }else {
				 * tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION); } }
				 */
			//commented below code as per Unmatched story requirement
		} /*
			 * else if (temDeviceAway == null) { logger.
			 * info("Tx Type V and Tx Sub Type F based on unavailibility of away Device Info  for lane Tx Id:{}"
			 * , tempTxDto.getLaneTxId()); //return processViolationTx(tempTxDto, agencyId,
			 * tempAccountInfoDto); }
			 */

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Away Tx class mismatch check
	 * 
	 * @param TransactionDto
	 */
	private void awayClassMismatchCheck(TransactionDto tempTxDto) {
		if (tempTxDto.getTransactionInfo() != null && tempTxDto.getTransactionInfo().length() > 3) {
			char[] txInfoArray = tempTxDto.getTransactionInfo().toCharArray();
			if (UnmatchedConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[0]))
					&& UnmatchedConstant.N.equalsIgnoreCase(String.valueOf(txInfoArray[1]))
					&& UnmatchedConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[2]))
					&& UnmatchedConstant.ZERO.equalsIgnoreCase(String.valueOf(txInfoArray[3]))) {
				if (classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(tempTxDto)) {
					tempTxDto.setTxType(UnmatchedConstant.V);
					tempTxDto.setTxSubType(UnmatchedConstant.G);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
					}
					logger.info(
							"Tx Type V and Tx Sub Type G based on away class Mismatch of valid veh and tag class  for lane Tx Id:{}",
							tempTxDto.getLaneTxId());
				} else {
					tempTxDto.setTxType(UnmatchedConstant.V);
					tempTxDto.setTxSubType(UnmatchedConstant.C);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
					}
					logger.info(
							"Tx Type V and Tx Sub Type C based on away class Mismatch of valid veh and tag class  for lane Tx Id:{}",
							tempTxDto.getLaneTxId());
				}
			}
		}
	}

	/**
	 * Call FPMS Account Info API
	 * 
	 * @param etcAccountId
	 * @return AccountApiInfoDto
	 */
	private AccountApiInfoDto callAccountApi(String etcAccountId) {
		try {
			CustomerInfoDto customerInfoDto = new CustomerInfoDto();
			customerInfoDto.setEtcAccountId(etcAccountId);
			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getAccountApiUri(),
					customerInfoDto, String.class);
			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				Gson gson = new Gson();
				return gson.fromJson(jsonObject.getAsJsonObject("result"), AccountApiInfoDto.class);
			}
		} catch (Exception e) {
			logger.error("Exception {} while get balance API call for etc account id :{}",ExceptionUtils.getStackTrace(e), etcAccountId);
		}
		return null;
	}

	/**
	 * Get H Device Status Info
	 * 
	 * @param deviceNumber
	 * @param txTimeStamp
	 * @return DeviceStatus
	 */
	private DeviceStatus getHDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp) {
		return accountInfoDao.getHDeviceStatus(deviceNumber, txTimeStamp);
	}

	/**
	 * Get HA Tag Status Info
	 * 
	 * @param deviceNumber
	 * @param txTimeStamp
	 * @return DeviceStatus
	 */
	private DeviceStatus getHATagStatus(String deviceNumber, String txDate) {
		return accountInfoDao.getHATagStatus(deviceNumber, txDate);
	}

	/**
	 * Get Account Info from DB and API call
	 * 
	 * @param etcAccountId
	 * @return AccountInfo
	 */
	private AccountInfo getAccountInfo(Long etcAccountId,String txDate) {
		AccountApiInfoDto tempAccountApiInfoDto = callAccountApi(etcAccountId.toString());
		AccountInfo tempAccountInfoDB = accountInfoDao.getAccountInfo(etcAccountId,txDate);
		if (tempAccountInfoDB != null && tempAccountApiInfoDto != null) {
			tempAccountInfoDB.setAccountTotalBalance(tempAccountApiInfoDto.getAccountBalance());
			tempAccountInfoDB.setPostPaidBalance(tempAccountApiInfoDto.getPostpaidBalance());
			tempAccountInfoDB.setCurrentBalance(tempAccountApiInfoDto.getPrepaidBalance());
			TCode tempTCode = null;
			if (tempAccountApiInfoDto.getAccountType() != null) {
				tempTCode = masterDataCache.getAccountTypeByCodeValue(tempAccountApiInfoDto.getAccountType());
				if (tempTCode != null) {
					tempAccountInfoDB.setAccountType(tempTCode.getCodeId());
				}
			}
			tempAccountInfoDB.setAgencyId(tempAccountApiInfoDto.getAgencyId());
			if (tempAccountApiInfoDto.getAcctFinStatus() != null) {
				tempTCode = masterDataCache.getFinStatusByCodeValue(tempAccountApiInfoDto.getAcctFinStatus());
				if (tempTCode != null) {
					tempAccountInfoDB.setAcctFinStatus(tempTCode.getCodeId());
				}
			}
			return tempAccountInfoDB;
		}
		return null;
	}

	/**
	 * Get Process Parameter Info
	 * 
	 * @param paramName
	 * @param agencyId
	 * @return ProcessParameter
	 */
	//commented as per Manu suggestion
	
	/*
	 * private ProcessParameter getProcessParameter(String paramName, Long agencyId)
	 * { return accountInfoDao.getProcessParameter(paramName, agencyId); }
	 */
	 

	// new code as per Manu suggestion to load data from master cache
	private ProcessParameter getProcessParameter(String paramName, Long agencyId) {
		return masterDataCache.getProcessParameter(paramName, agencyId);
	}
	/**
	 * Get Device Status Info
	 * 
	 * @param deviceNumber
	 * @param txTimeStamp
	 * @return DeviceStatus
	 */
	private DeviceStatus getDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp) {
		return accountInfoDao.getDeviceStatus(deviceNumber, txTimeStamp);
	}

	/**
	 * Companion Tag Check
	 * 
	 * @param TransactionDto
	 * @param agencyId
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto checkCompanionTag(TransactionDto tempTxDto,
			AccountInfoDto tempAccountInfoDto) {
		logger.info("Entered away tx processing of companion tag for laneTxId {}", tempTxDto.getLaneTxId());
		/**
		 * if iagTagStatus in (1,2) then set (O&T) else set (V&F)
		 */
		DeviceAway temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp().toLocalDateTime());
		logger.info("Device Away Result: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		if (temDeviceAway != null) {
			if (UnmatchedConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(temDeviceAway.getIagTagStatus())) {
				logger.info(
						"Tx Type O and Tx Sub Type T based on valid or low balance IAG status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(UnmatchedConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				tempTxDto.setTxType(UnmatchedConstant.O);
				tempTxDto.setTxSubType(UnmatchedConstant.T);
				//awayClassMismatchCheck(tempTxDto);
			} else if (UnmatchedConstant.IAG_STATUS_FOR_INVALID_AND_ZERO
					.contains(temDeviceAway.getIagTagStatus())) {
				logger.info(
						"Tx Type V and Tx Sub Type F based on invalid or zero IAG status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(UnmatchedConstant.F);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				tempTxDto.setTxType(UnmatchedConstant.V);
				tempTxDto.setTxSubType(UnmatchedConstant.F);
				
				String aetFlag=tempTxDto.getAetFlag();
				if(aetFlag!=null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
				}else {
					tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
				}
			}
		} 
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

}