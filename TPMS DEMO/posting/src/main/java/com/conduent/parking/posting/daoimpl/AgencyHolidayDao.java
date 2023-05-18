package com.conduent.parking.posting.daoimpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.IAgencyHolidayDao;
import com.conduent.parking.posting.model.AgencyHoliday;

@Repository
public class AgencyHolidayDao implements IAgencyHolidayDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/*
	 * Not called
	 */
	@Override
	public List<AgencyHoliday> getAgencyHoliday() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_AGENCY_HOLIDAY");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<AgencyHoliday>(AgencyHoliday.class));
	}

	// check for static
	@Override
	public AgencyHoliday getAgencyHoliday(Integer agencyId, LocalDate txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.AGENCY_ID, agencyId);
		paramSource.addValue(Constants.TX_DATE, txDate);
		String queryToCheckFile = "SELECT * FROM MASTER.T_AGENCY_HOLIDAY WHERE AGENCY_ID =:AGENCY_ID AND HOLIDAY = :TX_DATE";
		List<AgencyHoliday> holidayList = namedJdbcTemplate.query(queryToCheckFile, paramSource,
				new BeanPropertyRowMapper<AgencyHoliday>(AgencyHoliday.class));
		if (holidayList != null && !holidayList.isEmpty()) {
			return holidayList.get(0);
		} else {
			return null;
		}
	}

}
