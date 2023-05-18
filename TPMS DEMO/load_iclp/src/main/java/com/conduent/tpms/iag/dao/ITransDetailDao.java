package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.TranDetail;

public interface ITransDetailDao {

	public void saveTransDetail(TranDetail tranDetail);
	public List<Long> loadNextSeq(Integer numOfSeq) ;
	public void batchInsert(List<TranDetail> txlist);
 

}
