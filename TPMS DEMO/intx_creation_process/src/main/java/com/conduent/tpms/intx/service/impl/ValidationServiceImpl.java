package com.conduent.tpms.intx.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.model.Lane;
import com.conduent.tpms.intx.model.Plaza;
import com.conduent.tpms.intx.service.ValidationService;
import com.conduent.tpms.intx.utility.StaticDataLoad;

import ch.qos.logback.classic.Logger;

@Service
public class ValidationServiceImpl implements ValidationService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(ValidationServiceImpl.class);	
	
	@Autowired
	StaticDataLoad staticDataLoad;
	
	/**
	 * Validate transaction
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateTx(AwayTransaction tempAwayTx) {
		boolean resultFlag = false;
		if (tempAwayTx != null) {
			resultFlag = validateExitInfo(tempAwayTx);
			if (resultFlag) {
				resultFlag = validateEntryInfo(tempAwayTx);
			//	if (resultFlag) {
					//resultFlag = validateHomePlazaLaneInfo(tempAwayTx);
				//}
			}
		}
		return resultFlag;
	}

	/**
	 * Validate entry info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	@Override
	public Boolean validateEntryInfo(AwayTransaction tempAwayTx) {
		String tempTollSysType = tempAwayTx.getTollSystemType() != null ? tempAwayTx.getTollSystemType() : null;
		if (IntxConstant.TOLL_SYSTEM_TYPE_C.equalsIgnoreCase(tempTollSysType)) {
			if (!validateEntryLaneId(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("L");
				logger.info("Invalid entry lane for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
			if (!validateEntryPlazaId(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("L");
				logger.info("Invalid entry plaza for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
			if (!validateEntryTimestamp(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("D");
				logger.info("Invalid entry transaction timestamp for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate exit info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	@Override
	public Boolean validateExitInfo(AwayTransaction tempAwayTx) {
		if (!validateExitLaneId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid exit lane for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateExitPlazaId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid exit plaza for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateTxDate(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("D");
			logger.info("Invalid transaction date for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateTxTimestamp(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("D");
			logger.info("Invalid transaction timestamp for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		return true;
	}
		
	@Override
	public Boolean validateHomePlazaLaneInfo(AwayTransaction tempAwayTx) {
		if (!validateHomePlazaId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid Home Plaza Id for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateHomeLaneId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid Home lane Id for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean validateHomeLaneId(AwayTransaction tempAwayTx) {
		Integer tempLaneId = tempAwayTx.getLaneId() != null ? tempAwayTx.getLaneId() : null;
		if (tempLaneId != null && tempLaneId != 0) {
			Lane tempLane = staticDataLoad.getHomeLaneById(tempLaneId);
			if (tempLane != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateHomePlazaId(AwayTransaction tempAwayTx) {
		Integer tempPlazaId = tempAwayTx.getEntryPlazaId() != null ? tempAwayTx.getPlazaId() : null; // in case of tx type TC, UX
		if (tempPlazaId != null && tempPlazaId != 0) {
			Lane tempPlaza = staticDataLoad.getHomePlazaById(tempPlazaId);
			if (tempPlaza != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate tx timestamp
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateTxTimestamp(AwayTransaction tempAwayTx) {
//		if (tempAwayTx.getTxTimestamp() != null) {
//			return (tempAwayTx.getTxTimestamp().isEqual(LocalDateTime.now())
//					|| tempAwayTx.getTxTimestamp().isBefore(LocalDateTime.now()));
//		}
		if (tempAwayTx.getEtcTxTimestamp() != null) {
			return (tempAwayTx.getEtcTxTimestamp().isEqual(LocalDateTime.now())
					|| tempAwayTx.getEtcTxTimestamp().isBefore(LocalDateTime.now()));
		}
		return false;
	}

	/**
	 * Validate entry lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateEntryLaneId(AwayTransaction tempAwayTx) {
		Integer tempEntryLaneId = tempAwayTx.getEntryLaneId() != null ? tempAwayTx.getEntryLaneId() : null;
		if (tempEntryLaneId != null && tempEntryLaneId != 0) {
			Lane tempEntryLane = staticDataLoad.getLaneById(tempEntryLaneId);
			if (tempEntryLane != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate entry plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateEntryPlazaId(AwayTransaction tempAwayTx) {
		Integer tempEntryPlazaId = tempAwayTx.getEntryPlazaId() != null ? tempAwayTx.getEntryPlazaId() : null;
		if (tempEntryPlazaId != null && tempEntryPlazaId != 0) {
			Plaza tempEntryPlaza = staticDataLoad.getPlazaById(tempEntryPlazaId);
			if (tempEntryPlaza != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Validate entry timestamp
	 * 
	 * @param tempAwayTx
	 * @return
	 */
	@Override
	public boolean validateEntryTimestamp(AwayTransaction tempAwayTx) {
		if (tempAwayTx.getEntryTimestamp() != null) {
			return (tempAwayTx.getEntryTimestamp().isEqual(LocalDateTime.now())
					|| tempAwayTx.getEntryTimestamp().isBefore(LocalDateTime.now()));
		}
		return false;
	}
	
	/**
	 * Validate tx date
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateTxDate(AwayTransaction tempAwayTx) {
		LocalDate parsedEtcRevenueDate = LocalDate.parse(tempAwayTx.getEtcRevenueDate(), IntxConstant.LOCALDATEFORMATTER_yyyyMMdd);
		if (tempAwayTx.getEtcRevenueDate() != null) {
			return (parsedEtcRevenueDate.isEqual(LocalDate.now())
					|| parsedEtcRevenueDate.isBefore(LocalDate.now()));
		}
		return false;
	}

	/**
	 * Validate exit lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateExitLaneId(AwayTransaction tempAwayTx) {
		Integer tempLaneId = tempAwayTx.getEtcExitLane() != null ? Integer.parseInt(tempAwayTx.getEtcExitLane()) : null;
		if (tempLaneId != null && tempLaneId != 0) {
			Lane tempLane = staticDataLoad.getLaneById(tempLaneId);
			if (tempLane != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate exit plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	@Override
	public boolean validateExitPlazaId(AwayTransaction tempAwayTx) {
		String tempPlazaId = tempAwayTx.getEtcExitPlaza() != null ? tempAwayTx.getEtcExitPlaza() : null;
		if (tempPlazaId != null && tempPlazaId.length() == 3) {
			//Plaza tempPlaza = staticDataLoad.getPlazaById(tempPlazaId);
			//if (tempPlaza != null) {
			//	return true;
			//}
			return true;
		}
		return false;
	}
	
}
