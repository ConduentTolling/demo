package com.conduent.parking.posting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IPlazaPlanLimitDao;
import com.conduent.parking.posting.model.PlazaPlanLimit;
import com.conduent.parking.posting.service.IPlazaPlanLimitService;

@Service
public class PlazaPlanLimitService implements IPlazaPlanLimitService {

	@Autowired
	private IPlazaPlanLimitDao plazaPlanLimitDao; 
	
	@Override
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId) {
		// TODO Auto-generated method stub
		return plazaPlanLimitDao.getPlazaPlanLimit(entryPlazaId, exitPlazaId, apdId);
	}

	@Override
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId) {
		return plazaPlanLimitDao.getPlazaPlazaPlanLimit(entryPlazaId, exitPlazaId, apdId);
	}
}