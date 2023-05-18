package com.conduent.tpms.qatp.dao;

import java.util.List;

import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.FileDetailsDto;
import com.conduent.tpms.qatp.dto.PbFileStatsDto;
import com.conduent.tpms.qatp.dto.PbaDetailInfoVO;
import com.conduent.tpms.qatp.model.Transaction;

public interface IQatpDao {

	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDto);
	
	public Long insertAckStatus(AckFileInfoDto ackFileInfoDto);
	
	public Long insertPbFileStats(PbFileStatsDto pbFileStatsDto);

}
