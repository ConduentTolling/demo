package com.conduent.tpms.ictx.model;

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

//	XFER_CONTROL_ID
//	ICTX_FILE_NAME
//	ICRX_FILE_NAME
//	PROCESSED_FLAG
//	INPUT_COUNT
//	OUTPUT_COUNT
//	FROM_AGENCY
//	TO_AGENCY
//	FILE_DATE
//	UPDATE_TS
//	ATP_FILE_ID
//	FILE_SEQ_NUMBER
//	FILE_TYPE
//	DEPOSIT_ID
}
