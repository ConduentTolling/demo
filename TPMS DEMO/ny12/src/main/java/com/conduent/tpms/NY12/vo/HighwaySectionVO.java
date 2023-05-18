package com.conduent.tpms.NY12.vo;

public class HighwaySectionVO {
	
	private int sectionId;
	private int agencyId;
	private String sectionName;
	private int terminalPlazaId1;
	private int terminalPlazaId2;
	private int timeLimitMin;
	private String matchingEnabled;
	private String xmatchingEnabled;
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public int getTerminalPlazaId1() {
		return terminalPlazaId1;
	}
	public void setTerminalPlazaId1(int terminalPlazaId1) {
		this.terminalPlazaId1 = terminalPlazaId1;
	}
	public int getTerminalPlazaId2() {
		return terminalPlazaId2;
	}
	public void setTerminalPlazaId2(int terminalPlazaId2) {
		this.terminalPlazaId2 = terminalPlazaId2;
	}
	public int getTimeLimitMin() {
		return timeLimitMin;
	}
	public void setTimeLimitMin(int timeLimitMin) {
		this.timeLimitMin = timeLimitMin;
	}
	public String getMatchingEnabled() {
		return matchingEnabled;
	}
	public void setMatchingEnabled(String matchingEnabled) {
		this.matchingEnabled = matchingEnabled;
	}
	public String getXmatchingEnabled() {
		return xmatchingEnabled;
	}
	public void setXmatchingEnabled(String xmatchingEnabled) {
		this.xmatchingEnabled = xmatchingEnabled;
	}

}


