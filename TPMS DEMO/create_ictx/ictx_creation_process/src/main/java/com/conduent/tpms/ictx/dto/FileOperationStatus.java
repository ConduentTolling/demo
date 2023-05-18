package com.conduent.tpms.ictx.dto;

import java.util.List;

import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * FILE OPERATION META INFO
 * 
 * @author deepeshb
 *
 */
public class FileOperationStatus {

	boolean operationSuccessFlag = true;

	private List<AwayTransaction> iagStatusRecordList;

	private String fromAgency;

	private String toAgency;

	private String fileName;

	private String tempFileName;

	private boolean noRecordFlag = false;

	private boolean existingFileFlag;

	private Long existingRecordCount = 0L;

	public boolean isOperationSuccessFlag() {
		return operationSuccessFlag;
	}

	public void setOperationSuccessFlag(boolean operationFlag) {
		this.operationSuccessFlag = operationFlag;
	}

	public List<AwayTransaction> getIagStatusRecordList() {
		return iagStatusRecordList;
	}

	public void setIagStatusRecordList(List<AwayTransaction> iagStatusRecordList) {
		this.iagStatusRecordList = iagStatusRecordList;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTempFileName() {
		return tempFileName;
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public boolean isNoRecordFlag() {
		return noRecordFlag;
	}

	public void setNoRecordFlag(boolean noRecordFlag) {
		this.noRecordFlag = noRecordFlag;
	}

	public boolean isExistingFileFlag() {
		return existingFileFlag;
	}

	public void setExistingFileFlag(boolean existingFileFlag) {
		this.existingFileFlag = existingFileFlag;
	}

	public Long getExistingRecordCount() {
		return existingRecordCount;
	}

	public void setExistingRecordCount(Long existingRecordCount) {
		this.existingRecordCount = existingRecordCount;
	}

	@Override
	public String toString() {
		return "FileOperationStatus [operationSuccessFlag=" + operationSuccessFlag + ", iagStatusRecordList="
				+ iagStatusRecordList + ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileName="
				+ fileName + ", tempFileName=" + tempFileName + ", noRecordFlag=" + noRecordFlag + ", existingFileFlag="
				+ existingFileFlag + ", existingRecordCount=" + existingRecordCount + "]";
	}

}
