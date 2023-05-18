package com.conduent.tollposting.service;

import java.util.List;

import com.conduent.tollposting.model.ProcessParameters;

public interface IProcessParameterService 
{
	List<ProcessParameters> getProcessParameters();

	List<ProcessParameters> getMTARejAccount();

	ProcessParameters getTollAmountValue();

	List<ProcessParameters> getProcessParametersICTX();

}
