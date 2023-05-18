package com.conduent.tpms.qatp.dto;

import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.utility.Convertor;

public class PaTxDto {

	private String agTrxSerialNum;
	private String agRevenuDate;
	private String agFacAgency;
	private String agTrxType;
	private String agTrxDate;
	private String agTrxTime;
	private String agTrxPlaza;
	private String agTrxLane;
	private String agTrxLaneSeq;
	private String agTagAgency;
	private String agTagSerialNumber;
	private String agReadPerformance;
	private String agWritePerf;
	private String agReadType;
	private String agTagPgmStatus;
	private String agLaneMode;
	private String agValidationStatus;
	private String agLicState;
	private String agLicNumber;
	private String agLicType;
	private String agConfidence;
	private String agTagClass;
	private String agTagExtraAxles;
	private String agAvcClass;
	private String agAvcExtraAxles;
	private String agMismatch;
	private String agClassCharged;
	private String agChargedExtraAxles;
	private String agPlanCharged;
	private String agExitSpeed;
	private String agOverSpeed;
	private String agEzpassAmount;
	private String agEzpassAmountDs1;
	private String agEzpassAmountDs2;
	private String agVideoAmount;
	private String delimiter;

	public String getAgTrxSerialNum() {
		return agTrxSerialNum;
	}

	public void setAgTrxSerialNum(String agTrxSerialNum) {
		this.agTrxSerialNum = agTrxSerialNum;
	}

	public String getAgRevenuDate() {
		return agRevenuDate;
	}

	public void setAgRevenuDate(String agRevenuDate) {
		this.agRevenuDate = agRevenuDate;
	}

	public String getAgFacAgency() {
		return agFacAgency;
	}

	public void setAgFacAgency(String agFacAgency) {
		this.agFacAgency = agFacAgency;
	}

	public String getAgTrxType() {
		return agTrxType;
	}

	public void setAgTrxType(String agTrxType) {
		this.agTrxType = agTrxType;
	}

	public String getAgTrxDate() {
		return agTrxDate;
	}

	public void setAgTrxDate(String agTrxDate) {
		this.agTrxDate = agTrxDate;
	}

	public String getAgTrxTime() {
		return agTrxTime;
	}

	public void setAgTrxTime(String agTrxTime) {
		this.agTrxTime = agTrxTime;
	}

	public String getAgTrxPlaza() {
		return agTrxPlaza;
	}

	public void setAgTrxPlaza(String agTrxPlaza) {
		this.agTrxPlaza = agTrxPlaza;
	}

	public String getAgTrxLane() {
		return agTrxLane;
	}

	public void setAgTrxLane(String agTrxLane) {
		this.agTrxLane = agTrxLane;
	}

	public String getAgTrxLaneSeq() {
		return agTrxLaneSeq;
	}

	public void setAgTrxLaneSeq(String agTrxLaneSeq) {
		this.agTrxLaneSeq = agTrxLaneSeq;
	}

	public String getAgTagAgency() {
		return agTagAgency;
	}

	public void setAgTagAgency(String agTagAgency) {
		this.agTagAgency = agTagAgency;
	}

	public String getAgTagSerialNumber() {
		return agTagSerialNumber;
	}

	public void setAgTagSerialNumber(String agTagSerialNumber) {
		this.agTagSerialNumber = agTagSerialNumber;
	}

	public String getAgReadPerformance() {
		return agReadPerformance;
	}

	public void setAgReadPerformance(String agReadPerformance) {
		this.agReadPerformance = agReadPerformance;
	}

	public String getAgWritePerf() {
		return agWritePerf;
	}

	public void setAgWritePerf(String agWritePerf) {
		this.agWritePerf = agWritePerf;
	}

	public String getAgReadType() {
		return agReadType;
	}

	public void setAgReadType(String agReadType) {
		this.agReadType = agReadType;
	}

	public String getAgTagPgmStatus() {
		return agTagPgmStatus;
	}

	public void setAgTagPgmStatus(String agTagPgmStatus) {
		this.agTagPgmStatus = agTagPgmStatus;
	}

	public String getAgLaneMode() {
		return agLaneMode;
	}

	public void setAgLaneMode(String agLaneMode) {
		this.agLaneMode = agLaneMode;
	}

	public String getAgValidationStatus() {
		return agValidationStatus;
	}

	public void setAgValidationStatus(String agValidationStatus) {
		this.agValidationStatus = agValidationStatus;
	}

	public String getAgLicState() {
		return agLicState;
	}

	public void setAgLicState(String agLicState) {
		this.agLicState = agLicState;
	}

	public String getAgLicNumber() {
		return agLicNumber;
	}

	public void setAgLicNumber(String agLicNumber) {
		this.agLicNumber = agLicNumber;
	}

	public String getAgLicType() {
		return agLicType;
	}

	public void setAgLicType(String agLicType) {
		this.agLicType = agLicType;
	}

	public String getAgConfidence() {
		return agConfidence;
	}

	public void setAgConfidence(String agConfidence) {
		this.agConfidence = agConfidence;
	}

	public String getAgTagClass() {
		return agTagClass;
	}

	public void setAgTagClass(String agTagClass) {
		this.agTagClass = agTagClass;
	}

	public String getAgTagExtraAxles() {
		return agTagExtraAxles;
	}

	public void setAgTagExtraAxles(String agTagExtraAxles) {
		this.agTagExtraAxles = agTagExtraAxles;
	}

	public String getAgAvcClass() {
		return agAvcClass;
	}

	public void setAgAvcClass(String agAvcClass) {
		this.agAvcClass = agAvcClass;
	}

	public String getAgAvcExtraAxles() {
		return agAvcExtraAxles;
	}

	public void setAgAvcExtraAxles(String agAvcExtraAxles) {
		this.agAvcExtraAxles = agAvcExtraAxles;
	}

	public String getAgMismatch() {
		return agMismatch;
	}

	public void setAgMismatch(String agMismatch) {
		this.agMismatch = agMismatch;
	}

	public String getAgClassCharged() {
		return agClassCharged;
	}

	public void setAgClassCharged(String agClassCharged) {
		this.agClassCharged = agClassCharged;
	}

	public String getAgChargedExtraAxles() {
		return agChargedExtraAxles;
	}

	public void setAgChargedExtraAxles(String agChargedExtraAxles) {
		this.agChargedExtraAxles = agChargedExtraAxles;
	}

	public String getAgPlanCharged() {
		return agPlanCharged;
	}

	public void setAgPlanCharged(String agPlanCharged) {
		this.agPlanCharged = agPlanCharged;
	}

	public String getAgExitSpeed() {
		return agExitSpeed;
	}

	public void setAgExitSpeed(String agExitSpeed) {
		this.agExitSpeed = agExitSpeed;
	}

	public String getAgOverSpeed() {
		return agOverSpeed;
	}

	public void setAgOverSpeed(String agOverSpeed) {
		this.agOverSpeed = agOverSpeed;
	}

	public String getAgEzpassAmount() {
		return agEzpassAmount;
	}

	public void setAgEzpassAmount(String agEzpassAmount) {
		this.agEzpassAmount = agEzpassAmount;
	}

	public String getAgEzpassAmountDs1() {
		return agEzpassAmountDs1;
	}

	public void setAgEzpassAmountDs1(String agEzpassAmountDs1) {
		this.agEzpassAmountDs1 = agEzpassAmountDs1;
	}

	public String getAgEzpassAmountDs2() {
		return agEzpassAmountDs2;
	}

	public void setAgEzpassAmountDs2(String agEzpassAmountDs2) {
		this.agEzpassAmountDs2 = agEzpassAmountDs2;
	}

	public String getAgVideoAmount() {
		return agVideoAmount;
	}

	public void setAgVideoAmount(String agVideoAmount) {
		this.agVideoAmount = agVideoAmount;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

}
