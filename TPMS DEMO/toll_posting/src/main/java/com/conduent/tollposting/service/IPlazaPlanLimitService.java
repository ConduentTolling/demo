package com.conduent.tollposting.service;

import com.conduent.tollposting.model.PlazaPlanLimit;

public interface IPlazaPlanLimitService 
{
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId,Integer exitPlazaId,String apdId);
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId);
}
