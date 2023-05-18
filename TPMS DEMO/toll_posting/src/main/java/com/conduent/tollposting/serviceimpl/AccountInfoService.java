package com.conduent.tollposting.serviceimpl;

import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IAccountInfoDao;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.service.IAccountInfoService;

@Service
public class AccountInfoService implements IAccountInfoService
{
	@Autowired
	private IAccountInfoDao accountInfoDao;

	@Override
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType, LocalDate txDate) throws ParseException
	{
		return accountInfoDao.getAccountInfo(etcAccountId,accountType,txDate);
	}

	@Override
	public void getAccountBalance(AccountInfoDTO accountInfo) {
		accountInfoDao.getAccountBalance(accountInfo);
	}
}
