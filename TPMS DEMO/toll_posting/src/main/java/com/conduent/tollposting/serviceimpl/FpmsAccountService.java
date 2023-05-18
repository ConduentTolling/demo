package com.conduent.tollposting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IFpmsAccountDao;
import com.conduent.tollposting.model.FpmsAccount;
import com.conduent.tollposting.service.IFpmsAccountService;

@Service
public class FpmsAccountService implements IFpmsAccountService {

	@Autowired
	private IFpmsAccountDao fpmsAccountDao;
	
	@Override
	public FpmsAccount getFpmsAccount(Long etcAccountId) {
		return fpmsAccountDao.getFpmsAccount(etcAccountId);
	}

}
