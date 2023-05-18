package com.conduent.parking.posting.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TollException
{
	private Long laneTxId;
	private LocalDate txDate;
	private LocalDateTime txTimeStamp;
	private LocalDate postedDate;
	private String depositId;
	private String rowId;
	private Integer txStatus;
	private LocalDate revenueDate;
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	public LocalDateTime getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(LocalDateTime txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	
	
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	@Override
	public String toString() {
		return "TollException [laneTxId=" + laneTxId + ", txDate=" + txDate + ", txTimeStamp=" + txTimeStamp
				+ ", postedDate=" + postedDate + ", depositId=" + depositId + ", rowId=" + rowId + ", txStatus="
				+ txStatus + ", revenueDate=" + revenueDate + "]";
	}
	
}