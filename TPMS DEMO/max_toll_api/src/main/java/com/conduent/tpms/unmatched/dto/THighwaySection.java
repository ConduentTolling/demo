package com.conduent.tpms.unmatched.dto;

public class THighwaySection 
{
	private Integer sectionId;
	private Integer agencyId;
	private String sectionName;
	private Integer terminalPlazaId_1;
	private Integer terminalPlazaId_2;
	private Integer timeLimitMin;
	private String matchingEnabled;
	private String XMatchingEnabled;
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Integer getTerminalPlazaId_1() {
		return terminalPlazaId_1;
	}
	public void setTerminalPlazaId_1(Integer terminalPlazaId_1) {
		this.terminalPlazaId_1 = terminalPlazaId_1;
	}
	public Integer getTerminalPlazaId_2() {
		return terminalPlazaId_2;
	}
	public void setTerminalPlazaId_2(Integer terminalPlazaId_2) {
		this.terminalPlazaId_2 = terminalPlazaId_2;
	}
	public Integer getTimeLimitMin() {
		return timeLimitMin;
	}
	public void setTimeLimitMin(Integer timeLimitMin) {
		this.timeLimitMin = timeLimitMin;
	}
	public String getMatchingEnabled() {
		return matchingEnabled;
	}
	public void setMatchingEnabled(String matchingEnabled) {
		this.matchingEnabled = matchingEnabled;
	}
	public String getXMatchingEnabled() {
		return XMatchingEnabled;
	}
	public void setXMatchingEnabled(String xMatchingEnabled) {
		XMatchingEnabled = xMatchingEnabled;
	}
	@Override
	public String toString() {
		return "THighwaySection [sectionId=" + sectionId + ", agencyId=" + agencyId + ", sectionName=" + sectionName
				+ ", terminalPlazaId_1=" + terminalPlazaId_1 + ", terminalPlazaId_2=" + terminalPlazaId_2
				+ ", timeLimitMin=" + timeLimitMin + ", matchingEnabled=" + matchingEnabled + ", XMatchingEnabled="
				+ XMatchingEnabled + "]";
	}
	
	
	
	
}
