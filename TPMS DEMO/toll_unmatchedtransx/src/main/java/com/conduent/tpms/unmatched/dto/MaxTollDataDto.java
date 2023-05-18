package com.conduent.tpms.unmatched.dto;

public class MaxTollDataDto {

	
	private Long etcAccountId;
	private Long laneTxId;
	private Integer entryPlazaId;
	private Integer cLass;
	private Integer planId;
	private Integer tollRevenueType;
	private Integer laneId;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private String txTimestamp;
	private String dataSource;
	
	
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public Integer getcLass() {
		return cLass;
	}
	public void setcLass(Integer cLass) {
		this.cLass = cLass;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public String getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public String toString() {
		return "MaxTollDataDto [etcAccountId=" + etcAccountId + ", laneTxId=" + laneTxId + ", entryPlazaId="
				+ entryPlazaId + ", cLass=" + cLass + ", planId=" + planId + ", tollRevenueType=" + tollRevenueType
				+ ", laneId=" + laneId + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", txTimestamp="
				+ txTimestamp + ", dataSource=" + dataSource + "]";
	}
	
	
	
	
	
	

}
