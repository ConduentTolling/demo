package com.conduent.tpms.qatp.utility;

import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dto.MappingInfoDto;

@Component
public class GenericValidation {

	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);

	public boolean doValidation(MappingInfoDto fMapping, String value) {
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		
		switch (validationType) 
		{
			case Constants.FIXED_VALUE:
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = fixStringValidation(value, fMapping.getFixeddValidValue(), fMapping.getDefaultValue());
				}
	
				break;
			case Constants.RANGE:
				
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = rangeValidation(value, fMapping.getMinRangeValue(), fMapping.getMaxRangeValue());
				}
				break;
			case Constants.LOV:
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = listOfValue(value, fMapping.getListOfValidValues());
				}
				break;
			case Constants.DATE:
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = dateValidation(value, fMapping.getValidationValue());
				}
				break;
			case Constants.TIME:
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = timeValidation(value, fMapping.getValidationValue());
				}
				break;
			case Constants.REGEX:
				isValid = validateDefaultValue(value, fMapping.getDefaultValue());
				if (!isValid) 
				{
					isValid = regexValidation(value, fMapping.getValidationValue());
				}
				break;
		}
		return isValid;
	}

	public Boolean rangeValidation(String inputValue, BigInteger min, BigInteger max) {
		// TO DO: Handle Number format Exception if string
		if (!StringUtils.isBlank(inputValue) && inputValue.matches("[0-9]+")) {
			//long tempValue = Long.parseLong(inputValue);
			BigInteger tempValue = new BigInteger(inputValue);
			//if (tempValue >= min && tempValue <= max)
			if((tempValue.compareTo(min) == 1 || tempValue.compareTo(min) == 0)
					&& (tempValue.compareTo(max) == -1 || tempValue.compareTo(max) == 0) )
			{
				return true;
			}
		}
		return false;
	}

	public Boolean regexValidation(String inputValue, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputValue);
		return m.matches();
	}

	public Boolean timeValidation(String inputValue, String validValue) {
		boolean isValid = false;
		if (!StringUtils.isBlank(inputValue)) {
			switch (validValue) {
			case "HHMMSS":
				if (inputValue.length() != 6) {
					return false;
				}
				try {
					int hr = Integer.parseInt(inputValue.substring(0, 2));
					int min = Integer.parseInt(inputValue.substring(2, 4));
					int sec = Integer.parseInt(inputValue.substring(4, 6));
					LocalTime.of(hr, min, sec);
					isValid = true;
				} catch (NumberFormatException e) {
					// TO DO: check
					isValid = false;
				} catch (DateTimeException e) {
					// TO DO: check
					isValid = false;
				}

				break;
			case "HHMMSSTT":
				if (inputValue.length() != 8)
					isValid = false;
				// TO DO: Logic check on TT
				break;
			default:
				isValid = false;
			}
		}
		return isValid;
	}

	public Boolean listOfValue(String inputValue, List<String> validValueList) {
		return !StringUtils.isBlank(inputValue) && validValueList.contains(inputValue);
	}

	public Boolean validateDefaultValue(String inputValue, String defaultValue) {
		if (!StringUtils.isBlank(inputValue) && inputValue.equalsIgnoreCase(defaultValue)) {
			return true;
		}
		return false;
	}

	public Boolean dateValidation(String inputValue, String validValue) {
		// TO DO: Handle Number format Exception if string
		if (!StringUtils.isBlank(inputValue)) {
			// Below remark is not fixed , if we want to add another format in future
			switch (validValue) {
			case "YYYYMMDD":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				} catch (DateTimeParseException e) {
					// TO DO: check
					return false;
				}
				break;
			case "YYYYMMDD2":
				try {
					if(inputValue.matches(".*[a-zA-Z].*") || inputValue.contains(" ") || inputValue.equals("        "))
					{
						return false;
					}
					else
					{
						int year = Integer.parseInt(inputValue.substring(0, 4));
						int month = Integer.parseInt(inputValue.substring(4, 6));
						int day = Integer.parseInt(inputValue.substring(6, 8));
											
						LocalDate inputdate = LocalDate.of(year, month, day);							
						LocalDate todaydate = LocalDate.now();	
						
						if (!inputdate.isAfter(todaydate)) 
							return true;
					}					
				} catch (DateTimeException e) {
					// TO DO: check
					return false;
				}
				break;
			case "YYYYMMDDHHMMSS":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				} catch (DateTimeParseException e) {
					// TO DO: check
					return false;
				}
				break;
			
			case "YYYY-MM-DDThh:mm:ssZ":
				try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
				LocalDateTime todaydate = LocalDateTime.now();
				LocalDateTime inputdate = LocalDateTime.parse(inputValue, formatter);
				if (!inputdate.isAfter(todaydate))
				{
					return true;
				}
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;
			case "YYYY-MM-DDThh:mm:ss±HH:MM":
				if (inputValue.equals("*************************")) {
					return true;
				}
				try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
				LocalDateTime todaydate = LocalDateTime.now();
				LocalDateTime inputdate = LocalDateTime.parse(inputValue, formatter);
				if (!inputdate.isAfter(todaydate))
				{
					return true;
				}
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;
				
			default:
				return false;
			}
		}
		return false;
	}

	public LocalDateTime getFormattedDateTime(String fileDateTime, String defaultValue) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		return LocalDateTime.parse(fileDateTime, formatter);

	}

	public String getTodayTimestamp(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = LocalDateTime.now().format(formatter);
		return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");

	}
	
	public String getTodayTimestamp(LocalDateTime date,String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

		String formatDateTime = date.format(formatter);
		return formatDateTime;
	}

	public LocalDate getFormattedDate(String dateValue, String formatType) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		return LocalDate.parse(dateValue, formatter);

	}

	public Boolean TimeValidation(String inputValue, String validValue) {

		return null;
	}

	public Boolean fixStringValidation(String inputValue, String validValue, String defaultValue) {
		if (!StringUtils.isBlank(inputValue) && validValue.equalsIgnoreCase(inputValue)) {
			return true;
		}
		return false;

	}
	
	

	

}
