package com.conduent.transactionSearch.utility;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.conduent.transactionSearch.model.Lane;
import com.conduent.transactionSearch.model.Plaza;
import com.conduent.transactionSearch.service.LaneService;
import com.conduent.transactionSearch.service.PlazaService;


@Component
public class MasterCache 
{
	
	private static final Logger logger = LoggerFactory.getLogger(MasterCache.class);
	
	@Autowired
	private PlazaService plazaService;

	@Autowired
	private LaneService laneService;


	private List<Plaza> plazaList;

	private List<Lane> laneList;
	
	public MasterCache() {
		super();
		return;
	}
	
	public Plaza getPlaza(Integer plazaId)
	{
		if(plazaId==null || plazaList==null)
		{
			return null;
		}
		Optional<Plaza> plaza=plazaList.stream().filter(e->e.getPlazaId().longValue()==plazaId.longValue()).findFirst();
		if(plaza.isPresent())
		{
			return plaza.get();
		}
		return null;
	}
		
	
	public Lane getLane(Integer plazaId,Integer laneId)
	{
		if(plazaId==null || laneId==null || laneList==null || laneList.isEmpty())
		{
			return null;
		}
		Optional<Lane> tollPostLimit=laneList.stream().filter(e->e.getLaneId().intValue()==laneId && e.getPlazaId().intValue()==plazaId).findFirst();
		if(tollPostLimit.isPresent())
		{
			return tollPostLimit.get();
		}
		return null;
	}
	

	@PostConstruct
	public void init()
	{
		try
		{
			logger.debug("Starting the application..");
			logger.info("Starting the application..");
			//plazaList = tPlazaDao.getAll();
			//laneList = laneDAO.getAllTLane();
			//vehicleList = vehicleClassDao.getAll();
		}
		catch(Exception ex)
		{
			logger.info("Exception : {}",ex.getMessage());
			//ex.printStackTrace();
		}
	}
/*	public void loadData()
	{
		logger.info("cache data loading started");
		
		
		plazaList=plazaService.getPlaza();
		logger.info("plaza data loading completed");
		
		laneList=laneService.getLane();
		logger.info("lane data loading completed");
		
		
		
		// plazaList=plazaService.getPlaza();
		 * logger.info("plaza data loading completed");
		 * 
		 * 
		 * 
		 * agencyPlazaLaneList=agencyPlazaLaneService.getAgencyPlazaLane();
		 * logger.info("AgencyPlazaLane data loading completed");
		 * 
		 * agencyHolidayList=agencyHolidayService.getAgencyHoliday();
		 * logger.info("Agency Holiday data loading completed");
		 * 
		 * codesList=codeService.getCodes();
		 * logger.info("Codes data loading completed");
		 * 
		 * 
		 * 
		 * processParametersList=processParameterService.getProcessParameters();
		 * logger.info("Process Parameter data loading completed");
		 //
		
		
		
		logger.info("cache data loading completed..");
	}
	
	*/
	public void loadLane()
	{
		laneList=laneService.getLane();
		logger.info("lane data loading completed");
	}
	
}
