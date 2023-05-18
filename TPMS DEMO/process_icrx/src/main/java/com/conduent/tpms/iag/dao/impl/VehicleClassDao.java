package com.conduent.tpms.iag.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.dao.IVehicleClassDao;
import com.conduent.tpms.iag.model.VehicleClass;

@Repository
public class VehicleClassDao implements IVehicleClassDao 
{
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	@Override
	public VehicleClass getVehicleClass() 
	{
		try
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryToCheckFile = "select agency_id, agency_class, vehicle_type, base_axle_count, extern_agency_class from TPMS.t_vehicle_class";
			VehicleClass obj = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(VehicleClass.class));

			return obj;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
