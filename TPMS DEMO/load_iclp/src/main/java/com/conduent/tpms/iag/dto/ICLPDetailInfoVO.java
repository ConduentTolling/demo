package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ICLPDetailInfoVO implements Serializable {

	/**
	 * License plate file detail records
	 */
	
	private static final long serialVersionUID = 2798677267041309955L;
	

	private String licState;
	

	
	private String licNumber;
	
	private String licType;
	
	private String tagAgencyId;
	
	private String tagSerialNumber;
	
	private String deviceNo;
	
	private String filetype;
	
	private LocalDateTime licEffectiveFrom;
	
	

	private LocalDateTime licEffectiveTo;
	
	private String licHomeAgency;
	
	private String licAccntNo;
	
	private String licVin;
	
	private String licGuaranteed;
	
	private boolean isrecordvalid ;
	
	private String line;
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public boolean isIsrecordvalid() {
		return isrecordvalid;
	}

	public void setIsrecordvalid(boolean isrecordvalid) {
		this.isrecordvalid = isrecordvalid;
	}

	public ICLPDetailInfoVO() {
		
	}
	
	/*
	 * public ICLPDetailInfoVO(String licState, String licNumber, String licType,
	 * String deviceNo, String filetype) { super(); this.licState = licState;
	 * this.licNumber = licNumber; this.licType = licType; this.deviceNo = deviceNo;
	 * this.filetype = filetype; }
	 */
	
	public ICLPDetailInfoVO(String licState, String licNumber, String deviceNo, String licType,String  licAccntNo ) {
		super();
		this.licState = licState;
		this.licNumber = licNumber;
		this.deviceNo = deviceNo;
		this.licType = licType;
		this.licAccntNo= licAccntNo;
		
	}
	
	
	public ICLPDetailInfoVO(String licState, String licNumber, String licType, String deviceNo, 
			LocalDateTime licEffectiveFrom, LocalDateTime licEffectiveTo, String licHomeAgency, String licAccntNo,
			String licVin, String licGuaranteed , String filetype) {
		super();
		this.licState = licState;
		this.licNumber = licNumber;
		this.licType = licType;
		this.deviceNo = deviceNo;
		this.filetype = filetype;
		this.licEffectiveFrom = licEffectiveFrom;
		this.licEffectiveTo = licEffectiveTo;
		this.licHomeAgency = licHomeAgency;
		this.licAccntNo = licAccntNo;
		this.licVin = licVin;
		this.licGuaranteed = licGuaranteed;
	}



	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public String getLicState() {
		return licState;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public String getLicNumber() {
		return licNumber;
	}

	public void setLicNumber(String licNumber) {
		this.licNumber = licNumber;
	}

	public String getLicType() {
		return licType;
	}

	public void setLicType(String licType) {
		this.licType = licType;
	}

	public String getTagAgencyId() {
		return tagAgencyId;
	}

	public void setTagAgencyId(String tagAgencyId) {
		this.tagAgencyId = tagAgencyId;
	}

	public String getTagSerialNumber() {
		return tagSerialNumber;
	}

	public void setTagSerialNumber(String tagSerialNumber) {
		this.tagSerialNumber = tagSerialNumber;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	
	  @Override public String toString() { return "ICLPDetailInfoVO [licState=" +
	  licState + ", licNumber=" + licNumber + ", licType=" + licType +
	  ", tagAgencyId=" + deviceNo.substring(0, 4) + ", tagSerialNumber=" + deviceNo.subSequence(4, 14) +
	  ", deviceNo=" + deviceNo + ", filetype=" + filetype + ", licEffectiveFrom=" +
	  licEffectiveFrom + ", licEffectiveTo=" + licEffectiveTo + ", licHomeAgency="
	  + licHomeAgency + ", licAccntNo=" + licAccntNo + ", licVin=" + licVin +
	  ", licGuaranteed=" + licGuaranteed + "]"; }
	 

	
	
	public String toStringnew() {
		return ""+licState+""+licNumber+""+licType+""+deviceNo.substring(0, 4)+""+deviceNo.subSequence(4, 14)+""+dt(licEffectiveFrom)+""+dt(licEffectiveTo)+""+ licHomeAgency+""+licAccntNo+""+licVin+""+licGuaranteed+"";
	}
	
	public String record() {
		return ""+licState+""+licNumber+""+licType+""+deviceNo.substring(0, 4)+""+deviceNo.substring(4, 14)+""+dtfromfile(licEffectiveFrom)+""+dtfromfile(licEffectiveTo)+""+ licHomeAgency+""+licAccntNo+""+licVin+""+licGuaranteed+"";
	}

	public String recordfromtb() {
		return ""+licState+""+field(licNumber)+""+field(licType)+""+deviceNo.substring(0, 4)+""+deviceNo.subSequence(4, 14)+""+dtfromfile(licEffectiveFrom)+""+dtfromfile(licEffectiveTo)+""+ licHomeAgency+""+licAccntNo+""+licVin+""+licGuaranteed+"";
	}
	
	
	public String getLicHomeAgency() {
		return licHomeAgency;
	}

	public void setLicHomeAgency(String licHomeAgency) {
		this.licHomeAgency = licHomeAgency;
	}

	public String getLicAccntNo() {
		return licAccntNo;
	}

	public void setLicAccntNo(String licAccntNo) {
		this.licAccntNo = licAccntNo;
	}

	public String getLicVin() {
		return licVin;
	}

	public void setLicVin(String licVin) {
		this.licVin = licVin;
	}

	public String getLicGuaranteed() {
		return licGuaranteed;
	}

	public void setLicGuaranteed(String licGuaranteed) {
		this.licGuaranteed = licGuaranteed;
	}
	
	
	  public LocalDateTime getLicEffectiveFrom() { return licEffectiveFrom; }
	  
	  public void setLicEffectiveFrom(LocalDateTime licEffectiveFrom) {
	  this.licEffectiveFrom = licEffectiveFrom; }
	  
	  public LocalDateTime getLicEffectiveTo() { return licEffectiveTo; }
	  
	  public void setLicEffectiveTo(LocalDateTime licEffectiveTo) {
	  this.licEffectiveTo = licEffectiveTo; }
	 
	
	public String dt(LocalDateTime licdate) {
		String Licdate =null;
		
		if(licdate==null) {
			return null;
		}else {
		 Licdate =licdate.toString().concat("Z");
		
		
	}
	  return Licdate;
	  
	
	 
}
	
	public String field(String value) {
		
		
		if(value==null) {
			return null;
		}else {
			return value.trim();
		}
	}
	
	
	public String dtfromfile(LocalDateTime licdate) {
		
		LocalDateTime dt6 = LocalDateTime.now();
		LocalDate dt7 = dt6.toLocalDate();
		
		if(licdate.toLocalDate().equals(dt7)) {
			return "********************";
		}
		else
		{
			return licdate.toString().concat("Z");
		}
	}
	
	
}