package com.conduent.tollposting.service;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tollposting.model.TollSchedule;

public interface ITollScheduleService
{
	public List<TollSchedule> getTollSchedule(LocalDate txDate,Integer plazaId,Integer entryPlazaId,Integer vehicleClass);
	public List<TollSchedule> getTollAndPriceSchedule(LocalDate txDate,Integer agencyId,Integer entryPlazaId,Integer plazaId,Integer vehicleClass);
}
