package com.conduent.transactionSearch.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.conduent.transactionSearch.dao.AgencyDao;
import com.conduent.transactionSearch.model.Agency;
import com.conduent.transactionSearch.model.Plaza;

public class AgencyDaoImpl implements AgencyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Agency> getAgency() {
		// TODO Auto-generated method stub
		//String queryToCheckFile = LoadQueries.getQueryById("GET_T_PLAZA");
		String queryToCheckFile = "SELECT AGENCY_ID,AGENCY_NAME,AGENCY_SHORT_NAME,STMT_DESCRIPTION FROM CRM.V_AGENCY";
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	
	}

	@Override
	public String getAgencyShortName(String plazaAgencyIdInStr, List<Agency> agencyList) {
		// TODO Auto-generated method stub
		//return null;
		long plazaAgencyId = 0;
		try {
			plazaAgencyId = Long.parseLong(plazaAgencyIdInStr);
		} catch (Exception ex) {
			plazaAgencyId = 0;
		}
		if (plazaAgencyId == 0) return " ";
		Iterator itr = agencyList.iterator();
        Agency agency;
		while(itr.hasNext()) {
			agency = (Agency) itr.next();
			if (agency.getAgencyId()==plazaAgencyId)
			{
				return agency.getAgencyShortName();
			}
		}
        return "";
	}
	@Override
	public String getStmtDescription(String plazaAgencyIdInStr, List<Agency> agencyList) {
		// TODO Auto-generated method stub
		//return null;
		long plazaAgencyId = 0;
		try {
			plazaAgencyId = Long.parseLong(plazaAgencyIdInStr);
		} catch (Exception ex) {
			plazaAgencyId = 0;
		}
		if (plazaAgencyId == 0) return " ";
		Iterator itr = agencyList.iterator();
        Agency agency;
		while(itr.hasNext()) {
			agency = (Agency) itr.next();
			if (agency.getAgencyId()==plazaAgencyId)
			{
				return agency.getStmtDescription();
			}
		}
        return "";
	}

}
