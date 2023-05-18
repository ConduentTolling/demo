package com.conduent.tollposting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IPlazaDao;
import com.conduent.tollposting.model.Plaza;
import com.conduent.tollposting.service.IPlazaService;

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
