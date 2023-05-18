package com.conduent.Ibtsprocessing.model;

import java.time.LocalDate;
import java.util.Date;

public class XferControl {
	
	private Long xferControlId;
	private LocalDate dateCreated;
	
	
	public XferControl() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public String toString() {
		return "XferControl [xferControlId=" + xferControlId + ", dateCreated=" + dateCreated + ", getXferControlId()="
				+ getXferControlId() + ", getDateCreated()=" + getDateCreated() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
