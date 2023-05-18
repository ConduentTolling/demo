package com.conduent.tpms.qatp.classification.model;

/**
 * Away Device Model
 * 
 * @author deepeshb
 *
 */
public class DeviceAway {

	private Long iagTagStatus;
	private String deviceNumber;
	private Integer tagHomeAgency;

	public Long getIagTagStatus() {
		return iagTagStatus;
	}

	public void setIagTagStatus(Long iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}
	

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Boolean isDeviceAway() {
		if(iagTagStatus!=null)
			return true;
		return false;
	}
	public Integer getTagHomeAgency() {
		return tagHomeAgency;
	}

	public void setTagHomeAgency(Integer tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
	}

	@Override
	public String toString() {
		return "DeviceAway [iagTagStatus=" + iagTagStatus + ", deviceNumber=" + deviceNumber + ", tagHomeAgency="
				+ tagHomeAgency + "]";
	}

	
	
	
	
	
}
