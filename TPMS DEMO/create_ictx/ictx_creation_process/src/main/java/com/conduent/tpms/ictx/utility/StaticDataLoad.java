package com.conduent.tpms.ictx.utility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.ictx.dao.AgencyDao;
import com.conduent.tpms.ictx.dao.DistanceDiscountDao;
import com.conduent.tpms.ictx.dao.LaneDao;
import com.conduent.tpms.ictx.dao.PlazaDao;
import com.conduent.tpms.ictx.dao.ProcessParametersDao;
import com.conduent.tpms.ictx.dao.TCodeDao;
import com.conduent.tpms.ictx.dao.TollPostLimitDao;
import com.conduent.tpms.ictx.dao.VehicleClassDao;
import com.conduent.tpms.ictx.dto.RevenueDateStatisticsDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.DistanceDiscount;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.Plaza;
import com.conduent.tpms.ictx.model.ProcessParameter;
import com.conduent.tpms.ictx.model.TCode;
import com.conduent.tpms.ictx.model.TollPostLimit;
import com.conduent.tpms.ictx.model.VehicleClass;

/**
 * Load Data on startup
 * 
 * @author deepeshb
 *
 */
@Component
public class StaticDataLoad {

	private static final Logger logger = LoggerFactory.getLogger(StaticDataLoad.class);

	@Autowired
	private AgencyDao agencyDao;

	@Autowired
	private LaneDao laneDao;

	@Autowired
	private VehicleClassDao vehicleClassDao;

	@Autowired
	private DistanceDiscountDao distanceDiscountDao;

	@Autowired
	private PlazaDao plazaDao;

	@Autowired
	private TCodeDao tCodeDao;

	@Autowired
	private ProcessParametersDao processParametersDao;

	@Autowired
	private LocalDateTimeUtility localDateTimeUtility;

	@Autowired
	private TollPostLimitDao tollPostLimitDao;

	@Autowired
	private LocalTimeUtility localTimeUtility;

	private List<Agency> agencyList;

	private List<Lane> laneList;

	private List<VehicleClass> vehicleClassList;

	private List<DistanceDiscount> distanceDiscountList;

	private List<TCode> etcStatusList;

	private List<TCode> txStatusList;

	private List<Plaza> plazaList;

	private Map<String, LocalDateTime> cutOffDateMap = new HashMap<>();

	private List<ProcessParameter> cutOffDateList;

	private Map<Integer, Integer> agencyPoachingTimeLimitMap = new HashMap<>();;

	private Map<Integer, RevenueDateStatisticsDto> revenueStatsByAgencyMap = new HashMap<Integer, RevenueDateStatisticsDto>();
	
	private List<Lane> homeLanePlazaList;

	/**
	 * Get Agency Info By device prefix
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Agency getAgency(String devicePrefix) {
		Optional<Agency> agency = agencyList.stream().filter(e -> devicePrefix.equalsIgnoreCase(e.getDevicePrefix()))
				.findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}

	/**
	 * Get Away Agency Info
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public List<Agency> getAwayAgency() {
		Map<String, List<Agency>> awayAgencyMap = agencyList.stream()
				.collect(Collectors.groupingBy(Agency::getIsHomeAgency));
		return awayAgencyMap.getOrDefault("N", new ArrayList<Agency>());

	}

	/**
	 * Get Agency Info by agency id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Agency getAgencyById(Long agencyId) {
		Optional<Agency> agency = agencyList.stream().filter(e -> agencyId.equals(e.getAgencyId())).findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}

	
	/**
	 * Get Agency Info by agency id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Agency getFacHomeAgencyById(Long agencyId) {
		Optional<Agency> agency = agencyList.stream().filter(e -> agencyId.equals(e.getAgencyId()) && e.getIsHomeAgency().equalsIgnoreCase("Y")).findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}
	
	/**
	 * Get plaza by plaza id
	 * 
	 * @param plazaId
	 * @return Plaza
	 */
	public Plaza getPlazaById(Integer plazaId) {
		Optional<Plaza> plaza = plazaList.stream().filter(e -> plazaId.equals(e.getPlazaId())).findFirst();
		if (plaza.isPresent()) {
			return plaza.get();
		}
		return null;
	}

	/**
	 * Get lane by lane id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Lane getLaneById(Integer laneId) {
		Optional<Lane> lane = laneList.stream().filter(e -> laneId.equals(e.getLaneId())).findFirst();
		if (lane.isPresent()) {
			return lane.get();
		}
		return null;
	}

	/**
	 * Get lane by lane id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Lane getHomePlazaById(Integer plazaId) {
		Optional<Lane> plaza = homeLanePlazaList.stream().filter(e -> plazaId.equals(e.getPlazaId())).findFirst();
		if (plaza.isPresent()) {
			return plaza.get();
		}
		return null;
	}
	
	/**
	 * Get lane by lane id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Lane getHomeLaneById(Integer laneId) {
		Optional<Lane> lane = homeLanePlazaList.stream().filter(e -> laneId.equals(e.getLaneId())).findFirst();
		if (lane.isPresent()) {
			return lane.get();
		}
		return null;
	}
	
	/**
	 * Get TCode for ETC tx status
	 * 
	 * @param codeValue
	 * @return TCode
	 */
	public TCode geEtcTxStatusByCodeValue(String codeValue) {
		Optional<TCode> accountStatus = etcStatusList.stream().filter(e -> codeValue.equalsIgnoreCase(e.getCodeValue()))
				.findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}

	
	

	/**
	 * Get TCode for tx status
	 * 
	 * @param codeValue
	 * @return TCode
	 */
	public TCode geTxStatusByCodeValue(String codeValue) {
		Optional<TCode> accountStatus = txStatusList.stream().filter(e -> codeValue.equalsIgnoreCase(e.getCodeValue()))
				.findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}

	public String getPlateType(int codeId) {
		Optional<TCode> accountStatus = etcStatusList.stream().filter(e -> codeId==e.getCodeId())
				.findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get().getDisplayValue();
		}
		return "";
	}
	
	/**
	 * Get Vehicle Class By Agency ID and Actual Class
	 * 
	 * @param agencyId
	 * @param actualClass
	 * @return VehicleClass
	 */
	public VehicleClass getVehClassByAgencyIdandActualClass(Integer agencyId, Integer actualClass) {
		Optional<VehicleClass> vehicleClass = vehicleClassList.stream()
				.filter(e -> (agencyId.equals(e.getAgencyId()) && (actualClass.equals(e.getAgencyClass()))))
				.findFirst();
		if (vehicleClass.isPresent()) {
			return vehicleClass.get();
		}
		return null;
	}

	
	/**
	 * Get Cut off date for Agency
	 * 
	 * @param param
	 * @return LocalDateTime
	 */
	public LocalDateTime getCutOffDateForAgency(String param) {
		return cutOffDateMap.get(param);
	}


	/**
	 * Get Poaching limit by Agency
	 * 
	 * @param agencyId
	 * @return
	 */
	public Integer getPoachingLimitByAgency(Integer agencyId) {
		return agencyPoachingTimeLimitMap.get(agencyId);
	}

	@PostConstruct
	public void init() {
		try {
			logger.info("Loading intial master data successfully");
			agencyList = agencyDao.getAgency();
			laneList = laneDao.getLane();
			vehicleClassList = vehicleClassDao.getVehicleClass();
			distanceDiscountList = distanceDiscountDao.getDistanceDiscount();
			plazaList = plazaDao.getPlaza();
			etcStatusList = tCodeDao.getEtcTxStatus();
			txStatusList = tCodeDao.getTxStatus();
			homeLanePlazaList = laneDao.getHomeLanePlazaList();
			Optional<List<ProcessParameter>> optionalCutOffData = processParametersDao.getCutOffDateForAgency();
			if (optionalCutOffData.isPresent()) {
				setAgencyCutOffDateTime(optionalCutOffData.get());
			}
			Optional<List<TollPostLimit>> optionalTollPostLimit = tollPostLimitDao
					.getTollPostLimitByAgencyIdAndEtcTxStatus(1);
			if (optionalTollPostLimit.isPresent()) {
				setAgencyPoachingTimeLimit(optionalTollPostLimit.get());
			}
			logger.info("Loaded intial master data successfully");

		} catch (Exception ex) {
			logger.error("Failed to load intial master data: {}", ExceptionUtils.getStackTrace(ex));

		}

	}

	/**
	 * Set Poaching time limit for Agency
	 * 
	 * @param tollPostLimitList
	 */
	private void setAgencyPoachingTimeLimit(List<TollPostLimit> tollPostLimitList) {
		for (int i = 0; i < tollPostLimitList.size(); i++) {
			agencyPoachingTimeLimitMap.put(tollPostLimitList.get(i).getPlazaAgencyId(),
					localTimeUtility.getSeconds(tollPostLimitList.get(i).getPoachingLimit(), "HH:mm:ss"));
		}
	}

	/**
	 * Set Agency cut off Date time
	 * 
	 * @param processParameterList
	 */
	private void setAgencyCutOffDateTime(List<ProcessParameter> processParameterList) {
		cutOffDateList = processParametersDao.getCutOffDateForAgency().get();
		LocalDateTime tempLocalDateTime = null;
		for (int i = 0; i < cutOffDateList.size(); i++) {
			try {
				tempLocalDateTime = localDateTimeUtility.getLocalDateTime(cutOffDateList.get(i).getParamValue(),
						"yyyyMMddHHmmss");
			} catch (DateTimeParseException e) {
				logger.error("Exception message: {}", ExceptionUtils.getStackTrace(e));
			}

			cutOffDateMap.put(cutOffDateList.get(i).getParamName(), tempLocalDateTime);
		}
	}

	/**
	 * Get Revenue Status By Plaza
	 * 
	 * @param plazaId
	 * @return RevenueDateStatisticsDto
	 */
	public RevenueDateStatisticsDto getRevenueStatusByPlaza(Integer plazaId) {
		RevenueDateStatisticsDto tempRevenueDateStatisticsDto = revenueStatsByAgencyMap.get(plazaId);
		if (tempRevenueDateStatisticsDto != null) {
			return tempRevenueDateStatisticsDto;
		} else {
			tempRevenueDateStatisticsDto = new RevenueDateStatisticsDto();
			Optional<Plaza> tempOptionalPlaza = plazaList.stream().filter(e -> plazaId.equals(e.getPlazaId()))
					.findFirst();
			if (tempOptionalPlaza.isPresent()) {
				Plaza tempPlaza = tempOptionalPlaza.get();
				tempRevenueDateStatisticsDto.setPlazaId(tempPlaza.getPlazaId());
				tempRevenueDateStatisticsDto.setRevenueTime(tempPlaza.getRevenueTime());
				//tempRevenueDateStatisticsDto.setRevenueSecondOfDay(LocalTime.parse("23:59:59", DateTimeFormatter.ofPattern("H:mm:ss")).toSecondOfDay());
				tempRevenueDateStatisticsDto.setRevenueSecondOfDay(
						LocalTime.parse(tempPlaza.getRevenueTime(), DateTimeFormatter.ofPattern("H:mm:ss")).toSecondOfDay());
				revenueStatsByAgencyMap.put(plazaId, tempRevenueDateStatisticsDto);
			}

		}
		return revenueStatsByAgencyMap.get(plazaId);
	}
}
