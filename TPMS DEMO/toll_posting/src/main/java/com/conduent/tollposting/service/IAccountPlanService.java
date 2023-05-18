package com.conduent.tollposting.service;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tollposting.model.AccountPlan;

public interface IAccountPlanService
{
	public List<AccountPlan> getAccountPlanByEtcAccountId(Long etcAccountId,LocalDate txDate);
}
