package com.conduent.tpms.iag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.utility.IAGValidationUtils;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class IAGValidationUtilsTest {

	@InjectMocks
	IAGValidationUtils validationUtils;
	
	@Test
	void customFormatStringTest() {
		String str = validationUtils.customFormatString(Constants.TAG_ACCOUNT_INFO, 32);
		Assertions.assertNotNull(str);
		Assertions.assertTrue(6 == str.length());
	}

	@Test
	void customFormatStringExceptionTest() throws NullPointerException {

		String str = validationUtils.customFormatString("1234", 32);
		Assertions.assertNotNull(str);
		//Assertions.assertEquals(str, null);
		/*Assertions.assertThrows(NullPointerException.class, () -> {
			validationUtils.customFormatString(null, 32);
		  });	*/
	}

	@Test
	public void validateTagStatusVALIDTest() {
		boolean isExist = validationUtils.validateTagStatus(Constants.TS_VALID);
		Assertions.assertTrue(isExist);
	}
	
	@Test
	public void validateTagStatusLOWTest() {
		boolean isExist = validationUtils.validateTagStatus(Constants.TS_LOW);
		Assertions.assertTrue(isExist);
	}
	
	@Test
	public void validateTagStatusINVALIDTest() {
		boolean isExist = validationUtils.validateTagStatus(Constants.TS_INVALID);
		Assertions.assertTrue(isExist);
	}
	
	@Test
	public void validateTagStatusLOSTTest() {
		boolean isExist = validationUtils.validateTagStatus(Constants.TS_LOST);
		Assertions.assertTrue(isExist);
	}
	
	@Test
	public void validateTagStatusFalseTest() {
		boolean isExist = validationUtils.validateTagStatus(Constants.ZERO);
		Assertions.assertFalse(isExist);
	}


	@Test
	public void onlyDigitsTest() {
		boolean isDigits = validationUtils.onlyDigits("00488018");
		Assertions.assertTrue(isDigits);
	}
	
	@Test
	public void onlyDigitsFalseTest() {
		boolean isDigits = validationUtils.onlyDigits(null);
		Assertions.assertFalse(isDigits);
	}

}
