package com.conduent.tpms.parking.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class AckFileInfoDto implements Serializable {

	private static final long serialVersionUID = -2987860971989977763L;

	private String ackFileName;

	private LocalDate ackFileDate;

	private String ackFileTime;

	private String ackFileStatus;

	private String trxFileName;

	private String reconFileName;

	private String fileType;

	private String fromAgency;

	private String toAgency;

	private Long externFileId;

	private Long atpFileId;
	

	public AckFileInfoDto() {
		super();
	}

	public String getAckFileName() {
		return ackFileName;
	}

	public void setAckFileName(String ackFileName) {
		this.ackFileName = ackFileName;
	}

	public LocalDate getAckFileDate() {
		return ackFileDate;
	}

	public void setAckFileDate(LocalDate ackFileDate) {
		this.ackFileDate = ackFileDate;
	}

	public String getAckFileTime() {
		return ackFileTime;
	}

	public void setAckFileTime(String ackFileTime) {
		this.ackFileTime = ackFileTime;
	}

	public String getAckFileStatus() {
		return ackFileStatus;
	}

	public void setAckFileStatus(String ackFileStatus) {
		this.ackFileStatus = ackFileStatus;
	}

	public String getTrxFileName() {
		return trxFileName;
	}

	public void setTrxFileName(String trxFileName) {
		this.trxFileName = trxFileName;
	}

	public String getReconFileName() {
		return reconFileName;
	}

	public void setReconFileName(String reconFileName) {
		this.reconFileName = reconFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFromAgency() {
		return fromAgency;
	}

	public void setFromAgency(String fromAgency) {
		this.fromAgency = fromAgency;
	}

	public String getToAgency() {
		return toAgency;
	}

	public void setToAgency(String toAgency) {
		this.toAgency = toAgency;
	}

	public Long getExternFileId() {
		return externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

}
