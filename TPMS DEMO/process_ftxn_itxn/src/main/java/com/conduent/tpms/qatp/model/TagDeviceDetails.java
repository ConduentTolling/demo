package com.conduent.tpms.qatp.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TagDeviceDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate StartDate2;
	private String deviceNo1;

	@Override
	public String toString() {
		return "TagDeviceDetails [StartDate2=" + StartDate2 + ", deviceNo1=" + deviceNo1 + "]";
	}

	public String getDeviceNo1() {
		return deviceNo1;
	}

	public void setDeviceNo1(String deviceNo1) {
		this.deviceNo1 = deviceNo1;
	}

	public LocalDate getStartDate2() {
		return StartDate2;
	}

	public void setStartDate2(LocalDate startDate2) {
		StartDate2 = startDate2;
	}

}
