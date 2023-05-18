package com.conduent.tpms.qatp.classification.service.impl;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.service.CommonNotificationService;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;
import com.conduent.tpms.qatp.classification.service.MessageReaderService;
import com.conduent.tpms.qatp.classification.service.TransactionClassificationService;

/**
 * Test Class for QATP classification service
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
class QatpClassificationServiceImplTest {

	@Mock
	private MessageReaderService messageReaderService;

	@Mock
	private TransactionClassificationService transactionClassificationService;

	@Mock
	private ComputeTollService computeTollService;

	@Mock
	private CommonNotificationService commonNotificationService;

	@Mock
	private AccountInfoService accountInfoService;
	
	@Mock
	private ComputeTollDao computeTollDao;

	@InjectMocks
	private QatpClassificationServiceImpl qatpClassificationServiceImpl;

	OffsetDateTime today = OffsetDateTime.parse("2022-02-14T10:15:30-05:00");
	
	//@Test
	void TestProcessTxMsg() {
		// CREATE
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setLaneTxId(828L);
		transactionDto.setTxExternRefNo("0000023509000076");
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setTollSystemType("E");
		transactionDto.setTollRevenueType(9);
		transactionDto.setEntryTxTimeStamp(today);
		transactionDto.setEntryPlazaId(603);
		transactionDto.setEntryLaneId(6963);
		transactionDto.setEntryDataSource("T");
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setPlazaId(72);
		transactionDto.setLaneId(18003);
		transactionDto.setExternPlazaId("09A");
		transactionDto.setExternLaneId("16S");
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		transactionDto.setLaneMode(9);
		transactionDto.setLaneSn(28753L);
		transactionDto.setDeviceNo("00411571485");
		transactionDto.setTagAxles(2);
		transactionDto.setActualAxles(22);
		transactionDto.setExtraAxles(2);
		transactionDto.setPlateState("**");
		transactionDto.setPlateNumber("**********");
		transactionDto.setPlateType(0);
		transactionDto.setPlateCountry("***");
		transactionDto.setReadPerf(37);
		transactionDto.setWritePerf(0);
		transactionDto.setProgStat("0");
		transactionDto.setCollectorNumber(78999L);
		transactionDto.setImageCaptured("F");
		// transactionDto.setReciprocityTrx("F");
		transactionDto.setIsViolation("1");
		transactionDto.setIsLprEnabled("0");
		transactionDto.setExtFileId(8729L);
		transactionDto.setReceivedDate("2021-04-13");
		// transactionDto.setAccountType(1);
		// transactionDto.setAccAgencyId(1);
		// transactionDto.setEtcAccountId(207L);
		transactionDto.setDstFlag("*");
		transactionDto.setUpdateTs("2021-04-22 06:33:43.998");
		transactionDto.setCashFareAmount(0d);

		TransactionDto accInfotransactionDto = new TransactionDto();
		accInfotransactionDto.setLaneTxId(828L);
		accInfotransactionDto.setTxExternRefNo("0000023509000076");
		accInfotransactionDto.setTxType("V");
		accInfotransactionDto.setTxSubType("F");
		accInfotransactionDto.setTollSystemType("E");
		accInfotransactionDto.setTollRevenueType(9);
		accInfotransactionDto.setEntryTxTimeStamp(today);
		accInfotransactionDto.setEntryPlazaId(603);
		accInfotransactionDto.setEntryLaneId(6963);
		accInfotransactionDto.setEntryDataSource("T");
		accInfotransactionDto.setPlazaAgencyId(1);
		accInfotransactionDto.setPlazaId(72);
		accInfotransactionDto.setLaneId(18003);
		accInfotransactionDto.setExternPlazaId("09A");
		accInfotransactionDto.setExternLaneId("16S");
		accInfotransactionDto.setTxDate("2021-04-02");
		accInfotransactionDto.setTxTimeStamp(today);
		accInfotransactionDto.setLaneMode(9);
		accInfotransactionDto.setLaneSn(28753L);
		accInfotransactionDto.setDeviceNo("00411571485");
		accInfotransactionDto.setTagAxles(2);
		accInfotransactionDto.setActualAxles(22);
		accInfotransactionDto.setExtraAxles(2);
		accInfotransactionDto.setPlateState("**");
		accInfotransactionDto.setPlateNumber("**********");
		accInfotransactionDto.setPlateType(0);
		accInfotransactionDto.setPlateCountry("***");
		accInfotransactionDto.setReadPerf(37);
		accInfotransactionDto.setWritePerf(0);
		accInfotransactionDto.setProgStat("0");
		accInfotransactionDto.setCollectorNumber(78999L);
		accInfotransactionDto.setImageCaptured("F");
		accInfotransactionDto.setReciprocityTrx("F");
		accInfotransactionDto.setIsViolation("1");
		accInfotransactionDto.setIsLprEnabled("0");
		accInfotransactionDto.setExtFileId(8729L);
		accInfotransactionDto.setReceivedDate("2021-04-13");
		accInfotransactionDto.setAccountType(1);
		accInfotransactionDto.setAccAgencyId(1);
		accInfotransactionDto.setEtcAccountId(207L);
		accInfotransactionDto.setDstFlag("*");
		accInfotransactionDto.setUpdateTs("2021-04-22 06:33:43.998");

		AccountInfoDto accountInfoDto = new AccountInfoDto();
		accountInfoDto.setAcctBalance(163.0);
		accountInfoDto.setAcctType(1);
		accountInfoDto.setActStatus(3);
		accountInfoDto.setBadSubAcct(0);
		accountInfoDto.setCommercialId(0);
		accountInfoDto.setCurrentBalance(163.0);
		accountInfoDto.setDeviceInternalNo(null);
		accountInfoDto.setDeviceStatus(3);
		accountInfoDto.setErrorCode(0);
		accountInfoDto.setFinStatus(4);
		accountInfoDto.setHomeAgencyDev(1);
		accountInfoDto.setHomeIssuedDev("Y");
		accountInfoDto.setIagStatus(0);
		accountInfoDto.setInvalidAgencyPfx(0);
		accountInfoDto.setIsPrevalid("N");
		accountInfoDto.setIsUnregistered(0);
		accountInfoDto.setPlanCount(0);
		accountInfoDto.setPostPaidBalance(0.0);
		accountInfoDto.setPostPaidStatus(0);
		accountInfoDto.setPrePaidCount(0);
		accountInfoDto.setPrevalidAcct(0);
		accountInfoDto.setRebillType(4);
		accountInfoDto.setScheduledPricingFlag(0);
		accountInfoDto.setSpeedLimit(5000);
		accountInfoDto.setSpeedStatus(0);
		accountInfoDto.setStatus("Y");
		accountInfoDto.setTagFound(1);

		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(accInfotransactionDto);

		// List<TransactionDto> transactionDtoList = new ArrayList<TransactionDto>();
		// transactionDtoList.add(transactionDto);

		TransactionDto computeTollInfotransactionDto = new TransactionDto();
		computeTollInfotransactionDto.setLaneTxId(828L);
		computeTollInfotransactionDto.setTxExternRefNo("0000023509000076");
		computeTollInfotransactionDto.setTxType("V");
		computeTollInfotransactionDto.setTxSubType("F");
		computeTollInfotransactionDto.setTollSystemType("E");
		computeTollInfotransactionDto.setTollRevenueType(9);
		computeTollInfotransactionDto.setEntryTxTimeStamp(today);
		computeTollInfotransactionDto.setEntryPlazaId(603);
		computeTollInfotransactionDto.setEntryLaneId(6963);
		computeTollInfotransactionDto.setEntryDataSource("T");
		computeTollInfotransactionDto.setPlazaAgencyId(1);
		computeTollInfotransactionDto.setPlazaId(72);
		computeTollInfotransactionDto.setLaneId(18003);
		computeTollInfotransactionDto.setExternPlazaId("09A");
		computeTollInfotransactionDto.setExternLaneId("16S");
		computeTollInfotransactionDto.setTxDate("2021-04-02");
		computeTollInfotransactionDto.setTxTimeStamp(today);
		computeTollInfotransactionDto.setLaneMode(9);
		computeTollInfotransactionDto.setLaneSn(28753L);
		computeTollInfotransactionDto.setDeviceNo("00411571485");
		computeTollInfotransactionDto.setTagAxles(2);
		computeTollInfotransactionDto.setActualAxles(22);
		computeTollInfotransactionDto.setExtraAxles(2);
		computeTollInfotransactionDto.setPlateState("**");
		computeTollInfotransactionDto.setPlateNumber("**********");
		computeTollInfotransactionDto.setPlateType(0);
		computeTollInfotransactionDto.setPlateCountry("***");
		computeTollInfotransactionDto.setReadPerf(37);
		computeTollInfotransactionDto.setWritePerf(0);
		computeTollInfotransactionDto.setProgStat("0");
		computeTollInfotransactionDto.setCollectorNumber(78999L);
		computeTollInfotransactionDto.setImageCaptured("F");
		computeTollInfotransactionDto.setReciprocityTrx("F");
		computeTollInfotransactionDto.setIsViolation("1");
		computeTollInfotransactionDto.setIsLprEnabled("0");
		computeTollInfotransactionDto.setExtFileId(8729L);
		computeTollInfotransactionDto.setReceivedDate("2021-04-13");
		computeTollInfotransactionDto.setAccountType(1);
		computeTollInfotransactionDto.setAccAgencyId(1);
		computeTollInfotransactionDto.setEtcAccountId(207L);
		computeTollInfotransactionDto.setDstFlag("*");
		computeTollInfotransactionDto.setUpdateTs("2021-04-22 06:33:43.998");
		computeTollInfotransactionDto.setEtcFareAmount(20.00);
		computeTollInfotransactionDto.setCashFareAmount(0d);
		
		Integer value = null;

		Mockito.when(accountInfoService.getAccountInformation(transactionDto)).thenReturn(transactionDetailDto);

		Mockito.when(transactionClassificationService.processTransactionClassification(
				transactionDetailDto.getTransactionDto(), transactionDetailDto.getAccountInfoDto()))
				.thenReturn(transactionDetailDto);

		Mockito.when(computeTollService.computeToll(transactionDetailDto.getTransactionDto(),
				transactionDetailDto.getAccountInfoDto())).thenReturn(computeTollInfotransactionDto);
		
		Mockito.when(computeTollDao.NY12Plan(computeTollInfotransactionDto.getDeviceNo(), computeTollInfotransactionDto.getTxDate())).thenReturn(value);

		qatpClassificationServiceImpl.processTxMsg(transactionDto);

		Mockito.verify(accountInfoService, Mockito.times(1)).getAccountInformation(transactionDto);

	}

}
