package com.conduent.tpms.process25a.model;

import java.sql.Timestamp;

public class Pending25ATransactions {

	private static final long serialVersionUID = 1L;
	
	private String etcAccountId;
	private String deviceNo;
	private String plateNo;
	private String plateState;
	private String laneTxId;
	private String entryPlazaId; 
	private String plazaId;
	private Timestamp entryTimeStamp;	
	private String isReciprocityTxn;
	private Timestamp txnTimeStamp;
	private int waitDays;
	
	private int matchRefNo;
	
	@Override
	public String toString() {
		return "Pending25ATransactions [etcAccountId=" + etcAccountId + ", deviceNo=" + deviceNo + ", plateNo="
				+ plateNo + ", plateState=" + plateState + ", laneTxId=" + laneTxId + ", entryPlazaId=" + entryPlazaId
				+ ", plazaId=" + plazaId + ", entryTimeStamp=" + entryTimeStamp + ", isReciprocityTxn="
				+ isReciprocityTxn + ", txnTimeStamp=" + txnTimeStamp + ", waitDays=" + waitDays + ", matchRefNo=" + matchRefNo + "]";
	}
	
	
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(Timestamp entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public String isReciprocityTxn() {
		return isReciprocityTxn;
	}
	public void setReciprocityTxn(String isReciprocityTxn) {
		this.isReciprocityTxn = isReciprocityTxn;
	}
	public Timestamp getTxnTimeStamp() {
		return txnTimeStamp;
	}
	public void setTxnTimeStamp(Timestamp txnTimeStamp) {
		this.txnTimeStamp = txnTimeStamp;
	}


	public int getWaitDays() {
		return waitDays;
	}


	public void setWaitDays(int waitDays) {
		this.waitDays = waitDays;
	}


	public int getMatchRefNo() {
		return matchRefNo;
	}


	public void setMatchRefNo(int matchRefNo) {
		this.matchRefNo = matchRefNo;
	}

	
	
}
