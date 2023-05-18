package com.conduent.tpms.unmatched.dao;

import java.text.ParseException;

import com.conduent.tpms.unmatched.model.TUnmatchedEntryTxRequestModel;

public interface UpdateUnmatchedEntryDao {

	public Integer updateUnmatchedEntryDaoDetails(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest)
			throws ParseException;

}
