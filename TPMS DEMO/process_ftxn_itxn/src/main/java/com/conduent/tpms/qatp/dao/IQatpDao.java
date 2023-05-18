package com.conduent.tpms.qatp.dao;

import java.util.List;

import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.FileDetailsDto;
import com.conduent.tpms.qatp.dto.TProcessParamterDto;

public interface IQatpDao {

	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDto);
	
	public Long insertAckStatus(AckFileInfoDto ackFileInfoDto);
	
	public List<TProcessParamterDto> getProcessParamtersList();

}
