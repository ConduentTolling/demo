package com.conduent.tpms.qatp.dao;

import java.util.List;

import com.conduent.tpms.qatp.model.TranDetail;
import com.google.gson.Gson;

public interface ITransDetailDao 
{
	public void saveTransDetail(TranDetail tranDetail);
	public List<Long> loadNextSeq(Integer numOfSeq) ;
	public void batchInsert(List<TranDetail> txlist);
	
}
