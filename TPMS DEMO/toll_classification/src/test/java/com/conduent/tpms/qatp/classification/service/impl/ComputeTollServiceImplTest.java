package com.conduent.tpms.qatp.classification.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dao.TAgencyDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TollScheduleDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;

/**
 * Test Class for compute toll service
 *
 */
@ExtendWith(MockitoExtension.class)
class ComputeTollServiceImplTest {

	@InjectMocks
	private ComputeTollServiceImpl computeTollServiceImpl;

	@Mock
	private TAgencyDao tAgencyDao;

	@Mock
	private ComputeTollDao computeTollDAO;
	
	@Mock
	private MasterDataCache masterDataCache;
	
	OffsetDateTime today = OffsetDateTime.parse("2022-02-14T10:15:30-05:00");

	@Test
	void TestPAComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTxType("E");
		transactionDto.setLaneTxId(101L);
		transactionDto.setEtcFareAmount(21.0);
		transactionDto.setVideoFareAmount(31.0);
		transactionDto.setExpectedRevenueAmount(26.0);
		transactionDto.setTollRevenueType(1);
		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		TransactionDetailDto temptransactionDetailDto = computeTollServiceImpl.computePAToll(transactionDetailDto);

		assertEquals(21.0, temptransactionDetailDto.getTransactionDto().getExpectedRevenueAmount());

		transactionDto.setTxType("O");
		 temptransactionDetailDto = computeTollServiceImpl.computePAToll(transactionDetailDto);
		assertEquals(21.0, temptransactionDetailDto.getTransactionDto().getExpectedRevenueAmount());
	}

	@Test
	void TestPAComputeTollForViolation() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTxType("V");
		transactionDto.setLaneTxId(101L);
		transactionDto.setEtcFareAmount(21.0);
		transactionDto.setVideoFareAmount(31.0);
		transactionDto.setExpectedRevenueAmount(26.0);
		transactionDto.setTollRevenueType(1);
		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		TransactionDetailDto temptransactionDetailDto = computeTollServiceImpl.computePAToll(transactionDetailDto);

		assertEquals(31.0, temptransactionDetailDto.getTransactionDto().getExpectedRevenueAmount());
	}
	
	@Test
	void TestComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTollSystemType("B");
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		// transactionDto.setEntryPlazaId(62);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(1);
		transactionDto.setExtraAxles(1);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		TollPriceSchedule tollPriceSchedule = new TollPriceSchedule();
		tollPriceSchedule.setPriceScheduleId(22);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		// masterDataCache.getAgencyById
		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(tollPriceSchedule);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, null, 22,
				tollRevTypeList, 1L, 22)).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 22)).thenReturn(null);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(0.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(0.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getCashFareAmount());

	}
	
	@Test
	void TestComputeToll_WeekendTxDate() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTollSystemType("B");
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-04");
		transactionDto.setTxTimeStamp(today);
		// transactionDto.setEntryPlazaId(62);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(1);
		transactionDto.setActualClass(22);
		transactionDto.setExtraAxles(1);
		accountInfoDto.setAcctType(2);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		TollPriceSchedule tollPriceSchedule = new TollPriceSchedule();
		tollPriceSchedule.setPriceScheduleId(22);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(tollPriceSchedule);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 04), 72, null, 22,
				tollRevTypeList, 2L, 22)).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 04), 72, 72, 22, tollRevTypeList,
				2L, 22)).thenReturn(null);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(0.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(0.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getCashFareAmount());

	}

	@Test
	void TestComputeToll_TollSystemType_B_Wid_lcPriceScheduleId() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTollSystemType("B");
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		// transactionDto.setEntryPlazaId(62);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(1);
		transactionDto.setExtraAxles(1);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		TollPriceSchedule tollPriceSchedule = new TollPriceSchedule();
		tollPriceSchedule.setPriceScheduleId(22);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(tollPriceSchedule);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, null, 22,
				tollRevTypeList, 1L, 22)).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 22)).thenReturn(tollScheduleDtoList);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(2.5, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(2.5, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.5, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(5.5, temptransactionDetailDto.getCashFareAmount());

	}

	@Test
	void TestComputeToll_TollSystemType_B() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTollSystemType("B");
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		// transactionDto.setEntryPlazaId(62);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(1);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);
		
		TollPriceSchedule tollPriceSchedule = new TollPriceSchedule();
		tollPriceSchedule.setPriceScheduleId(22);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(tollPriceSchedule);
		
		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, null, 22,
				tollRevTypeList, 1L, 22)).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 22)).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 1)).thenReturn(tollScheduleDtoList);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(2.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(2.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

	}

	@Test
	void TestEtcComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		transactionDto.setEntryPlazaId(72);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(1);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 1)).thenReturn(tollScheduleDtoList);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(2.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(2.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

		transactionDto.setTxType("E");
		transactionDto.setTxSubType("Z");

		temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		//assertEquals(1.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(2.0, temptransactionDetailDto.getExpectedRevenueAmount());
		//assertEquals(1.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getPostedFareAmount());
		//assertEquals(1.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		//assertEquals(3.0, temptransactionDetailDto.getCashFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());
	}

	@Test
	void TestVideoComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		transactionDto.setEntryPlazaId(72);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(9);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 72, 22, tollRevTypeList,
				1L, 1)).thenReturn(tollScheduleDtoList);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(0.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(0.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

		transactionDto.setTxType("E");
		transactionDto.setTxSubType("Z");

		temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(0.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(0.0, temptransactionDetailDto.getPostedFareAmount());
		//assertEquals(1.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		//assertEquals(3.0, temptransactionDetailDto.getCashFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

	}

	@Test
	void TestCashComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTxType("V");
		transactionDto.setTxSubType("F");
		transactionDto.setLaneTxId(101L);
		transactionDto.setTxDate("2021-04-02");
		transactionDto.setTxTimeStamp(today);
		transactionDto.setEntryPlazaId(62);
		transactionDto.setEntryDataSource("*");
		transactionDto.setPlazaId(72);
		transactionDto.setActualClass(1);
		transactionDto.setPlazaAgencyId(1);
		transactionDto.setTollRevenueType(3);
		accountInfoDto.setAcctType(1);

		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);
		tempAgency.setScheduledPricingFlag("Y");

		List<TollScheduleDto> tollScheduleDtoList = new ArrayList<>();

		TollScheduleDto tollScheduleDto1 = new TollScheduleDto();
		tollScheduleDto1.setDiscountFareAmount(1.0);
		tollScheduleDto1.setExtraAxleCharge(0.5);
		tollScheduleDto1.setFullFareAmount(2.0);
		tollScheduleDto1.setRevenueTypeId(1);
		tollScheduleDto1.setTollScheduleId(11L);

		TollScheduleDto tollScheduleDto2 = new TollScheduleDto();
		tollScheduleDto2.setDiscountFareAmount(2.0);
		tollScheduleDto2.setExtraAxleCharge(1.0);
		tollScheduleDto2.setFullFareAmount(3.0);
		tollScheduleDto2.setRevenueTypeId(9);
		tollScheduleDto2.setTollScheduleId(99L);

		TollScheduleDto tollScheduleDto3 = new TollScheduleDto();
		tollScheduleDto3.setDiscountFareAmount(3.0);
		tollScheduleDto3.setExtraAxleCharge(1.5);
		tollScheduleDto3.setFullFareAmount(4.0);
		tollScheduleDto3.setRevenueTypeId(3);
		tollScheduleDto3.setTollScheduleId(33L);

		tollScheduleDtoList.add(tollScheduleDto1);
		tollScheduleDtoList.add(tollScheduleDto2);
		tollScheduleDtoList.add(tollScheduleDto3);

		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);

		// Mock masterDataCache.getAgencyById(1L))
		//Mockito.when(tAgencyDao.getAgencySchedulePricingById(1)).thenReturn(tempAgency);
		Mockito.when(masterDataCache.getAgencyById(1L)).thenReturn(tempAgency);

		Mockito.when(masterDataCache.getPriceSchedule(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(null);

		Mockito.when(computeTollDAO.getTollAmountByRevenueType(LocalDate.of(2021, 04, 02), 72, 62, 22, tollRevTypeList,
				1L, 1)).thenReturn(tollScheduleDtoList);

		TransactionDto temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		assertEquals(4.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(4.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

		transactionDto.setTxType("E");
		transactionDto.setTxSubType("Z");

		temptransactionDetailDto = computeTollServiceImpl.computeToll(transactionDto, accountInfoDto);

		//assertEquals(3.0, temptransactionDetailDto.getExpectedRevenueAmount());
		assertEquals(4.0, temptransactionDetailDto.getExpectedRevenueAmount());
		//assertEquals(3.0, temptransactionDetailDto.getPostedFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getPostedFareAmount());
		//assertEquals(1.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(2.0, temptransactionDetailDto.getEtcFareAmount());
		assertEquals(0.0, temptransactionDetailDto.getVideoFareAmount());
		//assertEquals(3.0, temptransactionDetailDto.getCashFareAmount());
		assertEquals(4.0, temptransactionDetailDto.getCashFareAmount());

	}

}
