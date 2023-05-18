package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.ZipCode;

public interface TAgencyDao {
	public List<AgencyInfoVO> getAgencyInfoList();

	List<ZipCode> getZipCodeList();

}
