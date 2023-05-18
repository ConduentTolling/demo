package com.conduent.tpms.tollcalculation.service;

import java.text.ParseException;

import com.conduent.tpms.tollcalculation.model.APIInputs;
import com.conduent.tpms.tollcalculation.model.APIOutput;

public interface CalculateTollService 
{
	public APIOutput process(APIInputs inputs) throws ParseException;
}
