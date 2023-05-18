package com.conduent.tpms.qeval.dao;

import java.util.List;

import com.conduent.tpms.qeval.dto.AgencyInfoVO;
import com.conduent.tpms.qeval.model.TranDetail;

public interface TAgencyDao {

	List<AgencyInfoVO> getAgencyInfoList();

	String getUnresgisteredStaus(TranDetail tranDetail);


}
