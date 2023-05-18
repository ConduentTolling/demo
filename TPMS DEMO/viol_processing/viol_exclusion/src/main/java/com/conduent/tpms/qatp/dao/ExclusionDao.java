package com.conduent.tpms.qatp.dao;

import java.sql.SQLException;
import java.util.List;

import com.conduent.tpms.qatp.dto.Exclusion;

public interface ExclusionDao {
	public List<Exclusion> getDataFromOracleDb() throws SQLException;
}
