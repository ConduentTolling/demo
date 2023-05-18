package com.conduent.tpms.process25a.dao;

import java.util.List;

import com.conduent.tpms.process25a.model.AgencyInfoVO;
import com.conduent.tpms.process25a.model.ZipCode;

public interface TAgencyDao {
	public List<AgencyInfoVO> getAgencyInfoList();

	List<ZipCode> getZipCodeList();

}
