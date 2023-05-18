package com.conduent.tpms.qatp.utility;

public class Convertor {
	public static Long toLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception ex) {
		}
		return null;
	}

	public static Integer toInteger(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception ex) {
		}
		return null;
	}
	public static Integer toInteger(long str)
	{
		try
		{
			return (int)str;  
		}catch(Exception ex){ 
			//ex.printStackTrace();
		}
		return null;
	}
	public static Double toDouble(String str) {
		try {
			return Double.valueOf(str);
		} catch (Exception ex) {
		}
		return null;
	}
		
	public static boolean isEmpty(final String value)
	{
		return value == null || value.isEmpty();
	}
	
}
