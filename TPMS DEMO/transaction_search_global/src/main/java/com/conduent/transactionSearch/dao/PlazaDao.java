package com.conduent.transactionSearch.dao;

import java.util.List;

import com.conduent.transactionSearch.model.Plaza;

public interface PlazaDao 
{
	public List<Plaza> getPlaza();
	public String getPlazaName(String plazaId,List<Plaza> plazaList);
		
}
