package com.conduent.transactionSearch.dao;

import java.util.List;

import com.conduent.transactionSearch.model.Agency;

public interface AgencyDao 
{
	public List<Agency> getAgency();
	public String getAgencyShortName(String plazaAgencyId,List<Agency> agencyList);
	public String getStmtDescription(String plazaAgencyId, List<Agency> agencyList);
				
}
