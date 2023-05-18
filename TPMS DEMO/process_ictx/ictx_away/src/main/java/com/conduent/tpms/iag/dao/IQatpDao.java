package com.conduent.tpms.iag.dao;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileDetailsDto;

public interface IQatpDao {

	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDto);
	
	public Long insertAckStatus(AckFileInfoDto ackFileInfoDto);

}
