package com.conduent.tpms.ibts.away.tx.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class DateTimeValidatorImpl implements DateTimeValidator {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public boolean isValid(String dateStr) {
		try {
			LocalDateTime.parse(dateStr, this.dateTimeFormatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
