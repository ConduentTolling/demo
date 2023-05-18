package com.conduent.tpms.ibts.away.tx.exception;

import java.util.List;

public class DateFormatException extends RuntimeException {

	private static final long serialVersionUID = -396291721252653890L;
	private List<String> errorMessages;

	public DateFormatException(String message, List<String> errorMessages) {
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
