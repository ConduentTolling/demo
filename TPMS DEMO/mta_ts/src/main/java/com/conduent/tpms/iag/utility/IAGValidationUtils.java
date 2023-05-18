package com.conduent.tpms.iag.utility;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;

@Component
public class IAGValidationUtils {

	
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
	
		/**
		 * Validates tag status
		 * @param tagStatus
		 * @return true/false
		 */
	//tagStatus == Constants.TS_VALID || tagStatus == Constants.TS_LOW || tagStatus == Constants.TS_INVALID|| tagStatus == Constants.TS_LOST
		public boolean validateTagStatus(int tagStatus) {
			if (tagStatus == Constants.TS_VALID || tagStatus == Constants.TS_LOW || tagStatus== Constants.TAGREPLINISH || tagStatus == Constants.TS_INVALID
					) {
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
		
		public LocalDate getFormattedDate(String dateValue, String formatPattern) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
			return LocalDate.parse(dateValue, formatter);

		}
		
		public String getFormattedDateString(String dateValue, String formatPattern) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
			String date = String.valueOf(LocalDate.parse(dateValue, formatter));
			return date;
		}

}
