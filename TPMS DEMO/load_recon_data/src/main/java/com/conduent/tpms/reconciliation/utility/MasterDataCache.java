package com.conduent.tpms.reconciliation.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.reconciliation.dao.ReconciliationDao;
import com.conduent.tpms.reconciliation.dto.PlazaDto;
import com.conduent.tpms.reconciliation.dto.TLaneDto;
import com.conduent.tpms.reconciliation.model.Agency;
import com.conduent.tpms.reconciliation.model.ProcessParameter;
import com.conduent.tpms.reconciliation.model.TCode;


@Component
public class MasterDataCache {

	private static final Logger logger = LoggerFactory.getLogger(MasterDataCache.class);
	
	@Autowired
	private ReconciliationDao reconciliationDAO;

	private List<Agency> agencyList;
	private List<TCode> accountStatusList;
	private List<TCode> accountTypeList;
	private List<TCode> finStatusList;
	private List<TCode> deviceStatusList;
	private List<TCode> etcStatusList;
	private List<ProcessParameter> processParameterList;
	
	private List<TLaneDto> laneList;
	private List<PlazaDto> plazaList;
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
	
	// added newly
		public ProcessParameter getProcessParameter(String paramName, Long agencyId ) {
			Optional<ProcessParameter> processParameter = processParameterList.stream().filter(e -> paramName.equals(e.getParamName())
					&& agencyId.equals(e.getAgencyId()))
					.findFirst();
			if (processParameter.isPresent()) {
				return processParameter.get();
			}
			return null;
		}

	@PostConstruct
	public void init() {
		try {
			logger.info("Loading intial master data successfully");

//			agencyList = agencyDao.getAll();
//			accountStatusList = tCodeDao.getAccountStatus();
//			finStatusList = tCodeDao.getFinStatus();
//			deviceStatusList = tCodeDao.getDeviceStatus();
//			accountTypeList = tCodeDao.getAccountType();
//			etcStatusList = tCodeDao.getTxStatus();
//			processParameterList = accountInfoDao.getAllProcessParameter();
			plazaList = reconciliationDAO.getPlazaInfo();
			laneList =  reconciliationDAO.getLaneInfo();
			logger.info("Loaded intial master data successfully");

		} catch (Exception ex) {
			logger.error("Failed to load intial master data: {}", ex.getMessage());

		}

	}

	public PlazaDto getPlaza(Integer plazaId) {
		Optional<PlazaDto> plaza=plazaList.stream().filter(e->e.getPlazaId().intValue()==plazaId.intValue()).findFirst();
		if(plaza.isPresent())
		{
			return plaza.get();
		}
		return null;
	}

	public TLaneDto getLane(Integer laneId) {
		Optional<TLaneDto> lane=laneList.stream().filter(e->e.getLaneId().intValue()==laneId.intValue()).findFirst();
		if(lane.isPresent())
		{
			return lane.get();
		}
		return null;
	}
}
