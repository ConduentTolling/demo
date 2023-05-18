package com.conduent.tpms.inserttable.service;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.TLaneRequestModel;

public interface TLaneService {

	 public Integer insertTLane(TLaneRequestModel tLaneRequestModel) throws ParseException;

}
