package com.conduent.tollposting.daoimpl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IDeviceDao;
import com.conduent.tollposting.model.Device;

@Repository
public class DeviceDao implements IDeviceDao
{
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public Device getDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.DEVICE_NO, deviceNo);
		paramSource.addValue(Constants.TX_TIME_STAMP, txTimeStamp);
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_USING_DEVICE_NO");
		List<Device> deviceList =namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<Device>(Device.class));
		if (deviceList.isEmpty()) {
			return null;
		} else {
			return deviceList.get(0);
		}
	}

	@Override
	public Device getHDeviceByDeviceNo(String deviceNo,LocalDateTime txTimeStamp)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.DEVICE_NO, deviceNo);
		paramSource.addValue(Constants.TX_TIME_STAMP, txTimeStamp);
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_USING_H_DEVICE_NO");
		List<Device> deviceList = namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<Device>(Device.class));
		if (deviceList.isEmpty()) {
			return null;
		} else {
			return deviceList.get(0);
		}
	}
	
	@Override
	public Device getHADeviceByDeviceNo(String deviceNo)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.DEVICE_NO, deviceNo);
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_HA_DEVICES");
		List<Device> deviceList = namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<Device>(Device.class));
		if (deviceList.isEmpty()) {
			return null;
		} else {
			return deviceList.get(0);
		}
	}
	
	@Override
	public Device getDeviceByEtcAccountId(String etcAccountId,LocalDateTime txTimeStamp)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.DEVICE_NO, etcAccountId);
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_USING_ETC_ACCOUNT_ID");
		List<Device> deviceList = namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<Device>(Device.class));
		if (deviceList.isEmpty()) {
			return null;
		} else {
			return deviceList.get(0);
		}
	}
}