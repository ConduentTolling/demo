package com.conduent.parking.posting.dao;

import com.conduent.parking.posting.serviceimpl.TollException;

public interface ITollExceptionDao {
	public void saveTollException(TollException tollException);
}
