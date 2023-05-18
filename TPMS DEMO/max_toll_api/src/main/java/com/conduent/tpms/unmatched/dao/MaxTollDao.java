package com.conduent.tpms.unmatched.dao;


import java.text.ParseException;
import java.util.List;
import com.conduent.tpms.unmatched.dto.THighwaySection;
import com.conduent.tpms.unmatched.model.APIInputs;
import com.conduent.tpms.unmatched.model.AgencyHoliday;
import com.conduent.tpms.unmatched.model.SessionIdInputs;
import com.conduent.tpms.unmatched.model.TollPriceSchedule;
import com.conduent.tpms.unmatched.model.TranDetail;

public interface MaxTollDao {

	
	public TranDetail getTrxnInfo(long lane_tx_id);
	
	public SessionIdInputs getSectionId(TranDetail tranDetail , APIInputs inputs);
	
	public THighwaySection getTerminalPlazaInfo(int sessionId);
	
	public List<AgencyHoliday> getAgencyHoliday();

	public Integer getPriceScheduleId(APIInputs inputs,TranDetail info ,  String daysInd) throws ParseException ;

	
	public Double getDiscountFare(TranDetail info , int terminalPlaza , int priceScheduleId,APIInputs inputs) throws ParseException ;
	
	public Double getDiscountFarePlaza2(TranDetail info , int terminalPlaza , int priceScheduleId,APIInputs inputs) throws ParseException ;
	

	public AgencyHoliday getAgencyHoliday(TranDetail info, APIInputs inputs);
	
	//TPMS
	public Integer getPriceScheduleIdTPMS(APIInputs info ,  String daysInd) throws ParseException ;
	
	public Double getDiscountFareTPMS(int terminalPlaza , int priceScheduleId,APIInputs inputs,TranDetail trandetailInfo) throws ParseException ;
	
	public Double getDiscountFarePlaza2TPMS(int terminalPlaza , int priceScheduleId,APIInputs inputs,TranDetail trandetailInfo) throws ParseException ;

	public TranDetail getTrnxInfo(Long laneTxId) throws ParseException;
	
}
