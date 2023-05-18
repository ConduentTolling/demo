package com.conduent.tpms.qatp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.controller.QatpController;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.service.impl.TransDetailService;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class QatpApplicationTests {

	@Autowired
	QatpService qatpService;

	@Autowired
	MasterDataCache masterDataCache;
	
	@Mock
	TimeZoneConv timeZoneConv;
	
	GenericValidation genericvalidation = new GenericValidation();
	QatpController qatpc = new QatpController();
	TransDetailService tds = new TransDetailService();
	FileParserImpl fpl = new FileParserImpl();

	
	//@Test
	public void _1_Test_ProcessFile() throws IOException {
		
		// String expected = "Job excuted successfully";
		// String actual = qatpc.processFile();
		// assertEquals(expected,actual);
		MDC.put("logFileName", "NYSTA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
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

	/*
	  @Test 
	  public void _3_Test_healthCheckbyId() throws IOException {
	 
	  String actual = qatpc.healthCheck(123);
	  assertEquals(false,actual);
	  
	  
	  //MasterDataCache mdc = new MasterDataCache();
	  //boolean expected = false; 
	 // boolean actual = mdc.isValidPlaza(null);
	 // assertEquals(expected, actual);
	  
	  }
	 */

	@Test
	public void _4_Test_PushMessage_2() {

		String expected = "Message pushed sucessfully";
		String actual = qatpc.pushMessageToOSS();
		assertNotEquals(expected, actual);

	}

	@Test
	public void _5_Test_GenericValidation_DateValidation() {

		Boolean actual = genericvalidation.dateValidation("20210421152502", "YYYYMMDDHHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericvalidation.dateValidation(" ", "YYYYMMDDHHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericvalidation.dateValidation("2021-04-22023421", "YYYYMMDDHHMMSS");
		assertEquals(false, actual2);

	}

	@Test
	public void _6_Test_GenericValidation_TimeValidation() {

		Boolean actual = genericvalidation.timeValidation("022446", "HHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericvalidation.timeValidation(" ", "HHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericvalidation.timeValidation("02-24-46 ", "HHMMSS");
		assertEquals(false, actual2);
	}

	@Test
	public void _7_Test_GenericValidation_fixStringValidation() {

		boolean actual = genericvalidation.fixStringValidation("abc", "abc");
		assertEquals(true, actual);

		boolean actual1 = genericvalidation.fixStringValidation(null, null);
		assertEquals(false, actual1);

		boolean actual2 = genericvalidation.fixStringValidation("abc", " ");
		assertEquals(false, actual2);

	}

	@Test
	public void _8_Test_GenericValidation_listOfValue_Validation() {

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
	public void _9_Test_GenericValidation_rangeValidation() {
		
		
		//boolean actual = genericvalidation.rangeValidation("12345", 5L, 15L);
		//assertEquals(true, actual);
		
		boolean actual1 = genericvalidation.rangeValidation("123", 3L, 15L);
		assertEquals(false, actual1);
		
		boolean actual2 = genericvalidation.rangeValidation(" ", 3L, 15L);
		assertEquals(false, actual2);
		
	}
	
	//@Test
	public void _10_Test_TransDetailService_loadnextSeq() {
		
		List<Long> actual = tds.loadNextSeq(null);
		assertEquals(null, actual);
		
	}
	
	@Test
	public void _11_Test_FieldType_Validation() {
		
		MappingInfoDto fieldmapping = new MappingInfoDto();
		String actual = fieldmapping.getFieldType();
		assertEquals(null, actual);
	}
	
	@Test
	public void _12_Test_FieldName_Validation() {
		
		MappingInfoDto fieldmapping = new MappingInfoDto();
		String actual = fieldmapping.getFieldName();
		assertEquals(null, actual);
	}
	
	@Test
	public void _13_Test_getAgencyId() {
		
		FileParserImpl fpl = new FileParserImpl();
		String actual = fpl.getAgencyId();
		assertEquals(null, actual);
	}
	
	@Test
	public void _14_Test_getFileFormat() {
		
		FileParserImpl fpl = new FileParserImpl();
		String actual = fpl.getFileFormat();
		assertEquals(null, actual);
	}
	
}







