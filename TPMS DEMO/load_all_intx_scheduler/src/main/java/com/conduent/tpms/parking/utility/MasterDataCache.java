package com.conduent.tpms.parking.utility;
//package com.conduent.tpms.iag.utility;
//
//import java.io.File;
//import java.util.List;
//import java.util.Optional;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.conduent.tpms.iag.dao.TAgencyDao;
//import com.conduent.tpms.iag.model.AgencyInfoVO;
//import com.conduent.tpms.iag.model.ZipCode;
//import com.conduent.tpms.iag.validation.InvalidTagFileParserImpl;
//
//
//
//@Component
//public class MasterDataCache {
//	
//	private static final Logger log = LoggerFactory.getLogger(InvalidTagFileParserImpl.class);
//	
//	@Autowired
//	private TAgencyDao tAgencyDao;
//
//	protected List<AgencyInfoVO> agencyInfoVOList;
//	
//	protected List<ZipCode> zipCodeList;
//	
//
//public TAgencyDao gettAgencyDao() {
//		return tAgencyDao;
//	}
//
//	public void settAgencyDao(TAgencyDao tAgencyDao) {
//		this.tAgencyDao = tAgencyDao;
//	}
//
//	public List<AgencyInfoVO> getAgencyInfoVOList() {
//		return agencyInfoVOList;
//	}
//
//	public void setAgencyInfoVOList(List<AgencyInfoVO> agencyInfoVOList) {
//		this.agencyInfoVOList = agencyInfoVOList;
//	}
//
//	@PostConstruct 
//	public void init() { 
//		 try { 
//			 agencyInfoVOList = tAgencyDao.getAgencyInfoList();
//			 System.out.println("AgencyInfo loaded successfully");
//			 zipCodeList = tAgencyDao.getZipCodeList();
//			 log.info("Zipcode List"+zipCodeList);
//			 System.out.println("Zipcode loaded successfully");
//			 setAgencyInfoVOList(agencyInfoVOList);
//			 setZipCodeList(zipCodeList);
//		 }
//	  catch (Exception ex) { ex.printStackTrace(); } }
//	 
//	
//
//	public List<ZipCode> getZipCodeList() {
//		return zipCodeList;
//	}
//
//	public void setZipCodeList(List<ZipCode> zipCodeList) {
//		this.zipCodeList = zipCodeList;
//	}
//
//	public Integer getXtraAxlesFromVehicleClassStatics(String etcIndVehClass) {
//		return null;
//	}
//
//	public Long getAVCAxlesFromVehicleAxleStatics(String etcIndVehClass) {
//		return null;
//	}
//
//	public boolean isHomeAgency(String agencyId) {
//		return true;
//	}
//
//
//}
