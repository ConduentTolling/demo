package com.conduent.tpms.unmatched.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.unmatched.dao.AccountInfoDao;
import com.conduent.tpms.unmatched.dao.TAgencyDao;
import com.conduent.tpms.unmatched.dao.TCodeDao;
import com.conduent.tpms.unmatched.model.Agency;
import com.conduent.tpms.unmatched.model.ProcessParameter;
import com.conduent.tpms.unmatched.model.TCode;


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
	private AccountInfoDao accountInfoDao;

	private List<Agency> agencyList;
	private List<TCode> accountStatusList;
	private List<TCode> accountTypeList;
	private List<TCode> finStatusList;
	private List<TCode> deviceStatusList;
	private List<TCode> etcStatusList;
	private List<ProcessParameter> processParameterList;
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

			agencyList = agencyDao.getAll();
			accountStatusList = tCodeDao.getAccountStatus();
			finStatusList = tCodeDao.getFinStatus();
			deviceStatusList = tCodeDao.getDeviceStatus();
			accountTypeList = tCodeDao.getAccountType();
			etcStatusList = tCodeDao.getTxStatus();
			processParameterList = accountInfoDao.getAllProcessParameter();

			logger.info("Loaded intial master data successfully");

		} catch (Exception ex) {
			logger.error("Failed to load intial master data: {}", ex.getMessage());

		}

	}
}
