package com.conduent.tpms.iag.utility;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.dao.TCodesDao;
import com.conduent.tpms.iag.dao.TLaneDAO;
import com.conduent.tpms.iag.dao.TPlazaDao;
import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.Plaza;
import com.conduent.tpms.iag.dto.TCodesVO;
import com.conduent.tpms.iag.dto.TLaneDto;
import com.conduent.tpms.iag.model.AccountInfo;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;
import com.conduent.tpms.iag.model.LaneTxReconPolicy;
import com.conduent.tpms.iag.model.TollPostLimitInfo;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.VehicleClass;
import com.conduent.tpms.iag.validation.IctxFileParserImpl;

@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(IctxFileParserImpl.class);

	@Autowired
	private TCodesDao tCodeDao;

	@Autowired
	private TAgencyDao tAgencyDao;
	
	@Autowired
	private TLaneDAO tLaneDAO;
	
	@Autowired
	private TPlazaDao tPlazaDao;
	
	@Autowired
	RestTemplateCall restTempalateCall;

	private List<TLaneDto> laneList;
	
	protected List<TCodesVO> tCodeVOList;

	protected List<AgencyInfoVO> agencyInfoVOList;

	private List<Plaza> plazaList;

	private List<LaneTxReconPolicy> laneTxReconPolicyList;

	private List<VehicleClass> vehicleClassList;

	private List<LaneIdExtLaneInfo> laneIdExtLaneList;
	
	private List<AgencyInfoVO> homeAgencyInfoVOList;
	
	private List<LaneIdExtLaneInfo> awayExtLanePazaList;
	
	private List<TollPostLimitInfo> tollPostLimitsInfo;

	public List<AgencyInfoVO> getHomeAgencyInfoVOList() {
		return homeAgencyInfoVOList;
	}

	public void setHomeAgencyInfoVOList(List<AgencyInfoVO> homeAgencyInfoVOList) {
		this.homeAgencyInfoVOList = homeAgencyInfoVOList;
	}

	public TAgencyDao gettAgencyDao() {
		return tAgencyDao;
	}

	public void settAgencyDao(TAgencyDao tAgencyDao) {
		this.tAgencyDao = tAgencyDao;
	}

	public List<AgencyInfoVO> getAgencyInfoVOList() {
		return agencyInfoVOList;
	}

	public void setAgencyInfoVOList(List<AgencyInfoVO> agencyInfoVOList) {
		this.agencyInfoVOList = agencyInfoVOList;
	}

	public boolean isValidPlaza(String externPlazaId, Integer plazaAgencyId) {
		if (externPlazaId.isBlank() || externPlazaId.isEmpty()) {
			return false;
		}
		Optional<Plaza> plazaDto = plazaList.stream()
				.filter(e -> e.getExternPlazaId().equals(externPlazaId.trim())
						&& e.getAgencyId().intValue() == plazaAgencyId.intValue())
				.findFirst();
		return plazaDto.isPresent();
	}

	public boolean isValidAgency(Integer agencyId) {
		if (agencyId == null) {
			return false;
		}
		Optional<AgencyInfoVO> agencyInfoVO = agencyInfoVOList.stream().filter(e -> e.getAgencyId() == agencyId)
				.findFirst();
		if (agencyInfoVO.isPresent()) {
			agencyInfoVO.get().setFileProcessingStatus("N");
			return true;
		}
		return false;
	}
	
	public Integer getAgencyIdByDevicePrefix(String devicePrefix) {
		if (devicePrefix.isBlank() || devicePrefix.isEmpty()) {
			return 0;
		}
		String devicePrefix3 = devicePrefix.substring(1, 4);
		Optional<AgencyInfoVO> agencyInfoVO = agencyInfoVOList.stream()
				.filter(e -> (devicePrefix.equals(e.getDevicePrefix()) || devicePrefix3.equals(e.getDevicePrefix())))
				.findFirst();
		if (agencyInfoVO.isPresent()) {
			return agencyInfoVO.get().getAgencyId();
		}
		return 0;
	}

	public TCodesVO getTcodeId(String codeValue) {
		if (codeValue == null || tCodeVOList == null) {
			log.info("============================>Inside GetTCodeid Method if Post Status Null");
			return null;
		}
		Optional<TCodesVO> tCodes = tCodeVOList.stream()
				.filter(e -> e.getCodeValue().equalsIgnoreCase(codeValue.trim())).findFirst();
		if (tCodes.isPresent()) {
			log.info("============================>Inside GetTCodeid Method" + tCodes.toString());
			return tCodes.get();
		}
		return null;
	}

	public List<TCodesVO> getTcodeList(String codeType) {
		log.info("Loading TCodesVO list..");
		List<TCodesVO> tCodes = tCodeVOList.stream().filter(e -> e.getCodeType().equals(codeType.trim()))
				.collect(Collectors.toList());
		return tCodes;
	}

	public boolean validateHomeDevice(String value) {
		log.info("Loading homeAgencyInfoVOList..");
		Optional<AgencyInfoVO> obj = homeAgencyInfoVOList.stream().filter(e -> e.getDevicePrefix().equals(value.trim()))
				.findAny();
		if(obj.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean validateAwayAgencyEntPlazaLane(String entLane, String entPlaza) {
		log.info("Validating awayExtLanePazaList..");
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
	
	public boolean validateAwayAgencyExtPlazaLane(String extLane, String extPlaza) {
		log.info("Validating awayExtLanePazaList..");
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
	
	public int getLaneId(String externLaneId, Integer plazaId) {
		if (externLaneId.trim() == null || laneList == null || plazaId == null || plazaId.equals(0)) {
			return 0;
		}
		//refresh the list
		laneList = tLaneDAO.getAllTLane();
		
		Optional<TLaneDto> lane = laneList.stream().filter(
				e -> e.getExternLaneId().equals(externLaneId.trim()) && e.getPlazaId().intValue() == plazaId.intValue())
				.findFirst();
		
		if (lane.isPresent()) {
			return lane.get().getLaneId();
		} else if (!lane.isPresent() && !externLaneId.equals("***") && !externLaneId.equals("   ") && !externLaneId.equals("000")) {
			boolean existing = false;
			//get max lane and maxlane+1
			TLaneDto lane_id = tLaneDAO.getMaxLaneId();
			Integer newLaneId = lane_id.getLaneId()+1;
			
			//insert that lane_id in master data
			log.info("Next New Lane Id is {}", newLaneId);
			
			//fetch other values
			Optional<TLaneDto> existingLaneDetail = laneList.stream().filter(e->e.getPlazaId()!=null && e.getPlazaId().intValue() == plazaId.intValue()).findFirst();
			log.info("Existing Lane Plaza Details {}",existingLaneDetail.toString());
			
			if(existingLaneDetail.isPresent()) {
				existing = true;
				//Insert in T_LANE API Calling
				boolean laneInfo = restTempalateCall.insertInTLane(newLaneId,externLaneId.trim(),plazaId,existingLaneDetail,existing);
				return laneInfo ? newLaneId : 0;
			} else {
				existing = false;
				//Insert in T_LANE API Calling
				boolean laneInfo = restTempalateCall.insertInTLane(newLaneId,externLaneId.trim(),plazaId,existingLaneDetail,existing);
				return laneInfo ? newLaneId : 0;
			}
		} else {
			log.info("Extern Lane Id cannot be ** or blank or zero");
			return 0;
		}
	}
	
	@PostConstruct
	public void init() {
		try {
			agencyInfoVOList = tAgencyDao.getAgencyInfoList();
			tCodeVOList = tAgencyDao.getTCodesList();
			settCodeVOList(tCodeVOList);
			homeAgencyInfoVOList = tAgencyDao.getHomeAgencyInfoList();
			setHomeAgencyInfoVOList(homeAgencyInfoVOList);
			awayExtLanePazaList = tLaneDAO.getAwayExtLanePlazaList();
			tollPostLimitsInfo = tAgencyDao.getTollPostLimits();
			laneList = getAllTLane();
			plazaList = tPlazaDao.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Integer getXtraAxlesFromVehicleClassStatics(String etcIndVehClass) {
		return null;
	}

	public Long getAVCAxlesFromVehicleAxleStatics(String etcIndVehClass) {
		return null;
	}

	public boolean isHomeAgency(String agencyId) {
		return true;
	}

	public Plaza getPlaza(String etcEntryPlazaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public com.conduent.tpms.iag.dto.AgencyInfoVO getAgency(String etcAgencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	public TLaneDto getLane(String etcEntryLaneId, Integer plazaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public VehicleClass getXtraAxlesFromVehicleClassStatics(Integer integer, String etcIndVehClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidAgency(String etcAgencyId) {
		// TODO Auto-generated method stub
		return false;
	}

	public TLaneDto getLaneFromPlaza(Integer plazaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TCodesVO> gettCodeVOList() {
		return tCodeVOList;
	}

	public void settCodeVOList(List<TCodesVO> tCodeVOList) {
		this.tCodeVOList = tCodeVOList;
	}

	public TCodesDao gettCodeDao() {
		return tCodeDao;
	}

	public void settCodeDao(TCodesDao tCodeDao) {
		this.tCodeDao = tCodeDao;
	}

	public List<LaneTxReconPolicy> getLaneTxReconPolicyList() {
		return laneTxReconPolicyList;
	}

	public void setLaneTxReconPolicyList(List<LaneTxReconPolicy> laneTxReconPolicyList) {
		this.laneTxReconPolicyList = laneTxReconPolicyList;
	}

	public List<VehicleClass> getVehicleClassList() {
		return vehicleClassList;
	}

	public void setVehicleClassList(List<VehicleClass> vehicleClassList) {
		this.vehicleClassList = vehicleClassList;
	}

	public AccountInfo getAccountInfoOne(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp) {
		// V_DEVICE
		return tAgencyDao.getAccountInfoOne(deviceNo14, deviceNo11, txTimestamp);
	}

	public AccountInfo getAccountInfoTwo(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp) {
		// V_H_DEVICE
		return tAgencyDao.getAccountInfoTwo(deviceNo14, deviceNo11, txTimestamp);
	}

	public AccountInfo getAccountInfoThree(String deviceNo14, String deviceNo11, String txTxDate) {
		// T_HA_DEVICES
		return tAgencyDao.getAccountInfoThree(deviceNo14, deviceNo11, txTxDate);
	}
	
	public Integer getHaDevicesIagTagStatus(String deviceNo14, String deviceNo11, String txTxDate) {
		// T_HA_DEVICES
		return tAgencyDao.getHaDevicesIagTagStatus(deviceNo14, deviceNo11, txTxDate);
	}
	
	public void getTxStatusCodeId(TranDetail tranDetail) {
		tAgencyDao.getTxStatusCodeId(tranDetail);
	}

	public AccountInfo getAccountInfo(TranDetail tranDetail) {
		// V_ETC_ACCOUNT
		return tAgencyDao.getAccountInfo(tranDetail);
	}
	
	public TollPostLimitInfo getTollPostLimit(int plazaAgencyId, int txStatus) {
		// MASTER.T_TOLL_POST_LIMIT
		return tollPostLimitsInfo.stream().filter(t -> t.getPlazaAgencyId() == plazaAgencyId && t.getEtcTxStatus() == txStatus).findFirst()
				.orElse(null);
	}

	public List<LaneIdExtLaneInfo> getLaneIdExtLaneList() {
		return tAgencyDao.getLaneIdExtLaneIdInfo();
	}
	
	public List<TLaneDto> getAllTLane() {
		return tLaneDAO.getAllTLane();
	}
	
	public List<Plaza> getAllTPlaza() {
		return tPlazaDao.getAll();
	}

	public String getUnresgisteredStaus(TranDetail tranDetail) {
		return tAgencyDao.getUnresgisteredStaus(tranDetail);
	}

	public void setLaneIdExtLaneList(List<LaneIdExtLaneInfo> laneIdExtLaneList) {
		this.laneIdExtLaneList = laneIdExtLaneList;
	}

}
