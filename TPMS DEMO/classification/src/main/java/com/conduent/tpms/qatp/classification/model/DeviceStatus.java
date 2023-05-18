package com.conduent.tpms.qatp.classification.model;

/**
 * Device Status Model
 * 
 * @author deepeshb
 *
 */
public class DeviceStatus {

	private Long etcAccountId;

	private Integer deviceStatus;

	private String isPrevalidated;

	private Integer retailTagStatus;

	private String deviceInternalNumber;

	private Long iagDeviceStatus;

	public DeviceStatus() {
		super();
	}

	public DeviceStatus(Long etcAccountId, Integer deviceStatus, String isPrevalidated, Integer retailTagStatus,
			String deviceInternalNumber) {
		super();
		this.etcAccountId = etcAccountId;
		this.deviceStatus = deviceStatus;
		this.isPrevalidated = isPrevalidated;
		this.retailTagStatus = retailTagStatus;
		this.deviceInternalNumber = deviceInternalNumber;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getIsPrevalidated() {
		return isPrevalidated;
	}

	public void setIsPrevalidated(String isPrevalidated) {
		this.isPrevalidated = isPrevalidated;
	}

	public Integer getRetailTagStatus() {
		return retailTagStatus;
	}

	public void setRetailTagStatus(Integer retailTagStatus) {
		this.retailTagStatus = retailTagStatus;
	}

	public String getDeviceInternalNumber() {
		return deviceInternalNumber;
	}

	public void setDeviceInternalNumber(String deviceInternalNumber) {
		this.deviceInternalNumber = deviceInternalNumber;
	}

	public Long getIagDeviceStatus() {
		return iagDeviceStatus;
	}

	public void setIagDeviceStatus(Long iagDeviceStatus) {
		this.iagDeviceStatus = iagDeviceStatus;
	}

	@Override
	public String toString() {
		return "DeviceStatus [etcAccountId=" + etcAccountId + ", deviceStatus=" + deviceStatus + ", isPrevalidated="
				+ isPrevalidated + ", retailTagStatus=" + retailTagStatus + ", deviceInternalNumber="
				+ deviceInternalNumber + ", iagDeviceStatus=" + iagDeviceStatus + "]";
	}

}
