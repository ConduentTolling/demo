package com.conduent.parking.posting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.ITollPostLimitDao;
import com.conduent.parking.posting.model.TollPostLimit;
import com.conduent.parking.posting.service.ITollPostLimitService;

@Service
public class TollPostLimitService implements ITollPostLimitService
{
	@Autowired
	private ITollPostLimitDao tollPostLimitDao;

	@Override
	public List<TollPostLimit> getTollPostLimit() 
	{
		return tollPostLimitDao.getTollPostLimit();
	}

}
