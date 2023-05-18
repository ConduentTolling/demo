package com.conduent.tollposting.service;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tollposting.model.AgencyHoliday;

public interface IAgencyHolidayService 
{
	List<AgencyHoliday> getAgencyHoliday();
	AgencyHoliday getAgencyHoliday(Integer agencyId,LocalDate txDate);
}
