package com.conduent.parking.posting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dao.ICodeDao;
import com.conduent.parking.posting.model.Codes;
import com.conduent.parking.posting.service.ICodeService;

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
