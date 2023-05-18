package com.conduent.tpms.process25a.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SystemAccountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer processParameterId;
	private Integer agencyId;
	private Integer plazaId;
	private Integer storeId;
	private String paramName;
	private String paramGroup;
	private String paramCode;
	private String paramValue;
	private Integer paramConfig;
	private String cscLookupKey;
	private LocalDateTime updateTs;
	
	
	public SystemAccountVO() {
		
	}


	public SystemAccountVO(Integer processParameterId, Integer agencyId, Integer plazaId, Integer storeId,
			String paramName, String paramGroup, String paramCode, String paramValue, Integer paramConfig,
			String cscLookupKey, LocalDateTime updateTs) {
		super();
		this.processParameterId = processParameterId;
		this.agencyId = agencyId;
		this.plazaId = plazaId;
		this.storeId = storeId;
		this.paramName = paramName;
		this.paramGroup = paramGroup;
		this.paramCode = paramCode;
		this.paramValue = paramValue;
		this.paramConfig = paramConfig;
		this.cscLookupKey = cscLookupKey;
		this.updateTs = updateTs;
	}


	public Integer getProcessParameterId() {
		return processParameterId;
	}


	public void setProcessParameterId(Integer processParameterId) {
		this.processParameterId = processParameterId;
	}


	public Integer getAgencyId() {
		return agencyId;
	}


	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}


	public Integer getPlazaId() {
		return plazaId;
	}


	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}


	public Integer getStoreId() {
		return storeId;
	}


	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}


	public String getParamName() {
		return paramName;
	}


	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public String getParamGroup() {
		return paramGroup;
	}


	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}


	public String getParamCode() {
		return paramCode;
	}


	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}


	public String getParamValue() {
		return paramValue;
	}


	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}


	public Integer getParamConfig() {
		return paramConfig;
	}


	public void setParamConfig(Integer paramConfig) {
		this.paramConfig = paramConfig;
	}


	public String getCscLookupKey() {
		return cscLookupKey;
	}


	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}


	public LocalDateTime getUpdateTs() {
		return updateTs;
	}


	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}


	@Override
	public String toString() {
		return "SystemAccountVO [processParameterId=" + processParameterId + ", agencyId=" + agencyId + ", plazaId="
				+ plazaId + ", storeId=" + storeId + ", paramName=" + paramName + ", paramGroup=" + paramGroup
				+ ", paramCode=" + paramCode + ", paramValue=" + paramValue + ", paramConfig=" + paramConfig
				+ ", cscLookupKey=" + cscLookupKey + ", updateTs=" + updateTs + "]";
	}
	
	
	
	
}


