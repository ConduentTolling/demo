package com.conduent.tpms.cams.service;

import java.util.List;

import com.conduent.tpms.cams.dto.TTripHistory;

public interface TripHistoryService {
	
	public List<TTripHistory> fetchingTripHistory(TTripHistory tripHistory);

}
