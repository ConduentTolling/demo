package com.conduent.tollposting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ITransDetailDao;
import com.conduent.tollposting.model.TranDetail;
import com.conduent.tollposting.service.ITranDetailService;

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
