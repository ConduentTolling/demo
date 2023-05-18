package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.dto.Tplaza;
import com.conduent.tpms.qatp.model.VehicleClass;

@Component
public class MasterDataCache {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterDataCache.class);

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
//	private List<AgencyInfoVO> agencyInfoVOList;
	
	public TLaneDto isValidLaneId(String externLaneId, Integer plazaId) {
		if (externLaneId == null || plazaId == null || laneList == null) {
			return null;
		}

		TLaneDto tLaneDto = null;
		for (TLaneDto tLaneDtoObj : laneList) {
			if (tLaneDtoObj != null) {
				if (tLaneDtoObj.getExternLaneId() != null && tLaneDtoObj.getExternLaneId().equals(externLaneId)
						&& tLaneDtoObj.getPlazaId() != null && tLaneDtoObj.getPlazaId().equals(plazaId)) {
					tLaneDto = tLaneDtoObj;
					break;
				}
			}
		}
		return tLaneDto;
	}

	public Integer isValidLane(String externLaneId, Integer plazaId) {
		if (externLaneId == null || plazaId == null || laneList == null) {
			return null;
		}

		//TLaneDto tLaneDto = null;
//		for (TLaneDto tLaneDtoObj : laneList) {
//			if (tLaneDtoObj != null) {
//				if (tLaneDtoObj.getExternLaneId() != null && tLaneDtoObj.getExternLaneId().equals(externLaneId)
//						&& tLaneDtoObj.getPlazaId() != null && tLaneDtoObj.getPlazaId().equals(plazaId)) {
//					tLaneDto = tLaneDtoObj;
//					break;
//				}
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

	public Tplaza isValidPlaza(String externPlazaId, Integer agencyId) {
		if (externPlazaId == null || agencyId == null || plazaList == null) {
			return null;
		}

		Tplaza tPlaza = null;
		for (Tplaza tPlazaObj : plazaList) {
			if (tPlazaObj != null) {
				if (tPlazaObj.getExternPlazaId() != null && tPlazaObj.getExternPlazaId().equals(externPlazaId)
						&& tPlazaObj.getAgencyId() != null && tPlazaObj.getAgencyId().equals(agencyId)) {
					tPlaza = tPlazaObj;
					break;
				}
			}
		}

		return tPlaza;
	}
	
	public Integer getPlazaIfNull(Integer agencyId) {
		if (plazaList == null || agencyId == null) {
			return null;
		}
		Optional<Tplaza> plazaDto = plazaList.stream().filter(e -> e.getAgencyId().equals(agencyId)).findFirst();
		if (plazaDto.isPresent()) {
			return plazaDto.get().getPlazaId();
		}
		return null;
	}
	
	@PostConstruct
	public void init() {
		try {

			plazaList = tPlazaDao.getAll();
			laneList = laneDAO.getAllTLane();
//			agencyInfoVOList = agencyDao.getAgencyInfo();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
