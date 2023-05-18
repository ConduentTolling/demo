package com.conduent.tpms.qatp;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
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
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.MtxFileNameDetailVO;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.MtaFixlengthParser;
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
	MtaFixlengthParser mtaFixlengthParser;

	@Mock
	MtxFileNameDetailVO fileNameVO;

	@Test
	void _1_fixedStringValidation() {
		Assert.assertEquals(false, genericValidation.fixStringValidation("", "abc", null));
		Assert.assertEquals(true, genericValidation.fixStringValidation("abc", "abc", null));
		Assert.assertEquals(false, genericValidation.fixStringValidation("pqr", "abc", null));
	}

	@Test
	void _2_rangeValidation() {

		Assert.assertEquals(false, genericValidation.rangeValidation("", 5L, 15L));
		Assert.assertTrue(genericValidation.rangeValidation("6", 5l, 15l));
		Assert.assertFalse(genericValidation.rangeValidation("0", 5L, 15L));
	}

	@Test
	void _3_dateValidation() {
		Assert.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDD"));
		Assert.assertEquals(false, genericValidation.dateValidation("20221202", "YYYYMMDD"));
		Assert.assertEquals(false, genericValidation.dateValidation("2020-02-02", "YYYYMMDD"));

	}

	@Test
	void _4_timeValidation() {
		Assert.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(false, genericValidation.dateValidation("20221202000000", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(false, genericValidation.dateValidation("2020-02-02010203", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(true, genericValidation.dateValidation("20200202015959", "YYYYMMDDHHMMSS"));

	}

	@Test
	void _5_lovValidation() {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("pqr");
		list.add("xyz");
		Assert.assertFalse(genericValidation.listOfValue("", list));
		Assert.assertTrue(genericValidation.listOfValue("abc", list));
		Assert.assertFalse(genericValidation.listOfValue("shf", list));

	}

	@Test
	void _6_regexValidation() {
		Assert.assertEquals(true, genericValidation.regexValidation("6", "[0-9]"));
		Assert.assertEquals(false, genericValidation.regexValidation("1234", "[A-Z]"));

	}

	@Test
	void _7_fileNotDownloaded() throws InvalidFileStatusException, FileAlreadyProcessedException {
		File file = new File("D:\\coduent project\\srcDirectory\\30_2020081614_0008.TRX");

		Mockito.when(tQatpMappingDao.checkFileDownloaded(file.getName())).thenReturn(null);

		assertThrows(InvalidFileStatusException.class, () -> {
			fileParserImpl.startFileProcess(file);

		});

	}

	//@Test
	void _8_fileAlreadyProcessed() throws InvalidFileStatusException, FileAlreadyProcessedException {
		File file = new File("D:\\coduent project\\srcDirectory\\30_2020081614_0008.TRX");
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
//	void _9_prePareAckFileMetaData() {
//		File file = new File("D:\\Conduent Project\\srcDirectory\\005_CSC_20050101050015.ATRN");
//		LocalDateTime date = LocalDateTime.now();
//		StringBuilder sbFileContent = new StringBuilder();
//		Mockito.when(fileNameVO.getFromEntity()).thenReturn("005");
//		Mockito.when(fileNameVO.getToEntity()).thenReturn("CSC");
//		Mockito.when(genericValidation.getTodayTimestamp(date)).thenReturn("20200102").toString();
//		AckFileWrapper ackObj = paFixlengthParser.prePareAckFileMetaData(AckStatusCode.INVALID_RECORD_COUNT, file);
//		Assertions.assertNull(ackObj);
//	}

	@Before
	public void setup() {

	}
}
