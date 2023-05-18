package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.TProcessParameter;

public interface TProcessParamterDao {

	public List<TProcessParameter> getProcessParameters();
	List<TProcessParameter> getProcessParametersList(String paramName);

	}

