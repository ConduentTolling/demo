package com.conduent.Ibtsprocessing.service;

import java.util.List;

import com.conduent.Ibtsprocessing.model.OaAddress;

public interface IOaAddressService {
	
	public List<OaAddress> getAddress(String deviceNo);

}
