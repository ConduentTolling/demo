package com.conduent.tollposting.dao;

import com.conduent.tollposting.serviceimpl.TollException;

public interface ITollExceptionDao {
	public void saveTollException(TollException tollException);
}
