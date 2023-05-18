package com.conduent.tpms.iag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.model.TranDetail;
import com.google.gson.Gson;

public interface ITransDetailService 
{
	public void saveTransDetail(TranDetail tranDetail);
	public List<Long> loadNextSeq(Integer numOfSeq) ;
	public void batchInsert(List<TranDetail> txlist);
	public void pushToOCR(List<TranDetail> txlist,Gson gson);
	public long publishMessages(List<TranDetail> txlist, Gson gson);

}
