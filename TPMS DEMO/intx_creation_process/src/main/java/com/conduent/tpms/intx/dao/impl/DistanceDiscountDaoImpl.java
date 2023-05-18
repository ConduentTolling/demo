package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.DistanceDiscountDao;
import com.conduent.tpms.intx.model.DistanceDiscount;

/**
 * Distance Discount Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class DistanceDiscountDaoImpl implements DistanceDiscountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Distance Discount Info
	 */
	@Override
	public List<DistanceDiscount> getDistanceDiscount() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_DISTANCE_DISCOUNT);
		return jdbcTemplate.query(queryToCheckFile,
				new BeanPropertyRowMapper<DistanceDiscount>(DistanceDiscount.class));
	}

}
