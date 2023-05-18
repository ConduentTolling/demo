package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.VehicleClassDao;
import com.conduent.tpms.ictx.model.VehicleClass;

/**
 * Vehicle class Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class VehicleClassDaoImpl implements VehicleClassDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Vehicle class Info
	 */
	@Override
	public List<VehicleClass> getVehicleClass() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_VEHICLE_CLASS);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<VehicleClass>(VehicleClass.class));
	}

}
