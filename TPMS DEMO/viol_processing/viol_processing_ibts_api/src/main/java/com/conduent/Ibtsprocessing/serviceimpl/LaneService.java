package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.ILaneDao;
import com.conduent.Ibtsprocessing.model.Lane;
import com.conduent.Ibtsprocessing.service.ILaneService;

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
