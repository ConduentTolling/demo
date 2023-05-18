package com.conduent.tpms.qatp.dto;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.constants.CorrConstants;
import com.conduent.tpms.qatp.controller.QatpController;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.VehicleClass;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.FOTrnxFixlengthParser;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.MasterDataCache;

@Service
public class FOTrnxDTO {
	
	private static final Logger log = LoggerFactory.getLogger(FOTrnxDTO.class);

	/**
	 * File details record fields
	 */
	private static final long serialVersionUID = 2798677367041309955L;

	private String etcTrxSerialNum;
	private String EtcRevenuDate;
	private String etcFacAgency;
	private String etcTrxType;
	private String etcEntryDate;
	private String etcEntryTime;
	private String etcEntryPlaza; 
	private String etcEntryLane;
	private String etcTagAgency; 
	private String etcTagSerialNumber;
	private String etcReadPerformance;
	private String etcWritePref;
	private String etcTagPgmStatus;
	private String etcLaneMode;
	private String etcValidationStatus;
	private String etcLicState;
	private String etcLicNumber;
	private String etcClassCharged;
	private String etcActualAxles;
	private String etcExitSpeed;
	private String etcOverSpeed;
	private String etcExitDate;
	private String etcExitTime;
	private String etcExitPlaza;
	private String etcExitLane;
	private String etcDebitCredit;
	private String etcTollAmount;
	private String etcCorrReason;
	private String atpFileId;
	
	
	public String getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(String atpFileId) {
		this.atpFileId = atpFileId;
	}
	public String getEtcTrxSerialNum() {
		return etcTrxSerialNum;
	}
	public void setEtcTrxSerialNum(String etcTrxSerialNum) {
		this.etcTrxSerialNum = etcTrxSerialNum;
	}
	public String getEtcRevenuDate() {
		return EtcRevenuDate;
	}
	public void setEtcRevenuDate(String etcRevenuDate) {
		EtcRevenuDate = etcRevenuDate;
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
	public String getEtcWritePref() {
		return etcWritePref;
	}
	public void setEtcWritePref(String etcWritePref) {
		this.etcWritePref = etcWritePref;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEtcCorrReason() {
		return etcCorrReason;
	}
	public void setEtcCorrReason(String etcCorrReason) {
		this.etcCorrReason = etcCorrReason;
	}
	
	
	@Override
	public String toString() {
		return "FOTrnxDTO [etcTrxSerialNum=" + etcTrxSerialNum + ", EtcRevenuDate=" + EtcRevenuDate + ", etcFacAgency="
				+ etcFacAgency + ", etcTrxType=" + etcTrxType + ", etcEntryDate=" + etcEntryDate + ", etcEntryTime="
				+ etcEntryTime + ", etcEntryPlaza=" + etcEntryPlaza + ", etcEntryLane=" + etcEntryLane
				+ ", etcTagAgency=" + etcTagAgency + ", etcTagSerialNumber=" + etcTagSerialNumber
				+ ", etcReadPerformance=" + etcReadPerformance + ", etcWritePref=" + etcWritePref + ", etcTagPgmStatus="
				+ etcTagPgmStatus + ", etcLaneMode=" + etcLaneMode + ", etcValidationStatus=" + etcValidationStatus
				+ ", etcLicState=" + etcLicState + ", etcLicNumber=" + etcLicNumber + ", etcClassCharged="
				+ etcClassCharged + ", etcActualAxles=" + etcActualAxles + ", etcExitSpeed=" + etcExitSpeed
				+ ", etcOverSpeed=" + etcOverSpeed + ", etcExitDate=" + etcExitDate + ", etcExitTime=" + etcExitTime
				+ ", etcExitPlaza=" + etcExitPlaza + ", etcExitLane=" + etcExitLane + ", etcDebitCredit="
				+ etcDebitCredit + ", etcTollAmount=" + etcTollAmount + ", etcCorrReason=" + etcCorrReason
				+ ", atpFileId=" + atpFileId + "]";
	}
	
	
	public Transaction getTransaction(MasterDataCache masterDataCache, FOTrnxHeaderInfoVO headerVO,
			XferControl xferControl, TQatpMappingDao tQatpMappingDao,String fileType) 
	{
		Transaction obj = new Transaction();
		Plaza plaza=null;
		
		obj.setEtcCorrReason(etcCorrReason);
		
		if(etcTollAmount!=null && !etcTollAmount.equals("*******") && validEtcTollAmount(etcTollAmount))
		{
			String thresholdAmount = masterDataCache.getParkingThresholdValue();
			double result = Convertor.toDouble(etcTollAmount);
			double amount = result/100;
			if(amount >= Convertor.toInteger(thresholdAmount))
			{
				obj.setTrxType("C");
				obj.setTrxSubtype("Z");
			}
			else
			{
				obj.setTrxType("P");
				obj.setTrxSubtype("Z");
			}
			
			obj.setEtcTollAmount(Convertor.toInteger(etcTollAmount));
		}
		else
		{
			log.info("Etc Toll Amount is invalid {}",etcTollAmount);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
			obj.setEtcTollAmount(0);
		}
		
		if(!etcActualAxles.equals("**") && validActualAxles(etcActualAxles))
		{
			obj.setActualAxles(Convertor.toInteger(etcActualAxles));
		}
		else
		{
			log.info("etcActualAxles {} is invalid",etcActualAxles);
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
			obj.setActualAxles(0);
			
		}
		
		if(etcClassCharged.equals("***"))
		{
			obj.setActualClass(0);// etcClassCharged is unused field
		}
		else
		{
			log.info("etcClassCharged {} is invalid",etcClassCharged);
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
		}
		
//		if((etcExitPlaza!=null || !etcExitPlaza.equals("***") || !etcExitPlaza.equals("   ") || !etcExitPlaza.equals("000")) &&
//				(etcExitLane!=null || !etcExitLane.equals("***") || !etcExitLane.equals("   ") || !etcExitLane.equals("000")))
//		{
			plaza=masterDataCache.getPlaza(etcExitPlaza,Convertor.toInteger(CorrConstants.AGENCY_ID_FTXN));
			obj.setExternPlazaId(etcExitPlaza);
			obj.setExternLaneId(etcExitLane);
			if(plaza!=null)
			{
				obj.setExitPlaza(plaza.getPlazaId()!=null?plaza.getPlazaId():null);
				obj.setPlazaAgencyId(plaza.getAgencyId()!=null?plaza.getAgencyId():null);
				obj.setExitLane(masterDataCache.getLane(etcExitLane,plaza.getPlazaId())!=null?masterDataCache.getLane(etcExitLane,plaza.getPlazaId()).getLaneId():null);
				if(obj.getExitLane()==null)
				{
					log.info("Exit Lane ID doesnt exists for {}",etcExitLane);
					obj.setTrxType("R");
					obj.setTrxSubtype("L");
				}
			}
			else
			{
				log.info("Exit Plaza ID doesnt exists for {}",etcExitPlaza);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
//		}
//		else
//		{
//			log.info("Exit Plaza {} is invalid",etcExitPlaza);
//			obj.setTrxType("R");
//			obj.setTrxSubtype("L");
//		}
		
		String aetFlag=getAetFlag(obj,masterDataCache);
		obj.setAetFlag(aetFlag);
		
		obj.setExtFileId(xferControl.getXferControlId());
		
//		long atpFileId = tQatpMappingDao.getATPFileId(fileType, xferControl.getXferControlId());
//		
//		obj.setAtpFileId(atpFileId);
		
		String device_no = etcTagAgency + etcTagSerialNumber;
		
		if(!validDeviceNo(etcTagAgency,etcTagSerialNumber))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("T");
		}
		else
		{
			obj.setDeviceNumber(device_no);
		}
		
		if(!etcTrxSerialNum.equals("************") && validateTrxSerialNum(etcTrxSerialNum))
		{
			obj.setExtHostRefNum(etcTrxSerialNum);
		}
		else
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		
		LocalDate date = DateUtils.parseLocalDateRecieved(EtcRevenuDate, "yyyyMMdd");
		if(date == null)
		{
			log.info("Invalid revenue date {}",EtcRevenuDate);
			obj.setTrxType("R");
			obj.setTrxSubtype("R");
		}
		else
		{
			log.info("Revenue Date is Valid");
		}
		
		
		if(etcExitDate!=null && !validate(etcExitDate))
		{
			obj.setTrxDate(etcExitDate);
			obj.setTrxTime(etcExitTime);
			String temp=etcExitDate+etcExitTime;
			obj.setTxTimeStamp(temp);
		}
		else if(etcExitDate!=null && validate(etcExitDate))
		{
			obj.setTrxDate(CorrConstants.DEFAULT_DATE);
			obj.setTrxTime(CorrConstants.DEFAULT_TIME);
			String temp = CorrConstants.DEFAULT_DATE + CorrConstants.DEFAULT_TIME;
			obj.setTxTimeStamp(temp);
		}
		
		obj.setReceivedDate(headerVO.getFileDate().toString());
		
		if(etcLaneMode!=null)
		{
			if(etcLaneMode.equals("E"))
			{
				obj.setLaneMode("1");
			}
			else if (etcLaneMode.equals("A"))
			{
				obj.setLaneMode("2");
			}
			else if (etcLaneMode.equals("M"))
			{
				obj.setLaneMode("3");
			}
			else
			{
				obj.setLaneMode("0");
				obj.setTrxType("R");
				obj.setTrxSubtype("M");
			}
		}
		
		if(etcTagPgmStatus!=null)
		{
			if(etcTagPgmStatus.equals("S"))
			{
				obj.setProgStat("1");
			}
			else if (etcTagPgmStatus.equals("U"))
			{
				obj.setProgStat("2");
			}
			else if (etcTagPgmStatus.equals("F"))
			{
				obj.setProgStat("3");
			}
			else if (etcTagPgmStatus.equals("*"))
			{
				obj.setProgStat("0");
			}
			else
			{
				obj.setProgStat("0");
				obj.setTrxType("R");
				obj.setTrxSubtype("M");
			}
		}
		
		if(etcExitSpeed!=null && !etcExitSpeed.equals("***") && validExitSpeed(etcExitSpeed))
		{
			obj.setVehicleSpeed(Convertor.toInteger(etcExitSpeed));
		}
		else
		{
			log.info("Invalid Exit Speed {}",etcExitSpeed);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
			obj.setVehicleSpeed(0);
		}
		
		if(etcOverSpeed!=null || !etcOverSpeed.equals(" ") || !etcOverSpeed.equals("*"))
		{
			if(etcOverSpeed.equals("Y"))
			{
				obj.setSpeedViolation("1");
			}
			else if(etcOverSpeed.equals("N"))
			{
				obj.setSpeedViolation("0");
			}
			else
			{
				obj.setSpeedViolation("0");
				obj.setTrxType("R");
				obj.setTrxSubtype("N");
			}
		}
		else
		{
			log.info("etcOverSpeed {} is invalid",etcOverSpeed);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		
		if(etcValidationStatus!=null && (etcValidationStatus.equals("1") || etcValidationStatus.equals("*")))
		{
			obj.setEtcValidation(Convertor.toLong(etcValidationStatus));
		}
		else
		{
			log.info("etcValidationStatus {} is invalid",etcValidationStatus);
			obj.setTrxType("R");
			obj.setTrxSubtype("M");
			obj.setEtcValidation(0L);
		}
		
		if((etcEntryPlaza!=null || !etcEntryPlaza.equals("***") || !etcEntryPlaza.equals("   ") || !etcEntryPlaza.equals("000")))
		{
			plaza = masterDataCache.getPlaza(etcEntryPlaza,Convertor.toInteger(CorrConstants.AGENCY_ID_FTXN));  
			if(plaza!=null)
			{
				obj.setEntryPlaza(plaza.getPlazaId());
	
				TLaneDto lane=masterDataCache.getLane(etcEntryLane,plaza.getPlazaId()); 
				if(lane!=null)
				{
					obj.setEntryLaneId(lane.getLaneId());
				}
				else
				{
					log.info("Entry Lane ID doesnt exists {}",etcEntryLane);
					obj.setTrxType("R");
					obj.setTrxSubtype("L");
				}
			}
			else
			{
				log.info("Entry Plaza ID doesnt exists {}",etcEntryPlaza);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
		}
		else
		{
			log.info("Entry Plaza {}",etcEntryPlaza);
			obj.setTrxType("R");
			obj.setTrxSubtype("L");
		}
		
		if(etcEntryDate!=null && !validate(etcEntryDate))
		{
			String temp=etcEntryDate+etcEntryTime;
			obj.setEntryTimeStamp(temp);
		}
		
		obj.setTollRevenueType(1);
		
		if(etcDebitCredit.equals("+") || etcDebitCredit.equals("-"))
		{
			obj.setDebitCredit(etcDebitCredit);
		}
		else if (etcDebitCredit.equals(" "))
		{
			obj.setDebitCredit("+");
		}
		else
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		
		
		if(etcLicNumber.equals("**********"))
		{
			obj.setPlateNumber(null);
		}
		else
		{
			obj.setPlateNumber(etcLicNumber);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		
		if(etcLicState.equals("**"))
		{
			obj.setPlateState(null);
		}
		else
		{
			obj.setPlateState(etcLicState);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		
		
		if(etcReadPerformance.equals("**")) 
		{
			obj.setReadPerf(0);
		}
		else if(etcReadPerformance.equals("  ") || !validReadPerf(etcReadPerformance))
		{
			obj.setReadPerf(0);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		else
		{
			obj.setReadPerf(Convertor.toInteger(etcReadPerformance));
		}
		
		
		if(etcWritePref.equals("**")) 
		{
			obj.setWritePerf(0);
		}
		else if(etcWritePref.equals("  ") || !validWritePerf(etcWritePref))
		{
			obj.setWritePerf(0);
			obj.setTrxType("R");
			obj.setTrxSubtype("N");
		}
		else
		{
			obj.setWritePerf(Convertor.toInteger(etcWritePref));
		}
		
		if(!etcTrxType.equals("P") && fileType.equals(CorrConstants.FTXN))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
		}
		else
		{
			obj.setTollSystemType(etcTrxType);
		}
		
		if(!etcTrxType.equals("P") && !etcTrxType.equals("N") && fileType.equals(CorrConstants.ITXN))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
		}
		else
		{
			obj.setTollSystemType(etcTrxType);
		}
		
		obj.setFileNum(Convertor.toInteger(headerVO.getFntxFileNum()));
		
		AgencyInfoVO homeAgency = masterDataCache.isHomeAgency(etcFacAgency);
		if(validFacAgency(etcFacAgency) && homeAgency!=null) 
		{
			//TODO
			//log.info("Valid FAC Agency {}",etcFacAgency);
		}
		else
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("T");
		}
		
		System.out.println(obj);
		
		return obj;
	}
	
	private boolean validateTrxSerialNum(String etcTrxSerialNum2) 
	{
		try 
		{
			if(etcTrxSerialNum2.matches("[0-9]{12}"))
			{
				return true;
			}
			else
			{
				log.info("Invalid TRX_SERIAL_NUMBER {}",etcTrxSerialNum2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid TRX_SERIAL_NUMBER {}",etcTrxSerialNum2);
			return false;
		}	
		
	}
	
	private boolean validFacAgency(String etcFacAgency2) 
	{
		try 
		{
			if((Convertor.toInteger(etcFacAgency2) >= 128 && Convertor.toInteger(etcFacAgency2) <= 999))
			{
				return true;
			}
			else
			{
				log.info("Invalid FAC_AGENCY {}",etcFacAgency2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid FAC_AGENCY {}",etcFacAgency2);
			return false;
		}	
	}
	private boolean validWritePerf(String etcWritePref2) 
	{
		try {
			if(Convertor.toInteger(etcWritePref2)>=0 && Convertor.toInteger(etcWritePref2)<=99 && onlydigit(etcWritePref2))
			{
				return true;
			}
			else
			{
				log.info("Invalid WRITE PEFR {}",etcWritePref2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid WRITE PEFR {}",etcWritePref2);
			return false;
		}	
	}
	
	private boolean validReadPerf(String etcReadPerformance2) 
	{
		try {
			if(Convertor.toInteger(etcReadPerformance2)>=0 && Convertor.toInteger(etcReadPerformance2)<=99 && onlydigit(etcReadPerformance2))
			{
				return true;
			}
			else
			{
				log.info("Invalid READ PEFR {}",etcReadPerformance2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid READ PEFR {}",etcReadPerformance2);
			return false;
		}
	}
	
	private boolean validExitSpeed(String etcExitSpeed2) 
	{
		try {
			if(Convertor.toInteger(etcExitSpeed2)>=0 && Convertor.toInteger(etcExitSpeed2)<=999 && onlyDigitForSpeed(etcExitSpeed2))
			{
				return true;
			}
			else
			{
				log.info("Invalid EXIT SPEED {}",etcExitSpeed2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid EXIT SPEED {}",etcExitSpeed2);
			return false;
		}
		
	}
	
	private boolean validDeviceNo(String etcTagAgency2, String etcTagSerialNumber2) 
	{
		try 
		{
			if((!(Convertor.toInteger(etcTagAgency2) >= 0 && Convertor.toInteger(etcTagAgency2) <= 127) || 
			!(Convertor.toInteger(etcTagSerialNumber2)> 0 && Convertor.toInteger(etcTagSerialNumber2) <= 16777216)) || 
					(!(etcTagAgency2+etcTagSerialNumber2).matches("[0-9]{11}")))
			{
				log.info("Invalid DEVICE NO {}",etcTagAgency2+etcTagSerialNumber2);
				return false;
			}
			else
			{
				return true;
			}
		} catch (Exception e) 
		{
			log.info("Invalid DEVICE NO {}",etcTagAgency2+etcTagSerialNumber2);
			return false;
		}	
		
	}
	
	private boolean validActualAxles(String etcActualAxles2) 
	{
		try 
		{
			if((Convertor.toInteger(etcActualAxles2)>=0 && Convertor.toInteger(etcActualAxles2)<=99) && onlydigit(etcActualAxles2))
			{
				return true;
			}
			else
			{
				log.info("Invalid ACTUAL AXLES {}",etcActualAxles2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid ACTUAL AXLES {}",etcActualAxles2);
			return false;
		}
	}
	
	private boolean onlydigit(String etcActualAxles2) {
		
		if(etcActualAxles2.matches("[0-9]{2}"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean onlyDigitForSpeed(String etcExitSpeed2) {
		if(etcExitSpeed2.matches("[0-9]{3}"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean onlyDigitForAmount(String amount) {
		if(amount.matches("[0-9]{7}"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean validEtcTollAmount(String etcTollAmount2) 
	{
		try 
		{
			if((Convertor.toInteger(etcTollAmount2)>=0 && Convertor.toInteger(etcTollAmount2)<=9999999) && onlyDigitForAmount(etcTollAmount2))
			{
				return true;
			}
			else
			{
				log.info("Invalid ETC TOLL AMOUNT {}",etcTollAmount2);
				return false;
			}
		} catch (Exception e) 
		{
			log.info("Invalid ETC TOLL AMOUNT {}",etcTollAmount2);
			return false;
		}
	}
	
	private LocalDateTime validDate(String temp) 
	{
		try 
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			return LocalDateTime.parse(temp, formatter);
		} catch (Exception e) 
		{
			return null;
		}
	}
	
		private String getAetFlag(Transaction obj, MasterDataCache masterDataCache) {

			String aetFlag=null;
			TLaneDto lane = null;
			
			Plaza plaza = masterDataCache.getPlaza(obj.getExternPlazaId(),Convertor.toInteger(CorrConstants.AGENCY_ID_FTXN));
			if(plaza != null)
			{
				lane = masterDataCache.getLane(obj.getExternLaneId(),plaza.getPlazaId());
				
				if(lane!=null && lane.getLprEnabled()!=null) {
					if(lane.getLprEnabled().equals("Y")) {
						aetFlag="Y";
					}else {
						aetFlag="N";
					}
				}
				else if(plaza!=null && plaza.getLprEnabled()!=null) {
					if(plaza.getLprEnabled().equals("Y")) {
						aetFlag="Y";
					}else {
						aetFlag="N";
					}
				}
				else
				{
					aetFlag="N";
				}
				
				return aetFlag;
			}
			else
			{
				log.info("Plaza is null for extern plaza id : {}",obj.getExternPlazaId());
				aetFlag="N";
			}
			return aetFlag;	
		}

	public boolean validate(String value)
	{
		String matcher_String = "\\s{"+value.length()+"}|[*|0]{"+value.length()+"}";
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