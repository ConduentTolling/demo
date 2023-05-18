package com.conduent.tpms.cams.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public class TripHistoryResponseDto {
	
	List<TTripHistory> tripHistoryResponseList;

	public List<TTripHistory> getTripHistoryResponseList() {
		return tripHistoryResponseList;
	}

	public void setTripHistoryResponseList(List<TTripHistory> tripHistoryResponseList) {
		this.tripHistoryResponseList = tripHistoryResponseList;
	}

}
