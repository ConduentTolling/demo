package com.conduent.tpms.cams.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.conduent.tpms.cams.dto.TPMSGateWay;
import com.conduent.tpms.cams.dto.TTripHistory;
import com.conduent.tpms.cams.dto.TripHistoryResponseDto;
import com.conduent.tpms.cams.service.impl.TripHistoryServiceImpl;

@RestController
@RequestMapping("/api")
public class TripHistoryApiController {

	@Autowired
	TripHistoryServiceImpl tripHistoryService;
	
	@PostMapping(value = "/fetch-trip-history", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE })
		public TPMSGateWay<?> getTripHistoryDetails(
//				@RequestParam(required = false) Integer page,
//			@RequestParam(required = false) Integer size,
			@RequestBody TTripHistory tripHistory) {
		
//		tripHistory.setPage(page != null ?page:0);
//		tripHistory.setSize(null != size?size:10);
		
		if (tripHistory.getSize() == 0) {
			tripHistory.setSize(10);
		}
		
		Page<TTripHistory> result = tripHistoryService.accountSearch(tripHistory);
	  
		return new TPMSGateWay<>(true, HttpStatus.OK, result);

	}
}
