package com.conduent.tpms.ibts.away.tx.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

@ExtendWith(MockitoExtension.class)
public class EntryPlazaIdValidatorTest {

	@Test
	void testValidEntryPlazaId() {
		EntryPlazaIdValidator entryPlazaIdValidator = new EntryPlazaIdValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("B");
		boolean respFlag = entryPlazaIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction.setTollSystemType("C");
		awayTransaction.setEntryPlazaId(1);
		respFlag = entryPlazaIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		respFlag = entryPlazaIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);

		awayTransaction = null;
		respFlag = entryPlazaIdValidator.isValid(awayTransaction, null);
		assertTrue(respFlag);
	}
	
	
	@Test
	void testInValidEntryPlazaId() {
		EntryPlazaIdValidator entryPlazaIdValidator = new EntryPlazaIdValidator();
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setTollSystemType("C");
		boolean respFlag = entryPlazaIdValidator.isValid(awayTransaction, null);
		assertFalse(respFlag);
	}
}
