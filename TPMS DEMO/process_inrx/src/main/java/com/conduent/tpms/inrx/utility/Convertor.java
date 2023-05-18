package com.conduent.tpms.inrx.utility;

public class Convertor 
{
	public static Long toLong(String str)
	{
		try
		{
			return Long.valueOf(str);
		}catch(Exception ex){ 	}
		return null;
	}
	
	public static Integer toInteger(String str)
	{
		try
		{
			return Integer.valueOf(str);
		}catch(Exception ex){ 	}
		return null;
	}
	
	public static boolean isEmpty(final String value)
	{
		return value == null || value.isEmpty();
	}
}
