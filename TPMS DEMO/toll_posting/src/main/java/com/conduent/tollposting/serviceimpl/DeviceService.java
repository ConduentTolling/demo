package com.conduent.tollposting.serviceimpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dao.IDeviceDao;
import com.conduent.tollposting.model.Device;
import com.conduent.tollposting.service.IDeviceService;

@Service
public class DeviceService implements IDeviceService 
{

	@Autowired
	private IDeviceDao deviceDao;
	
	@Override
	public Device getDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp) 
	{
		return deviceDao.getDeviceByDeviceNo(deviceNo,txTimeStamp);
	}

	@Override
	public Device getDeviceByEtcAccountId(String etcAccountId,LocalDateTime txTimeStamp) 
	{
		return deviceDao.getDeviceByEtcAccountId(etcAccountId,txTimeStamp);
	}

	@Override
	public Device getHDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp) {
		return deviceDao.getHDeviceByDeviceNo(deviceNo,txTimeStamp);
	}

	@Override
	public Device getHADeviceByDeviceNo(String deviceNo) {
		return deviceDao.getHADeviceByDeviceNo(deviceNo);
	}

	
	
}