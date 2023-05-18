package com.conduent.parking.posting.daoimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.ITollScheduleDao;
import com.conduent.parking.posting.model.TollSchedule;

@Repository
public class TollScheduleDao implements ITollScheduleDao
{
	private static final Logger logger = LoggerFactory.getLogger(TollScheduleDao.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<TollSchedule> getTollAndPriceSchedule(LocalDate txDate,Integer agencyId,Integer entryPlazaId, Integer plazaId,  Integer vehicleClass)
	{
		//1st
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.AGENCY_ID, agencyId);
		paramSource.addValue(Constants.PLAZA_ID, plazaId);
		paramSource.addValue(Constants.ENTRY_PLAZA_ID, entryPlazaId);
		paramSource.addValue(Constants.VEHICLE_CLASS, vehicleClass);
		paramSource.addValue(Constants.TX_DATE, txDate);
		
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_TOLL_PRICE_SCHEDULE");
		LocalDateTime fromTime = LocalDateTime.now();
		List<TollSchedule> list= namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<TollSchedule>(TollSchedule.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTollAndPriceSchedule: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		return list;
	}


	@Override
	public List<TollSchedule> getTollSchedule(LocalDate txDate, 	Integer plazaId,Integer entryPlazaId, Integer vehicleClass)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.PLAZA_ID, plazaId);
		paramSource.addValue(Constants.ENTRY_PLAZA_ID, entryPlazaId);
		paramSource.addValue(Constants.VEHICLE_CLASS, vehicleClass);
		paramSource.addValue(Constants.TX_DATE, txDate);
		
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_TOLL_SCHEDULE");
		LocalDateTime fromTime = LocalDateTime.now();
		List<TollSchedule> list= namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<TollSchedule>(TollSchedule.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTollSchedule: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		return list;
	}
	
	@Override
	public Long getTollSchedPriceObj(LocalDate txDate, Integer plazaAgencyId, Integer entryPlazaId, OffsetDateTime txDateTime, String inDaysInd)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.PLAZA_AGENCY_ID, plazaAgencyId);
		paramSource.addValue(Constants.DAYS_IND, inDaysInd);
		paramSource.addValue(Constants.TX_DATE, txDate);
		String time = txDateTime.toString().substring(11, 19);
		paramSource.addValue(Constants.TX_DATETIME, time);
		
		String queryToCheckFile = "SELECT P.PRICE_SCHEDULE_ID \r\n"
				+ "FROM master.t_toll_price_schedule p\r\n"
				+ "WHERE p.days_ind = '"+inDaysInd+"' AND p.agency_id = "+plazaAgencyId+" \r\n"
				+ "AND to_date('"+txDate+"','YYYY-MM-DD') between p.effective_date AND p.expiry_date\r\n"
				+ "and to_date('"+time+"', 'HH24:MI:SS') between to_date(p.start_time, 'HH24:MI:SS') and\r\n"
				+ "to_date(p.end_time, 'HH24:MI:SS')\r\n"
				+ "AND ROWNUM < 2";
				//LoadJpaQueries.getQueryById("GET_T_TOLL_SCHEDULE_PRICE_OBJ");
		LocalDateTime fromTime = LocalDateTime.now();
		List<Long> list = namedJdbcTemplate.queryForList(queryToCheckFile,paramSource, Long.class);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTollSchedPriceObj: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	

}
