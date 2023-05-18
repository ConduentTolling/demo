package com.conduent.tpms.process25a.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import com.conduent.tpms.process25a.dto.AccountTollPostDTO;
import com.conduent.tpms.process25a.dto.RevenueDateStatisticsDto;

public class Util {
	
	public static int getRandomNumber(int n) {
		int m = (int) Math.pow(10, n - 1);
		return m + new Random().nextInt(9 * m);
	}

	public static boolean isValidDevice(String deviceNo) {
		if (deviceNo == null || deviceNo.contains("*") || deviceNo.equals("00000000000") || deviceNo.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isValidPlateNumber(String plateNumber) {
		if (plateNumber == null || plateNumber.contains("*") || plateNumber.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isValidPlateState(String plateState) {
		if (plateState == null || plateState.contains("*") || plateState.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static LocalDate setRevenueDate(int plazaId,LocalDate postedDate,  MasterDataCache masterCache) {
		try {
			RevenueDateStatisticsDto tempRevenueDateStatisticsDto = masterCache.getRevenueStatusByPlaza(plazaId);
			LocalDateTime postedDateTime = LocalDateTime.now();
			int plazaRevenueTimeSec = tempRevenueDateStatisticsDto.getRevenueSecondOfDay();
			if (0 == plazaRevenueTimeSec) {
				return postedDate;
			} else if (plazaRevenueTimeSec > 43200) {
				if (postedDateTime.toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
					return postedDateTime.plusDays(1).toLocalDate();
				} else {
					return postedDate;
				}
			} else if (plazaRevenueTimeSec < 43200) {
				if (postedDateTime.toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
					return postedDateTime.toLocalDate();
				} else {
					return postedDateTime.minusDays(1).toLocalDate();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return postedDate;
	}

}