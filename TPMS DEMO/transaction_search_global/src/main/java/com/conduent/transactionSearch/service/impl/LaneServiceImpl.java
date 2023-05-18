package com.conduent.transactionSearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.transactionSearch.dao.LaneDao;
import com.conduent.transactionSearch.model.Lane;
import com.conduent.transactionSearch.service.LaneService;

@Service
public class LaneServiceImpl implements LaneService 
{
	@Autowired
	private LaneDao laneDao;

	@Override
	public List<Lane> getLane() 
	{
		return laneDao.getLane();
	}

}
