package com.conduent.tpms.unmatched.service;

import java.text.ParseException;

import com.conduent.tpms.unmatched.model.TUnmatchedEntryTxRequestModel;

public interface UpdateUnmatchedEntryService {

	public Integer updateUnmatchedEntryDetails(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequestModel)
			throws ParseException;

}
