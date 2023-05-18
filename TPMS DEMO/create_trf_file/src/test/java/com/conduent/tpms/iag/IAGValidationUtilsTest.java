package com.conduent.tpms.iag;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.recon.utility.TRFValidationUtils;
import com.conduent.tpms.recon.utility.MasterDataCache;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class IAGValidationUtilsTest {

	@InjectMocks
	TRFValidationUtils validationUtils;
	
	@InjectMocks
	MasterDataCache masterdatacache;
	
	private List<String> devicePrefixList;
	
	@Test
	void validTagStatus()
	{
		boolean actual = true;;
		boolean expected = validationUtils.validateTagStatus(2);
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validTagStatusneg()
	{
		boolean actual = true;;
		boolean expected = validationUtils.validateTagStatus(5);
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validateonlydigits()
	{
		boolean actual = true;;
		boolean expected = validationUtils.onlyDigits("25");
		
		Assertions.assertEquals(actual, expected);
	}

	@Test
	void validateonlydigitsneg()
	{
		boolean actual = true;;
		boolean expected = validationUtils.onlyDigits("ABCD");
		
		Assertions.assertNotEquals(actual, expected);
	}
}
