package com.conduent.tpms.qatp.classification.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.dao.TSpeedThresholdDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.SpeedThreshold;
import com.conduent.tpms.qatp.classification.utility.ClassificationBusinessRuleHelper;

/**
 * Test Class for Speed Processing Service
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
class SpeedProcessingServiceImplTest {

	@Mock
	private TSpeedThresholdDao tSpeedThresholdDao;

	@Mock
	private ClassificationBusinessRuleHelper classificationBusinessRuleHelper;

	@InjectMocks
	private SpeedProcessingServiceImpl speedProcessingServiceImpl;

	@Test
	void TestCheckSpeedStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		accDto.setHomeAgencyDev(1);
		accDto.setDeviceStatus(3);
		accDto.setActStatus(3);
		accDto.setFinStatus(1);
		accDto.setSpeedStatus(2);

		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkNonZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = speedProcessingServiceImpl.processSpeedBusinessRule(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("S", txDto.getTxSubType());
	}

	@Test
	void TestCheckSpeedValidation() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		accDto.setHomeAgencyDev(1);
		accDto.setDeviceStatus(3);
		accDto.setActStatus(3);
		accDto.setFinStatus(1);
		accDto.setSpeedStatus(2);

		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkNonZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = speedProcessingServiceImpl.processSpeedBusinessRule(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("S", txDto.getTxSubType());
	}

	@Test
	void TestCheckSpeedValidationBySpeedLimitForLane() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		txDto.setVehicleSpeed(20);
		txDto.setEntryLaneId(1);
		accDto.setHomeAgencyDev(1);
		accDto.setDeviceStatus(3);
		accDto.setActStatus(3);
		accDto.setFinStatus(1);
		accDto.setSpeedStatus(2);
		txDto.setEntryVehicleSpeed(20);

		SpeedThreshold speedThreshold = new SpeedThreshold();
		speedThreshold.setLowerSpeedThreshold(10);

		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkNonZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDto)).thenReturn(false);
		Mockito.when(tSpeedThresholdDao.getSpeedLimitForLane(txDto.getPlazaAgencyId(), txDto.getEntryLaneId(),
				txDto.getAccountType())).thenReturn(speedThreshold);
		TransactionDetailDto transactionDetailDto = speedProcessingServiceImpl.processSpeedBusinessRule(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("S", txDto.getTxSubType());
	}
	
	@Test
	void TestCheckSpeedValidationWidOutSpeedThreshold() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		txDto.setVehicleSpeed(20);
		txDto.setEntryLaneId(1);
		accDto.setHomeAgencyDev(1);
		accDto.setDeviceStatus(3);
		accDto.setActStatus(3);
		accDto.setFinStatus(1);
		accDto.setSpeedStatus(2);
		txDto.setEntryVehicleSpeed(6000);

		SpeedThreshold speedThreshold = new SpeedThreshold();
		speedThreshold.setLowerSpeedThreshold(10);

		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkNonZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDto)).thenReturn(false);
		Mockito.when(tSpeedThresholdDao.getSpeedLimitForLane(txDto.getPlazaAgencyId(), txDto.getEntryLaneId(),
				txDto.getAccountType())).thenReturn(null);
		TransactionDetailDto transactionDetailDto = speedProcessingServiceImpl.processSpeedBusinessRule(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("S", txDto.getTxSubType());
	}

}
