package com.conduent.tpms.qatp.classification.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.dao.AgencyTxPendingDao;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.NY12Dto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.service.CommonClassificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.classification.utility.TxStatusHelper;

@ExtendWith(MockitoExtension.class)
public class CommonNotificationServiceImplTest {

	@Mock
	ConfigVariable configVariable;

	@Mock
	private CommonClassificationService commonClassificationService;
	
	@Mock
	private TUnmatchedTx unMatchedTrx;

	@Mock
	private ComputeTollDao computeTollDao;

	@Mock
	private TxStatusHelper txStatusHelper;

	@Mock
	private AgencyTxPendingDao agencyTxPendingDao;
	@Mock
	private AsyncProcessCall asyncProcessCall;

	@InjectMocks
	private CommonNotificationServiceImpl commonNotificationServiceImpl;
	
	@Mock
	TimeZoneConv timeZoneConv;
	OffsetDateTime today = OffsetDateTime.parse("2022-02-14T10:15:30-05:00");
	@Test
	void testPushMessageForHome() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("E");
		txDto.setTxSubType("Z");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setDeviceNo("123456789");
		txDto.setPlazaAgencyId(0);
		//commented for performance testing
		//Mockito.when(configVariable.getHomeAgencyStreamId()).thenReturn("1234");
		Mockito.when(txStatusHelper.getTxStatus("E", "Z", null, 12L)).thenReturn(71);
		//commented for performance testing
		commonNotificationServiceImpl.pushMessage(txDto);
		
		Mockito.verify(commonClassificationService, Mockito.times(1))
				.insertToHomeEtcQueue(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
//		Mockito.verify(asyncProcessCall, Mockito.times(2))
//				.pushMessage(Mockito.any(CommonClassificationDto.class), Mockito.any(String.class));

	}
	
	@Test
	void testPushMessageForNY12() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("E");
		txDto.setTxSubType("Z");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setPlazaId(21);
		txDto.setDeviceNo("00411571485");
		txDto.setPlazaAgencyId(1);
		
		Mockito.when(txStatusHelper.getTxStatus("E", "Z", null, 12L)).thenReturn(71);
		Mockito.when(computeTollDao.NY12Plan(txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(1);
		commonNotificationServiceImpl.pushMessage(txDto);
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	
	@Test
	void testPushMessageFor25APlaza() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("E");
		txDto.setTxSubType("Z");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setPlazaId(21);
		txDto.setEntryPlazaId(22);
		txDto.setDeviceNo("00411571485");
		txDto.setEtcAccountId(2L);
		txDto.setPlazaAgencyId(1);
		
		
		//Mockito.when(txStatusHelper.getTxStatus("E", "Z", null, 12L)).thenReturn(71);
		Mockito.when(computeTollDao.NY12Plan(txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(anyInt());
		commonNotificationServiceImpl.pushMessage(txDto);
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}

	
	
	@Test
	void testPushMessageForNY12ForAway() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("O");
		txDto.setTxSubType("T");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setPlazaId(21);
		txDto.setDeviceNo("00411571485");
		
		Mockito.when(txStatusHelper.getTxStatus("O", "T", null, 12L)).thenReturn(71);
		Mockito.when(computeTollDao.NY12Plan(txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(1);
		commonNotificationServiceImpl.pushMessage(txDto);
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	
	@Test
	void testPushMessageFor25APlazaForAway() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("O");
		txDto.setTxSubType("T");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setPlazaId(21);
		txDto.setEntryPlazaId(22);
		txDto.setDeviceNo("00411571485");
		txDto.setEtcAccountId(2L);
		
		
	//	Mockito.when(txStatusHelper.getTxStatus("O", "T", null, 12L)).thenReturn(71);
		Mockito.when(computeTollDao.NY12Plan(txDto.getDeviceNo(), txDto.getTxDate())).thenReturn(anyInt());
		commonNotificationServiceImpl.pushMessage(txDto);
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	
	@Test
	void testPushMessageForAway() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("O");
		txDto.setTxSubType("T");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setTxTimeStamp(today);
		txDto.setCashFareAmount(12.2);
		txDto.setDeviceNo("123456789");
		Mockito.when(txStatusHelper.getTxStatus("O", "T", null, 12L)).thenReturn(71);
		commonNotificationServiceImpl.pushMessage(txDto);
		
		
		Mockito.verify(agencyTxPendingDao, Mockito.times(1)).insert(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	
	//@Test
	void testPushMessageForAway1() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("O");
		txDto.setTxSubType("T");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");

		Mockito.when(txStatusHelper.getTxStatus("O", "T", null, 12L)).thenReturn(83);

		commonNotificationServiceImpl.pushMessage(txDto);

		
		Mockito.verify(agencyTxPendingDao, Mockito.times(1)).insert(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));

	}

	@Test
	void testRejectRecord() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("R");
		txDto.setTxSubType("D");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setEtcValidStatus(1);

		Mockito.when(txStatusHelper.getTxStatus("R", "D", null, 12L)).thenReturn(51);

		commonNotificationServiceImpl.pushMessage(txDto);

		
		Mockito.verify(commonClassificationService, Mockito.times(1))
		.insertToHomeEtcQueue(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));

	}
	
	@Test
	void testDiscardRecord() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("D");
		txDto.setTxSubType("E");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setEtcValidStatus(1);
		
		Mockito.when(txStatusHelper.getTxStatus("D", "E", null, 12L)).thenReturn(67);

		commonNotificationServiceImpl.pushMessage(txDto);

		
		Mockito.verify(commonClassificationService, Mockito.times(1))
		.insertToHomeEtcQueue(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));

	}

	@Test
	void testPushMessageForUnmatchedEntry() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("U");
		txDto.setTxSubType("E");
		txDto.setTollSystemType("E");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setEtcValidStatus(1);
		
		Mockito.when(txStatusHelper.getTxStatus("U", "E", null, 12L)).thenReturn(63);

		commonNotificationServiceImpl.pushMessage(txDto);

		Mockito.verify(commonClassificationService, Mockito.times(1))
		.insertToTUnmatchedEntryTx(Mockito.any(TUnmatchedTx.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	
	@Test
	void testPushMessageForUnmatchedExit() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("U");
		txDto.setTxSubType("E");
		txDto.setTollSystemType("X");
		txDto.setLaneTxId(12L);
		txDto.setReceivedDate("2014-12-21");
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setEtcValidStatus(1);
		
		Mockito.when(txStatusHelper.getTxStatus("U", "E", null, 12L)).thenReturn(63);

		commonNotificationServiceImpl.pushMessage(txDto);

		Mockito.verify(commonClassificationService, Mockito.times(1))
		.insertToTUnmatchedExitTx(Mockito.any(TUnmatchedTx.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
	}
	@Test
	void testPushMessageForViolation() {
		TransactionDto txDto = new TransactionDto();
		txDto.setTxType("V");
		txDto.setTxSubType("F");
		txDto.setLaneTxId(12L);
		txDto.setRevenueDate("2014-12-21");//12-APR-22
		txDto.setReceivedDate("2014-12-21");
		txDto.setTxDate("2014-12-21");//12-APR-22
		txDto.setUpdateTs("2014-12-21");
		txDto.setEventTimestamp(timeZoneConv.currentTime());
		txDto.setEtcValidStatus(1);
		// commented for performance testing
		//Mockito.when(configVariable.getViolationStreamId()).thenReturn("1234");
		Mockito.when(txStatusHelper.getTxStatus("V", "F", null, 12L)).thenReturn(72);

		//commented for performance testing
		commonNotificationServiceImpl.pushMessage(txDto);

		Mockito.verify(commonClassificationService, Mockito.times(1))
				.insertToViolQueue(Mockito.any(CommonClassificationDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateTTransDetail(Mockito.any(TransactionDto.class));
		Mockito.verify(computeTollDao, Mockito.times(1)).updateQtpStatitics(Mockito.any(TransactionDto.class));
//		Mockito.verify(asyncProcessCall, Mockito.times(1))
//				.pushMessage(Mockito.any(CommonClassificationDto.class), Mockito.any(String.class));

	}

}
