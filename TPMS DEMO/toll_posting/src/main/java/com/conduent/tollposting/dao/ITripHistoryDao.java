package com.conduent.tollposting.dao;

import java.time.LocalDate;
import java.util.List;
import com.conduent.tollposting.model.TripHistory;

public interface ITripHistoryDao 
{
	public TripHistory getTripHistoryStartEndDate(String apdId, LocalDate trxDate);
	public TripHistory getTripHistory(String apdId,LocalDate trxDate);
	public List<TripHistory> getTripHistory(String apdId,LocalDate txDate,List<String> reconFlag);
	public TripHistory getTripHistory(String apdId,LocalDate txDate,Long suspDay);
	public TripHistory getTripHistoryBeforeEndDate(String apdId,LocalDate txDate,Long suspDay);
	public void updateLateTrip(TripHistory history);
	public TripHistory getTripHistory(String apdId,String reconFlag);
	public void insertTripHistory(TripHistory history);
	public void updateLateTripEnd_1(TripHistory history);
	public void updateLateTripEnd_2(TripHistory history);
	public LocalDate getLatestTripfromApdId(String apdId);
	public int getAnniversaryDom(Long etcAccountId);
	public String getApdId(Long etcAccountId);
}
