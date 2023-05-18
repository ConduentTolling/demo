package com.conduent.tpms.qatp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.dao.IVehicleClassDao;
import com.conduent.tpms.qatp.model.VehicleClass;

@Repository
public class VehicleClassDao implements IVehicleClassDao 
{
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	@Override
	public VehicleClass getVehicleClass(String agTagAgency,String externAgencyClass) 
	{
		try
		{   
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			String queryToCheckFile = "select agency_id, agency_class, vehicle_type, base_axle_count, extern_agency_class from MASTER.t_vehicle_class where agency_id="+agTagAgency+" and extern_agency_class='"+externAgencyClass+"'";
			VehicleClass obj = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(VehicleClass.class));

			return obj;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
