package com.conduent.tpms.parking.utility;

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
}
