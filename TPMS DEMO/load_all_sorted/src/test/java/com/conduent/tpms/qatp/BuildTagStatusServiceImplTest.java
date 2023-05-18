package com.conduent.tpms.qatp;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.service.impl.BuildTagStatusServiceImpl;

/**
 * Test class for BuildTagStatusServiceImpl
 * 
 * @author Urvashi C
 *
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuildTagStatusServiceImplTest {
	
	private static final Logger log = LoggerFactory.getLogger(BuildTagStatusServiceImplTest.class);
	
	@InjectMocks
	BuildTagStatusServiceImpl buildTagStatusService;

	@Test
	public void buildPaStatusAsInvalidTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("deviceNo");
		tagStatusSortedRecord.setSpeedAgency(10);
		tagStatusSortedRecord.setAgencyId(Constants.NYSTA_AGENCY_ID);
		tagStatusSortedRecord.setNystaNonRevCount(1);
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		
		buildTagStatusService.buildPaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getPaTagStatus());
		Assertions.assertEquals(Constants.TS_NEG_VAL, tagStatusSortedRecord.getPaTagStatus());
	}
	
	@Test
	public void buildPaStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("deviceNo");
		tagStatusSortedRecord.setSpeedAgency(-10);
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOW);
		tagStatusSortedRecord.setPaNonRevCount(20);
		
		buildTagStatusService.buildPaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getPaTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getPaTagStatus());
	}

	@Test
	public void buildMTARioStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setRioNonRevCount(10);
		tagStatusSortedRecord.setMtafCount(1);
		tagStatusSortedRecord.setGovtCount(1);
		tagStatusSortedRecord.setMtaNonRevCount(1);
		tagStatusSortedRecord.setTbnrfpCount(0);
		tagStatusSortedRecord.setMtafCount(100);
		
		buildTagStatusService.buildMtaRioStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRioTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getRioTagStatus());
	}
	
	@Test
	public void buildMTARioStatusDefaultTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_INVALID);
		
		buildTagStatusService.buildMtaRioStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRioTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getRioTagStatus());
	}
	
	@Test
	public void buildMTAStatusAsInvalidTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setSpeedAgency(3);
		tagStatusSortedRecord.setTbnrfpCount(1);
		tagStatusSortedRecord.setRevPlanCount(0);
		
		buildTagStatusService.buildMtaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getMtaTagStatus());
	}
	
	@Test
	public void buildMTAStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setSpeedAgency(-1);
		tagStatusSortedRecord.setTbnrfpCount(-1);
		tagStatusSortedRecord.setRevPlanCount(1);
		tagStatusSortedRecord.setMtaNonRevCount(100);
		
		buildTagStatusService.buildMtaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getMtaTagStatus());
	}

	@Test
	public void builddNysbaStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setNysbaNonRevCount(1);
		
		buildTagStatusService.buildNysbaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNysbaTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getNysbaTagStatus());
	}
	
	@Test
	public void builddNysbaStatusAsDefaultTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		tagStatusSortedRecord.setNysbaNonRevCount(1);
		
		buildTagStatusService.buildNysbaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNysbaTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getNysbaTagStatus());
	}

	
	@Test
	public void builddNystaStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
		tagStatusSortedRecord.setDeviceNo("00800012042");
		tagStatusSortedRecord.setAgencyId(1);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setNysbaNonRevCount(1);
		tagStatusSortedRecord.setNystaNonRevCount(0);
		tagStatusSortedRecord.setAllPlans(152);
		
		int nystanystacount = tagStatusSortedRecord.getNysbaNonRevCount() + tagStatusSortedRecord.getNystaNonRevCount();
		log.info("nystanysbacount...{}",nystanystacount);
		if(nystanystacount > 0) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.TS_GOOD);
		}
		
		buildTagStatusService.buildNystaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNystaTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getNystaTagStatus());
	}

	@Test
	public void buildNystaStatusAsBadTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		tagStatusSortedRecord.setNystaNonRevCount(-1);
		
		tagStatusSortedRecord.setNystaPostPaidComlCount(1);
		tagStatusSortedRecord.setPostPaidStatus(2);
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setAgencyId(1);
		
		buildTagStatusService.buildNystaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNystaTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getNystaTagStatus());
	}

	@Test
	public void buildNystaStatusAsBadItagStatusIsBadTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
		tagStatusSortedRecord.setNystaPostPaidComlCount(-1);
		tagStatusSortedRecord.setPostPaidStatus(2);
		tagStatusSortedRecord.setNystaAnnualPlanCount(1);
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setAgencyId(1);
		tagStatusSortedRecord.setNysbaNonRevCount(1);
		tagStatusSortedRecord.setNystaNonRevCount(0);
		tagStatusSortedRecord.setAllPlans(152);
		
		int nystanystacount = tagStatusSortedRecord.getNysbaNonRevCount() + tagStatusSortedRecord.getNystaNonRevCount();
		log.info("nystanysbacount...{}",nystanystacount);
		if(nystanystacount > 0) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.TS_GOOD);
		}
		
		buildTagStatusService.buildNystaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNystaTagStatus());
		Assertions.assertEquals(Constants.TS_GOOD, tagStatusSortedRecord.getNystaTagStatus());
	}

	@Test
	public void buildNystaStatusAsZeroItagStatusIsInvalidTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		tagStatusSortedRecord.setSpeedAgency(3);
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setAgencyId(1);
		
		buildTagStatusService.buildNystaStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getNystaTagStatus());
		Assertions.assertEquals(Constants.FS_AUTOPAY, tagStatusSortedRecord.getNystaTagStatus());
	}
	
	@Test
	public void buildRetailerStatusUTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setRetailTagStatus(Constants.UNREGISTERED);
		tagStatusSortedRecord.setFirstTollDate(null);
		
		buildTagStatusService.buildRetailerStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRetailerStatus());
		Assertions.assertEquals(Constants.RETAILER_STATUS_U, tagStatusSortedRecord.getRetailerStatus());
	}
	
	@Test
	public void buildRetailerStatusWTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setRetailTagStatus(Constants.UNREGISTERED);
		tagStatusSortedRecord.setFirstTollDate(LocalDate.now());
		
		buildTagStatusService.buildRetailerStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRetailerStatus());
		Assertions.assertEquals(Constants.RETAILER_STATUS_W, tagStatusSortedRecord.getRetailerStatus());
	}
	
	@Test
	public void buildRetailerStatusVTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setRetailTagStatus(Constants.UNREGISTERED);
		tagStatusSortedRecord.setFirstTollDate(LocalDate.now().minusDays(7));
		
		buildTagStatusService.buildRetailerStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRetailerStatus());
		Assertions.assertEquals(Constants.RETAILER_STATUS_V, tagStatusSortedRecord.getRetailerStatus());
	}
	
	@Test
	public void buildRetailerStatusDefaultTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setRetailTagStatus(Constants.REGISTERED);
		tagStatusSortedRecord.setFirstTollDate(LocalDate.of(2021, 01, 01));
		
		buildTagStatusService.buildRetailerStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getRetailerStatus());
		Assertions.assertEquals(Constants.RETAILER_STATUS_DEFAULT, tagStatusSortedRecord.getRetailerStatus());
	}
	
	@Test
	public void buildITAGStatusGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.YES);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setFinancialStatus(Constants.FS_GOOD);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getItagTagStatus());
	}
	
	@Test
	public void buildITAGStatusInvalidTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.NO);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setRetailerStatus(Constants.RETAILER_STATUS_W);
		tagStatusSortedRecord.setFinancialStatus(Constants.FS_ZERO);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getItagTagStatus());
	}
	
	@Test
	public void buildITAGStatusAsFinanceStatusTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.YES);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setRetailerStatus("A");
		
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setRebillAmount(10.0);
		tagStatusSortedRecord.setPrimaryRebillPayType(Constants.ZERO_VALUE);
		tagStatusSortedRecord.setFinancialStatus(Constants.FS_ZERO);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_LOST_STOLEN, tagStatusSortedRecord.getItagTagStatus());
	}
	
	@Test
	public void buildITAGStatusAsGoodTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.NO);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setRetailerStatus("A");
		
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setRebillAmount(10.0);
		tagStatusSortedRecord.setPrimaryRebillPayType(Constants.RPT_VISA);
		tagStatusSortedRecord.setFinancialStatus(Constants.FS_GOOD);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getItagTagStatus());
	}
	
	@Test
	public void buildITAGStatusAsGoodWhenRebillAmountIsNegativeTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.NO);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setRetailerStatus("A");
		
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setRebillAmount(-10.0);
		tagStatusSortedRecord.setPrimaryRebillPayType(Constants.RPT_ACHCPPT);
		tagStatusSortedRecord.setFinancialStatus(Constants.FS_GOOD);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_INVALID, tagStatusSortedRecord.getItagTagStatus());
	}

	@Test
	public void buildITAGStatusAsBadTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);	
		tagStatusSortedRecord.setIsPrevalidated(Constants.YES);
		tagStatusSortedRecord.setCurrentBalance(null);
		tagStatusSortedRecord.setDeviceNo("00400010000");
		tagStatusSortedRecord.setRetailerStatus("A");
		
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setRebillAmount(-10.0);
		tagStatusSortedRecord.setPrimaryRebillPayType(Constants.RPT_VISA);
		tagStatusSortedRecord.setAccountStatus(0);
		
		buildTagStatusService.buildItagStatus(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getItagTagStatus());
		Assertions.assertEquals(Constants.TS_LOST_STOLEN, tagStatusSortedRecord.getItagTagStatus());
	} 
	
}
