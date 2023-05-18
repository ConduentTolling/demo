package com.conduent.parking.posting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IProcessParameterDao;
import com.conduent.parking.posting.model.ProcessParameters;
import com.conduent.parking.posting.service.IProcessParameterService;

@Service
public class ProcessParameterService implements IProcessParameterService 
{
	@Autowired
	private IProcessParameterDao processParameterDao;

	@Override
	public List<ProcessParameters> getProcessParameters()
	{
		return processParameterDao.getProcessParameters();
	}

}
