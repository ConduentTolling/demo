package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.dto.Plaza;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.model.VehicleClass;

@Component
public class MasterDataCache
{

	@Autowired
	private TLaneDAO laneDAO;

	@Autowired
	private TPlazaDao tPlazaDao;
	
	@Autowired
	private TAgencyDao agencyDao;
	
	@Autowired
	private IVehicleClassDao vehicleClassDao;

	private List<AgencyInfoVO> agencyInfoVOList;
	private List<TLaneDto> laneList;
	private List<Plaza> plazaList;

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
		Optional<Plaza> plazaDto=plazaList.stream().filter(e->e.getExternPlazaId().equals(externPlazaId.trim()) && 
				e.getAgencyId()==agencyId).findFirst();
		if(plazaDto.isPresent())
		{
			return plazaDto.get();
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

	@PostConstruct
	public void init()
	{
		try
		{
			agencyInfoVOList= agencyDao.getAgencyInfo();
			plazaList=tPlazaDao.getAll();
			laneList=laneDAO.getAllTLane();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
