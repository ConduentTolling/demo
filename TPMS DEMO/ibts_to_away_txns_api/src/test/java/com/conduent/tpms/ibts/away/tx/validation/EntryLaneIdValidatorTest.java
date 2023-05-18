package com.conduent.tpms.ibts.away.tx.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

@ExtendWith(MockitoExtension.class)
public class EntryLaneIdValidatorTest {

	@Test
	void testValidEntryLaneId() {
		EntryLaneIdValidator entryLaneIdValidator = new EntryLaneIdValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("B");
		boolean respFlag = entryLaneIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryLaneId(1);
		respFlag = entryLaneIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		respFlag = entryLaneIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction = null;
		respFlag = entryLaneIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);
	}

	@Test
	void testInValidEntryLaneId() {
		EntryLaneIdValidator entryLaneIdValidator = new EntryLaneIdValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("C");
		boolean respFlag = entryLaneIdValidator.isValid(awayTransaction, null);
		assertFalse(respFlag);
	}
}
