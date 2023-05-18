package com.conduent.tpms.qatp.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.conduent.tpms.qatp.model.ConfigVariable;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class OracleConfiguration {

	@Autowired
	ConfigVariable configVariable;

	@Bean(name = "h2DataSource")
	@Primary
	public DataSource h2DataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder.url(configVariable.getH2Url());
		dataSourceBuilder.username(configVariable.getH2Username());
		dataSourceBuilder.password(configVariable.getH2Password());
		return dataSourceBuilder.build();
	}

	@Bean(name = "OracleDataSource")
	public DataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(configVariable.getOracleUsername());// change
		dataSource.setPassword(configVariable.getOraclePassword());
		
		dataSource.setURL(configVariable.getOracleUrl());
		dataSource.setImplicitCachingEnabled(true);
		dataSource.setFastConnectionFailoverEnabled(true);
		return dataSource;
	}

}
