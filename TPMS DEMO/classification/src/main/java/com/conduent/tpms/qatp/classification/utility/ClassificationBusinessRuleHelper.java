package com.conduent.tpms.qatp.classification.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Businnes Rule for Transaction Classification
 * 
 * @author Sameer
 *
 */
@Component
public class ClassificationBusinessRuleHelper {

	/**
	 * Check Device and Toll System Type B,C,P
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	public boolean checkDeviceAndTollSystem(TransactionDto tempTxDto) {
		return ((StringUtils.isBlank(tempTxDto.getDeviceNo())
				|| QatpClassificationConstant.DEFAULT_DEVICE_VALUE.equalsIgnoreCase(tempTxDto.getDeviceNo()))
				&& checkTollSystemType(tempTxDto));
	}

	/**
	 * Check Toll System Type B,C,P
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	private boolean checkTollSystemType(TransactionDto tempTxDto) {
		return QatpClassificationConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_C.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_P.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_X.equalsIgnoreCase(tempTxDto.getTollSystemType());
				
	}

	/**
	 * Check Toll System Type B,C,P,T
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	public boolean checkTollSystem(TransactionDto tempTxDto) {
		return QatpClassificationConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_C.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_P.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_T.equalsIgnoreCase(tempTxDto.getTollSystemType());
	}

	/**
	 * Check Toll System Type E and X
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	public boolean checkTollSystemTypeForEAndX(TransactionDto tempTxDto) {
		return QatpClassificationConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTxDto.getTollSystemType())
				|| QatpClassificationConstant.TOLL_SYSTEM_TYPE_X.equalsIgnoreCase(tempTxDto.getTollSystemType());
	}

	/**
	 * Check home device, transaction type and device status
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkHomeDevTxTypeAndDeviceStatus(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		return (QatpClassificationConstant.HOME_ACCOUNT_DEVICE.equals(tempAccountInfoDto.getHomeAgencyDev())
				&& QatpClassificationConstant.V.equalsIgnoreCase(tempTxDto.getTxType())
				&& QatpClassificationConstant.ACTIVE_DEVICE_STATUS.equals(tempAccountInfoDto.getDeviceStatus()));
	}

	/**
	 * Check active device status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkActiveDeviceStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.ACTIVE_DEVICE_STATUS.equals(tempAccountInfoDto.getDeviceStatus());
	}

	/**
	 * Check Home Device and Transaction Type
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkHomeDevTxTypeAndViolationStatus(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		return (QatpClassificationConstant.HOME_ACCOUNT_DEVICE.equals(tempAccountInfoDto.getHomeAgencyDev())
				&& QatpClassificationConstant.V.equalsIgnoreCase(tempTxDto.getTxType()));
	}

	/**
	 * Check IAG status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkIAGStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.IAG_INVALID_ZERO_STATUS.contains(tempAccountInfoDto.getIagStatus());
	}

	/**
	 * Check Account Status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkAccountStatus(AccountInfoDto tempAccountInfoDto) {
		return tempAccountInfoDto.getActStatus().equals(QatpClassificationConstant.ACTIVE_ACCOUNT_STATUS);
	}

	/**
	 * Check Home Device
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkHomeDev(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.HOME_ACCOUNT_DEVICE.equals(tempAccountInfoDto.getHomeAgencyDev());
	}

	/**
	 * Check Active Account Status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkActiveAccountStatus(AccountInfoDto tempAccountInfoDto) {
		return tempAccountInfoDto.getActStatus().equals(QatpClassificationConstant.ACTIVE_ACCOUNT_STATUS);
	}

	/**
	 * Check Non Zero Fin Status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkNonZeroFinStatus(AccountInfoDto tempAccountInfoDto) {
		return tempAccountInfoDto.getFinStatus() != 0;
	}

	/**
	 * Check Speed Status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkSpeedStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.SPEED_WARN_REVOKE_STATUS.contains(tempAccountInfoDto.getSpeedStatus());
	}

	/**
	 * Check Speed Validation
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkSpeedValidation(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		if (tempTxDto.getVehicleSpeed() != null) {
			return tempTxDto.getVehicleSpeed() > tempAccountInfoDto.getSpeedLimit();
		}
		return false;
	}

	/**
	 * Check Speed Threshold Validation
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkSpeedThresholdValidation(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto) {
		return tempTxDto.getVehicleSpeed() > tempAccountInfoDto.getSpeedLimit();
	}

	/**
	 * Check suspended postpaid status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkSuspendedPostPaidStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.SUSPENDED_POST_PAID_STATUS.equals(tempAccountInfoDto.getPostPaidStatus());
	}

	/**
	 * Check Non Zero Fin Status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkZeroFinStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.ZERO_FIN_STATUS.equals(tempAccountInfoDto.getFinStatus());
	}

	/**
	 * Check home device, active device and revoke account status
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkHomeDeviceAndDeviceAndAccountStatus(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.HOME_ACCOUNT_DEVICE.equals(tempAccountInfoDto.getHomeAgencyDev())
				&& QatpClassificationConstant.ACTIVE_DEVICE_STATUS.equals(tempAccountInfoDto.getDeviceStatus())
				&& QatpClassificationConstant.ACCOUNT_STATUS_RVKW.equals(tempAccountInfoDto.getActStatus());
	}

	/**
	 * Check Rebill condition
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkRebillCondition(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.REBII_TYPE_CC.contains(tempAccountInfoDto.getRebillType());
	}

	/**
	 * Check Rebill Cash Check Condition
	 * 
	 * @param AccountInfoDto
	 * @return boolean
	 */
	public boolean checkRebillCashCheckCondition(AccountInfoDto tempAccountInfoDto) {
		return QatpClassificationConstant.REBII_TYPE_CC_CASH_CHECK.contains(tempAccountInfoDto.getRebillType());
	}

	public boolean checkTxInfoVehAndTagClass(TransactionDto tempTxDto) {
		return tempTxDto.getActualClass() != null && tempTxDto.getTagClass() != null && tempTxDto.getActualClass() > 4
				&& tempTxDto.getTagClass() <= 3;

	}
}
