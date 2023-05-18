package com.conduent.tpms.iag.dao;

import java.time.OffsetDateTime;
import java.util.List;

import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.TCodesVO;
import com.conduent.tpms.iag.model.AccountInfo;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;
import com.conduent.tpms.iag.model.LaneTxReconPolicy;
import com.conduent.tpms.iag.model.TollPostLimitInfo;
import com.conduent.tpms.iag.model.TranDetail;

public interface TAgencyDao {
	public List<AgencyInfoVO> getAgencyInfoList();

	//public List<ZipCode> getZipCodeList();
	public List<TCodesVO> getTCodesList() ;
	//public List<VehicleClass> getVehicleClassList() ;
	public List<LaneTxReconPolicy> getLaneTxReconPolicyList() ;
	public List<LaneIdExtLaneInfo> getLaneIdExtLaneIdInfo() ;
	public AccountInfo getAccountInfoOne(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp);
	public AccountInfo getAccountInfoTwo(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp);
	public AccountInfo getAccountInfoThree(String deviceNo14, String deviceNo11, String txDate);
	public Integer getHaDevicesIagTagStatus(String deviceNo14, String deviceNo11, String txDate);
	public void getTxStatusCodeId(TranDetail tranDetail) ;
	public AccountInfo getAccountInfo(TranDetail tranDetail);
	public String getUnresgisteredStaus(TranDetail tranDetail);
	public List<AgencyInfoVO> getHomeAgencyInfoList();
	public List<TollPostLimitInfo> getTollPostLimits();
}
