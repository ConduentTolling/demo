package com.conduent.tollposting.dto;

import java.time.LocalDate;
import com.google.gson.annotations.Expose;

public class IBTSDataDto 
{
	@Expose(serialize = true, deserialize = true)
	private String depositId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;
	
	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate=LocalDate.now();
	
	@Expose(serialize = true, deserialize = true)
	private LocalDate reconDate=LocalDate.now();
	
	@Expose(serialize = true, deserialize = true)
	private String revenueDate;
	
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public LocalDate getReconDate() {
		return reconDate;
	}

	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
	}

	public String getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}

	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}

	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}

	public Double getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	@Override
	public String toString() {
		return "IBTSDataDto [depositId=" + depositId + ", txStatus=" + txStatus + ", laneTxId=" + laneTxId
				+ ", planTypeId=" + planTypeId + ", postedDate=" + postedDate + ", reconDate=" + reconDate
				+ ", revenueDate=" + revenueDate + ", tollRevenueType=" + tollRevenueType + ", unrealizedAmount="
				+ unrealizedAmount + ", collectedAmount=" + collectedAmount + ", expectedRevenueAmount="
				+ expectedRevenueAmount + ", postedFareAmount=" + postedFareAmount + "]";
	}
}