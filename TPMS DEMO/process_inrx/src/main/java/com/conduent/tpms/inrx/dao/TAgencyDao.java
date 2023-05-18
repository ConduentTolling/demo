package com.conduent.tpms.inrx.dao;

import java.util.List;

import com.conduent.tpms.inrx.model.AgencyInfoVO;
import com.conduent.tpms.inrx.model.ZipCode;

public interface TAgencyDao {
	public List<AgencyInfoVO> getAgencyInfoList();

	List<ZipCode> getZipCodeList();

}
