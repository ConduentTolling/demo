package com.conduent.tpms.iag.utility;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;
@Component
public class IAGValidationUtils {

	private List<String> devicePrefixList;
	
	/**
	 * Returns String with custom padding format
	 * @param pattern
	 * @param value
	 * @return
	 */
	public String customFormatString(String pattern, long value) {
		String output = null;
		try {
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			output = myFormatter.format(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public LocalDate getFormattedDate(String dateValue, String formatPattern) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
		return LocalDate.parse(dateValue, formatter);

	}
	
	
	/**
	 * Validates tag status
	 * @param tagStatus
	 * @return true/false
	 */
	public boolean validateTagStatus(int tagStatus) {
		if (tagStatus == Constants.TS_GOOD || tagStatus == Constants.TS_LOW || tagStatus == Constants.TS_ZERO
			/*	|| tagStatus == Constants.TS_LOST */) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates if input contains only digits
	 * @param str
	 * @return
	 */
	public boolean onlyDigits(String str) {
		String regex = "[0-9]+";
		Pattern p = Pattern.compile(regex);
		if (str == null) {
			return false;
		} else {
			Matcher m = p.matcher(str);
			return m.matches();
		}
	}
		
		public LocalDateTime getFormattedDateTime(String fileDateTime) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			return LocalDateTime.parse(fileDateTime, formatter);
		}
}
