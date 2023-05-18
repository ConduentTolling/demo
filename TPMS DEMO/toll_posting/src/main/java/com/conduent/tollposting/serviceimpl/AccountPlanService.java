package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dao.IAccountPlanDao;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.service.IAccountPlanService;

@Service
public class AccountPlanService implements IAccountPlanService 
{
	@Autowired
	private IAccountPlanDao accountPlanDao; 

	@Override
	public List<AccountPlan> getAccountPlanByEtcAccountId(Long etcAccountId,LocalDate txDate)
	{
		return accountPlanDao.getAccountPlanByEtcAccountId(etcAccountId,txDate);
	}

}
