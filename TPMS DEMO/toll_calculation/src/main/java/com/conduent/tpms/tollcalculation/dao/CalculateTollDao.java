package com.conduent.tpms.tollcalculation.dao;

import java.text.ParseException;

import com.conduent.tpms.tollcalculation.model.APIInputs;
import com.conduent.tpms.tollcalculation.model.APIOutput;
import com.conduent.tpms.tollcalculation.model.AgencyHoliday;
import com.conduent.tpms.tollcalculation.model.TTranInputs;

public interface CalculateTollDao 
{
	public AgencyHoliday getAgencyHoliday(APIInputs inputs, TTranInputs ttinputs);
	
	public Integer getPriceScheduleId(APIInputs inputs, TTranInputs ttinputs, String daysInd);
	
	public APIOutput getDiscountFare(Integer priceScheduleId, APIInputs inputs, TTranInputs ttinputs) throws ParseException ;
	
	public TTranInputs getInfoFromTTranDetails(Long laneTxId);
	
	//for APIInputs only
	public AgencyHoliday getAgencyHolidayfromAPIInputs(APIInputs inputs);
	
	public Integer getPriceScheduleIdfromAPIInputs(APIInputs inputs, String daysInd);
	
	public APIOutput getDiscountFarefromAPIInputs(Integer priceScheduleId, APIInputs inputs) throws ParseException ;
}
