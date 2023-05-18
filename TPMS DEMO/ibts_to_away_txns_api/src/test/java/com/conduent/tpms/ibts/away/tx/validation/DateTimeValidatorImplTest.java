package com.conduent.tpms.ibts.away.tx.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateTimeValidatorImplTest {

	@Test
	void testValidDateTimeFormat() {
		DateTimeValidatorImpl dateTimeValidatorImpl = new DateTimeValidatorImpl();
		boolean responseFlag = dateTimeValidatorImpl.isValid("2021-08-22 12:23:23.002");
		assertTrue(responseFlag);
	}

	@Test
	void testInValidDateTimeFormat() {
		DateTimeValidatorImpl dateTimeValidatorImpl = new DateTimeValidatorImpl();
		boolean responseFlag = dateTimeValidatorImpl.isValid("2021-08-22 12:23:23.002-");
		assertFalse(responseFlag);
	}
}
