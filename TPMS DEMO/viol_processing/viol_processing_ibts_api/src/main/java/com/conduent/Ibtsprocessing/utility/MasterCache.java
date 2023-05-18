package com.conduent.Ibtsprocessing.utility;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.Ibtsprocessing.dao.ILaneDao;
import com.conduent.Ibtsprocessing.dao.IPlazaDao;
import com.conduent.Ibtsprocessing.dao.ITxTypeMappingDao;
import com.conduent.Ibtsprocessing.dao.IXferControlDao;
import com.conduent.Ibtsprocessing.model.Agency;
import com.conduent.Ibtsprocessing.model.Codes;
import com.conduent.Ibtsprocessing.model.EtcAccount;
import com.conduent.Ibtsprocessing.model.Lane;
import com.conduent.Ibtsprocessing.model.OADevices;
import com.conduent.Ibtsprocessing.model.Plaza;
import com.conduent.Ibtsprocessing.model.ProcessParameters;
import com.conduent.Ibtsprocessing.model.TxTypeMapping;
import com.conduent.Ibtsprocessing.model.XferControl;
import com.conduent.Ibtsprocessing.service.IAgencyService;
import com.conduent.Ibtsprocessing.service.ICodeService;
import com.conduent.Ibtsprocessing.service.IEtcAccount;
import com.conduent.Ibtsprocessing.service.IOaDevices;
import com.conduent.Ibtsprocessing.service.IProcessParameterService;
import com.conduent.Ibtsprocessing.serviceimpl.PushMessageServiceImpl;

@Component
public class MasterCache {
	
	private static final Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);
	
	@Autowired
	private IAgencyService agencyService;

	@Autowired
	private ICodeService codeService;

	@Autowired
	private IProcessParameterService processParameterService;

	@Autowired
	private IOaDevices oaDevices;

	@Autowired
	private IEtcAccount etcAccountService;
	
	@Autowired
	private ITxTypeMappingDao txTypeMappingDao;
	
	@Autowired
	private IXferControlDao xferControlDao;
	
	@Autowired
	private ILaneDao laneDao;
	
	@Autowired
	private IPlazaDao plazaDao;

	private List<EtcAccount> etcAccountList;

	private List<Agency> agencyList;

	private List<Codes> codesList;

	private List<OADevices> OaDeviceList;

	private List<ProcessParameters> processParametersList;
	
	private List<TxTypeMapping> txTypeMappingList;
	
	private List<XferControl> xferControlList;
	
	private List<Plaza> plazaList;
	
	private List<Lane> laneList;
	

	public Codes getCodeID(String codeType, String codeValue) {
		if (codeType == null || codeValue == null || codesList == null) {
			return null;
		}
		Optional<Codes> codeList = codesList.stream()
				.filter(e -> e.getCodeType().equals(codeType) && e.getCodeValue().equals(codeValue)).findFirst();
		if (codeList.isPresent()) {
			return codeList.get();
		}
		return null;
	}

	public ProcessParameters getParamValue(Integer plazaAgencyId, String paramName) {
		if (plazaAgencyId == null || processParametersList == null || paramName == null) {
			return null;
		}
		Optional<ProcessParameters> processParametersLists = processParametersList.stream()
				.filter(e -> e.getAgencyId() == (plazaAgencyId) && e.getParamName().equals(paramName)).findFirst();
		if (processParametersLists.isPresent()) {
			return processParametersLists.get();
		}
		return null;
	}

	public OADevices getCurrentDeviceStatus(String deviceNo, LocalDateTime txTimeStamp) {
		Timestamp ts = Timestamp.valueOf(txTimeStamp);
		LocalDate localDate = ts.toLocalDateTime().toLocalDate();
		if (deviceNo == null || OaDeviceList == null) {
			return null;
		}
		Optional<OADevices> oaDeviceLists = OaDeviceList.stream()
				.filter(e -> e.getDeviceNo().equals(deviceNo)
						&& (localDate.isAfter(e.getStartDate()) && localDate.isBefore(e.getEndDate()))
						|| localDate.isEqual(e.getStartDate()) || localDate.isEqual(e.getStartDate()))
				.findFirst();
		if (oaDeviceLists.isPresent()) {
			return oaDeviceLists.get();
		}
		return null;
	}

	public Agency getAgencyId(String agencyName) {
		if (agencyName == null || agencyList == null) {
			return null;
		}
		Optional<Agency> agencyLists = agencyList.stream().filter(e -> e.getStmtDescription().equals(agencyName))
				.findFirst();
		if (agencyLists.isPresent()) {
			return agencyLists.get();
		}
		return null;
	}

	public EtcAccount getEtcAccountInfo(Integer etcAccount) {

		if (etcAccount == null || etcAccountList == null) {

			return null;
		}

		Optional<EtcAccount> etcAccountsList = etcAccountList.stream().filter(e -> e.getEtcAccountId() == etcAccount)
				.findFirst();
		if (etcAccountsList.isPresent()) {
			return etcAccountsList.get();
		}
		return null;

	}

	
	public TxTypeMapping validateTxSubTypeValue(Integer voilType){
		if (voilType == null || txTypeMappingList == null) {

			return null;
		}

		Optional<TxTypeMapping> txTypeMappingLists = txTypeMappingList.stream().filter(e -> e.getViolationType()==voilType)
				.findFirst();
		if (txTypeMappingLists.isPresent()) {
			return txTypeMappingLists.get();
		}
		return null;
		
	}
	
//	public XferControl getXferControlDate(Long xferControlId){
//		if (xferControlId == null || xferControlList == null) {
//
//			return null;
//		}
//
//		Optional<XferControl> xferControlLists = xferControlList.stream().filter(e -> e.getXferControlId().longValue() == 
//				xferControlId.longValue())
//				.findFirst();
//
//		if (xferControlLists.isPresent()) {
//
//			return xferControlLists.get();
//		}
//		return null;
//		
//	}
	
	public String getLane(Long laneId) {
		
		if(laneList == null || laneId == null) {
			return null;
		}
		
		Optional<Lane> laneLists = laneList.stream().filter(e->e.getLaneId().longValue()==laneId.longValue()).findFirst();
		if(laneLists.isPresent()) {
			if(laneLists.get().getLprEnabled()!=null) {
			return laneLists.get().getLprEnabled();
			}
		}
		return null;
	}
	
	public String getPlaza(Long plazaId) {
		
		if(plazaList == null || plazaId == null) {
			return null;
		}
		
		Optional<Plaza> plazaLists = plazaList.stream().filter(e->e.getPlazaId().longValue()==plazaId.longValue()).findFirst();
		if(plazaLists.isPresent()) {
			if(plazaLists.get().getLprEnabled()!=null) {
			return plazaLists.get().getLprEnabled();
			}
		}
		return null;
	}
	@PostConstruct
	public void loadData() {
		try {
			codesList = codeService.getCodes();
			processParametersList = processParameterService.getProcessParameters();
			OaDeviceList = oaDevices.getOaDevices();
			agencyList = agencyService.getAgency();
			etcAccountList = etcAccountService.getEtcAccount();
			txTypeMappingList=txTypeMappingDao.getTxTypeMappings();
			//xferControlList = xferControlDao.getXferControlDate();
			plazaList = plazaDao.getPlaza();
			laneList = laneDao.getLane();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
