package com.conduent.parking.posting.dao;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import com.conduent.parking.posting.model.TollSchedule;

public interface ITollScheduleDao 
{
	public List<TollSchedule> getTollSchedule(LocalDate txDate,Integer plazaId,Integer entryPlazaId,Integer vehicleClass);
	public List<TollSchedule> getTollAndPriceSchedule(LocalDate txDate,Integer agencyId,Integer entryPlazaId,Integer plazaId,Integer vehicleClass);
	public Long getTollSchedPriceObj(LocalDate txDate, Integer plazaId, Integer entryPlazaId,
			OffsetDateTime txDateTime, String inDaysInd);
}
