package com.conduent.tpms.ibts.away.tx.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ibts.away.tx.dao.AwayTransactionDao;
import com.conduent.tpms.ibts.away.tx.exception.DuplicateTransactionException;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;
import com.conduent.tpms.ibts.away.tx.service.ValidationService;

@ExtendWith(MockitoExtension.class)
public class AwayTransactionServiceImplTest {

	@Mock
	ValidationService validationService;

	@Mock
	AwayTransactionDao awayTransactionDao;

	@InjectMocks
	AwayTransactionServiceImpl awayTransactionServiceImpl;

	@Test
	void testDuplicateTransaction() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setLaneTxId(101L);

		AwayTransaction awayTransactionDb = new AwayTransaction();
		awayTransactionDb.setLaneTxId(101L);

		Mockito.when(validationService.validate(awayTransaction)).thenReturn(true);

		Mockito.when(awayTransactionDao.checkLaneTxId(101L)).thenReturn(awayTransactionDb);

		DuplicateTransactionException exception = assertThrows(DuplicateTransactionException.class, () -> {
			awayTransactionServiceImpl.insert(awayTransaction);
			;
		});
		String actualExceptionMsg = "Transaction already exists with lane transaction id:101";
		String expectedExceptionMsg = exception.getMessage();

		assertTrue(actualExceptionMsg.contains(expectedExceptionMsg));
	}

	@Test
	void testValidTransaction() {
		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setLaneTxId(101L);

		AwayTransaction awayTransactionDb = new AwayTransaction();
		awayTransactionDb.setLaneTxId(101L);

		Mockito.when(validationService.validate(awayTransaction)).thenReturn(true);

		Mockito.when(awayTransactionDao.checkLaneTxId(101L)).thenReturn(null);

		Boolean responseFlag = awayTransactionServiceImpl.insert(awayTransaction);
		assertTrue(responseFlag);
	}
}
