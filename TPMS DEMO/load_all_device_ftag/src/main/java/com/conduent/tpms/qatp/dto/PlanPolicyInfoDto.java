package com.conduent.tpms.qatp.dto;

import java.io.Serializable;


/**
 * Plan policy response dto
 * 
 * @author Urvashi C
 *
 */

public class PlanPolicyInfoDto implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -359808262896070377L;
	
	private int lcAllPlans = 0;
	private int revPlanCount = 0;
	private Double itagInfo = 0.0;
	private int nystaNonRevCount = 0;
	private int nystaAnnualPlanCount = 0;
	private int nystaPostPaidComlCount = 0;
	private int nysbaNonRevCount = 0;
	private int mtaNonRevCount = 0;
	private int paNonRevCount = 0;
	private int rioNonRevCount = 0;
	private int paSiBridgesCount = 0;
	private int paBridgesCount = 0;
	private int paCarpoolCount = 0;
	private int plazaLimited = 0;
	private int tbnrfpCount = 0;
	private int mtafCount = 0;
	private int govtCount = 0;
	private int sirCount = 0;
	private int rrCount = 0;
	private int brCount = 0;
	private int qrCount = 0;
	
	public int getLcAllPlans() {
		return lcAllPlans;
	}
	public void setLcAllPlans(int lcAllPlans) {
		this.lcAllPlans = lcAllPlans;
	}
	public int getRevPlanCount() {
		return revPlanCount;
	}
	public void setRevPlanCount(int revPlanCount) {
		this.revPlanCount = revPlanCount;
	}
	public Double getItagInfo() {
		return itagInfo;
	}
	public void setItagInfo(Double itagInfo) {
		this.itagInfo = itagInfo;
	}
	public int getNystaNonRevCount() {
		return nystaNonRevCount;
	}
	public void setNystaNonRevCount(int nystaNonRevCount) {
		this.nystaNonRevCount = nystaNonRevCount;
	}
	public int getNystaAnnualPlanCount() {
		return nystaAnnualPlanCount;
	}
	public void setNystaAnnualPlanCount(int nystaAnnualPlanCount) {
		this.nystaAnnualPlanCount = nystaAnnualPlanCount;
	}
	public int getNystaPostPaidComlCount() {
		return nystaPostPaidComlCount;
	}
	public void setNystaPostPaidComlCount(int nystaPostPaidComlCount) {
		this.nystaPostPaidComlCount = nystaPostPaidComlCount;
	}
	public int getNysbaNonRevCount() {
		return nysbaNonRevCount;
	}
	public void setNysbaNonRevCount(int nysbaNonRevCount) {
		this.nysbaNonRevCount = nysbaNonRevCount;
	}
	public int getMtaNonRevCount() {
		return mtaNonRevCount;
	}
	public void setMtaNonRevCount(int mtaNonRevCount) {
		this.mtaNonRevCount = mtaNonRevCount;
	}
	public int getPaNonRevCount() {
		return paNonRevCount;
	}
	public void setPaNonRevCount(int paNonRevCount) {
		this.paNonRevCount = paNonRevCount;
	}
	public int getRioNonRevCount() {
		return rioNonRevCount;
	}
	public void setRioNonRevCount(int rioNonRevCount) {
		this.rioNonRevCount = rioNonRevCount;
	}
	public int getPaSiBridgesCount() {
		return paSiBridgesCount;
	}
	public void setPaSiBridgesCount(int paSiBridgesCount) {
		this.paSiBridgesCount = paSiBridgesCount;
	}
	public int getPaBridgesCount() {
		return paBridgesCount;
	}
	public void setPaBridgesCount(int paBridgesCount) {
		this.paBridgesCount = paBridgesCount;
	}
	public int getPaCarpoolCount() {
		return paCarpoolCount;
	}
	public void setPaCarpoolCount(int paCarpoolCount) {
		this.paCarpoolCount = paCarpoolCount;
	}
	public int getPlazaLimited() {
		return plazaLimited;
	}
	public void setPlazaLimited(int plazaLimited) {
		this.plazaLimited = plazaLimited;
	}
	public int getTbnrfpCount() {
		return tbnrfpCount;
	}
	public void setTbnrfpCount(int tbnrfpCount) {
		this.tbnrfpCount = tbnrfpCount;
	}
	public int getMtafCount() {
		return mtafCount;
	}
	public void setMtafCount(int mtafCount) {
		this.mtafCount = mtafCount;
	}
	public int getGovtCount() {
		return govtCount;
	}
	public void setGovtCount(int govtCount) {
		this.govtCount = govtCount;
	}
	public int getSirCount() {
		return sirCount;
	}
	public void setSirCount(int sirCount) {
		this.sirCount = sirCount;
	}
	public int getRrCount() {
		return rrCount;
	}
	public void setRrCount(int rrCount) {
		this.rrCount = rrCount;
	}
	public int getBrCount() {
		return brCount;
	}
	public void setBrCount(int brCount) {
		this.brCount = brCount;
	}
	public int getQrCount() {
		return qrCount;
	}
	public void setQrCount(int qrCount) {
		this.qrCount = qrCount;
	}
	
	
	@Override
	public String toString() {
		return "PlanPolicyInfoDto [lcAllPlans=" + lcAllPlans + ", revPlanCount=" + revPlanCount + ", itagInfo="
				+ itagInfo + ", nystaNonRevCount=" + nystaNonRevCount + ", nystaAnnualPlanCount=" + nystaAnnualPlanCount
				+ ", nystaPostPaidComlCount=" + nystaPostPaidComlCount + ", nysbaNonRevCount=" + nysbaNonRevCount
				+ ", mtaNonRevCount=" + mtaNonRevCount + ", paNonRevCount=" + paNonRevCount + ", rioNonRevCount="
				+ rioNonRevCount + ", paSiBridgesCount=" + paSiBridgesCount + ", paBridgesCount=" + paBridgesCount
				+ ", paCarpoolCount=" + paCarpoolCount + ", plazaLimited=" + plazaLimited + ", tbnrfpCount="
				+ tbnrfpCount + ", mtafCount=" + mtafCount + ", govtCount=" + govtCount + ", sirCount=" + sirCount
				+ ", rrCount=" + rrCount + ", brCount=" + brCount + ", qrCount=" + qrCount + "]";
	}
}
