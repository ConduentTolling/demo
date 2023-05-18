package com.conduent.tpms.qatp.parser;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertMTADaoImpl;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.parser.agency.MtaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.TextFileReader;
import com.google.common.base.Stopwatch;


@Component
public class BackgroundJobHandler 
{
	private static final Logger logger = LoggerFactory.getLogger(BackgroundJobHandler.class);
	
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
	protected TextFileReader textFileReader;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	InsertMTADaoImpl insertMTADaoImpl;

	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;
	
	@Autowired
	protected ITransDetailDao transDetailDao;

	MtaFixlengthParser temp;

	@PostConstruct
	public void init()
	{
		temp= new MtaFixlengthParser(tQatpMappingDao,qatpDao,transDetailService,genericValidation,masterDataCache,insertMTADaoImpl,timeZoneConv,asyncProcessCall,transDetailDao);
		FileParserParameters fileParserParameters=tQatpMappingDao.getMappingConfigurationDetails(temp.getFileParserParameter());
		//temp.setFileParserParameter(fileParserParameters);
		temp.setConfigurationMapping(fileParserParameters);
	}

	public void startJob()
	{
		List<File> allfilesFromSrcFolder=null;
		try
		{
			allfilesFromSrcFolder = temp.getAllFilesFromSourceFolder();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		for(File file:allfilesFromSrcFolder)
		{
			try
			{
				MtaFixlengthParser obj=new MtaFixlengthParser(tQatpMappingDao,qatpDao,transDetailService,genericValidation,masterDataCache,insertMTADaoImpl,timeZoneConv,asyncProcessCall,transDetailDao);
				BeanUtils.copyProperties(temp, obj);
				obj.setFile(file);
				obj.setTextFileReader(new TextFileReader());
				Thread thread = new Thread(obj);
				thread.start();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}	
}
