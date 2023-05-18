package com.conduent.tpms.ibts.away.tx.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ibts.away.tx.constants.AwayTransactionConstant;
import com.conduent.tpms.ibts.away.tx.exception.DateFormatException;
import com.conduent.tpms.ibts.away.tx.exception.DateTimeFormatException;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;
import com.conduent.tpms.ibts.away.tx.validation.DateTimeValidatorImpl;
import com.conduent.tpms.ibts.away.tx.validation.DateValidatorImpl;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceImplTest {

	@InjectMocks
	private ValidationServiceImpl validationServiceImpl;

	@Mock
	private DateValidatorImpl dateValidatorImpl;

	@Mock
	private DateTimeValidatorImpl dateTimeValidatorImpl;

	@Test
	void testInvalidTxDate() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31-");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31-")).thenReturn(false);
		DateFormatException exception = assertThrows(DateFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATE_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_TX_DATE_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

	@Test
	void testInvalidRevenueDate() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setRevenueDate("2021-08-31-");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateValidatorImpl.isValid("2021-08-31-")).thenReturn(false);
		DateFormatException exception = assertThrows(DateFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATE_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_REVENUE_DATE_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

	@Test
	void testInvalidPostedDate() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setPostedDate("2021-08-31-");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateValidatorImpl.isValid("2021-08-31-")).thenReturn(false);
		DateFormatException exception = assertThrows(DateFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATE_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_POSTED_DATE_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

	@Test
	void testInvalidExternFileDate() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setExternFileDate("2021-08-31-");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateValidatorImpl.isValid("2021-08-31-")).thenReturn(false);
		DateFormatException exception = assertThrows(DateFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATE_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_EXTERN_FILE_DATE_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

//	@Test
	void testInvalidTxTimestamp() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setExternFileDate("2021-08-31");
		awayTransaction.setTxTimestamp("2021-08-31 12:45:55.012-");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateTimeValidatorImpl.isValid("2021-08-31 12:45:55.012-")).thenReturn(false);
		DateTimeFormatException exception = assertThrows(DateTimeFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATETIME_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_TX_TIMESTAMP_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

//	@Test
	void testInvalidEntryTxTimestamp() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setPostedDate("2021-08-31");
		awayTransaction.setExternFileDate("2021-08-31");
		awayTransaction.setTxTimestamp("2021-08-31 12:45:55.012");
		awayTransaction.setEntryTimestamp("2021-08-31 12:45:55.012-");
		awayTransaction.setTollSystemType("C");

		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateTimeValidatorImpl.isValid("2021-08-31 12:45:55.012")).thenReturn(true);
		Mockito.when(dateTimeValidatorImpl.isValid("2021-08-31 12:45:55.012-")).thenReturn(false);
		DateTimeFormatException exception = assertThrows(DateTimeFormatException.class, () -> {
			validationServiceImpl.validate(awayTransaction);
		});

		String actualExceptionMsg = AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATETIME_FORMAT;
		String expectedExceptionMsg = exception.getMessage();

		String expectedErrMsg = AwayTransactionConstant.EXCEPTION_MSG_ENTRY_TX_TIMESTAMP_FORMAT;
		String actualErrMsg = exception.getErrorMessages().get(0);

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
		assertTrue(actualErrMsg.contains(expectedErrMsg));
	}

	//@Test
	void testValidData() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTxDate("2021-08-31");
		awayTransaction.setExternFileDate("2021-08-31");
		awayTransaction.setRevenueDate("2021-08-31");
		awayTransaction.setTxTimestamp("2021-08-31 12:45:55.012");
		awayTransaction.setEntryTimestamp("2021-08-31 12:45:55.012");
		awayTransaction.setTollSystemType("C");
		Mockito.when(dateValidatorImpl.isValid("2021-08-31")).thenReturn(true);
		Mockito.when(dateTimeValidatorImpl.isValid("2021-08-31 12:45:55.012")).thenReturn(true);
		Boolean validationFlag = validationServiceImpl.validate(awayTransaction);
		assertTrue(validationFlag);
	}

}
