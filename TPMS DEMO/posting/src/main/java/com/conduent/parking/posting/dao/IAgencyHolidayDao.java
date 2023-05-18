package com.conduent.parking.posting.dao;

import java.time.LocalDate;
import java.util.List;

import com.conduent.parking.posting.model.AgencyHoliday;

public interface IAgencyHolidayDao 
{
	List<AgencyHoliday> getAgencyHoliday();
	AgencyHoliday getAgencyHoliday(Integer agencyId,LocalDate txDate);
}
