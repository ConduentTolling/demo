package com.conduent.tpms.qatp.utility;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.dao.impl.ExclusionDaoImpl;

@Component
public class MasterCache {
	@Autowired(required = true)
	private ExclusionDaoImpl impl;

	@PostConstruct
	public void init() {
		try {

			//impl.getDataFromOracleDb();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
