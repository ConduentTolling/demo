package com.conduent.tpms.qeval.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qeval.config.LoadJpaQueries;
import com.conduent.tpms.qeval.constants.Constants;
import com.conduent.tpms.qeval.dao.QEvaluationFpmsDao;

@Repository
public class QEvaluationFpmsDaoImpl implements QEvaluationFpmsDao {

	private static final Logger log = LoggerFactory.getLogger(QEvaluationFpmsDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public String getLastEvalRunDate() {

		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_EVAL_DATE");
		//String queryRules = "SELECT   to_char(last_rundate + 1,'DD' ) FROM fpms.t_stmt_run_date WHERE stmt_run_id = 401 and file_type='QVAL'";
		MapSqlParameterSource paramSource=new MapSqlParameterSource();
		paramSource.addValue(Constants.STMTRUNID, Constants.STMTRUNID_VALUE);
		
		
		String date=namedParameterJdbcTemplate.queryForObject(queryRules, paramSource, String.class);
		//log.info("Last STMTRUNID Run date...."+date);
		//List<String> date = jdbcTemplate.queryForList(queryRules, (String.class));
		if(date.isEmpty()) {
			return null;
		}
		//System.out.println(date);
		return date;

	}

/*
		Stopwatch stopwatch = Stopwatch.createStarted();
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		dao_log.info("Total time taken to execute getLaneIdExtLaneIdInfo query {}", millis);

	} */
}
