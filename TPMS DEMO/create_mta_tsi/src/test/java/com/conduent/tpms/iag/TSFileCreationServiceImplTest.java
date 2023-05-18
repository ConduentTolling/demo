package com.conduent.tpms.iag;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;
import com.conduent.tpms.iag.service.impl.TSFileCreationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TSFileCreationServiceImplTest {

	@InjectMocks
	TSFileCreationServiceImpl tsFileCreationServiceImpl;
	
	@Mock
	IAGDao iagDao;

	@Mock
	IAGFileCreationSevice iagFileCreationSevice;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Test
	void doFieldMappingFAgencyTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FROM_AGENCY_ID);
		fMapping.setDefaultValue(Constants.HOME_AGENCY_ID);
		fMapping.setFormat(Constants.HOME_AGENCY_ID);
		fMapping.setFieldLength(4);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = Constants.HOME_AGENCY_ID;
		//test method
		String fileagency = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(fileagency);
		Assertions.assertEquals(expectedVal, fileagency);
	}
	
	//@Test
	void doFieldMappingFDateTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_DATE_TIME);
		fMapping.setFormat("yyyyMMddHHmmss");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		System.out.println("Expected Value:"+expectedVal);
		//test method
		String fileDateTime = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(fileDateTime);
		Assertions.assertEquals(expectedVal, fileDateTime);
	}
	
	@Test
	void doFieldMappingFDotTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_DOT);
		fMapping.setDefaultValue(".");
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = Constants.DOT;
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingFExtnTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_EXTENSION);
		fMapping.setDefaultValue(Constants.TSI);
		fMapping.setFieldLength(3);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = Constants.TSI;
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHRecTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_REC_TYPE);
		fMapping.setDefaultValue("H");
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = "H";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHSerCenterTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_SERVICE_CENTER);
		TAGDevice tagDevice = new TAGDevice();
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "");
	}
	
	@Test
	void doFieldMappingHHubIdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_HUB_ID);
		fMapping.setDefaultValue("");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHAgencyIdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_AGENCY_ID);
		fMapping.setFieldLength(4);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setDefaultValue("0008");
		TAGDevice tagDevice = new TAGDevice();
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		String expectedVal = "0008";
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}

//	@Test
//	void doFieldMappingHPlazaIdTest() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_PLAZA_ID);
//		fMapping.setDefaultValue("01");
//		TAGDevice tagDevice = new TAGDevice();		
//		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		Assertions.assertNotNull(value);
//	}
	
	//@Test
	void doFieldMappingHLastTSTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_LAST_TS);
		TAGDevice tagDevice = new TAGDevice();		
		tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		verify(iagDao, times(1)).getLastDwnldTS("YYYYMMddHHMMss", false);
	}
	
	//@Test
	void doFieldMappingHPCurrentTSTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_CURRENT_TS);
		fMapping.setFormat("yyyyMMddHHmmss");
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHStolenTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_STOLEN);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHLostTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_LOST);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHInvalidTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_INVALID);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHInventoryTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_INVENTORY);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHReplenishTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_REPLENISH);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHMinbalTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_MINBAL);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHValidTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_VALID);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHRecordCountTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_TOTAL);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDRecTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_REC_TYPE);
		fMapping.setDefaultValue("D");
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		String expectedValue = "D";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, expectedValue);
	}
	
	@Test
	void doFieldMappingDRecNumTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_REC_NUM);
		fMapping.setFieldLength(10);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		TAGDevice tagDevice = new TAGDevice();
		
		tagDevice.setIagCodedClass(0);
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagAgencyIdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_AGENCY_ID);
		fMapping.setFieldLength(4);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setDevicePrefix("016");
		String expectedVal = "0016";
		//test method
		String tagAgencyId = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(tagAgencyId);
		Assertions.assertEquals(expectedVal, tagAgencyId);
	}
	
	@Test
	void doFieldMappingDTagSerialNumTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_SERIAL_NUM);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		//String expectedVal = tagDevice.getDeviceNo();
		String expectedVal = "0000000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagClassTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_CLASS);
		fMapping.setFieldLength(4);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		TAGDevice tagDevice = new TAGDevice();
		//tagDevice.setIagCodedClass(0);
		tagDevice.setTagClass("0016");
		String expectedVal = "0016";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDRevTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_REV_TYPE);
		TAGDevice tagDevice = new TAGDevice();
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "");
	}

	@Test
	void doFieldMappingDTagTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_TYPE);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setTagType("*");
		String expectedVal = "";
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
	
	//@Test
	void doFieldMappingDCI1TagStatusTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI1_TAG_STATUS);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "I");
	}
	
	@Test
	void doFieldMappingDCI2VesTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI2_VES);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "W");
	}
	
	@Test
	void doFieldMappingDCI3FutureUseTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI3_FUTURE_USE);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "");
	}
	
	@Test
	void doFieldMappingDCI4AccountDeviceStatusTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI4_ACCOUNT_DEVICE_STATUS);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "R");
	}
	
	@Test
	void doFieldMappingDCI5DiscountPlanFlagORTTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI5_DISCOUNT_PLAN_FLAG_ORT);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "C");
	}
	
	@Test
	void doFieldMappingDCI6DiscountPlanFlagCBDTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_CI6_DISCOUNT_PLAN_FLAG_CBD);
		fMapping.setFieldLength(8);
		fMapping.setDefaultValue("00000000");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setMtaCtrlStr("IW0RC0)");
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "00000000");
	}
	
	@Test
	void doFieldMappingDTagAccountNoTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_ACCOUNT_NO);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(50);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setAccountNo("22580");
		String expectedVal = "00000000000000000000000000000000000000000000022580";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagAccountTypeCdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_AC_TYPE_IND);
		fMapping.setFieldLength(1);
		fMapping.setDefaultValue("*");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setAccountTypeCd("P");
		String expectedVal = "P";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	//@Test
	void doFieldMappingDTagHomeAgencyTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_HOME_AGENCY);
		fMapping.setFieldLength(4);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setDevicePrefix("0021");
		String expectedVal = "0021";
		//test method
		String tagAgencyId = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(tagAgencyId);
		Assertions.assertEquals(expectedVal, tagAgencyId);
	}
	
	@Test
	void doFieldMappingDTagProtocolTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_PROTOCOL);
		fMapping.setPadCharacter(" ");
		fMapping.setFieldLength(3);
		fMapping.setJustification("R");
		fMapping.setDefaultValue("***");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setTagProtocol("TS");
		String expectedVal = "TS ";
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagMountTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_MOUNT);
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setTagMount("V");
		String expectedVal = "V";
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDAccInfoTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_ACCT_INFO);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(6);
		fMapping.setJustification("L");
		fMapping.setDefaultValue("000000");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDFillerField1Test() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_FILLER_FIELD1);
		TAGDevice tagDevice = new TAGDevice();
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "");
	}
	
	@Test
	void doFieldMappingDFillerField2Test() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_FILLER_FIELD2);
		TAGDevice tagDevice = new TAGDevice();
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(value, "");
	}
	
	@Test
	void doFieldMappingTRecTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.T_REC_TYPE);
		fMapping.setDefaultValue("E");
		fMapping.setFieldLength(1);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "E";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
}
