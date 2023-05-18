package com.conduent.Ibtsprocessing.model;

public class Plaza {

	private Integer plazaId;
	private String lprEnabled;
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	@Override
	public String toString() {
		return "Plaza [plazaId=" + plazaId + ", lprEnabled=" + lprEnabled + "]";
	} 
	
	
}
