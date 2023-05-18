package com.conduent.tpms.qatp.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.constants.CorrConstants;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.MessagePublisher;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.google.gson.Gson;
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
	private MasterDataCache masterDataCache;
	
	@Autowired
	private AsyncProcessCall asycnProcessCall;
	
	@Autowired
	Gson gson;

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
		//transDetailDao.batchInsert(txlist);
		transDetailDao.batchInsertnEW(txlist);
	}

	public long publishMessages(List<TranDetail> txlist) 
	{
		TranDetail tempTx;
		long msgCounter = 0;
		List<PutMessagesDetailsEntry> messages = new LinkedList<>();
		for (int i = 0; i < txlist.size(); i++) 
		{
			tempTx=txlist.get(i);
			if(tempTx.getPlazaAgencyId()==null) {
				tempTx.setPlazaAgencyId(Convertor.toInteger(CorrConstants.AGENCY_ID_FTXN));
			}
			if(tempTx.getPlazaId()==null) {
				tempTx.setPlazaId(masterDataCache.getPlazaIfNull(Convertor.toInteger(CorrConstants.AGENCY_ID_FTXN)));
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
	
	
}