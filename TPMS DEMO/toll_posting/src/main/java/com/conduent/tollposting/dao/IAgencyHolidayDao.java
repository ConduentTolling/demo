package com.conduent.tollposting.dao;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tollposting.model.AgencyHoliday;

public interface IAgencyHolidayDao 
{
	List<AgencyHoliday> getAgencyHoliday();
	AgencyHoliday getAgencyHoliday(Integer agencyId,LocalDate txDate);
}
