package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IOaAddressDao;
import com.conduent.Ibtsprocessing.model.OaAddress;
import com.conduent.Ibtsprocessing.service.IOaAddressService;

@Service
public class OaAddressService implements IOaAddressService{

	
	@Autowired
	private IOaAddressDao addressDao;
	
	@Override
	public List<OaAddress> getAddress(String deviceNo) {
		
		return addressDao.getAddress(deviceNo);
	}


}
