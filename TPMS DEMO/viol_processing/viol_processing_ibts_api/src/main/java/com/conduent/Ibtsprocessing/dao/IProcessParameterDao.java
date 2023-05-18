package com.conduent.Ibtsprocessing.dao;

import java.util.List;

import com.conduent.Ibtsprocessing.model.ProcessParameters;

public interface IProcessParameterDao 
{
	List<ProcessParameters> getProcessParameters();
	List<ProcessParameters> getProcessParametersList(String paramName);

}
