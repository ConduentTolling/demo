package com.conduent.tpms.qatp.service.impl;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertNystaStatDaoImpl;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.TextFileReader;

@Service
public class QatpServiceImpl implements QatpService 
{
	private static final Logger logger = LoggerFactory.getLogger(QatpServiceImpl.class);
	
	@Autowired
	NystaFixlengthParser nystaFixlengthParser;
	
	@Autowired
	NystaFixlengthParser nystaFixlengthParserThreadSafe;
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	private IQatpDao qatpDao;

	@Autowired
	protected ITransDetailService transDetailService;

	@Autowired
	protected
	GenericValidation genericValidation;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	InsertNystaStatDaoImpl insertNystaStatDaoImpl;

	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;

	NystaFixlengthParser temp;
	
	
	@Override
	public void process(Integer agencyId) throws Exception 
	{	
		nystaFixlengthParser.fileParseTemplate(agencyId);
	}	
	
	@Override
	public void parallelProcess(String fileName,Integer agencyId) throws Exception 
	{
		try 
		{
			//nystaFixlengthParser.fileParseTemplatewithFileName(fileName);
			nystaFixlengthParserThreadSafe = new NystaFixlengthParser(tQatpMappingDao,qatpDao,transDetailService,genericValidation,masterDataCache,insertNystaStatDaoImpl,timeZoneConv,asyncProcessCall);
			nystaFixlengthParserThreadSafe.fileParseTemplatewithFileName(fileName,agencyId);
		}
		catch (IOException e) 
		{
			logger.info("Exception {}",e.getMessage());
		}
	}	
} 