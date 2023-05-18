package com.conduent.tpms.qatp.model;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * Speed status response dto
 * 
 * @author Urvashi C
 *
 */
public class VSpeedStatusDto implements Serializable{
	
	private static final long serialVersionUID = 6951504455807291845L;
	
	private int agencyId;
	private long etcAccountId;
	private String deviceNo;
	private String speedStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	private int suspendDays;
	private LocalDate warningMailDate;
	private String isFast;
	private int lastCitationLevel;
	private LocalDate speedStatusDate;
	private int suspensionCount;
	private int appealCount;
	private String speedStatusId;
	
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getSpeedStatus() {
		return speedStatus;
	}
	public void setSpeedStatus(String speedStatus) {
		this.speedStatus = speedStatus;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getSuspendDays() {
		return suspendDays;
	}
	public void setSuspendDays(int suspendDays) {
		this.suspendDays = suspendDays;
	}
	public LocalDate getWarningMailDate() {
		return warningMailDate;
	}
	public void setWarningMailDate(LocalDate warningMailDate) {
		this.warningMailDate = warningMailDate;
	}
	public String getIsFast() {
		return isFast;
	}
	public void setIsFast(String isFast) {
		this.isFast = isFast;
	}
	public int getLastCitationLevel() {
		return lastCitationLevel;
	}
	public void setLastCitationLevel(int lastCitationLevel) {
		this.lastCitationLevel = lastCitationLevel;
	}
	public LocalDate getSpeedStatusDate() {
		return speedStatusDate;
	}
	public void setSpeedStatusDate(LocalDate speedStatusDate) {
		this.speedStatusDate = speedStatusDate;
	}
	public int getSuspensionCount() {
		return suspensionCount;
	}
	public void setSuspensionCount(int suspensionCount) {
		this.suspensionCount = suspensionCount;
	}
	public int getAppealCount() {
		return appealCount;
	}
	public void setAppealCount(int appealCount) {
		this.appealCount = appealCount;
	}
	public String getSpeedStatusId() {
		return speedStatusId;
	}
	public void setSpeedStatusId(String speedStatusId) {
		this.speedStatusId = speedStatusId;
	}

}
