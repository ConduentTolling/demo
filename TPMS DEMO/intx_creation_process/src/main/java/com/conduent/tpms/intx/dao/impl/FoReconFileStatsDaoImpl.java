package com.conduent.tpms.intx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.FoReconFileStatsDao;
import com.conduent.tpms.intx.model.FoReconFileStats;
import com.conduent.tpms.intx.utility.LocalDateTimeUtility;

import ch.qos.logback.classic.Logger;

@Repository
public class FoReconFileStatsDaoImpl implements FoReconFileStatsDao {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(FoReconFileStatsDaoImpl.class);	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	LocalDateTimeUtility localDateTimeUtility;
	
	@Override
	public void insertInFoReconFileStats(FoReconFileStats fileStats, String header) {
		logger.info("Insert into FoReconFileStats...");	
		String query = LoadJpaQueries.getQueryById(IntxConstant.INSERT_INTO_T_FO_RECON_FILE_STATS);
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, 0);
				ps.setString(2, fileStats.getTrxEpsFileName());
				ps.setLong(3, fileStats.getTrxEpsCount());
				ps.setDouble(4, fileStats.getTrxEpsAmount());
				ps.setString(5, "N");
				ps.setNull(6, java.sql.Types.VARCHAR);
				ps.setNull(7, java.sql.Types.NUMERIC);
				ps.setNull(8, java.sql.Types.VARCHAR);
				ps.setNull(9, java.sql.Types.VARCHAR);
				ps.setString(10, header.substring(4, 7));
				ps.setString(11, header.substring(7, 10));
				ps.setLong(12, Long.parseLong(header.substring(32, 38), 10));
				ps.setTimestamp(13, java.sql.Timestamp.from(Instant.now()));
				return ps;
			}
		};
		
		jdbcTemplate.update(psc);
		logger.info("Inserted into FoReconFileStats.");
	}

	@Override
	public void updateFoReconFileStats(FoReconFileStats FoReconFileStats) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FoReconFileStats getFoReconFileStats(FoReconFileStats FoReconFileStats) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoReconFileStats getAtpFileId(FoReconFileStats FoReconFileStats) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRecordCount(String fileName, Long recordCount) {
		// TODO Auto-generated method stub
		
	}

}
