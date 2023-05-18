package com.conduent.tpms.iag.utility;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
@Component
public class IAGValidationUtils {

	private List<String> devicePrefixList;
	
	/**
	 * Returns String with custom padding format
	 * @param pattern
	 * @param value
	 * @return
	 */
	public String customFormatString(String pattern, long value) {
		String output = null;
		try {
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			output = myFormatter.format(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
		public boolean validatePlateState(String str) {
			String regex = "[A-Z]{2}";
			Pattern p = Pattern.compile(regex);
			if (str == null) {
				return false;
			} else {
				Matcher m = p.matcher(str);
				return m.matches();
			}
		}

		
		public boolean validatePlateType(String PlateType) {
			
			String regex = "[0-99]+";
			Pattern p = Pattern.compile(regex);
			if (PlateType == null) {
				return false;
			} else {
				Matcher m = p.matcher(PlateType);
				return m.matches();
			}
		}
		
		public boolean validatePlateNumber(String PlateNo) {
			String regex = "[A-Z0-9A-Z*]{0,10}";
			Pattern p = Pattern.compile(regex);
			if (PlateNo == null) {
				return false;
			} else {
				Matcher m = p.matcher(PlateNo);
				//return m.matches();
				//as asked by PB and Ronnie to exclude regex
				return true;
			}
		}
		
		public LocalDateTime getFormattedDateTime(String fileDateTime) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			return LocalDateTime.parse(fileDateTime, formatter);
		}
		
		public boolean validateLastName(String LastName) {
			String regex = "[a-zA-Z \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$";
			Pattern p = Pattern.compile(regex);
			if (LastName == null) {
				return false;
			} else {
				Matcher m = p.matcher(LastName);
				return m.matches();
			}
		}
		
		public boolean validateFirstName(String FirstName) {
			String regex = "[a-zA-Z \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$";
			Pattern p = Pattern.compile(regex);
			if (FirstName == null) {
				return true;
			} else {
				Matcher m = p.matcher(FirstName);
				return m.matches();
			}
		}
		
		public boolean validateMiddleInitial(String MiddleInitial) {
			String regex = "[a-zA-Z \\\\s]{0,1}";
			Pattern p = Pattern.compile(regex);
			if (MiddleInitial == null) {
				return true;
			} else {
				Matcher m = p.matcher(MiddleInitial);
				return m.matches();
			}
		}
		
		
		public boolean validateCompanyName(String CompanyName) {
			String regex = "[a-zA-Z \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$";
			Pattern p = Pattern.compile(regex);
			if (CompanyName == null) {
				return false;
			} else {
				Matcher m = p.matcher(CompanyName);
				return m.matches();
			}
		}
		public boolean validateAddress1(String Addr1) {
			String regex = "[a-zA-Z0-9 \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$";
			Pattern p = Pattern.compile(regex);
			if (Addr1 == null) {
				return true;
			} else {
				Matcher m = p.matcher(Addr1);
				return m.matches();
			}
		}
		
		public boolean validateAddress2(String Addr2) {
			String regex = "[a-zA-Z0-9 \\\\s $&+,:;=?@#|'/\\\\<>.^*()%!`-]+$";
			Pattern p = Pattern.compile(regex);
			if (Addr2 == null) {
				return true;
			} else {
				Matcher m = p.matcher(Addr2);
				return m.matches();
			}
		}
		
		public boolean validateCity(String city) {
			String regex = "[a-zA-Z \\\\s]+$";
			Pattern p = Pattern.compile(regex);
			if (city == null) {
				return false;
			} else {
				Matcher m = p.matcher(city);
				return m.matches();
			}
		}
		
		public boolean validateState(String state) {
			String regex = "[A-Za-z]{2}";
			Pattern p = Pattern.compile(regex);
			if (state == null) {
				return false;
			} else {
				Matcher m = p.matcher(state);
				return m.matches();
			}
		}
		
		public boolean validateZipcode(String zipcode) {
			String regex = "[0-9]{5,10}";
			Pattern p = Pattern.compile(regex);
			if (zipcode == null) {
				return false;
			} else {
				Matcher m = p.matcher(zipcode);
				return m.matches();
			}
		}
}
