package com.conduent.tpms.inrx.utility;

import java.io.File;
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

import com.conduent.tpms.inrx.dao.PlanPolicyDao;
import com.conduent.tpms.inrx.dao.SystemAccountDao;
import com.conduent.tpms.inrx.dao.TAgencyDao;
import com.conduent.tpms.inrx.dao.TCodesDao;
import com.conduent.tpms.inrx.dao.TPlazaDao;
import com.conduent.tpms.inrx.dto.Plaza;
import com.conduent.tpms.inrx.dto.RevenueDateStatisticsDto;
import com.conduent.tpms.inrx.model.AgencyInfoVO;
import com.conduent.tpms.inrx.model.PlanPolicyVO;
import com.conduent.tpms.inrx.model.SystemAccountVO;
import com.conduent.tpms.inrx.model.TCodesVO;
import com.conduent.tpms.inrx.model.ZipCode;
import com.conduent.tpms.inrx.validation.InrxFileParserImpl;



@Component
public class MasterDataCache {
	
	private static final Logger log = LoggerFactory.getLogger(InrxFileParserImpl.class);
	
	@Autowired
	private TAgencyDao tAgencyDao;
	
	@Autowired
	private TCodesDao tCodeDao;
	
	@Autowired
	private PlanPolicyDao planPolicyDao;
	
	@Autowired
	private SystemAccountDao  systemAccountDao;
	
	@Autowired
	private TPlazaDao  plazaDao;
	
	
	private List<Plaza> plazaList;
	protected List<AgencyInfoVO> agencyInfoVOList;
	protected List<ZipCode> zipCodeList;
	
	
	protected List<PlanPolicyVO> planPolicyList; // added by shrikant
	protected List<TCodesVO> tCodeVOList; //added by shrikant
	protected List<SystemAccountVO> systemAccountList; //added by shrikant
	private Map<Integer, RevenueDateStatisticsDto> revenueStatsByAgencyMap = new HashMap<Integer, RevenueDateStatisticsDto>();

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

	/*	public boolean isValidAgency(Integer agencyId) {
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
	
*/	
	public RevenueDateStatisticsDto getRevenueStatusByPlaza(Integer plazaId) {
		RevenueDateStatisticsDto tempRevenueDateStatisticsDto = revenueStatsByAgencyMap.get(plazaId);
		if (tempRevenueDateStatisticsDto != null) {
			return tempRevenueDateStatisticsDto;
		} else {
			tempRevenueDateStatisticsDto = new RevenueDateStatisticsDto();
			Optional<Plaza> tempOptionalPlaza = plazaList.stream()
					.filter(e -> plazaId.intValue() == e.getPlazaId().intValue()).findFirst();
			if (tempOptionalPlaza.isPresent()) {
				Plaza tempPlaza = tempOptionalPlaza.get();
				tempRevenueDateStatisticsDto.setPlazaId(tempPlaza.getPlazaId());
				tempRevenueDateStatisticsDto.setRevenueTime(tempPlaza.getRevenueTime());
				tempRevenueDateStatisticsDto.setRevenueSecondOfDay(
						LocalTime.parse("23:59:59", DateTimeFormatter.ofPattern("H:mm:ss")).toSecondOfDay());
				revenueStatsByAgencyMap.put(plazaId, tempRevenueDateStatisticsDto);
			}
		}
		return revenueStatsByAgencyMap.get(plazaId);
	}
	
	public TCodesVO getTcodeId(String codeValue) {
		if (codeValue == null || tCodeVOList == null) {
			log.info("Inside GetTCodeid Method if Post Status ==> [Null]");
			return null;
		}
		Optional<TCodesVO> tCodes = tCodeVOList.stream().filter(e -> e.getCodeValue().equalsIgnoreCase(codeValue.trim())).findFirst();
		if (tCodes.isPresent()) {
			log.info("ETC_POST_STATUS aviliable Inside GetTCodeid Method object ==> "+tCodes.toString());
			return tCodes.get();
		}
		return null;
	}
	
	
	public PlanPolicyVO getPlanId(String planeName) {
		if (planeName == null || planPolicyList == null) {
			log.info("============================>Inside getPlanId Method if PlaneName Null");
			return null;
		}
		Optional<PlanPolicyVO> plan = planPolicyList.stream().filter(e -> e.getPlanName().equalsIgnoreCase(planeName.trim())).findFirst();
		if (plan.isPresent()) {
			log.info("============================>Inside getPlanId Method"+plan.toString());
			return plan.get();
		}
		return null;
	}
	
	public SystemAccountVO getParamValue(Integer paramConfig) {
		if (paramConfig == null || systemAccountList == null) {
			log.info("Param config value is null or not present..");
			return null;
		}
		Optional<SystemAccountVO> plan = systemAccountList.stream().filter(e -> e.getParamConfig()==(paramConfig)).findFirst();
		if (plan.isPresent()) {
			log.info("Param config value is presennt get systemAccount object ==> "+plan.toString());
			return plan.get();
		}
		return null;
	}
	
	public AgencyInfoVO getAccountAgencyId(String fromAgencytId) {
		if (fromAgencytId == null || agencyInfoVOList == null) {
			log.info("fromAgencytId value is null or not present");
			return null;
		}
		Optional<AgencyInfoVO> filePrefix = agencyInfoVOList.stream().filter(e -> e.getFilePrefix().equalsIgnoreCase(fromAgencytId.trim())).findFirst();
		if (filePrefix.isPresent()) {
			log.info("FromAgencytId value present inside getAccountAgencyId method"+filePrefix.toString());
			return filePrefix.get();
		}
		return null;
	}
	
	
	@PostConstruct 
	public void init() { 
		 try { 
			 agencyInfoVOList = tAgencyDao.getAgencyInfoList();
			 log.info("AgencyInfo loaded successfully");
			 //zipCodeList = tAgencyDao.getZipCodeList();
			 //log.info("Zipcode List"+zipCodeList);
			 //System.out.println("Zipcode loaded successfully");
			 setAgencyInfoVOList(agencyInfoVOList);
			// setZipCodeList(zipCodeList);
			 tCodeVOList = tCodeDao.getTCodes();
			 log.info("TCodes loaded successfully");
			 planPolicyList = planPolicyDao.getPlanPolicy();
			 log.info("Plan Policy loaded successfully");
			 systemAccountList = systemAccountDao.getAccount();
			 System.out.println("SystemAccount loaded successfully");
			 plazaList=plazaDao.getPlaza();
			 System.out.println("Plaza loaded successfully");
		 }
	  catch (Exception ex) { ex.printStackTrace(); } }
	 
	

	/*
	 * public List<ZipCode> getZipCodeList() { return zipCodeList; }
	 * 
	 * public void setZipCodeList(List<ZipCode> zipCodeList) { this.zipCodeList =
	 * zipCodeList; }
	 */

	public Integer getXtraAxlesFromVehicleClassStatics(String etcIndVehClass) {
		return null;
	}

	public Long getAVCAxlesFromVehicleAxleStatics(String etcIndVehClass) {
		return null;
	}

	public boolean isHomeAgency(String agencyId) {
		return true;
	}

	public TCodesDao gettCodeDao() {
		return tCodeDao;
	}

	public void settCodeDao(TCodesDao tCodeDao) {
		this.tCodeDao = tCodeDao;
	}

	public PlanPolicyDao getPlanPolicyDao() {
		return planPolicyDao;
	}

	public void setPlanPolicyDao(PlanPolicyDao planPolicyDao) {
		this.planPolicyDao = planPolicyDao;
	}

	public List<PlanPolicyVO> getPlanPolicyList() {
		return planPolicyList;
	}

	public void setPlanPolicyList(List<PlanPolicyVO> planPolicyList) {
		this.planPolicyList = planPolicyList;
	}

	public List<TCodesVO> gettCodeVOList() {
		return tCodeVOList;
	}

	public void settCodeVOList(List<TCodesVO> tCodeVOList) {
		this.tCodeVOList = tCodeVOList;
	}

	
}
