package com.conduent.tpms.process25a.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlanPolicyVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer planType;
	private String planName;
	private Integer planId;
	private Integer mapAgencyId;
	private Integer planDays;
	private Integer maxTrips;
	private Integer minTrips;
	private Integer planBit;
	private String monthlyPlan;
	private String chargePerDevice;
	private Integer expirationMonth;
	private Integer expirationDay;
	private Integer planAgencyPriority;
	private Integer minDaysForRental;
	private String plazaLimited;
	private String recedentialLimited;
	private String chargeUnusedTrips;
	private String manualSuspention;
	private String automaticReactivation;
	private String renewLimitInd;
	private String chargePlanBalance;
	private String applyUsageCredit;
	private Integer renewReqTrips;
	private Integer renewReqDays;
	private Integer minSuspensionDays;
	private Integer numberOfPeriods;
	private Integer reconLagPeriods;
	private String isCommute;
	private String isDeviceSpecific;
	private String isRevenuePlan;
	private String isProrateFee;
	private String isChargeFee;
	private String isAnnualPlan;
	private String isZeroDepositePlan;
	private String isPostPaid;
	private String isAccountLevelDiscount;
	private String isATripLimited;
	private String isServiceFeeExempt;
	private String isSatmentFeeExempt;
	private String isCalenderPeriod;
	private String isHybridPlan;
	private String isGovtAgency;
	private LocalDateTime updateTs;
	
	
	
	
	public PlanPolicyVO() {
		
	}


	public PlanPolicyVO(Integer planType, String planName, Integer planId, Integer mapAgencyId, Integer planDays,
			Integer maxTrips, Integer minTrips, Integer planBit, String monthlyPlan, String chargePerDevice,
			Integer expirationMonth, Integer expirationDay, Integer planAgencyPriority, Integer minDaysForRental,
			String plazaLimited, String recedentialLimited, String chargeUnusedTrips, String manualSuspention,
			String automaticReactivation, String renewLimitInd, String chargePlanBalance, String applyUsageCredit,
			Integer renewReqTrips, Integer renewReqDays, Integer minSuspensionDays, Integer numberOfPeriods,
			Integer reconLagPeriods, String isCommute, String isDeviceSpecific, String isRevenuePlan,
			String isProrateFee, String isChargeFee, String isAnnualPlan, String isZeroDepositePlan, String isPostPaid,
			String isAccountLevelDiscount, String isATripLimited, String isServiceFeeExempt, String isSatmentFeeExempt,
			String isCalenderPeriod, String isHybridPlan, String isGovtAgency, LocalDateTime updateTs) {
		super();
		this.planType = planType;
		this.planName = planName;
		this.planId = planId;
		this.mapAgencyId = mapAgencyId;
		this.planDays = planDays;
		this.maxTrips = maxTrips;
		this.minTrips = minTrips;
		this.planBit = planBit;
		this.monthlyPlan = monthlyPlan;
		this.chargePerDevice = chargePerDevice;
		this.expirationMonth = expirationMonth;
		this.expirationDay = expirationDay;
		this.planAgencyPriority = planAgencyPriority;
		this.minDaysForRental = minDaysForRental;
		this.plazaLimited = plazaLimited;
		this.recedentialLimited = recedentialLimited;
		this.chargeUnusedTrips = chargeUnusedTrips;
		this.manualSuspention = manualSuspention;
		this.automaticReactivation = automaticReactivation;
		this.renewLimitInd = renewLimitInd;
		this.chargePlanBalance = chargePlanBalance;
		this.applyUsageCredit = applyUsageCredit;
		this.renewReqTrips = renewReqTrips;
		this.renewReqDays = renewReqDays;
		this.minSuspensionDays = minSuspensionDays;
		this.numberOfPeriods = numberOfPeriods;
		this.reconLagPeriods = reconLagPeriods;
		this.isCommute = isCommute;
		this.isDeviceSpecific = isDeviceSpecific;
		this.isRevenuePlan = isRevenuePlan;
		this.isProrateFee = isProrateFee;
		this.isChargeFee = isChargeFee;
		this.isAnnualPlan = isAnnualPlan;
		this.isZeroDepositePlan = isZeroDepositePlan;
		this.isPostPaid = isPostPaid;
		this.isAccountLevelDiscount = isAccountLevelDiscount;
		this.isATripLimited = isATripLimited;
		this.isServiceFeeExempt = isServiceFeeExempt;
		this.isSatmentFeeExempt = isSatmentFeeExempt;
		this.isCalenderPeriod = isCalenderPeriod;
		this.isHybridPlan = isHybridPlan;
		this.isGovtAgency = isGovtAgency;
		this.updateTs = updateTs;
	}
	
	
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getMapAgencyId() {
		return mapAgencyId;
	}
	public void setMapAgencyId(Integer mapAgencyId) {
		this.mapAgencyId = mapAgencyId;
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
	public Integer getPlanBit() {
		return planBit;
	}
	public void setPlanBit(Integer planBit) {
		this.planBit = planBit;
	}
	public String getMonthlyPlan() {
		return monthlyPlan;
	}
	public void setMonthlyPlan(String monthlyPlan) {
		this.monthlyPlan = monthlyPlan;
	}
	public String getChargePerDevice() {
		return chargePerDevice;
	}
	public void setChargePerDevice(String chargePerDevice) {
		this.chargePerDevice = chargePerDevice;
	}
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
	public Integer getPlanAgencyPriority() {
		return planAgencyPriority;
	}
	public void setPlanAgencyPriority(Integer planAgencyPriority) {
		this.planAgencyPriority = planAgencyPriority;
	}
	public Integer getMinDaysForRental() {
		return minDaysForRental;
	}
	public void setMinDaysForRental(Integer minDaysForRental) {
		this.minDaysForRental = minDaysForRental;
	}
	public String getPlazaLimited() {
		return plazaLimited;
	}
	public void setPlazaLimited(String plazaLimited) {
		this.plazaLimited = plazaLimited;
	}
	public String getRecedentialLimited() {
		return recedentialLimited;
	}
	public void setRecedentialLimited(String recedentialLimited) {
		this.recedentialLimited = recedentialLimited;
	}
	public String getChargeUnusedTrips() {
		return chargeUnusedTrips;
	}
	public void setChargeUnusedTrips(String chargeUnusedTrips) {
		this.chargeUnusedTrips = chargeUnusedTrips;
	}
	public String getManualSuspention() {
		return manualSuspention;
	}
	public void setManualSuspention(String manualSuspention) {
		this.manualSuspention = manualSuspention;
	}
	public String getAutomaticReactivation() {
		return automaticReactivation;
	}
	public void setAutomaticReactivation(String automaticReactivation) {
		this.automaticReactivation = automaticReactivation;
	}
	public String getRenewLimitInd() {
		return renewLimitInd;
	}
	public void setRenewLimitInd(String renewLimitInd) {
		this.renewLimitInd = renewLimitInd;
	}
	public String getChargePlanBalance() {
		return chargePlanBalance;
	}
	public void setChargePlanBalance(String chargePlanBalance) {
		this.chargePlanBalance = chargePlanBalance;
	}
	public String getApplyUsageCredit() {
		return applyUsageCredit;
	}
	public void setApplyUsageCredit(String applyUsageCredit) {
		this.applyUsageCredit = applyUsageCredit;
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
	public Integer getMinSuspensionDays() {
		return minSuspensionDays;
	}
	public void setMinSuspensionDays(Integer minSuspensionDays) {
		this.minSuspensionDays = minSuspensionDays;
	}
	public Integer getNumberOfPeriods() {
		return numberOfPeriods;
	}
	public void setNumberOfPeriods(Integer numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}
	public Integer getReconLagPeriods() {
		return reconLagPeriods;
	}
	public void setReconLagPeriods(Integer reconLagPeriods) {
		this.reconLagPeriods = reconLagPeriods;
	}
	public String getIsCommute() {
		return isCommute;
	}
	public void setIsCommute(String isCommute) {
		this.isCommute = isCommute;
	}
	public String getIsDeviceSpecific() {
		return isDeviceSpecific;
	}
	public void setIsDeviceSpecific(String isDeviceSpecific) {
		this.isDeviceSpecific = isDeviceSpecific;
	}
	public String getIsRevenuePlan() {
		return isRevenuePlan;
	}
	public void setIsRevenuePlan(String isRevenuePlan) {
		this.isRevenuePlan = isRevenuePlan;
	}
	public String getIsProrateFee() {
		return isProrateFee;
	}
	public void setIsProrateFee(String isProrateFee) {
		this.isProrateFee = isProrateFee;
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
	public String getIsZeroDepositePlan() {
		return isZeroDepositePlan;
	}
	public void setIsZeroDepositePlan(String isZeroDepositePlan) {
		this.isZeroDepositePlan = isZeroDepositePlan;
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
	public String getIsATripLimited() {
		return isATripLimited;
	}
	public void setIsATripLimited(String isATripLimited) {
		this.isATripLimited = isATripLimited;
	}
	public String getIsServiceFeeExempt() {
		return isServiceFeeExempt;
	}
	public void setIsServiceFeeExempt(String isServiceFeeExempt) {
		this.isServiceFeeExempt = isServiceFeeExempt;
	}
	public String getIsSatmentFeeExempt() {
		return isSatmentFeeExempt;
	}
	public void setIsSatmentFeeExempt(String isSatmentFeeExempt) {
		this.isSatmentFeeExempt = isSatmentFeeExempt;
	}
	public String getIsCalenderPeriod() {
		return isCalenderPeriod;
	}
	public void setIsCalenderPeriod(String isCalenderPeriod) {
		this.isCalenderPeriod = isCalenderPeriod;
	}
	public String getIsHybridPlan() {
		return isHybridPlan;
	}
	public void setIsHybridPlan(String isHybridPlan) {
		this.isHybridPlan = isHybridPlan;
	}
	public String getIsGovtAgency() {
		return isGovtAgency;
	}
	public void setIsGovtAgency(String isGovtAgency) {
		this.isGovtAgency = isGovtAgency;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	@Override
	public String toString() {
		return "PlanPolicyVO [planType=" + planType + ", planName=" + planName + ", planId=" + planId + ", mapAgencyId="
				+ mapAgencyId + ", planDays=" + planDays + ", maxTrips=" + maxTrips + ", minTrips=" + minTrips
				+ ", planBit=" + planBit + ", monthlyPlan=" + monthlyPlan + ", chargePerDevice=" + chargePerDevice
				+ ", expirationMonth=" + expirationMonth + ", expirationDay=" + expirationDay + ", planAgencyPriority="
				+ planAgencyPriority + ", minDaysForRental=" + minDaysForRental + ", plazaLimited=" + plazaLimited
				+ ", recedentialLimited=" + recedentialLimited + ", chargeUnusedTrips=" + chargeUnusedTrips
				+ ", manualSuspention=" + manualSuspention + ", automaticReactivation=" + automaticReactivation
				+ ", renewLimitInd=" + renewLimitInd + ", chargePlanBalance=" + chargePlanBalance
				+ ", applyUsageCredit=" + applyUsageCredit + ", renewReqTrips=" + renewReqTrips + ", renewReqDays="
				+ renewReqDays + ", minSuspensionDays=" + minSuspensionDays + ", numberOfPeriods=" + numberOfPeriods
				+ ", reconLagPeriods=" + reconLagPeriods + ", isCommute=" + isCommute + ", isDeviceSpecific="
				+ isDeviceSpecific + ", isRevenuePlan=" + isRevenuePlan + ", isProrateFee=" + isProrateFee
				+ ", isChargeFee=" + isChargeFee + ", isAnnualPlan=" + isAnnualPlan + ", isZeroDepositePlan="
				+ isZeroDepositePlan + ", isPostPaid=" + isPostPaid + ", isAccountLevelDiscount="
				+ isAccountLevelDiscount + ", isATripLimited=" + isATripLimited + ", isServiceFeeExempt="
				+ isServiceFeeExempt + ", isSatmentFeeExempt=" + isSatmentFeeExempt + ", isCalenderPeriod="
				+ isCalenderPeriod + ", isHybridPlan=" + isHybridPlan + ", isGovtAgency=" + isGovtAgency + ", updateTs="
				+ updateTs + "]";
	}
	
	
	
}
