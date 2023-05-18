package com.conduent.tpms.qatp.dto;

/**
 * Transaction Account Info Dto
 * 
 * @author Urvashi C
 *
 */
public class AccountInfoDto {

	private Double acctBalance = 0.0;
	private Integer acctType = 0;
	private Integer actStatus = 0;
	private Integer badSubAcct = 0;
	private Integer commercialId = 0;
	private Double currentBalance = 0.0;
	private String deviceInternalNo;
	private Integer deviceStatus = 0;
	private Integer errorCode = 0;
	private Integer finStatus = 0;
	private Integer homeAgencyDev = 0;
	private String homeIssuedDev;
	private Integer iagStatus = 0;
	private Integer invalidAgencyPfx = 0;
	private String isPrevalid;
	private Integer isUnregistered = 0;
	private Integer planCount = 0;
	private Double postPaidBalance = 0.0;
	private Integer postPaidStatus = 0;
	private Integer prePaidCount = 0;
	private Integer prevalidAcct = 0;
	private Integer rebillType = 0;
	private Integer scheduledPricingFlag = 0;
	private Integer speedLimit = 5000;
	private Integer speedStatus = 0;
	private String status;
	private Integer tagFound = 0;

	public AccountInfoDto() {
		super();
	}

	public Double getAcctBalance() {
		return acctBalance;
	}

	public void setAcctBalance(Double acctBalance) {
		this.acctBalance = acctBalance;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public Integer getBadSubAcct() {
		return badSubAcct;
	}

	public void setBadSubAcct(Integer badSubAcct) {
		this.badSubAcct = badSubAcct;
	}

	public Integer getCommercialId() {
		return commercialId;
	}

	public void setCommercialId(Integer commercialId) {
		this.commercialId = commercialId;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getDeviceInternalNo() {
		return deviceInternalNo;
	}

	public void setDeviceInternalNo(String deviceInternalNo) {
		this.deviceInternalNo = deviceInternalNo;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getFinStatus() {
		return finStatus;
	}

	public void setFinStatus(Integer finStatus) {
		this.finStatus = finStatus;
	}

	public Integer getHomeAgencyDev() {
		return homeAgencyDev;
	}

	public void setHomeAgencyDev(Integer homeAgencyDev) {
		this.homeAgencyDev = homeAgencyDev;
	}

	public String getHomeIssuedDev() {
		return homeIssuedDev;
	}

	public void setHomeIssuedDev(String homeIssuedDev) {
		this.homeIssuedDev = homeIssuedDev;
	}

	public Integer getIagStatus() {
		return iagStatus;
	}

	public void setIagStatus(Integer iagStatus) {
		this.iagStatus = iagStatus;
	}

	public Integer getInvalidAgencyPfx() {
		return invalidAgencyPfx;
	}

	public void setInvalidAgencyPfx(Integer invalidAgencyPfx) {
		this.invalidAgencyPfx = invalidAgencyPfx;
	}

	public String getIsPrevalid() {
		return isPrevalid;
	}

	public void setIsPrevalid(String isPrevalid) {
		this.isPrevalid = isPrevalid;
	}

	public Integer getIsUnregistered() {
		return isUnregistered;
	}

	public void setIsUnregistered(Integer isUnregistered) {
		this.isUnregistered = isUnregistered;
	}

	public Integer getPlanCount() {
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	public Double getPostPaidBalance() {
		return postPaidBalance;
	}

	public void setPostPaidBalance(Double postPaidBalance) {
		this.postPaidBalance = postPaidBalance;
	}

	public Integer getPostPaidStatus() {
		return postPaidStatus;
	}

	public void setPostPaidStatus(Integer postPaidStatus) {
		this.postPaidStatus = postPaidStatus;
	}

	public Integer getPrePaidCount() {
		return prePaidCount;
	}

	public void setPrePaidCount(Integer prePaidCount) {
		this.prePaidCount = prePaidCount;
	}

	public Integer getPrevalidAcct() {
		return prevalidAcct;
	}

	public void setPrevalidAcct(Integer prevalidAcct) {
		this.prevalidAcct = prevalidAcct;
	}

	public Integer getRebillType() {
		return rebillType;
	}

	public void setRebillType(Integer rebillType) {
		this.rebillType = rebillType;
	}

	public Integer getScheduledPricingFlag() {
		return scheduledPricingFlag;
	}

	public void setScheduledPricingFlag(Integer scheduledPricingFlag) {
		this.scheduledPricingFlag = scheduledPricingFlag;
	}

	public Integer getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(Integer speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Integer getSpeedStatus() {
		return speedStatus;
	}

	public void setSpeedStatus(Integer speedStatus) {
		this.speedStatus = speedStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTagFound() {
		return tagFound;
	}

	public void setTagFound(Integer tagFound) {
		this.tagFound = tagFound;
	}

	@Override
	public String toString() {
		return "AccountInfoDto [acctBalance=" + acctBalance + ", acctType=" + acctType + ", actStatus=" + actStatus
				+ ", badSubAcct=" + badSubAcct + ", commercialId=" + commercialId + ", currentBalance=" + currentBalance
				+ ", deviceInternalNo=" + deviceInternalNo + ", deviceStatus=" + deviceStatus + ", errorCode="
				+ errorCode + ", finStatus=" + finStatus + ", homeAgencyDev=" + homeAgencyDev + ", homeIssuedDev="
				+ homeIssuedDev + ", iagStatus=" + iagStatus + ", invalidAgencyPfx=" + invalidAgencyPfx
				+ ", isPrevalid=" + isPrevalid + ", isUnregistered=" + isUnregistered + ", planCount=" + planCount
				+ ", postPaidBalance=" + postPaidBalance + ", postPaidStatus=" + postPaidStatus + ", prePaidCount="
				+ prePaidCount + ", prevalidAcct=" + prevalidAcct + ", rebillType=" + rebillType
				+ ", scheduledPricingFlag=" + scheduledPricingFlag + ", speedLimit=" + speedLimit + ", speedStatus="
				+ speedStatus + ", status=" + status + ", tagFound=" + tagFound + "]";
	}

}
