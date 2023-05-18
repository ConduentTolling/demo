package com.conduent.tpms.intx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.FoFileStatsDao;
import com.conduent.tpms.intx.model.FoReconFileStats;

import ch.qos.logback.classic.Logger;

@Repository
public class FoFileStatsDaoImpl implements FoFileStatsDao {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(FoFileStatsDaoImpl.class);	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override 
	public void insertFoFileStats(FoReconFileStats foReconFileStats, String header) {
		logger.info("Insert into FoFileStats...");	
		String query = LoadJpaQueries.getQueryById(IntxConstant.INSERT_INTO_T_FO_FILE_STATS);
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, 0);
				ps.setString(2, foReconFileStats.getTrxEpsFileName());
				ps.setNull(3, java.sql.Types.VARCHAR);
				ps.setString(4, "N");
				ps.setLong(5, foReconFileStats.getTrxEpsCount());
				ps.setLong(6, 0);
				ps.setString(7, header.substring(4, 7));
				ps.setString(8, header.substring(7, 10));
				ps.setDate(9,  java.sql.Date.valueOf(LocalDate.parse(header.substring(10, 18), IntxConstant.LOCALDATEFORMATTER_yyyyMMdd)));
				ps.setLong(10, Long.parseLong(header.substring(32, 38), 10));
				ps.setString(11, header.substring(0, 4));
				ps.setTimestamp(12, java.sql.Timestamp.from(Instant.now()));
				return ps;
			}
		};
		
		jdbcTemplate.update(psc);
		logger.info("Inserted into FoFileStats.");
	}
	
	@Override 
	public void updateFoFileStats(FoReconFileStats foFileStats) {

	}
	
	@Override
	public void updateRecordCount(String fileName, Long fileRecordCount) {
		
	}

}
