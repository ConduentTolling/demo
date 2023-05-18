package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IOaDevicesDao;
import com.conduent.Ibtsprocessing.model.OADevices;
import com.conduent.Ibtsprocessing.service.IOaDevices;

@Service
public class OaDevices implements IOaDevices {

	@Autowired
	private IOaDevicesDao OaDeviceDao;

	@Override
	public List<OADevices> getOaDevices() {
		return OaDeviceDao.getOaDevices();
	}

}
