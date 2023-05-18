package com.conduent.tpms.iag.dao;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileDetailsDto;
import com.conduent.tpms.iag.dto.IaFileStats;
import com.conduent.tpms.iag.model.XferControl;

public interface TIagDao {

	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDto);
	
	public Long insertAckStatus(AckFileInfoDto ackFileInfoDto);
	
	public AckFileInfoDto getAckFileNameInfo(String ackFileName);
	
	public XferControl getXferControlInfo(String xferFileName);
	
	public IaFileStats getIaFileStatsInfo(String iaStatsFileName);
	
	public Long checkAtpFileInStats(AckFileInfoDto ackFileInfoDto);

}
