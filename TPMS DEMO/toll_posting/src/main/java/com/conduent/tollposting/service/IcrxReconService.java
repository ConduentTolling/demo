package com.conduent.tollposting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dao.IIcrxReconDao;
import com.conduent.tollposting.model.IcrxRecon;

@Service
public class IcrxReconService implements IIcrxReconService{

	@Autowired
	private IIcrxReconDao icrxReconDao;
	
	@Override
	public void saveIcrxRecon(IcrxRecon icrxRecon) {
		icrxReconDao.saveIcrxRecon(icrxRecon);
	}

}
