package com.conduent.parking.posting.utility;

import java.time.LocalDateTime;
import java.util.Random;

import com.conduent.parking.posting.dto.AccountTollPostDTO;
import com.conduent.parking.posting.dto.RevenueDateStatisticsDto;

public class Util 
{
	public static int getRandomNumber(int n)
	{
		int m = (int) Math.pow(10, n - 1);
		return m+new Random().nextInt(9 * m);	    
	}
	public static boolean isValidDevice(String deviceNo)
	{
		if(deviceNo==null || deviceNo.contains("*") || deviceNo.equals("00000000000") || deviceNo.trim().isEmpty())
		{
			return false;
		}
		return true;
	}
	public static boolean isValidPlateNumber(String plateNumber)
	{
		if(plateNumber==null || plateNumber.contains("*") || plateNumber.trim().isEmpty())
		{
			return false;
		}
		return true;
	}
	public static boolean isValidPlateState(String plateState)
	{
		if(plateState==null || plateState.contains("*") || plateState.trim().isEmpty())
		{
			return false;
		}
		return true;
	}

	public static void setRevenueDate(AccountTollPostDTO tollPost,MasterCache masterCache)
	{
		try
		{
			RevenueDateStatisticsDto tempRevenueDateStatisticsDto = masterCache.getRevenueStatusByPlaza(tollPost.getPlazaId());
			LocalDateTime postedDateTime= tollPost.getTxTimestamp().toLocalDateTime();
			tollPost.setRevenueDate(tollPost.getTxDate());
			int plazaRevenueTimeSec = tempRevenueDateStatisticsDto.getRevenueSecondOfDay();
			if (0 == plazaRevenueTimeSec) 
			{
				tollPost.setRevenueDate(tollPost.getTxDate());
			} 
			else if (plazaRevenueTimeSec > 43200) //12 hours
			{
				if (postedDateTime.toLocalTime().toSecondOfDay() > plazaRevenueTimeSec)
				{
					tollPost.setRevenueDate(postedDateTime.plusDays(1).toLocalDate());
				} 
				else 
				{
					tollPost.setRevenueDate(postedDateTime.toLocalDate());
				}
			} 
			else if (plazaRevenueTimeSec < 43200) 
			{
				if (postedDateTime.toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) 
				{
					tollPost.setRevenueDate(postedDateTime.toLocalDate());
				}
				else 
				{
					tollPost.setRevenueDate(postedDateTime.minusDays(1).toLocalDate());
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
