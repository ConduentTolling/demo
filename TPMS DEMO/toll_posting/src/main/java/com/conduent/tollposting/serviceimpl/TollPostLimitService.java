package com.conduent.tollposting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ITollPostLimitDao;
import com.conduent.tollposting.model.TollPostLimit;
import com.conduent.tollposting.service.ITollPostLimitService;

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
