package com.conduent.tpms.ibts.away.tx.exception;

import java.util.List;

public class DateTimeFormatException extends RuntimeException {


	private static final long serialVersionUID = -4191649399211214306L;
	private List<String> errorMessages;

	public DateTimeFormatException(String message, List<String> errorMessages) {
		super(message);
		this.errorMessages = errorMessages;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessages = errorMessage;
	}

}
