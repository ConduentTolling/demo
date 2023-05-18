package com.conduent.tpms.ictx.model;

import java.sql.Timestamp;

/**
 * Vehicle Class Model
 * 
 * @author deepeshb
 *
 */
public class VehicleClass 
{
	private Integer agencyClass;
	private String classDescription;
	private Integer baseAxleCount;
	private Integer extraAxleLb;
	private Integer extraAxleUb;
	private Timestamp updateTs;
	private Integer agencyId;
	private Integer vehicleType;
	private String isPrivate;
	private String externAgencyClass;
	public Integer getAgencyClass() {
		return agencyClass;
	}
	public void setAgencyClass(Integer agencyClass) {
		this.agencyClass = agencyClass;
	}
	public String getClassDescription() {
		return classDescription;
	}
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	public Integer getBaseAxleCount() {
		return baseAxleCount;
	}
	public void setBaseAxleCount(Integer baseAxleCount) {
		this.baseAxleCount = baseAxleCount;
	}
	public Integer getExtraAxleLb() {
		return extraAxleLb;
	}
	public void setExtraAxleLb(Integer extraAxleLb) {
		this.extraAxleLb = extraAxleLb;
	}
	public Integer getExtraAxleUb() {
		return extraAxleUb;
	}
	public void setExtraAxleUb(Integer extraAxleUb) {
		this.extraAxleUb = extraAxleUb;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}
	public String getExternAgencyClass() {
		return externAgencyClass;
	}
	public void setExternAgencyClass(String externAgencyClass) {
		this.externAgencyClass = externAgencyClass;
	}
}