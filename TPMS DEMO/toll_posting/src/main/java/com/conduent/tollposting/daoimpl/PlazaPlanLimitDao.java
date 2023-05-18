package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IPlazaPlanLimitDao;
import com.conduent.tollposting.model.PlazaPlanLimit;

@Repository
public class PlazaPlanLimitDao implements IPlazaPlanLimitDao
{
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public PlazaPlanLimit getPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.ENTRY_PLAZA_ID, entryPlazaId);
		paramSource.addValue(Constants.EXIT_PLAZA_ID, exitPlazaId);
		String sql = "SELECT APD_ID FROM T_PLAZA_PLAN_LIMITS a, MASTER.T_PLAZA p WHERE a.APD_ID = :APD_ID AND a.plaza_entry = :ENTRY_PLAZA_ID AND a.plaza_exit =:EXIT_PLAZA_ID AND rownum < 2";
		return (PlazaPlanLimit) namedJdbcTemplate.queryForObject(sql,paramSource, new BeanPropertyRowMapper<PlazaPlanLimit>(PlazaPlanLimit.class));

	}

	@Override
	public PlazaPlanLimit getPlazaPlazaPlanLimit(Integer entryPlazaId, Integer exitPlazaId, String apdId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.ENTRY_PLAZA_ID, entryPlazaId);
		paramSource.addValue(Constants.EXIT_PLAZA_ID, exitPlazaId);
		String sql = "SELECT APD_ID FROM T_PLAZA_PLAN_LIMITS a, MASTER.T_PLAZA p1, MASTER.T_PLAZA p2, MASTER.T_PLAZA p3, MASTER.T_PLAZA p4 WHERE a.APD_ID = :APD_ID AND p1.PLAZA_ID = :ENTRY_PLAZA_ID AND p2.PLAZA_ID = :EXIT_PLAZA_ID AND p3.PLAZA_ID = a.plaza_entry AND p4.PLAZA_ID = a.plaza_exit AND p3.SECTION_ID = p4.SECTION_ID AND p1.SECTION_ID = p3.SECTION_ID AND p2.SECTION_ID = p3.SECTION_ID AND (((p1.PLAZA_SEQ_NUMBER between p3.PLAZA_SEQ_NUMBER AND p4.PLAZA_SEQ_NUMBER) AND p3.PLAZA_SEQ_NUMBER <> 0 AND p4.PLAZA_SEQ_NUMBER <> 0) OR ((p1.PLAZA_SEQ_NUMBER between p4.PLAZA_SEQ_NUMBER AND p3.PLAZA_SEQ_NUMBER) AND p3.PLAZA_SEQ_NUMBER <> 0 AND p4.PLAZA_SEQ_NUMBER <> 0) OR (p3.PLAZA_SEQ_NUMBER = p1.PLAZA_SEQ_NUMBER) OR (p4.PLAZA_SEQ_NUMBER = p1.PLAZA_SEQ_NUMBER)) AND (((p2.PLAZA_SEQ_NUMBER between p3.PLAZA_SEQ_NUMBER AND p4.PLAZA_SEQ_NUMBER) AND p3.PLAZA_SEQ_NUMBER <> 0 AND p4.PLAZA_SEQ_NUMBER <> 0) OR ((p2.PLAZA_SEQ_NUMBER between p4.PLAZA_SEQ_NUMBER AND p3.PLAZA_SEQ_NUMBER) AND p3.PLAZA_SEQ_NUMBER <> 0 AND p4.PLAZA_SEQ_NUMBER <> 0) OR (p3.PLAZA_SEQ_NUMBER = p2.PLAZA_SEQ_NUMBER) OR (p4.PLAZA_SEQ_NUMBER = p2.PLAZA_SEQ_NUMBER)) AND rownum < 2";
		List<PlazaPlanLimit> list=namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<PlazaPlanLimit>(PlazaPlanLimit.class));
		if(list!=null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

}
