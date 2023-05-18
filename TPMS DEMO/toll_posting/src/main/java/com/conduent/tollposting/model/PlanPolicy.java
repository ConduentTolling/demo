package com.conduent.tollposting.model;

public class PlanPolicy {
	private Integer planType;
	private Integer expirationMonth;
	private Integer expirationDay;
	private String isChargeFee;
	private String isAnnualPlan;
	private String isRevenuePlan;
	private Integer minDaysForRenewal;
	private Integer planDays;
	private Integer maxTrips;
	private Integer minTrips;
	private String residentLimited;
	private String isCommute;
	private String plazaLimited;
	private String isDeviceSpecific;
	private String chargeUnusedTrips;
	private String manualSuspension;
	private String automaticReactivation;
	private Integer mapAgencyId;
	private Integer planAgencyPriority;
	private String chargePlanBalance;
	private Integer renewReqTrips;
	private Integer renewReqDays;
	private String isProrateFee;
	private Integer minSuspensionDays;
	private String renewLimitInd;
	private String monthlyPlan;
	private Integer numberOfPeriods;
	private Integer planBit;
	private String chargePerDevice;
	private String applyUsageCredit;
	private Integer reconLagPeriod;
	private String isPostPaid;
	private String isAccountLevelDiscount="F";
	private String isTripLimited;
	private String isServiceFeeExempt;
	private String isStmtFeeExempt;
	private String isCalendarPeriod;
	private String displayValue;
	private String historyFlag; //:TODO
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public Integer getExpirationDay() {
		return expirationDay;
	}
	public void setExpirationDay(Integer expirationDay) {
		this.expirationDay = expirationDay;
	}
	public String getIsChargeFee() {
		return isChargeFee;
	}
	public void setIsChargeFee(String isChargeFee) {
		this.isChargeFee = isChargeFee;
	}
	
	public String getIsAnnualPlan() {
		return isAnnualPlan;
	}
	public void setIsAnnualPlan(String isAnnualPlan) {
		this.isAnnualPlan = isAnnualPlan;
	}
	public String getIsRevenuePlan() {
		return isRevenuePlan;
	}
	public void setIsRevenuePlan(String isRevenuePlan) {
		this.isRevenuePlan = isRevenuePlan;
	}
	public Integer getMinDaysForRenewal() {
		return minDaysForRenewal;
	}
	public void setMinDaysForRenewal(Integer minDaysForRenewal) {
		this.minDaysForRenewal = minDaysForRenewal;
	}
	public Integer getPlanDays() {
		return planDays;
	}
	public void setPlanDays(Integer planDays) {
		this.planDays = planDays;
	}
	public Integer getMaxTrips() {
		return maxTrips;
	}
	public void setMaxTrips(Integer maxTrips) {
		this.maxTrips = maxTrips;
	}
	public Integer getMinTrips() {
		return minTrips;
	}
	public void setMinTrips(Integer minTrips) {
		this.minTrips = minTrips;
	}
	public String getResidentLimited() {
		return residentLimited;
	}
	public void setResidentLimited(String residentLimited) {
		this.residentLimited = residentLimited;
	}
	public String getIsCommute() {
		return isCommute;
	}
	public void setIsCommute(String isCommute) {
		this.isCommute = isCommute;
	}
	public String getPlazaLimited() {
		return plazaLimited;
	}
	public void setPlazaLimited(String plazaLimited) {
		this.plazaLimited = plazaLimited;
	}
	public String getIsDeviceSpecific() {
		return isDeviceSpecific;
	}
	public void setIsDeviceSpecific(String isDeviceSpecific) {
		this.isDeviceSpecific = isDeviceSpecific;
	}
	public String getChargeUnusedTrips() {
		return chargeUnusedTrips;
	}
	public void setChargeUnusedTrips(String chargeUnusedTrips) {
		this.chargeUnusedTrips = chargeUnusedTrips;
	}
	public String getManualSuspension() {
		return manualSuspension;
	}
	public void setManualSuspension(String manualSuspension) {
		this.manualSuspension = manualSuspension;
	}
	public String getAutomaticReactivation() {
		return automaticReactivation;
	}
	public void setAutomaticReactivation(String automaticReactivation) {
		this.automaticReactivation = automaticReactivation;
	}
	public Integer getMapAgencyId() {
		return mapAgencyId;
	}
	public void setMapAgencyId(Integer mapAgencyId) {
		this.mapAgencyId = mapAgencyId;
	}
	public Integer getPlanAgencyPriority() {
		return planAgencyPriority;
	}
	public void setPlanAgencyPriority(Integer planAgencyPriority) {
		this.planAgencyPriority = planAgencyPriority;
	}
	public String getChargePlanBalance() {
		return chargePlanBalance;
	}
	public void setChargePlanBalance(String chargePlanBalance) {
		this.chargePlanBalance = chargePlanBalance;
	}
	public Integer getRenewReqTrips() {
		return renewReqTrips;
	}
	public void setRenewReqTrips(Integer renewReqTrips) {
		this.renewReqTrips = renewReqTrips;
	}
	public Integer getRenewReqDays() {
		return renewReqDays;
	}
	public void setRenewReqDays(Integer renewReqDays) {
		this.renewReqDays = renewReqDays;
	}
	public String getIsProrateFee() {
		return isProrateFee;
	}
	public void setIsProrateFee(String isProrateFee) {
		this.isProrateFee = isProrateFee;
	}
	public Integer getMinSuspensionDays() {
		return minSuspensionDays;
	}
	public void setMinSuspensionDays(Integer minSuspensionDays) {
		this.minSuspensionDays = minSuspensionDays;
	}
	public String getRenewLimitInd() {
		return renewLimitInd;
	}
	public void setRenewLimitInd(String renewLimitInd) {
		this.renewLimitInd = renewLimitInd;
	}
	public String getMonthlyPlan() {
		return monthlyPlan;
	}
	public void setMonthlyPlan(String monthlyPlan) {
		this.monthlyPlan = monthlyPlan;
	}
	public Integer getNumberOfPeriods() {
		return numberOfPeriods;
	}
	public void setNumberOfPeriods(Integer numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}
	public Integer getPlanBit() {
		return planBit;
	}
	public void setPlanBit(Integer planBit) {
		this.planBit = planBit;
	}
	public String getChargePerDevice() {
		return chargePerDevice;
	}
	public void setChargePerDevice(String chargePerDevice) {
		this.chargePerDevice = chargePerDevice;
	}
	public String getApplyUsageCredit() {
		return applyUsageCredit;
	}
	public void setApplyUsageCredit(String applyUsageCredit) {
		this.applyUsageCredit = applyUsageCredit;
	}
	public Integer getReconLagPeriod() {
		return reconLagPeriod;
	}
	public void setReconLagPeriod(Integer reconLagPeriod) {
		this.reconLagPeriod = reconLagPeriod;
	}
	public String getIsPostPaid() {
		return isPostPaid;
	}
	public void setIsPostPaid(String isPostPaid) {
		this.isPostPaid = isPostPaid;
	}
	public String getIsAccountLevelDiscount() {
		return isAccountLevelDiscount;
	}
	public void setIsAccountLevelDiscount(String isAccountLevelDiscount) {
		this.isAccountLevelDiscount = isAccountLevelDiscount;
	}
	public String getIsTripLimited() {
		return isTripLimited;
	}
	public void setIsTripLimited(String isTripLimited) {
		this.isTripLimited = isTripLimited;
	}
	public String getIsServiceFeeExempt() {
		return isServiceFeeExempt;
	}
	public void setIsServiceFeeExempt(String isServiceFeeExempt) {
		this.isServiceFeeExempt = isServiceFeeExempt;
	}
	public String getIsStmtFeeExempt() {
		return isStmtFeeExempt;
	}
	public void setIsStmtFeeExempt(String isStmtFeeExempt) {
		this.isStmtFeeExempt = isStmtFeeExempt;
	}
	public String getIsCalendarPeriod() {
		return isCalendarPeriod;
	}
	public void setIsCalendarPeriod(String isCalendarPeriod) {
		this.isCalendarPeriod = isCalendarPeriod;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public String getHistoryFlag() {
		return historyFlag;
	}
	public void setHistoryFlag(String historyFlag) {
		this.historyFlag = historyFlag;
	}
	@Override
	public String toString() {
		return "PlanPolicy [expirationMonth=" + expirationMonth + ", expirationDay=" + expirationDay + ", isChargeFee="
				+ isChargeFee + ", isAnnualPlan=" + isAnnualPlan + ", isRevenuePlan=" + isRevenuePlan
				+ ", minDaysForRenewal=" + minDaysForRenewal + ", planDays=" + planDays + ", maxTrips=" + maxTrips
				+ ", minTrips=" + minTrips + ", residentLimited=" + residentLimited + ", isCommute=" + isCommute
				+ ", plazaLimited=" + plazaLimited + ", isDeviceSpecific=" + isDeviceSpecific + ", chargeUnusedTrips="
				+ chargeUnusedTrips + ", manualSuspension=" + manualSuspension + ", automaticReactivation="
				+ automaticReactivation + ", mapAgencyId=" + mapAgencyId + ", planAgencyPriority=" + planAgencyPriority
				+ ", chargePlanBalance=" + chargePlanBalance + ", renewReqTrips=" + renewReqTrips + ", renewReqDays="
				+ renewReqDays + ", isProrateFee=" + isProrateFee + ", minSuspensionDays=" + minSuspensionDays
				+ ", renewLimitInd=" + renewLimitInd + ", monthlyPlan=" + monthlyPlan + ", numberOfPeriods="
				+ numberOfPeriods + ", planBit=" + planBit + ", chargePerDevice=" + chargePerDevice
				+ ", applyUsageCredit=" + applyUsageCredit + ", reconLagPeriod=" + reconLagPeriod + ", isPostPaid="
				+ isPostPaid + ", isAccountLevelDiscount=" + isAccountLevelDiscount + ", isTripLimited=" + isTripLimited
				+ ", isServiceFeeExempt=" + isServiceFeeExempt + ", isStmtFeeExempt=" + isStmtFeeExempt
				+ ", isCalendarPeriod=" + isCalendarPeriod + ", displayValue=" + displayValue + "]";
	}	
}