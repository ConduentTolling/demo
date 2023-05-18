package com.conduent.tpms.qatp.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.impl.ExclusionDaoImpl;
import com.conduent.tpms.qatp.dto.Exclusion;
import com.conduent.tpms.qatp.service.impl.ExclusionServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
/*
 * 
 * 
 */ 

@RestController
@RequestMapping("/api")
public class ExclusionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExclusionController.class);

	@Autowired(required = true)
	ExclusionServiceImpl exclusionService;
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@Autowired(required = true)
	private ExclusionDaoImpl exclusionDaoObj;
	
	private List<Exclusion> exclusionList;

	
	//@PostMapping("/viol-exclusion")
	@RequestMapping(value = "/viol-exclusion", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value = "API call for viol exclusion service.")
	public boolean vioExculsion(@RequestBody Exclusion exclusion) throws SQLException, ParseException {
		logger.info("Starting from here {}", exclusion);
		MDC.put("logFileName", "VIOL_EXCLUSION_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		return exclusionService.checkExclusionRecords(exclusionList,exclusion);
	}	
	
	@PostConstruct
	public void init()
	{
		try {
			exclusionList = exclusionDaoObj.getDataFromOracleDb();

		} catch (Exception ex) {
			logger.info("Exception : {}", ex.getMessage());
			ex.printStackTrace();
		}
	}

	
}
