package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IaStatsDao;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.XferControl;

@Repository
public class IaStatsDaoImpl implements IaStatsDao {

	private static final Logger logger = LoggerFactory.getLogger(IaStatsDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	

	@Override
	public void updateIaFileStats(List<TranDetail> tranDetailList, XferControl xferControl){
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(Constants.UPDATE_ATP_FILE_ID_IN_T_IA_FILE_STATS);
		
		try {
			paramSource.addValue(Constants.ICTX_FILE_NAME, xferControl.getXferFileName());
			paramSource.addValue(Constants.XFER_CONTROL_ID, xferControl.getXferControlId());
			TranDetail tranDetail = tranDetailList.stream()
		            .filter(e -> "I".equals(e.getTxType()))
		            .findFirst()
		            .get();
			
			paramSource.addValue(Constants.ATP_FILE_ID, tranDetail.getAtpFileId());

			namedJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("Exception while inserting data into T_IA_FILE_STATS: {}",paramSource);
			e.printStackTrace();
		}

	}

	
}
