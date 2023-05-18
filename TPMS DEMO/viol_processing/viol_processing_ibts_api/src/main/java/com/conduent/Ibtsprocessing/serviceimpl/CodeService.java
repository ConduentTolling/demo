package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.ICodeDao;
import com.conduent.Ibtsprocessing.model.Codes;
import com.conduent.Ibtsprocessing.service.ICodeService;

@Service
public class CodeService implements ICodeService 
{
	@Autowired
	private ICodeDao codeDao;

	@Override
	public List<Codes> getCodes() 
	{
		return codeDao.getCodes();
	}

}
