package com.conduent.transactionSearch.dao;

import java.util.List;

import com.conduent.transactionSearch.model.TranDetailClass;
import com.conduent.transactionSearch.model.TranQueryReturn;
import com.conduent.transactionSearch.model.TranQueryReturnFromView;

public interface TranDetailDao 
{
	public List<TranDetailClass> getAll(String queryToCheckFile);
	public List<TranQueryReturn> getAllRowsFromQuery(String queryToCheckFile,int page,int size);
	public List<TranQueryReturnFromView> getAllRowsFromQueryView(String queryToCheckFile,int page,int size);
			
}
