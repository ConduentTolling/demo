package com.conduent.tpms.iag.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IagFileStats {



	public IagFileStats() {
		super();
	}
	
	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	private String inputFileName;

	private String ictxFileName;
	
	private String icrxFileName;
	
	private String processedFlag;
	
	private String fileType;
	
	private String fileDate;
	
	private Long xferControlId;

	private Long atpFileId;

	private Long inputRecCount;

	private Long outputRecCount;

	private String fromAgency;

	private String toAgency;

	private LocalDateTime updateTs;

}
