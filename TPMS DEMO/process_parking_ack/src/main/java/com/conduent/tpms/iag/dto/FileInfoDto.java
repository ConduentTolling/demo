package com.conduent.tpms.iag.dto;

public class FileInfoDto {

	String srcDir;

	String procDir;

	String unProcDir;

	String ackDir;

	String workingDir;

	public String getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	public String getProcDir() {
		return procDir;
	}

	public void setProcDir(String procDir) {
		this.procDir = procDir;
	}

	public String getUnProcDir() {
		return unProcDir;
	}

	public void setUnProcDir(String unProcDir) {
		this.unProcDir = unProcDir;
	}

	public String getAckDir() {
		return ackDir;
	}

	public void setAckDir(String ackDir) {
		this.ackDir = ackDir;
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

}
