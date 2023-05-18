package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

@ExtendWith(MockitoExtension.class)
public class DuplicateCheckServiceImplTest {
	
	@InjectMocks
	private DuplicateCheckServiceImpl duplicateCheckServiceImpl;
	
	@Mock
	AwayTransactionDao awayTransactionDao;

	@Mock
	StaticDataLoad staticDataLoad;

	@Mock
	LocalDateTimeUtility localDateTimeUtility;
	
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
	void TestvalidateDuplicateTx() {
		
		AwayTransaction awayTx = init();
		
		Mockito.when(staticDataLoad.getPoachingLimitByAgency(awayTx.getPlazaAgencyId())).thenReturn(10);
		
		Assert.notNull(duplicateCheckServiceImpl.validateDuplicateTx(awayTx), "Result is null");
	}

}
