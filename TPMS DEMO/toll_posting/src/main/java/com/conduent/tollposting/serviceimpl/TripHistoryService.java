package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ITripHistoryDao;
import com.conduent.tollposting.model.TripHistory;
import com.conduent.tollposting.service.ITripHistoryService;

@Service
public class TripHistoryService implements ITripHistoryService 
{
	@Autowired
	private ITripHistoryDao tripHistoryDao;
	
	/**Get trip history record where trx date before trip end date & unreconciled**/
	
	@Override
	public TripHistory getTripHistoryStartEndDate(String apdId, LocalDate trxDate) {
		// TODO Auto-generated method stub
		return tripHistoryDao.getTripHistoryStartEndDate(apdId, trxDate);
	}
	
	@Override
	public TripHistory getTripHistory(String apdId, LocalDate trxDate) 
	{
		return tripHistoryDao.getTripHistory(apdId, trxDate);
	}

	@Override
	public List<TripHistory> getTripHistory(String apdId,LocalDate txDate,List<String> reconFlag) 
	{
		return tripHistoryDao.getTripHistory(apdId, txDate, reconFlag);
	}

	@Override
	public TripHistory getTripHistory(String apdId, LocalDate txDate, Long suspDay) {
		return tripHistoryDao.getTripHistory(apdId, txDate, suspDay);
	}

	@Override
	public TripHistory getTripHistoryBeforeEndDate(String apdId, LocalDate txDate, Long suspDay) {
		// TODO Auto-generated method stub
		return tripHistoryDao.getTripHistoryBeforeEndDate(apdId, txDate, suspDay);
	}

	@Override
	public void updateLateTrip(TripHistory history) {
		tripHistoryDao.updateLateTrip(history);
		
	}

	@Override
	public TripHistory getTripHistory(String apdId, String reconFlag) {
		return tripHistoryDao.getTripHistory(apdId, reconFlag);
	}

	@Override
	public void insertTripHistory(TripHistory history) {
		tripHistoryDao.insertTripHistory(history);
		
	}

	//Q4. update the trip is (upd_trip_end_dt ==1)
	@Override
	public void updateLateTripEnd_1(TripHistory history) {
		tripHistoryDao.updateLateTripEnd_1(history);
		
	}
	//Q5. update the trip is (upd_trip_end_dt ==0)
	@Override
	public void updateLateTripEnd_2(TripHistory history) {
		tripHistoryDao.updateLateTripEnd_2(history);
		
	}

	public LocalDate getLatestTripForApdId(String apdId) {
		return tripHistoryDao.getLatestTripfromApdId(apdId);
	}

	@Override
	public int getAnniversaryDom(Long etcAccountId) 
	{
		return tripHistoryDao.getAnniversaryDom(etcAccountId);
	}

	@Override
	public String getApdId(Long etcAccountId) 
	{
		return tripHistoryDao.getApdId(etcAccountId);
	}

	
}
