package com.conduent.tpms.qatp.dto;

import java.io.Serializable;


import com.conduent.tpms.qatp.validation.BaseVO;

public class MtaFileNameDetailVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8448937760682414500L;
	private String hostSystem;
	private String plazaId;
	private String fileDate;
	private String fileHour;
	private String numberOfFiles;
	private String fileExtension;
	public String getHostSystem() {
		return hostSystem;
	}
	public void setHostSystem(String hostSystem) {
		this.hostSystem = hostSystem;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileHour() {
		return fileHour;
	}
	public void setFileHour(String fileHour) {
		this.fileHour = fileHour;
	}
	public String getNumberOfFiles() {
		return numberOfFiles;
	}
	public void setNumberOfFiles(String numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	
	
}
