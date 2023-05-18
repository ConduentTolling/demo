package com.conduent.parking.posting.service;

import com.conduent.parking.posting.model.PlazaPlanLimit;

public interface IPlazaPlanLimitService 
{
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId,Integer exitPlazaId,String apdId);
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId);
}
