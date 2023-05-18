package com.conduent.tpms.cams.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.cams.dto.TTripHistory;

@Repository
public interface TripHistoryDao {
	
	public List<TTripHistory> fetchTripHistory(TTripHistory dto);
	
	public long fetchTripHistoryCount(TTripHistory dto);
	

}
