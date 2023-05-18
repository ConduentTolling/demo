package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ITollScheduleDao;
import com.conduent.tollposting.model.TollSchedule;
import com.conduent.tollposting.service.ITollScheduleService;

@Service
public class TollScheduleService implements ITollScheduleService{

	@Autowired
	private ITollScheduleDao tollScheduleDao;
	
	@Override
	public List<TollSchedule> getTollSchedule(LocalDate txDate,Integer plazaId,Integer entryPlazaId,Integer vehicleClass) 
	{
		return tollScheduleDao.getTollSchedule(txDate, plazaId, entryPlazaId, vehicleClass);
	}

	@Override
	public List<TollSchedule> getTollAndPriceSchedule(LocalDate txDate,Integer agencyId,Integer entryPlazaId,Integer plazaId,Integer vehicleClass) 
	{
		return tollScheduleDao.getTollAndPriceSchedule(txDate,agencyId,entryPlazaId,plazaId,vehicleClass);
	}
}
