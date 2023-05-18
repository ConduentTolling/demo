package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IFpmsAccountDao;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.model.FpmsAccount;

@Repository
public class FpmsAccountDao implements IFpmsAccountDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public FpmsAccount getFpmsAccount(Long etcAccountId)
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_BALANCE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, etcAccountId);
		List<FpmsAccount> list= namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<FpmsAccount>(FpmsAccount.class));
		if(!list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

}
