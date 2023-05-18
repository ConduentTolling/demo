package com.conduent.tpms.reconciliation.model;

/**
 * Away Device Model
 * 
 * @author deepeshb
 *
 */
public class DeviceAway {

	private Long iagTagStatus;

	public Long getIagTagStatus() {
		return iagTagStatus;
	}

	public void setIagTagStatus(Long iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}

	public Boolean isDeviceAway() {
		if(iagTagStatus!=null)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "DeviceAway [iagTagStatus=" + iagTagStatus + "]";
	}
	
	
}
