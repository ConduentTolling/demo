package com.conduent.tollposting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IProcessParameterDao;
import com.conduent.tollposting.model.ProcessParameters;
import com.conduent.tollposting.service.IProcessParameterService;

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

	@Override
	public List<ProcessParameters> getProcessParametersICTX()
	{
		return processParameterDao.getProcessParametersICTX();
	}

	@Override
	public List<ProcessParameters> getMTARejAccount() {
		return processParameterDao.getMTARejAccount();
	}

	@Override
	public ProcessParameters getTollAmountValue() {
		return processParameterDao.getTollAmountValue();
	}

}
