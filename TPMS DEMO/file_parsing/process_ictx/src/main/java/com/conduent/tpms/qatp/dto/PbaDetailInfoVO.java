package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.VehicleClass;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

@Service
public class PbaDetailInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PbaDetailInfoVO.class);
		
	private String etcTrxSerialNum;
	private String etcRevenueDate;
	private String etcFacAgency;
	private String etcTrxType;
	private String etcEntryDate;
	private String etcEntryTime;
	private String etcEntryDateTime;
	private String etcEntryPlaza;
	private String etcEntryLane;
	private String etcTagAgency;
	private String etcTagSerialNumber;
	private String etcReadPerformance;
	private String etcWritePerf;
	private String etcTagPgmStatus;
	private String etcLaneMode;
	private String etcValidationStatus;
	private String etcLicState;
	private String etcLicNumber;
	private String etcLicType;
	private String etcClassCharged;
	private String etcActualAxles;
	private String etcExitSpeed;
	private String etcOverSpeed;
	private String etcExitDate;
	private String etcExitTime;
	private String etcExitDateTime;
	private String etcExitPlaza;
	private String etcExitLane;
	private String etcDebitCredit;
	private String etcTollAmount;
	private String delimiter;

	public String getEtcEntryDateTime() {
		return etcEntryDateTime;
	}

	public void setEtcEntryDateTime(String etcEntryDateTime) {
		this.etcEntryDateTime = etcEntryDateTime;
	}

	public String getEtcExitDateTime() {
		return etcExitDateTime;
	}

	public void setEtcExitDateTime(String etcExitDateTime) {
		this.etcExitDateTime = etcExitDateTime;
	}

	public String getEtcLicType() {
		return etcLicType;
	}

	public void setEtcLicType(String etcLicType) {
		this.etcLicType = etcLicType;
	}

	public String getEtcTrxSerialNum() {
		return etcTrxSerialNum;
	}

	public void setEtcTrxSerialNum(String etcTrxSerialNum) {
		this.etcTrxSerialNum = etcTrxSerialNum;
	}

	public String getEtcRevenueDate() {
		return etcRevenueDate;
	}

	public void setEtcRevenueDate(String etcRevenueDate) {
		this.etcRevenueDate = etcRevenueDate;
	}

	public String getEtcFacAgency() {
		return etcFacAgency;
	}

	public void setEtcFacAgency(String etcFacAgency) {
		this.etcFacAgency = etcFacAgency;
	}

	public String getEtcTrxType() {
		return etcTrxType;
	}

	public void setEtcTrxType(String etcTrxType) {
		this.etcTrxType = etcTrxType;
	}

	public String getEtcEntryDate() {
		return etcEntryDate;
	}

	public void setEtcEntryDate(String etcEntryDate) {
		this.etcEntryDate = etcEntryDate;
	}

	public String getEtcEntryTime() {
		return etcEntryTime;
	}

	public void setEtcEntryTime(String etcEntryTime) {
		this.etcEntryTime = etcEntryTime;
	}

	public String getEtcEntryPlaza() {
		return etcEntryPlaza;
	}

	public void setEtcEntryPlaza(String etcEntryPlaza) {
		this.etcEntryPlaza = etcEntryPlaza;
	}

	public String getEtcEntryLane() {
		return etcEntryLane;
	}

	public void setEtcEntryLane(String etcEntryLane) {
		this.etcEntryLane = etcEntryLane;
	}

	public String getEtcTagAgency() {
		return etcTagAgency;
	}

	public void setEtcTagAgency(String etcTagAgency) {
		this.etcTagAgency = etcTagAgency;
	}

	public String getEtcTagSerialNumber() {
		return etcTagSerialNumber;
	}

	public void setEtcTagSerialNumber(String etcTagSerialNumber) {
		this.etcTagSerialNumber = etcTagSerialNumber;
	}

	public String getEtcReadPerformance() {
		return etcReadPerformance;
	}

	public void setEtcReadPerformance(String etcReadPerformance) {
		this.etcReadPerformance = etcReadPerformance;
	}

	public String getEtcWritePerf() {
		return etcWritePerf;
	}

	public void setEtcWritePerf(String etcWritePerf) {
		this.etcWritePerf = etcWritePerf;
	}

	public String getEtcTagPgmStatus() {
		return etcTagPgmStatus;
	}

	public void setEtcTagPgmStatus(String etcTagPgmStatus) {
		this.etcTagPgmStatus = etcTagPgmStatus;
	}

	public String getEtcLaneMode() {
		return etcLaneMode;
	}

	public void setEtcLaneMode(String etcLaneMode) {
		this.etcLaneMode = etcLaneMode;
	}

	public String getEtcValidationStatus() {
		return etcValidationStatus;
	}

	public void setEtcValidationStatus(String etcValidationStatus) {
		this.etcValidationStatus = etcValidationStatus;
	}

	public String getEtcLicState() {
		return etcLicState;
	}

	public void setEtcLicState(String etcLicState) {
		this.etcLicState = etcLicState;
	}

	public String getEtcLicNumber() {
		return etcLicNumber;
	}

	public void setEtcLicNumber(String etcLicNumber) {
		this.etcLicNumber = etcLicNumber;
	}

	public String getEtcClassCharged() {
		return etcClassCharged;
	}

	public void setEtcClassCharged(String etcClassCharged) {
		this.etcClassCharged = etcClassCharged;
	}

	public String getEtcActualAxles() {
		return etcActualAxles;
	}

	public void setEtcActualAxles(String etcActualAxles) {
		this.etcActualAxles = etcActualAxles;
	}

	public String getEtcExitSpeed() {
		return etcExitSpeed;
	}

	public void setEtcExitSpeed(String etcExitSpeed) {
		this.etcExitSpeed = etcExitSpeed;
	}

	public String getEtcOverSpeed() {
		return etcOverSpeed;
	}

	public void setEtcOverSpeed(String etcOverSpeed) {
		this.etcOverSpeed = etcOverSpeed;
	}

	public String getEtcExitDate() {
		return etcExitDate;
	}

	public void setEtcExitDate(String etcExitDate) {
		this.etcExitDate = etcExitDate;
	}

	public String getEtcExitTime() {
		return etcExitTime;
	}

	public void setEtcExitTime(String etcExitTime) {
		this.etcExitTime = etcExitTime;
	}

	public String getEtcExitPlaza() {
		return etcExitPlaza;
	}

	public void setEtcExitPlaza(String etcExitPlaza) {
		this.etcExitPlaza = etcExitPlaza;
	}

	public String getEtcExitLane() {
		return etcExitLane;
	}

	public void setEtcExitLane(String etcExitLane) {
		this.etcExitLane = etcExitLane;
	}

	public String getEtcDebitCredit() {
		return etcDebitCredit;
	}

	public void setEtcDebitCredit(String etcDebitCredit) {
		this.etcDebitCredit = etcDebitCredit;
	}

	public String getEtcTollAmount() {
		return etcTollAmount;
	}

	public void setEtcTollAmount(String etcTollAmount) {
		this.etcTollAmount = etcTollAmount;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Transaction getTransaction(MasterDataCache masterDataCache, XferControl xferControl, PbaHeaderInfoVO headerVO, TQatpMappingDao tQatpMappingDao, Integer agencyIdPbaOrTiba)
	{
		AgencyInfoVO agencyInfoVO = masterDataCache.getAgency(etcTagAgency != null ? etcTagAgency : null);		
	
		Tplaza plaza = masterDataCache.getPlaza(etcExitPlaza != null ? StringUtils.leftPad(String.valueOf(etcExitPlaza.trim()), 3, "0") : null,
				agencyIdPbaOrTiba);
		
		Transaction obj = new Transaction();

		GenericValidation validation = new GenericValidation();
				
		obj.setTrxType("E");
		obj.setTrxSubtype("Z");
		obj.setEtcViolObserved(0);
		
		// etcTrxType - R/X
		if (etcTrxType.equals("B")) 
		{
			obj.setEtcTrxType(etcTrxType);	
		}
		else 
		{
			logger.info("R/X - Invalid ETC_TRX_TYPE : {}", etcTrxType);
			if(!obj.getTrxType().equals("R")) 
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("X");
			}
			obj.setEtcTrxType(etcTrxType);
		}

		// Device Number - R/T
		if(etcTagAgency.matches(".*[a-zA-Z].*") || etcTagSerialNumber.matches(".*[a-zA-Z].*") || validate(etcTagAgency+etcTagSerialNumber))
		{
			if(!obj.getTrxType().equals("R")) 
			{				
				logger.info("R/T - Null ETC_TAG_AGENCY or ETC_TAG_SERIAL_NUMBER");
				obj.setTrxType("R");
				obj.setTrxSubtype("T");
			}
			if (etcTagAgency.matches(".*[a-zA-Z].*") || etcTagSerialNumber.matches(".*[a-zA-Z].*"))
			{
				obj.setDeviceNumber(etcTagAgency+etcTagSerialNumber);
				obj.setDeviceAgencyId(etcTagAgency);
			}
		}
		else 
		{
			if (agencyInfoVO != null) 
			{
				String dnumber = ((agencyInfoVO.getDevicePrefix()) + (etcTagSerialNumber != null ? etcTagSerialNumber : null));
				obj.setDeviceNumber(dnumber);
				obj.setDeviceAgencyId(etcTagAgency);
			} 
			else  
			{					
				if(!obj.getTrxType().equals("R"))
				{
					logger.info("R/T - Invalid ETC_TAG_AGENCY : {}", etcTagAgency);
					obj.setTrxType("R");
					obj.setTrxSubtype("T");
				}					
				obj.setDeviceNumber(etcTagAgency+etcTagSerialNumber);	
				obj.setDeviceAgencyId(etcTagAgency);
			}
		}

		//etxExitPlaza - R/L
		Tplaza tplaza = masterDataCache.getPlaza(etcExitPlaza != null ? StringUtils.leftPad(String.valueOf(etcExitPlaza.trim()), 3, "0") : null, agencyIdPbaOrTiba);
		if (tplaza == null) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/L - Invalid ETC_EXIT_PLAZA : {}", etcExitPlaza);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
		} 
		else 
		{
			obj.setExitPlaza(tplaza.getPlazaId());
			obj.setPlazaAgencyId(tplaza.getAgencyId());	
			
			if (agencyIdPbaOrTiba == Integer.valueOf(QATPConstants.PBA_AGENCY_ID))
			{
				int exitPlaza = Integer.parseInt(etcExitPlaza.replace(" ",""));
				obj.setExternPlazaId(String.valueOf(exitPlaza));
			}
			else if (agencyIdPbaOrTiba == Integer.valueOf(QATPConstants.TIBA_AGENCY_ID))
			{
				obj.setExternPlazaId(etcExitPlaza);
			}
			
		}
		
		
		
		//etcExitLane - R/L
		logger.debug("Checking for lane id present in master.T_LANE Table");
		int laneDto = masterDataCache.getLaneId(etcExitLane != null ? etcExitLane : null,
				plaza != null ? plaza.getPlazaId() : null);

		if (laneDto == 0) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/L - Invalid ETC_EXIT_LANE : {}", etcExitLane);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
		}
		else 
		{
			obj.setTrxLaneId(laneDto);
			
			int exitLane = Integer.parseInt(etcExitLane.replace(" ",""));
			obj.setExternLaneId(String.valueOf(exitLane));
		}
		
        
			
		// etcClassCharged - R/C
		VehicleClass vehicleClass = masterDataCache.getAgencyIdandExternalAgencyClass(plaza != null ? plaza.getAgencyId() : null,
				etcClassCharged != null ? etcClassCharged.trim() : null);
				
		if (vehicleClass == null) 
		{
			if(!obj.getTrxType().equals("R"))
			{
				logger.info("R/C - Invalid ETC_CLASS_CHARGED : {}", etcClassCharged);
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
		} 
		else 
		{
			obj.setActualClass(vehicleClass.getAgencyClass());	
		}
		
		//etcTrxSerialNum - R/X			
		if (etcTrxSerialNum.length() != 20 || etcTrxSerialNum.matches("[a-zA-Z]+") ||
				etcTrxSerialNum.equals("                    ") || etcTrxSerialNum.contains(" ") )     
		{		
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/X - Invalid ETC_TRX_SERIAL_NUMBER : {}", etcTrxSerialNum);
				obj.setTrxType("R");
				obj.setTrxSubtype("X");
			}
		} 
		else 
		{						
			obj.setExtHostRefNum(new BigInteger(etcTrxSerialNum));
		}
		
		// etcExitDate and etcExitTime - R/D
		if (etcExitDate != null && !validation.dateValidation(etcExitDate, "YYYYMMDD2")) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/D - Invalid ETC_EXIT_DATE : {}", etcExitDate);
				obj.setTrxType("R");
				obj.setTrxSubtype("D");
			}
			obj.setTrxDate("19000101");	
		}
		else 
		{
			obj.setTrxDate(etcExitDate); 
		}
			
		if (etcExitTime != null && !validation.timeValidation(etcExitTime, "HHMMSS")) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/D - Invalid ETC_EXIT_TIME : {}", etcExitTime);
				obj.setTrxType("R");
				obj.setTrxSubtype("D");
			}
			obj.setTrxTime("000000");
		}
		else
		{
			obj.setTrxTime(etcExitTime);
		}
				
		String date = validation.dateValidation(etcExitDate,"YYYYMMDD2") == true ? etcExitDate : "19000101";
		String time = validation.timeValidation(etcExitTime,"HHMMSS") == true ? etcExitTime : "000000";
		
		String txTimeStamp = date + time;
		obj.setTxTimeStamp(txTimeStamp);
		
		//etcRevenueDate - R/D
		if (etcRevenueDate != null && !validation.dateValidation(etcRevenueDate, "YYYYMMDD2")) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/D - Invalid ETC_REVENUE_DATE : {}", etcRevenueDate);
				obj.setTrxType("R");
				obj.setTrxSubtype("D");
			}
			obj.setEtcRevenueDate("19000101");	
		}
		else 
		{
			obj.setEtcRevenueDate(etcRevenueDate);
		}

		// etcActualAxles - R/X
		if(etcActualAxles.matches(".*[a-zA-Z].*") || etcActualAxles.equals("  ") || etcActualAxles.equals("**") ||
				Convertor.toInteger(etcActualAxles) < 0 || Convertor.toInteger(etcActualAxles) > 99) 
		{
			if(!obj.getTrxType().equals("R"))
			{
				logger.info("R/X - Invalid ETC_ACTUAL_AXLES : {}", etcActualAxles);
				obj.setTrxType("R");
				obj.setTrxSubtype("X");				
			}
		}
		else 
		{				
			obj.setActualAxles(Integer.parseInt(etcActualAxles));
		}
		
		//etcLaneMode - R/M
		if (etcLaneMode != null && !"EAMC".contains(etcLaneMode)) 
		{
			if(!obj.getTrxType().equals("R"))
			{
				logger.info("R/M - Invalid ETC_LANE_MODE : {}", etcLaneMode);
				obj.setTrxType("R");
				obj.setTrxSubtype("M");
			}
		}	
		else if (etcLaneMode != null && "EA".contains(etcLaneMode)) 
		{
			obj.setLaneMode("1");
		} 
		else if (etcLaneMode != null && "MC".contains(etcLaneMode)) 
		{
			obj.setLaneMode("7");
		} 
		else 
		{
			obj.setLaneMode("0");
		}
		
		// Vehicle Speed - R/N
		if(etcExitSpeed.matches(".*[a-zA-Z].*") || etcExitSpeed.equals("   ") || etcExitSpeed.contains(" ") 
				|| Convertor.toInteger(etcExitSpeed) < 0 || Convertor.toInteger(etcExitSpeed) > 999 ) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/N - Invalid ETC_EXIT_SPEED : {}", etcExitSpeed);
				obj.setTrxType("R");	
				obj.setTrxSubtype("N");
			}
		}
		else 
		{
			obj.setVehicleSpeed(Convertor.toInteger(etcExitSpeed));
		}
				
		
		// etcOverSpeed
		if (etcOverSpeed.equals("N")) 
		{
			obj.setSpeedViolation(QATPConstants.SPEED_NOT_OVER_THRESHOLD);
		} 
		else if (etcOverSpeed.equals("Y")) 
		{
			obj.setSpeedViolation(QATPConstants.SPEED_OVER_THRESHOLD);
		} 
		else if (etcOverSpeed.equals("S")) 
		{
			obj.setSpeedViolation(QATPConstants.AUTO_SUSPEND);
		} 
		else 
		{
			obj.setSpeedViolation(QATPConstants.SPEED_NOT_OVER_THRESHOLD);
		}
		
		// etcValidationStatus
		if(agencyIdPbaOrTiba == Integer.valueOf(QATPConstants.PBA_AGENCY_ID))
		{
			if (etcValidationStatus != null && "12".contains(etcValidationStatus)) 
			{
				obj.setEtcValidation(Convertor.toLong(etcValidationStatus));
			}	
			else 
			{
				obj.setEtcValidation(null);
			}
		}
		else if (agencyIdPbaOrTiba == Integer.valueOf(QATPConstants.TIBA_AGENCY_ID))
		{
			if (etcValidationStatus != null && "12".contains(etcValidationStatus)) // on hold because of ICD (should have 3 also)
			{
				obj.setEtcValidation(Convertor.toLong(etcValidationStatus));
			}	
			else 
			{
				obj.setEtcValidation(null);
			}
		}
		
		
		// etcReadPerformance
		if(etcReadPerformance.equals("**") || etcReadPerformance.equals("  "))
		{
			obj.setReadPerf(0);
		}
		else
		{
			obj.setReadPerf(Convertor.toInteger(etcReadPerformance));
		}
		
		// etcWritePerf
		if(etcWritePerf.equals("**") || etcWritePerf.equals("  "))
		{
			obj.setWritePerf(0);
		}
		else
		{
			obj.setWritePerf(Convertor.toInteger(etcWritePerf));
		}
		
		// etcTagPgmStatus
		if (etcTagPgmStatus != null && "SUF*".contains(etcTagPgmStatus)) 
		{
			obj.setProgStat(etcTagPgmStatus);
		}
		else if (etcTagPgmStatus != null && etcTagPgmStatus.equals(" "))
		{
			obj.setProgStat("*");
		}
		
		//etcDebitCredit
		if (etcDebitCredit != null && ( etcDebitCredit.equals("+") || etcDebitCredit.equals(" ") ) ) 
		{
			obj.setDebitCredit("+");
		}	
		else if (etcDebitCredit != null && etcDebitCredit.equals("-")) 
		{
			obj.setDebitCredit("-");
		}
		else 
		{
			obj.setDebitCredit("+");
		}
			
		// Reciprocity Trx
		if (obj.getDeviceNumber() != null) 
		{
			AgencyInfoVO checkAwayAgency = masterDataCache.checkAwayAgency(obj.getDeviceNumber().substring(0, 3));
			if (checkAwayAgency != null && checkAwayAgency.getIsHomeAgency() != null) 
			{
				obj.setReciprocityTrx(checkAwayAgency.getIsHomeAgency().equals("N") ? "T" : "F");
			} 
			else 
			{
				obj.setReciprocityTrx("F");
			}
		}

		//Amounts
		obj.setEtcFareAmount(etcTollAmount != null ? Double.valueOf(etcTollAmount) : 0d);
		obj.setExpectedReveneueAmount(etcTollAmount != null ? Double.valueOf(etcTollAmount) : 0d);
		obj.setPostedFareAmount(etcTollAmount != null ? Double.valueOf(etcTollAmount) : 0d);		
				
		obj.setReceivedDate(headerVO.getFileDate().substring(0, 8));
		obj.setFileNum(Convertor.toInteger(headerVO.getIctxFileNum()));
		obj.setExtFileId(xferControl.getXferControlId());
		
		obj.setTollSystemType("B");
		obj.setTollRevenueType(1);
		
		obj.setPlateNumber("**********");
		obj.setPlateState("**");
		obj.setPlateType("******************************");
		
		obj.setEtcFacAgency(etcFacAgency);
		
		
		obj.setAetFlag("N");
		obj.setDstFlag("N");
		obj.setIsViolation(0);

		return obj;

	}
	
	public boolean validate(String value) 
	{
		String matcher_String = "\\s{"+value.length()+"}|[*|0| ]{"+value.length()+"}";
        if(value.matches(matcher_String)) 
        {
            return true;
        }
        else
        {
            return false;
        }
	}

}
