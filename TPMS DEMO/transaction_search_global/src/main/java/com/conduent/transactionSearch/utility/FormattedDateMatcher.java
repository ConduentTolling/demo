package com.conduent.transactionSearch.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class FormattedDateMatcher {

	public int checkDateFormat(String fileDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			LocalDateTime fileDateTime = LocalDateTime.parse(fileDate, formatter);

			if (fileDateTime.isAfter(LocalDateTime.now())) {
				return 1;// invalid date
			} else {
				return 0;// valid date
			}
		} catch (IllegalArgumentException exception) {
			// IllegalArgumentException if the pattern is invalid
			return 1;
		}
	}

	public LocalDateTime getFormattedDateTime(String fileDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		return LocalDateTime.parse(fileDateTime, formatter);

	}
}
