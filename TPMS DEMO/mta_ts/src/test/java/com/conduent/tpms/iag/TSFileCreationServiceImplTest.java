package com.conduent.tpms.iag;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	
	//@Test
	void doFieldMappingFDateTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_DATE_TIME);
		fMapping.setFormat("YYYYMMddHHMMss");
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String fileDateTime = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(fileDateTime);
		Assertions.assertEquals(expectedVal, fileDateTime);
	}
	
	@Test
	void doFieldMappingFSerialTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_SERIAL);
		fMapping.setDefaultValue("001");
		TAGDevice tagDevice = new TAGDevice();
		String expectedVal = "001";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingFDotTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_DOT);
		fMapping.setDefaultValue(".");
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
		fMapping.setDefaultValue(Constants.TS);
		TAGDevice tagDevice = new TAGDevice();	
		String expectedVal = Constants.TS;
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHRecTypeTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_RECORD_TYPE_CODE);
		fMapping.setDefaultValue("H");
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
		fMapping.setDefaultValue("          ");
		TAGDevice tagDevice = new TAGDevice();
		//String expectedVal = "          ";
		String expectedVal = "";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHAgeIdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_AGENCY_ID);
		fMapping.setDefaultValue("008");
		TAGDevice tagDevice = new TAGDevice();		
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
	}

	@Test
	void doFieldMappingHPlazaIdTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_PLAZA_ID);
		fMapping.setDefaultValue("01");
		TAGDevice tagDevice = new TAGDevice();		
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
	}
	
//	@Test
//	void doFieldMappingHDwnldTypeTest() {
//		MappingInfoDto fMapping = new MappingInfoDto();
//		fMapping.setFieldName(Constants.H_LAST_DOWNLOAD_TS);
//		TAGDevice tagDevice = new TAGDevice();		
//		tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
//		verify(iagDao, times(1)).getLastDwnldTS();
//	}
	
	//@Test
	void doFieldMappingHPCurrentTSTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_CURRENT_TS);
		fMapping.setFormat("YYYYMMddHHMMss");
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHStolenTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_STOLEN_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHLostTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_LOST_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHInvalidTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_INVALID_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHInventoryTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_INVENTORY_DEVICE_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHReplenishTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_REPLENISH_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHMinusTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_MINUS_BAL_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	@Test
	void doFieldMappingHValidTagTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_VALID_TAG_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	@Test
	void doFieldMappingHRecordCountTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_RECORD_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(7);
		TAGDevice tagDevice = new TAGDevice();		
		String expectedVal = "0000000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	/*
	 * @Test void doFieldMappingDRecordTypeTest() { MappingInfoDto fMapping = new
	 * MappingInfoDto(); fMapping.setFieldName(Constants.D_RECORD_TYPE_START);
	 * TAGDevice tagDevice = new TAGDevice(); //String expectedVal = ""; String
	 * expectedVal = "D"; //test method String value =
	 * tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
	 * System.out.println(value); Assertions.assertNotNull(value);
	 * Assertions.assertEquals(expectedVal, value); }
	 */
	
	
	@Test
	void doFieldMappingDTagOwnAgencyTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_TAG_AGENCY_ID);
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(3);
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setDevicePrefix("0008");
		String expectedVal = "008";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDSerialNoTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_SERIAL_NO);
		fMapping.setFieldLength(10);
		fMapping.setPadCharacter("0");
		fMapping.setJustification("L");
		TAGDevice tagDevice = new TAGDevice();
		tagDevice.setSerialNo(100000);
		//String expectedVal = "00000186a0";
		String expectedVal = "0000100000";
		//test method
		String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	/*
	 * @Test void doFieldMappingDTagClassTest() { MappingInfoDto fMapping = new
	 * MappingInfoDto(); fMapping.setFieldName(Constants.D_TAG_IAG_CLASS);
	 * fMapping.setFieldLength(3); fMapping.setJustification("L");
	 * fMapping.setPadCharacter("0"); TAGDevice tagDevice = new TAGDevice();
	 * 
	 * tagDevice.setIagCodedClass(0); String expectedVal = "000"; //test method
	 * String value = tsFileCreationServiceImpl.doFieldMapping(fMapping, tagDevice);
	 * Assertions.assertNotNull(value); Assertions.assertEquals(expectedVal, value);
	 * }
	 */
	
}
