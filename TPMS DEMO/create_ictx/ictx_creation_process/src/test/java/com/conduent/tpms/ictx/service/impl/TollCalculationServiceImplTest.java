package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.TollScheduleDao;
import com.conduent.tpms.ictx.dto.TollFareDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.TollSchedule;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

/**
 * TollCalculationServiceImpl Test Class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class TollCalculationServiceImplTest {
/*
	@Mock
	private StaticDataLoad staticDataLoad;

	@Mock
	private TollScheduleDao tollScheduleDao;

	@InjectMocks
	private TollCalculationServiceImpl tollCalculationServiceImpl;

	@Test
	public void testCalculateToll() {

		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime paCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime nystaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime mtaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		AwayTransaction awayTx = new AwayTransaction();
		awayTx.setPlazaAgencyId(1);
		awayTx.setPlazaId(1);
		awayTx.setActualClass(22);
		awayTx.setTxDate(tempDateTime.toLocalDate());
		awayTx.setTollRevenueType(1);
		awayTx.setLaneId(2);
		awayTx.setDeviceNo("00812345678");
		awayTx.setTollSystemType("B");
		awayTx.setEntryLaneId(22);
		awayTx.setEntryPlazaId(11);
		awayTx.setTxTimestamp(tempDateTime);
		awayTx.setPostedFareAmount(2.0);
		awayTx.setExpectedRevenueAmount(3.0);
		awayTx.setActualExtraAxles(1);

		Lane tempLane = new Lane();
		tempLane.setLaneId(2);
		tempLane.setCalculateTollAmount("N");

		Agency tempAgency = new Agency();
		tempAgency.setScheduledPricingFlag("N");
		tempAgency.setCalculateTollAmount("N");
		tempAgency.setAgencyShortName("NY");
		tempAgency.setParentAgencyId(2);
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");

		Agency accountAgency = new Agency();
		accountAgency.setAgencyId(2L);
		accountAgency.setDevicePrefix("008");

		TollSchedule tollSchedule = new TollSchedule();
		tollSchedule.setFullFareAmount(2.0);
		tollSchedule.setDiscountFareAmount(1.0);
		tollSchedule.setExtraAxleCharge(0.5);

		Mockito.when(staticDataLoad.getAgencyById(1L)).thenReturn(tempAgency);
		Mockito.when(staticDataLoad.getLaneById(2)).thenReturn(tempLane);
		Mockito.when(staticDataLoad.getAgency("008")).thenReturn(accountAgency);
		// Mockito.when(tollScheduleDao.getTollAmount(Mockito.any(TollFareDto.class))).thenReturn(tollSchedule);

		Double calculatedAmount = tollCalculationServiceImpl.calculateToll(awayTx);

		Assert.isTrue(3.0 == calculatedAmount, "Calculated amount is not correct");
	}

	@Test
	public void testCalculateToll_nysta_scenario() {

		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime paCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime nystaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime mtaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		AwayTransaction awayTx = new AwayTransaction();
		awayTx.setPlazaAgencyId(1);
		awayTx.setPlazaId(1);
		awayTx.setActualClass(22);
		awayTx.setTxDate(tempDateTime.toLocalDate());
		awayTx.setTollRevenueType(1);
		awayTx.setLaneId(2);
		awayTx.setDeviceNo("00812345678");
		awayTx.setTollSystemType("B");
		awayTx.setEntryLaneId(22);
		awayTx.setEntryPlazaId(11);
		awayTx.setTxTimestamp(tempDateTime);
		awayTx.setPostedFareAmount(2.0);
		awayTx.setExpectedRevenueAmount(3.0);
		awayTx.setActualExtraAxles(1);

		Lane tempLane = new Lane();
		tempLane.setLaneId(2);
		tempLane.setCalculateTollAmount("Y");

		Agency tempAgency = new Agency();
		tempAgency.setScheduledPricingFlag("Y");
		tempAgency.setCalculateTollAmount("Y");
		tempAgency.setAgencyShortName("NY");
		tempAgency.setParentAgencyId(2);
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");

		Agency accountAgency = new Agency();
		accountAgency.setAgencyId(2L);
		accountAgency.setDevicePrefix("008");

		TollSchedule tollSchedule = new TollSchedule();
		tollSchedule.setFullFareAmount(2.0);
		tollSchedule.setDiscountFareAmount(1.0);
		tollSchedule.setExtraAxleCharge(0.5);

		Mockito.when(staticDataLoad.getAgencyById(1L)).thenReturn(tempAgency);
		Mockito.when(staticDataLoad.getLaneById(2)).thenReturn(tempLane);
		Mockito.when(staticDataLoad.getAgency("008")).thenReturn(accountAgency);
		//Mockito.when(tollScheduleDao.getTollAmount(Mockito.any(TollFareDto.class))).thenReturn(tollSchedule);

		Double calculatedAmount = tollCalculationServiceImpl.calculateToll(awayTx);

		Assert.isTrue(2.0 == calculatedAmount, "Calculated amount is not correct");
	}

	@Test
	public void testCalculateToll_mta_scenario() {

		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime paCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime nystaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime mtaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		AwayTransaction awayTx = new AwayTransaction();
		awayTx.setPlazaAgencyId(2);
		awayTx.setPlazaId(1);
		awayTx.setActualClass(22);
		awayTx.setTxDate(tempDateTime.toLocalDate());
		awayTx.setTollRevenueType(1);
		awayTx.setLaneId(2);
		awayTx.setDeviceNo("00812345678");
		awayTx.setTollSystemType("B");
		awayTx.setEntryLaneId(22);
		awayTx.setEntryPlazaId(11);
		awayTx.setTxTimestamp(tempDateTime);
		awayTx.setPostedFareAmount(2.0);
		awayTx.setExpectedRevenueAmount(3.0);
		awayTx.setActualExtraAxles(1);

		Lane tempLane = new Lane();
		tempLane.setLaneId(2);
		tempLane.setCalculateTollAmount("Y");

		Agency tempAgency = new Agency();
		tempAgency.setScheduledPricingFlag("Y");
		tempAgency.setCalculateTollAmount("Y");
		tempAgency.setAgencyShortName("MTA");
		tempAgency.setParentAgencyId(2);
		tempAgency.setAgencyId(2L);
		tempAgency.setDevicePrefix("005");

		Agency accountAgency = new Agency();
		accountAgency.setAgencyId(2L);
		accountAgency.setDevicePrefix("008");

		TollSchedule tollSchedule = new TollSchedule();
		tollSchedule.setFullFareAmount(2.0);
		tollSchedule.setDiscountFareAmount(1.0);
		tollSchedule.setExtraAxleCharge(0.5);

		Mockito.when(staticDataLoad.getAgencyById(2L)).thenReturn(tempAgency);
		Mockito.when(staticDataLoad.getLaneById(2)).thenReturn(tempLane);
		Mockito.when(staticDataLoad.getAgency("008")).thenReturn(accountAgency);
		//Mockito.when(tollScheduleDao.getTollAmount(Mockito.any(TollFareDto.class))).thenReturn(tollSchedule);

		Double calculatedAmount = tollCalculationServiceImpl.calculateToll(awayTx);

		Assert.isTrue(2.0 == calculatedAmount, "Calculated amount is not correct");
	}

	@Test
	public void testCalculateToll_away_scenario() {

		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime paCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime nystaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime mtaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		AwayTransaction awayTx = new AwayTransaction();
		awayTx.setPlazaAgencyId(8);
		awayTx.setPlazaId(1);
		awayTx.setActualClass(22);
		awayTx.setTxDate(tempDateTime.toLocalDate());
		awayTx.setTollRevenueType(1);
		awayTx.setLaneId(2);
		awayTx.setDeviceNo("01612345678");
		awayTx.setTollSystemType("B");
		awayTx.setEntryLaneId(22);
		awayTx.setEntryPlazaId(11);
		awayTx.setTxTimestamp(tempDateTime);
		awayTx.setPostedFareAmount(2.0);
		awayTx.setExpectedRevenueAmount(3.0);
		awayTx.setActualExtraAxles(1);

		Lane tempLane = new Lane();
		tempLane.setLaneId(2);
		tempLane.setCalculateTollAmount("Y");

		Agency tempAgency = new Agency();
		tempAgency.setScheduledPricingFlag("Y");
		tempAgency.setCalculateTollAmount("Y");
		tempAgency.setAgencyShortName("MDTA");
		tempAgency.setParentAgencyId(8);
		tempAgency.setAgencyId(8L);
		tempAgency.setDevicePrefix("016");

		Agency accountAgency = new Agency();
		accountAgency.setAgencyId(8L);
		accountAgency.setDevicePrefix("016");
		accountAgency.setParentAgencyId(8);

		TollSchedule tollSchedule = new TollSchedule();
		tollSchedule.setFullFareAmount(2.0);
		tollSchedule.setDiscountFareAmount(1.0);
		tollSchedule.setExtraAxleCharge(0.5);

		Mockito.when(staticDataLoad.getAgencyById(8L)).thenReturn(tempAgency);
		Mockito.when(staticDataLoad.getLaneById(2)).thenReturn(tempLane);
		Mockito.when(staticDataLoad.getAgency("016")).thenReturn(accountAgency);
		//Mockito.when(tollScheduleDao.getTollAmount(Mockito.any(TollFareDto.class))).thenReturn(tollSchedule);

		Double calculatedAmount = tollCalculationServiceImpl.calculateToll(awayTx);

		Assert.isTrue(2.0 == calculatedAmount, "Calculated amount is not correct");
	}

	@Test
	public void testCalculateTollPA() {

		LocalDateTime tempDateTime = LocalDateTime.parse("2021-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime paCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime nystaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);
		LocalDateTime mtaCutOffDateTime = LocalDateTime.parse("2020-10-13 12:00:01.007",
				IctxConstant.DATEFORMATTER_yyyyMMddHHmmssSSS);

		AwayTransaction awayTx = new AwayTransaction();
		awayTx.setPlazaAgencyId(1);
		awayTx.setPlazaId(1);
		awayTx.setActualClass(22);
		awayTx.setTxDate(tempDateTime.toLocalDate());
		awayTx.setTollRevenueType(1);
		awayTx.setLaneId(2);
		awayTx.setDeviceNo("00812345678");
		awayTx.setTollSystemType("B");
		awayTx.setEntryLaneId(22);
		awayTx.setEntryPlazaId(11);
		awayTx.setTxTimestamp(tempDateTime);
		awayTx.setPostedFareAmount(2.0);
		awayTx.setExpectedRevenueAmount(3.0);
		awayTx.setActualExtraAxles(1);

		Lane tempLane = new Lane();
		tempLane.setLaneId(2);
		tempLane.setCalculateTollAmount("N");

		Agency tempAgency = new Agency();
		tempAgency.setScheduledPricingFlag("N");
		tempAgency.setCalculateTollAmount("N");
		tempAgency.setAgencyShortName("PA");
		tempAgency.setParentAgencyId(2);
		tempAgency.setAgencyId(3L);
		tempAgency.setDevicePrefix("005");

		Agency accountAgency = new Agency();
		accountAgency.setAgencyId(2L);
		accountAgency.setDevicePrefix("008");

		TollSchedule tollSchedule = new TollSchedule();
		tollSchedule.setFullFareAmount(2.0);
		tollSchedule.setDiscountFareAmount(1.0);
		tollSchedule.setExtraAxleCharge(0.5);

		Mockito.when(staticDataLoad.getAgencyById(1L)).thenReturn(tempAgency);
		Mockito.when(staticDataLoad.getLaneById(2)).thenReturn(tempLane);
		Mockito.when(staticDataLoad.getAgency("008")).thenReturn(accountAgency);
		// Mockito.when(tollScheduleDao.getTollAmount(Mockito.any(TollFareDto.class))).thenReturn(tollSchedule);

		Double calculatedAmount = tollCalculationServiceImpl.calculateToll(awayTx);

		Assert.isTrue(2.0 == calculatedAmount, "Calculated amount is not correct");
	}
*/
}
