package com.conduent.tpms.ibts.away.tx.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

@ExtendWith(MockitoExtension.class)
public class EntryTimestampValidatorTest {

	@Test
	void testValidEntryTimestamp() {
		EntryTimestampValidator entryTimestampValidator = new EntryTimestampValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("B");
		boolean respFlag = entryTimestampValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryTimestamp("2021-02-01 14:55:23.002");
		respFlag = entryTimestampValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		respFlag = entryTimestampValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction = null;
		respFlag = entryTimestampValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);
	}
	
	
	@Test
	void testInValidEntryTimestamp() {
		EntryTimestampValidator entryTimestampValidator = new EntryTimestampValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("C");
		boolean respFlag = entryTimestampValidator.isValid(awayTransaction, null);
		assertFalse(respFlag);
	}
}
