package com.conduent.tpms.process25a.dao;

import java.util.List;

import com.conduent.tpms.process25a.dto.TProcessParameter;

public interface TProcessParamterDao {

	public List<TProcessParameter> getProcessParameters();
	List<TProcessParameter> getProcessParametersList(String paramName);

	}

