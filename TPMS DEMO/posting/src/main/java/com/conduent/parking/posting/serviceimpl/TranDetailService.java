package com.conduent.parking.posting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.ITransDetailDao;
import com.conduent.parking.posting.model.TranDetail;
import com.conduent.parking.posting.service.ITranDetailService;

@Service
public class TranDetailService implements ITranDetailService
{
	@Autowired
	private ITransDetailDao transDetailDao;
	
	@Override
	public void updateTranDetail(TranDetail tranDetail) throws Exception
	{
		transDetailDao.updateTranDetail(tranDetail);
	}
}
