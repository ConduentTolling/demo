package com.conduent.parking.posting.dao;

import java.time.LocalDateTime;

import com.conduent.parking.posting.model.Device;

public interface IDeviceDao 
{
	public Device getDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp);
	public Device getHDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp);
	public Device getDeviceByEtcAccountId(String etcAccountId,LocalDateTime txTimeStamp);
	public Device getHADeviceByDeviceNo(String deviceNo);

}
