package com.conduent.tpms.qatp.utility;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.dao.TCodeDao;
import com.conduent.tpms.qatp.model.TCode;

@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(MasterDataCache.class);

	@Autowired
	private TCodeDao tCodeDao;

	private List<TCode> finStatusList;

	public List<TCode> getFinStatusList() {
		return finStatusList;
	}

	public void setFinStatusList(List<TCode> finStatusList) {
		this.finStatusList = finStatusList;
	}

	public int getTcodeId(String codeValue) {
    	int codeId = 0;
    	for (TCode tCode : finStatusList) {
    		if(tCode.getCodeValue().equals(codeValue))
    		{
    			codeId = tCode.getCodeId();
    		}
		}
		return codeId;
		
	}

	@PostConstruct
	public void init() {
		try {
			finStatusList = tCodeDao.getFinStatus();
			setFinStatusList(finStatusList);
			log.info("Master data loaded for TCode table ..");
		} catch (Exception ex) {
			log.error("Failed to load initial master date: {}", ex.getMessage());

		}

	}
}
