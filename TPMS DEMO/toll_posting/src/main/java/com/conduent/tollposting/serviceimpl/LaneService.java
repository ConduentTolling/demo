package com.conduent.tollposting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ILaneDao;
import com.conduent.tollposting.model.Lane;
import com.conduent.tollposting.service.ILaneService;

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
