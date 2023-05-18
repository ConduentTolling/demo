package com.conduent.tpms.reconciliation.model;

/**
 * Speed Threshold Model
 * 
 * @author deepeshb
 *
 */
public class SpeedThreshold {

	private Integer lowerSpeedThreshold;

	public Integer getLowerSpeedThreshold() {
		return lowerSpeedThreshold;
	}

	public void setLowerSpeedThreshold(Integer lowerSpeedThreshold) {
		this.lowerSpeedThreshold = lowerSpeedThreshold;
	}

	@Override
	public String toString() {
		return "SpeedThreshold [lowerSpeedThreshold=" + lowerSpeedThreshold + "]";
	}
	
	
}
