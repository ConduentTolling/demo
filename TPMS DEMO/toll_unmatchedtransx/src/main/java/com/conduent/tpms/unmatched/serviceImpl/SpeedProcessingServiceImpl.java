package com.conduent.tpms.unmatched.serviceImpl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.TSpeedThresholdDao;
import com.conduent.tpms.unmatched.dto.AccountInfoDto;
import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.model.SpeedThreshold;
import com.conduent.tpms.unmatched.service.SpeedProcessingService;
import com.conduent.tpms.unmatched.utility.UnmatchedBusinessRuleHelper;
import com.google.common.base.Stopwatch;


/**
 * Speed Process Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class SpeedProcessingServiceImpl implements SpeedProcessingService {

	@Autowired
	private TSpeedThresholdDao tSpeedThresholdDao;

	@Autowired
	private UnmatchedBusinessRuleHelper classificationBusinessRuleHelper;

	private static final Logger logger = LoggerFactory.getLogger(SpeedProcessingServiceImpl.class);

	
	/**
	 * Process Transaction for Speed Business Rule
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto processSpeedBusinessRule(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {

		if (classificationBusinessRuleHelper.checkHomeDev(tempAccountInfoDto)
				&& classificationBusinessRuleHelper.checkActiveDeviceStatus(tempAccountInfoDto)
				&& classificationBusinessRuleHelper.checkActiveAccountStatus(tempAccountInfoDto)
				&& classificationBusinessRuleHelper.checkNonZeroFinStatus(tempAccountInfoDto)) {
			if (classificationBusinessRuleHelper.checkSpeedStatus(tempAccountInfoDto)) {// Need to check Warning and
				logger.info("Tx Type V and Tx Sub Type S based on valid speed status validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId()); // revoke status
				tempTxDto.setTxType(UnmatchedConstant.V);
				tempTxDto.setTxSubType(UnmatchedConstant.S);
				tempTxDto.setSpeedViolation(true);
				
				String aetFlag=tempTxDto.getAetFlag();
				if(aetFlag!=null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
				}else {
					tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
				}
			} else if (classificationBusinessRuleHelper.checkSpeedValidation(tempTxDto, tempAccountInfoDto)) {
				logger.info("Tx Type V and Tx Sub Type S based on valid speed validation  for lane Tx Id:{}",
						tempTxDto.getLaneTxId());
				tempTxDto.setTxType(UnmatchedConstant.V);
				tempTxDto.setTxSubType(UnmatchedConstant.S);
				tempTxDto.setSpeedViolation(true);
				
				String aetFlag=tempTxDto.getAetFlag();
				if(aetFlag!=null && aetFlag.equals("Y")) {
					tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
				}else {
					tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
				}
			} else {
				checkEntryLaneSpeed(tempTxDto);
			}
		}

		return new TransactionDetailDto(tempTxDto, tempAccountInfoDto);
	}

	/**
	 * Speed Validation for entry lane
	 * 
	 * @param TransactionDto
	 */
	private void checkEntryLaneSpeed(TransactionDto tempTxDto) {
		if (tempTxDto.getEntryLaneId() != null && tempTxDto.getEntryVehicleSpeed() != null) {
			Stopwatch stopwatch = Stopwatch.createStarted();
			SpeedThreshold speedThreshold = tSpeedThresholdDao.getSpeedLimitForLane(
					tempTxDto.getPlazaAgencyId(), tempTxDto.getEntryLaneId(), tempTxDto.getAccountType());
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Time taken to select data from TPMS.T_SPEED_THRESHOLD Shrikant ==> {}ms", millis);
			if (speedThreshold != null && speedThreshold.getLowerSpeedThreshold() != null) {
				if (tempTxDto.getEntryVehicleSpeed() > speedThreshold.getLowerSpeedThreshold()) {
					logger.info(
							"Tx Type V and Tx Sub Type S based on valid speed validation of entry lane forlane Tx Id:{}",
							tempTxDto.getLaneTxId());
					tempTxDto.setTxType(UnmatchedConstant.V);
					tempTxDto.setTxSubType(UnmatchedConstant.S);
					tempTxDto.setSpeedViolation(true);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
					}
				}
			} else {
				if (tempTxDto.getEntryVehicleSpeed() > UnmatchedConstant.DEFAULT_SPEED) {
					logger.info(
							"Tx Type V and Tx Sub Type S based on valid speed validation of entry lane forlane Tx Id:{}",
							tempTxDto.getLaneTxId());
					tempTxDto.setTxType(UnmatchedConstant.V);
					tempTxDto.setTxSubType(UnmatchedConstant.S);
					tempTxDto.setSpeedViolation(true);
					
					String aetFlag=tempTxDto.getAetFlag();
					if(aetFlag!=null && aetFlag.equals("Y")) {
						tempTxDto.setTollRevenueType(UnmatchedConstant.AET_VIOLATION_REVENUE_TYPE);
					}else {
						tempTxDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_VIOLATION);	
					}
				}

			}
		}
	}

}
