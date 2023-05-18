package com.conduent.tpms.qatp.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.conduent.tpms.qatp.constants.CorrConstants;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;

@Component
public class GenericValidation {

	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);

	public boolean doValidation(MappingInfoDto fMapping, String value)
	{
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		switch(validationType) {
		case CorrConstants.FIXED_VALUE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = fixStringValidation(value, fMapping.getFixeddValidValue());
			}
			break;
		case CorrConstants.RANGE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = rangeValidation(value, fMapping.getMinRangeValue(),
						fMapping.getMaxRangeValue());
			}
			break;
		case CorrConstants.LOV:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = listOfValue(value, fMapping.getListOfValidValues());
			}
			break;
		case CorrConstants.DATE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = dateValidation(value, fMapping.getValidationValue());
			}
			break;
		case CorrConstants.TIME:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = timeValidation(value, fMapping.getValidationValue());
			}
			break;
		case CorrConstants.REGEX:
			isValid = validateDefaultValue(value, fMapping.getValidationValue());
			if (!isValid) {
				isValid = regexValidation(value, fMapping.getValidationValue());
			}
			break;
		default:
			isValid = false;
		}
		return isValid;
	}
	public Boolean rangeValidation(String inputValue, Long min, Long max) {
		// TO DO: Handle Number format Exception if string
		if (!StringUtils.isBlank(inputValue)) {
			long tempValue = Long.parseLong(inputValue);
			if (tempValue >= min && tempValue <= max) {
				return true;
			}
		}
		return false;
	}
	public Boolean timeValidation(String inputValue, String validValue) {
		boolean isValid=false;
		if (!StringUtils.isBlank(inputValue)) {
			switch (validValue) {
			case "HHMMSS":
				if (inputValue.length() != 6)
				{
					return false;
				}
				try 
				{
					int hr = Integer.parseInt(inputValue.substring(0, 2));
					int min = Integer.parseInt(inputValue.substring(2, 4));
					int sec = Integer.parseInt(inputValue.substring(4, 6));
					if((hr >=0 && hr<24) && (min >=0 && min<=59) && (sec >=0 && sec<=59) )
					{
						isValid= true;
					}
					else
					{
						isValid= false;
					}

				} catch (NumberFormatException e) 
				{
					isValid= false;
				} 
				break;
			case "HHMMSSTT":
				if (inputValue.length() != 8)
				{
					isValid=false;
				}
				else
				{
					isValid=true;
					try 
					{
						int hr = Integer.parseInt(inputValue.substring(0, 2));
						int min = Integer.parseInt(inputValue.substring(2, 4));
						int sec = Integer.parseInt(inputValue.substring(4, 6));
						int tt = Integer.parseInt(inputValue.substring(6, 8));
						if((hr >=0 && hr<24) && (min >=0 && min<=59) && (sec >=0 && sec<=59) && (tt >=0 && sec<=99) )
						{
							isValid= true;
						}
						else
						{
							isValid= false;
						}

					} catch (NumberFormatException e) 
					{
						isValid= false;
					} 
				}
				break;
			default:
				isValid=false;
			}
		}
		return isValid;
	}
	public Boolean listOfValue(String inputValue, List<String> validValueList) {
		return !StringUtils.isBlank(inputValue) && validValueList.contains(inputValue);
	}

	public Boolean validateDefaultValue(String inputValue, String defaultValue)
	{
		if(!StringUtils.isBlank(inputValue)
				&& inputValue.equalsIgnoreCase(defaultValue)) {
			return true;
		}
		return false;
	}

	public Boolean dateValidation(String inputValue, String validValue) {
		// TO DO: Handle Number format Exception if string
		if (!StringUtils.isBlank(inputValue)) {
			//Below remark is not fixed , if we want to add another format in future
			switch(validValue) {
			case "YYYYMMDD":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
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
	public LocalDateTime getFormattedDateTime(String fileDateTime) throws InvalidFileNameException 
	{
		try 
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			return LocalDateTime.parse(fileDateTime, formatter);
		} catch (Exception e) 
		{
			throw new InvalidFileNameException("Invalid File Date or Time");
		}

	}

	public String getTodayTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = LocalDateTime.now().format(formatter);
		return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");

	}
	public String getTodayTimestamp(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = date.format(formatter);
		return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");

	}
	public String getTodayTimestamp(LocalDateTime date,String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

		String formatDateTime = date.format(formatter);
		return formatDateTime;
	}

	public LocalDate getFormattedDate(String dateValue,String formatType) throws InvalidFileHeaderException {


		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");



			return LocalDate.parse(dateValue, formatter);
		} catch (Exception e) 
		{
			throw new InvalidFileHeaderException("Invalid Date");
		}

	}

	public Boolean TimeValidation(String inputValue, String validValue) {

		return null;
	}

	public Boolean fixStringValidation(String inputValue, String validValue) {
		if(!StringUtils.isBlank(inputValue) && validValue.equalsIgnoreCase(inputValue)) {
			return true;
		}
		return false;

	}
	
	public boolean buildDate(String value, String ValidationValue) {
		String regex = "^[*]{8}$"; boolean isValid = false;
		if(value.contains("*")) {
			isValid = regexValidation(value, regex);
		}else {
			isValid = dateValidation(value.trim(), ValidationValue);
		}
		return isValid;
	}
	
	public Boolean regexValidation(String inputValue, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputValue);
		return m.matches();
	}
	
	
	public boolean buildTime(String value, String ValidationValue) {
		String regex = "^[*]{6}$"; boolean isValid = false;
		if(value.contains("*")) {
			isValid = regexValidation(value, regex);
		}else {
			isValid = timeValidation(value.trim(), ValidationValue);
		}
		return isValid;
	}



}
