package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IEtcAccountDao;
import com.conduent.Ibtsprocessing.model.EtcAccount;
import com.conduent.Ibtsprocessing.service.IEtcAccount;

@Service
public class EtcAccountImpl implements IEtcAccount{

	@Autowired
	IEtcAccountDao etcAccountDao;
	
	@Override
	public List<EtcAccount> getEtcAccount() {
		return etcAccountDao.getEtcAccount();
	}

}
