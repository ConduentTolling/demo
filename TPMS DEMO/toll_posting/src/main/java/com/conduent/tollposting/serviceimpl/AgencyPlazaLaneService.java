package com.conduent.tollposting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dao.IAgencyPlazaLaneDao;
import com.conduent.tollposting.model.AgencyPlazaLane;
import com.conduent.tollposting.service.IAgencyPlazaLaneService;

@Service
public class AgencyPlazaLaneService implements IAgencyPlazaLaneService
{
	@Autowired
	private IAgencyPlazaLaneDao agencyPlazaLaneDao;

	@Override
	public List<AgencyPlazaLane> getAgencyPlazaLane() {
		return agencyPlazaLaneDao.getAgencyPlazaLane();
	}

}
