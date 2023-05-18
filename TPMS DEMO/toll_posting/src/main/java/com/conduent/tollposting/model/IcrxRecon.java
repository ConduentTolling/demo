package com.conduent.tollposting.model;

import java.time.LocalDate;
import java.util.Date;

import com.conduent.tollposting.constant.TxStatus;

public class IcrxRecon 
{
	private Integer plazaAgencyId;
	private Long externFileId;
	private Long laneTxId;
	private String txExternRefNo;
	private Integer txModSeq;
	private TxStatus txStatus;
	private Integer accountAgenyId;
	private LocalDate postedDate;
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Integer getTxModSeq() {
		return txModSeq;
	}
	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}
	public TxStatus getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(TxStatus txStatus) {
		this.txStatus = txStatus;
	}
	public Integer getAccountAgenyId() {
		return accountAgenyId;
	}
	public void setAccountAgenyId(Integer accountAgenyId) {
		this.accountAgenyId = accountAgenyId;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	
	
}