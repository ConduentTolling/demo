package com.conduent.tpms.qatp.classification.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.AccountPlanDetail;
import com.conduent.tpms.qatp.classification.model.PlanDetails;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.service.TransactionClassificationService;
import com.conduent.tpms.qatp.classification.utility.ClassificationBusinessRuleHelper;

/**
 * Transaction Classification Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class TransactionClassificationServiceImpl implements TransactionClassificationService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionClassificationServiceImpl.class);

	@Autowired
	private AccountInfoDao accountInfoDao;

	@Autowired
	private ClassificationBusinessRuleHelper classificationBusinessRuleHelper;

	@Autowired
	private AccountInfoService accountInfoService;

	/**
	 * Classify Transaction
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	//boolean flag = false;
	public TransactionDetailDto processTransactionClassification(TransactionDto tempTxDto,
			AccountInfoDto tempAccountInfoDto) {

		if (tempTxDto.getEtcViolObserved() !=null && tempTxDto.getEtcViolObserved().equals(0)) {
			/**
			 * 	Nysta, Nysba & PA file classification logic for EZ & OT transaction.
			 */
			homeAndAwayTrxForNystaNysbaPA(tempTxDto, tempAccountInfoDto);
			
		} else if (tempTxDto.getEtcViolObserved() !=null && tempTxDto.getEtcViolObserved().equals(1)) {

			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.F);
			tempTxDto.setIsViolation("1");

			String aetFlag = tempTxDto.getAetFlag();
			if (aetFlag != null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			} else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
			}
			logger.info(
					"Tx Type V and Tx Sub Type F based on valid validation of device and toll system  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
			return checkDetailTransactions(tempTxDto, tempAccountInfoDto);

		} else if (tempTxDto.getPlazaAgencyId() == 2) {
			/**
			 * 	MTA file classification logic for EZ, OT & VF transaction.
			 */
			
			homeAndAwayTrxForMTA(tempTxDto, tempAccountInfoDto);
		} 
		else if (classificationBusinessRuleHelper.checkTollSystemTypeForEAndX(tempTxDto)) {
			if (QatpClassificationConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTxDto.getTollSystemType())) {
				tempTxDto.setTxType(QatpClassificationConstant.U);
				tempTxDto.setTxSubType(QatpClassificationConstant.E);
				logger.info(
						"Tx Type U and Tx Sub Type E based on valid toll system validation of E and X for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
			}
			if (QatpClassificationConstant.TOLL_SYSTEM_TYPE_X.equalsIgnoreCase(tempTxDto.getTollSystemType())) {
				tempTxDto.setTxType(QatpClassificationConstant.U);
				tempTxDto.setTxSubType(QatpClassificationConstant.E);
				logger.info(
						"Tx Type U and Tx Sub Type E based on valid toll system validation of E and X for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
			}
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	
	private TransactionDetailDto homeAndAwayTrxForNystaNysbaPA(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		// TODO Auto-generated method stub
		
		if ((tempTxDto.getDeviceNo() == null || validateDevice(tempTxDto.getDeviceNo()))
				//&& !classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)
				) {

			tempTxDto.setTxType(QatpClassificationConstant.R);
			tempTxDto.setTxSubType(QatpClassificationConstant.T);

		} else if (classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)
				&& !tempTxDto.isSpeedViolation()) {

			if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)) {
				tempTxDto.setTxType(QatpClassificationConstant.E);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
			} else if (!classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)) {
				tempTxDto.setTxType(QatpClassificationConstant.E);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
			} else if (!classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)) {
				tempTxDto.setTxType(QatpClassificationConstant.E);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
			}
		} else {
			if (tempTxDto.getDeviceNo() != null && !validateDevice(tempTxDto.getDeviceNo())
					&& !classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)) {
				logger.info(" #### Inside Home tag....###");
				return accountInfoService.checkHomeTag(tempTxDto, tempAccountInfoDto);
			}
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}


	private TransactionDetailDto homeAndAwayTrxForMTA(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		// logic for Home violation tag
		logger.info(" #### Inside MTA Violation Home tag....###");
		if ((tempTxDto.getDeviceNo() == null || validateDevice(tempTxDto.getDeviceNo()))
				//&& !classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)
				) {

			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.F);

		} else if (classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)
				&& !tempTxDto.isSpeedViolation()) {
			if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
					&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag() !=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
					&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
					&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
					&& QatpClassificationConstant.ACC_FIN_STATUS.contains(tempAccountInfoDto.getFinStatus())
					&& tempTxDto.getTagClass()==14) {
				logger.info(" #### log for row 11....###");
				tempTxDto.setTxType(QatpClassificationConstant.E);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
			}
			else if (tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
					&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
					&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
				logger.info(" #### log for row 9....###");
				tempTxDto.setTxType(QatpClassificationConstant.E);
				tempTxDto.setTxSubType(QatpClassificationConstant.Z);
			}
			else if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
					&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag() !=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
					&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
					&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.Y)
					&& QatpClassificationConstant.ACC_FIN_STATUS.contains(tempAccountInfoDto.getFinStatus())) {
				logger.info(" #### log for row 14....###");
				
				PlanDetails accountPlan = accountInfoDao.getPlanName(tempTxDto.getEtcAccountId());
				if(accountPlan!=null) {
					
					if(accountPlan.getPlanType().equals(QatpClassificationConstant.PLAN_TYPE_186)	
						&& accountPlan.getPlanTypeName().equals(QatpClassificationConstant.PLAN_NAME_SICP))
					{
						tempTxDto.setHovFlag(QatpClassificationConstant.Y);
						logger.info(" #### plan is associated to SICP....#1");
					}else {
						logger.info(" #### plan not associated to SICP....#2");
						tempTxDto.setHovFlag(QatpClassificationConstant.C);
					}
				}else {
					logger.info(" #### plan not associated to SICP....#3");		
				tempTxDto.setHovFlag(QatpClassificationConstant.C);
				}
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.H);
			}
			else if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
					&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
					&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
					&& QatpClassificationConstant.ACC_FIN_STATUS.contains(tempAccountInfoDto.getFinStatus())
					&& (tempTxDto.getTagClass()<=3 && tempTxDto.getActualClass()>=4)) {
				logger.info(" #### log for row 15....###");
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.G);
			}
			else if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
					&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag()!=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.Y))
					&& (tempTxDto.getTi4TagStatus()!=null && tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
					&& QatpClassificationConstant.ACC_FIN_STATUS.contains(tempAccountInfoDto.getFinStatus())
					&& !(tempTxDto.getTagClass()<=3 && tempTxDto.getActualClass()>=4)) {
				logger.info(" #### log for row 16....###");
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.C);
			}
			else if (classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
					&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
					&& tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag() !=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
					&& (tempTxDto.getTi4TagStatus() ==null || !(tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO)))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
					&& QatpClassificationConstant.ACC_FIN_STATUS.contains(tempAccountInfoDto.getFinStatus())) {
				logger.info(" #### log for row 20....###");
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.T);
			}
			else if (tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.N)
					&& (tempTxDto.getTi3ClassMismatchFlag() !=null && tempTxDto.getTi3ClassMismatchFlag().equals(QatpClassificationConstant.N))
					&& (tempTxDto.getTi4TagStatus() ==null || !(tempTxDto.getTi4TagStatus().equals(QatpClassificationConstant.ZERO)))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)
					&& QatpClassificationConstant.ZERO_FIN_STATUS.equals(tempAccountInfoDto.getFinStatus())) {
				logger.info(" #### log for row 22....###");
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.Y);
			}
			else if (tempTxDto.getTi1TagReadStatus().equals(QatpClassificationConstant.Y)
					&& tempTxDto.getTi2AdditionalTags().equals(QatpClassificationConstant.Y)
					&& (tempTxDto.getTi5PaymentFlag()!=null && tempTxDto.getTi5PaymentFlag().equals(QatpClassificationConstant.V))
					&& tempTxDto.getHovFlag().equals(QatpClassificationConstant.N)) {
				logger.info(" #### log for row 19 in Home....###");
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.Q);
			}
			else {
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.F);
			}

		} else {
			// logic for away Violation
			if (tempTxDto.getDeviceNo() != null && !validateDevice(tempTxDto.getDeviceNo())
					&& !classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)) {
				logger.info(" #### Inside MTA Violation Away tag....###");
				return accountInfoService.checkAwayMTATag(tempTxDto, tempAccountInfoDto);
			}
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	private TransactionDetailDto checkDetailTransactions(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {

		if (!classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)) {
			invalidDeviceCheck(tempTxDto);
			if (tempTxDto.getDeviceNo() != null && !validateDevice(tempTxDto.getDeviceNo())) {
				logger.info(" #### Inside companion tag....###");
				return accountInfoService.checkCompanionTag(tempTxDto, tempAccountInfoDto);
			}
		} else if (QatpClassificationConstant.ACCOUNT_STATUS_RVKF.equals(tempAccountInfoDto.getActStatus())) {
			checkForRVKF(tempTxDto);
		} else if (!classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)) {
			invalidAccountStatusCheck(tempTxDto, tempAccountInfoDto);
		} else if (homeClassMismatchCheck(tempTxDto)) {
			classifyBasedOnTxInfo(tempTxDto);
		} else if (classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(tempAccountInfoDto)) {
			setTransactionTypeForSuspendStatus(tempTxDto);
		} else if (QatpClassificationConstant.ACCOUNT_STATUS_RVKW.equals(tempAccountInfoDto.getActStatus())) {
			checkForRVKW(tempTxDto);
		}  else if (classificationBusinessRuleHelper.checkZeroFinStatus(tempAccountInfoDto)) {
			checkRebillConditionAndHomeTx(tempTxDto, tempAccountInfoDto);
		} else if (!classificationBusinessRuleHelper.checkZeroFinStatus(tempAccountInfoDto)) {
			checkHealthyFinStatus(tempTxDto);
		
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);

	}

	private void checkHealthyFinStatus(TransactionDto tempTxDto) {
		
		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.T);
		tempTxDto.setTxStatus("501");
		
		String aetFlag = tempTxDto.getAetFlag();
		if (aetFlag != null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		} else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
		}
		logger.info(
				"Tx Type V and Tx Sub Type T based on  zero fin status and rebill cash check validation for lane Tx Id:{}",
				tempTxDto.getLaneTxId());
	}

	private void checkForRVKF(TransactionDto tempTxDto) {

		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.F);
		
		String aetFlag=tempTxDto.getAetFlag();
		if(aetFlag!=null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		}else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
		}
		logger.info("Tx Type V and Tx Sub Type F based on revoke account status for lane Tx Id:{}",
				tempTxDto.getLaneTxId());
		
	}

	private void checkForRVKW(TransactionDto tempTxDto) {
		
		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.Y);
		
		String aetFlag=tempTxDto.getAetFlag();
		if(aetFlag!=null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		}else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
		}
		logger.info("Tx Type V and Tx Sub Type Y based on revoke warning account status for lane Tx Id:{}",
				tempTxDto.getLaneTxId());
		if (tempTxDto.getPlazaAgencyId() == 1 && tempTxDto.getAccAgencyId() == 1) {
			tempTxDto.setReserved("X");
		}
		
	}

	private void invalidDeviceCheck(TransactionDto tempTxDto) {
		
		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.F);
		tempTxDto.setIsViolation("1");

		String aetFlag = tempTxDto.getAetFlag();
		if (aetFlag != null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		} else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
		}
		logger.info(
				"Tx Type V and Tx Sub Type F based on invalid active device status validation for lane Tx Id:{}",
				tempTxDto.getLaneTxId());
	}

	private void setTransactionTypeForSuspendStatus(TransactionDto tempTxDto) {

		tempTxDto.setTxType(QatpClassificationConstant.V);
		tempTxDto.setTxSubType(QatpClassificationConstant.Y);

		String aetFlag = tempTxDto.getAetFlag();
		if (aetFlag != null && aetFlag.equals("Y")) {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
		} else {
			tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
		}
		logger.info(
				"Tx Type V and Tx Sub Type Y based on valid suspended postpaid status validation  for lane Tx Id:{}",
				tempTxDto.getLaneTxId());

	}

	/**
	 * Process Transaction for toll system B,C,P,T
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto processTollSystem(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		if (classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto) && !tempTxDto.isSpeedViolation()) {
			if (!classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)) {
				// Need to check active status value
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.F);
				tempTxDto.setIsViolation("1");
				
				String aetFlag=tempTxDto.getAetFlag();
				if(aetFlag!=null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
				}else {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
				}
				logger.info(
						"Tx Type V and Tx Sub Type F based on invalid active device status validation for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				return accountInfoService.checkCompanionTag(tempTxDto, tempAccountInfoDto);
			} else if (!classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)) {
				invalidAccountStatusCheck(tempTxDto, tempAccountInfoDto);
			} else if (homeClassMismatchCheck(tempTxDto)) {
				classifyBasedOnTxInfo(tempTxDto);
			} else if (classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(tempAccountInfoDto)) {
				tempTxDto.setTxType(QatpClassificationConstant.V);
				tempTxDto.setTxSubType(QatpClassificationConstant.Y);
				
				String aetFlag=tempTxDto.getAetFlag();
				if(aetFlag!=null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
				}else {
					tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
				}
				logger.info(
						"Tx Type V and Tx Sub Type Y based on valid suspended postpaid status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
			} else {
				checkDeviceFinAccountStatus(tempTxDto, tempAccountInfoDto);
			}
		}
		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Classify home tx based on tag class and vehicle class
	 * 
	 * @param TransactionDto
	 */
	private void classifyBasedOnTxInfo(TransactionDto tempTxDto) {
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
					"Tx Type V and Tx Sub Type G based on home class Mismatch of valid veh and tag class  for lane Tx Id:{}",
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
					"Tx Type V and Tx Sub Type C based on home class Mismatch of invalid veh and tag class  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
		}
	}

	/**
	 * Set tx type and tx sub type based on account status
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 */
	private void invalidAccountStatusCheck(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.Y);
			
			String aetFlag=tempTxDto.getAetFlag();
			if(aetFlag!=null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			}else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);	
			}
			logger.info(
					"Tx Type V and Tx Sub Type Y based on invalid active account status validation  for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
		
	}

	/**
	 * Check Device, Fin and Account Status
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 */
	private void checkDeviceFinAccountStatus(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		// Query Q11
		
		//commenting the below query code for V and T type because of insufficient data in Query
		
//		AccountPlanDetail accountPlanDetail = accountInfoDao.getDeviceSpecialPlan(tempTxDto.getEtcAccountId(),
//				tempTxDto.getPlateType(), tempTxDto.getPlazaAgencyId(), tempTxDto.getDeviceNo(), tempTxDto.getTxDate());
//		if (accountPlanDetail != null && accountPlanDetail.getPlanType() != null) {
//			tempTxDto.setTxType(QatpClassificationConstant.V);
//			tempTxDto.setTxSubType(QatpClassificationConstant.T);
//			logger.info("Tx Type V and Tx Sub Type T based on  account plan detail for lane Tx Id:{}",
//					tempTxDto.getLaneTxId());
//		} else
			if (classificationBusinessRuleHelper.checkZeroFinStatus(tempAccountInfoDto)) {
			checkRebillConditionAndHomeTx(tempTxDto, tempAccountInfoDto);
		} 

	}

	/**
	 * Validate the Rebill Condition
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 */
	private void checkRebillConditionAndHomeTx(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		if (classificationBusinessRuleHelper.checkRebillCashCheckCondition(tempAccountInfoDto)) {// rebill type cash/check
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.Y);

			String aetFlag = tempTxDto.getAetFlag();
			if (aetFlag != null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			} else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
			}
			logger.info(
					"Tx Type V and Tx Sub Type T based on  zero fin status and rebill cash check validation for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
		} else {
			tempTxDto.setTxType(QatpClassificationConstant.V);
			tempTxDto.setTxSubType(QatpClassificationConstant.T);
			tempTxDto.setTxStatus("2");
			
			String aetFlag = tempTxDto.getAetFlag();
			if (aetFlag != null && aetFlag.equals("Y")) {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
			} else {
				tempTxDto.setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_VIOLATION);
			}
			logger.info(
					"Tx Type V and Tx Sub Type T based on  zero fin status and rebill cash check validation for lane Tx Id:{}",
					tempTxDto.getLaneTxId());
		}
	}

	/**
	 * Set Home Tx on passing all the validation
	 * 
	 * @param tempTxDto
	 */
	private void setHomeTxType(TransactionDto tempTxDto) {
		tempTxDto.setTxType(QatpClassificationConstant.E);
		tempTxDto.setTxSubType(QatpClassificationConstant.Z);
		logger.info("Tx Type E and Tx Sub Type Z based on all valid validation for lane Tx Id:{}",
				tempTxDto.getLaneTxId());
	}

	/**
	 * Home transaction class mismatch check
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	private boolean homeClassMismatchCheck(TransactionDto tempTxDto) {
		if (tempTxDto.getTransactionInfo() != null && tempTxDto.getTransactionInfo().length() > 3) {
			Long tempEtcAccountId = tempTxDto.getEtcAccountId() != null ? tempTxDto.getEtcAccountId() : 0L;
			char[] txInfoArray = tempTxDto.getTransactionInfo().toCharArray();
			return tempTxDto.getReciprocityTrx() != null
					&& QatpClassificationConstant.F.equalsIgnoreCase(tempTxDto.getReciprocityTrx())
					&& QatpClassificationConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[0]))
					&& QatpClassificationConstant.N.equalsIgnoreCase(String.valueOf(txInfoArray[1]))
					&& QatpClassificationConstant.Y.equalsIgnoreCase(String.valueOf(txInfoArray[2]))
					&& QatpClassificationConstant.ZERO.equalsIgnoreCase(String.valueOf(txInfoArray[3]))
					&& tempEtcAccountId > 0;
		}
		return false;
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
}


