package com.conduent.tpms.qatp.dto;


import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.VehicleClass;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.MasterDataCache;

@Service
public class NystaAetTxDto {
	
	private static final Logger logger = LoggerFactory.getLogger(NystaFixlengthParser.class);
	
	private boolean isUnmatched;

	private String  etcTrxTypeNew;
	private String  recordTypeCode;
	private String  etcTrxType;
	private String  etcTrxTypeSubType;
	private String  etcEntryDate;
	private String  etcEntryTime;
	private String  etcEntryPlazaId;
	private String  etcEntryLaneId;
	private String  etcEntryDataSource;
	private String  etcAppId;
	private String  etcType;
	private String  etcGroupId;
	private String  etcAgencyId;
	private String  etcTagSerialNum;
	private String  etcSerialNumber;
	private String  etcReadPerformance;
	private String  etcLaneMode;
	private String  etcCollectorId;
	private String  etcDerivedVehClass;
	private String  etcReadVehType;
	private String  etcReadVehAxles;
	private String  etcReadVehWeight;
	private String  etcReadRearTires;
	private String  etcWriteVehType;
	private String  etcWriteVehAxles;
	private String  etcWriteVehWeight;
	private String  etcWriteRearTires;
	private String  etcIndVehClass;
	private String  etcValidationStatus;
	private String  etcViolObserved;
	private String  etcImageCaptured;
	private String  etcRevenueType;
	private String  etcReadAgencyData;
	private String  etcWritAgencyData;
	private String  etcPreWritTrxNum;
	private String  etcPostWritTrxNum;
	private String  etcIndVehAxles;
	private String  etcIndAxleOffset;
	private String  etcVehSpeed;
	private String  etcExitDate;
	private String  etcExitTime;
	private String  etcExitPlazaId;
	private String  etcExitLaneId;
	private String  etcBufferedFeed;
	private String  etcTxnSrlNum;
	private String  frontOcrPlateCountry;
	private String  frontOcrPlateState;
	private String  frontOcrPlateType;
	private String  frontOcrPlateNumber;
	private String  frontOcrConfidence;
	private String  frontOcrImageIndex;
	private String  frontImageColor;
	private String  rearOcrPlateCountry;
	private String  rearOcrPlateState;
	private String  rearOcrPlateType;
	private String  rearOcrPlateNumber;
	private String  rearOcrConfidence;
	private String  rearOcrImageIndex;
	private String  rearImageColor;
	private String  etcRecordTerm;
	private String tollSystemType;
	private String txExternRefNoMatched;
	private String etcClassMismatch;
	
	public String getTxExternRefNoMatched() {
		return txExternRefNoMatched;
	}
	public void setTxExternRefNoMatched(String txExternRefNoMatched) {
		this.txExternRefNoMatched = txExternRefNoMatched;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public String getEtcTrxTypeSubType() {
		return etcTrxTypeSubType;
	}
	public void setEtcTrxTypeSubType(String etcTrxTypeSubType) {
		this.etcTrxTypeSubType = etcTrxTypeSubType;
	}
	public String getEtcTrxTypeNew() {
		return etcTrxTypeNew;
	}
	public void setEtcTrxTypeNew(String etcTrxTypeNew) {
		this.etcTrxTypeNew = etcTrxTypeNew;
	}
	public String getRecordTypeCode() {
		return recordTypeCode;
	}
	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
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
	public String getEtcEntryPlazaId() {
		return etcEntryPlazaId;
	}
	public void setEtcEntryPlazaId(String etcEntryPlazaId) {
		this.etcEntryPlazaId = etcEntryPlazaId;
	}
	public String getEtcEntryLaneId() {
		return etcEntryLaneId;
	}
	public void setEtcEntryLaneId(String etcEntryLaneId) {
		this.etcEntryLaneId = etcEntryLaneId;
	}
	public String getEtcEntryDataSource() {
		return etcEntryDataSource;
	}
	public void setEtcEntryDataSource(String etcEntryDataSource) {
		this.etcEntryDataSource = etcEntryDataSource;
	}
	public String getEtcAppId() {
		return etcAppId;
	}
	public void setEtcAppId(String etcAppId) {
		this.etcAppId = etcAppId;
	}
	public String getEtcType() {
		return etcType;
	}
	public void setEtcType(String etcType) {
		this.etcType = etcType;
	}
	public String getEtcGroupId() {
		return etcGroupId;
	}
	public void setEtcGroupId(String etcGroupId) {
		this.etcGroupId = etcGroupId;
	}
	public String getEtcAgencyId() {
		return etcAgencyId;
	}
	public void setEtcAgencyId(String etcAgencyId) {
		this.etcAgencyId = etcAgencyId;
	}
	public String getEtcTagSerialNum() {
		return etcTagSerialNum;
	}
	public void setEtcTagSerialNum(String etcTagSerialNum) {
		this.etcTagSerialNum = etcTagSerialNum;
	}
	public String getEtcSerialNumber() {
		return etcSerialNumber;
	}
	public void setEtcSerialNumber(String etcSerialNumber) {
		this.etcSerialNumber = etcSerialNumber;
	}
	public String getEtcReadPerformance() {
		return etcReadPerformance;
	}
	public void setEtcReadPerformance(String etcReadPerformance) {
		this.etcReadPerformance = etcReadPerformance;
	}
	public String getEtcLaneMode() {
		return etcLaneMode;
	}
	public void setEtcLaneMode(String etcLaneMode) {
		this.etcLaneMode = etcLaneMode;
	}
	public String getEtcCollectorId() {
		return etcCollectorId;
	}
	public void setEtcCollectorId(String etcCollectorId) {
		this.etcCollectorId = etcCollectorId;
	}
	public String getEtcDerivedVehClass() {
		return etcDerivedVehClass;
	}
	public void setEtcDerivedVehClass(String etcDerivedVehClass) {
		this.etcDerivedVehClass = etcDerivedVehClass;
	}
	public String getEtcReadVehType() {
		return etcReadVehType;
	}
	public void setEtcReadVehType(String etcReadVehType) {
		this.etcReadVehType = etcReadVehType;
	}
	public String getEtcReadVehAxles() {
		return etcReadVehAxles;
	}
	public void setEtcReadVehAxles(String etcReadVehAxles) {
		this.etcReadVehAxles = etcReadVehAxles;
	}
	public String getEtcReadVehWeight() {
		return etcReadVehWeight;
	}
	public void setEtcReadVehWeight(String etcReadVehWeight) {
		this.etcReadVehWeight = etcReadVehWeight;
	}
	public String getEtcReadRearTires() {
		return etcReadRearTires;
	}
	public void setEtcReadRearTires(String etcReadRearTires) {
		this.etcReadRearTires = etcReadRearTires;
	}
	public String getEtcWriteVehType() {
		return etcWriteVehType;
	}
	public void setEtcWriteVehType(String etcWriteVehType) {
		this.etcWriteVehType = etcWriteVehType;
	}
	public String getEtcWriteVehAxles() {
		return etcWriteVehAxles;
	}
	public void setEtcWriteVehAxles(String etcWriteVehAxles) {
		this.etcWriteVehAxles = etcWriteVehAxles;
	}
	public String getEtcWriteVehWeight() {
		return etcWriteVehWeight;
	}
	public void setEtcWriteVehWeight(String etcWriteVehWeight) {
		this.etcWriteVehWeight = etcWriteVehWeight;
	}
	public String getEtcWriteRearTires() {
		return etcWriteRearTires;
	}
	public void setEtcWriteRearTires(String etcWriteRearTires) {
		this.etcWriteRearTires = etcWriteRearTires;
	}
	public String getEtcIndVehClass() {
		return etcIndVehClass;
	}
	public void setEtcIndVehClass(String etcIndVehClass) {
		this.etcIndVehClass = etcIndVehClass;
	}
	public String getEtcValidationStatus() {
		return etcValidationStatus;
	}
	public void setEtcValidationStatus(String etcValidationStatus) {
		this.etcValidationStatus = etcValidationStatus;
	}
	public String getEtcViolObserved() {
		return etcViolObserved;
	}
	public void setEtcViolObserved(String etcViolObserved) {
		this.etcViolObserved = etcViolObserved;
	}
	public String getEtcImageCaptured() {
		return etcImageCaptured;
	}
	public void setEtcImageCaptured(String etcImageCaptured) {
		this.etcImageCaptured = etcImageCaptured;
	}
	public String getEtcRevenueType() {
		return etcRevenueType;
	}
	public void setEtcRevenueType(String etcRevenueType) {
		this.etcRevenueType = etcRevenueType;
	}
	public String getEtcReadAgencyData() {
		return etcReadAgencyData;
	}
	public void setEtcReadAgencyData(String etcReadAgencyData) {
		this.etcReadAgencyData = etcReadAgencyData;
	}
	public String getEtcWritAgencyData() {
		return etcWritAgencyData;
	}
	public void setEtcWritAgencyData(String etcWritAgencyData) {
		this.etcWritAgencyData = etcWritAgencyData;
	}
	public String getEtcPreWritTrxNum() {
		return etcPreWritTrxNum;
	}
	public void setEtcPreWritTrxNum(String etcPreWritTrxNum) {
		this.etcPreWritTrxNum = etcPreWritTrxNum;
	}
	public String getEtcPostWritTrxNum() {
		return etcPostWritTrxNum;
	}
	public void setEtcPostWritTrxNum(String etcPostWritTrxNum) {
		this.etcPostWritTrxNum = etcPostWritTrxNum;
	}
	public String getEtcIndVehAxles() {
		return etcIndVehAxles;
	}
	public void setEtcIndVehAxles(String etcIndVehAxles) {
		this.etcIndVehAxles = etcIndVehAxles;
	}
	public String getEtcIndAxleOffset() {
		return etcIndAxleOffset;
	}
	public void setEtcIndAxleOffset(String etcIndAxleOffset) {
		this.etcIndAxleOffset = etcIndAxleOffset;
	}
	public String getEtcVehSpeed() {
		return etcVehSpeed;
	}
	public void setEtcVehSpeed(String etcVehSpeed) {
		this.etcVehSpeed = etcVehSpeed;
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
	public String getEtcExitPlazaId() {
		return etcExitPlazaId;
	}
	public void setEtcExitPlazaId(String etcExitPlazaId) {
		this.etcExitPlazaId = etcExitPlazaId;
	}
	public String getEtcExitLaneId() {
		return etcExitLaneId;
	}
	public void setEtcExitLaneId(String etcExitLaneId) {
		this.etcExitLaneId = etcExitLaneId;
	}
	public String getEtcBufferedFeed() {
		return etcBufferedFeed;
	}
	public void setEtcBufferedFeed(String etcBufferedFeed) {
		this.etcBufferedFeed = etcBufferedFeed;
	}
	public String getEtcTxnSrlNum() {
		return etcTxnSrlNum;
	}
	public void setEtcTxnSrlNum(String etcTxnSrlNum) {
		this.etcTxnSrlNum = etcTxnSrlNum;
	}
	public String getFrontOcrPlateCountry() {
		return frontOcrPlateCountry;
	}
	public void setFrontOcrPlateCountry(String frontOcrPlateCountry) {
		this.frontOcrPlateCountry = frontOcrPlateCountry;
	}
	public String getFrontOcrPlateState() {
		return frontOcrPlateState;
	}
	public void setFrontOcrPlateState(String frontOcrPlateState) {
		this.frontOcrPlateState = frontOcrPlateState;
	}
	public String getFrontOcrPlateType() {
		return frontOcrPlateType;
	}
	public void setFrontOcrPlateType(String frontOcrPlateType) {
		this.frontOcrPlateType = frontOcrPlateType;
	}
	public String getFrontOcrPlateNumber() {
		return frontOcrPlateNumber;
	}
	public void setFrontOcrPlateNumber(String frontOcrPlateNumber) {
		this.frontOcrPlateNumber = frontOcrPlateNumber;
	}
	public String getFrontOcrConfidence() {
		return frontOcrConfidence;
	}
	public void setFrontOcrConfidence(String frontOcrConfidence) {
		this.frontOcrConfidence = frontOcrConfidence;
	}
	public String getFrontOcrImageIndex() {
		return frontOcrImageIndex;
	}
	public void setFrontOcrImageIndex(String frontOcrImageIndex) {
		this.frontOcrImageIndex = frontOcrImageIndex;
	}
	public String getFrontImageColor() {
		return frontImageColor;
	}
	public void setFrontImageColor(String frontImageColor) {
		this.frontImageColor = frontImageColor;
	}
	public String getRearOcrPlateCountry() {
		return rearOcrPlateCountry;
	}
	public void setRearOcrPlateCountry(String rearOcrPlateCountry) {
		this.rearOcrPlateCountry = rearOcrPlateCountry;
	}
	public String getRearOcrPlateState() {
		return rearOcrPlateState;
	}
	public void setRearOcrPlateState(String rearOcrPlateState) {
		this.rearOcrPlateState = rearOcrPlateState;
	}
	public String getRearOcrPlateType() {
		return rearOcrPlateType;
	}
	public void setRearOcrPlateType(String rearOcrPlateType) {
		this.rearOcrPlateType = rearOcrPlateType;
	}
	public String getRearOcrPlateNumber() {
		return rearOcrPlateNumber;
	}
	public void setRearOcrPlateNumber(String rearOcrPlateNumber) {
		this.rearOcrPlateNumber = rearOcrPlateNumber;
	}
	public String getRearOcrConfidence() {
		return rearOcrConfidence;
	}
	public void setRearOcrConfidence(String rearOcrConfidence) {
		this.rearOcrConfidence = rearOcrConfidence;
	}
	public String getRearOcrImageIndex() {
		return rearOcrImageIndex;
	}
	public void setRearOcrImageIndex(String rearOcrImageIndex) {
		this.rearOcrImageIndex = rearOcrImageIndex;
	}
	public String getRearImageColor() {
		return rearImageColor;
	}
	public void setRearImageColor(String rearImageColor) {
		this.rearImageColor = rearImageColor;
	}
	public String getEtcRecordTerm() {
		return etcRecordTerm;
	}
	public void setEtcRecordTerm(String etcRecordTerm) {
		this.etcRecordTerm = etcRecordTerm;
	}
	
	public String getEtcClassMismatch() {
		return etcClassMismatch;
	}
	public void setEtcClassMismatch(String etcClassMismatch) {
		this.etcClassMismatch = etcClassMismatch;
	}
	
	public Transaction getTransaction(MasterDataCache masterDataCache,NystaHeaderInfoVO headerInfoVO,XferControl xferControl, Integer agencyIdNystaOrNysba) throws InterruptedException
	{
		Transaction obj=new Transaction();
		obj.setTrxType("*");
		
		Plaza plaza = null;
		
		boolean barrier=false;
		boolean entry=false;
		boolean complete=false;
		boolean exit=false;

		if(etcTrxType!=null && agencyIdNystaOrNysba == 1)
		{
			if (etcTrxType.equals("B") || etcTrxType.endsWith("P"))
			{
				barrier=true;
			}
			if (etcTrxType.equals("E"))
			{
				entry=true;
			}
			if (etcTrxType.equals("C") || etcTrxType.endsWith("T"))
			{
				complete=true;
			}
			if (etcTrxType.equals("X"))
			{
				exit=true;
			}
		}
		else if (etcTrxType!=null && agencyIdNystaOrNysba == 4)
		{
			if (etcTrxType.equals("B"))
			{
				barrier=true;
			}
			else
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("X");
				
				//PB said to add this condition for bug 9941 & 9942
				plaza=masterDataCache.getPlaza(etcExitPlazaId,agencyIdNystaOrNysba);
				obj.setExternPlazaId(etcExitPlazaId);
				obj.setExternLaneId(etcExitLaneId);
				if(plaza!=null)
				{
					obj.setExitPlaza(plaza.getPlazaId());
					obj.setPlazaAgencyId(plaza.getAgencyId());
					obj.setExitLane(masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId())!=null?masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId()).getLaneId():null);
				}
			}
		}
		
		
		//PB told to add condition not B ,P and X
		if(!(barrier || exit) && (complete || entry))
		{
			plaza = masterDataCache.getPlaza(etcEntryPlazaId,agencyIdNystaOrNysba);  
			if(plaza!=null)
			{
				obj.setEntryPlaza(plaza.getPlazaId());
				
				if(masterDataCache.getInsertLaneParamter()!=null && masterDataCache.getInsertLaneParamter().equals("N"))
				{
					TLaneDto lane=masterDataCache.getLane(etcEntryLaneId,plaza.getPlazaId()); 
					if(lane!=null)
					{
						obj.setEntryLaneId(lane.getLaneId());
					}
				}
				else if(masterDataCache.getInsertLaneParamter()!=null && masterDataCache.getInsertLaneParamter().equals("Y"))
				{
					int lane=masterDataCache.getLaneId(etcEntryLaneId,plaza.getPlazaId()); 
					if(lane>0)
					{
						obj.setEntryLaneId(lane);
					}
				}
			}
		}

		obj.setTollRevenueType(1);
		obj.setTollSystemType(etcTrxType);
		obj.setEntryDate(etcEntryDate);
		obj.setEntryTime(etcEntryTime);
		String temp=etcEntryDate+etcEntryTime;
		obj.setEntryTimeStamp(temp);
		
		if(obj.getTollSystemType().equals("X") && etcTrxType.equals("X"))
		{
			obj.setTrxType("U");
			//obj.setTrxSubtype("X");
			obj.setTrxSubtype("E");		//changed for posting
		}
		
		obj.setEntryDataSource(etcEntryDataSource);

		if(obj.getTollSystemType()!=null && !obj.getTollSystemType().equals("C") && !obj.getTollSystemType().equals("T"))
		{
			obj.setVehicleSpeed(Convertor.toInteger(etcVehSpeed));
		}
		
		if(etcAgencyId!= null && !validate(etcAgencyId)) {
		AgencyInfoVO agencyInfoVO=masterDataCache.getAgency(etcAgencyId);
			if(agencyInfoVO==null) {
			
			obj.setTrxType("R");
			obj.setTrxSubtype("T");
			}
		}	
		
		obj.setDeviceAgencyId(etcAgencyId);
		obj.setDeviceNumber(etcAgencyId+etcTagSerialNum);
		obj.setEntryLaneSN(Convertor.toLong(etcSerialNumber));
		obj.setLaneType("*");
		obj.setLaneState("*");
		obj.setLaneMode(etcLaneMode);
		obj.setCollectorNumber(Convertor.toLong(etcCollectorId));
		obj.setActualAxles(Convertor.toInteger(etcIndVehAxles));
		Integer xtraAxles=Convertor.toInteger(etcIndVehAxles);
		if(xtraAxles==null)
		{
			xtraAxles=0;
		}
		xtraAxles = xtraAxles + (Convertor.toInteger(etcIndAxleOffset)!=null?Convertor.toInteger(etcIndAxleOffset):0);

		if (validate(etcIndVehClass) && validate(etcDerivedVehClass))
		{
			obj.setActualClass(Integer.valueOf(0));
		}
		if((etcIndVehClass!=null && (etcIndVehClass.equals("00") || etcIndVehClass.equals("09"))) || (etcDerivedVehClass!=null && ( etcDerivedVehClass.equals("00") || etcDerivedVehClass.equals("09"))))
		{
			//obj.setActualClass(Integer.valueOf(22));
			etcIndVehClass = "**";
			etcDerivedVehClass="**";
		}
		else if (!validate(etcIndVehClass) && (entry || complete))	//PB told to add condition not B ,P and X
		{
			VehicleClass vehicleClass=masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcIndVehClass);
			if(plaza!=null && etcIndVehClass!=null) 
			{

			}else {
				logger.info("plaza value : null and etcIndVehClass value : "+etcIndVehClass);
			}
			if(vehicleClass == null) //PB told to remove the commented lines
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
			else
			{
				obj.setActualClass(vehicleClass.getAgencyClass());
				obj.setAvcClass(obj.getActualClass());
				obj.setAvcAxles(vehicleClass.getBaseAxleCount());
				if( obj.getAvcAxles()!=null && xtraAxles!=null && xtraAxles > obj.getAvcAxles()) //PB told to remove the commented lines
				{
					xtraAxles = xtraAxles - obj.getAvcAxles();
				}
			}

		}
		else if (!validate(etcDerivedVehClass) && (entry || complete))		//PB told to add condition not B ,P and X
		{
			VehicleClass vehicleClass=masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcDerivedVehClass);	//entryPlazaId
			if(vehicleClass==null)
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
			else
			{
				obj.setActualClass(vehicleClass.getAgencyClass());
				obj.setTagAxles(vehicleClass.getAgencyClass());
				obj.setAvcAxles(vehicleClass.getBaseAxleCount());

				if(obj.getAvcAxles()!=null && xtraAxles!=null && xtraAxles > obj.getAvcAxles()) //PB told to remove the commented lines
				{
					xtraAxles=xtraAxles-obj.getAvcAxles();
				}
			}
		}
		obj.setExtraAxles(xtraAxles);

		obj.setTagAxles(Convertor.toInteger(etcReadVehAxles));
		obj.setTagIAGClass(0);
		obj.setAvcAxles(Convertor.toInteger(etcReadVehAxles));
		obj.setEtcValidation(Convertor.toLong(etcValidationStatus));
		obj.setReadPerf(Convertor.toInteger(etcReadPerformance));
		obj.setWritePerf(Integer.valueOf(0));
		obj.setProgStat("0");

		if(etcImageCaptured!=null && etcImageCaptured.equals("0"))
		{
			obj.setImageCaptured("F");
		}
		else
		{
			obj.setImageCaptured("T");
		}

		
		if(Convertor.toInteger(etcVehSpeed)!=null)
		{
			obj.setVehicleSpeed(Convertor.toInteger(etcVehSpeed));
		}
		else
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("C");
		}
		obj.setSpeedViolation("F");
		obj.setReciprocityTrx("F");
		
		if(etcAgencyId!= null && !((validate(etcAgencyId)) || (validate(etcAgencyId)) || (validate(etcAgencyId)))) {
		AgencyInfoVO homeAgency = masterDataCache.isHomeAgency(etcAgencyId);
		String isHomeAgency = null;
		if(homeAgency!=null) {
			
			isHomeAgency = homeAgency.getIsHomeAgency();
		}

		if(isHomeAgency!=null && isHomeAgency.equals("N"))
		{
			obj.setReciprocityTrx("T");
		}
		}
			//set cond for not entry // told by PB
		if(exit || complete || barrier)
		{
			plaza=masterDataCache.getPlaza(etcExitPlazaId,agencyIdNystaOrNysba);
			obj.setExternPlazaId(etcExitPlazaId);
			obj.setExternLaneId(etcExitLaneId);
			if(plaza!=null)
			{
				obj.setExitPlaza(plaza.getPlazaId());
				obj.setPlazaAgencyId(plaza.getAgencyId());
				if(masterDataCache.getInsertLaneParamter()!=null && masterDataCache.getInsertLaneParamter().equals("N"))
				{
					obj.setExitLane(masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId())!=null?masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId()).getLaneId():null);
				}
				else if(masterDataCache.getInsertLaneParamter()!=null && masterDataCache.getInsertLaneParamter().equals("Y"))
				{
					int lane=masterDataCache.getLaneId(etcExitLaneId,plaza.getPlazaId()); 
					if(lane>0)
					{
						obj.setExitLane(lane);
					}
				}
			}
			else
			{
				logger.info("Exit Plaza ID doesnt exists for {}",etcExitPlazaId);
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			
		}
		if(etcExitDate!=null && !validate(etcExitDate))
		{
			obj.setTrxDate(etcExitDate);
			obj.setTrxTime(etcExitTime);
			temp=etcExitDate+etcExitTime;
			obj.setTxTimeStamp(temp);
		}
		else if(etcExitDate!=null && validate(etcExitDate) && entry && etcEntryDate!=null && !etcEntryDate.equals("********") && etcEntryTime!=null && !etcEntryTime.equals("********"))
		{
			obj.setTrxDate(etcEntryDate);
			obj.setTrxTime(etcEntryTime);
			temp = etcEntryDate+etcEntryTime;
			obj.setTxTimeStamp(temp);
		}
		
		obj.setTrxLaneSN(Convertor.toLong(etcSerialNumber));

		if(etcBufferedFeed!=null && etcBufferedFeed.equals("R"))
		{
			obj.setBufRead("T");
		}
		else
		{
			obj.setBufRead("F");
		}

		obj.setLicensePlate("**********");
		obj.setLicenseState("**");
		obj.setLaneHealth("****");
		obj.setReaderId("****");
		obj.setExtHostRefNum(getEtcTxnSrlNum());
		obj.setReceivedDate(obj.getTrxDate());
		obj.setCorrection("**********");
		obj.setTourSegmentId(Long.valueOf(0));
		obj.setDstFlag("*");
		obj.setLaneDataSource("E");

		if(recordTypeCode!=null && !recordTypeCode.equals("["))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("R");
		}
		if(etcRecordTerm!=null && !etcRecordTerm.equals("]"))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("R");
		}

		if(etcTrxType!=null && !"BCEXPT".contains(etcTrxType) && agencyIdNystaOrNysba == 1)
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		}
		if(etcValidationStatus!=null && !"12345".contains(etcValidationStatus))
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		}
		
		if ((entry || complete) && etcEntryDataSource!=null)// && etcEntryDataSource.equals("T"))
		{
			if(DateUtils.dateValidation(etcEntryDate, Constants.DATE_YYYYMMDD).equals(Boolean.FALSE) || (DateUtils.timeValidation(etcEntryTime,"HHMMSSTT").equals(Boolean.FALSE)) )
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("E");
			}
			
			if (etcEntryDataSource!=null &&  !"ET*".contains(etcEntryDataSource))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			
			if ((etcEntryPlazaId!=null && validate(etcEntryPlazaId)) || (etcEntryLaneId!=null && validate(etcEntryLaneId)))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
		}

		if( !(Convertor.toInteger(etcSerialNumber)!=null &&  Convertor.toInteger(etcSerialNumber)>=0 && Convertor.toInteger(etcSerialNumber) <=32000))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcSerialNumber = "0";
		}
		else if(validateSpaces(etcSerialNumber))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcSerialNumber = "0";
		}

		if(!(Convertor.toInteger(etcReadPerformance)!=null && Convertor.toInteger(etcReadPerformance)>=0 
				&& Convertor.toInteger(etcReadPerformance) <=99) && !barrier)
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcReadPerformance = "0";
		}
		else if(validateSpaces(etcReadPerformance))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcReadPerformance = "0";
		}
		
		if(etcLaneMode!=null && !"139".contains(etcLaneMode))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("M");
		}
		
		if(!(Convertor.toInteger(etcCollectorId)!=null && Convertor.toInteger(etcCollectorId)>=0 && Convertor.toInteger(etcCollectorId) <=99999))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcCollectorId = "0";
		}
		else if(validateSpaces(etcCollectorId))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcCollectorId = "0";
		}

		if(etcValidationStatus!=null && !etcValidationStatus.equals("5"))
		//if(etcValidationStatus!=null) // PB told to do for UAT Bug 68471
		{
			int derivedClassStatus=0;
			if(!validate(etcDerivedVehClass))
			{
				VehicleClass vehicleClass=masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcDerivedVehClass);	//exitPlazaId
				
				if(vehicleClass!=null)
				{
					derivedClassStatus=1;
					obj.setActualClass(vehicleClass.getAgencyClass());
					obj.setAvcClass(obj.getActualClass());
					obj.setAvcAxles(vehicleClass.getBaseAxleCount());
				}
				else
				{
					if(etcDerivedVehClass!=null && (etcDerivedVehClass.equals("00") || etcDerivedVehClass.equals("09") || etcDerivedVehClass.equals("33") || etcDerivedVehClass.equals("37") || etcDerivedVehClass.equals("73") || etcDerivedVehClass.equals("77") ))
					{
						derivedClassStatus=1;
					}
	//				else
	//				{
	//					obj.setTrxType("R");			//as per changes told by ronnie it should not be rejected
	//					obj.setTrxSubtype("C");
	//				}
				}
			}
			
			else if (!validate(etcIndVehClass))				//added due to bug 9943 was causing issue in posting
			{
				VehicleClass vehicleClass=masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcIndVehClass);	//exitPlazaId
				if(vehicleClass!=null)
				{
					derivedClassStatus=1;
					obj.setActualClass(vehicleClass.getAgencyClass());
					obj.setAvcClass(obj.getActualClass());
					obj.setAvcAxles(vehicleClass.getBaseAxleCount());
				}
				else
				{
					if(etcIndVehClass!=null && (etcIndVehClass.equals("00") || etcIndVehClass.equals("09") || etcIndVehClass.equals("33") || etcIndVehClass.equals("37") || etcIndVehClass.equals("73") || etcIndVehClass.equals("77") ))
					{
						derivedClassStatus=1;
					}
				}
			}
			
			if(!(Convertor.toInteger(etcReadVehType)!=null && Convertor.toInteger(etcReadVehType)>=0 
					&& Convertor.toInteger(etcReadVehType)<=31) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcReadVehType = "0";
			}
			else if(validateSpaces(etcReadVehType))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcReadVehType = "0";
			}
			
			if(!(Convertor.toInteger(etcReadVehAxles)!=null && Convertor.toInteger(etcReadVehAxles)>=0 
					&& Convertor.toInteger(etcReadVehAxles)<=15) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcReadVehAxles = "0";
			}
			else if(validateSpaces(etcReadVehAxles))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcReadVehAxles = "0";
			}
			
			if(etcReadVehWeight!=null && !"01".contains(etcReadVehWeight) && !barrier) 
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			else if(validateSpaces(etcReadVehWeight))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			
			if(etcReadRearTires!=null && !"01".contains(etcReadRearTires) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			else if(validateSpaces(etcReadRearTires))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			
			if(Convertor.toInteger(etcWriteVehType)!=null && !(Convertor.toInteger(etcWriteVehType)>=0 
					&& Convertor.toInteger(etcWriteVehType)<=31) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcWriteVehType = "0";
			}
			else if(validateSpaces(etcWriteVehType))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcWriteVehType = "0";
			}
						
			if(Convertor.toInteger(etcWriteVehAxles)!=null && !(Convertor.toInteger(etcWriteVehAxles)>=0 
					&& Convertor.toInteger(etcWriteVehAxles)<=15) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcWriteVehAxles = "0";
			}
			else if(validateSpaces(etcWriteVehAxles))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
				etcWriteVehAxles = "0";
			}
			
			if(etcWriteVehWeight!=null && !"01".contains(etcWriteVehWeight) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			else if(validateSpaces(etcWriteVehWeight))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			
			if(etcWriteRearTires!=null && !"01".contains(etcWriteRearTires) && !barrier)
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			else if(validateSpaces(etcWriteRearTires))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("X");
			}
			
			if((etcIndVehClass.equals("**")) || etcDerivedVehClass.equals("  ") || ((etcIndVehClass!=null && etcDerivedVehClass!=null) && (etcIndVehClass.equals("**") && (etcDerivedVehClass.equals("33") || etcDerivedVehClass.equals("37") || etcDerivedVehClass.equals("73") || etcDerivedVehClass.equals("77")))))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("C");
			}
			if(etcIndVehClass!=null && plaza!=null && (masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcIndVehClass)==null) 
					&& ((!etcIndVehClass.equals("00") || !etcIndVehClass.equals("99")) && derivedClassStatus==0) )
			{	
				obj.setTrxType("R");	
				obj.setTrxSubtype("C");
			}
			if(etcIndVehClass.equals("**") && etcDerivedVehClass.equals("2L"))
			{
				obj.setTrxType("E");	
				obj.setTrxSubtype("Z");
			}
		}
		
		if (etcViolObserved!=null && !"01".contains(etcViolObserved))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
		}
		if (etcImageCaptured!=null && !"01".contains(etcImageCaptured))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
		}
		
		if (Convertor.toInteger(etcRevenueType)==null || !(Convertor.toInteger(etcRevenueType)>=0 && Convertor.toInteger(etcRevenueType)<=5))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcRevenueType = "0";
		}
		else if(validateSpaces(etcRevenueType))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcRevenueType="0";
		}

		if ((etcValidationStatus!=null && etcValidationStatus.equals("5")) && ((etcAgencyId!=null && !etcAgencyId.equals("***") && etcTagSerialNum!=null && !etcTagSerialNum.equals("********")) || (etcDerivedVehClass!=null && !etcDerivedVehClass.equals("**"))  ||  (etcRevenueType!=null && !etcRevenueType.equals("**")) || (etcReadAgencyData!=null && !etcReadAgencyData.equals("***********")) || (etcWritAgencyData!=null && !etcWritAgencyData.equals("********")) || (etcReadVehType!=null && !etcReadVehType.equals("**"))  || (etcReadVehAxles!=null && !etcReadVehAxles.equals("**")) || (etcViolObserved!=null && !etcViolObserved.equals("1"))))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("T");
		}
		if (etcIndVehAxles!=null && !"0123456789".contains(etcIndVehAxles))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");

		}
		if (etcIndVehAxles!=null && validateSpacesAndStar(etcIndVehAxles))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");

		}

		if (etcIndAxleOffset!=null)
		{
			if (Convertor.toInteger(etcIndAxleOffset)==null)	
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("N");
			}
			else if(!(Convertor.toInteger(etcIndAxleOffset)>=-2 && Convertor.toInteger(etcIndAxleOffset) <=4))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("N");
			}
			else if(etcIndAxleOffset.equals("**") || etcIndAxleOffset.equals("  "))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("N");
			}
			
		}
		else
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("N");
		}
		
		if (Convertor.toInteger(etcVehSpeed)==null || !(Convertor.toInteger(etcVehSpeed)>=0 && Convertor.toInteger(etcVehSpeed)<=99))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("X");
			etcVehSpeed = "0";
		}

		if (etcBufferedFeed!=null && !"BR".contains(etcBufferedFeed))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("B");
		}

		if ( exit || complete || barrier )
		{
			if (DateUtils.dateValidation(etcExitDate, Constants.DATE_YYYYMMDD).equals(Boolean.FALSE))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("D");
			}
			if (DateUtils.timeValidation(etcExitTime,"HHMMSSTT").equals(Boolean.FALSE)) 
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("D");
			}
		}

		Date entryDate=DateUtils.parseDate(etcEntryDate, "yyyyMMdd");
		Date headerDate=DateUtils.parseDate(headerInfoVO.getFileDate(), "yyyyMMdd");
		if (entry && (entryDate!=null && headerDate!=null && entryDate.after(headerDate)))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("E");
		}


		if (complete && etcEntryDataSource!=null && !etcEntryDataSource.equals("T"))
		{
			Date exitDate=DateUtils.parseDate(etcExitDate, Constants.NYSTA_DATE_FORMAT);

			if (entryDate!=null && exitDate!=null && entryDate.after(exitDate))
			{
				obj.setTrxType("R");
				obj.setTrxSubtype("E"); 
			}

		}

		if (etcValidationStatus!=null && !etcValidationStatus.equals("5"))
		{
			if ((etcAppId!=null && !etcAppId.equals("1")))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("T");
			}
			if (etcGroupId!=null && !etcGroupId.equals("065"))
			{
				obj.setTrxType("R");	
				obj.setTrxSubtype("G");
			}
			else
			{
				if(!masterDataCache.isValidAgency(etcAgencyId) && etcViolObserved.equals("0"))
				{
					//etcViolObserved = "1";
					obj.setTrxType("R");	
					obj.setTrxSubtype("T");  // Changed by ronnie for bug 8089
				}	
			}
		}
		
		if (etcValidationStatus!=null && !etcValidationStatus.equals("5"))
		{
//			if (etcAgencyId!=null && etcTagSerialNum!=null && (validate(etcAgencyId) || validate(etcTagSerialNum)))
//			{
//				//etcViolObserved = "1";
//			}
//			else
//			{
				if(!masterDataCache.isValidAgency(etcAgencyId) && etcViolObserved.equals("0"))
				{
					//etcViolObserved = "1";
					obj.setTrxType("R");	
					obj.setTrxSubtype("T");	// Changed by ronnie for bug 8089
					//obj.setDeviceNumber(null);
				}	
	
				if (etcTagSerialNum.equals("**********") && etcViolObserved.equals("0"))
				{
					obj.setTrxType("R");	
					obj.setTrxSubtype("T");
					obj.setDeviceNumber(null);
				}
				if (etcTagSerialNum.equals("0000000000") || etcTagSerialNum.equals("          ") && etcViolObserved.equals("0"))
				{
					obj.setTrxType("R");	
					obj.setTrxSubtype("T");
					obj.setDeviceNumber(null);
				}
			//}
		}
		
		//told by PB for UAT Bug 69909
		if(entry && (validate(etcExitPlazaId)|| validate(etcExitLaneId)))
		{
			obj.setExternPlazaId(etcEntryPlazaId);
			obj.setExternLaneId(etcEntryLaneId);
		}

		String aetFlag=getAetFlag(obj,masterDataCache,agencyIdNystaOrNysba);
		obj.setAetFlag(aetFlag);
		if(etcViolObserved!=null && etcViolObserved.equals("1"))
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			
			if(aetFlag !=null && aetFlag.equals("Y")) {
				obj.setTollRevenueType(60);
			}else {
				obj.setTollRevenueType(9);
			}
		}
		 
		if (!Convertor.isAlphaNumeric(etcTxnSrlNum))
		{
			obj.setTrxType("R");	
			obj.setTrxSubtype("N");
		}

		if ( barrier)  //B or P 
		{
			plaza=masterDataCache.getPlaza(etcExitPlazaId,agencyIdNystaOrNysba);
			if (plaza==null)
			{
				logger.info("Inserted in RL condition exit plaza info for barrier is null");
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			else if (etcExitLaneId!=null && etcExitLaneId.equals("***") && etcExitLaneId.equals("   "))
			{
				TLaneDto lane=masterDataCache.getLaneFromPlaza(plaza.getPlazaId());
				obj.setEntryPlaza(plaza.getPlazaId());
				obj.setEntryLaneId(lane!=null?lane.getLaneId():null);
			}
			else if(plaza!=null) {
				TLaneDto lane=masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId());
				if(lane == null) {
					logger.info("Inserted in RL condition exit lane info for barrier is null");
					obj.setTrxType("R");	
					obj.setTrxSubtype("L");
				}
			}
			
		}
		else if (entry)	// E 
		{
			plaza=masterDataCache.getPlaza(etcEntryPlazaId,agencyIdNystaOrNysba);
			if (plaza==null)
			{
				logger.info("Inserted in RL condition entry plaza info for entry is null");
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			else if (etcEntryLaneId!=null && (etcEntryLaneId.equals("***") || etcEntryLaneId.equals("   ")))
			{
				TLaneDto lane=masterDataCache.getLaneFromPlaza(plaza.getPlazaId());
				obj.setEntryPlaza(plaza.getPlazaId());
				obj.setEntryLaneId(lane.getLaneId());
				obj.setExitPlaza(plaza.getPlazaId());
				obj.setExitLane(lane.getLaneId());
			}
			else if(plaza!=null) {
				TLaneDto lane=masterDataCache.getLane(etcEntryLaneId,plaza.getPlazaId());
				if(lane == null) {
					logger.info("Inserted in RL condition entry lane info for entry is null");
					obj.setTrxType("R");	
					obj.setTrxSubtype("L");
				}
			}
		}
		else if (complete) // C or T
		{
			plaza=masterDataCache.getPlaza(etcEntryPlazaId,agencyIdNystaOrNysba);
			if (plaza==null)
			{
				logger.info("Inserted in RL condition entry plaza info for complete is null");
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			else if(plaza!=null) {
				TLaneDto lane=masterDataCache.getLane(etcEntryLaneId,plaza.getPlazaId());
				if(lane == null) {
					logger.info("Inserted in RL condition entry lane info for entry is null");
					obj.setTrxType("R");	
					obj.setTrxSubtype("L");
				}
			}
			plaza=masterDataCache.getPlaza(etcExitPlazaId,agencyIdNystaOrNysba);
			if (plaza==null)
			{
				logger.info("Inserted in RL condition exit plaza info for complete is null");
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			else if (etcExitLaneId!=null && (etcExitLaneId.equals("***") || etcExitLaneId.equals("   ")))
			{
				TLaneDto laneDto = masterDataCache.getLaneFromPlaza(plaza.getPlazaId());
				obj.setEntryPlaza(plaza.getPlazaId());
				obj.setEntryLaneId(laneDto.getLaneId());
			}
			else if(plaza!=null) {
				TLaneDto lane=masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId());
				if(lane == null) {
					logger.info("Inserted in RL condition exit lane info for complete is null");
					obj.setTrxType("R");	
					obj.setTrxSubtype("L");
				}
			}
			
			
		}
		else if (exit) // X
		{
			plaza=masterDataCache.getPlaza(etcExitPlazaId,agencyIdNystaOrNysba);
			if (plaza==null)
			{
				logger.info("Inserted in RL condition exit plaza info for exit is null");
				obj.setTrxType("R");	
				obj.setTrxSubtype("L");
			}
			else if (etcExitLaneId!=null && (etcExitLaneId.equals("***") || etcExitLaneId.equals("   ")))
			{
				TLaneDto laneDto = masterDataCache.getLaneFromPlaza(plaza.getPlazaId());
				obj.setEntryPlaza(plaza.getPlazaId());
				obj.setEntryLaneId(laneDto.getLaneId());
			}
			else if(plaza!=null) {
				TLaneDto lane=masterDataCache.getLane(etcExitLaneId,plaza.getPlazaId());
				if(lane == null) {
					logger.info("Inserted in RL condition exit lane info for exit is null");
					obj.setTrxType("R");	
					obj.setTrxSubtype("L");
				}
			}
			
			
		}

		
		obj.setFrontOcrPlateCountry(frontOcrPlateCountry);
		obj.setFrontOcrPlateState(frontOcrPlateState);
		obj.setFrontOcrPlateType(frontOcrPlateType);
		obj.setFrontOcrPlateNumber(frontOcrPlateNumber);
		obj.setFrontOcrConfidence(frontOcrConfidence);
		obj.setFrontOcrImageIndex(frontOcrImageIndex);
		obj.setFrontImageColor(frontImageColor);

		obj.setRearOcrPlateCountry(rearOcrPlateCountry);
		obj.setRearOcrPlateState(rearOcrPlateState);
		obj.setRearOcrPlateType(rearOcrPlateType);
		obj.setRearOcrPlateNumber(rearOcrPlateNumber);
		obj.setRearOcrConfidence(rearOcrConfidence);
		obj.setRearOcrImageIndex(rearOcrImageIndex);
		obj.setRearImageColor(rearImageColor);
		obj.setPlateCountry("***");
		obj.setPlateCountry("***");
		obj.setPlateState("**");
		obj.setPlateType(0);
		obj.setPlateNumber("**********");
		if(validateSpacesAndStar(etcViolObserved))
		{
			obj.setEtcViolObserved(null);
		}
		else
		{
			obj.setEtcViolObserved(etcViolObserved);
		}

		if (obj.getTrxType()!=null && !obj.getTrxType().equals("R") && !exit)
		{
			if (etcViolObserved!=null && etcViolObserved.equals("1"))
			{
				obj.setTrxType("V");	
				obj.setIsViolation(1);
			}
			else
			{
				obj.setTrxType("E");
				obj.setTrxSubtype("Z");
				obj.setTollRevenueType(1);
				//set actual class? yes based on ind_veh _class
				if(etcDerivedVehClass.equals("**") && !validate(etcIndVehClass))
				{
					VehicleClass vehicleClass = masterDataCache.getAgencyIdandExternalAgencyClass(plaza!=null?plaza.getAgencyId():null,etcIndVehClass);
					if(vehicleClass!=null)
					{
						obj.setActualClass(vehicleClass.getAgencyClass());
						obj.setAvcClass(obj.getActualClass());
						obj.setAvcAxles(vehicleClass.getBaseAxleCount());
					}
				}
				
			}
		}
		
		else if (obj.getTrxType()!=null && (obj.getTrxType().equals("R") && obj.getTrxSubtype().equals("C") && etcViolObserved.equals("1") && etcImageCaptured.equals("1")))
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			obj.setIsViolation(1);
			obj.setActualClass(0);
		}
		obj.setExtFileId(xferControl.getXferControlId());
		obj.setReceivedDate(headerInfoVO.getFileDate());
		
		if(etcClassMismatch!= null && !etcClassMismatch.equals("*") && !etcClassMismatch.equals("0"))
		{
			obj.setEtcClassMismatch(etcClassMismatch);
		}
		else if (etcClassMismatch==null)
		{
			obj.setEtcClassMismatch(null);
		}
		else
		{
			obj.setEtcClassMismatch("N");
		}
		
		//////////////////////////////////////////    unmatched transaction  //////////////////////////////////////////////
		
		if(isUnmatched == true)
		{
			obj.setTrxType(etcTrxTypeNew);
			obj.setTrxSubtype(etcTrxTypeSubType);
		}
		obj.setMatchedTxExternRefNo(null);
		
		return obj;
	}

	
	private String getAetFlag(Transaction obj, MasterDataCache masterDataCache, Integer agencyIdNystaOrNysba) {

		String aetFlag=null;
		TLaneDto lane = null;
		
		Plaza plaza = masterDataCache.getPlaza(obj.getExternPlazaId(),agencyIdNystaOrNysba);
		if(plaza != null)
		{
			lane = masterDataCache.getLane(obj.getExternLaneId(),plaza.getPlazaId());
			
			if(lane!=null && lane.getLprEnabled()!=null) 
			{
				if(lane.getLprEnabled().equals("Y")) {
					aetFlag="Y";
				}else {
					aetFlag="N";
				}
			}
			else if(plaza!=null && plaza.getLprEnabled()!=null) 
			{
				if(plaza.getLprEnabled().equals("Y")) {
					aetFlag="Y";
				}else {
					aetFlag="N";
				}
			}
			else
			{
				aetFlag="Y";
			}
			
			return aetFlag;
		}
		else
		{
			logger.info("Plaza is null for extern plaza id : {}",obj.getExternPlazaId());
		}
		return aetFlag;	
	}
	
	public boolean isUnmatched() {
		return isUnmatched;
	}
	public void setUnmatched(boolean isUnmatched) {
		this.isUnmatched = isUnmatched;
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
	
	private boolean validateSpaces(String value) {
		String matcher_String = "\\s{"+value.length()+"}|[ ]{"+value.length()+"}";
		if(value.matches(matcher_String)) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean validateSpacesAndStar(String value) {
		String matcher_String = "\\s{"+value.length()+"}|[*| ]{"+value.length()+"}";
		if(value.matches(matcher_String)) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "NystaAetTxDto [isUnmatched=" + isUnmatched + ", etcTrxTypeNew=" + etcTrxTypeNew + ", recordTypeCode="
				+ recordTypeCode + ", etcTrxType=" + etcTrxType + ", etcTrxTypeSubType=" + etcTrxTypeSubType
				+ ", etcEntryDate=" + etcEntryDate + ", etcEntryTime=" + etcEntryTime + ", etcEntryPlazaId="
				+ etcEntryPlazaId + ", etcEntryLaneId=" + etcEntryLaneId + ", etcEntryDataSource=" + etcEntryDataSource
				+ ", etcAppId=" + etcAppId + ", etcType=" + etcType + ", etcGroupId=" + etcGroupId + ", etcAgencyId="
				+ etcAgencyId + ", etcTagSerialNum=" + etcTagSerialNum + ", etcSerialNumber=" + etcSerialNumber
				+ ", etcReadPerformance=" + etcReadPerformance + ", etcLaneMode=" + etcLaneMode + ", etcCollectorId="
				+ etcCollectorId + ", etcDerivedVehClass=" + etcDerivedVehClass + ", etcReadVehType=" + etcReadVehType
				+ ", etcReadVehAxles=" + etcReadVehAxles + ", etcReadVehWeight=" + etcReadVehWeight
				+ ", etcReadRearTires=" + etcReadRearTires + ", etcWriteVehType=" + etcWriteVehType
				+ ", etcWriteVehAxles=" + etcWriteVehAxles + ", etcWriteVehWeight=" + etcWriteVehWeight
				+ ", etcWriteRearTires=" + etcWriteRearTires + ", etcIndVehClass=" + etcIndVehClass
				+ ", etcValidationStatus=" + etcValidationStatus + ", etcViolObserved=" + etcViolObserved
				+ ", etcImageCaptured=" + etcImageCaptured + ", etcRevenueType=" + etcRevenueType
				+ ", etcReadAgencyData=" + etcReadAgencyData + ", etcWritAgencyData=" + etcWritAgencyData
				+ ", etcPreWritTrxNum=" + etcPreWritTrxNum + ", etcPostWritTrxNum=" + etcPostWritTrxNum
				+ ", etcIndVehAxles=" + etcIndVehAxles + ", etcIndAxleOffset=" + etcIndAxleOffset + ", etcVehSpeed="
				+ etcVehSpeed + ", etcExitDate=" + etcExitDate + ", etcExitTime=" + etcExitTime + ", etcExitPlazaId="
				+ etcExitPlazaId + ", etcExitLaneId=" + etcExitLaneId + ", etcBufferedFeed=" + etcBufferedFeed
				+ ", etcTxnSrlNum=" + etcTxnSrlNum + ", frontOcrPlateCountry=" + frontOcrPlateCountry
				+ ", frontOcrPlateState=" + frontOcrPlateState + ", frontOcrPlateType=" + frontOcrPlateType
				+ ", frontOcrPlateNumber=" + frontOcrPlateNumber + ", frontOcrConfidence=" + frontOcrConfidence
				+ ", frontOcrImageIndex=" + frontOcrImageIndex + ", frontImageColor=" + frontImageColor
				+ ", rearOcrPlateCountry=" + rearOcrPlateCountry + ", rearOcrPlateState=" + rearOcrPlateState
				+ ", rearOcrPlateType=" + rearOcrPlateType + ", rearOcrPlateNumber=" + rearOcrPlateNumber
				+ ", rearOcrConfidence=" + rearOcrConfidence + ", rearOcrImageIndex=" + rearOcrImageIndex
				+ ", rearImageColor=" + rearImageColor + ", etcRecordTerm=" + etcRecordTerm + ", tollSystemType="
				+ tollSystemType + ", txExternRefNoMatched=" + txExternRefNoMatched + ", etcClassMismatch="
				+ etcClassMismatch + "]";
	}
	
	
}
