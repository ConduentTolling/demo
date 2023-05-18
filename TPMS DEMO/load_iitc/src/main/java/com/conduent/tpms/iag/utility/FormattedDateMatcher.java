package com.conduent.tpms.iag.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;

@Component
public class FormattedDateMatcher {
	
	@Autowired
	TimeZoneConv timeZoneConv;

	public  int checkDateFormat(String fileDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			LocalDateTime fileDateTime = LocalDateTime.parse(fileDate, formatter);

			if (fileDateTime.isAfter(timeZoneConv.currentTime())) {
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
