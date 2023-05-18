package com.conduent.parking.posting.serviceimpl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IAccountPlanSuspensionDao;
import com.conduent.parking.posting.model.AccountPlanSuspension;
import com.conduent.parking.posting.service.IAccountPlanSuspensionService;

@Service
public class AccountPlanSuspensionService implements IAccountPlanSuspensionService
{
	@Autowired
	private IAccountPlanSuspensionDao accountPlanSuspensionDao;

	@Override
	public AccountPlanSuspension getAccountPlanSuspension(String apdId, Long etcAccountId, LocalDate trxDate) 
	{
		return accountPlanSuspensionDao.getAccountPlanSuspension(apdId, etcAccountId, trxDate);
	}
	@Override
	public void updateAcountPlanSuspension(LocalDate txDate, String apdId, Long etcAccountId, LocalDate suspStart,LocalDate suspEnd)
	{
		accountPlanSuspensionDao.updateAcountPlanSuspension(txDate, apdId, etcAccountId, suspStart, suspEnd);
	}	
}