package com.conduent.tpms.qatp;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.PaFileNameDetailVO;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.PaFixlengthParser;
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

	@Test
	void _1_Test_fixedStringValidation() {
		Assert.assertEquals(false, genericValidation.fixStringValidation("", "abc", null));
		Assert.assertEquals(true, genericValidation.fixStringValidation("abc", "abc", null));
		Assert.assertEquals(false, genericValidation.fixStringValidation("pqr", "abc", null));
	}

	@Test
	void _2_Test_rangeValidation() {

		Assert.assertEquals(false, genericValidation.rangeValidation("", 5L, 15L));
		Assert.assertTrue(genericValidation.rangeValidation("6", 5l, 15l));
		Assert.assertFalse(genericValidation.rangeValidation("0", 5L, 15L));
	}

	@Test
	void _3_Test_dateValidation() {
		Assert.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDD"));
		Assert.assertEquals(false, genericValidation.dateValidation("20221202", "YYYYMMDD"));
		Assert.assertEquals(false, genericValidation.dateValidation("2020-02-02", "YYYYMMDD"));
		Assert.assertEquals(true, genericValidation.dateValidation("20200202", "YYYYMMDD"));

	}

	@Test
	void _4_Test_timeValidation() {
		Assert.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(false, genericValidation.dateValidation("20221202000000", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(false, genericValidation.dateValidation("2020-02-02010203", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(true, genericValidation.dateValidation("20200202015959", "YYYYMMDDHHMMSS"));

	}

	@Test
	void _5_Test_lovValidation() {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("pqr");
		list.add("xyz");
		Assert.assertFalse(genericValidation.listOfValue("", list));
		Assert.assertTrue(genericValidation.listOfValue("abc", list));
		Assert.assertFalse(genericValidation.listOfValue("shf", list));

	}

	@Test
	void _6_Test_regexValidation() {
		Assert.assertEquals(true, genericValidation.regexValidation("6", "[0-9]"));
		Assert.assertEquals(false, genericValidation.regexValidation("1234", "[A-Z]"));

	}

	@Test
	void _7_Test_fileNotDownloaded() throws InvalidFileStatusException, FileAlreadyProcessedException {
		File file = new File("D:\\Conduent Project\\srcDirectory\\005_CSC_20050101050015.ATRN");

		Mockito.when(tQatpMappingDao.checkFileDownloaded(file.getName())).thenReturn(null);

		assertThrows(InvalidFileStatusException.class, () -> {
			fileParserImpl.startFileProcess(file);

		});

	}

	//@Test
	void _8_Test_fileAlreadyProcessed() throws InvalidFileStatusException, FileAlreadyProcessedException {
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
	void _9_Test_prePareAckFileMetaData() {
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
	void _9_Test_processFile() throws IOException {
		String expected1 = "Job excuted successfully";
		String actual1 = qatpController.processPaFile();
		Assert.assertNotEquals(expected1, actual1);
	}

	@Before
	public void setup() {

	}
}
