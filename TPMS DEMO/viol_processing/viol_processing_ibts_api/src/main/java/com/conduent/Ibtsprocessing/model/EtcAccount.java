package com.conduent.Ibtsprocessing.model;

public class EtcAccount {
	
	private Integer etcAccountId;
	private Integer accountType;
	private String unregistered;
	private String accountSuspended;
	public EtcAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Integer etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getUnregistered() {
		return unregistered;
	}
	public void setUnregistered(String unregistered) {
		this.unregistered = unregistered;
	}
	public String getAccountSuspended() {
		return accountSuspended;
	}
	public void setAccountSuspended(String accountSuspended) {
		this.accountSuspended = accountSuspended;
	}
	@Override
	public String toString() {
		return "EtcAccount [etcAccountId=" + etcAccountId + ", accountType=" + accountType + ", unregistered="
				+ unregistered + ", accountSuspended=" + accountSuspended + "]";
	}
	
	

}
