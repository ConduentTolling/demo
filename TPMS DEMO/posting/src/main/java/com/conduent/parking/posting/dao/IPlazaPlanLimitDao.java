package com.conduent.parking.posting.dao;

import com.conduent.parking.posting.model.PlazaPlanLimit;

public interface IPlazaPlanLimitDao 
{
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId,Integer exitPlazaId,String apdId);
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId);
}
