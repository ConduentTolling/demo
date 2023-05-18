package com.conduent.tpms.unmatched.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.conduent.tpms.unmatched.model.AccountInfo;
import com.conduent.tpms.unmatched.model.AccountPlanDetail;
import com.conduent.tpms.unmatched.model.DeviceAway;
import com.conduent.tpms.unmatched.model.DeviceStatus;
import com.conduent.tpms.unmatched.model.ProcessParameter;


/**
 * Account Information Interface
 * 
 * @author deepeshb
 *
 */
public interface AccountInfoDao {

	/**
	 *Get DeviceStatus Info
	 */
	DeviceStatus getDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp);

	/**
	 *Get H Device Status Info
	 */
	DeviceStatus getHDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp);

	/**
	 *Get HA Tag Status Info
	 */
	DeviceStatus getHATagStatus(String deviceNumber, String txTimeStamp);

	/**
	 *Get Account Info
	 */
	AccountInfo getAccountInfo(Long etcAccountId, String txDate);

	/**
	 *Get Away Device Info
	 */
	DeviceAway findDeviceAway(String deviceNumber, LocalDateTime txTimeStamp);

	/**
	 *Get Process Parameter Info
	 */
	ProcessParameter getProcessParameter(String paramName, Long agencyId);

	/**
	 *Get Device Special Plan Info
	 */
	AccountPlanDetail getDeviceSpecialPlan(Long etcAccountId, Integer planType, Integer agencyId,
			String deviceNumber, String txDate);
	
	public List<ProcessParameter> getAllProcessParameter();

}
