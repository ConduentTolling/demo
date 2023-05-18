package com.conduent.tollposting.model;

public class Agency 
{
	private Long agencyId;
	private String agencyName;
    private String agencyShortName;
    private String devicePrefix;
    private String isHomeAgency;
    private String scheduledPricingFlag;
    private String clmmAdjAllowed;
    private String isIagStyleReciprocity;
    private String calculateTollAmount;
    private Long parentAgencyId;
    private String stmtDescription;
    private String invoiceEnabled;

    
    public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyShortName() {
		return agencyShortName;
	}
	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}
	public String getDevicePrefix() {
		return devicePrefix;
	}
	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	public String getIsHomeAgency() {
		return isHomeAgency;
	}
	public void setIsHomeAgency(String isHomeAgency) {
		this.isHomeAgency = isHomeAgency;
	}
	public String getScheduledPricingFlag() {
		return scheduledPricingFlag;
	}
	public void setScheduledPricingFlag(String scheduledPricingFlag) {
		this.scheduledPricingFlag = scheduledPricingFlag;
	}
	public String getClmmAdjAllowed() {
		return clmmAdjAllowed;
	}
	public void setClmmAdjAllowed(String clmmAdjAllowed) {
		this.clmmAdjAllowed = clmmAdjAllowed;
	}
	public String getIsIagStyleReciprocity() {
		return isIagStyleReciprocity;
	}
	public void setIsIagStyleReciprocity(String isIagStyleReciprocity) {
		this.isIagStyleReciprocity = isIagStyleReciprocity;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public Long getParentAgencyId() {
		return parentAgencyId;
	}
	public void setParentAgencyId(Long parentAgencyId) {
		this.parentAgencyId = parentAgencyId;
	}
	public String getStmtDescription() {
		return stmtDescription;
	}
	public void setStmtDescription(String stmtDescription) {
		this.stmtDescription = stmtDescription;
	}
	public String getInvoiceEnabled() {
		return invoiceEnabled;
	}
	public void setInvoiceEnabled(String invoiceEnabled) {
		this.invoiceEnabled = invoiceEnabled;
	}
    
    

}
