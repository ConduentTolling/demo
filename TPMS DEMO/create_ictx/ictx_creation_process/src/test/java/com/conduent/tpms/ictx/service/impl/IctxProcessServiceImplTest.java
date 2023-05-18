
package com.conduent.tpms.ictx.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.dao.IagFileStatsDao;
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dao.TransactionDetailDao;
import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.dto.MessageConversionDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.Plaza;
import com.conduent.tpms.ictx.model.QatpStatistics;
import com.conduent.tpms.ictx.model.ReconciliationCheckPoint;
import com.conduent.tpms.ictx.service.AtpMessageFormatService;
import com.conduent.tpms.ictx.service.DuplicateCheckService;
import com.conduent.tpms.ictx.service.IctxStatisticsService;
import com.conduent.tpms.ictx.service.MessagePublisherService;
import com.conduent.tpms.ictx.utility.FileOperation;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

/**
 * IctxProcessServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class IctxProcessServiceImplTest {
	/*
	@InjectMocks
	private IctxProcessServiceImpl ictxProcessServiceImpl;

	@Mock
	private StaticDataLoad staticDataLoad;

	@Mock
	private SequenceDao sequenceDao;

	@Mock
	IctxConversionServiceImpl ictxConversionServiceImpl;

	@Mock
	ConfigVariable configVariable;

	@Mock
	FileOperation fileOperation;

	@Mock
	AwayTransactionDao awayTransactionDao;

	@Mock
	IagFileStatisticsDao iagFileStatisticsDao;
	
	@Mock
	IagFileStatsDao iagFileStatsDao;
	
	@Mock
	QatpStatisticsDao qatpStatisticsDao;

	@Mock
	AtpMessageFormatService atpMessageFormatService;

	@Mock
	MessagePublisherService messagePublisherService;

	@Mock
	TransactionDetailDao transactionDetailDao;

	@Mock
	ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Mock
	DuplicateCheckService duplicateCheckService;

	@Mock
	IctxStatisticsService ictxStatisticsService;
	
	
	private String fileType = "ICTX";

	@Test
	void testIagStatisticsForNewFile() throws Exception {
		// file IagStatistics Exists

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		AwayTransaction awayTransaction2 = new AwayTransaction();
		awayTransaction2.setTxDate(LocalDate.now());
		awayTransaction2.setLaneTxId(18731197982L);
		awayTransaction2.setTxExternRefNo("0000000000000000");
		awayTransaction2.setTxSeqNumber(1869L);
		awayTransaction2.setExternFileId(525L);
		awayTransaction2.setPlazaAgencyId(2);
		awayTransaction2.setPlazaId(202);
		awayTransaction2.setLaneId(536);
		awayTransaction2.setTxTimestamp(LocalDateTime.now());
		awayTransaction2.setTxModSeq(0);
		awayTransaction2.setTxType("O");
		awayTransaction2.setTxSubType("T");
		awayTransaction2.setLaneMode(1);
		awayTransaction2.setLaneType(0L);
		awayTransaction2.setLaneState(0);
		awayTransaction2.setLaneHealth(0);
		awayTransaction2.setCollectorId(21312L);
		awayTransaction2.setTourSegmentId(0L);
		awayTransaction2.setEntryDataSource("E");
		awayTransaction2.setEntryLaneId(535);
		awayTransaction2.setEntryPlazaId(201);
		awayTransaction2.setEntryTimestamp(LocalDateTime.now());
		awayTransaction2.setEntryTxSeqNumber(0);
		awayTransaction2.setEntryVehicleSpeed(0);
		awayTransaction2.setLaneTxStatus(0);
		awayTransaction2.setTollRevenueType(1);
		awayTransaction2.setActualClass(1);
		awayTransaction2.setActualAxles(2);
		awayTransaction2.setActualExtraAxles(0);
		awayTransaction2.setCollectorClass(0);
		awayTransaction2.setCollectorAxles(0);
		awayTransaction2.setForwardAxles(0);
		awayTransaction2.setReverseAxles(0);
		awayTransaction2.setFullFareAmount(8.0);
		awayTransaction2.setDiscountedAmount(0.0);
		awayTransaction2.setUnrealizedAmount(0.0);
		awayTransaction2.setCollectedAmount(0.0);
		awayTransaction2.setIsDiscountable("N");
		awayTransaction2.setIsMedianFare("N");
		awayTransaction2.setIsPeak("N");
		awayTransaction2.setPriceScheduleId(0);
		awayTransaction2.setVehicleSpeed(0);
		awayTransaction2.setReceiptIssued(0);
		awayTransaction2.setDeviceNo("01600018132");
		awayTransaction2.setAccountType(0);
		awayTransaction2.setDeviceCodedClass(0);
		awayTransaction2.setDeviceAgencyClass(1);
		awayTransaction2.setDeviceIagClass(0);
		awayTransaction2.setDeviceAxles(2);
		awayTransaction2.setEtcAccountId(0L);
		awayTransaction2.setAccountAgencyId(26);
		awayTransaction2.setReadAviClass(1);
		awayTransaction2.setReadAviAxles(2);
		awayTransaction2.setDeviceProgramStatus("S");
		awayTransaction2.setBufferedReadFlag("F");
		awayTransaction2.setLaneDeviceStatus(0L);
		awayTransaction2.setPostDeviceStatus(0);
		awayTransaction2.setPlateCountry("****");
		awayTransaction2.setPlateNumber("**********");
		awayTransaction2.setPlateState("**");
		awayTransaction2.setRevenueDate(LocalDate.now());
		awayTransaction2.setAtpFileId(4085891L);
		awayTransaction2.setTollSystemType("C");

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);
		tempAwayTxList.add(awayTransaction2);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		
		//Mockito.when(staticDataLoad.getHomeLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomePlazaById(202)).thenReturn(tempLane);


		Mockito.when(ictxConversionServiceImpl.getIctxTransaction(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(messageConversionDto);

		Mockito.when(configVariable.getBatchSize()).thenReturn(1);

		Mockito.when(configVariable.getDstDirPath()).thenReturn("/samplePath/");

		Mockito.when(
				fileOperation.writeToFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyList()))
				.thenReturn(true);

		Mockito.when(configVariable.getBatchSize()).thenReturn(2);

		// awayTransactionDao
		// .getAwayTransactionByDevicePrefix(tempAgency.getDevicePrefix());

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		
		  Mockito.when(iagFileStatisticsDao.getIagFileStatistics(Mockito.any(
		  IagFileStatistics.class))) .thenReturn(iagFileStatistics);
		 

		Mockito.when(reconciliationCheckPointDao.getFileInfo(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class)))
				.thenReturn(null);

		Mockito.when(duplicateCheckService.validateDuplicateTx(Mockito.any(AwayTransaction.class))).thenReturn(true);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(ictxStatisticsService, Mockito.times(2))
				.updateIagStatistics(Mockito.any(FileOperationStatus.class),Mockito.any(String.class));

	}

	@Test
	void testIagStatisticsForExistingFile() throws Exception {
		// file IagStatistics Exists

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		AwayTransaction awayTransaction2 = new AwayTransaction();
		awayTransaction2.setTxDate(LocalDate.now());
		awayTransaction2.setLaneTxId(18731197982L);
		awayTransaction2.setTxExternRefNo("0000000000000000");
		awayTransaction2.setTxSeqNumber(1869L);
		awayTransaction2.setExternFileId(525L);
		awayTransaction2.setPlazaAgencyId(2);
		awayTransaction2.setPlazaId(202);
		awayTransaction2.setLaneId(536);
		awayTransaction2.setTxTimestamp(LocalDateTime.now());
		awayTransaction2.setTxModSeq(0);
		awayTransaction2.setTxType("O");
		awayTransaction2.setTxSubType("T");
		awayTransaction2.setLaneMode(1);
		awayTransaction2.setLaneType(0L);
		awayTransaction2.setLaneState(0);
		awayTransaction2.setLaneHealth(0);
		awayTransaction2.setCollectorId(21312L);
		awayTransaction2.setTourSegmentId(0L);
		awayTransaction2.setEntryDataSource("E");
		awayTransaction2.setEntryLaneId(535);
		awayTransaction2.setEntryPlazaId(201);
		awayTransaction2.setEntryTimestamp(LocalDateTime.now());
		awayTransaction2.setEntryTxSeqNumber(0);
		awayTransaction2.setEntryVehicleSpeed(0);
		awayTransaction2.setLaneTxStatus(0);
		awayTransaction2.setTollRevenueType(1);
		awayTransaction2.setActualClass(1);
		awayTransaction2.setActualAxles(2);
		awayTransaction2.setActualExtraAxles(0);
		awayTransaction2.setCollectorClass(0);
		awayTransaction2.setCollectorAxles(0);
		awayTransaction2.setForwardAxles(0);
		awayTransaction2.setReverseAxles(0);
		awayTransaction2.setFullFareAmount(8.0);
		awayTransaction2.setDiscountedAmount(0.0);
		awayTransaction2.setUnrealizedAmount(0.0);
		awayTransaction2.setCollectedAmount(0.0);
		awayTransaction2.setIsDiscountable("N");
		awayTransaction2.setIsMedianFare("N");
		awayTransaction2.setIsPeak("N");
		awayTransaction2.setPriceScheduleId(0);
		awayTransaction2.setVehicleSpeed(0);
		awayTransaction2.setReceiptIssued(0);
		awayTransaction2.setDeviceNo("01600018132");
		awayTransaction2.setAccountType(0);
		awayTransaction2.setDeviceCodedClass(0);
		awayTransaction2.setDeviceAgencyClass(1);
		awayTransaction2.setDeviceIagClass(0);
		awayTransaction2.setDeviceAxles(2);
		awayTransaction2.setEtcAccountId(0L);
		awayTransaction2.setAccountAgencyId(26);
		awayTransaction2.setReadAviClass(1);
		awayTransaction2.setReadAviAxles(2);
		awayTransaction2.setDeviceProgramStatus("S");
		awayTransaction2.setBufferedReadFlag("F");
		awayTransaction2.setLaneDeviceStatus(0L);
		awayTransaction2.setPostDeviceStatus(0);
		awayTransaction2.setPlateCountry("****");
		awayTransaction2.setPlateNumber("**********");
		awayTransaction2.setPlateState("**");
		awayTransaction2.setRevenueDate(LocalDate.now());
		awayTransaction2.setAtpFileId(4085891L);
		awayTransaction2.setTollSystemType("C");

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);
		tempAwayTxList.add(awayTransaction2);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		ReconciliationCheckPoint reconciliationCheckPoint = new ReconciliationCheckPoint();
		reconciliationCheckPoint.setRecordCount(100L);
		reconciliationCheckPoint.setFileName("008_016_20211014173110.ICTX");

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		
		//Mockito.when(staticDataLoad.getHomeLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomePlazaById(202)).thenReturn(tempLane);


		Mockito.when(ictxConversionServiceImpl.getIctxTransaction(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(messageConversionDto);

		Mockito.when(configVariable.getBatchSize()).thenReturn(1);

		Mockito.when(configVariable.getDstDirPath()).thenReturn("/samplePath/");

		Mockito.when(configVariable.getBatchSize()).thenReturn(2);
		;

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		Mockito.when(reconciliationCheckPointDao.getFileInfo(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class)))
				.thenReturn(reconciliationCheckPoint);

		Mockito.when(duplicateCheckService.validateDuplicateTx(Mockito.any(AwayTransaction.class))).thenReturn(true);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(ictxStatisticsService, Mockito.times(1))
		.updateIagStatistics(Mockito.any(FileOperationStatus.class),Mockito.any(String.class));

	}

	@Test
	void testRecordLessThanConfigBatch() throws Exception {
		// Record less than the config batch

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);
		
		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomeLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomePlazaById(202)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);

		Mockito.when(ictxConversionServiceImpl.getIctxTransaction(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(messageConversionDto);

		Mockito.when(duplicateCheckService.validateDuplicateTx(Mockito.any(AwayTransaction.class))).thenReturn(true);

		Mockito.when(configVariable.getBatchSize()).thenReturn(20);

		Mockito.when(configVariable.getDstDirPath()).thenReturn("/samplePath/");

		Mockito.when(
				fileOperation.writeToFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyList()))
				.thenReturn(true);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);
		

		
//		Mockito.when(iagFileStatsDao.IagFileStatsDao(Mockito.anyString(),))
//		.thenReturn(tempAwayTxList);
//		Mockito.when(iagFileStatisticsDao.getAwayTransactionByDevicePrefix(Mockito.anyString()))
//		.thenReturn(tempAwayTxList);
	
		ictxProcessServiceImpl.process(8L, fileType);
		Mockito.verify(iagFileStatsDao, Mockito.times(1))
		.updateRecordCount(Mockito.any(String.class),Mockito.any(Long.class));
		Mockito.verify(iagFileStatisticsDao, Mockito.times(1))
		.updateRecordCount(Mockito.any(String.class),Mockito.any(Long.class));
		Mockito.verify(ictxStatisticsService, Mockito.times(1))
		.updateIagStatistics(Mockito.any(FileOperationStatus.class),Mockito.any(String.class));

	}

	@Test
	void testInvalidPlaza() throws Exception { // Invalid Plaza

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(null);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidEntryPlaza() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		//Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(null);

		//Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidEntryLane() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		//Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(null);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidEntryTxTimestamp() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now().plusDays(2));
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

	//	Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);

	//	Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidTxTimestamp() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now().plusDays(2));
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidTxDate() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now().plusDays(2));
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidLane() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		AtpMessageDto atpMessageDto = new AtpMessageDto();

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(null);

		Mockito.when(atpMessageFormatService.getAtpMessage(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(atpMessageDto);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(messagePublisherService, Mockito.times(1)).publishMessageToStream(Mockito.anyList());
		Mockito.verify(transactionDetailDao, Mockito.times(1))
				.updateTransactionDetail(Mockito.any(AwayTransaction.class));

	}

	@Test
	void testInvalidDuplicateCheck() throws Exception {

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		AwayTransaction awayTransaction2 = new AwayTransaction();
		awayTransaction2.setTxDate(LocalDate.now());
		awayTransaction2.setLaneTxId(18731197982L);
		awayTransaction2.setTxExternRefNo("0000000000000000");
		awayTransaction2.setTxSeqNumber(1869L);
		awayTransaction2.setExternFileId(525L);
		awayTransaction2.setPlazaAgencyId(2);
		awayTransaction2.setPlazaId(202);
		awayTransaction2.setLaneId(536);
		awayTransaction2.setTxTimestamp(LocalDateTime.now());
		awayTransaction2.setTxModSeq(0);
		awayTransaction2.setTxType("O");
		awayTransaction2.setTxSubType("T");
		awayTransaction2.setLaneMode(1);
		awayTransaction2.setLaneType(0L);
		awayTransaction2.setLaneState(0);
		awayTransaction2.setLaneHealth(0);
		awayTransaction2.setCollectorId(21312L);
		awayTransaction2.setTourSegmentId(0L);
		awayTransaction2.setEntryDataSource("E");
		awayTransaction2.setEntryLaneId(535);
		awayTransaction2.setEntryPlazaId(201);
		awayTransaction2.setEntryTimestamp(LocalDateTime.now());
		awayTransaction2.setEntryTxSeqNumber(0);
		awayTransaction2.setEntryVehicleSpeed(0);
		awayTransaction2.setLaneTxStatus(0);
		awayTransaction2.setTollRevenueType(1);
		awayTransaction2.setActualClass(1);
		awayTransaction2.setActualAxles(2);
		awayTransaction2.setActualExtraAxles(0);
		awayTransaction2.setCollectorClass(0);
		awayTransaction2.setCollectorAxles(0);
		awayTransaction2.setForwardAxles(0);
		awayTransaction2.setReverseAxles(0);
		awayTransaction2.setFullFareAmount(8.0);
		awayTransaction2.setDiscountedAmount(0.0);
		awayTransaction2.setUnrealizedAmount(0.0);
		awayTransaction2.setCollectedAmount(0.0);
		awayTransaction2.setIsDiscountable("N");
		awayTransaction2.setIsMedianFare("N");
		awayTransaction2.setIsPeak("N");
		awayTransaction2.setPriceScheduleId(0);
		awayTransaction2.setVehicleSpeed(0);
		awayTransaction2.setReceiptIssued(0);
		awayTransaction2.setDeviceNo("01600018132");
		awayTransaction2.setAccountType(0);
		awayTransaction2.setDeviceCodedClass(0);
		awayTransaction2.setDeviceAgencyClass(1);
		awayTransaction2.setDeviceIagClass(0);
		awayTransaction2.setDeviceAxles(2);
		awayTransaction2.setEtcAccountId(0L);
		awayTransaction2.setAccountAgencyId(26);
		awayTransaction2.setReadAviClass(1);
		awayTransaction2.setReadAviAxles(2);
		awayTransaction2.setDeviceProgramStatus("S");
		awayTransaction2.setBufferedReadFlag("F");
		awayTransaction2.setLaneDeviceStatus(0L);
		awayTransaction2.setPostDeviceStatus(0);
		awayTransaction2.setPlateCountry("****");
		awayTransaction2.setPlateNumber("**********");
		awayTransaction2.setPlateState("**");
		awayTransaction2.setRevenueDate(LocalDate.now());
		awayTransaction2.setAtpFileId(4085891L);
		awayTransaction2.setTollSystemType("C");

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);
		tempAwayTxList.add(awayTransaction2);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		
		//Mockito.when(staticDataLoad.getHomeLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomePlazaById(202)).thenReturn(tempLane);


		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		Mockito.when(reconciliationCheckPointDao.getFileInfo(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class)))
				.thenReturn(null);

		Mockito.when(duplicateCheckService.validateDuplicateTx(Mockito.any(AwayTransaction.class))).thenReturn(false);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(transactionDetailDao, Mockito.times(2)).updateEtcTxStatus(Mockito.any(AwayTransaction.class));

		Mockito.verify(awayTransactionDao, Mockito.times(2)).deleteRejectedTx(Mockito.any(Long.class), Mockito.any(String.class));
		
		//Mockito.verify(awayTransactionDao, Mockito.times(2)).deleteRejectedITXCQ(Mockito.any(Long.class), Mockito.any(String.class));
		
	}

	@Test
	void testFileRenameOperation() throws Exception {
		// file Rename Operation

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate(LocalDate.now());
		awayTransaction.setLaneTxId(18731197981L);
		awayTransaction.setTxExternRefNo("0000000000000000");
		awayTransaction.setTxSeqNumber(1869L);
		awayTransaction.setExternFileId(525L);
		awayTransaction.setPlazaAgencyId(2);
		awayTransaction.setPlazaId(202);
		awayTransaction.setLaneId(536);
		awayTransaction.setTxTimestamp(LocalDateTime.now());
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
		awayTransaction.setEntryTimestamp(LocalDateTime.now());
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
		awayTransaction.setDeviceNo("01600018132");
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

		List<AwayTransaction> tempAwayTxList = new ArrayList<>();
		tempAwayTxList.add(awayTransaction);

		Plaza tempPlaza = new Plaza();
		tempPlaza.setPlazaId(202);
		tempPlaza.setExternPlazaId("02P");

		Lane tempLane = new Lane();
		tempLane.setLaneId(536);
		tempLane.setExternLaneId("02L");

		Plaza entrytempPlaza = new Plaza();
		tempPlaza.setPlazaId(201);
		tempPlaza.setExternPlazaId("01P");

		Lane entrytempLane = new Lane();
		tempLane.setLaneId(535);
		tempLane.setExternLaneId("01L");

		String fileRecord = "018731197981      005C20210812101114      03000018132***** *************1  2 000N2021081210111401P01L 00080";

		MessageConversionDto messageConversionDto = new MessageConversionDto();
		messageConversionDto.setRevenueDate(LocalDate.now());
		messageConversionDto.setExpectedRevenueAmount(2.0);
		messageConversionDto.setTxMessage(fileRecord);

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(4085891L);
		iagFileStatistics.setFromAgency("008");
		iagFileStatistics.setToAgency("016");
		iagFileStatistics.setInputRecCount(20L);
		iagFileStatistics.setOutputRecCount(10L);

		QatpStatistics qatpStatisticsDb = new QatpStatistics();
		qatpStatisticsDb.setRecordCount(20L);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);

		Mockito.when(sequenceDao.getFileSequence("016")).thenReturn(101L);

		Mockito.when(staticDataLoad.getPlazaById(202)).thenReturn(tempPlaza);

		Mockito.when(staticDataLoad.getLaneById(536)).thenReturn(tempLane);

		Mockito.when(staticDataLoad.getPlazaById(201)).thenReturn(entrytempPlaza);

		Mockito.when(staticDataLoad.getLaneById(535)).thenReturn(entrytempLane);
		
		//Mockito.when(staticDataLoad.getHomeLaneById(536)).thenReturn(tempLane);
		
		//Mockito.when(staticDataLoad.getHomePlazaById(202)).thenReturn(tempLane);

		Mockito.when(ictxConversionServiceImpl.getIctxTransaction(Mockito.any(AwayTransaction.class), Mockito.any(String.class)))
				.thenReturn(messageConversionDto);

		Mockito.when(configVariable.getDstDirPath()).thenReturn("/samplePath/");

		Mockito.when(
				fileOperation.writeToFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyList()))
				.thenReturn(true);

		Mockito.when(awayTransactionDao.getAwayTransactionByAgencyId(Mockito.anyLong()))
				.thenReturn(tempAwayTxList);

		Mockito.when(duplicateCheckService.validateDuplicateTx(Mockito.any(AwayTransaction.class))).thenReturn(true);

		Mockito.when(fileOperation.overwriteFileData(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyString())).thenReturn(true);

		ictxProcessServiceImpl.process(8L, fileType);

		Mockito.verify(fileOperation, Mockito.times(1)).renameFile(Mockito.any(File.class), Mockito.any(File.class));
	}
*/
}
