package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class Exclusion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 974376802303349683L;

	//@ApiModelProperty(value = "agencyId", dataType ="Integer", required = true, example = "1")
	private Integer agencyId;
	//@ApiModelProperty(value = "plazaId", dataType ="Integer", required = true, example = "72")
	private Integer plazaId;
	//@ApiModelProperty(value = "laneId", dataType ="Integer", required = true, example = "18003")
	private Integer laneId;
	//@ApiModelProperty(value = "laneMode", dataType ="Integer", required = true, example = "9")
	private Integer laneMode;
	//@ApiModelProperty(value = "violType", dataType ="Integer", required = true, example = "3")
	private Integer violType;
	//@ApiModelProperty(value = "tollRevenuType", dataType ="Integer", required = true, example = "9")
	private Integer tollRevenuType;
	//@ApiModelProperty(value = "exclusionStage", dataType ="Integer", required = true, example = "3")
	private Integer exclusionStage;
	//@ApiModelProperty(value = "etcAccountId", dataType ="Integer", required = true, example = "21736719")
	private Integer etcAccountId;
	//@ApiModelProperty(value = "plateState", dataType ="Integer", required = true, example = "NJ")
	private String plateState;
	//@ApiModelProperty(value = "plateNumber", dataType ="Integer", required = true, example = "NDL48Y")
	private String plateNumber;
	//@ApiModelProperty(value = "vehicleSpeed", dataType ="Integer", required = true, example = "12")
	private Integer vehicleSpeed;
	//@ApiModelProperty(value = "txTypeInd", dataType ="String", required = true, example = "V")
	private String txTypeInd;
	//@ApiModelProperty(value = "txSubtypeInd", dataType ="String", required = true, example = "F")
	private String txSubtypeInd;
	//@ApiModelProperty(value = "startDate", dataType ="LocalDateTime", required = true, example = "2021-04-11T14:00:12.065")
	private LocalDateTime startDate;
	//@ApiModelProperty(value = "endDate", dataType ="LocalDateTime", required = true, example = "2021-04-11T18:00:12.065")
	private LocalDateTime endDate;
	//@ApiModelProperty(value = "empId", dataType ="Integer", required = true, example = "12345682")
	private Integer empId;
	private String pelName;
	private String cscLookUp;
	private Timestamp updateTs;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	//@ApiModelProperty(value = "trxDateTime", dataType ="LocalDateTime", required = true, example = "2021-04-11T18:00:12.065")
	private LocalDateTime trxDateTime; 

	public Exclusion() {
		// TODO Auto-generated constructor stub
	}

	
	public Exclusion(Integer agencyId, Integer plazaId, Integer laneId, Integer laneMode, Integer violType,
			Integer tollRevenuType, Integer exclusionStage, Integer etcAccountId, String plateState, String plateNumber,
			Integer vehicleSpeed, String txTypeInd, String txSubtypeInd, LocalDateTime startDate, LocalDateTime endDate,
			Integer empId, String pelName, String cscLookUp, Timestamp updateTs) {
		super();
		this.agencyId = agencyId;
		this.plazaId = plazaId;
		this.laneId = laneId;
		this.laneMode = laneMode;
		this.violType = violType;
		this.tollRevenuType = tollRevenuType;
		this.exclusionStage = exclusionStage;
		this.etcAccountId = etcAccountId;
		this.plateState = plateState;
		this.plateNumber = plateNumber;
		this.vehicleSpeed = vehicleSpeed;
		this.txTypeInd = txTypeInd;
		this.txSubtypeInd = txSubtypeInd;
		this.startDate = startDate;
		this.endDate = endDate;
		this.empId = empId;
		this.pelName = pelName;
		this.cscLookUp = cscLookUp;
		this.updateTs = updateTs;
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

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public Integer getViolType() {
		return violType;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public Integer getTollRevenuType() {
		return tollRevenuType;
	}

	public void setTollRevenuType(Integer tollRevenuType) {
		this.tollRevenuType = tollRevenuType;
	}

	public Integer getExclusionStage() {
		return exclusionStage;
	}

	public void setExclusionStage(Integer exclusionStage) {
		this.exclusionStage = exclusionStage;
	}

	public Integer getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Integer etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public String getPlateState() {
		return plateState;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public String getTxTypeInd() {
		return txTypeInd;
	}

	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}

	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}

	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getPelName() {
		return pelName;
	}

	public void setPelName(String pelName) {
		this.pelName = pelName;
	}

	public String getCscLookUp() {
		return cscLookUp;
	}

	public void setCscLookUp(String cscLookUp) {
		this.cscLookUp = cscLookUp;
	}

	public Timestamp getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LocalDateTime getTrxDateTime() {
		return trxDateTime;
	}


	public void setTrxDateTime(LocalDateTime trxDateTime) {
		this.trxDateTime = trxDateTime;
	}


	@Override
	public String toString() {
		return "Exclusion [agencyId=" + agencyId + ", plazaId=" + plazaId + ", laneId=" + laneId + ", laneMode="
				+ laneMode + ", violType=" + violType + ", tollRevenuType=" + tollRevenuType + ", exclusionStage="
				+ exclusionStage + ", etcAccountId=" + etcAccountId + ", plateState=" + plateState + ", plateNumber="
				+ plateNumber + ", vehicleSpeed=" + vehicleSpeed + ", txTypeInd=" + txTypeInd + ", txSubtypeInd="
				+ txSubtypeInd + ", startDate=" + startDate + ", endDate=" + endDate + ", empId=" + empId + ", pelName="
				+ pelName + ", cscLookUp=" + cscLookUp + ", updateTs=" + updateTs + ", trxDateTime=" + trxDateTime
				+ "]";
	}
	
 
}
