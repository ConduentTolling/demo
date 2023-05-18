package com.conduent.tpms.qatp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.service.impl.PlanStringServiceImpl;

/**
 * Test class for PlanStringServiceImpl
 * 
 * @author Urvashi C
 *
 */

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PlanStringServiceImplTest {
	
	
	@InjectMocks
	PlanStringServiceImpl planStringServiceImpl;

	/**
	 * Test cases for DeviceStatus = ACTIVE
	 */
	
	@Test
	public void buildPaCtrlStrWhenPaTagStatusIsGood() {
		
		String paPlanString="000V00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setPaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaTagStatusIsLow() {
		
		String paPlanString="000M00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setPaTagStatus(Constants.TS_LOW);
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaTagStatusIsZero() {
		
		String paPlanString="000N00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setPaTagStatus(Constants.FS_ZERO);
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaTagStatusIsNotAvailable() {
		
		String paPlanString="II0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	
	/**
	 * Test cases for DeviceStatus = LOST 
	 */

	@Test
	public void buildPaCtrlStrWhenDeviceStatusIsLost() {
		
		String paPlanString="LI0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_LOST);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPAstringforRVKF() {
		
		String pastrng = "LI0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setSpeedAgency(1);
		tagStatusSortedRecord.setAccountStatus(Constants.RVKF);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertEquals(pastrng, tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
	}
	
	
	
/*	
 * Won't work because LOST and STOELEN both are 4
	@Test
	public void buildPaCtrlStrWhenDeviceStatusIsStolen() {
		
		String paPlanString="SI0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.STOLEN);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
*/	
	
	@Test
	public void buildPaCtrlStrDefault() {
		
		String paPlanString="RI0000";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_INVENTORY);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	
	/**
	 * Test cases for DeviceStatus = ACTIVE And SPEED > 0
	 */
	
	@Test
	public void buildPaCtrlStrWhenDeviceStatusIsActiveSpeedGrtr0() {
		
		String paPlanString="IW0S00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setSpeedAgency(4);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	/**
	 * Next if
	 */
	
	@Test
	public void buildPaCtrlStrWhenPaNonRevCountIsGreater0() {
		
		String paPlanString="II0R80";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaNonRevCount(1);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaBridgesCountIsGreater0() {
		
		String paPlanString="II0R70";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaBridgesCount(1);
		tagStatusSortedRecord.setPaSiBridgesCount(1);
        tagStatusSortedRecord.setPaCarpoolCount(1);
		tagStatusSortedRecord.setPaNonRevCount(-1);
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	
	@Test
	public void buildPaCtrlStrWhenPaSiBridgesCountIsGreater0() {
		
		String paPlanString="II0R80";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaNonRevCount(1);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaCarpoolCountIsGreater0() {
		String paPlanString="II0R60";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
        tagStatusSortedRecord.setPaCarpoolCount(1);
        tagStatusSortedRecord.setPaSiBridgesCount(1);
		tagStatusSortedRecord.setPaNonRevCount(-1);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}

	@Test
	public void buildPaCtrlStrWhenOnlyPaCarpoolCountIsGreater0() {
		String paPlanString="II0R40";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaBridgesCount(-1);
        tagStatusSortedRecord.setPaCarpoolCount(1);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenPaAndPaSiBridgesCountIsGreater0() {
		String paPlanString="II0R30";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaBridgesCount(1);
		tagStatusSortedRecord.setPaSiBridgesCount(1);
        tagStatusSortedRecord.setPaCarpoolCount(-1);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenOnlyPaSiBridgesCountIsGreater0() {
		String paPlanString="II0R20";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaBridgesCount(-1);
		tagStatusSortedRecord.setPaSiBridgesCount(1);
        tagStatusSortedRecord.setPaCarpoolCount(-1);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildPaCtrlStrWhenOnlyPaBridgesCountIsGreater0() {
		String paPlanString="II0R10";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPaBridgesCount(1);
		tagStatusSortedRecord.setPaSiBridgesCount(-1);
        tagStatusSortedRecord.setPaCarpoolCount(-1);
		tagStatusSortedRecord.setDeviceNo("00300010062");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildPaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getTsCtrlStr());
		Assertions.assertEquals(paPlanString, tagStatusSortedRecord.getTsCtrlStr());
	}
	
	@Test
	public void buildMtastringforRVKF() {
		
		String mtastrng = "LW0RC0";
		String riostrng = "LW0RC0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00400010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setSpeedAgency(1);
		tagStatusSortedRecord.setAccountStatus(Constants.RVKF);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertEquals(mtastrng, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(riostrng, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	/**
	 * Test cases for DeviceStatus = ACTIVE, PrevalidationFlag = YES
	 */
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActive() {
		
		String mtaCtrlStr = "000V00";
		String rioCtrlStr = "000V00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveMtaTagStatusLow() {
		String mtaCtrlStr = "000M00";
		String rioCtrlStr = "000M00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_LOW);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrAsDefaultWhenDeviceStatusIsActive() {
		String mtaCtrlStr = "IW0R00";
		String rioCtrlStr = "IW0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveAndAgencyIsNotMTA() {
		
		String mtaCtrlStr = "000VC0";
		String rioCtrlStr = "000VC0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00400010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveBRCountGrtr0() {
		
		String mtaCtrlStr = "000VB0";
		String rioCtrlStr = "000VB0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setBrCount(1);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveQRCountGrtr0() {
		
		String mtaCtrlStr = "000VQ0";
		String rioCtrlStr = "000VQ0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setQrCount(1);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveSIRCountGrtr0() {
		
		String mtaCtrlStr = "000VS0";
		String rioCtrlStr = "000VS0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setSirCount(1);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsActiveRRCountGrtr0() {
		
		String mtaCtrlStr = "000VR0";
		String rioCtrlStr = "000VR0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.YES);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setRrCount(1);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	/**
	 * Test cases for DeviceStatus = Not ACTIVE And PrevalidationFlag = NO
	 */
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsLOST() {
		
		String mtaCtrlStr = "LW0R00";
		String rioCtrlStr = "LW0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_LOST);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setMtagPlanStr(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsINVENTORY() {
		
		String mtaCtrlStr = "R00000";
		String rioCtrlStr = "R00000";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_INVENTORY);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusNone() {
		
		String mtaCtrlStr = "IW0R00";
		String rioCtrlStr = "IW0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setMtagPlanStr(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	/**
	 * Test cases for DeviceStatus = Not ACTIVE And devicePrefix=008
	 */
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsLOST_Agency004() {
		
		String mtaCtrlStr = "LW0RC0";
		String rioCtrlStr = "LW0RC0";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00400010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_LOST);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setMtagPlanStr(Constants.TS_GOOD);
		tagStatusSortedRecord.setAgencyId(8);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	/**
	 * Test cases for DeviceStatus = ACTIVE And SpeedAgency >0
	 */
	
	@Test
	public void buildMtaCtrlStrWhenDeviceStatusIsLOST_SpeedAgencyGrtr0() {
		
		String mtaCtrlStr = "IW0R00";
		String rioCtrlStr = "IW0R00";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setDeviceNo("00800010062");
		tagStatusSortedRecord.setDeviceStatus(Constants.DS_ACTIVE);
		tagStatusSortedRecord.setPrevalidationFlag(Constants.NO);
		tagStatusSortedRecord.setSpeedAgency(1);
		tagStatusSortedRecord.setAccountStatus(0);
		
		planStringServiceImpl.buildmtaCtrlStr(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertEquals(mtaCtrlStr, tagStatusSortedRecord.getMtaCtrlStr());
		Assertions.assertNotNull(tagStatusSortedRecord.getRioCtrlStr());
		Assertions.assertEquals(rioCtrlStr, tagStatusSortedRecord.getRioCtrlStr());
	}
	
	
	/**
	 * Test cases for MTAG plan string
	 * 
	 */
	
	@Test
	public void buildMtaPlanStringStandard() {
		
		int mtagPlanStr = 64;
		String planString = "00020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountType(Constants.PRIVATE);
		tagStatusSortedRecord.setPlansStr(planString);
		
		try {
			planStringServiceImpl.buildMtagPlanString(tagStatusSortedRecord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtagPlanStr());
		Assertions.assertEquals(mtagPlanStr, tagStatusSortedRecord.getMtagPlanStr());
	}
	
	
	
/*		
	@Test
	public void buildMtaPlanStringResident() {
		
		int mtagPlanStr = 320;
		String planString = "00340000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		TagStatusSortedRecord tagStatusSortedRecord = new  TagStatusSortedRecord();
		tagStatusSortedRecord.setPlansStr(planString);
		
		planStringServiceImpl.buildMtagPlanString(tagStatusSortedRecord);
		
		Assertions.assertNotNull(tagStatusSortedRecord.getMtagPlanStr());
		Assertions.assertEquals(mtagPlanStr, tagStatusSortedRecord.getMtagPlanStr());
	}	
*/		
		
}
