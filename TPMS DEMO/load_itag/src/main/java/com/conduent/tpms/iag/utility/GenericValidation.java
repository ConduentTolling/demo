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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.ItagConstants;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Component
public class GenericValidation {
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);
	
	public boolean doValidation(MappingInfoDto fMapping, String value) {
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		
		
		switch(validationType) {
		case ItagConstants.FIXED_VALUE:
			isValid = fixStringValidation(value, fMapping.getFixeddValidValue());
			break;
		case ItagConstants.RANGE:
			isValid = rangeValidation(value, fMapping.getMinRangeValue(),
			fMapping.getMaxRangeValue());
			break;
		case ItagConstants.LOV:
			isValid = listOfValue(value, fMapping.getListOfValidValues());
			break;
		case ItagConstants.DATE:
			isValid = dateValidation(value, fMapping.getValidationValue());
			break;
		case ItagConstants.TIME:
			isValid = timeValidation(value, fMapping.getValidationValue());
			break;
		case ItagConstants.DATETIME: 
			isValid = dateTimevalidation(value, ItagConstants.DATETIMEFORMAT);
			break;
		case ItagConstants.REGEX:
			isValid= validateRegex(value, fMapping.getValidationValue());
		}
		return isValid;
	}
	public Boolean rangeValidation(String inputValue, Long min, Long max) {
			try
			{
				if (!StringUtils.isBlank(inputValue)) {
					long tempValue = Long.parseLong(inputValue);
					if (tempValue >= min && tempValue <= max) {
						return true;
					}
				}
			log.info(inputValue + " is a valid integer");
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
			default:
				return false;
			}
		}
		return false;
	}
	
	public Boolean dateTimevalidation(String inputValue, String validValue) {
		
		
	
		if (!StringUtils.isBlank(inputValue)) {
			//Below remark is not fixed , if we want to add another format in future
			try {
	             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(validValue);
	             //LocalDate todaydate = LocalDate.now();
	             LocalDateTime inputDate = LocalDateTime.parse(inputValue, dateFormatter); 	
	             LocalDate inputLocalDate =inputDate.toLocalDate();
	             LocalDate todayDate =LocalDate.now();
	             
					/*
					 * LocalTime inputLocalTime= inputDate.toLocalTime();
					 * 
					 * 
					 * String actualTime = inputLocalTime.toString();
					 * 
					 * 
					 * int hr= Integer.parseInt(actualTime.substring(0, 2)); int mm=
					 * Integer.parseInt(actualTime.substring(2,4)); int sec=
					 * Integer.parseInt(actualTime.substring(4,6));
					 * 
					 * LocalTime.of(hr,mm,sec);
					 */
	            
	            if (!inputLocalDate.isAfter(todayDate))
						return true;
					
                 System.out.println(inputDate);
	        }
			 catch (DateTimeParseException e) {
					/*
					 * if(fMapping.getFieldType().equals("FILENAME")) {
					 * 
					 * }
					 */
	            return false;
	        }
		}
		
		return false;
	}
	
	public Boolean validateRegex(String inputValue, String validValue) {
		
		Pattern p = Pattern.compile(validValue);
		if (inputValue == null) {
			return false;
		} else {
			Matcher m = p.matcher(inputValue);
			return m.matches();
		}
	}
	public LocalDateTime getFormattedDateTime(String fileDateTime) throws DateTimeParseException {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
	   //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		//validate for !future date fileDateTime
		return LocalDateTime.parse(fileDateTime, formatter);

	}
	
	public String getTodayTimestamp() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        String formatDateTime = timeZoneConv.currentTime().format(formatter);
	        return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");
	        
	}
	
	public String getTodayTimeStampAck() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ItagConstants.DATETIMEFORMAT);

		String formatDateTime = timeZoneConv.currentTime().format(formatter);
		 //LocalDate todayDate122 =LocalDate.now();
		LocalDateTime dateTimeack = LocalDateTime.parse(formatDateTime, formatter);
	    String datetimeack= dateTimeack.toString();
	     return datetimeack;
		
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
	
	public  LocalDateTime getFormattedDateTimeNew(String dateValue) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
      
        return  LocalDateTime.parse(dateValue, dateFormatter); 	
		
		
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
