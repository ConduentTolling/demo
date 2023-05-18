package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class IITCDetailInfoVO implements Serializable {

	/**
	 * IITC file details record fields
	 */
	private static final long serialVersionUID = 2798677367041309955L;

	private String fileType;
	private String fromAgencyId;
	private String fileDate;
	private String fileTime;
	private String recordCount;
	private String custTagAgencyId; 
	private String custTagSerial; 
	private String custLastName;
	private String custFirstName; 
	private String custMi;
	private String custCompany; 
	private String custAddr1;
	private String custAddr2;
	private String custCity;
	private String custState; 
	private String custZip;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getCustTagAgencyId() {
		return custTagAgencyId;
	}
	public void setCustTagAgencyId(String custTagAgencyId) {
		this.custTagAgencyId = custTagAgencyId;
	}
	public String getCustTagSerial() {
		return custTagSerial;
	}
	public void setCustTagSerial(String custTagSerial) {
		this.custTagSerial = custTagSerial;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getCustMi() {
		return custMi;
	}
	public void setCustMi(String custMi) {
		this.custMi = custMi;
	}
	public String getCustCompany() {
		return custCompany;
	}
	public void setCustCompany(String custCompany) {
		this.custCompany = custCompany;
	}
	public String getCustAddr1() {
		return custAddr1;
	}
	public void setCustAddr1(String custAddr1) {
		this.custAddr1 = custAddr1;
	}
	public String getCustAddr2() {
		return custAddr2;
	}
	public void setCustAddr2(String custAddr2) {
		this.custAddr2 = custAddr2;
	}
	public String getCustCity() {
		return custCity;
	}
	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}
	public String getCustState() {
		return custState;
	}
	public void setCustState(String custState) {
		this.custState = custState;
	}
	public String getCustZip() {
		return custZip;
	}
	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "IITCDetailInfoVO [fileType=" + fileType + ", fromAgencyId=" + fromAgencyId + ", fileDate=" + fileDate
				+ ", fileTime=" + fileTime + ", recordCount=" + recordCount + ", custTagAgencyId=" + custTagAgencyId
				+ ", custTagSerial=" + custTagSerial + ", custLastName=" + custLastName + ", custFirstName="
				+ custFirstName + ", custMi=" + custMi + ", custCompany=" + custCompany + ", custAddr1=" + custAddr1
				+ ", custAddr2=" + custAddr2 + ", custCity=" + custCity + ", custState=" + custState + ", custZip="
				+ custZip + "]";
	}
	
	 
	
}
