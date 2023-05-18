package com.conduent.tpms.ictx.service.impl;

import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.aspectj.lang.annotation.Before;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.utility.EtcTxStatusHelper;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;

/**
 * AtpMessageFormatServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class AtpMessageFormatServiceImplTest {

	@InjectMocks
	private AtpMessageFormatServiceImpl atpMessageFormatServiceImpl;

	@Mock
	private EtcTxStatusHelper etcTxStatusHelper;

	@Mock
	private TimeZoneConv timeZoneConv;
	
	@Mock
	private LocalDateTimeUtility localDateTimeUtility;
	
	AwayTransaction awayTransaction = null;
	//@Mock AwayTransaction awayTransaction;
	@Initialized
	public AwayTransaction init() {
	LocalDateTime tempDateTime = LocalDateTime.now();
	OffsetDateTime tempOffsetDateTime = OffsetDateTime.now();
	AwayTransaction awayTransaction = new AwayTransaction();
	awayTransaction.setLaneTxId(18731197981L);
	awayTransaction.setTxExternRefNo("0000000000000000");
	awayTransaction.setTxSeqNumber(1869L);
	awayTransaction.setExternFileId(525L);
	awayTransaction.setPlazaAgencyId(2);
	awayTransaction.setPlazaId(202);
	awayTransaction.setLaneId(536);
	awayTransaction.setTxTimestamp(tempOffsetDateTime);
	awayTransaction.setTxModSeq(0);
	awayTransaction.setTxType("O");
	awayTransaction.setTxSubType("T");
	awayTransaction.setLaneMode(1);
	awayTransaction.setLaneType(0L);
	awayTransaction.setLaneState(0);
	awayTransaction.setLaneHealth(0);
	awayTransaction.setCollectorId(21312L);
	awayTransaction.setTourSegmentId(0L);
	awayTransaction.setEntryDataSource("E");
	awayTransaction.setEntryLaneId(535);
	awayTransaction.setEntryPlazaId(201);
	awayTransaction.setEntryTimestamp(tempOffsetDateTime);
	awayTransaction.setEntryTxSeqNumber(0);
	awayTransaction.setEntryVehicleSpeed(0);
	awayTransaction.setLaneTxStatus(0);
	awayTransaction.setTollRevenueType(1);
	awayTransaction.setActualClass(1);
	awayTransaction.setActualAxles(2);
	awayTransaction.setActualExtraAxles(0);
	awayTransaction.setCollectorClass(0);
	awayTransaction.setCollectorAxles(0);
	awayTransaction.setForwardAxles(0);
	awayTransaction.setReverseAxles(0);
	awayTransaction.setFullFareAmount(8.0);
	awayTransaction.setDiscountedAmount(0.0);
	awayTransaction.setUnrealizedAmount(0.0);
	awayTransaction.setCollectedAmount(0.0);
	awayTransaction.setIsDiscountable("N");
	awayTransaction.setIsMedianFare("N");
	awayTransaction.setIsPeak("N");
	awayTransaction.setPriceScheduleId(0);
	awayTransaction.setVehicleSpeed(0);
	awayTransaction.setReceiptIssued(0);
	awayTransaction.setDeviceNo("03000018132");
	awayTransaction.setAccountType(0);
	awayTransaction.setDeviceCodedClass(0);
	awayTransaction.setDeviceAgencyClass(1);
	awayTransaction.setDeviceIagClass(0);
	awayTransaction.setDeviceAxles(2);
	awayTransaction.setEtcAccountId(0L);
	awayTransaction.setAccountAgencyId(26);
	awayTransaction.setReadAviClass(1);
	awayTransaction.setReadAviAxles(2);
	awayTransaction.setDeviceProgramStatus("S");
	awayTransaction.setBufferedReadFlag("F");
	awayTransaction.setLaneDeviceStatus(0L);
	awayTransaction.setPostDeviceStatus(0);
	awayTransaction.setPlateCountry("****");
	awayTransaction.setPlateNumber("**********");
	awayTransaction.setPlateState("**");
	awayTransaction.setRevenueDate(LocalDate.now());
	awayTransaction.setAtpFileId(4085891L);
	awayTransaction.setTollSystemType("C");
	awayTransaction.setCorrReasonId(05);
	return awayTransaction;
	}

	@Test
	void TestGetAtpMessage() {

		AwayTransaction awayTransaction = init();
	
		LocalDateTime tempDateTime = LocalDateTime.now();

		//Mockito.when(localDateTimeUtility.getDateTimeByFormat(tempDateTime, IctxConstant.FORMAT_yyyyMMddHHmmssSSS)).thenReturn("2021-10-13 12:00:01.007");

		Mockito.when(etcTxStatusHelper.getEtcTxStatus("O", "T", 0L, 18731197981L)).thenReturn(83);

		AtpMessageDto atpMessageDto = atpMessageFormatServiceImpl.getAtpMessage(awayTransaction, "ICTX");
		

		Assert.notNull(atpMessageDto, "Result is null");
	}
	
	@Test
	void TestGetAtpMessageCorr() {

		AwayTransaction awayTransaction = init();
	
		LocalDateTime tempDateTime = LocalDateTime.now();

		lenient().when(localDateTimeUtility.getDateTimeByFormat(tempDateTime, IctxConstant.FORMAT_yyyyMMddHHmmssSSS)).thenReturn("2021-10-13 12:00:01.007");

		Mockito.when(etcTxStatusHelper.getEtcTxStatus("O", "T", 0L, 18731197981L)).thenReturn(83);
		
		lenient().when(etcTxStatusHelper.getEtcTxStatus("O", "A", 0L, 18731197981L)).thenReturn(83);

		AtpMessageDto atpMessageDto = atpMessageFormatServiceImpl.getAtpMessage(awayTransaction, "ITXC");
		
		

		Assert.notNull(atpMessageDto, "Result is null");
	}
}
