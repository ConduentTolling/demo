package com.conduent.tpms.inrx;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.inrx.constants.Constants;
import com.conduent.tpms.inrx.dao.INRXDao;
import com.conduent.tpms.inrx.model.Agency;
import com.conduent.tpms.inrx.model.FileStats;
import com.conduent.tpms.inrx.model.MappingInfoDto;
import com.conduent.tpms.inrx.model.TxDetailRecord;
import com.conduent.tpms.inrx.service.impl.InrxProcessServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class InrxProcessServiceImplTest {

	@InjectMocks
	InrxProcessServiceImpl inrxProcessServiceImpl;
	
	@Mock
	InrxProcessServiceImpl mockservice;
	
	@Mock
	INRXDao inrxDao;
	
	@Mock
	Agency tempAgency;
	
	@Mock
	FileStats fileStatsRecord;
	
	
	

	//doFieldMapping(MappingInfoDto fMapping, TxDetailRecord txRecord, FileStats fileRecord) {
	@Test
	void doFieldMappingFDateTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_DATE_TIME);
		fMapping.setFormat("YYYYMMddHHMMss");
		String expectedVal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String fileDateTime = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(fileDateTime);
		Assertions.assertEquals(expectedVal, fileDateTime);
	}
	
	@Test
	void doFieldMappingFSerialTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_UNERSCORE);
		fMapping.setDefaultValue("_");
		String expectedVal = "_";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingFDotTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_DOT);
		fMapping.setDefaultValue(".");
		String expectedVal = ".";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingFExtnTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.F_FILE_EXTENSION);
		fMapping.setDefaultValue(Constants.TEMP);
		String expectedVal = Constants.TEMP;
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHFileTypeTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FILE_TYPE);
		String expectedVal = Constants.INRX;
		FileStats fileRecord = new FileStats();
		//fileRecord.setFileType(Constants.FILE_EXTENSION_INTX);
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, fileRecord,tempAgency);
//		Assertions.assertNotNull(value);
//		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHFromAgencyIdTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FROM_AGENCY_ID);
		fMapping.setDefaultValue(String.valueOf(Constants.HOME_AGENCY_ID));
		String expectedVal = "2";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHDateTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_FILE_DATE_TIME);
		fMapping.setFormat("YYYYMMddHHMMss");
		String expectedVal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
		//test method
		String fileDateTime = inrxProcessServiceImpl.doFieldMapping(fMapping, null, null,tempAgency);
		Assertions.assertNotNull(fileDateTime);
		Assertions.assertEquals(expectedVal, fileDateTime);
	}
	

	@Test
	void doFieldMappingHRecordCountTest() {
		tempAgency.setAgencyId(16L);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_RECORD_COUNT);
		fMapping.setJustification("L");
		fMapping.setPadCharacter("0");
		fMapping.setFieldLength(8);
		fileStatsRecord.setEpsReconCount(0);
		String expectedVal = "00000000";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, fileStatsRecord,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingHSequenceTest() {
		tempAgency.setAgencyId(16L);
		FileStats fileRecord = new FileStats();
		fileRecord.setFileSeqNumber(01);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.H_INTX_FILE_NUM);
		fMapping.setPadCharacter("0");
		fMapping.setJustification("L");
		fMapping.setFieldLength(6);
		String expectedVal = "000001";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, null, fileRecord,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingDTxSerialTest() {
		tempAgency.setAgencyId(16L);
		TxDetailRecord txRecord = new TxDetailRecord();
		txRecord.setTxExternRefNo("2111");
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_ETC_TRX_SERIAL_NUM);
		fMapping.setPadCharacter("0");
		fMapping.setJustification("L");
		fMapping.setFieldLength(12);
		String expectedVal = "000000002111";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, txRecord, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	@Test
	void doFieldMappingPostStatusTest() {
		tempAgency.setAgencyId(16L);
		TxDetailRecord txRecord = new TxDetailRecord();
		txRecord.setTxStatus("204");
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_ETC_POST_STATUS);
		fMapping.setDefaultValue("    ");
		fMapping.setJustification("L");
		fMapping.setFieldLength(4);
		String expectedVal = "NPST";
		//Mock
		when(inrxDao.getPostStatusForId(Mockito.anyString())).thenReturn(expectedVal);
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, txRecord, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
/*	@Test
	void doFieldMappingPostPlanTest() {
		TxDetailRecord txRecord = new TxDetailRecord();
		txRecord.setPlanTypeId("51");
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_ETC_POST_PLAN);
		fMapping.setDefaultValue("     ");
		fMapping.setJustification("L");
		fMapping.setFieldLength(5);
		txRecord.setPlanTypeId("SIR");
		String expectedVal = "00011";
		//Mock
		when(icrxDao.getPlanNameForPlanId(Mockito.anyString())).thenReturn(expectedVal);
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, txRecord, null);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	*/
	@Test
	void doFieldMappingDDebitCreditTest() {
		tempAgency.setAgencyId(16L);
		TxDetailRecord txRecord = new TxDetailRecord();
		txRecord.setPostedFareAmount(500);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_ETC_DEBIT_CREDIT);
		fMapping.setFieldLength(1);
		String expectedVal = "+";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, txRecord, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
	
	@Test
	void doFieldMappingDPostedAmtTest() {
		tempAgency.setAgencyId(16L);
		TxDetailRecord txRecord = new TxDetailRecord();
		txRecord.setPostedFareAmount(500);
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setFieldName(Constants.D_ETC_OWED_AMOUNT);
		fMapping.setPadCharacter("0");
		fMapping.setJustification("L");
		fMapping.setFieldLength(5);
		String expectedVal = "00500";
		//test method
		String value = inrxProcessServiceImpl.doFieldMapping(fMapping, txRecord, null,tempAgency);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedVal, value);
	}
	
}
