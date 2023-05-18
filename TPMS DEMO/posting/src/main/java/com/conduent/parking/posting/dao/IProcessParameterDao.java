package com.conduent.parking.posting.dao;

import java.util.List;

import com.conduent.parking.posting.model.ProcessParameters;

public interface IProcessParameterDao 
{
	List<ProcessParameters> getProcessParameters();

}
