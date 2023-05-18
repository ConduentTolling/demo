package com.conduent.parking.posting.dao;

import java.util.List;

import com.conduent.parking.posting.model.TollPostLimit;

public interface ITollPostLimitDao 
{
	public List<TollPostLimit> getTollPostLimit();
}
