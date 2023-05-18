
package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dto.MessageConversionDto;
import com.conduent.tpms.ictx.dto.RevenueDateStatisticsDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.Plaza;
import com.conduent.tpms.ictx.model.VehicleClass;
import com.conduent.tpms.ictx.service.TollCalculationService;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;
import com.conduent.tpms.ictx.utility.LocalTimeUtility;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

/**
 * IctxConversionServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class IctxConversionServiceImplTest {
/*
	@InjectMocks
	private IctxConversionServiceImpl ictxConversionServiceImpl;

	@Mock
	private StaticDataLoad staticDataLoad;

	@Mock
	private TollCalculationService tollCalculationService;

	@Mock
	private AwayTransactionDao awayTransactionDao;

	@Mock
	private LocalDateTimeUtility localDateTimeUtility;

	@Mock
	private LocalTimeUtility localTimeUtility;
	
	private String fileType = "ICTX";

	@Test
	void testGetIctxTransaction() {

		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(202);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(202)).thenReturn(tempRevenueDateStatistics);

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
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
		
		

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(2L);
		tempAgency.setDevicePrefix("005");
		Mockito.when(staticDataLoad.getFacHomeAgencyById(2L)).thenReturn(tempAgency);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");
		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");
		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Plaza entrytempPlaza = new Plaza();
		entrytempPlaza.setPlazaId(201);
		entrytempPlaza.setExternPlazaId("01P");
		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Lane entrytempLane = new Lane();
		entrytempLane.setLaneId(535);
		entrytempLane.setExternLaneId("01L");
		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);

		Assert.notNull(messageConversionDto, "Result is null");
	}

	@Test
	void testEtcTrxSerialNum() { // 0-11
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("            ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(0, 12)),
				"Invalid EtcTrxSerialNum");

		awayTransaction.setLaneTxId(2345L);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("000000002345".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(0, 12)),
				"Invalid EtcTrxSerialNum");

		awayTransaction.setLaneTxId(1234567899123L);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);

		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid EtcTrxSerialNum");

	}

	@Test
	void testRevenueDate() { // 12-19
		AwayTransaction awayTransaction = new AwayTransaction();
		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		awayTransaction.setPlazaId(1);
		awayTransaction.setPlazaAgencyId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);

		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		Mockito.when(localDateTimeUtility.getDateYYYYMMDD(tempDateTime)).thenReturn("20211013");

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("        ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(12, 20)),
				"Invalid Revenue Date");

		tempRevenueDateStatistics.setRevenueSecondOfDay(0);
		awayTransaction.setTxTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("20211013".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(12, 20)),
				"Invalid Revenue Date");

		tempRevenueDateStatistics.setRevenueSecondOfDay(43500);
		awayTransaction.setTxTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("20211013".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(12, 20)),
				"Invalid Revenue Date");

		tempRevenueDateStatistics.setRevenueSecondOfDay(41500);
		awayTransaction.setTxTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("20211013".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(12, 20)),
				"Invalid Revenue Date");

	}

	@SuppressWarnings("deprecation")
	@Test
	void testEtcFacAgency() { // 20-22
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPlazaAgencyId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(20, 23)),
				"Invalid EtcFacAgency");

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");

		Mockito.when(staticDataLoad.getFacHomeAgencyById(1L)).thenReturn(tempAgency);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("004".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(20, 23)));

		awayTransaction.setPlazaId(2);
		awayTransaction.setPlazaAgencyId(2);

		Agency tempAgency2 = new Agency();
		tempAgency2.setAgencyId(2L);
		tempAgency2.setDevicePrefix("0045");

		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(2);

		Mockito.when(staticDataLoad.getFacHomeAgencyById(2L)).thenReturn(tempAgency2);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(2)).thenReturn(tempRevenueDateStatistics);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid EtcFacAgency");

	}

	@Test
	void testEtcTxType() { // 23
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(" ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(23, 24)), "Invalid EtcTxType");

		awayTransaction.setTollSystemType("B");

		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("B".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(23, 24)), "Invalid EtcTxType");

		awayTransaction.setTollSystemType("BD");

		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid EtcTxType");
	}

	@Test
	void testEtcEntryDate() { // 24-32
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		Mockito.when(localDateTimeUtility.getDateYYYYMMDD(tempDateTime)).thenReturn("20211013");

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("        ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(24, 32)),
				"Invalid Entry Date");

		awayTransaction.setEntryLaneId(1);
		awayTransaction.setTollSystemType("B");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("********".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(24, 32)),
				"Invalid Entry Date");

		awayTransaction.setTollSystemType("E");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("********".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(24, 32)),
				"Invalid Entry Date");

		awayTransaction.setTollSystemType("U");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("********".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(24, 32)),
				"Invalid Entry Date");

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("20211013".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(24, 32)),
				"Invalid Entry Date");
	}

	@Test
	void testEtcEntryTime() { // 32-37
		AwayTransaction awayTransaction = new AwayTransaction();
		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);
		Mockito.when(localTimeUtility.getTimeHHMMSS(tempDateTime)).thenReturn("120001");
		Mockito.when(localDateTimeUtility.getDateYYYYMMDD(tempDateTime)).thenReturn("20211013");
		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("      ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(32, 38)),
				"Invalid Entry Time");

		awayTransaction.setEntryLaneId(1);
		awayTransaction.setTollSystemType("B");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("******".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(32, 38)),
				"Invalid Entry Time");

		awayTransaction.setTollSystemType("E");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("******".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(32, 38)),
				"Invalid Entry Time");

		awayTransaction.setTollSystemType("U");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("******".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(32, 38)),
				"Invalid Entry Time");

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("120001".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(32, 38)),
				"Invalid Entry Time");
	}

	@Test
	void testEtcEntryPlaza() { // 38-40
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(38, 41)),
				"Invalid Entry Plaza");

		awayTransaction.setEntryLaneId(1);
		awayTransaction.setTollSystemType("B");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(38, 41)),
				"Invalid Entry Plaza");

		awayTransaction.setTollSystemType("E");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(38, 41)),
				"Invalid Entry Plaza");

		awayTransaction.setTollSystemType("U");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(38, 41)),
				"Invalid Entry Plaza");

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryPlazaId(201);
		Plaza entrytempPlaza = new Plaza();
		entrytempPlaza.setPlazaId(201);
		entrytempPlaza.setExternPlazaId("01P");
		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01P".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(38, 41)),
				"Invalid Entry Plaza");
	}

	@Test
	void testEtcEntryLane() { // 41-43
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(41, 44)),
				"Invalid Entry Lane");

		awayTransaction.setEntryLaneId(535);
		awayTransaction.setTollSystemType("B");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(41, 44)),
				"Invalid Entry Lane");

		awayTransaction.setTollSystemType("E");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(41, 44)),
				"Invalid Entry Lane");

		awayTransaction.setTollSystemType("U");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("***".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(41, 44)),
				"Invalid Entry Lane");

		awayTransaction.setTollSystemType("C");
		Lane entrytempLane = new Lane();
		entrytempLane.setLaneId(535);
		entrytempLane.setExternLaneId("01L");
		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01L".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(41, 44)),
				"Invalid Entry Lane");
	}

	@Test
	void testEtcTagAgency() { // 44-46
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(44, 47)),
				"Invalid Etc Tag Agency");

		awayTransaction.setDeviceNo("03000018132");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("030".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(44, 47)),
				"Invalid Etc Tag Agency");

	}

	@Test
	void testEtcTagSerialNum() { // 47-54
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("        ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(47, 55)),
				"Invalid Etc Tag Serial Num");

		awayTransaction.setDeviceNo("03000018132");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("00018132".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(47, 55)),
				"Invalid Etc Tag Serial Num");

	}

	@Test
	void testEtcReadPerformance() { // 55-56
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(55, 57)),
				"Invalid Etc Read Performance");

		awayTransaction.setDeviceReadCount(1);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(55, 57)),
				"Invalid Etc Read Performance");

		awayTransaction.setDeviceReadCount(123);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Read Performance");
	}

	@Test
	void testEtcWritePerformance() { // 57-58
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(57, 59)),
				"Invalid Etc Write Performance");

		awayTransaction.setDeviceWriteCount(1);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(57, 59)),
				"Invalid Etc Write Performance");

		awayTransaction.setDeviceWriteCount(123);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Write Performance");
	}

	@Test
	void testEtcTagPgmStatus() { // 59
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("*".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(59, 60)),
				"Invalid Etc Tag PgmStatus");

		awayTransaction.setDeviceProgramStatus("0");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("S".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(59, 60)),
				"Invalid Etc Tag PgmStatus");

		awayTransaction.setDeviceProgramStatus("1");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("S".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(59, 60)),
				"Invalid Etc Tag PgmStatus");

		awayTransaction.setDeviceProgramStatus("4");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("F".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(59, 60)),
				"Invalid Etc Tag PgmStatus");

		awayTransaction.setDeviceProgramStatus("6");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("F".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(59, 60)),
				"Invalid Etc Tag PgmStatus");
	}

	@Test
	void testEtcLaneMode() { // 60
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(IctxConstant.LANE_MODE_E.equalsIgnoreCase(messageConversionDto.getTxMessage().substring(60, 61)),
				"Invalid Etc Lane Mode");

		awayTransaction.setLaneMode(1);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("E".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(60, 61)),
				"Invalid Etc Lane Mode");

		awayTransaction.setLaneMode(2);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("A".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(60, 61)),
				"Invalid Etc Lane Mode");

		awayTransaction.setLaneMode(3);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("M".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(60, 61)),
				"Invalid Etc Lane Mode");

		awayTransaction.setLaneMode(8);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("C".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(60, 61)),
				"Invalid Etc Lane Mode");
	}

	@Test
	void testEtcValidationStatus() { // 61
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("*".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(61, 62)),
				"Invalid Etc Validation Status");

		awayTransaction.setLaneDeviceStatus(1L);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("1".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(61, 62)),
				"Invalid Etc Validation Status");

		awayTransaction.setLaneDeviceStatus(12L);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Validation Status");
	}

	@Test
	void testEtcLicState() { // 62-63
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(62, 64)),
				"Invalid Etc Lic State");

		awayTransaction.setPlateState("**");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(62, 64)),
				"Invalid Etc Lic State");

		awayTransaction.setPlateState("****");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Lic State");
	}

	@Test
	void testEtcLicNumber() { // 64-73
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**********".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(64, 74)),
				"Invalid Etc Lic Number");

		awayTransaction.setPlateState("**********");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("**********".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(64, 74)),
				"Invalid Etc Lic Number");

		awayTransaction.setPlateState("************");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Lic Number");
	}

	@Test
	void testEtcClassCharged() { // 74-76
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);

		VehicleClass vehicleClass = new VehicleClass();
		vehicleClass.setExternAgencyClass("OO");

		Mockito.when(staticDataLoad.getVehClassByAgencyIdandActualClass(1, 0)).thenReturn(vehicleClass);

		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		awayTransaction.setPlazaAgencyId(1);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("OO ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(74, 77)),
				"Invalid Etc Class Charged");

		VehicleClass vehicleClass2 = new VehicleClass();
		vehicleClass2.setExternAgencyClass("O1");

		Mockito.when(staticDataLoad.getVehClassByAgencyIdandActualClass(1, 1)).thenReturn(vehicleClass2);

		awayTransaction.setActualClass(1);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("O1 ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(74, 77)),
				"Invalid Etc Class Charged");

		awayTransaction.setActualClass(12);

		VehicleClass vehicleClass3 = new VehicleClass();
		vehicleClass3.setExternAgencyClass("O134");

		Mockito.when(staticDataLoad.getVehClassByAgencyIdandActualClass(1, 12)).thenReturn(vehicleClass3);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Class Charged");
	}

	@Test
	void testEtcActualAxles() { // 77-78
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("0 ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(77, 79)),
				"Invalid Etc Actual Axles");

		awayTransaction.setActualAxles(1);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("1 ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(77, 79)),
				"Invalid Etc Actual Axles");

		awayTransaction.setActualAxles(1234);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Actual Axles");
	}

	@Test
	void testEtcExitSpeed() { // 79-81
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("000".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(79, 82)),
				"Invalid Etc Exit Speed");

		awayTransaction.setVehicleSpeed(3689);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue(!messageConversionDto.getIsValidLengthTx(), "Invalid Etc Exit Speed");
	}

	@Test
	void testEtcOverSpeed() { // SpeedViolation -82
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		awayTransaction.setSpeedViolFlag("T");
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("Y".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(82, 83)),
				"Invalid Etc Over Speed");

		awayTransaction.setSpeedViolFlag("Y");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("Y".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(82, 83)),
				"Invalid Etc Over Speed");

		awayTransaction.setSpeedViolFlag("N");
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("N".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(82, 83)),
				"Invalid Etc Over Speed");
	}

	@Test
	void testEtcExitDate() { // 83-90
		AwayTransaction awayTransaction = new AwayTransaction();
		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		Mockito.when(localDateTimeUtility.getDateYYYYMMDD(tempDateTime)).thenReturn("20211013");

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("        ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(83, 91)),
				"Invalid Exit Date");

		awayTransaction.setTxTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("20211013".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(83, 91)),
				"Invalid Exit Date");
	}

	@Test
	void testEtcExitTime() { // 91-96
		AwayTransaction awayTransaction = new AwayTransaction();
		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);

		Mockito.when(localDateTimeUtility.getDateYYYYMMDD(tempDateTime)).thenReturn("20211013");

		Mockito.when(localTimeUtility.getTimeHHMMSS(tempDateTime)).thenReturn("120001");

		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("      ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(91, 97)),
				"Invalid Exit Time");

		awayTransaction.setTxTimestamp(tempDateTime);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("120001".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(91, 97)),
				"Invalid Exit Time");
	}

	@Test
	void testEtcExitPlaza() { // 97-99
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(97, 100)),
				"Invalid Exit Plaza");

		RevenueDateStatisticsDto tempRevenueDateStatistics2 = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics2.setRevenueSecondOfDay(100);
		tempRevenueDateStatistics2.setPlazaId(201);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(201)).thenReturn(tempRevenueDateStatistics2);

		awayTransaction.setPlazaId(201);
		Plaza entrytempPlaza = new Plaza();
		entrytempPlaza.setPlazaId(201);
		entrytempPlaza.setExternPlazaId("01P");
		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01P".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(97, 100)),
				"Invalid Exit Plaza");
	}

	@Test
	void testEtcExitLane() { // 100-102
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("   ".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(100, 103)),
				"Invalid Exit Lane");

		awayTransaction.setLaneId(535);
		Lane entrytempLane = new Lane();
		entrytempLane.setLaneId(535);
		entrytempLane.setExternLaneId("01L");
		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("01L".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(100, 103)),
				"Invalid Exit Lane");
	}

	@Test
	void testDebitCredit() { // 103
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("+".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(103, 104)),
				"Invalid Debit Credit");
	}

	@Test
	void testEtcTollAmount() { // 104-108
		AwayTransaction awayTransaction = new AwayTransaction();

		awayTransaction.setPlazaId(1);
		awayTransaction.setPostedFareAmount(0.0);
		awayTransaction.setVideoFareAmount(0.0);
		awayTransaction.setPlateState("");
		RevenueDateStatisticsDto tempRevenueDateStatistics = new RevenueDateStatisticsDto();
		tempRevenueDateStatistics.setRevenueSecondOfDay(1);
		tempRevenueDateStatistics.setPlazaId(1);
		Mockito.when(staticDataLoad.getRevenueStatusByPlaza(1)).thenReturn(tempRevenueDateStatistics);

		//Mockito.when(tollCalculationService.calculateToll(awayTransaction)).thenReturn(0.0);
		MessageConversionDto messageConversionDto = ictxConversionServiceImpl.getIctxTransaction(awayTransaction,fileType);
		Assert.isTrue("00000".equalsIgnoreCase(messageConversionDto.getTxMessage().substring(104, 109)),
				"Invalid Etc Toll Amount");
	}
*/
}
