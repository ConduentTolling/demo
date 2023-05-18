package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IProcessParameterDao;
import com.conduent.Ibtsprocessing.model.ProcessParameters;
import com.conduent.Ibtsprocessing.service.IProcessParameterService;

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
