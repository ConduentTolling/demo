package com.conduent.tpms.inrx.dao;

import java.util.List;

import com.conduent.tpms.inrx.dto.TProcessParameter;

public interface TProcessParamterDao {

	public List<TProcessParameter> getProcessParameters();
	List<TProcessParameter> getProcessParametersList(String paramName);

	}

