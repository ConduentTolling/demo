package com.conduent.tpms.qatp.classification.dao;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.conduent.tpms.qatp.classification.dto.Plaza;
import com.conduent.tpms.qatp.classification.dto.TLaneDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.AccountPlanDetail;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
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

	public List<ProcessParameter> getProcessParamtersList();

	public List<Plaza> getAll();

	public List<TLaneDto> getAllTLane();

}
