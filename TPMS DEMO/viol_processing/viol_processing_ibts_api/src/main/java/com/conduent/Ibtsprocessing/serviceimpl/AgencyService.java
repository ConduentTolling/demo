package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IAgencyDao;
import com.conduent.Ibtsprocessing.model.Agency;
import com.conduent.Ibtsprocessing.service.IAgencyService;

@Service
public class AgencyService implements IAgencyService 
{
	@Autowired
	private IAgencyDao agencyDao;

	@Override
	public List<Agency> getAgency() 
	{
		return agencyDao.getAgency();
	}

}
