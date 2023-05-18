package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IPlazaDao;
import com.conduent.Ibtsprocessing.model.Plaza;
import com.conduent.Ibtsprocessing.service.IPlazaService;

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
