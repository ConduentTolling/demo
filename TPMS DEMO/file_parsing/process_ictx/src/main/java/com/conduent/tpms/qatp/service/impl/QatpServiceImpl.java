package com.conduent.tpms.qatp.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertPbaDaoImpl;
import com.conduent.tpms.qatp.parser.agency.PbaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.TextFileReader;

@Service
public class QatpServiceImpl implements QatpService {
	
	private static final Logger logger = LoggerFactory.getLogger(QatpServiceImpl.class);

	@Autowired
	PbaFixlengthParser pastaFixlengthParser;
	
	@Autowired
	PbaFixlengthParser paFixlengthParserThreadSafe;
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	private IQatpDao qatpDao;

	@Autowired
	protected ITransDetailDao transDetailDao;

	@Autowired
	protected GenericValidation genericValidation;

	@Autowired
	private TextFileReader textFileReader;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	protected ITransDetailService transDetailService;
	
	@Autowired
	private InsertPbaDaoImpl insertPaDaoImpl;
	
	@Autowired
	protected AsyncProcessCall asyncProcessCall;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Override
	public void process(Integer agencyId)
	{
		try 
		{
			pastaFixlengthParser.fileParseTemplate(agencyId);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
		
	@Override
	public void parallelProcess(String fileName) throws Exception 
	{
		try 
		{
			//nystaFixlengthParser.fileParseTemplatewithFileName(fileName);
			paFixlengthParserThreadSafe = new PbaFixlengthParser(tQatpMappingDao,qatpDao,transDetailService,genericValidation,masterDataCache,insertPaDaoImpl,timeZoneConv,asyncProcessCall,transDetailDao);
			paFixlengthParserThreadSafe.fileParseTemplatewithFileName(fileName);
		}
		catch (IOException e) 
		{
			logger.info("Exception {}",e.getMessage());
		}
	}	
}