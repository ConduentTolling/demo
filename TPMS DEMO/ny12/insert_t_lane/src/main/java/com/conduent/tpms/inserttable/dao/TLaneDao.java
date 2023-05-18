package com.conduent.tpms.inserttable.dao;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.TLaneRequestModel;


public interface TLaneDao {
	
	public Integer insertTLaneDao(TLaneRequestModel tLaneRequestModel) throws ParseException;

}
