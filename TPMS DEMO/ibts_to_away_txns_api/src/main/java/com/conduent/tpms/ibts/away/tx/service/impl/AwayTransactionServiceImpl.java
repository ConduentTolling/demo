package com.conduent.tpms.ibts.away.tx.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ibts.away.tx.dao.AwayTransactionDao;
import com.conduent.tpms.ibts.away.tx.exception.DuplicateTransactionException;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;
import com.conduent.tpms.ibts.away.tx.service.AwayTransactionService;
import com.conduent.tpms.ibts.away.tx.service.ValidationService;

@Service
public class AwayTransactionServiceImpl implements AwayTransactionService {

	private static final Logger logger = LoggerFactory.getLogger(AwayTransactionServiceImpl.class);

	@Autowired
	ValidationService validationService;

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Override
	public boolean insert(AwayTransaction awayTransaction) {
		logger.info("Validation started for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		validationService.validate(awayTransaction);
		logger.info("Validation ended for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		AwayTransaction tempAwayTransaction = awayTransactionDao.checkLaneTxId(awayTransaction.getLaneTxId());
		if (tempAwayTransaction != null) {
			logger.info("Transaction already exists with lane tx id: {}", awayTransaction.getLaneTxId());
			StringBuilder sb = new StringBuilder();
			sb.append("Transaction already exists with lane transaction id:")
					.append(String.valueOf(awayTransaction.getLaneTxId()));
			throw new DuplicateTransactionException(String.valueOf(sb));
		}
		logger.info("Transaction insertion started  with lane tx id: {}", awayTransaction.getLaneTxId());
		awayTransactionDao.insert(awayTransaction);
		logger.info("Transaction insertion ended  with lane tx id: {}", awayTransaction.getLaneTxId());
		return true;
	}

}
