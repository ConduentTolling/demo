package com.conduent.parking.posting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IAgencyDao;
import com.conduent.parking.posting.model.Agency;
import com.conduent.parking.posting.service.IAgencyService;

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
