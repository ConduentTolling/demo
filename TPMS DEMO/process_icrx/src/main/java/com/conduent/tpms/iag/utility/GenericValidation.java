package com.conduent.tpms.iag.utility;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.ICRXConstants;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Component
public class GenericValidation {
	
	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);
	
	public boolean doValidation(MappingInfoDto fMapping, String value) {
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		switch(validationType) {
		case ICRXConstants.FIXED_VALUE:
			isValid = fixStringValidation(value, fMapping.getFixeddValidValue());
			break;
		case ICRXConstants.RANGE:
			isValid = rangeValidation(value, fMapping.getMinRangeValue(),
			fMapping.getMaxRangeValue());
			if(value.equalsIgnoreCase(ICRXConstants.HUB_AGENCY_ID) && (fMapping.getFieldName().equalsIgnoreCase(ICRXConstants.F_FROM_AGENCY_ID) || 
					fMapping.getFieldName().equalsIgnoreCase(ICRXConstants.H_FROM_AGENCY_ID))) {
				isValid = true;
			}
			break;
		case ICRXConstants.LOV:
			isValid = listOfValue(value, fMapping.getListOfValidValues());
			break;
		case ICRXConstants.DATE:
			isValid = dateValidation(value, fMapping.getValidationValue());
			break;
		case ICRXConstants.TIME:
			isValid = timeValidation(value, fMapping.getValidationValue());
			break;
		}
		return isValid;
	}
	public Boolean rangeValidation(String inputValue, Long min, Long max) {
			try
			{ if(StringUtils.isBlank(inputValue))
			{
				log.info(inputValue + "is a valid integer for blank value");
				return true;
			}else {
				if (!StringUtils.isBlank(inputValue)) {
					long tempValue = Long.parseLong(inputValue);
					if (tempValue >= min && tempValue <= max) {
						return true;
					}
				}
			log.info(inputValue + " is a valid integer");
			}
			}
			catch (NumberFormatException e)
			{
			log.info(inputValue + " is not a valid integer");
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
	public Boolean listOfValue(String inputValue, List<String> validValueList) {
		return !StringUtils.isBlank(inputValue) && validValueList.contains(inputValue);
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
				LocalDate todaydate = LocalDate.now();
				LocalDate inputdate = LocalDate.parse(inputValue, formatter);
				if (!inputdate.isAfter(todaydate))
					return true;
				}
				catch(DateTimeParseException e) {
					//TO DO: check 
					return false;
				}
				break;
			case "YYYY-MM-DDThh:mm:ssZ":
				try {
				
				String latestVal = inputValue.replaceAll("T", " ");
				latestVal = latestVal.replaceAll("Z", "");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime todaydate = LocalDateTime.now();
				LocalDateTime inputdate = LocalDateTime.parse(latestVal, formatter);
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
	
	public String getTodayTimestampNew() {
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formatDateTime = LocalDateTime.now().format(formatter);
	        return formatDateTime.replaceAll("\\s", "T")+"Z";
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
	
	

}
