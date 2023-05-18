package com.conduent.tpms.qatp.service;

import java.sql.SQLException;
import java.util.List;

import com.conduent.tpms.qatp.dto.Exclusion;


public interface ExclusionService {

	public boolean checkExclusionRecords(List<Exclusion> ExclusionInfo,Exclusion exclusion) throws SQLException;
}