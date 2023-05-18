package com.conduent.tpms.qatp.classification.dao;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.conduent.tpms.qatp.classification.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.AccountPlanDetail;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
import com.conduent.tpms.qatp.classification.model.PlanDetails;
import com.conduent.tpms.qatp.classification.model.ProcessParameter;

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
	List<DeviceStatus> getDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp);

	/**
	 *Get H Device Status Info
	 */
	List<DeviceStatus> getHDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp);

	/**
	 *Get HA Tag Status Info
	 */
	List<DeviceStatus> getHATagStatus(String deviceNumber, String deviceNo11charecter, String txTimeStamp);

	/**
	 *Get Account Info
	 */
	AccountInfo getAccountInfo(Long etcAccountId, String txDate);

	/**
	 *Get Away Device Info
	 */
	List<DeviceAway> findDeviceAway(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp);

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

	AccountApiInfoDto getAccountInfoFromFPMS(String etcAccountId);

	Long getAgency(Long etcAccountId);

	PlanDetails getPlanName(Long etcAccountId);
}
