package com.conduent.tollposting.dao;

import com.conduent.tollposting.model.PlazaPlanLimit;

public interface IPlazaPlanLimitDao 
{
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId,Integer exitPlazaId,String apdId);
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId);
}
