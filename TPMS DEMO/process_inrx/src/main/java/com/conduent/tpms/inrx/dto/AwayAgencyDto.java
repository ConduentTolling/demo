package com.conduent.tpms.inrx.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class AwayAgencyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long laneTxId;
	private Long etcAccountId;
	private Integer txStatus;
	private LocalDate postedDate;
	private String depositId;
	private LocalDate txDate;
	private OffsetDateTime txTimestamp;
	private LocalDate revenueDate;
	private String txExternRefNo;
	private Long externFileId;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer laneId;
	private String deviceNo;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	// private LocalDate revenueDate;
	private LocalDateTime updateTs;
	private String rowId;
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	@Override
	public String toString() {
		return "AwayAgencyDto [laneTxId=" + laneTxId + ", etcAccountId=" + etcAccountId + ", txStatus=" + txStatus
				+ ", postedDate=" + postedDate + ", depositId=" + depositId + ", txDate=" + txDate + ", txTimestamp="
				+ txTimestamp + ", revenueDate=" + revenueDate + ", txExternRefNo=" + txExternRefNo + ", externFileId="
				+ externFileId + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", laneId=" + laneId
				+ ", deviceNo=" + deviceNo + ", plateCountry=" + plateCountry + ", plateState=" + plateState
				+ ", plateNumber=" + plateNumber + ", updateTs=" + updateTs + ", rowId=" + rowId + "]";
	}
	
	

	

}
