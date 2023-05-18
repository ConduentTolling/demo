package com.conduent.transactionSearch.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.transactionSearch.config.LoadQueries;
import com.conduent.transactionSearch.dao.PlazaDao;
import com.conduent.transactionSearch.model.Plaza;

@Repository
public class PlazaDaoImpl implements PlazaDao
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public  List<Plaza> getPlaza()
	{
		//String queryToCheckFile = LoadQueries.getQueryById("GET_T_PLAZA");
		String queryToCheckFile = "SELECT PLAZA_ID,NAME,EXTERN_PLAZA_ID,AGENCY_ID,LPR_ENABLED FROM MASTER.T_PLAZA";
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Plaza>(Plaza.class));
	}
	public String getPlazaName(String plazaId,List<Plaza> plazaList) {
		//List<Plaza> plazaList = getPlaza();
		if (plazaId == null) return " ";
		Iterator itr = plazaList.iterator();
        Plaza plaza;
		while(itr.hasNext()) {
			plaza = (Plaza) itr.next();
			if (plaza.getPlazaId()==Long.parseLong(plazaId))
			{
				return plaza.getName();
			}
		}
		return " ";
	}
}
