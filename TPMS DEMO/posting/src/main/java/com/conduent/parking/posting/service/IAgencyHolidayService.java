package com.conduent.parking.posting.service;

import java.time.LocalDate;
import java.util.List;

import com.conduent.parking.posting.model.AgencyHoliday;

public interface IAgencyHolidayService 
{
	List<AgencyHoliday> getAgencyHoliday();
	AgencyHoliday getAgencyHoliday(Integer agencyId,LocalDate txDate);
}
