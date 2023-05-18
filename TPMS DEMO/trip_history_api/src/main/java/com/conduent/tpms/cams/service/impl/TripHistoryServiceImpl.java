package com.conduent.tpms.cams.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import com.conduent.tpms.cams.controller.TripHistoryApiController;
import com.conduent.tpms.cams.dao.TripHistoryDao;
import com.conduent.tpms.cams.dto.TTripHistory;
import com.conduent.tpms.cams.dto.TripHistoryResponseDto;
import com.conduent.tpms.cams.service.TripHistoryService;

@Service
public class TripHistoryServiceImpl{
	
	@Autowired
	TripHistoryDao tripHistoryDao;
	Page<BigDecimal> page;

	final Logger logger = LoggerFactory.getLogger(TripHistoryServiceImpl.class);
	
	public Page<TTripHistory> accountSearch(TTripHistory tripHistory) {
		
		TripHistoryResponseDto response=new TripHistoryResponseDto();
		TTripHistory tripHistoryDto=new TTripHistory();
		tripHistoryDto.setPage(tripHistory.getPage());
		tripHistoryDto.setSize(tripHistory.getSize());
		if(tripHistory.getEtcAccountId()!=null) {
			tripHistoryDto.setEtcAccountId(tripHistory.getEtcAccountId());
		}
		if(tripHistory.getApdId()!=null) {
			tripHistoryDto.setApdId(tripHistory.getApdId());
		}
		if(tripHistory.getTripStartDate()!=null) {
			tripHistoryDto.setTripStartDate(tripHistory.getTripStartDate());
		}
		if(tripHistory.getTripEndDate()!=null) {
			tripHistoryDto.setTripEndDate(tripHistory.getTripEndDate());
		}
		if(tripHistory.getStatus()!=null) {
			tripHistoryDto.setStatus(tripHistory.getStatus());
		}
		
		List<TTripHistory> tripHistoryList=tripHistoryDao.fetchTripHistory(tripHistory);
			if(!tripHistoryList.isEmpty())
			{
				response.setTripHistoryResponseList(tripHistoryList);
				logger.info("Response from Service Layer is: "+response);
			}
		
		//return response;
		return new PageImpl<>(tripHistoryList, PageRequest.of(tripHistory.getPage(), tripHistory.getSize()),tripHistoryDao.fetchTripHistoryCount(tripHistory));
	}

}
