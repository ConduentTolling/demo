package com.conduent.tollposting.dao;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tollposting.model.AccountPlan;

public interface IAccountPlanDao 
{
	public List<AccountPlan> getAccountPlanByEtcAccountId(Long etcAccountId,LocalDate txDate);

	public List<AccountPlan> getAccountPlanList();
}
