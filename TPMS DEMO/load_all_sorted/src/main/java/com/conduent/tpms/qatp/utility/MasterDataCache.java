package com.conduent.tpms.qatp.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.dao.TCodeDao;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.model.TCode;
import com.conduent.tpms.qatp.model.TPlaza;

@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(MasterDataCache.class);

	@Autowired
	private TCodeDao tCodeDao;
	
	@Autowired
	private TPlazaDao tPlazaDao;

	private List<TCode> finStatusList;
	private List<TPlaza> plazaList;

	

	public TCodeDao gettCodeDao() {
		return tCodeDao;
	}

	public void settCodeDao(TCodeDao tCodeDao) {
		this.tCodeDao = tCodeDao;
	}

	public TPlazaDao gettPlazaDao() {
		return tPlazaDao;
	}

	public void settPlazaDao(TPlazaDao tPlazaDao) {
		this.tPlazaDao = tPlazaDao;
	}

	public List<TCode> getFinStatusList() {
		return finStatusList;
	}

	public void setFinStatusList(List<TCode> finStatusList) {
		this.finStatusList = finStatusList;
	}

	public List<TPlaza> getPlazaList() {
		return plazaList;
	}

	public void setPlazaList(List<TPlaza> plazaList) {
		this.plazaList = plazaList;
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
	
	public int getTcodeIdForFinStatus(String codeValue, String codeType) {
    	int codeId = 0;
    	for (TCode tCode : finStatusList) {
    		if(tCode.getCodeValue().equalsIgnoreCase(codeValue) && tCode.getCodeType().equalsIgnoreCase(codeType))
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
			plazaList=tPlazaDao.getAll();//plazalist
			setFinStatusList(finStatusList);
			log.info("Master data loaded for TCode table ..");
			
		} catch (Exception ex) {
			log.error("Failed to load initial master data: {}", ex.getMessage());

		}
		
		
	}
	
	public Integer getPlazabyorder(int agency_id)
    {
        if(plazaList != null)
        {
            Optional<TPlaza> plazaDto=plazaList.stream().filter(e->e!=null && e.getAgencyId()!=null && e.getAgencyId().equals(agency_id)).findFirst();
            if(plazaDto.isPresent())
            {
                return plazaDto.get().getPlazaId();
            }
        }
        return null;
        
    }
}
