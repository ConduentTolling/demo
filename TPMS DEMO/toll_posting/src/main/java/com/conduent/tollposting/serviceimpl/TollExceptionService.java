package com.conduent.tollposting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dao.ITollExceptionDao;
import com.conduent.tollposting.service.ITollExceptionService;

@Service
public class TollExceptionService implements ITollExceptionService{

	@Autowired
	private ITollExceptionDao tollExceptionDao;
	
	@Override
	public void saveTollException(TollException tollException) {
		tollExceptionDao.saveTollException(tollException);
	}

}
