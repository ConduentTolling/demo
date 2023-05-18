package com.conduent.transactionSearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.transactionSearch.dao.TranDetailDao;
import com.conduent.transactionSearch.model.TranDetailClass;
import com.conduent.transactionSearch.service.TranDetailService;

@Service
public class TranDetailServiceImpl implements TranDetailService
{
	@Autowired
	private TranDetailDao tranDetailDao;
	
	@Override
	public void updateTranDetail(TranDetailClass tranDetail)
	{
	//	tranDetailDao.updateTranDetail(tranDetail);
	}
}
