package com.conduent.tpms.iag.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.utility.GenericValidation;

@ExtendWith(MockitoExtension.class)
public class GenericValidationTest {

	@InjectMocks
	GenericValidation genericValidation;
	
	@Mock
	TQatpMappingDao tQatpMappingDao;

	static List<String> tagStatusList;
	
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("009_20210310230438.ITAG").getFile());


	@BeforeAll
	public static void init() {
		tagStatusList = new ArrayList<String>();
		tagStatusList.add("1");
		tagStatusList.add("2");
		tagStatusList.add("3");
		tagStatusList.add("4");
	}

	@Test
	public void fixedStringValidation() {
		assertTrue(genericValidation.fixStringValidation("abc", "abc"));
		assertFalse(genericValidation.fixStringValidation("", "abc"));
		assertFalse(genericValidation.fixStringValidation("pqr", "abc"));
		assertFalse(genericValidation.fixStringValidation("", "abc"));
	}

	@Test
	public void rangeValidation() {

		Assertions.assertEquals(false, genericValidation.rangeValidation("", 5L, 15L));
		Assertions.assertTrue(genericValidation.rangeValidation("6", 5l, 15l));
		Assertions.assertFalse(genericValidation.rangeValidation("0", 5L, 15L));
	}

	/*
	 * @Test public void invalidDateFormatValidation() {
	 * Assertions.assertEquals(false, genericValidation.dateValidation("2020-02-02",
	 * "YYYYMMDD")); }
	 */

	@Test
	public void NullDateValidation() {

		Assertions.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDD"));
		Assertions.assertFalse(genericValidation.dateValidation(null, "YYYYMMDD"));
	}

	@Test
	public void DateTimeValidation() {

		Assertions.assertEquals(true, genericValidation.dateValidation("20200202015959", "YYYYMMDDHHMMSS"));
	}

	/*
	 * @Test public void invalidDateTimeFormatValidation() {
	 * 
	 * Assertions.assertFalse(genericValidation.dateValidation("2020-02-02010203",
	 * "YYYYMMDDHHMMSS")); }
	 */
	@Test
	public void nullDateTimeFormatValidation() {

		Assertions.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDDHHMMSS"));
		Assertions.assertEquals(false, genericValidation.dateValidation(null, "YYYYMMDDHHMMSS"));
	}

	//@Test
	public void futureDateValidation() {

		Assertions.assertFalse(genericValidation.dateValidation("20220202000000", "YYYYMMDDHHMMSS"));
	}

	@Test
	public void lovValidation() {

		Assertions.assertTrue(genericValidation.listOfValue("3", tagStatusList));
	}

	@Test
	public void lovValidationWithEmptyValue() {

		Assertions.assertFalse(genericValidation.listOfValue("", tagStatusList));
	}

	@Test
	public void lovValidationWithInvalidValue() {

		Assertions.assertFalse(genericValidation.listOfValue("6", tagStatusList));
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

}
