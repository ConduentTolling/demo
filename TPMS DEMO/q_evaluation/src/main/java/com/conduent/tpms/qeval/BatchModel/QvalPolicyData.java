package com.conduent.tpms.qeval.BatchModel;

public class QvalPolicyData {
	private Integer agencyId;
	private Long maxAmount;
	private Long maxRange;
	private Long minAmount;
	private Long minRange;
	private String accountType;
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Long getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Long getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(Long maxRange) {
		this.maxRange = maxRange;
	}
	public Long getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Long minAmount) {
		this.minAmount = minAmount;
	}
	public Long getMinRange() {
		return minRange;
	}
	public void setMinRange(Long minRange) {
		this.minRange = minRange;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	@Override
	public String toString() {
		return "QvalPolicyData [agencyId=" + agencyId + ", maxAmount=" + maxAmount + ", maxRange=" + maxRange
				+ ", minAmount=" + minAmount + ", minRange=" + minRange + ", accountType=" + accountType + "]";
	}
	

}
