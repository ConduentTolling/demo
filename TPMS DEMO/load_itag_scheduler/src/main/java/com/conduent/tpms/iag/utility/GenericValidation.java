package com.conduent.tpms.iag.utility;

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

import com.conduent.tpms.iag.constants.IITCConstants;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Component
public class GenericValidation {
	
	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);
	
	public boolean doValidation(MappingInfoDto fMapping, String value) {
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		switch(validationType) {
		case IITCConstants.FIXED_VALUE:
			isValid = fixStringValidation(value.trim(), fMapping.getFixeddValidValue());
			break;
		case IITCConstants.RANGE:
			isValid = rangeValidation(value.trim(), fMapping.getMinRangeValue(),
			fMapping.getMaxRangeValue());
			break;
		case IITCConstants.DATE:
			isValid = dateValidation(value.trim(), fMapping.getValidationValue());
			break;
		case IITCConstants.TIME:
			isValid = timeValidation(value.trim(), fMapping.getValidationValue());
			break;
		case IITCConstants.REGEX:
			isValid = validateDefaultValue(value, fMapping.getValidationValue());
			if (!isValid) {
				isValid = regexValidation(value, fMapping.getValidationValue());
			}
			break;
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
                if (inputValue.length() != 6) {
                    return false;}
                try {
                    int hr = Integer.parseInt(inputValue.substring(0, 2));
                    int min = Integer.parseInt(inputValue.substring(2, 4));
                    int sec = Integer.parseInt(inputValue.substring(4, 6));
                    LocalTime.of(hr, min, sec);
                    isValid= true;
                } catch (NumberFormatException e) {
                    // TO DO: check
                	isValid= false;
                } catch (DateTimeException e) {
                    // TO DO: check
                	isValid= false;
                }
               
                break;
            case "HHMMSSTT":
                if (inputValue.length() != 8)
                	isValid=false;
                // TO DO: Logic check on TT
                break;
            default:
            	isValid=false;
            }
        }
        return isValid;
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
				{
					return true;
				}
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;
			case "YYYYMMDDHHMMSS":
				try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
				LocalDate todaydate = LocalDate.now();
				LocalDate inputdate = LocalDate.parse(inputValue, formatter);
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
		log.info("File date cannot be {}",inputValue);
		return false;
	}
	
	
	public LocalDateTime getFormattedDateTime(String fileDateTime) throws DateTimeParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		//validate for !future date fileDateTime
		return LocalDateTime.parse(fileDateTime, formatter);

	}
	
	public String getTodayTimestamp() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        String formatDateTime = LocalDateTime.now().format(formatter);
	        return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");
	        
	}
	
	public String getTodayTimestamp(LocalDateTime date,String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

		 String formatDateTime = date.format(formatter);
		return formatDateTime;
		}
	
	public LocalDate getFormattedDate(String dateValue,String formatType) throws DateTimeParseException{
		
		
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
	public String getTodayTimestamp(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = date.format(formatter);
		return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");

	}
	
	public Boolean validateDefaultValue(String inputValue, String defaultValue) {
		if (!StringUtils.isBlank(inputValue) && inputValue.equalsIgnoreCase(defaultValue)) {
			return true;
		}
		return false;
	}
	
	public Boolean regexValidation(String inputValue, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputValue);
		return m.matches();
	}
	
	
	

}
