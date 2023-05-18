package com.conduent.tpms.qatp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qatp.controller.QatpController;
import com.conduent.tpms.qatp.dao.impl.TransDetailDao;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaAetTxDto;
import com.conduent.tpms.qatp.dto.TollCalculationResponseDto;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.service.impl.TransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class QatpApplicationTests {

	@Autowired
	QatpService qatpService;

	@Autowired
	MasterDataCache masterDataCache;
	
	GenericValidation genericvalidation = new GenericValidation();
	QatpController qatpc = new QatpController();
	TransDetailService tds = new TransDetailService();
	TransDetailDao TranDao = new TransDetailDao();
	FileParserImpl fpl = new FileParserImpl();
	NystaAetTxDto nystaAetTxDto = new NystaAetTxDto();
	
	@Test
	public void _1_Test_ProcessFile() throws IOException {
		
		// String expected = "Job excuted successfully";
		// String actual = qatpc.processFile();
		// assertEquals(expected,actual);
		//MDC.put("logFileName", "NYSTA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		String expected1 = "Job excuted successfully";
		String actual1 = qatpc.processFile();
		assertNotEquals(expected1, actual1);
	}

	@Test
	public void _2_Test_healthCheck() throws IOException {

		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		String expected = "Nysta parser service is running on " +podName;
		String actual = qatpc.healthCheck();
		assertEquals(expected, actual);
		
	}

	@Test
	public void _3_Test_PushMessage_2() {

		String expected = "Message pushed sucessfully";
		String actual = qatpc.pushMessageToOSS();
		assertNotEquals(expected, actual);

	}

	@Test
	public void _4_Test_GenericValidation_DateValidation() {

		Boolean actual = genericvalidation.dateValidation("20210421152502", "YYYYMMDDHHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericvalidation.dateValidation(" ", "YYYYMMDDHHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericvalidation.dateValidation("2021-04-22023421", "YYYYMMDDHHMMSS");
		assertEquals(false, actual2);

	}

	@Test
	public void _5_Test_GenericValidation_TimeValidation() {

		Boolean actual = genericvalidation.timeValidation("022446", "HHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericvalidation.timeValidation(" ", "HHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericvalidation.timeValidation("02-24-46 ", "HHMMSS");
		assertEquals(false, actual2);
	}

	@Test
	public void _6_Test_GenericValidation_fixStringValidation() {

		boolean actual = genericvalidation.fixStringValidation("abc", "abc");
		assertEquals(true, actual);

		boolean actual1 = genericvalidation.fixStringValidation(null, null);
		assertEquals(false, actual1);

		boolean actual2 = genericvalidation.fixStringValidation("abc", " ");
		assertEquals(false, actual2);

	}

	@Test
	public void _7_Test_GenericValidation_listOfValue_Validation() {

		List<String> list = new ArrayList<String>();

		list.add("abc");

		boolean actual = genericvalidation.listOfValue("abc", list);
		assertEquals(true, actual);
		
		boolean actual1 = genericvalidation.listOfValue(" ", list);
		assertEquals(false, actual1);

		boolean actual2 = genericvalidation.listOfValue("123", list);
		assertEquals(false, actual2);

	}
	
	@Test
	public void _8_Test_GenericValidation_rangeValidation() {
		
		boolean actual1 = genericvalidation.rangeValidation("123", 3L, 15L);
		assertEquals(false, actual1);
		
		boolean actual2 = genericvalidation.rangeValidation(" ", 3L, 15L);
		assertEquals(false, actual2);
		
	}
	
	@Test
	public void _9_Test_FieldType_Validation() {
		
		MappingInfoDto fieldmapping = new MappingInfoDto();
		String actual = fieldmapping.getFieldType();
		assertEquals(null, actual);
	}
	
	@Test
	public void _10_Test_toLong() {
		
		Long actual = Convertor.toLong("12");
		assertNotEquals(null, actual);
		assertEquals(12, actual);
	}
	
	@Test
	public void _11_Test_isAlphaNumeric() {
		
		Boolean actual = Convertor.isAlphaNumeric("Ab1");
		assertNotEquals(false, actual);
		assertEquals(true, actual);
	}
	
	@Test
	public void _12_Test_toInteger() {
		
		Integer actual = Convertor.toInteger("12");
		assertNotEquals(null, actual);
		assertEquals(12, actual);
	}
	
	@Test
	public void _13_Test_toDouble() {
		
		Double actual = Convertor.toDouble(12L);
		assertNotEquals(null, actual);
		assertEquals(12, actual);
	}
	
	@Test
	public void _13_Test_getFormattedDate() {
		
		LocalDate actual = Convertor.getFormattedDate("15-DEC-22","yyyyMMdd");
		assertEquals(null, actual);
	}
	
	@Test
	public void _14_Test_isEmpty() {
		
		Boolean actual = Convertor.isEmpty("");
		assertEquals(true, actual);
	}
	
	@Test
	public void _15_Test_validateDeviceNo() {
		
		Boolean actual = nystaAetTxDto.validate("***********");
		assertEquals(true, actual);
		
		Boolean actual1 = nystaAetTxDto.validate("0000000000");
		assertEquals(true, actual1);
		
		Boolean actual2 = nystaAetTxDto.validate("           ");
		assertEquals(true, actual2);
	}
	
	@Test
	public void _16_Test_dateValidation() {
		
		Boolean actual = DateUtils.dateValidation("20221205","YYYYMMDD");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}

	@Test
	public void _17_Test_dateValidation() {
		
		Boolean actual = DateUtils.dateValidation("20221205121314","YYYYMMDDHHMMSS");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
	@Test
	public void _18_Test_timeValidation() {
		
		Boolean actual = DateUtils.timeValidation("121314","HHMMSS");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
	@Test
	public void _19_Test_timeValidation() {
		
		Boolean actual = DateUtils.timeValidation("12131415","HHMMSSTT");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
}







