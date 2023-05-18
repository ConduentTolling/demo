package com.conduent.tpms.iag;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.dao.TIagMappingDao;
import com.conduent.tpms.iag.parser.agency.IagFixlengthParser;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.validation.FileParserImpl;

@ExtendWith(MockitoExtension.class)
class QatpApplicationTests {
	@InjectMocks
	GenericValidation genericValidation;

	@InjectMocks
	FileParserImpl fileParserImpl;

	@Mock
	TIagMappingDao tIagMappingDao;

	@InjectMocks
	IagFixlengthParser iagFixlengthParser;

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
		Assert.assertEquals(true, genericValidation.dateValidation("20220202", "YYYYMMDD"));
		Assert.assertEquals(false, genericValidation.dateValidation("2020-02-02", "YYYYMMDD"));

	}

	@Test
	void _4_timeValidation() {
		Assert.assertEquals(false, genericValidation.dateValidation("", "YYYYMMDDHHMMSS"));
		Assert.assertEquals(true, genericValidation.dateValidation("20220202000000", "YYYYMMDDHHMMSS"));
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

	@Before
	public void setup() {

	}
}
