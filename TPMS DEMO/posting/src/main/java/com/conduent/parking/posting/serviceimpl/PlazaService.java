package com.conduent.parking.posting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IPlazaDao;
import com.conduent.parking.posting.model.Plaza;
import com.conduent.parking.posting.service.IPlazaService;

@Service
public class PlazaService implements IPlazaService{

	@Autowired
	private IPlazaDao plazaDao;
	
	@Override
	public List<Plaza> getPlaza() 
	{
		return plazaDao.getPlaza();
	}
}
