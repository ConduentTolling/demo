package com.conduent.tpms.intx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constant.IntxSchedulerConstant;
import com.conduent.tpms.intx.dao.ExternalFileDao;
import com.conduent.tpms.intx.model.ExternalFile;

@Repository
public class ExternalFileDaoImpl implements ExternalFileDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * Get All External File IDs of an Agency
	 */
	@Override
	public List<ExternalFile> getExternalFilesByAgency(String devicePrefix) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxSchedulerConstant.GET_DISTINCT_EXTERN_FILE_IDS);
		paramSource.addValue(IntxSchedulerConstant.DEVICE_PREFIX, devicePrefix + "%");
		List<ExternalFile> tempExternalFileList = namedParameterJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(ExternalFile.class));
		if (tempExternalFileList != null) {
			return tempExternalFileList;
		}
		return new ArrayList<>();
	}
	
	/**
     * Get All Correction External File IDs of an Agency
     */
    @Override
    public List<ExternalFile> getCorrExternalFilesByAgency(String devicePrefix) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        String queryFile = LoadJpaQueries.getQueryById(IntxSchedulerConstant.GET_DISTINCT_CORR_EXTERN_FILE_IDS);
        paramSource.addValue(IntxSchedulerConstant.DEVICE_PREFIX, devicePrefix + "%");
        List<ExternalFile> tempExternalFileList = namedParameterJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(ExternalFile.class));
        if (tempExternalFileList != null) {
            return tempExternalFileList;
        }
        return new ArrayList<>();
    }

}
