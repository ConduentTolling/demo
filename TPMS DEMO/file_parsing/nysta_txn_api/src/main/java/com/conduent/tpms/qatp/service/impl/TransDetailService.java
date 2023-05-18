package com.conduent.tpms.qatp.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dto.OCRDto;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.MessagePublisher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

@Service
public class TransDetailService implements ITransDetailService
{
	private static final Logger logger = LoggerFactory.getLogger(TransDetailService.class);

	@Autowired
	protected ITransDetailDao transDetailDao;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigVariable configVariable;
	
	@Autowired
	private MasterDataCache masterDataCache;

	@Override
	public void saveTransDetail(TranDetail tranDetail) {
		transDetailDao.saveTransDetail(tranDetail);
	}

	@Override
	public List<Long> loadNextSeq(Integer numOfSeq) {
		return transDetailDao.loadNextSeq(numOfSeq);
	}

	@Override
	public void batchInsert(List<TranDetail> txlist) {
		transDetailDao.batchInsert(txlist);
	}

	public long publishMessages(List<TranDetail> txlist, Gson gson) 
	{
		TranDetail tempTx;
		long msgCounter = 0;
		List<PutMessagesDetailsEntry> messages = new LinkedList<>();
		for (int i = 0; i < txlist.size(); i++) 
		{
			tempTx=txlist.get(i);
			if(tempTx.getPlazaAgencyId()==null) {
				tempTx.setPlazaAgencyId(Convertor.toInteger(QATPConstants.AGENCY_ID));
			}
			if(tempTx.getPlazaId()==null) {
				tempTx.setPlazaId(masterDataCache.getPlazaIfNull(Convertor.toInteger(QATPConstants.AGENCY_ID)));
			}
			byte[] msg = gson.toJson(tempTx).getBytes();
			logger.info("Publish Message ====>"+ gson.toJson(tempTx));
			messages.add(PutMessagesDetailsEntry.builder().key(tempTx.getLaneTxId().toString().trim().getBytes())
					.value(msg).build());
		}

		int msgsSize = messages.size();
		if (msgsSize != 0) 
		{
			msgCounter = messagePublisher.publishMessages(messages);
			if (msgCounter == msgsSize) 
			{
				logger.debug("Messages Published Successfully: Expectd : {},Published : {} ", msgsSize, msgCounter);

			}
			else if (msgCounter == 0) 
			{
				logger.info("Failed to publish Messages: Expectd : {},Published : {}", msgsSize, msgCounter);
			}
			else 
			{
				logger.info("Messages Published Partially: Expectd : {},Published : {} ", msgsSize, msgCounter);
			}
		}
		return msgCounter;
	}

	@Override
	public void pushToOCR(List<TranDetail> txlist,Gson gson) 
	{
		try
		{
			List<OCRDto> list=new ArrayList<>(txlist.size());
			for(TranDetail t:txlist)
			{
				if(t.getTxType()=="E" || t.getTxType()=="V")
				{
					if((t.getFrontOcrPlateState()!=null && !t.getFrontOcrPlateState().equals("  ") && !t.getFrontOcrPlateState().equals("**")) || (t.getRearOcrPlateState()!=null && !t.getRearOcrPlateState().equals("  ") && !t.getRearOcrPlateState().equals("**")) &&
							(t.getFrontOcrPlateNumber()!=null && !t.getFrontOcrPlateNumber().equals("          ") && !t.getFrontOcrPlateNumber().equals("**********")) || (t.getRearOcrPlateNumber()!=null && !t.getRearOcrPlateNumber().equals("          ") && !t.getRearOcrPlateNumber().equals("**********")))
					{
						list.add(t.getOCRDto());
					}
				}
			}
			logger.info(gson.toJson(list));
			ResponseEntity<String> result= restTemplate.postForEntity(configVariable.getOcrAPI(), list, String.class);
			if(result.getStatusCodeValue()==200)
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info(jsonObject.toString());
			}
		}
		catch(Exception ex)
		{
			logger.info(ex.getMessage());
		}
	}
}