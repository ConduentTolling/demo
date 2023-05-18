package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.dto.Plaza;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.dto.TProcessParamterDto;
import com.conduent.tpms.qatp.model.LaneIdExtLaneInfo;
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
	private IQatpDao iQatpDao;
	
	@Autowired
	private IVehicleClassDao vehicleClassDao;

	private List<AgencyInfoVO> agencyInfoVOList;
	private List<TLaneDto> laneList;
	private List<Plaza> plazaList;
	private List<VehicleClass> vehicleList;
	private List<TProcessParamterDto> processParamtersList;
	
	//fo Trnx
	private List<AgencyInfoVO> homeAgencyInfoVOList;
	
	private List<LaneIdExtLaneInfo> awayExtLanePazaList;
	
	
	public List<AgencyInfoVO> getHomeAgencyInfoVOList() {
		return homeAgencyInfoVOList;
	}

	public void setHomeAgencyInfoVOList(List<AgencyInfoVO> homeAgencyInfoVOList) {
		this.homeAgencyInfoVOList = homeAgencyInfoVOList;
	}

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
	
	public TLaneDto getLane(String externLaneId,Integer plazaId)
	{
		if(externLaneId==null || laneList==null)
		{
			return null;
		}
		Optional<TLaneDto> lane=laneList.stream().filter(e->e.getExternLaneId().equals(externLaneId.trim()) && e.getPlazaId().intValue()==plazaId.intValue()).findFirst();
		if(lane.isPresent())
		{
			return lane.get();
		}
		return null;
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
				filter(e->e!=null && e.getAgencyId().equals(agencyId) && e.getExternAgencyClass().equals(externAgencyClass)).findFirst();
		if(vehicleInfo.isPresent())
		{
			return vehicleInfo.get();
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
			agencyInfoVOList= agencyDao.getAgencyInfo();
			plazaList = tPlazaDao.getAll();
			laneList = laneDAO.getAllTLane();
			vehicleList = vehicleClassDao.getAll();
			//foTrnx
			awayExtLanePazaList = laneDAO.getAwayExtLanePlazaList();
			homeAgencyInfoVOList = agencyDao.getHomeAgencyInfoList();
			processParamtersList = iQatpDao.getProcessParamtersList();
		}
		catch(Exception ex)
		{
			logger.info("Exception : {}",ex.getMessage());
			//ex.printStackTrace();
		}
	}

	
	//Fo Trnx
	public boolean validateAwayAgencyEntPlazaLane(String entLane, String entPlaza) {
		logger.info("Validating awayExtLanePazaList..");
		Optional<LaneIdExtLaneInfo> obj = null;
		if(entPlaza !=null && entLane != null) {
		obj = awayExtLanePazaList.stream().filter(e -> e.getExternPlazaId().equals(entPlaza.trim()) && e.getExternLaneId().equals(entLane.trim()))
				.findAny();
		}
		if(obj.isPresent()) {
			return true;
		}else {
			return false;
		}
		
	}

	public boolean validateHomeDevice(String value) {
		logger.info("Loading homeAgencyInfoVOList..");
		Optional<AgencyInfoVO> obj = homeAgencyInfoVOList.stream().filter(e -> e.getDevicePrefix().equals(value.trim()))
				.findAny();
		if(obj.isPresent()) {
			return true;
		}else {
			return false;
		}
	}

	public boolean validateAwayAgencyExtPlazaLane(String extLane, String extPlaza) {
		logger.info("Validating awayExtLanePazaList..");
		Optional<LaneIdExtLaneInfo> obj = null;
		if(extPlaza !=null && extLane != null) {
		obj = awayExtLanePazaList.stream().filter(e -> e.getExternPlazaId().equals(extPlaza.trim()) && e.getExternLaneId().equals(extLane.trim()))
				.findAny();
		}
		if(obj.isPresent()) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public String getParkingThresholdValue()
	{
		if(processParamtersList!=null)
		{
			Optional<TProcessParamterDto> processParam = processParamtersList.stream().findFirst();
			if(processParam!=null)
			{
				return processParam.get().getParam_value();
			}
			else
			{
				return "0";
			}
		}
		return "0";
		
	}
}
