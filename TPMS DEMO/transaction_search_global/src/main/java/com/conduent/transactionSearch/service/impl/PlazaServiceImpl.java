package com.conduent.transactionSearch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.transactionSearch.dao.PlazaDao;
import com.conduent.transactionSearch.model.Plaza;
import com.conduent.transactionSearch.service.PlazaService;

@Service
public class PlazaServiceImpl implements PlazaService{

	@Autowired
	private PlazaDao plazaDao;
	
	@Override
	public List<Plaza> getPlaza() 
	{
		return plazaDao.getPlaza();
	}
}
