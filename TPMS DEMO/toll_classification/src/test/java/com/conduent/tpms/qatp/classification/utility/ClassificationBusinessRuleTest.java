package com.conduent.tpms.qatp.classification.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Test Cases for class ClassificationBusinessRule
 *
 */
@ExtendWith(MockitoExtension.class)
class ClassificationBusinessRuleTest {

	@InjectMocks
	private ClassificationBusinessRuleHelper classificationBusinessRuleHelper;

	@Test
	void TestCheckDeviceAndTollSystem() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTollSystemType("B");
		txDto.setDeviceNo("");
		assertEquals(true, classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto));

		txDto.setTollSystemType("B");
		txDto.setDeviceNo("***********");
		assertEquals(true, classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto));

		txDto.setTollSystemType("B");
		txDto.setDeviceNo("****");
		assertEquals(false, classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto));
	}

	@Test
	void TestCheckTollSystemType() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTollSystemType("B");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystem(txDto));

		txDto.setTollSystemType("C");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystem(txDto));

		txDto.setTollSystemType("P");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystem(txDto));

		txDto.setTollSystemType("T");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystem(txDto));

		txDto.setTollSystemType("F");
		assertEquals(false, classificationBusinessRuleHelper.checkTollSystem(txDto));
	}

	@Test
	void TestCheckTollSystemTypeForEAndX() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTollSystemType("E");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystemTypeForEAndX(txDto));

		txDto.setTollSystemType("X");
		assertEquals(true, classificationBusinessRuleHelper.checkTollSystemTypeForEAndX(txDto));

		txDto.setTollSystemType("F");
		assertEquals(false, classificationBusinessRuleHelper.checkTollSystemTypeForEAndX(txDto));
	}

	@Test
	void TestCheckHomeDevTxTypeAndDeviceStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setHomeAgencyDev(1);
		txDto.setTxType("V");
		accDTo.setDeviceStatus(3);

		assertEquals(true, classificationBusinessRuleHelper.checkHomeDevTxTypeAndDeviceStatus(txDto, accDTo));

		txDto.setTxType("X");
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDevTxTypeAndDeviceStatus(txDto, accDTo));

		txDto.setTxType("V");
		accDTo.setDeviceStatus(2);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDevTxTypeAndDeviceStatus(txDto, accDTo));

		accDTo.setHomeAgencyDev(2);
		txDto.setTxType("V");
		accDTo.setDeviceStatus(3);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDevTxTypeAndDeviceStatus(txDto, accDTo));
	}

	@Test
	void TestCheckIAGStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setIagStatus(3);

		assertEquals(true, classificationBusinessRuleHelper.checkIAGStatus(accDTo));

		accDTo.setIagStatus(4);
		assertEquals(true, classificationBusinessRuleHelper.checkIAGStatus(accDTo));

		accDTo.setIagStatus(5);
		assertEquals(false, classificationBusinessRuleHelper.checkIAGStatus(accDTo));
	}

	@Test
	void TestCheckAccountStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setActStatus(3);

		assertEquals(true, classificationBusinessRuleHelper.checkAccountStatus(accDTo));

		accDTo.setActStatus(2);
		assertEquals(false, classificationBusinessRuleHelper.checkAccountStatus(accDTo));
	}

	@Test
	void TestCheckHomeDev() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setHomeAgencyDev(1);

		assertEquals(true, classificationBusinessRuleHelper.checkHomeDev(accDTo));

		accDTo.setHomeAgencyDev(2);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDev(accDTo));

	}

	@Test
	void TestCheckActiveDeviceStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setDeviceStatus(3);
		assertEquals(true, classificationBusinessRuleHelper.checkActiveDeviceStatus(accDTo));

		accDTo.setDeviceStatus(2);
		assertEquals(false, classificationBusinessRuleHelper.checkActiveDeviceStatus(accDTo));

	}

	@Test
	void TestCheckActiveAccountStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setActStatus(3);
		assertEquals(true, classificationBusinessRuleHelper.checkActiveAccountStatus(accDTo));

		accDTo.setActStatus(2);
		assertEquals(false, classificationBusinessRuleHelper.checkActiveAccountStatus(accDTo));

	}

	@Test
	void TestCheckNonZeroFinStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setFinStatus(1);
		assertEquals(true, classificationBusinessRuleHelper.checkNonZeroFinStatus(accDTo));

		accDTo.setFinStatus(0);
		assertEquals(false, classificationBusinessRuleHelper.checkNonZeroFinStatus(accDTo));

	}

	@Test
	void TestCheckSpeedStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setSpeedStatus(2);
		assertEquals(true, classificationBusinessRuleHelper.checkSpeedStatus(accDTo));

		accDTo.setSpeedStatus(6);
		assertEquals(true, classificationBusinessRuleHelper.checkSpeedStatus(accDTo));

		accDTo.setSpeedStatus(7);
		assertEquals(true, classificationBusinessRuleHelper.checkSpeedStatus(accDTo));

		accDTo.setSpeedStatus(8);
		assertEquals(false, classificationBusinessRuleHelper.checkSpeedStatus(accDTo));

	}

	@Test
	void TestCheckSpeedValidation() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setSpeedLimit(10);
		txDto.setVehicleSpeed(20);
		assertEquals(true, classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDTo));

		accDTo.setSpeedLimit(20);
		txDto.setVehicleSpeed(10);
		assertEquals(false, classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDTo));

		accDTo.setSpeedLimit(20);
		txDto.setVehicleSpeed(null);
		assertEquals(false, classificationBusinessRuleHelper.checkSpeedValidation(txDto, accDTo));

	}

	@Test
	void TestCheckSpeedThresholdValidation() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setSpeedLimit(10);
		txDto.setVehicleSpeed(20);
		assertEquals(true, classificationBusinessRuleHelper.checkSpeedThresholdValidation(txDto, accDTo));

		accDTo.setSpeedLimit(20);
		txDto.setVehicleSpeed(10);
		assertEquals(false, classificationBusinessRuleHelper.checkSpeedThresholdValidation(txDto, accDTo));

	}

	@Test
	void TestCheckSuspendedPostPaidStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setPostPaidStatus(2);
		assertEquals(true, classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDTo));

		accDTo.setPostPaidStatus(3);
		assertEquals(false, classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDTo));
	}

	@Test
	void TestCheckZeroFinStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setFinStatus(3);
		assertEquals(true, classificationBusinessRuleHelper.checkZeroFinStatus(accDTo));

		accDTo.setFinStatus(2);
		assertEquals(false, classificationBusinessRuleHelper.checkZeroFinStatus(accDTo));
	}

	@Test
	void TestCheckHomeDeviceAndDeviceAndAccountStatus() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setHomeAgencyDev(1);
		accDTo.setDeviceStatus(3);
		accDTo.setActStatus(5);
		assertEquals(true, classificationBusinessRuleHelper.checkHomeDeviceAndDeviceAndAccountStatus(accDTo));

		accDTo.setHomeAgencyDev(2);
		accDTo.setDeviceStatus(3);
		accDTo.setActStatus(5);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDeviceAndDeviceAndAccountStatus(accDTo));

		accDTo.setHomeAgencyDev(1);
		accDTo.setDeviceStatus(2);
		accDTo.setActStatus(5);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDeviceAndDeviceAndAccountStatus(accDTo));

		accDTo.setHomeAgencyDev(1);
		accDTo.setDeviceStatus(3);
		accDTo.setActStatus(4);
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDeviceAndDeviceAndAccountStatus(accDTo));
	}

	@Test
	void TestCheckRebillCondition() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setRebillType(2);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCondition(accDTo));

		accDTo.setRebillType(4);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCondition(accDTo));

		accDTo.setRebillType(7);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCondition(accDTo));

		accDTo.setRebillType(8);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCondition(accDTo));

		accDTo.setRebillType(9);
		assertEquals(false, classificationBusinessRuleHelper.checkRebillCondition(accDTo));
	}

	@Test
	void TestCheckHomeDevTxTypeAndViolationStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setHomeAgencyDev(1);
		txDto.setTxType("V");

		assertEquals(true, classificationBusinessRuleHelper.checkHomeDevTxTypeAndViolationStatus(txDto, accDTo));

		txDto.setTxType("X");
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDevTxTypeAndViolationStatus(txDto, accDTo));

		accDTo.setHomeAgencyDev(2);
		txDto.setTxType("V");
		assertEquals(false, classificationBusinessRuleHelper.checkHomeDevTxTypeAndViolationStatus(txDto, accDTo));
	}

	@Test
	void TestCheckRebillCashCheckCondition() {
		AccountInfoDto accDTo = new AccountInfoDto();
		accDTo.setRebillType(1);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDTo));

		accDTo.setRebillType(6);
		assertEquals(true, classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDTo));

		accDTo.setRebillType(7);
		assertEquals(false, classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDTo));

		accDTo.setRebillType(8);
		assertEquals(false, classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDTo));

		accDTo.setRebillType(9);
		assertEquals(false, classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDTo));
	}

	@Test
	void TestCheckTxInfoVehAndTagClass() {
		TransactionDto txDto = new TransactionDto();
		txDto.setActualClass(5);
		txDto.setTagClass(3);

		assertEquals(true, classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(txDto));

		txDto.setActualClass(3);
		assertEquals(false, classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(txDto));

		txDto.setTagClass(4);
		assertEquals(false, classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(txDto));
	}
}
