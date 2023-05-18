package com.conduent.parking.posting.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.IAccountInfoDao;
import com.conduent.parking.posting.dto.AccountInfoDTO;
import com.conduent.parking.posting.service.IAccountInfoService;

@Service
public class AccountInfoService implements IAccountInfoService
{
	@Autowired
	private IAccountInfoDao accountInfoDao;

	@Override
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType)
	{
		return accountInfoDao.getAccountInfo(etcAccountId,accountType);
	}

	@Override
	public void getAccountBalance(AccountInfoDTO accountInfo) {
		accountInfoDao.getAccountBalance(accountInfo);
	}
}
