package com.conduent.parking.posting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IFpmsAccountDao;
import com.conduent.parking.posting.model.FpmsAccount;
import com.conduent.parking.posting.service.IFpmsAccountService;

@Service
public class FpmsAccountService implements IFpmsAccountService {

	@Autowired
	private IFpmsAccountDao fpmsAccountDao;
	
	@Override
	public FpmsAccount getFpmsAccount(Long etcAccountId) {
		return fpmsAccountDao.getFpmsAccount(etcAccountId);
	}

}
