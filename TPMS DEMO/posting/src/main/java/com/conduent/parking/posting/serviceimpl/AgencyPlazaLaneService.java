package com.conduent.parking.posting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IAgencyPlazaLaneDao;
import com.conduent.parking.posting.model.AgencyPlazaLane;
import com.conduent.parking.posting.service.IAgencyPlazaLaneService;

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
