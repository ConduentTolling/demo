package com.conduent.tpms.ibts.away.tx.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateValidatorImplTest {
	
	@Test
	void testValidDateFormat() {
		DateValidatorImpl dateValidatorImpl = new DateValidatorImpl();
		boolean responseFlag = dateValidatorImpl.isValid("2021-08-22");
		assertTrue(responseFlag);
	}

	@Test
	void testInValidDateFormat() {
		DateValidatorImpl dateValidatorImpl = new DateValidatorImpl();
		boolean responseFlag = dateValidatorImpl.isValid("2021-08-22-");
		assertFalse(responseFlag);
	}

}
