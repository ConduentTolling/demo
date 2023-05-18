package com.conduent.tpms.qatp.classification.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.utility.ClassificationBusinessRuleHelper;

/**
 * Test Class Transaction Classification Service
 * 
 * @author shrikantm3
 *
 */
@ExtendWith(MockitoExtension.class)
class TransactionClassificationServiceImplTest {

	@Mock
	private ClassificationBusinessRuleHelper classificationBusinessRuleHelper;

	@Mock
	private AccountInfoDao accountInfoDao;

	@Mock
	private AccountInfoService accountInfoService;

	@InjectMocks
	private TransactionClassificationServiceImpl transactionClassificationServiceImpl;

	@Test
	void TestCheckForHomeTransactionForInvalidDevice() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(0);
		txDto.setDeviceNo(null);
		txDto.setPlazaAgencyId(1);
		//Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(false);
		//Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(false);
		//Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("R", txDto.getTxType());
		assertEquals("T", txDto.getTxSubType());
	}
	
	@Test
	void TestCheckForHomeTransaction() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(0);
		txDto.setPlazaAgencyId(1);
		txDto.setDeviceNo("00426180130");
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("E", txDto.getTxType());
		assertEquals("Z", txDto.getTxSubType());
	}

	@Test
	void TestCheckForHomeTransactionInActiveDevice() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(0);
		txDto.setPlazaAgencyId(1);
		txDto.setDeviceNo("00426180130");
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(false);
		// Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("E", txDto.getTxType());
		assertEquals("Z", txDto.getTxSubType());
		//assertEquals("27", txDto.getTxStatus());
	}
	

	@Test
	void TestCheckForHomeTransactionInActiveAccountStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(0);
		txDto.setPlazaAgencyId(1);
		accDto.setActStatus(2);
		txDto.setDeviceNo("00426180130");
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(!classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(false);
		Mockito.when(!classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("E", txDto.getTxType());
		assertEquals("Z", txDto.getTxSubType());
		//assertEquals("34", txDto.getTxStatus());
	}

	// @Test not working....
	void TestCheckForAwayTransaction() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		TransactionDetailDto temptransactionDetailDto = new TransactionDetailDto(txDto, accDto);
		txDto.setEtcViolObserved(0);

		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(accountInfoService.checkCompanionTag(txDto, accDto)).thenReturn(temptransactionDetailDto);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		// assertEquals("O", txDto.getTxType());
		// assertEquals("T", txDto.getTxSubType());
	}
	
	@Test
	void TestCheckForRVKF() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		accDto.setActStatus(6);
		// txDto.setPlazaAgencyId(1);
		// txDto.setAccAgencyId(1);
		// accDto.setActStatus(5);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		//Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		//Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
		// Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("F", txDto.getTxSubType());
	}

	@Test
	void TestCheckForViolationInvalidAccountCheck() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}

	@Test
	void TestCheckForViolationCheckPostPaidStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}

	@Test
	void TestCheckForRVKW() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		txDto.setPlazaAgencyId(1);
		txDto.setAccAgencyId(1);
		accDto.setActStatus(5);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
		// Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}



	@Test
	void TestCheckForViolationCheckZeroFinStatusWithCashCheck() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDto)).thenReturn(true);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}

	@Test
	void TestCheckForViolationCheckZeroFinStatusWithoutCashCheck() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("T", txDto.getTxSubType());
	}

	@Test
	void TestCheckForViolationCheckHealthyFinStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(1);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("T", txDto.getTxSubType());
	}

	// below test case not in used..
//	@Test
	void TestCheckForAway() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		txDto.setEtcViolObserved(0);
		accDto.setHomeAgencyDev(1);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("O", txDto.getTxType());
		assertEquals("T", txDto.getTxSubType());
	}

//	@Test
	void TestCheckTollSystemTypeForEAndX() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystemTypeForEAndX(txDto)).thenReturn(true);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("U", txDto.getTxType());
		assertEquals("E", txDto.getTxSubType());
	}

//	@Test
	void TestCheckHomeDevTxTypeAndDeviceStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		TransactionDetailDto temptransactionDetailDto = new TransactionDetailDto(txDto, accDto);

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(false);

		Mockito.when(accountInfoService.checkCompanionTag(txDto, accDto)).thenReturn(temptransactionDetailDto);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("F", txDto.getTxSubType());
	}

//	@Test
	void TestCheckInActiveAccountStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}

//	@Test
	void TestCheckSuspendedPostPaidStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(true);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
	}

//	@Test
//	void TestGetDeviceSpecialPlan() {
//		TransactionDto txDto = new TransactionDto();
//		txDto.setTxDate("2020-05-12");
//		AccountInfoDto accDto = new AccountInfoDto();
//		AccountPlanDetail accountPlanDetail = new AccountPlanDetail();
//		accountPlanDetail.setPlanType(1);
//
//		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
//		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
//		Mockito.when(accountInfoDao.getDeviceSpecialPlan(txDto.getEtcAccountId(), txDto.getPlateType(),
//				txDto.getPlazaAgencyId(), txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(accountPlanDetail);
//
//		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl.processTransactionClassification(txDto,
//				accDto);
//		txDto = transactionDetailDto.getTransactionDto();
//		assertEquals("V", txDto.getTxType());
//		assertEquals("T", txDto.getTxSubType());
//	}

//	@Test
//	void TestcheckZeroFinStatusAndRebillCashCheckCondition() {
//		TransactionDto txDto = new TransactionDto();
//		txDto.setTxDate("2020-05-12");
//		AccountInfoDto accDto = new AccountInfoDto();
//		AccountPlanDetail accountPlanDetail = null;
//
//		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
//		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
//		Mockito.when(accountInfoDao.getDeviceSpecialPlan(txDto.getEtcAccountId(), txDto.getPlateType(),
//				txDto.getPlazaAgencyId(), txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(accountPlanDetail);
//		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDto)).thenReturn(true);
//
//		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl.processTransactionClassification(txDto,
//				accDto);
//		txDto = transactionDetailDto.getTransactionDto();
//		assertEquals("V", txDto.getTxType());
//		assertEquals("T", txDto.getTxSubType());
//	}

//	@Test
//	void TestCheckZeroFinStatusAndinvalidRebillCondition() {
//		TransactionDto txDto = new TransactionDto();
//		txDto.setTxDate("2020-05-12");
//		txDto.setTxType("V");
//		AccountInfoDto accDto = new AccountInfoDto();
//		AccountPlanDetail accountPlanDetail = null;
//
//		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
//		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
//		Mockito.when(accountInfoDao.getDeviceSpecialPlan(txDto.getEtcAccountId(), txDto.getPlateType(),
//				txDto.getPlazaAgencyId(), txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(accountPlanDetail);
//		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkRebillCondition(accDto)).thenReturn(false);
//
//		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl.processTransactionClassification(txDto,
//				accDto);
//		txDto = transactionDetailDto.getTransactionDto();
//		assertEquals("V", txDto.getTxType());
//		assertEquals("F", txDto.getTxSubType());
//	}

//	@Test
//	void TestCheckZeroFinStatusAndrebillCondition() {
//		TransactionDto txDto = new TransactionDto();
//		txDto.setTxDate("2020-05-12");
//		txDto.setTxType("V");
//		AccountInfoDto accDto = new AccountInfoDto();
//		AccountPlanDetail accountPlanDetail = null;
//
//		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
//		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
//		Mockito.when(accountInfoDao.getDeviceSpecialPlan(txDto.getEtcAccountId(), txDto.getPlateType(),
//				txDto.getPlazaAgencyId(), txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(accountPlanDetail);
//		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkRebillCondition(accDto)).thenReturn(true);
//		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl.processTransactionClassification(txDto,
//				accDto);
//		txDto = transactionDetailDto.getTransactionDto();
//		assertEquals("V", txDto.getTxType());
//		assertEquals("T", txDto.getTxSubType());
//	}

//	@Test
	void TestHomeTxClassMismatchWidValidVehAndTagClass() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTransactionInfo("YNY0VN");
		txDto.setReciprocityTrx("F");
		txDto.setEtcAccountId(111L);
		AccountInfoDto accDto = new AccountInfoDto();

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(txDto)).thenReturn(true);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("G", txDto.getTxSubType());
	}

//	@Test
	void TestHomeTxClassMismatchWidInvalidVehAndTagClass() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTransactionInfo("YNY0VN");
		txDto.setReciprocityTrx("F");
		txDto.setEtcAccountId(111L);
		AccountInfoDto accDto = new AccountInfoDto();

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(txDto)).thenReturn(false);

		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("C", txDto.getTxSubType());
	}

//	@Test
	void TestCheckRevokeWarningAccountStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		accDto.setActStatus(5);
		txDto.setAccAgencyId(1);
		txDto.setPlazaAgencyId(1);

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("Y", txDto.getTxSubType());
		assertEquals("X", txDto.getReserved());
	}

//	@Test
	void TestCheckRevokeAccountStatus() {
		TransactionDto txDto = new TransactionDto();
		AccountInfoDto accDto = new AccountInfoDto();
		accDto.setActStatus(6);

		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(false);
		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl
				.processTransactionClassification(txDto, accDto);
		txDto = transactionDetailDto.getTransactionDto();
		assertEquals("V", txDto.getTxType());
		assertEquals("F", txDto.getTxSubType());
	}

//	@Test
//	void TestcheckHomeTxTypeCondition() {
//		TransactionDto txDto = new TransactionDto();
//		txDto.setTxDate("2020-05-12");
//		AccountInfoDto accDto = new AccountInfoDto();
//		AccountPlanDetail accountPlanDetail = null;
//
//		Mockito.when(classificationBusinessRuleHelper.checkDeviceAndTollSystem(txDto)).thenReturn(false);
//		Mockito.when(classificationBusinessRuleHelper.checkTollSystem(txDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkHomeDev(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveDeviceStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkActiveAccountStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkSuspendedPostPaidStatus(accDto)).thenReturn(false);
//		Mockito.when(accountInfoDao.getDeviceSpecialPlan(txDto.getEtcAccountId(), txDto.getPlateType(),
//				txDto.getPlazaAgencyId(), txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(accountPlanDetail);
//		Mockito.when(classificationBusinessRuleHelper.checkZeroFinStatus(accDto)).thenReturn(true);
//		Mockito.when(classificationBusinessRuleHelper.checkRebillCashCheckCondition(accDto)).thenReturn(false);
//
//		TransactionDetailDto transactionDetailDto = transactionClassificationServiceImpl.processTransactionClassification(txDto,
//				accDto);
//		txDto = transactionDetailDto.getTransactionDto();
//		assertEquals("E", txDto.getTxType());
//		assertEquals("Z", txDto.getTxSubType());
//	}
}
