package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dao.impl.TAgencyDaoImpl;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.dto.Tplaza;
import com.conduent.tpms.qatp.model.VehicleClass;

import ch.qos.logback.core.joran.event.InPlayListener;

@Component
public class MasterDataCache {

	private static final Logger dao_log = LoggerFactory.getLogger(MasterDataCache.class);
	@Autowired
	private TLaneDAO laneDAO;

	@Autowired
	private TPlazaDao tPlazaDao;

	@Autowired
	private IVehicleClassDao vehicleClassDao;

	@Autowired
	private TAgencyDao agencyDao;
	
	@Autowired
	RestTemplateCall restTempalateCall;

	private List<TLaneDto> laneList;
	private List<Tplaza> plazaList;
	private List<AgencyInfoVO> agencyInfoVOList;
	private List<VehicleClass> vehicleList;

	public boolean isValidLane(Integer laneId) {
		Optional<TLaneDto> laneDto = laneList.stream().filter(e -> e.getLaneId() == laneId).findFirst();
		if (laneDto.isPresent()) {
			return true;
		}
		return false;
	}

	public boolean isValidAgency(String agencyId) {
		Optional<AgencyInfoVO> laneDto = agencyInfoVOList.stream().filter(e -> e.getDevicePrefix().equals(agencyId.trim()))
				.findFirst();
		if (laneDto.isPresent()) {
			return true;
		}
		return false;
	}

	public AgencyInfoVO getAgency(String agencyId) {
		if(agencyId ==null || agencyId.matches(".*[a-zA-Z].*") || agencyId.length() != 4 || agencyId.contains(" ")) {
			return null;
		}
		Optional<AgencyInfoVO> agencyDto = agencyInfoVOList.stream().filter(e -> e.getDevicePrefix().equals(agencyId.trim())).findFirst();
		if (agencyDto.isPresent()) {
			return agencyDto.get();
		}
		return null;
	}

	public boolean isValidPlaza(Integer plazaId) {
		if (plazaId == null) {
			return false;
		}
		Optional<Tplaza> plazaDto = plazaList.stream().filter(e -> e.getPlazaId().intValue() == plazaId.intValue())
				.findFirst();
		if (plazaDto.isPresent()) {
			return true;
		}
		return false;
	}

	public Tplaza getPlaza(String externPlazaId,Integer agencyId) 
	{
		if (externPlazaId == null || plazaList == null || externPlazaId.matches(".*[a-zA-Z].*"))
		{
			return null;
		}
		Optional<Tplaza> plazaDto = plazaList.stream().filter(e -> e.getExternPlazaId().equals(externPlazaId.trim()) 
				&& e.getAgencyId()==agencyId)
				.findFirst();
		if (plazaDto.isPresent()) {
			return plazaDto.get();
		}
		return null;
	}

	public Integer isLprEnabled(Integer exitPlaza) {
		if (exitPlaza == null || plazaList == null) {
			return null;
		}
		Optional<Tplaza> plazaDto = plazaList.stream().filter(e -> e.getPlazaId().equals(exitPlaza))
				.findFirst();
		if (plazaDto.isPresent()) {
			Integer lpr=plazaDto.get().getLprEnabled().equals("Y")||plazaDto.get().getLprEnabled().equals("T")?1:0;
			return lpr;
		}
		return null;
	}

	public TLaneDto getLane(String externLaneId, Integer plazaId) {
		if (externLaneId == null || laneList == null || plazaId==null || externLaneId.matches(".*[a-zA-Z].*") || externLaneId.length() != 3 || externLaneId.contains(" ")) {
			return null;
		}
		Optional<TLaneDto> lane = laneList.stream().filter(
				e -> e.getExternLaneId().equals(externLaneId.trim()) && e.getPlazaId().intValue() == plazaId.intValue())
				.findFirst();
		if (lane.isPresent()) {
			return lane.get();
		}
		return null;
	}
	
	public int getLaneId(String externLaneId, Integer plazaId) 
	{
		if (externLaneId == null || plazaId==null || laneList == null || externLaneId.matches(".*[a-zA-Z].*") || externLaneId.length() != 3 || externLaneId.contains(" ")) 
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
			boolean existing = false;
			
			//get max lane and maxlane+1
			TLaneDto lane_id = laneDAO.getMaxLaneId();
			Integer newLaneId = lane_id.getLaneId()+1;
			
			//insert that lane_id in master data
			dao_log.info("Next New Lane Id is {}",newLaneId);
			
			//fetch other values
			Optional<TLaneDto> existingLaneDetail = laneList.stream().filter(e->e.getPlazaId()!=null && e.getPlazaId().intValue() == plazaId.intValue()).findFirst();
			dao_log.info("Existing Plaza Details {}",existingLaneDetail.toString());
			
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
			dao_log.info("Extern Lane Id cannot be ** or blank or zero");
			return 0;
		}
	}

//	public VehicleClass findVehicleClass(Integer agencyId, String externAgencyClass) {
//		return vehicleClassDao.getVehicleClass(agencyId, externAgencyClass.trim());
//
//	}
	
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
	
	public Tplaza getPlazabyorder(int agency_id)
	{
		if(plazaList != null)
		{
			Optional<Tplaza> plazaDto=plazaList.stream().filter(e->e!=null && e.getAgencyId()!=null && e.getAgencyId().equals(agency_id)).findFirst();
			if(plazaDto.isPresent())
			{
				return plazaDto.get();
			}
		}
		return null;
		
	}
	
	public AgencyInfoVO checkAwayAgency(String deviceno) {
		if(deviceno ==null) {
			return null;
		}
		Optional<AgencyInfoVO> agencyDto = agencyInfoVOList.stream().filter(e -> e.getDevicePrefix().equals(deviceno))
				.findFirst();
		if (agencyDto.isPresent()) {
			return agencyDto.get();
		}
		return null;
	}
	
	public Integer getPlazaIfNull(Integer agencyId)
	{
		if(plazaList==null || agencyId==null)
		{
			return null;
		}
		Optional<Tplaza> plazaDto=plazaList.stream().filter(e->e.getAgencyId()==agencyId).findFirst();
		if(plazaDto.isPresent())
		{
			return plazaDto.get().getPlazaId();
		}
		return null;
	}

	@PostConstruct
	public void init() {
		try {

			plazaList = tPlazaDao.getAll();
			dao_log.info("Plaza info fetched from T_PLAZA table successfully.");
			laneList = laneDAO.getAllTLane();
			dao_log.info("Lane info fetched from T_LANE table successfully.");
			agencyInfoVOList = agencyDao.getAgencyInfo();
			vehicleList = vehicleClassDao.getAll();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
