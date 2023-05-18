package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TFOFileStats implements Serializable {

		private Long fileControlId;
		private String fileName;
		private String  distribFileName ;
		private String  processedFlag ;
		private Integer inputCount;
		private Integer outputCount;
		private String fromAgency;
		private String toAgency;
		private Date fileDate;
		private String fileSeqNum;
		private String fileType;
		private Timestamp updateTs;
		
		public Long getFileControlId() {
			return fileControlId;
		}
		public void setFileControlId(Long fileControlId) {
			this.fileControlId = fileControlId;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getDistribFileName() {
			return distribFileName;
		}
		public void setDistribFileName(String distribFileName) {
			this.distribFileName = distribFileName;
		}
		public String getProcessedFlag() {
			return processedFlag;
		}
		public void setProcessedFlag(String processedFlag) {
			this.processedFlag = processedFlag;
		}
		public Integer getInputCount() {
			return inputCount;
		}
		public void setInputCount(Integer inputCount) {
			this.inputCount = inputCount;
		}
		public Integer getOutputCount() {
			return outputCount;
		}
		public void setOutputCount(Integer outputCount) {
			this.outputCount = outputCount;
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
		public Date getFileDate() {
			return fileDate;
		}
		public void setFileDate(Date fileDate) {
			this.fileDate = fileDate;
		}
		public String getFileSeqNum() {
			return fileSeqNum;
		}
		public void setFileSeqNum(String fileSeqNum) {
			this.fileSeqNum = fileSeqNum;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		public Timestamp getUpdateTs() {
			return updateTs;
		}
		public void setUpdateTs(Timestamp updateTs) {
			this.updateTs = updateTs;
		}
		
		
		@Override
		public String toString() {
			return "TFOFileStats [fileControlId=" + fileControlId + ", fileName=" + fileName + ", distribFileName="
					+ distribFileName + ", processedFlag=" + processedFlag + ", inputCount=" + inputCount + ", outputCount="
					+ outputCount + ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileDate=" + fileDate
					+ ", fileSeqNum=" + fileSeqNum + ", fileType=" + fileType + ", updateTs=" + updateTs + "]";
		}


//	private static final long serialVersionUID = -2987860971989977763L;
//
//	private String fntxFileName;
//	private String ftnxFileName;
//	private String intxFileName;
//	private String itnxFileName;
//	private String fndxFileName;
//	private String fdnxFileName;
//	private String fnrxFileName;
//	private String frxnFileName;
//	private String inrxFileName;
//	private String irxnFileName;
//	private String xferControlId;
//	private String atpFileId;
//
//	public String getFntxFileName() {
//		return fntxFileName;
//	}
//
//	public void setFntxFileName(String fntxFileName) {
//		this.fntxFileName = fntxFileName;
//	}
//
//	public String getFtnxFileName() {
//		return ftnxFileName;
//	}
//
//	public void setFtnxFileName(String ftnxFileName) {
//		this.ftnxFileName = ftnxFileName;
//	}
//
//	public String getIntxFileName() {
//		return intxFileName;
//	}
//
//	public void setIntxFileName(String intxFileName) {
//		this.intxFileName = intxFileName;
//	}
//
//	public String getItnxFileName() {
//		return itnxFileName;
//	}
//
//	public void setItnxFileName(String itnxFileName) {
//		this.itnxFileName = itnxFileName;
//	}
//
//	public String getFndxFileName() {
//		return fndxFileName;
//	}
//
//	public void setFndxFileName(String fndxFileName) {
//		this.fndxFileName = fndxFileName;
//	}
//
//	public String getFdnxFileName() {
//		return fdnxFileName;
//	}
//
//	public void setFdnxFileName(String fdnxFileName) {
//		this.fdnxFileName = fdnxFileName;
//	}
//
//	public String getFnrxFileName() {
//		return fnrxFileName;
//	}
//
//	public void setFnrxFileName(String fnrxFileName) {
//		this.fnrxFileName = fnrxFileName;
//	}
//
//	public String getFrxnFileName() {
//		return frxnFileName;
//	}
//
//	public void setFrxnFileName(String frxnFileName) {
//		this.frxnFileName = frxnFileName;
//	}
//
//	public String getInrxFileName() {
//		return inrxFileName;
//	}
//
//	public void setInrxFileName(String inrxFileName) {
//		this.inrxFileName = inrxFileName;
//	}
//
//	public String getIrxnFileName() {
//		return irxnFileName;
//	}
//
//	public void setIrxnFileName(String irxnFileName) {
//		this.irxnFileName = irxnFileName;
//	}
//
//	public String getXferControlId() {
//		return xferControlId;
//	}
//
//	public void setXferControlId(String xferControlId) {
//		this.xferControlId = xferControlId;
//	}
//
//	public String getAtpFileId() {
//		return atpFileId;
//	}
//
//	public void setAtpFileId(String atpFileId) {
//		this.atpFileId = atpFileId;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//	@Override
//	public String toString() {
//		return "tFOFileStats [fntxFileName=" + fntxFileName + ", ftnxFileName=" + ftnxFileName + ", intxFileName="
//				+ intxFileName + ", itnxFileName=" + itnxFileName + ", fndxFileName=" + fndxFileName + ", fdnxFileName="
//				+ fdnxFileName + ", fnrxFileName=" + fnrxFileName + ", frxnFileName=" + frxnFileName + ", inrxFileName="
//				+ inrxFileName + ", irxnFileName=" + irxnFileName + ", xferControlId=" + xferControlId + ", atpFileId="
//				+ atpFileId + "]";
//	}
//
}
