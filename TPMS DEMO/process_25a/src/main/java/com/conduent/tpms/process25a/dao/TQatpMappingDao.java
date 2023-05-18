package com.conduent.tpms.process25a.dao;


import java.util.List;

import com.conduent.tpms.process25a.model.AgencyInfoVO;
import com.conduent.tpms.process25a.model.TagDeviceDetails;
import com.conduent.tpms.process25a.model.TranDetail;

public interface TQatpMappingDao {
	public void insertaddressinaddresstable(int address_id);
	List<AgencyInfoVO> getAgencyDetails();
	public void updateTranDetail(TranDetail tranDetail);
	public void updateTranDetailPostedAmt(TranDetail tranDetail);
	public TagDeviceDetails checkRecordExistInDevice(String device_no);
	public List<String> getZipCodeList();	
}
