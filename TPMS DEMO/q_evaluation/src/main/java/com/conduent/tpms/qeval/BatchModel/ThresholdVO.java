package com.conduent.tpms.qeval.BatchModel;

import java.util.List;

//import com.conduent.fpms.dto.AccountReceivableSalesOrderDetailDTO;


public class ThresholdVO {
	
	private String accountType;
	private Integer agencyId;
	private Double amount;
	private String payType;
	private Double percentage;
	private List<String> planList;
	//List<AccountReceivableSalesOrderDetailDTO> orderList;
	//private PlanRequestDetails[] planRequestDetails;
	//private TagRequestDetails[] tagDetails;
	private Double rebillAmt;
	private String sourceFlag;
	
	
	
	public Double getRebillAmt() {
		return rebillAmt;
	}
	public void setRebillAmt(Double rebillAmt) {
		this.rebillAmt = rebillAmt;
	}
	public String getSourceFlag() {
		return sourceFlag;
	}
	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
	
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public List<String> getPlanList() {
		return planList;
	}
	public void setPlanList(List<String> planList) {
		this.planList = planList;
	}
	

}
