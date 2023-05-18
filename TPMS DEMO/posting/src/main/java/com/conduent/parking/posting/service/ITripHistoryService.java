package com.conduent.parking.posting.service;

import java.time.LocalDate;
import java.util.List;

import com.conduent.parking.posting.model.TripHistory;

public interface ITripHistoryService 
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
}
