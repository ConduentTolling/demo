package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.conduent.tpms.iag.validation.BaseVO;

/**
 * 
 * @author MAYURIG1
 *
 */
public class ITAGHeaderInfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489240691078090844L;
	private String fileType;
	private String version;
	private String fromAgencyId;
	private LocalDateTime filedatetime;
	private String recordCount;
	
	
	private LocalDate fileDate;
	private String fileTime;
	private String countstat1;
	private String countstat2;
	private String countstat3;
	private String countstat4;

	
	
  

	
	
	public String getFromAgencyId() {
		return fromAgencyId;
	}

	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public LocalDate getFileDate() {
		return fileDate;
	}

	public void setFileDate(LocalDate fileDate) {
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

	public String getCountstat1() {
		return countstat1;
	}

	public void setCountstat1(String countstat1) {
		this.countstat1 = countstat1;
	}

	public String getCountstat2() {
		return countstat2;
	}

	public void setCountstat2(String countstat2) {
		this.countstat2 = countstat2;
	}

	public String getCountstat3() {
		return countstat3;
	}

	public void setCountstat3(String countstat3) {
		this.countstat3 = countstat3;
	}

	public String getCountstat4() {
		return countstat4;
	}

	public void setCountstat4(String countstat4) {
		this.countstat4 = countstat4;
	}
	
	  public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
		
		public LocalDateTime getFiledatetime() {
			return filedatetime;
		}

		public void setFiledatetime(LocalDateTime filedatetime) {
			this.filedatetime = filedatetime;
		}

	
	
}
