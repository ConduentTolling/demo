package com.conduent.tpms.intx.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.conduent.tpms.intx.constants.IntxConstant;

/**
 * Local Date Time Utility Class
 * 
 * @author deepeshb
 *
 */
@Component
public class LocalDateTimeUtility {

	/**
	 * Get Date in YYYYMMDD format
	 * 
	 * @param tempLocalDateTime
	 * @return
	 */
	public String getDateYYYYMMDD(LocalDateTime tempLocalDateTime) {
		return tempLocalDateTime.format(DateTimeFormatter.ofPattern(IntxConstant.FORMAT_yyyyMMdd));
	}



	/**
	 * Get LocalDatetime
	 * 
	 * @param tempLocalDateTime
	 * @param format
	 * @return LocalDateTime
	 */
	public LocalDateTime getLocalDateTime(String tempLocalDateTime, String format) {
		return LocalDateTime.parse(tempLocalDateTime, DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Get DateTimeByFormat
	 * 
	 * @param tempLocalDateTime
	 * @param format
	 * @return String
	 */
	public String getDateTimeByFormat(LocalDateTime tempLocalDateTime, String format) {
		return tempLocalDateTime.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Get UTC LocalDateTime
	 * 
	 * @param tempLocalDateTime
	 * @return LocalDateTime
	 */
	public LocalDateTime getUTCLocalDateTime(LocalDateTime tempLocalDateTime) {
		ZonedDateTime zonedDateTime = tempLocalDateTime.atZone(ZoneId.systemDefault());
		return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
	}
}
