package com.conduent.Ibtsprocessing.dao;

import java.util.List;

import com.conduent.Ibtsprocessing.model.OaAddress;

public interface IOaAddressDao {
	
	public List<OaAddress> getAddress(String deviceNo);

}
