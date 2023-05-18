package com.conduent.tpms.iag.utility;

import java.time.DateTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.ICLPDetailInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Component
public class GenericValidation {
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	protected List<String> validStateList;
	
	@Autowired(required = true)
	protected TQatpMappingDao tQatpMappingDao;

	private static final Logger log = LoggerFactory.getLogger(GenericValidation.class);

	public boolean doValidation(MappingInfoDto fMapping, String value) {
		boolean isValid = false;
		String validationType = fMapping.getValidationType();
		log.info(validationType);
		switch (validationType) {
		case ICLPConstants.FIXED_VALUE:
			isValid = fixStringValidation(value, fMapping.getFixeddValidValue());
			break;
		case ICLPConstants.RANGE:
			isValid = rangeValidation(value, fMapping.getMinRangeValue(), fMapping.getMaxRangeValue());
			break;
		case ICLPConstants.LOV:
			isValid = listOfValue(value, fMapping.getListOfValidValues());
			break;
		case ICLPConstants.DATE:
			isValid = dateValidation(value, fMapping.getValidationValue());
			break;
		case ICLPConstants.TIME:
			isValid = timeValidation(value, fMapping.getValidationValue());
			break;
		case ICLPConstants.DATETIME: 
			isValid = dateTimevalidation(value, ICLPConstants.DATETIMEFORMAT);
			break;
		case ICLPConstants.REGEX:
			isValid = validateDefaultValue(value, fMapping.getValidationValue());
			if (!isValid) {
				isValid = regexValidation(value, fMapping.getValidationValue());
				if(isValid) {
					
					if(fMapping.getFieldName().equals("D_LIC_STATE")) {
						boolean stateval =validateLicState(value);
						if(stateval) {
							return true;
						}
						else {
							return false;
						}
					}else {
						return isValid;
					}
				}
				
				
			}
			break;

		}
		return isValid;
	}
	
	
    public boolean validateLicState(String state) {
    	Boolean stateval= false;
    	
    	validStateList =tQatpMappingDao.getLicState();
    	
    	Optional<String> statevalid = validStateList.stream().filter(s -> s.equals(state)).findAny();
    	if(statevalid.isPresent()) {
    		stateval =true;
    	}
    	
    	return stateval;
    }
	

	public Boolean rangeValidation(String inputValue, Long min, Long max) {
		// TO DO: Handle Number format Exception if string
		if(inputValue !=null && inputValue.equals("**")) {
			inputValue="0";
		}
		if (!StringUtils.isBlank(inputValue)) {
			
			try {
				long tempValue = Long.parseLong(inputValue);
				if (tempValue >= min && tempValue <= max) {
					return true;
				}
			}catch(NumberFormatException e) {
				return false;
			}
			
		}
		
		
		
		return false;
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

	public Boolean dateValidation(String inputValue, String validValue) {
		
		if (!StringUtils.isBlank(inputValue)) {
			
			switch (validValue) {
			case "YYYYMMDD":
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
					LocalDate inputdate = LocalDate.parse(inputValue, formatter);
					LocalDate todaydate = LocalDate.now();
					if (!inputdate.isAfter(todaydate))
						return true;
				} catch (DateTimeParseException e) {
					
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
			
			
			boolean dateval = validate(inputValue);
			if(dateval) {
				
				return true;
				
			}else {
				
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
		            return false;
		        }
				
			}
				
			
		}
		
		return false;
	}
	
	
	public boolean validate(String value)
    {
        String matcher_String = "\\s{"+value.length()+"}|[*]{"+value.length()+"}";
        if(value.matches(matcher_String)) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	
	
	public LocalDateTime getFormattedDateTime(String fileDateTime) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return LocalDateTime.parse(fileDateTime, formatter);
	}

	public String getTodayTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String formatDateTime = timeZoneConv.currentTime().format(formatter);
		return formatDateTime.replaceAll("\\s", "").replaceAll(":", "").replaceAll("-", "");

	}
	
	public String getTodayTimeStampAck() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ICLPConstants.DATETIMEFORMAT);

		String formatDateTime = timeZoneConv.currentTime().format(formatter);
		 //LocalDate todayDate122 =LocalDate.now();
		LocalDateTime dateTimeack = LocalDateTime.parse(formatDateTime, formatter);
	    String datetimeack= dateTimeack.toString();
	     return datetimeack;
		
	}

	public LocalDate getFormattedDate(String dateValue, String formatType) {

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
		if (!StringUtils.isBlank(inputValue) && validValue.equalsIgnoreCase(inputValue)) {
			return true;
		}
		return false;

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
