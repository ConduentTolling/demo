package com.conduent.parking.posting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.ITollExceptionDao;
import com.conduent.parking.posting.service.ITollExceptionService;

@Service
public class TollExceptionService implements ITollExceptionService{

	@Autowired
	private ITollExceptionDao tollExceptionDao;
	
	@Override
	public void saveTollException(TollException tollException) {
		tollExceptionDao.saveTollException(tollException);
	}

}
