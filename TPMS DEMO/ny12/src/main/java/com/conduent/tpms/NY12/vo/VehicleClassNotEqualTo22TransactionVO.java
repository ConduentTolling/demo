package com.conduent.tpms.NY12.vo;

import java.sql.Timestamp;

public class VehicleClassNotEqualTo22TransactionVO {
	
	private String laneTxId;
	private String entryPlazaId; 
	private String plazaId;
	private String entryLaneId;
	private Timestamp entryTimeStamp;
	private double discountedAmount;
	private double collectedAmount;
	private double unrealizedAmount;
	private String actualClass;
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(String entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(Timestamp entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public String getActualClass() {
		return actualClass;
	}
	public void setActualClass(String actualClass) {
		this.actualClass = actualClass;
	}


}
