package com.conduent.parking.posting.service;

import com.conduent.parking.posting.serviceimpl.TollException;

public interface ITollExceptionService {
	public void saveTollException(TollException tollException);
}
