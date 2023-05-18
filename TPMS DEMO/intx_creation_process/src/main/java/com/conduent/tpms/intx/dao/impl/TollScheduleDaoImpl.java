package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.TollScheduleDao;
import com.conduent.tpms.intx.dto.TollFareDto;
import com.conduent.tpms.intx.model.TollSchedule;

/**
 * Toll Schedule Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class TollScheduleDaoImpl implements TollScheduleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Toll Amount
	 * 
	 * @param tollFareDto
	 * @return TollSchedule
	 */
	@Override
	public TollSchedule getTollAmount(TollFareDto tollFareDto) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_TOLL_PRICES);
		String temptxDate = tollFareDto.getTxDate() != null ? String.valueOf(tollFareDto.getTxDate()) : null;
		List<TollSchedule> list = jdbcTemplate.query(queryToCheckFile,
				new BeanPropertyRowMapper<TollSchedule>(TollSchedule.class), temptxDate, temptxDate,
				tollFareDto.getPlazaId(), tollFareDto.getEntryPlazaId(), tollFareDto.getActualClass(),
				tollFareDto.getPlanType(), tollFareDto.getTollRevenueType());

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

}
