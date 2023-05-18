package com.conduent.tpms.ibts.away.tx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ibts.away.tx.constants.AwayTransactionConstant;
import com.conduent.tpms.ibts.away.tx.exception.DateFormatException;
import com.conduent.tpms.ibts.away.tx.exception.DateTimeFormatException;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;
import com.conduent.tpms.ibts.away.tx.service.ValidationService;
import com.conduent.tpms.ibts.away.tx.validation.DateTimeValidatorImpl;
import com.conduent.tpms.ibts.away.tx.validation.DateValidatorImpl;

/**
 * ICTX Process Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private DateValidatorImpl dateValidatorImpl;

	@Autowired
	private DateTimeValidatorImpl dateTimeValidatorImpl;

	private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

	@Override
	public boolean validate(AwayTransaction awayTransaction) {
		logger.info("Date validation started for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		validateDateFormat(awayTransaction);
		logger.info("Date validation ended for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		logger.info("DateTime validation started for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		validateDateTimeFormat(awayTransaction);
		logger.info("DateTime validation ended for transaction with lane tx id: {}", awayTransaction.getLaneTxId());
		return true;
	}

	private void validateDateTimeFormat(AwayTransaction awayTransaction) {
		// Validate Date time format
		List<String> errorMessages = new ArrayList<>();
		validateTxTimstamp(awayTransaction, errorMessages);
		if ("C".equalsIgnoreCase(awayTransaction.getTollSystemType())) {
			validateEntryTxTimstamp(awayTransaction, errorMessages);
		}

		if (!errorMessages.isEmpty()) {
			throw new DateTimeFormatException(AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATETIME_FORMAT,
					errorMessages);
		}
	}

	private void validateDateFormat(AwayTransaction awayTransaction) {
		// Validate date format
		List<String> errorMessages = new ArrayList<>();
		validateTxDate(awayTransaction, errorMessages);
		validateRevenueDate(awayTransaction, errorMessages);
		validatePostedDate(awayTransaction, errorMessages);
		validateExternFileDate(awayTransaction, errorMessages);

		if (!errorMessages.isEmpty()) {
			throw new DateFormatException(AwayTransactionConstant.EXCEPTION_MSG_INVALID_DATE_FORMAT, errorMessages);
		}

	}

	private void validateExternFileDate(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getExternFileDate() != null
				&& !dateValidatorImpl.isValid(awayTransaction.getExternFileDate())) {
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_EXTERN_FILE_DATE_FORMAT);
		}

	}

	private void validatePostedDate(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getPostedDate() != null && !dateValidatorImpl.isValid(awayTransaction.getPostedDate())) {
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_POSTED_DATE_FORMAT);
		}
	}

	private void validateEntryTxTimstamp(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getEntryTimestamp() == null) {
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_ENTRY_TX_TIMESTAMP_FORMAT);
		}

	}

	private void validateTxTimstamp(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getTxTimestamp() == null) {
			
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_TX_TIMESTAMP_FORMAT);
		}

	}

	private void validateRevenueDate(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getRevenueDate() != null && !dateValidatorImpl.isValid(awayTransaction.getRevenueDate())) {
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_REVENUE_DATE_FORMAT);
		}

	}

	private void validateTxDate(AwayTransaction awayTransaction, List<String> errorMessages) {
		if (awayTransaction.getTxDate() != null && !dateValidatorImpl.isValid(awayTransaction.getTxDate())) {
			errorMessages.add(AwayTransactionConstant.EXCEPTION_MSG_TX_DATE_FORMAT);
		}
	}

}
