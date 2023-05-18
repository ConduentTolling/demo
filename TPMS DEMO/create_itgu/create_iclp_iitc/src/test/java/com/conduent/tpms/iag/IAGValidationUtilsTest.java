package com.conduent.tpms.iag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.utility.IAGValidationUtils;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class IAGValidationUtilsTest {

	@InjectMocks
	IAGValidationUtils validationUtils;
	

	@Test
	void validatePlateState() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateState("AB");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatePlateStatenegative() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateState("01");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatePlateType() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateType("25");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatePlateTypenegative() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateType("AB");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatePlateNumber() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateNumber("AB012BN0");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatePlateNumbernegative() {
		boolean actual = true;;
		boolean expected = validationUtils.validatePlateNumber("AB012B*_");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatLastName() {
		boolean actual = true;
		boolean expected = validationUtils.validateLastName("JONAS");
		
		Assertions.assertEquals(actual, expected);
	}

	@Test
	void validatLastNameneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateLastName("125D");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatFirstName() {
		boolean actual = true;
		boolean expected = validationUtils.validateFirstName("NICK");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatFirstNameneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateFirstName("NI1$3");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatMiddleInitial() {
		boolean actual = true;
		boolean expected = validationUtils.validateMiddleInitial("A");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatMiddleInitialneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateMiddleInitial("$");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	
	@Test
	void validatCompanyName() {
		boolean actual = true;
		boolean expected = validationUtils.validateCompanyName("ABCD EFGH");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatCompanyNameneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateCompanyName(null);
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatAddress1() {
		boolean actual = true;
		boolean expected = validationUtils.validateAddress1("20 EAST STREET");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatAddress1neg() {
		boolean actual = true;
		boolean expected = validationUtils.validateAddress1(" _ ~ ");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validatAddress2() {
		boolean actual = true;
		boolean expected = validationUtils.validateAddress2("NEXT TO CHURCH");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validatAddress2neg() {
		boolean actual = true;
		boolean expected = validationUtils.validateAddress2(" _ ~ ");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validateState() {
		boolean actual = true;
		boolean expected = validationUtils.validateState("MH");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validateStateneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateState("&M");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validateCity() {
		boolean actual = true;
		boolean expected = validationUtils.validateCity("PUNE");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validateCityneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateCity("P&*_+");
		
		Assertions.assertNotEquals(actual, expected);
	}
	
	@Test
	void validateZipcode() {
		boolean actual = true;
		boolean expected = validationUtils.validateZipcode("411051");
		
		Assertions.assertEquals(actual, expected);
	}
	
	@Test
	void validateZipcodeneg() {
		boolean actual = true;
		boolean expected = validationUtils.validateZipcode("P&*_+E");
		
		Assertions.assertNotEquals(actual, expected);
	}
}
