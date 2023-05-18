package com.conduent.parking.posting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.ILaneDao;
import com.conduent.parking.posting.model.Lane;
import com.conduent.parking.posting.service.ILaneService;

@Service
public class LaneService implements ILaneService 
{
	@Autowired
	private ILaneDao laneDao;

	@Override
	public List<Lane> getLane() 
	{
		return laneDao.getLane();
	}

}
