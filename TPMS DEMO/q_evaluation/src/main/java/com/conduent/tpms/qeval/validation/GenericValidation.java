package com.conduent.tpms.qeval.validation;

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

/**
 * Controller class
 * 
 * @author Urvashic
 *
 */

@Component
public class GenericValidation {
	
	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);
	
	//public boolean doValidation(MappingInfoDto fMapping, String value)
/*	public boolean doValidation(String fMapping, String value) throws NumberFormatException{
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		
		switch(validationType) {
		case Constants.FIXED_VALUE:
			isValid = fixStringValidation(value.trim(), fMapping.getFixeddValidValue());
			break;
		case Constants.RANGE:
			isValid = rangeValidation(value.trim(), fMapping.getMinRangeValue(),
			fMapping.getMaxRangeValue());
			break;
		case Constants.DATE:
			isValid = dateValidation(value.trim(), fMapping.getValidationValue());
			break;
		case Constants.TIME:
			isValid = timeValidation(value.trim(), fMapping.getValidationValue());
			break;
		case Constants.REGEX:
			isValid = validateDefaultValue(value, fMapping.getValidationValue());
			if (!isValid) {
				isValid = regexValidation(value, fMapping.getValidationValue());
			}
			break;
		case ICTXConstants.LOV:
			isValid = listOfValue(value, fMapping.getListOfValidValues());
			break;
		}
		return isValid;
	}
	*/
	public Boolean rangeValidation(String inputValue, Long min, Long max) throws NumberFormatException {
		// TO DO: Handle Number format Exception if string
		try {
		if (!StringUtils.isBlank(inputValue)) {
			long tempValue = Long.parseLong(inputValue);
			if (tempValue >= min && tempValue <= max) {
				return true;
			}
		}
		}catch(NumberFormatException ex) {
		//	throw new NumberFormatException(ex.getMessage());
			log.error("NumberFormatException for value {}",inputValue);
			return false;
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
	
	public Boolean listOfValue(String inputValue, List<String> validValueList) {
		return !StringUtils.isBlank(inputValue) && validValueList.contains(inputValue);
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
	
	public boolean buildDate(String value, String ValidationValue) {
		String regex = "^[*]{8}$"; boolean isValid = false;
		if(value.contains("*")) {
			isValid = regexValidation(value, regex);
		}else {
			isValid = dateValidation(value.trim(), ValidationValue);
		}
		return isValid;
	}
	
	public boolean validateAgencyMatch(String agencyId, String fileAgencyID)  { 
		if (fileAgencyID.equals(agencyId)) {
			return true;
		} else {
			log.info("Agency id {} mismatch with file agency id {}.", agencyId, fileAgencyID);
			return false;
		}
	}
	

}
