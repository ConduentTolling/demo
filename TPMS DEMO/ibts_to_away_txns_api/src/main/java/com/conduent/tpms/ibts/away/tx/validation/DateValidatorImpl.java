package com.conduent.tpms.ibts.away.tx.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class DateValidatorImpl implements DateValidator {

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public boolean isValid(String dateStr) {
		try {
			LocalDate.parse(dateStr, this.dateFormatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
