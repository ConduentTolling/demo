package com.conduent.tpms.qatp.dao;

import java.util.List;

import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.model.ProcessParameter;


public interface TAgencyDao {
	public List<AgencyInfoVO> getAgencyInfo();

	public String insertLane();

}
