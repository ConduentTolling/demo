package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import com.conduent.tpms.qatp.validation.BaseVO;
/**
 * 
 * @author MAYURIG1
 *
 */
public class NystaHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String fileDate;
	private String fileTime;
	private String recordCount;
	private String recordTypeCode;
	private Integer fileSerial;
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getRecordTypeCode() {
		return recordTypeCode;
	}
	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}
	public Integer getFileSerial() {
		return fileSerial;
	}
	public void setFileSerial(Integer fileSerial) {
		this.fileSerial = fileSerial;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}