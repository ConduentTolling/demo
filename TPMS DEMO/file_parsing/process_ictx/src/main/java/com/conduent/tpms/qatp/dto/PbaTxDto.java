package com.conduent.tpms.qatp.dto;

import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.utility.Convertor;

public class PbaTxDto {

	private String etcTrxSerialNum;
	private String etcRevenueDate;
	private String etcFacAgency;
	private String etcTrxType;
	private String etcEntryDate;
	private String etcEntryTime;
	private String etcEntryPlaza;
	private String etcEntryLane;
	private String etcTagAgency;
	private String etcTagSerialNumber;
	private String etcReadPerformance;
	private String etcWritePerf;
	private String etcTagPgmStatus;
	private String etcLaneMode;
	private String etcValidationStatus;
	private String etcLicState;
	private String etcLicNumber;
	private String etcClassCharged;
	private String etcActualAxles;
	private String etcExitSpeed;
	private String etcOverSpeed;
	private String etcExitDate;
	private String etcExitTime;
	private String etcExitPlaza;
	private String etcExitLane;
	private String etcDebitCredit;
	private String etcTollAmount;
	private String delimiter;
	
	public String getEtcTrxSerialNum() {
		return etcTrxSerialNum;
	}
	public void setEtcTrxSerialNum(String etcTrxSerialNum) {
		this.etcTrxSerialNum = etcTrxSerialNum;
	}
	public String getEtcRevenueDate() {
		return etcRevenueDate;
	}
	public void setEtcRevenueDate(String etcRevenueDate) {
		this.etcRevenueDate = etcRevenueDate;
	}
	public String getEtcFacAgency() {
		return etcFacAgency;
	}
	public void setEtcFacAgency(String etcFacAgency) {
		this.etcFacAgency = etcFacAgency;
	}
	public String getEtcTrxType() {
		return etcTrxType;
	}
	public void setEtcTrxType(String etcTrxType) {
		this.etcTrxType = etcTrxType;
	}
	public String getEtcEntryDate() {
		return etcEntryDate;
	}
	public void setEtcEntryDate(String etcEntryDate) {
		this.etcEntryDate = etcEntryDate;
	}
	public String getEtcEntryTime() {
		return etcEntryTime;
	}
	public void setEtcEntryTime(String etcEntryTime) {
		this.etcEntryTime = etcEntryTime;
	}
	public String getEtcEntryPlaza() {
		return etcEntryPlaza;
	}
	public void setEtcEntryPlaza(String etcEntryPlaza) {
		this.etcEntryPlaza = etcEntryPlaza;
	}
	public String getEtcEntryLane() {
		return etcEntryLane;
	}
	public void setEtcEntryLane(String etcEntryLane) {
		this.etcEntryLane = etcEntryLane;
	}
	public String getEtcTagAgency() {
		return etcTagAgency;
	}
	public void setEtcTagAgency(String etcTagAgency) {
		this.etcTagAgency = etcTagAgency;
	}
	public String getEtcTagSerialNumber() {
		return etcTagSerialNumber;
	}
	public void setEtcTagSerialNumber(String etcTagSerialNumber) {
		this.etcTagSerialNumber = etcTagSerialNumber;
	}
	public String getEtcReadPerformance() {
		return etcReadPerformance;
	}
	public void setEtcReadPerformance(String etcReadPerformance) {
		this.etcReadPerformance = etcReadPerformance;
	}
	public String getEtcWritePerf() {
		return etcWritePerf;
	}
	public void setEtcWritePerf(String etcWritePerf) {
		this.etcWritePerf = etcWritePerf;
	}
	public String getEtcTagPgmStatus() {
		return etcTagPgmStatus;
	}
	public void setEtcTagPgmStatus(String etcTagPgmStatus) {
		this.etcTagPgmStatus = etcTagPgmStatus;
	}
	public String getEtcLaneMode() {
		return etcLaneMode;
	}
	public void setEtcLaneMode(String etcLaneMode) {
		this.etcLaneMode = etcLaneMode;
	}
	public String getEtcValidationStatus() {
		return etcValidationStatus;
	}
	public void setEtcValidationStatus(String etcValidationStatus) {
		this.etcValidationStatus = etcValidationStatus;
	}
	public String getEtcLicState() {
		return etcLicState;
	}
	public void setEtcLicState(String etcLicState) {
		this.etcLicState = etcLicState;
	}
	public String getEtcLicNumber() {
		return etcLicNumber;
	}
	public void setEtcLicNumber(String etcLicNumber) {
		this.etcLicNumber = etcLicNumber;
	}
	public String getEtcClassCharged() {
		return etcClassCharged;
	}
	public void setEtcClassCharged(String etcClassCharged) {
		this.etcClassCharged = etcClassCharged;
	}
	public String getEtcActualAxles() {
		return etcActualAxles;
	}
	public void setEtcActualAxles(String etcActualAxles) {
		this.etcActualAxles = etcActualAxles;
	}
	public String getEtcExitSpeed() {
		return etcExitSpeed;
	}
	public void setEtcExitSpeed(String etcExitSpeed) {
		this.etcExitSpeed = etcExitSpeed;
	}
	public String getEtcOverSpeed() {
		return etcOverSpeed;
	}
	public void setEtcOverSpeed(String etcOverSpeed) {
		this.etcOverSpeed = etcOverSpeed;
	}
	public String getEtcExitDate() {
		return etcExitDate;
	}
	public void setEtcExitDate(String etcExitDate) {
		this.etcExitDate = etcExitDate;
	}
	public String getEtcExitTime() {
		return etcExitTime;
	}
	public void setEtcExitTime(String etcExitTime) {
		this.etcExitTime = etcExitTime;
	}
	public String getEtcExitPlaza() {
		return etcExitPlaza;
	}
	public void setEtcExitPlaza(String etcExitPlaza) {
		this.etcExitPlaza = etcExitPlaza;
	}
	public String getEtcExitLane() {
		return etcExitLane;
	}
	public void setEtcExitLane(String etcExitLane) {
		this.etcExitLane = etcExitLane;
	}
	public String getEtcDebitCredit() {
		return etcDebitCredit;
	}
	public void setEtcDebitCredit(String etcDebitCredit) {
		this.etcDebitCredit = etcDebitCredit;
	}
	public String getEtcTollAmount() {
		return etcTollAmount;
	}
	public void setEtcTollAmount(String etcTollAmount) {
		this.etcTollAmount = etcTollAmount;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	
}
