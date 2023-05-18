package com.conduent.tollposting.dao;

import java.util.List;

import com.conduent.tollposting.model.ProcessParameters;

public interface IProcessParameterDao 
{
	List<ProcessParameters> getProcessParameters();

	List<ProcessParameters> getMTARejAccount();

	ProcessParameters getTollAmountValue();

	List<ProcessParameters> getProcessParametersICTX();

}
