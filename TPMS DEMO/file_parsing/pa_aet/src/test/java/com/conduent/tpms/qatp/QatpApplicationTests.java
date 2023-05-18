package com.conduent.tpms.qatp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.controller.QatpController;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.TransDetailDao;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.PaDetailInfoVO;
import com.conduent.tpms.qatp.dto.PaFileNameDetailVO;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.PaFixlengthParser;
import com.conduent.tpms.qatp.service.impl.TransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.validation.FileParserImpl;

@ExtendWith(MockitoExtension.class)
class QatpApplicationTests {
	@InjectMocks
	GenericValidation genericValidation;

	@InjectMocks
	FileParserImpl fileParserImpl;

	@Mock
	TQatpMappingDao tQatpMappingDao;

	@InjectMocks
	PaFixlengthParser paFixlengthParser;

	@Mock
	PaFileNameDetailVO fileNameVO;
	
	@Mock
	QatpController qatpController;

	QatpController qatpc = new QatpController();
	TransDetailService tds = new TransDetailService();
	TransDetailDao TranDao = new TransDetailDao();
	PaDetailInfoVO paDetailInfoVo = new PaDetailInfoVO();
	
//	@Test
//	public void _1_Test_ProcessPaFile() throws IOException {
//		
//		// String expected = "Job excuted successfully";
//		// String actual = qatpc.processFile();
//		// assertEquals(expected,actual);
//		//MDC.put("logFileName", "NYSTA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
//		String expected1 = "Job excuted successfully";
//		String actual1 = qatpc.processPaFile();
//		assertNotEquals(expected1, actual1);
//	}
	
	@Test
	public void _2_Test_healthCheck() throws IOException {

		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		String expected = "PA parser service is running on " +podName;
		String actual = qatpc.healthCheck();
		assertEquals(expected, actual);
		
	}
	
	@Test
	void _3_Test_fixedStringValidation() {
		Assert.assertEquals(false, genericValidation.fixStringValidation("", "abc", null));
		Assert.assertEquals(true, genericValidation.fixStringValidation("abc", "abc", null));
		Assert.assertEquals(false, genericValidation.fixStringValidation("pqr", "abc", null));
	}

	@Test
	void _4_Test_rangeValidation() {

		Assert.assertEquals(false, genericValidation.rangeValidation("", 5L, 15L));
		Assert.assertTrue(genericValidation.rangeValidation("6", 5l, 15l));
		Assert.assertFalse(genericValidation.rangeValidation("0", 5L, 15L));
	}

	@Test
	public void _5_Test_GenericValidation_DateValidation() 
	{

		Boolean actual = genericValidation.dateValidation("20210421152502", "YYYYMMDDHHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericValidation.dateValidation(" ", "YYYYMMDDHHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericValidation.dateValidation("2021-04-22023421", "YYYYMMDDHHMMSS");
		assertEquals(false, actual2);

	}
	
	@Test
	public void _6_Test_GenericValidation_TimeValidation() {

		Boolean actual = genericValidation.timeValidation("022446", "HHMMSS");
		assertEquals(true, actual);

		Boolean actual1 = genericValidation.timeValidation(" ", "HHMMSS");
		assertEquals(false, actual1);

		Boolean actual2 = genericValidation.timeValidation("02-24-46 ", "HHMMSS");
		assertEquals(false, actual2);
	}
	
	@Test
	void _7_Test_lovValidation() {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("pqr");
		list.add("xyz");
		Assert.assertFalse(genericValidation.listOfValue("", list));
		Assert.assertTrue(genericValidation.listOfValue("abc", list));
		Assert.assertFalse(genericValidation.listOfValue("shf", list));

	}

	@Test
	void _8_Test_regexValidation() {
		Assert.assertEquals(true, genericValidation.regexValidation("6", "[0-9]"));
		Assert.assertEquals(false, genericValidation.regexValidation("1234", "[A-Z]"));

	}

	@Test
	void _9_Test_fileNotDownloaded() throws InvalidFileStatusException, FileAlreadyProcessedException {
		File file = new File("D:\\Conduent Project\\srcDirectory\\005_CSC_20050101050015.ATRN");

		Mockito.when(tQatpMappingDao.checkFileDownloaded(file.getName())).thenReturn(null);

		assertThrows(InvalidFileStatusException.class, () -> {
			fileParserImpl.startFileProcess(file);

		});

	}

	//@Test
	void _10_Test_fileAlreadyProcessed() throws InvalidFileStatusException, FileAlreadyProcessedException {
		File file = new File("D:\\Conduent Project\\srcDirectory\\005_CSC_20050101050015.ATRN");
		XferControl xferControl = new XferControl();
		xferControl.setXferXmitStatus("C");
		ReconciliationCheckPoint reconciliationCheckPoint = new ReconciliationCheckPoint();
		reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
		Mockito.when(tQatpMappingDao.checkFileDownloaded(file.getName())).thenReturn(xferControl);
		Mockito.when(tQatpMappingDao.getReconsilationCheckPoint(file.getName())).thenReturn(reconciliationCheckPoint);

		assertThrows(FileAlreadyProcessedException.class, () -> {
			fileParserImpl.startFileProcess(file);

		});

	}
	


	//@Test
	void _11_Test_prePareAckFileMetaData() {
		File file = new File("D:\\Conduent Project\\srcDirectory\\005_CSC_20050101050015.ATRN");
		LocalDateTime date = LocalDateTime.now();
		StringBuilder sbFileContent = new StringBuilder();
		Mockito.when(fileNameVO.getFromEntity()).thenReturn("005");
		Mockito.when(fileNameVO.getToEntity()).thenReturn("CSC");
		Mockito.when(genericValidation.getTodayTimestamp(date)).thenReturn("20200102").toString();
		AckFileWrapper ackObj = paFixlengthParser.prePareAckFileMetaData(AckStatusCode.INVALID_RECORD_COUNT, file);
		Assertions.assertNull(ackObj);
	}
	
	@Test
	void _12_Test_processFile() throws IOException {
		String expected1 = "Job excuted successfully";
		String actual1 = qatpController.processPaFile();
		Assert.assertNotEquals(expected1, actual1);
	}

	@Test
	public void _13_Test_FieldType_Validation() {
		
		MappingInfoDto fieldmapping = new MappingInfoDto();
		String actual = fieldmapping.getFieldType();
		assertEquals(null, actual);
	}
	
	@Test
	public void _14_Test_toLong() {
		
		Long expected = (long) 12;
		Long actual = Convertor.toLong("12");
		assertNotEquals(null, actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void _15_Test_isAlphaNumeric() {
		
		Boolean actual = Convertor.isAlphaNumeric("Ab1");
		assertNotEquals(false, actual);
		assertEquals(true, actual);
	}
	
	@Test
	public void _16_Test_toInteger() {
		
		Integer expected = 12;
		Integer actual = Convertor.toInteger("12");
		assertNotEquals(null, actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void _17_Test_toDouble() {
		
		Double actual = Convertor.toDouble2(12L);
		assertNotEquals(null, actual);
		//assertEquals(12, actual);
		assertEquals(Double.valueOf(12), actual);
	}
	
	@Test
	public void _18_Test_getFormattedDate() {
		
		LocalDate actual = Convertor.getFormattedDate("15-DEC-22","yyyyMMdd");
		assertEquals(null, actual);
	}
	
	@Test
	public void _19_Test_isEmpty() {
		
		Boolean actual = Convertor.isEmpty("");
		assertEquals(true, actual);
	}
	
	@Test
	public void _20_Test_validateDeviceNo() {
		
		Boolean actual = paDetailInfoVo.validate("**************");
		assertEquals(true, actual);
		
		Boolean actual1 = paDetailInfoVo.validate("00000000000000");
		assertEquals(true, actual1);
		
		Boolean actual2 = paDetailInfoVo.validate("              ");
		assertEquals(true, actual2);
	}
	
	@Test
	public void _21_Test_dateValidation() {
		
		Boolean actual = DateUtils.dateValidation("20221205","YYYYMMDD");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}

	@Test
	public void _22_Test_dateValidation() {
		
		Boolean actual = DateUtils.dateValidation("20221205121314","YYYYMMDDHHMMSS");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
	@Test
	public void _23_Test_timeValidation() {
		
		Boolean actual = DateUtils.timeValidation("121314","HHMMSS");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
	@Test
	public void _24_Test_timeValidation() {
		
		Boolean actual = DateUtils.timeValidation("12131415","HHMMSSTT");
		assertEquals(true, actual);
		assertNotEquals(false, actual);
	}
	
	
	@Before
	public void setup() {

	}
}
