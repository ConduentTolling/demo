package com.conduent.tpms.ictx.utility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.conduent.tpms.ictx.constants.IctxConstant;

/**
 * Local Time Utility Class
 * 
 * @author deepeshb
 *
 */
@Component
public class LocalTimeUtility {

	/**
	 * Get Seconds
	 * 
	 * @param input
	 * @param format
	 * @return int
	 */
	public int getSeconds(String input, String format) {
		return LocalTime.parse(input, DateTimeFormatter.ofPattern(format)).getSecond();
	}

	/**
	 * Get Time in HHMMSS
	 * 
	 * @param tempLocalDateTime
	 * @return String
	 */
	public String getTimeHHMMSS(LocalDateTime tempLocalDateTime) {
		return tempLocalDateTime.format(DateTimeFormatter.ofPattern(IctxConstant.FORMAT_HHmmss));
	}

}
