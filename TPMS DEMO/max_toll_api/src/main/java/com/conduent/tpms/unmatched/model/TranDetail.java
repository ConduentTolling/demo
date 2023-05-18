package com.conduent.tpms.unmatched.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class TranDetail 
{
	
	private Long laneTxId;
	private String txTimeStamp;
	private LocalDate txDate;
	private Integer tollRevenueType;
	private Integer actualClass;
	private Integer planType;  
	private Integer plazaAgencyId;
	private Integer laneId;
	private Integer plazaId;
	private String reciprocityTrx;
	private String txSubType;
	private String deviceNo;
	private String entryPlazaId;
	

	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getTxSubType() {
		return txSubType;
	}
	public void setTxSubType(String txSubType) {
		this.txSubType = txSubType;
	}
	public String getReciprocityTrx() {
		return reciprocityTrx;
	}
	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Integer getActualClass() {
		return actualClass;
	}
	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(String txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	@Override
	public String toString() {
		return "TranDetail [laneTxId=" + laneTxId + ", txTimeStamp=" + txTimeStamp + ", txDate=" + txDate
				+ ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass + ", planType=" + planType
				+ ", plazaAgencyId=" + plazaAgencyId + ", laneId=" + laneId + ", plazaId=" + plazaId
				+ ", reciprocityTrx=" + reciprocityTrx + ", txSubType=" + txSubType + ", deviceNo=" + deviceNo
				+ ", entryPlazaId=" + entryPlazaId + "]";
	}
	
	

}