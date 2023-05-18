package com.conduent.tpms.iag;

import java.time.LocalDateTime;
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
import com.conduent.tpms.iag.service.impl.PAFileCreationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PAFileCreationServiceImplTest {
	
	@InjectMocks
	PAFileCreationServiceImpl paFileCreationServiceImpl;
	
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
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = Constants.HOME_AGENCY_ID;
		//test method
		String fileagency = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(fileagency);
		Assertions.assertEquals(expectedVal, fileagency);
	}
	
	
	@Test
	void doFieldMappingFUnderscoreTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_UNERSCORE);
		fMapping.setFormat("_");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = fMapping.getDefaultValue();
		//test method
		String fileunderscore = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(fileunderscore);
		Assertions.assertEquals(expectedVal, fileunderscore);
	}
	
	
	
	//@Test
	void doFieldMappingFDateTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_DATE_TIME);
		fMapping.setFormat("YYYYMMddHHMMss");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String fileDateTime = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(fileDateTime);
		Assertions.assertEquals(expectedVal, fileDateTime);
	}
	
	@Test
	void doFieldMappingFextensionTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_EXTENSION);
		fMapping.setFormat(".ITAG");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = fMapping.getDefaultValue();
		//test method
		String fileextension = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(fileextension);
		Assertions.assertEquals(expectedVal, fileextension);
	}
	
	@Test
	void doFieldMappingHFileTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FILE_TYPE);
		fMapping.setFormat("ITAG");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = fMapping.getDefaultValue();
		//test method
		String hfileType = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(hfileType);
		Assertions.assertEquals(expectedVal, hfileType);
	}
	
	@Test
	void doFieldMappingHAgencyTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FROM_AGENCY_ID);
		fMapping.setFormat(Constants.HOME_AGENCY_ID);
		fMapping.setDefaultValue(Constants.HOME_AGENCY_ID);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = Constants.HOME_AGENCY_ID;
		//test method
		String hagency = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(hfileType);
		Assertions.assertEquals(expectedVal, hagency);
	}
	
	//@Test
	void doFieldMappingHdateTimeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FILE_DATE_TIME);
		fMapping.setFormat("YYYYMMddHHMMss");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String hdatetime = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(hfileType);
		Assertions.assertEquals(expectedVal, hdatetime);
	}
	
	@Test
	void doFieldMappingHRecordCountTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_RECORD_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(10);
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "0000000000";
		//test method
		String hrecordcount = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(hfileType);
		Assertions.assertEquals(expectedVal, hrecordcount);
	}
	
//	@Test
//	void doFieldMappingHCountStat1Test() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_COUNT_STAT1);
//		fMapping.setJustification("L");
//		fMapping.setPadCharacter("0");
//		fMapping.setFieldLength(8);
//		TAGDevice tagDevice = new TAGDevice();	
//		String expectedVal = "00000000";
//		//test method
//		String hcountstat1 = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		//Assertions.assertNotNull(hfileType);
//		Assertions.assertEquals(expectedVal, hcountstat1);
//	}
//	
//	@Test
//	void doFieldMappingHCountStat2Test() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_COUNT_STAT2);
//		fMapping.setJustification("L");
//		fMapping.setPadCharacter("0");
//		fMapping.setFieldLength(8);
//		TAGDevice tagDevice = new TAGDevice();	
//		String expectedVal = "00000000";
//		//test method
//		String hcountstat2 = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		//Assertions.assertNotNull(hfileType);
//		Assertions.assertEquals(expectedVal, hcountstat2);
//	}
//
//	@Test
//	void doFieldMappingHCountStat3Test() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_COUNT_STAT3);
//		fMapping.setJustification("L");
//		fMapping.setPadCharacter("0");
//		fMapping.setFieldLength(8);
//		TAGDevice tagDevice = new TAGDevice();	
//		String expectedVal = "00000000";
//		//test method
//		String hcountstat3 = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		//Assertions.assertNotNull(hfileType);
//		Assertions.assertEquals(expectedVal, hcountstat3);
//	}
//	
//	@Test
//	void doFieldMappingHCountStat4Test() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_COUNT_STAT4);
//		fMapping.setJustification("L");
//		fMapping.setPadCharacter("0");
//		fMapping.setFieldLength(8);
//		TAGDevice tagDevice = new TAGDevice();	
//		String expectedVal = "00000000";
//		//test method
//		String hcountstat4 = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		//Assertions.assertNotNull(hfileType);
//		Assertions.assertEquals(expectedVal, hcountstat4);
//	}
	
	@Test
	void doFieldMappingDTagOwnAgencyTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_SERIAL_NUMBER_ID);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(4);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		//String expectedVal = tagDevice.getDeviceNo();
		String expectedVal = "0000";
		//test method
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagStatusTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_STATUS);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(1);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "0";
		//test method
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDAccInfoTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_ACC_INFO);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(6);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "000000";
		//test method
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		//Assertions.assertNotNull(value);
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
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTagTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_TYPE);
		fMapping.setFieldLength(1);
		fMapping.setDefaultValue("*");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "*";
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
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
		String value = paFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertEquals(expectedVal, value);
	}
}
