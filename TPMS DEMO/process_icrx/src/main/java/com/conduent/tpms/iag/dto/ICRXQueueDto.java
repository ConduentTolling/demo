package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class ICRXQueueDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate;
	@Expose(serialize = true, deserialize = true)
	private String depositId;
	@Expose(serialize = true, deserialize = true)
	private String atpFileId;
	@Expose(serialize = true, deserialize = true)
	private LocalDate reconDate;
	@Expose(serialize = true, deserialize = true)
	private Integer eventType;
	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;
	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;
	
	
	
	public static ICRXQueueDto dtoFromJson(String message) {
		ICRXQueueDto queueMessageDTO = null;
		Gson gson = new Gson();
		queueMessageDTO = gson.fromJson(message, ICRXQueueDto.class);
		return queueMessageDTO;
		}
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
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
	public String getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(String atpFileId) {
		this.atpFileId = atpFileId;
	}
	public LocalDate getReconDate() {
		return reconDate;
	}
	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public Integer getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}
	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}
	
	
	@Override
	public String toString() {
		return "ICRXQueueVO [laneTxId=" + laneTxId + ", txStatus=" + txStatus + ", postedDate=" + postedDate
				+ ", depositId=" + depositId + ", atpFileId=" + atpFileId + ", reconDate=" + reconDate + ", eventType="
				+ eventType + ", planTypeId=" + planTypeId + ", accountAgencyId=" + accountAgencyId
				+ ", postedFareAmount=" + postedFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + "]";
	}
	
	
	
	
}
