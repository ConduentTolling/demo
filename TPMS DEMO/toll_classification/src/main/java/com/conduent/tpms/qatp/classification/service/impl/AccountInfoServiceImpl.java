package com.conduent.tpms.qatp.classification.service.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
	public TransactionDetailDto getAccountInformation(TransactionDto tempTxDto) {
		if (tempTxDto == null)
			return null;
		if (tempTxDto.getDeviceNo() != null) {
			return getAcctByTag(tempTxDto);
		}
		
		else if (tempTxDto.getTxType().equals("V")) {// txStatus=9
			if (!validateAgeForVoilation(tempTxDto)) {
				logger.info("######### Calling beyond logic code for TxType => V..... ##########");
				tempTxDto.setTxType("R");
				tempTxDto.setTxSubType("B");
				tempTxDto.setTollRevenueType(1);
				AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}else {
				AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}
		}
		else if (tempTxDto.getTxType().equals("E")) {// txStatus=1
			if (!validateAge(tempTxDto)) {
				logger.info("######### Calling beyond logic code for TxType => E..... ##########");
				tempTxDto.setTxType("R");
				tempTxDto.setTxSubType("B");
				AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}else {
				AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}
		}
		else if (!StringUtils.isBlank(tempTxDto.getPlateNumber())
				&& !("**********".equalsIgnoreCase(tempTxDto.getPlateNumber()))) {
			return getAcctByPlate(tempTxDto);
		} 
		else {
			logger.info("Tx Type V and Tx Sub Type F based on invalid device and license plate for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.F);
			tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
			
			String aetFlag=tempTxDto.getAetFlag();
			if(aetFlag!=null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			}else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
			}
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
	private List<DeviceAway> findDeviceAway(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		return accountInfoDao.findDeviceAway(deviceNumber, deviceNo11charecter, txTimeStamp);
	}

	/**
	 * Get Account Information By Device
	 * 
	 * @param tempTxDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto getAcctByTag(TransactionDto tempTxDto) {
		AccountInfoDto tempAccountInfoDto = new AccountInfoDto();
		//TransactionDetailDto transactionDetailDto = null; //commented as per new NY logic.
		logger.info("Entered Get Account By Tag for laneTxId {}", tempTxDto.getLaneTxId());
		
		if (tempTxDto.getTxType().equals("V")) {// txStatus=9
			if (!validateAgeForVoilation(tempTxDto)) {
				logger.info("######### Calling beyond logic code for TxType => V..... ##########");
				tempTxDto.setTxType("R");
				tempTxDto.setTxSubType("B");
				tempTxDto.setTollRevenueType(1);
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}
		}
		 if (tempTxDto.getTxType().equals("E")) {// txStatus=1
			if (!validateAge(tempTxDto)) {
				logger.info("######### Calling beyond logic code for TxType => E..... ##########");
				tempTxDto.setTxType("R");
				tempTxDto.setTxSubType("B");
				return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
			}
		}
			
		return processHomeDevice(tempTxDto, tempAccountInfoDto);
		
		//below code commented as per new NY logic.
/*		
		if (tempTxDto.getDeviceNo() != null) {
			Agency agency = null;
			agency = masterDataCache.getAgency(tempTxDto.getDeviceNo().substring(0, 3));
			logger.info("Agency: {}", agency);
			Long agencyId = agency != null ? agency.getAgencyId() : 0L;
			logger.info("Agency Id: {}", agencyId);

			// if invalidAgencyId
			if (agencyId == 0L) {
				// Invalid Agency
				if (tempTxDto.getTxType().equals("V")) {// txStatus=9
					if (!validateAgeForVoilation(tempTxDto)) {
						logger.info("######### Calling beyond logic code for TxType => V..... ##########");
						tempTxDto.setTxType("R");
						tempTxDto.setTxSubType("B");
						tempTxDto.setTollRevenueType(1);
						return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
					}else {
						transactionDetailDto = processInvalidAgencyTx(tempTxDto, tempAccountInfoDto);
					}
				}
				// beyond logic
				else if (tempTxDto.getTxType().equals("E")) {// txStatus=1
					if (!validateAge(tempTxDto)) {
						logger.info("######### Calling beyond logic code for TxType => E..... ##########");
						tempTxDto.setTxType("R");
						tempTxDto.setTxSubType("B");
						return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
					}else {
						transactionDetailDto = processInvalidAgencyTx(tempTxDto, tempAccountInfoDto);
					}
				}else {
				
				transactionDetailDto = processInvalidAgencyTx(tempTxDto, tempAccountInfoDto);
				}
			} else if (agencyId >= 0) {
				// Valid Agency
				if (tempTxDto.getTxType().equals("V")) {// txStatus=9
					if (!validateAgeForVoilation(tempTxDto)) {
						logger.info("######### Calling beyond logic code for TxType => V..... ##########");
						tempTxDto.setTxType("R");
						tempTxDto.setTxSubType("B");
						tempTxDto.setTollRevenueType(1);
						return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
					}else {
						transactionDetailDto = processValidAgencyTx(tempTxDto, tempAccountInfoDto, agency, agencyId);
					}
				}
				// beyond logic
				else if (tempTxDto.getTxType().equals("E")) {// txStatus=1
					if (!validateAge(tempTxDto)) {
						logger.info("######### Calling beyond logic code for TxType => E..... ##########");
						tempTxDto.setTxType("R");
						tempTxDto.setTxSubType("B");
						return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
					}else {
						transactionDetailDto = processValidAgencyTx(tempTxDto, tempAccountInfoDto, agency, agencyId);
					}
				}else {
				transactionDetailDto = processValidAgencyTx(tempTxDto, tempAccountInfoDto, agency, agencyId);
				}
			}
		}

		logger.info("TransactionDetailDto: {}", transactionDetailDto);
		return transactionDetailDto;
		
*/		
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
		if (!StringUtils.isBlank(homeIssuedDev) && QatpClassificationConstant.Y.equalsIgnoreCase(homeIssuedDev)) {
			tempAccountInfoDto.setHomeIssuedDev(homeIssuedDev);
			return processHomeDevice(tempTxDto, tempAccountInfoDto);
		} else {
			return checkHomeTag(tempTxDto, tempAccountInfoDto);
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
	private TransactionDetailDto processHomeDevice(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {

		// HomeBusiness logic
		boolean awayViolationCheckFlag = false;
		boolean flag = false;
		tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
		
		
		String device11Digit=null;
		int deviceLength=tempTxDto.getDeviceNo().length();
		
		if(deviceLength==14) {
			
			device11Digit = checkForDevice(tempTxDto.getDeviceNo());
			logger.info("device11Digit =====> "+device11Digit);			
		}
		
		List<DeviceStatus> deviceStatus = getDeviceStatus(tempTxDto.getDeviceNo(), device11Digit, tempTxDto.getTxTimeStamp() !=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		if (deviceStatus != null) {
			for (DeviceStatus deviceInfo : deviceStatus) {
				String deviceNo = deviceInfo.getDeviceNumber();
				if (deviceNo.length() == 14) {
					tempTxDto.setDeviceNo(deviceNo);
					break;
				}

				else {
					tempTxDto.setDeviceNo(deviceNo);
				}
			}
		}
		
		logger.info("Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());

		if (deviceStatus == null) {
			deviceStatus = getHDeviceStatus(tempTxDto.getDeviceNo(), device11Digit,
					tempTxDto.getTxTimeStamp() != null ? tempTxDto.getTxTimeStamp().toLocalDateTime() : null);
			logger.info("H Device Status Result: {} for lane Tx Id:{}", deviceStatus, tempTxDto.getLaneTxId());

			if (deviceStatus != null) {
				for (DeviceStatus deviceInfo : deviceStatus) {
					String deviceNo = deviceInfo.getDeviceNumber();
					if (deviceNo.length() == 14) {
						tempTxDto.setDeviceNo(deviceNo);
						break;
					} else {
						tempTxDto.setDeviceNo(deviceNo);
					}

				}
			}

		}
		
		// get agencyid based on etcAccountId
		Long agencyId = 0L;
		Integer retailTagStatus = 0;
		if (deviceStatus != null) {
			for (DeviceStatus deviceInfo : deviceStatus) {
				String deviceNo = deviceInfo.getDeviceNumber();
				if (deviceNo.length() == 14) {
					tempTxDto.setEtcAccountId(deviceInfo.getEtcAccountId());
					tempAccountInfoDto.setDeviceStatus(deviceInfo.getDeviceStatus());
					tempAccountInfoDto.setIsUnregistered(deviceInfo.getRetailTagStatus());
					tempAccountInfoDto.setIsPrevalid(deviceInfo.getIsPrevalidated());
					tempAccountInfoDto.setHomeAgencyDev(1);
					tempAccountInfoDto.setTagFound(1);
					tempAccountInfoDto.setStatus(QatpClassificationConstant.Y);
					retailTagStatus = deviceInfo.getRetailTagStatus();
					break;
				} else {
					tempTxDto.setEtcAccountId(deviceInfo.getEtcAccountId());
					tempAccountInfoDto.setDeviceStatus(deviceInfo.getDeviceStatus());
					tempAccountInfoDto.setIsUnregistered(deviceInfo.getRetailTagStatus());
					tempAccountInfoDto.setIsPrevalid(deviceInfo.getIsPrevalidated());
					tempAccountInfoDto.setHomeAgencyDev(1);
					tempAccountInfoDto.setTagFound(1);
					tempAccountInfoDto.setStatus(QatpClassificationConstant.Y);
					retailTagStatus = deviceInfo.getRetailTagStatus();
				}
			}
			//get AgencyId based on etcAccountId
			//agencyId = accountInfoDao.getAgency(tempTxDto.getEtcAccountId());
			agencyId = tempTxDto.getPlazaAgencyId().longValue();
			logger.info("######### Plaza Agency Id ########## "+agencyId);
			// beyond logic
			if (!validateDevice(tempTxDto.getDeviceNo()) && tempAccountInfoDto.getDeviceStatus() == 3) {
				if (!validateAge(tempTxDto)) {
					logger.info("######### Calling beyond logic code for Active Device..... ##########");
					tempTxDto.setTxType(QatpClassificationConstant.R);
					tempTxDto.setTxSubType(QatpClassificationConstant.B);
					return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
				}
			}
			
		} else {
			awayViolationCheckFlag = true;
		}

		if(!flag)
		{
		if (!awayViolationCheckFlag) {
			ProcessParameter tempProcessParameter = getProcessParameter(
					QatpClassificationConstant.QATP_RETAIL_TAG_CHECK, agencyId);

			//checkUnregisteredDevice(tempTxDto, tempAccountInfoDto, deviceStatus, tempProcessParameter);
			
			checkUnregisteredDevice(tempTxDto, tempAccountInfoDto, deviceStatus,retailTagStatus, tempProcessParameter);

			Long tempEtcAccountId = tempTxDto.getEtcAccountId() != null ? tempTxDto.getEtcAccountId() : 0L;
			if (tempEtcAccountId != 0) {
				logger.info("Etc Account Id {} exists for lane Tx Id:{}",tempEtcAccountId, tempTxDto.getLaneTxId());
				AccountInfo tempAccountInfo = getAccountInfo(tempEtcAccountId,tempTxDto.getTxDate());
				logger.info("AccountInfo Result:{} for lane Tx Id:{}", tempAccountInfo, tempTxDto.getLaneTxId());
				if (tempAccountInfo != null) {
					setAccountInfoParam(tempAccountInfoDto, tempAccountInfo, tempTxDto);
					getSpeedStatus(tempTxDto, tempAccountInfoDto);
					return processSpeedLimitForAccount(tempTxDto, tempAccountInfoDto);
				} else {
					awayViolationCheckFlag = true;
				}

			} else {
				awayViolationCheckFlag = true;
			}
		}
		}

//		if (awayViolationCheckFlag) {
//			return processAwayAndViolationDevice(tempTxDto, agencyId, tempAccountInfoDto);
//		}

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	
	public String checkForDevice(String deviceNo) {

			// TODO Auto-generated method stub
		
			String deviceNo11charecter=null;
		
			char a =deviceNo.charAt(4);
			char b = deviceNo.charAt(5);
			
			String s = String.valueOf(a);
			String t = String.valueOf(b);
			
			System.out.println(" s ---> "+ s);
			System.out.println(" t ---> "+ t);
			
			if (s.contains("0") &&  t.contains("0") ) {
				System.out.println("check for 11 & 14 digit device");
				 deviceNo11charecter= deviceNo.substring(1, 4)+deviceNo.substring(6, 14);
				logger.info("Device Number 14 charecter: {} and 11 charecter :{}", deviceNo, 
						deviceNo11charecter);
			}
			return deviceNo11charecter;
			
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
	
	public boolean validateAgeForVoilation(TransactionDto tempTxDto) {
		logger.info("Checking beyond condition for laneTxId {} plazaAgencyId {} TxStatus {}",
				tempTxDto.getLaneTxId(), tempTxDto.getPlazaAgencyId(), tempTxDto.getTxStatus());//6/7
        LocalDate postDate = LocalDate.now();
        String txDate = tempTxDto.getTxDate();
        LocalDate localDate = LocalDate.parse(txDate);
        Long txAge = ChronoUnit.DAYS.between(postDate, localDate);
        logger.info("Transaction age for laneTxId {} is {}", tempTxDto.getLaneTxId(), txAge);
        TollPostLimit tollPostLimit = masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 9);

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
	 * Get Speed Status
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 */
	private void getSpeedStatus(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		TCode tempTCode = null;
		if (QatpClassificationConstant.PRIVATE.equals(tempAccountInfoDto.getAcctType())) {
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
//	private void checkUnregisteredDevice(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto,
//			List<DeviceStatus> deviceStatus, ProcessParameter tempProcessParameter) {
//		if (tempProcessParameter != null
//				&& QatpClassificationConstant.Y.equalsIgnoreCase(tempProcessParameter.getParamValue())
//				&& deviceStatus != null
//				&& QatpClassificationConstant.RET_TAG_UNREG.equals(deviceStatus.getRetailTagStatus())) {
//
//			DeviceStatus tempHaDeviceStatus = getHATagStatus(tempTxDto.getDeviceNo(), tempTxDto.getTxDate());
//			logger.info("HA Device Status Result: {} for lane Tx Id:{}", tempHaDeviceStatus, tempTxDto.getLaneTxId());
//			Long iagDeviceStatus = tempHaDeviceStatus != null ? tempHaDeviceStatus.getIagDeviceStatus() : null;
//			if (iagDeviceStatus != null && (3 == iagDeviceStatus || 4 == iagDeviceStatus)) {
//				tempAccountInfoDto.setIsUnregistered(QatpClassificationConstant.RET_TAG_UNREG_1.intValue());
//			}
//		}
//	}
	
	private void checkUnregisteredDevice(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto,List<DeviceStatus> deviceStatus,
			Integer retailTagStatus, ProcessParameter tempProcessParameter) {
		
		if (tempProcessParameter != null
				&& QatpClassificationConstant.Y.equalsIgnoreCase(tempProcessParameter.getParamValue())
				&& deviceStatus != null
				&& QatpClassificationConstant.RET_TAG_UNREG.equals(retailTagStatus)) {
			
			String device11Digit=null;
			int deviceLength=tempTxDto.getDeviceNo().length();
			
			if(deviceLength==14) {
				
				device11Digit = checkForDevice(tempTxDto.getDeviceNo());
				System.out.println("device11Digit =====> "+device11Digit);
				
				/*
				deviceNo11charecter= tempTxDto.getDeviceNo().substring(1, 4)+tempTxDto.getDeviceNo().substring(6, 14);
				logger.info("Device Number 14 charecter: {} and 11 charecter :{}", tempTxDto.getDeviceNo(), 
						deviceNo11charecter);
				*/
			}

			List<DeviceStatus> tempHaDeviceStatus = getHATagStatus(tempTxDto.getDeviceNo(), device11Digit, tempTxDto.getTxDate());
			logger.info("HA Device Status Result: {} for lane Tx Id:{}", tempHaDeviceStatus, tempTxDto.getLaneTxId());
			
			Long iagDeviceStatus=null;
			if (tempHaDeviceStatus != null) {
				for (DeviceStatus haDeviceStatus : tempHaDeviceStatus) {
					String deviceNo = haDeviceStatus.getDeviceNumber();
					if (deviceNo.length() == 14) {
						tempTxDto.setDeviceNo(deviceNo);
						iagDeviceStatus = haDeviceStatus.getIagDeviceStatus();
						break;
					} else {
						tempTxDto.setDeviceNo(deviceNo);
						iagDeviceStatus = haDeviceStatus.getIagDeviceStatus();
					}
				}
			}
			iagDeviceStatus = tempHaDeviceStatus != null ? iagDeviceStatus : null;
			if (iagDeviceStatus != null && (3 == iagDeviceStatus || 4 == iagDeviceStatus)) {
				tempAccountInfoDto.setIsUnregistered(QatpClassificationConstant.RET_TAG_UNREG_1.intValue());
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
			speedThreshold = tSpeedThresholdDao.getSpeedLimitForAgency(tempTxDto.getPlazaAgencyId(),
					tempTxDto.getAccountType());
			logger.info("Speed Threshold Result for Agency:{} for lane Tx Id:{}", speedThreshold,
					tempTxDto.getLaneTxId());
			if (speedThreshold != null && speedThreshold.getLowerSpeedThreshold() != null) {
				tempAccountInfoDto.setSpeedLimit(speedThreshold.getLowerSpeedThreshold());
			} else {
				tempAccountInfoDto.setSpeedLimit(QatpClassificationConstant.DEFAULT_SPEED_LIMIT_THRESHOLD);
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
		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.F);
		tempAccountInfoDto.setInvalidAgencyPfx(1);
		
		String aetFlag=tempTxDto.getAetFlag();
		if(aetFlag!=null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		}else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
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
		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.F);
		tempAccountInfoDto.setTagFound(0);
		
		String aetFlag=tempTxDto.getAetFlag();
		if(aetFlag!=null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		}else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
		}

		// Violation
		if (QatpClassificationConstant.V.equalsIgnoreCase(tempTxDto.getTxType())) {
			tempTxDto.setTxSubType(QatpClassificationConstant.F);
		}
		if (QatpClassificationConstant.I.equalsIgnoreCase(tempTxDto.getTxType())
				|| QatpClassificationConstant.V.equalsIgnoreCase(tempTxDto.getTxSubType())) {
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
	public TransactionDetailDto checkHomeTag(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		logger.info("Entered away tx processing for laneTxId {}", tempTxDto.getLaneTxId());
		// Away
		/**
		 * if iagTagStatus in (1,2) then set (O&T) else set (V&F)
		 */
		//DeviceAway temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		
		String device11Digit=null;
		int deviceLength=tempTxDto.getDeviceNo().length();
		
		if(deviceLength==14) {
			
			device11Digit = checkForDevice(tempTxDto.getDeviceNo());
			System.out.println("device11Digit =====> "+device11Digit);
			/*
			deviceNo11charecter= tempTxDto.getDeviceNo().substring(1, 4)+tempTxDto.getDeviceNo().substring(6, 14);
			logger.info("Device Number 14 charecter: {} and 11 charecter :{}", tempTxDto.getDeviceNo(), 
					deviceNo11charecter);
			*/
		}
		
		List<DeviceAway> temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), device11Digit, tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		
		logger.info("Device Away Result: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		
		Long iagTagStatus = 0l;
		if (temDeviceAway!=null && !temDeviceAway.isEmpty()) {
			for (DeviceAway deviceAway : temDeviceAway) {
				String deviceNo = deviceAway.getDeviceNumber();
				if (deviceNo.length() == 14) {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
					tempTxDto.setAccAgencyId(deviceAway.getTagHomeAgency());
					break;
				} else {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
					tempTxDto.setAccAgencyId(deviceAway.getTagHomeAgency());
				}
			}
		}
		
		if (temDeviceAway!=null && !temDeviceAway.isEmpty()) {
		//	if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)) {
				logger.info(
						"Tx Type O and Tx Sub Type T based on valid or low balance IAG status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
				tempTxDto.setTxType(QatpClassificationConstant.O);
				tempTxDto.setTxSubType(QatpClassificationConstant.T);
				tempTxDto.setIsViolation("0");
				awayClassMismatchCheck(tempTxDto);
		/*	} 
			else if (QatpClassificationConstant.IAG_STATUS_FOR_INVALID_AND_ZERO.contains(iagTagStatus)) {
				logger.info("Tx Type R and Tx Sub Type T based on unavailibility of away Device Info  for lane Tx Id:{} bz TagStatus is not 1 or 2",
						tempTxDto.getLaneTxId());
				tempTxDto.setTxType(QatpClassificationConstant.R);
				tempTxDto.setTxSubType(QatpClassificationConstant.T);
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
			}
			*/
		} else if (temDeviceAway == null) {
			logger.info("Tx Type R and Tx Sub Type T based on unavailibility of away Device Info  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
			tempTxDto.setTxType(QatpClassificationConstant.R);
			tempTxDto.setTxSubType(QatpClassificationConstant.T);
			
			//return processViolationTx(tempTxDto, agencyId, tempAccountInfoDto);
		}

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
			if (QatpClassificationConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[0]))
					&& QatpClassificationConstant.N.equalsIgnoreCase(String.valueOf(txInfoArray[1]))
					&& QatpClassificationConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[2]))
					&& QatpClassificationConstant.ZERO.equalsIgnoreCase(String.valueOf(txInfoArray[3]))) {
				if (classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(tempTxDto)) {
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.G);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
					}
					logger.info(
							"Tx Type V and Tx Sub Type G based on away class Mismatch of valid veh and tag class  for lane Tx Id:{}",
							tempTxDto.getLaneTxId());
				} else {
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.C);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
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
	private List<DeviceStatus> getHDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		return accountInfoDao.getHDeviceStatus(deviceNumber, deviceNo11charecter, txTimeStamp);
	}

	/**
	 * Get HA Tag Status Info
	 * 
	 * @param deviceNumber
	 * @param txTimeStamp
	 * @return DeviceStatus
	 */
	private List<DeviceStatus> getHATagStatus(String deviceNumber, String deviceNo11charecter, String txDate) {
		return accountInfoDao.getHATagStatus(deviceNumber, deviceNo11charecter, txDate);
	}

	/**
	 * Get Account Info from DB and API call
	 * 
	 * @param etcAccountId
	 * @return AccountInfo
	 */
	private AccountInfo getAccountInfo(Long etcAccountId,String txDate) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		//AccountApiInfoDto tempAccountApiInfoDto = callAccountApi(etcAccountId.toString());
		AccountApiInfoDto tempAccountApiInfoDto = accountInfoDao.getAccountInfoFromFPMS(etcAccountId.toString());
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to get data from FPMS Account API ==> {}ms", millis);
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
	private List<DeviceStatus> getDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		return accountInfoDao.getDeviceStatus(deviceNumber, deviceNo11charecter, txTimeStamp);
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
		
		String device11Digit=null;
		int deviceLength=tempTxDto.getDeviceNo().length();
		
		if(deviceLength==14) {
			
			device11Digit = checkForDevice(tempTxDto.getDeviceNo());
			System.out.println("device11Digit =====> "+device11Digit);
			
			/*
			deviceNo11charecter= tempTxDto.getDeviceNo().substring(1, 4)+tempTxDto.getDeviceNo().substring(6, 14);
			logger.info("Device Number 14 charecter: {} and 11 charecter :{}", tempTxDto.getDeviceNo(), 
					deviceNo11charecter);
					*/
		}
		List<DeviceAway> temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), device11Digit, tempTxDto.getTxTimeStamp().toLocalDateTime());

		Long iagTagStatus = 0l;
		if (temDeviceAway != null) {
			for (DeviceAway deviceAway : temDeviceAway) {
				String deviceNo = deviceAway.getDeviceNumber();
				if (deviceNo.length() == 14) {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
					break;
				} else {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
				}
			}
		}
		logger.info("Device Away Result: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		if (temDeviceAway != null) {
			if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)) {
				logger.info("Tx Type V and Tx Sub Type T based on valid or low balance IAG status validation  for lane Tx Id:{}",tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.T);
				tempTxDto.setIsViolation("1");
				tempTxDto.setTxStatus("511");
				tempTxDto.setAwayAgency("Y");
				// awayClassMismatchCheck(tempTxDto);
				//tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_ETC);
			} else if (QatpClassificationConstant.IAG_STATUS_3_FOR_AWAY_DEVICE.contains(iagTagStatus)) {
				logger.info("Tx Type V and Tx Sub Type F based on invalid or zero IAG status validation  for lane Tx Id:{}",tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.Y);
				tempTxDto.setIsViolation("1");

				String aetFlag = tempTxDto.getAetFlag();
				if (aetFlag != null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
				} else {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
				}

			} else if (QatpClassificationConstant.IAG_STATUS_4_FOR_AWAY_DEVICE.contains(iagTagStatus)) {
				logger.info("Tx Type V and Tx Sub Type F based on invalid or zero IAG status validation  for lane Tx Id:{}",tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.F);
				tempTxDto.setIsViolation("1");

				String aetFlag = tempTxDto.getAetFlag();
				if (aetFlag != null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
				} else {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
				}

			}
			
		} else if (temDeviceAway == null) {
			logger.info("Tx Type V and Tx Sub Type Y based on unavailibility of away Device Info  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
			tempTxDto.setReciprocityTrx(QatpClassificationConstant.F);
			tempAccountInfoDto.setTagFound(1);
			tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.F);
			
			String aetFlag = tempTxDto.getAetFlag();
			if (aetFlag != null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			} else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
			}
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	@Override
	public TransactionDetailDto checkAwayMTATag(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {

		logger.info("Entered away tx processing for laneTxId {}", tempTxDto.getLaneTxId());
		// Away
		/**
		 * if iagTagStatus in (1,2) then set (O&T) else set (V&F)
		 */
		String device11Digit=null;
		int deviceLength=tempTxDto.getDeviceNo().length();
		
		if(deviceLength==14) {
			
			device11Digit = checkForDevice(tempTxDto.getDeviceNo());
			System.out.println("device11Digit =====> "+device11Digit);
		}
		
		List<DeviceAway> temDeviceAway = findDeviceAway(tempTxDto.getDeviceNo(), device11Digit, tempTxDto.getTxTimeStamp()!=null? tempTxDto.getTxTimeStamp().toLocalDateTime():null);
		
		logger.info("Device Away Result for MTA: {} for laneTxId: {}", temDeviceAway, tempTxDto.getLaneTxId());
		
		Long iagTagStatus = 0l;
		if (temDeviceAway!=null && !temDeviceAway.isEmpty()) {
			for (DeviceAway deviceAway : temDeviceAway) {
				String deviceNo = deviceAway.getDeviceNumber();
				if (deviceNo.length() == 14) {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
					tempTxDto.setAccAgencyId(deviceAway.getTagHomeAgency());
					break;
				} else {
					tempTxDto.setDeviceNo(deviceNo);
					iagTagStatus = deviceAway.getIagTagStatus();
					tempTxDto.setAccAgencyId(deviceAway.getTagHomeAgency());
				}
			}
		}
		
		if (temDeviceAway!=null && !temDeviceAway.isEmpty()) {
				logger.info(
						"Tx Type O and Tx Sub Type T based on valid or low balance IAG status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setReciprocityTrx(QatpClassificationConstant.T);
				tempAccountInfoDto.setTagFound(1);
				tempAccountInfoDto.setIagStatus(iagTagStatus.intValue());
				tempTxDto.setIsViolation("0");
				
				if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)
						&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
						&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
						&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
						&& tempTxDto.getTagClass()==14) {
					logger.info(" #### log for row 12....###");
					tempTxDto.setTxType(QatpClassificationConstant.O);
					tempTxDto.setTxSubType(QatpClassificationConstant.T);
				}
				else if (tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
						&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
						&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
					logger.info(" #### log for row 10....###");
					tempTxDto.setTxType(QatpClassificationConstant.O);
					tempTxDto.setTxSubType(QatpClassificationConstant.T);
				}
				else if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)
						&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
						&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTagClass()<=3 && tempTxDto.getActualClass()>=4)) {
					logger.info(" #### log for row 17....###");
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.G);
				}
				else if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)
						&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
						&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
						&& !(tempTxDto.getTagClass()<=3 && tempTxDto.getActualClass()>=4)) {
					logger.info(" #### log for row 18....###");
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.C);
				}
				else if (QatpClassificationConstant.IAG_STATUS_FOR_AWAY_DEVICE.contains(iagTagStatus)
						&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
						&& (tempTxDto.getTi4TagStatus() ==null || !(tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO)))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
					logger.info(" #### log for row 21....###");
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.T);
					tempTxDto.setAwayAgency("Y");
				}
				else if (QatpClassificationConstant.IAG_STATUS_3_FOR_AWAY_DEVICE.contains(iagTagStatus)
						&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
						&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
						&& (tempTxDto.getTi4TagStatus()==null || !(tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO)))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
					logger.info(" #### log for row 23....###");
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.Y);
				}
				else if (tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
						&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.Y)
						&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
						&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
					logger.info(" #### log for row 19 for AWAY....###");
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.Q);
					tempTxDto.setAwayAgency("Y");
				}
				else {
					tempTxDto.setTxType(QatpClassificationConstant.V);
					tempTxDto.setTxSubType(QatpClassificationConstant.F);
				}
				
		} else if (temDeviceAway == null) {
			logger.info("Tx Type V and Tx Sub Type F based on unavailibility of away Device Info  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.F);
		}

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	
	}

}