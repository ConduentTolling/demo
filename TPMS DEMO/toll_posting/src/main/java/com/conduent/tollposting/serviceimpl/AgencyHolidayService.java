package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IAgencyHolidayDao;
import com.conduent.tollposting.model.AgencyHoliday;
import com.conduent.tollposting.service.IAgencyHolidayService;

@Service
public class AgencyHolidayService implements IAgencyHolidayService 
{
	@Autowired
	private IAgencyHolidayDao agencyHolidayDao;

	@Override
	public List<AgencyHoliday> getAgencyHoliday() 
	{
		return agencyHolidayDao.getAgencyHoliday();
	}

	@Override
	public AgencyHoliday getAgencyHoliday(Integer agencyId, LocalDate txDate) {
		return agencyHolidayDao.getAgencyHoliday(agencyId, txDate);
	}
}
