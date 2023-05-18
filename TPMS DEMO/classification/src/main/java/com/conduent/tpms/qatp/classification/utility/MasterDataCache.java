package com.conduent.tpms.qatp.classification.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dao.TAgencyDao;
import com.conduent.tpms.qatp.classification.dao.TCodeDao;
import com.conduent.tpms.qatp.classification.dto.Plaza;
import com.conduent.tpms.qatp.classification.dto.TLaneDto;
import com.conduent.tpms.qatp.classification.dto.TollScheduleDto;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.AgencyInfoVO;
import com.conduent.tpms.qatp.classification.model.ProcessParameter;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;

/**
 * Load Data on startup
 * 
 * @author deepeshb
 *
 */
@Component
public class MasterDataCache {

	private static final Logger logger = LoggerFactory.getLogger(MasterDataCache.class);

	@Autowired
	private TAgencyDao agencyDao;

	@Autowired
	private TCodeDao tCodeDao;
	
	@Autowired
	private ComputeTollDao computeTollDAO;
	
	@Autowired
	private AccountInfoDao accountInfoDao;

	private List<Agency> agencyList;
	private List<TCode> accountStatusList;
	private List<TCode> accountTypeList;
	private List<TCode> finStatusList;
	private List<TCode> deviceStatusList;
	private List<TCode> etcStatusList;
	private List<TollPriceSchedule> tollPriceScheduleList;
	private List<TollScheduleDto> tollScheduleDtoList;
	private List<ProcessParameter> processParameterList;
	private List<TollPostLimit> tollPostLimitList;
	private List<ProcessParameter> processParamtersListParking;
	private List<TLaneDto> laneList;
	private List<Plaza> plazaList;
	
	//Parking
	@Autowired
	private TAgencyDao tAgencyDao;

	List<AgencyInfoVO> agencyInfoVOList;
	
	List<AgencyInfoVO> agencyInfoVOListHome;
	

	public List<AgencyInfoVO> getAgencyInfoVOList() {
		return agencyInfoVOList;
	}
	public void setAgencyInfoVOList(List<AgencyInfoVO> agencyInfoVOList) {
		this.agencyInfoVOList = agencyInfoVOList;
	}
	public List<AgencyInfoVO> getAgencyInfoVOListHome() {
		return agencyInfoVOListHome;
	}
	public void setAgencyInfoVOListHome(List<AgencyInfoVO> agencyInfoVOListHome) {
		this.agencyInfoVOListHome = agencyInfoVOListHome;
	}
	
	public boolean isHomeAgency(String devicePrefix)
	{
		if(agencyInfoVOListHome == null || agencyInfoVOListHome.isEmpty() || agencyInfoVOListHome.size()==0)
		{
			return false;
		}
		else
		{
			Optional<AgencyInfoVO> agency = agencyInfoVOListHome.stream().filter(e-> devicePrefix!=null && e.getDevicePrefix().equals(devicePrefix)).findFirst();
			if(agency.isPresent())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}
	
	public boolean isAwayAgency(String devicePrefix)
	{
		if(agencyInfoVOList == null || agencyInfoVOList.isEmpty() || agencyInfoVOList.size()==0)
		{
			return false;
		}
		else
		{
			Optional<AgencyInfoVO> agency = agencyInfoVOList.stream().filter(e-> e.getDevicePrefix()!=null && 
					devicePrefix!=null && e.getDevicePrefix().equals(devicePrefix)).findFirst();
			if(agency.isPresent())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}
	/**
	 * Get Agency Info
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
	// added newly
	public Agency getAgencyById(Long agencyId) {
		logger.info("Agency ID  {}",agencyId);
		logger.info("Agency List  {}",agencyList);
		Optional<Agency> agency = agencyList.stream().filter(e -> agencyId.equals(e.getAgencyId()))
				.findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}
	// added newly
	public TollPriceSchedule getPriceSchedule(LocalDate effectiveDate, LocalDate expiryDate, String daysInd,Integer agencyId ) {
		Optional<TollPriceSchedule> price = tollPriceScheduleList.stream().filter(e -> agencyId.equals(e.getAgencyId())
				&& e.getDaysInd().equals(daysInd) && e.getEffectiveDate().isBefore(effectiveDate) && e.getExpiryDate().isAfter(expiryDate))
				.findFirst();
		if (price.isPresent()) {
			return price.get();
		}
		return null;
	}
	// added newly
	
	public List<TollScheduleDto> getTollAmountByRevenueType(LocalDate txDate, Integer plazaId, Integer entryPlazaId,
			Integer vehicleClass, List<String> revenueTypeIdList, Long planTypeId, Integer priceScheduleId) {

		List<TollScheduleDto> list = tollScheduleDtoList.stream()
				.filter(e -> e.getEffectiveDate().isBefore(txDate) && plazaId.equals(e.getPlazaId())
						&& txDate.isAfter(e.getExpirayDate()) && entryPlazaId.equals (e.getEntryPlazaId())
						&& vehicleClass.equals(e.getVehicleClass()) && revenueTypeIdList.equals(e.getRevenueTypeIdList())
						&& planTypeId.equals(e.getPlanTypeId()) && priceScheduleId.equals(e.getPriceScheduleId()))
				.collect(Collectors.toList());
		/*
		 * if (tollSchedule.isPresent()) { return tollSchedule.get(); }
		 */
		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}
	 
	
	// added newly
	public ProcessParameter getProcessParameter(String paramName, Long agencyId ) {
		Optional<ProcessParameter> processParameter = processParameterList.stream().filter(e -> paramName.equals(e.getParam_name())
				&& agencyId.equals(e.getAgencyId()))
				.findFirst();
		if (processParameter.isPresent()) {
			return processParameter.get();
		}
		return null;
	}
		
	/**
	 * Get TCode for Account Status
	 * 
	 * @param codeId
	 * @return TCode
	 */
	public TCode getAccountStatusByCodeId(Integer codeId) {
		Optional<TCode> accountStatus = accountStatusList.stream().filter(e -> codeId.equals(e.getCodeId()))
				.findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}

	/**
	 * Get TCode for Fin Status
	 * 
	 * @param codeId
	 * @return TCode
	 */
	public TCode getFinStatusByCodeId(Integer codeId) {
		Optional<TCode> accountStatus = finStatusList.stream().filter(e -> codeId.equals(e.getCodeId())).findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}

	/**
	 * Get TCode for Device Status
	 * 
	 * @param codeId
	 * @return TCode
	 */
	public TCode getDeviceStatusByCodeId(Integer codeId) {
		Optional<TCode> accountStatus = deviceStatusList.stream().filter(e -> codeId.equals(e.getCodeId())).findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}

	/**
	 * Get TCode for Account Type
	 * 
	 * @param codeValue
	 * @return TCode
	 */
	public TCode getAccountTypeByCodeValue(String codeValue) {
		Optional<TCode> accountType = accountTypeList.stream().filter(e -> codeValue.equalsIgnoreCase(e.getCodeValue()))
				.findFirst();
		if (accountType.isPresent()) {
			return accountType.get();
		}
		return null;
	}

	/**
	 * Get TCode for Account Type
	 * 
	 * @param codeValue
	 * @return TCode
	 */
	public TCode getFinStatusByCodeValue(String codeValue) {
		Optional<TCode> accountStatus = finStatusList.stream().filter(e -> codeValue.equalsIgnoreCase(e.getCodeValue()))
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
		Optional<TCode> accountStatus = etcStatusList.stream().filter(e -> codeValue.equalsIgnoreCase(e.getCodeValue()))
				.findFirst();
		if (accountStatus.isPresent()) {
			return accountStatus.get();
		}
		return null;
	}
	
	public TollPostLimit getTollPostLimit(Integer plazaAgencyId,Integer txStatus)
	{
		if(plazaAgencyId==null || tollPostLimitList==null)
		{
			return null;
		}
		Optional<TollPostLimit> tollPostLimit=tollPostLimitList.stream().filter(e->e.getPlazaAgencyId().intValue()==plazaAgencyId.intValue() && e.getEtcTxStatus().intValue()==txStatus.intValue() ).findFirst();
		if(tollPostLimit.isPresent())
		{
			return tollPostLimit.get();
		}
		return null;
	}

	@PostConstruct
	public void init() {
		try {
			logger.info("Loading intial master data successfully");

			agencyList = agencyDao.getAll();
//			accountStatusList = tCodeDao.getAccountStatus();			
//			deviceStatusList = tCodeDao.getDeviceStatus();			
//			tollPriceScheduleList = computeTollDAO.getAllPriceSchedule();
//			tollScheduleDtoList = computeTollDAO.getAllTollSchedule();
//			processParameterList = accountInfoDao.getAllProcessParameter();
//			
//			
//			LocalDateTime fromTime1 = LocalDateTime.now();
//			tollPostLimitList=computeTollDAO.getTollPostLimit();
//			logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in tollPostLimitList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime1, LocalDateTime.now()));
//			logger.info("Toll Post Limit data loading completed");
			

			logger.info("Loaded intial master data successfully");
			
			//Parking_Classification
			agencyList = agencyDao.getAll();
			finStatusList = tCodeDao.getFinStatus();
			accountTypeList = tCodeDao.getAccountType();
			tollPostLimitList=computeTollDAO.getTollPostLimit();
			etcStatusList = tCodeDao.getTxStatus();
//			agencyInfoVOListHome = tAgencyDao.getAgencyInfoListforHome();
	//		agencyInfoVOList = tAgencyDao.getAgencyInfoListforAway();
			processParamtersListParking = accountInfoDao.getProcessParamtersList();
			plazaList = accountInfoDao.getAll();
			laneList = accountInfoDao.getAllTLane();

		} catch (Exception ex) {
			logger.error("Failed to load intial master data: {}", ex.getMessage());

		}

	}
	public String getParkingThresholdValue() {
		if(processParamtersListParking!=null)
		{
			Optional<ProcessParameter> processParam = processParamtersListParking.stream().findFirst();
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
	
	public String getPlazaFromValue(Integer plazaId)
	{
		if(plazaId==null || plazaList==null)
		{
			return null;
		}
		Optional<Plaza> plaza=plazaList.stream().filter(e->e.getPlazaId().intValue()==plazaId.intValue()).findFirst();
		if(plaza.isPresent())
		{
			return plaza.get().getExternPlazaId();
		}
		return null;
	}
	
	public String getLaneFromValue(Integer plazaId)
	{
		if(plazaId==null || laneList==null)
		{
			return null;
		}
		Optional<TLaneDto> lane=laneList.stream().filter(e->e.getLaneId().intValue()==plazaId.intValue()).findFirst();
		if(lane.isPresent())
		{
			return lane.get().getExternLaneId();
		}
		return null;
	}
}
