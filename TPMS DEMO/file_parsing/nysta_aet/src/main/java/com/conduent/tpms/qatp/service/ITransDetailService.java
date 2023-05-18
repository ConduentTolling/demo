package com.conduent.tpms.qatp.service;

import java.util.List;

import com.conduent.tpms.qatp.dto.TollCalculationResponseDto;
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.QatpStatistics;
import com.conduent.tpms.qatp.model.TranDetail;
import com.google.gson.Gson;

public interface ITransDetailService 
{
	public void saveTransDetail(TranDetail tranDetail);
	public List<Long> loadNextSeq(Integer numOfSeq) ;
	public void batchInsert(List<TranDetail> txlist);
	public void pushToOCR(List<TranDetail> txlist);
	public long publishMessages(List<TranDetail> txlist, List<AtpFileIdObject> atpFileIdList, Integer agencyIdNystaOrNysba) throws Exception;
	public List<AtpFileIdObject> getAtpFileId(Long xferControlId);

}
