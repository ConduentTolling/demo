package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.dto.Plaza;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.model.ProcessParameter;
import com.conduent.tpms.qatp.model.VehicleClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MasterDataCache
{
	private static final Logger logger = LoggerFactory.getLogger(MasterDataCache.class);
	
	@Autowired
	private TLaneDAO laneDAO;

	@Autowired
	private TPlazaDao tPlazaDao;
	
	@Autowired
	private TAgencyDao agencyDao;
	
	@Autowired
	private IVehicleClassDao vehicleClassDao;
	
	@Autowired
	RestTemplateCall restTempalateCall;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private List<AgencyInfoVO> agencyInfoVOList;
	private List<TLaneDto> laneList;
	private List<Plaza> plazaList;
	private List<VehicleClass> vehicleList;
	private String processParameter;
	
	
	public boolean isValidLane(Integer laneId)
	{
		Optional<TLaneDto> laneDto=laneList.stream().filter(e->e.getLaneId().intValue()==laneId.intValue()).findFirst();
		if(laneDto.isPresent())
		{
			return true;
		}
		return false;
	}

	public boolean isValidAgency(String agencyId)
	{
		Optional<AgencyInfoVO> laneDto=agencyInfoVOList.stream().filter(e->e.getDevicePrefix().equals(agencyId.trim())).findFirst();
		if(laneDto.isPresent())
		{
			return true;
		}
		return false;
	}
	
	public AgencyInfoVO getAgency(String agencyId)
	{
		Optional<AgencyInfoVO> agencyDto=agencyInfoVOList.stream().filter(e->e.getDevicePrefix().equals(agencyId.trim())).findFirst();
		if(agencyDto.isPresent())
		{
			return agencyDto.get();
		}
		return null;
	}
	public boolean isValidPlaza(Integer plazaId)
	{
		if(plazaId==null)
		{
			return false;
		}
		Optional<Plaza> plazaDto=plazaList.stream().filter(e->e.getPlazaId().intValue()==plazaId.intValue()).findFirst();
		if(plazaDto.isPresent())
		{
			return true;
		}
		return false;
	}
	public Plaza getPlaza(String externPlazaId,Integer agencyId)
	{
		if(externPlazaId==null || plazaList==null || agencyId==null)
		{
			return null;
		}
		
		Optional<Plaza> plazaDto=plazaList.stream().filter(e->e!=null && e.getExternPlazaId()!=null && e.getExternPlazaId().equals(externPlazaId.trim()) && 
				e.getAgencyId()==agencyId).findFirst();
		if(plazaDto.isPresent())
		{
			return plazaDto.get();
		}
		return null;
	}
	
	public Plaza getPlazabyorder(int agency_id)
	{
		if(plazaList != null)
		{
			Optional<Plaza> plazaDto=plazaList.stream().filter(e->e!=null && e.getAgencyId()!=null && e.getAgencyId().equals(agency_id)).findFirst();
			if(plazaDto.isPresent())
			{
				return plazaDto.get();
			}
		}
		return null;
		
	}
	
	public Integer getPlazaIfNull(Integer agencyId)
	{
		if(plazaList==null || agencyId==null)
		{
			return null;
		}
		Optional<Plaza> plazaDto=plazaList.stream().filter(e->e.getAgencyId()==agencyId).findFirst();
		if(plazaDto.isPresent())
		{
			return plazaDto.get().getPlazaId();
		}
		return null;
	}
	
	public int getLaneId(String externLaneId,Integer plazaId)
	{
		if(externLaneId==null || laneList==null)
		{
			return 0;
		}
		Optional<TLaneDto> lane = laneList.stream().filter(
				e -> e.getExternLaneId().equals(externLaneId.trim()) && e.getPlazaId().intValue() == plazaId.intValue())
				.findFirst();
		if (lane.isPresent())
		{
			return lane.get().getLaneId();
		}
		else if (!lane.isPresent() && !externLaneId.equals("***") && !externLaneId.equals("   ") && !externLaneId.equals("000"))
		{ 
			logger.info("Lane {} is not present in MASTER.T_LANE Table",externLaneId);
			boolean existing = false;
			//get max lane and maxlane+1
			TLaneDto lane_id = laneDAO.getMaxLaneId();
			Integer newLaneId = lane_id.getLaneId()+1;
			
			//insert that lane_id in master data
			logger.info("Next New Lane Id is {}",newLaneId);
			
			//fetch other values
			Optional<TLaneDto> existingLaneDetail = laneList.stream().filter(e->e.getPlazaId()!=null && e.getPlazaId().intValue() == plazaId.intValue()).findFirst();
			logger.info("Existing Plaza Details {}",existingLaneDetail.toString());
			
			if(existingLaneDetail.isPresent())
			{
				existing = true;
				//Insert in T_LANE API Calling
				boolean laneInfo = restTempalateCall.insertInTLane(newLaneId,externLaneId.trim(),plazaId,existingLaneDetail,existing);
				
				if(laneInfo)
				{
					//refresh the list and return
					laneList = laneDAO.getAllTLane();
					return newLaneId;
				}
				else
				{
					return 0;
				}
			}
			else
			{
				existing = false;
				//Insert in T_LANE API Calling
				boolean laneInfo = restTempalateCall.insertInTLane(newLaneId,externLaneId.trim(),plazaId,existingLaneDetail,existing);
				
				if(laneInfo)
				{
					//refresh the list and return
					laneList = laneDAO.getAllTLane();
					return newLaneId;
				}
				else
				{
					return 0;
				}
			}
			
		}
		else
		{
			logger.info("Extern Lane Id cannot be ** or blank or zero");
			return 0;
		}
	}
	
	public TLaneDto getLane(String externLaneId,Integer plazaId)
	{
		if(externLaneId==null || laneList==null)
		{
			return null;
		}
		Optional<TLaneDto> lane = laneList.stream().filter(
				e -> e.getExternLaneId().equals(externLaneId.trim()) && e.getPlazaId().intValue() == plazaId.intValue())
				.findFirst();
		if (lane.isPresent())
		{
			return lane.get();
		}
		else
		{
			//logger.info("Lane {} is not present in MASTER.T_LANE Table",externLaneId);
			return null;
		}
	}
	
	public TLaneDto getLaneFromPlaza(Integer plazaId)
	{
		if(plazaId==null || laneList==null)
		{
			return null;
		}
		Optional<TLaneDto> lane=laneList.stream().filter(e->e.getPlazaId().intValue()==plazaId.intValue()).findFirst();
		if(lane.isPresent())
		{
			return lane.get();
		}
		return null;
	}
		
	public VehicleClass getXtraAxlesFromVehicleClassStatics(Integer agencyId,String externAgencyClass)
	{
		return vehicleClassDao.getVehicleClass(agencyId, externAgencyClass.trim());
		
	}
	public Long getAVCAxlesFromVehicleAxleStatics(Integer agencyId,String externAgencyClass)
	{
		return null;
	}
	
	public AgencyInfoVO isHomeAgency(String agencyId)
	{
		Optional<AgencyInfoVO> agency = agencyInfoVOList.stream().filter(e->e.getDevicePrefix().equals(agencyId)).findFirst();
		if(agency.isPresent())
		{
			return agency.get();
		}
		return null;
	}
	
	//add method to get vehicle class from vehicleList
	//pass param agency id and external agency id

	public VehicleClass getAgencyIdandExternalAgencyClass(Integer agencyId,String externAgencyClass)
	{
		Optional<VehicleClass> vehicleInfo = vehicleList.stream().
				filter(e->e!=null && e.getAgencyId().equals(agencyId) && e.getExternAgencyClass().equals(externAgencyClass.replaceFirst("0", ""))).findFirst();
		if(vehicleInfo.isPresent())
		{
			return vehicleInfo.get();
		}
		return null;
		
	}
	
	public String getInsertLaneParamter()
	{
		if(processParameter!=null)
		{
			return processParameter;
		}
		else
			return null;
	}
	
	@PostConstruct
	public void init()
	{
		try
		{
			logger.debug("Starting the application..");
			logger.info("Starting the application..");
			agencyInfoVOList= agencyDao.getAgencyInfo();
			plazaList = tPlazaDao.getAll();
			laneList = laneDAO.getAllTLane();
			vehicleList = vehicleClassDao.getAll();
			processParameter = agencyDao.insertLane();
		}
		catch(Exception ex)
		{
			logger.info("Exception : {}",ex.getMessage());
			//ex.printStackTrace();
		}
	}
}
