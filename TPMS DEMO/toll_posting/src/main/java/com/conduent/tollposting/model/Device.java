package com.conduent.tollposting.model;

import java.time.LocalDateTime;

import com.conduent.tollposting.constant.DeviceStatus;
import com.conduent.tollposting.constant.TxStatus;

public class Device
{
	private Long etcAccountId;
	private Long deviceStatus;
	private LocalDateTime statusTimestamp;
	private String deviceNumber;
	private Integer iagTagStatus;
	
	public Long getEtcAccountId() 
	{
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) 
	{
		this.etcAccountId = etcAccountId;
	}
	public Long getDeviceStatus() 
	{
		return deviceStatus;
	}
	public void setDeviceStatus(Long deviceStatus) 
	{
		this.deviceStatus = deviceStatus;
	}
	public LocalDateTime getStatusTimestamp() {
		return statusTimestamp;
	}
	public void setStatusTimestamp(LocalDateTime statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	
	public Integer getIagTagStatus() {
		return iagTagStatus;
	}
	public void setIagTagStatus(Integer iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}
	
	public TxStatus validateDeviceStatus(LocalDateTime txTimeStamp)
	{
		if(deviceStatus.intValue()!=DeviceStatus.ACTIVE.getCode() && deviceStatus.intValue()!=DeviceStatus.FOUND.getCode() )
		{

			if(deviceStatus.intValue()==DeviceStatus.PENDING.getCode() || deviceStatus.intValue()==DeviceStatus.CLOSE_PEND.getCode() )
			{
				return TxStatus.TX_STATUS_INVTAG;
			}
			else if(deviceStatus.intValue()==DeviceStatus.INVENTORY.getCode())
			{
				return TxStatus.TX_STATUS_TAGINV;
			}
			else if(deviceStatus.intValue()==DeviceStatus.LOST.getCode())
			{
				if(txTimeStamp.isBefore(statusTimestamp))
				{
					return null;
				}
				return TxStatus.TX_STATUS_TAGLOST;
			}
			else if(deviceStatus.intValue()==DeviceStatus.STOLEN.getCode())
			{
				if(txTimeStamp.isBefore(statusTimestamp))
				{
					return null;
				}
				return TxStatus.TX_STATUS_TAGSTOLEN;
			}
			else if(deviceStatus.intValue()==DeviceStatus.RETURNED.getCode() || deviceStatus.intValue()==DeviceStatus.SHIPVEND.getCode() || deviceStatus.intValue()==DeviceStatus.RETURNDEF.getCode())
			{
				return TxStatus.TX_STATUS_TAGRETURNED;
			}
			else if(deviceStatus.intValue()==DeviceStatus.DAMAGED.getCode())
			{
				return TxStatus.TX_STATUS_TAGDAMAGED;
			}
			else if(deviceStatus.intValue()==DeviceStatus.RVKF.getCode())
			{
				return TxStatus.TX_STATUS_INVACRVKF;
			}
			else
			{
				return TxStatus.TX_STATUS_INVTAG;
			}
		}else
		{
			return  null;
		} 
	}
	@Override
	public String toString() {
		return "Device [etcAccountId=" + etcAccountId + ", deviceStatus=" + deviceStatus + ", statusTimestamp="
				+ statusTimestamp + ", deviceNumber=" + deviceNumber + ", iagTagStatus=" + iagTagStatus + "]";
	}
}