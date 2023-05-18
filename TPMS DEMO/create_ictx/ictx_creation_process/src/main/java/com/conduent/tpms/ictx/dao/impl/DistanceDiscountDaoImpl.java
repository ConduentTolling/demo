package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.DistanceDiscountDao;
import com.conduent.tpms.ictx.model.DistanceDiscount;

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
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_DISTANCE_DISCOUNT);
		return jdbcTemplate.query(queryToCheckFile,
				new BeanPropertyRowMapper<DistanceDiscount>(DistanceDiscount.class));
	}

}
