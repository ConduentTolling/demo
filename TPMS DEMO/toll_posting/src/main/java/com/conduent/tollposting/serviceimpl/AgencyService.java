package com.conduent.tollposting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IAgencyDao;
import com.conduent.tollposting.model.Agency;
import com.conduent.tollposting.service.IAgencyService;

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
