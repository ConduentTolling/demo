package com.conduent.tpms.unmatched.service;

import java.text.ParseException;

import com.conduent.tpms.unmatched.model.APIInputs;

public interface GetMaxTollAmountCRMService {
	
	public double getMaxTollAmount(APIInputs inputs) throws ParseException;

}
