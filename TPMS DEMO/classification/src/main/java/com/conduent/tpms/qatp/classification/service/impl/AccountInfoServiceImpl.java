package com.conduent.tpms.qatp.classification.service.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dao.TCodeDao;
import com.conduent.tpms.qatp.classification.dao.TSpeedThresholdDao;
import com.conduent.tpms.qatp.classification.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.CustomerInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
import com.conduent.tpms.qatp.classification.model.ProcessParameter;
import com.conduent.tpms.qatp.classification.model.SpeedThreshold;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.service.SpeedProcessingService;
import com.conduent.tpms.qatp.classification.utility.ClassificationBusinessRuleHelper;
import com.conduent.tpms.qatp.classification.utility.Convertor;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;
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

	@Autowired
	private TCodeDao tCodeDao;

	@Autowired
	private TSpeedThresholdDao tSpeedThresholdDao;

	@Autowired
	private AccountInfoDao accountInfoDao;

	@Autowired
	private MasterDataCache masterDataCache;

	@Autowired
	private ConfigVariable configVariable;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpeedProcessingService speedProcessingService;

	@Autowired
	ClassificationBusinessRuleHelper classificationBusinessRuleHelper;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

	/**
	 * Get Account Information
	 */
	public TransactionDetailDto getAccountInformation(TransactionDto tempTxDto) 
	{
		if (tempTxDto == null)
		{
			return null;
		}
		
		if (tempTxDto.getDeviceNo() != null) 
		{
			return getAcctByTag(tempTxDto);
		}
		else
		{
			return null;
		}
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
	private TransactionDetailDto getAcctByTag(TransactionDto tempTxDto) 
	{
		AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
		TransactionDetailDto transactionDetailDto = null;
		logger.info("Entered Get Account By Tag for laneTxId {}", tempTxDto.getLaneTxId());

		if (tempTxDto.getDeviceNo() != null) {
			Agency agency = null;
			agency = masterDataCache.getAgency(tempTxDto.getDeviceNo().substring(0, 3));
			logger.info("Agency: {}", agency);
			Long agencyId = agency != null ? agency.getAgencyId() : 0L;
			logger.info("Agency Id: {}", agencyId);
			
			transactionDetailDto = processValidAgencyTx(tempTxDto, tempAccountInfoDto, agency, agencyId);
			
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
			Agency agency, Long agencyId) 
	{
		logger.info("Process valid Agency tx for laneTxId {}", tempTxDto.getLaneTxId());
		String homeIssuedDev = agency.getIsHomeAgency() != null ? agency.getIsHomeAgency() : null;
		
		if (!StringUtils.isBlank(homeIssuedDev) && QatpClassificationConstant.Y.equalsIgnoreCase(homeIssuedDev)) 
		{
			logger.info("Process starts for HOME DEVICE");
			tempAccountInfoDto.setHomeIssuedDev(homeIssuedDev);
			
			return processHomeDevice(tempTxDto, tempAccountInfoDto, agencyId);
		} 
		else 
		{
			logger.info("Process starts for AWAY DEVICE");
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
		boolean flag = false;
		tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
		
		DeviceStatus deviceStatus = null;
		deviceStatus = getDeviceStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp() !=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		logger.info("Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());

		if (deviceStatus == null) 
		{
			deviceStatus = getHDeviceStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
			logger.info("H Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());
		}

		if (deviceStatus != null) {
			tempTxDto.setEtcAccountId(deviceStatus.getEtcAccountId());
			tempAccountInfoDto.setDeviceStatus(deviceStatus.getDeviceStatus());
			tempAccountInfoDto.setIsUnregistered(deviceStatus.getRetailTagStatus());
			tempAccountInfoDto.setIsPrevalid(deviceStatus.getIsPrevalidated());
			tempAccountInfoDto.setHomeAgencyDev(1);
			tempAccountInfoDto.setTagFound(1);
			tempAccountInfoDto.setStatus(QatpClassificationConstant.Y);
		
			//beyond logic need to implement
			if(!validateDevice(tempTxDto.getDeviceNo()) && tempAccountInfoDto.getDeviceStatus() ==3) {
				if(!validateAge(tempTxDto)) {
					logger.info("######### Calling beyond logic code for Active Device..... ##########");
					tempTxDto.setTxType("R");
					tempTxDto.setTxSubType("B");
					return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
				}
			}
		} 
		else 
		{
			logger.info("Device info not found in V_DEVICE and V_H_DEVICE Table for device no {}",tempTxDto.getDeviceNo());
		}

		if(!flag)
		{
			if (!awayViolationCheckFlag) 
			{
				Long tempEtcAccountId = tempTxDto.getEtcAccountId() != null ? tempTxDto.getEtcAccountId() : 0L;
				if (tempEtcAccountId != 0) {
					logger.info("Etc Account Id {} exists for lane Tx Id:{}",tempEtcAccountId, tempTxDto.getLaneTxId());
					AccountInfo tempAccountInfo = getAccountInfo(tempEtcAccountId,tempTxDto.getTxDate());
					logger.info("AccountInfo Result:{} for lane Tx Id:{}", tempAccountInfo, tempTxDto.getLaneTxId());
					if (tempAccountInfo != null) 
					{
							int rebillPayType = tempAccountInfo.getPrimaryRebillPayType();
							logger.info("Primary Rebill Pay Type for laneTxId {} is {}",tempTxDto.getLaneTxId(),rebillPayType);
							 
							//check for threshold amount
							String thresholdAmount = masterDataCache.getParkingThresholdValue();
							if(tempTxDto.getPostedFareAmount() < Convertor.toInteger(thresholdAmount))
							{
								logger.info("Amount is less that Threshold Amount");
								tempTxDto.setTxType("P");
								tempTxDto.setTxSubType("Z");
								tempTxDto.setTxStatus(QatpClassificationConstant.NOCC);
							}
							else
							{
								//MASTERCARD or VISA
								if(rebillPayType == QatpClassificationConstant.MASTERCARD || rebillPayType == QatpClassificationConstant.VISA)
								{
									logger.info("Rebill Pay Type is MASTER_CARD or VISA");
									tempTxDto.setTxType("C");
									tempTxDto.setTxSubType("Z");
									tempTxDto.setTxStatus(QatpClassificationConstant.NOCC);
								}
								else
								{
									logger.info("Rebill Pay Type other than MASTER_CARD or VISA");
									tempTxDto.setTxType("R");
									tempTxDto.setTxSubType("Z");
									tempTxDto.setTxStatus(QatpClassificationConstant.NOCC);
								}
							}
							
							setAccountInfoParam(tempAccountInfoDto, tempAccountInfo, tempTxDto);
							return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
					} 
					else 
					{
						
					}

				} else {
					awayViolationCheckFlag = true;
				}
			}
		}

		if (awayViolationCheckFlag) 
		{
			return processAwayAndViolationDevice(tempTxDto, agencyId, tempAccountInfoDto);
		}

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	
	public boolean validateDevice(String value)

	{
		String matcher_String = "\\s{" + value.length() + "}|[*|0]{" + value.length() + "}";
		if (value.matches(matcher_String))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean validateAge(TransactionDto tempTxDto) {
		logger.info("Checking beyond condition for laneTxId {} plazaAgencyId {}",
				tempTxDto.getLaneTxId(), tempTxDto.getPlazaAgencyId());//6/7
        LocalDate postDate = LocalDate.now();
        String txDate = tempTxDto.getTxDate();
        LocalDate localDate = LocalDate.parse(txDate);
        Long txAge = ChronoUnit.DAYS.between(postDate, localDate);
        logger.info("Transaction age for laneTxId {} is {}", tempTxDto.getLaneTxId(), txAge);
        TollPostLimit tollPostLimit = masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 1);

       if ((tollPostLimit != null && (txAge * -1) > tollPostLimit.getAllowedDays()) || txAge > 0) {
            logger.info("Transaction is Beyond for laneTxId {} calling exception TxStatus {}",
            		tempTxDto.getLaneTxId(), 46);
           // tempTxDto.setTxStatus("46");
            
            return false;
        }
		return true;
		
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
	 * Process Away and Violation Transaction
	 * 
	 * @param TransactionDto
	 * @param agencyId
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processAwayAndViolationDevice(TransactionDto tempTxDto, Long agencyId,
			AccountInfoDto tempAccountInfoDto) 
	{
		logger.info("Entered away tx processing for laneTxId {}", tempTxDto.getLaneTxId());
		/**
		 * if iagTagStatus in (1,2) then set (P&T)
		 */
		DeviceAway temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		logger.info("Device Away Result: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		if (temDeviceAway != null) 
		{
			if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(temDeviceAway.getIagTagStatus())) 
			{
				logger.info("Tx Type P and Tx Sub Type T based on valid or low balance IAG status validation  "
						+ "for lane Tx Id:{}",tempTxDto.getLaneTxId());
				
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				tempTxDto.setTxType(QatpClassificationConstant.P);
				tempTxDto.setTxSubType(QatpClassificationConstant.T);
				tempTxDto.setTxStatus(QatpClassificationConstant.NOCC);
			} 
			else
			{
				logger.info("Tx Type R and Tx Sub Type Z for lane Tx Id:{}",tempTxDto.getLaneTxId());
				
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(temDeviceAway.getIagTagStatus().intValue());
				tempTxDto.setTxType(QatpClassificationConstant.R);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
				tempTxDto.setTxStatus(QatpClassificationConstant.NOCC);
			}
		} 
		else
		{
			logger.info("No record found in T_OA_DEVICES for device no {}",tempTxDto.getDeviceNo());
		}
		
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
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
	 * Get Account Info from DB and API call
	 * 
	 * @param etcAccountId
	 * @return AccountInfo
	 */
	private AccountInfo getAccountInfo(Long etcAccountId,String txDate) {
		
		//DB CALL
		AccountInfo tempAccountInfoDB = accountInfoDao.getAccountInfo(etcAccountId,txDate);
		if(tempAccountInfoDB!=null)
		{
			return tempAccountInfoDB;
		}
		else
		{
			return null;
		}
		
//		//FPMS API CALL
//		//NA
//		AccountApiInfoDto tempAccountApiInfoDto = callAccountApi(etcAccountId.toString());
//		Stopwatch stopwatch = Stopwatch.createStarted();
//		stopwatch.stop();
//		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
//		logger.info("Time taken to select data from  CRM.V_ETC_ACCOUNT  ==> {}ms", millis);
//		if (tempAccountInfoDB != null && tempAccountApiInfoDto != null) 
//		{
//			tempAccountInfoDB.setAccountTotalBalance(tempAccountApiInfoDto.getAccountBalance());
//			tempAccountInfoDB.setPostPaidBalance(tempAccountApiInfoDto.getPostpaidBalance());
//			tempAccountInfoDB.setCurrentBalance(tempAccountApiInfoDto.getPrepaidBalance());
//			
//			TCode tempTCode = null;
//			if (tempAccountApiInfoDto.getAccountType() != null) 
//			{
//				tempTCode = masterDataCache.getAccountTypeByCodeValue(tempAccountApiInfoDto.getAccountType());
//				if (tempTCode != null) 
//				{
//					tempAccountInfoDB.setAccountType(tempTCode.getCodeId());
//				}
//			}
//			tempAccountInfoDB.setAgencyId(tempAccountApiInfoDto.getAgencyId());
//			if (tempAccountApiInfoDto.getAcctFinStatus() != null) 
//			{
//				tempTCode = masterDataCache.getFinStatusByCodeValue(tempAccountApiInfoDto.getAcctFinStatus());
//				if (tempTCode != null) 
//				{
//					tempAccountInfoDB.setAcctFinStatus(tempTCode.getCodeId());
//				}
//			}
//			return tempAccountInfoDB;
//		}
//		return null;
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
	 

	// new code as per Manu suggestion to load data fromo master cache
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
		return accountInfoDao.getDeviceStatus(deviceNumber , txTimeStamp);
	}

}