package com.conduent.tpms.ibts.away.tx.exception;

public class DuplicateTransactionException extends RuntimeException {

	
	private static final long serialVersionUID = 5150960831247976457L;

	public DuplicateTransactionException(String message) {
		super(message);
	}

}
