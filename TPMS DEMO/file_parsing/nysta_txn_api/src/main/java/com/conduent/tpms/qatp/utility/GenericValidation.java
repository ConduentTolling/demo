package com.conduent.tpms.qatp.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dto.MappingInfoDto;

@Component
public class GenericValidation {

	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);

	public boolean doValidation(MappingInfoDto fMapping, String value)
	{
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		switch(validationType) {
		case Constants.FIXED_VALUE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = fixStringValidation(value, fMapping.getFixeddValidValue());
			}
			break;
		case Constants.RANGE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = rangeValidation(value, fMapping.getMinRangeValue(),
						fMapping.getMaxRangeValue());
			}
			break;
		case Constants.LOV:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = listOfValue(value, fMapping.getListOfValidValues());
			}
			break;
		case Constants.DATE:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = dateValidation(value, fMapping.getValidationValue());
			}
			break;
		case QATPConstants.TIME:
			isValid =validateDefaultValue(value, fMapping.getDefaultValue());
			if(!isValid)
			{
				isValid = timeValidation(value, fMapping.getValidationValue());
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
	public LocalDateTime getFormattedDateTime(String fileDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		return LocalDateTime.parse(fileDateTime, formatter);

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

	public LocalDate getFormattedDate(String dateValue,String formatType) {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");



		return LocalDate.parse(dateValue, formatter);

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



}
