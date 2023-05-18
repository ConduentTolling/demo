package com.conduent.tpms.iag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.CustomerAddressRecord;
import com.conduent.tpms.iag.utility.GenericValidation;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GenericValidationTest {
/*
	@InjectMocks
	GenericValidation genericValidation;
	
	@Mock
	TQatpMappingDao tQatpMappingDao;

	CustomerAddressRecord custRecord;
	
	String headerDate;
	String headerTime;
	String dateTime;
	String fileType;
	String headerFromAgencyId;
	
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("016_20200716210431.IITC").getFile());
	MappingInfoDto fMapping = new MappingInfoDto();

	@BeforeAll
	public void init() {
		getFileContent(file);
	}

	@Test
	public void FixedValueValidation() {
		fMapping.setValidationType("FIXED_VALUE");
		fMapping.setFixeddValidValue("IITC");
		Assertions.assertTrue(genericValidation.doValidation(fMapping, fileType));
	}
	
	@Test
	public void fixedValueValidation() {
		assertTrue(genericValidation.fixStringValidation("IITC", "IITC"));
		assertFalse(genericValidation.fixStringValidation("", "IITC"));
		assertFalse(genericValidation.fixStringValidation("ITAG", "IITC"));
		assertFalse(genericValidation.fixStringValidation("", "IITC"));
	}

	@Test
	public void regexValidationTest() {
		MappingInfoDto fMapping = new MappingInfoDto();
		fMapping.setValidationType("REGEX");
		fMapping.setValidationValue("[a-zA-Z \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$");
		Assertions.assertTrue(genericValidation.doValidation(fMapping, custRecord.getFirstName()));
	}
	
	@Test
	public void rangeValidationForAgencyId() {
		fMapping.setValidationType("RANGE");
		fMapping.setMinRangeValue(0L);
		fMapping.setMaxRangeValue(127L);
		Assertions.assertTrue(genericValidation.doValidation(fMapping, headerFromAgencyId));

	}
	
	@Test
	public void rangeValidationFailForAgencyId() {
		fMapping.setValidationType("RANGE");
		fMapping.setMinRangeValue(0L);
		fMapping.setMaxRangeValue(127L);
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "200"));
	}
	
	@Test
	public void rangeValidation() {
		Assertions.assertEquals(false, genericValidation.rangeValidation("", 5L, 15L));
		Assertions.assertTrue(genericValidation.rangeValidation("6", 5l, 15l));
		Assertions.assertFalse(genericValidation.rangeValidation("0", 5L, 15L));
	}

	@Test
	public void dateValidationTest() {
		fMapping.setValidationType("DATE");
		fMapping.setValidationValue("YYYYMMDD");
		Assertions.assertTrue(genericValidation.doValidation(fMapping, headerDate));
	}
	
	@Test
	public void futureDateValidation() {
		fMapping.setValidationType("DATE");
		fMapping.setValidationValue("YYYYMMDD");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "20220202000000"));
	}
	
	@Test
	public void dateParsingValidationFail() {
		fMapping.setValidationType("DATE");
		fMapping.setValidationValue("YYYYMMDD");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "20**0202000000"));
	}
	
	@Test
	public void invalidDateFormatValidation() {
		Assertions.assertEquals(false, genericValidation.dateValidation("2020-02-02", "YYYYMMDD"));
	}

	@Test
	public void nullDateValidation() {
		Assertions.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDD"));
		Assertions.assertFalse(genericValidation.dateValidation(null, "YYYYMMDD"));
	}

	@Test
	public void DateTimeValidation() {
		Assertions.assertEquals(true, genericValidation.dateValidation("20200202015959", "YYYYMMDDHHMMSS"));
	}

	@Test
	public void invalidDateTimeFormatValidation() {
		Assertions.assertFalse(genericValidation.dateValidation("2020-02-02010203", "YYYYMMDDHHMMSS"));
	}

	@Test
	public void nullDateTimeFormatValidation() {
		Assertions.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDDHHMMSS"));
		Assertions.assertEquals(false, genericValidation.dateValidation(null, "YYYYMMDDHHMMSS"));
	}

	@Test
	public void timeValidationSuccess() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSS");
		Assertions.assertTrue(genericValidation.doValidation(fMapping, headerTime));
	}
	
	@Test
	public void timeValidationFail() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSS");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "11223"));
	}
	
	@Test
	public void timeParseValidationFail() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSS");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "1122**"));
	}
	
	@Test
	public void dateTimeParseFail() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSS");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "210461"));
	}
	
	@Test
	public void timeLengthValidationTest() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSSTT");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, "2104312"));
	}
	
	@Test
	public void timeValidationWithEmptyValue() {
		fMapping.setValidationType("TIME");
		fMapping.setValidationValue("HHMMSS");
		Assertions.assertFalse(genericValidation.doValidation(fMapping, ""));
	}
	
	@Test
	public void todaysDateTimeValidation() {
		String dateTime=genericValidation.getTodayTimestamp(LocalDateTime.now());
		Assertions.assertNotNull(dateTime);
	}
	
	@Test
	public void todaysTimeValidation() {
		String todayTime=genericValidation.getTodayTimestamp(LocalDateTime.now(),"hhmmss");
		Assertions.assertNotNull(todayTime);
	}
	
	@Test
	public void formattedDateTimeValidation() {
		LocalDateTime fileDateTime=genericValidation.getFormattedDateTime(dateTime);
		assertThat(fileDateTime).isInstanceOf(LocalDateTime.class);
		Assertions.assertNotNull(fileDateTime);
		
	}
	
	/*
	@Test
	void CheckfileAlreadyProcessed() throws InvalidFileStatusException, FileAlreadyProcessedException {
	XferControl xferControl = new XferControl();
	xferControl.setXferXmitStatus("C");
	FileDetails checkPoint = new FileDetails();
	checkPoint.setProcessStatus(FileProcessStatus.COMPLETE);
	Mockito.when(tQatpMappingDao.checkFileDownloaded(file.getName())).thenReturn(xferControl);
	Mockito.when(tQatpMappingDao.checkIfFileProcessedAlready(file.getName())).thenReturn(checkPoint);
	
	}
	*/
	/*
	private void getFileContent(File file) {
		custRecord = new CustomerAddressRecord();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			 String line;
			 try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					
					if(line.startsWith("IITC")) {
						headerFromAgencyId=line.substring(4,7);
						headerDate=line.substring(7,15);
						headerTime=line.substring(15,21);
						dateTime=line.substring(7,21);
						fileType = line.substring(0,4);
					}
					if(line.contains("MULBERRY"))
					{
						custRecord.setFirstName(line.substring(51,91));
						custRecord.setLastName(line.substring(11,51));
						custRecord.setMiddleInitial(line.substring(91,92));
					}
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
