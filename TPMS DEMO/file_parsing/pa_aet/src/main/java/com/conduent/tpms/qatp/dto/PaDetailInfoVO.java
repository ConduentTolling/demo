package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.util.ArrayList;
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
public class PaDetailInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PaDetailInfoVO.class);
		
	private String agTrxSerialNum;
	private String agRevenuDate;
	private String agFacAgency;
	private String agTrxType;
	private String agTrxDate;
	private String agTrxTime;
	private String agTrxPlaza;
	private String agTrxLane;
	private String agTrxLaneSeq;
	private String agTagAgency;
	private String agTagSerialNumber;
	private String agReadPerformance;
	private String agWritePerf;
	private String agReadType;
	private String agTagPgmStatus;
	private String agLaneMode;
	private String agValidationStatus;
	private String agLicState;
	private String agLicNumber;
	private String agLicType;
	private String agConfidence;
	private String agTagClass;
	private String agTagExtraAxles;
	private String agAvcClass;
	private String agAvcExtraAxles;
	private String agMismatch;
	private String agClassCharged;
	private String agChargedExtraAxles;
	private String agPlanCharged;
	private String agExitSpeed;
	private String agOverSpeed;
	private String agEzpassAmount;
	private String agEzpassAmountDs1;
	private String agEzpassAmountDs2;
	private String agEzpassAmountDs3;
	private String agEzpassAmountDs4;
	private String agVideoAmount;
	private String delimiter;

	public String getAgTrxSerialNum() {
		return agTrxSerialNum;
	}

	public void setAgTrxSerialNum(String agTrxSerialNum) {
		this.agTrxSerialNum = agTrxSerialNum;
	}

	public String getAgRevenuDate() {
		return agRevenuDate;
	}

	public void setAgRevenuDate(String agRevenuDate) {
		this.agRevenuDate = agRevenuDate;
	}

	public String getAgFacAgency() {
		return agFacAgency;
	}

	public void setAgFacAgency(String agFacAgency) {
		this.agFacAgency = agFacAgency;
	}

	public String getAgTrxType() {
		return agTrxType;
	}

	public void setAgTrxType(String agTrxType) {
		this.agTrxType = agTrxType;
	}

	public String getAgTrxDate() {
		return agTrxDate;
	}

	public void setAgTrxDate(String agTrxDate) {
		this.agTrxDate = agTrxDate;
	}

	public String getAgTrxTime() {
		return agTrxTime;
	}

	public void setAgTrxTime(String agTrxTime) {
		this.agTrxTime = agTrxTime;
	}

	public String getAgTrxPlaza() {
		return agTrxPlaza;
	}

	public void setAgTrxPlaza(String agTrxPlaza) {
		this.agTrxPlaza = agTrxPlaza;
	}

	public String getAgTrxLane() {
		return agTrxLane;
	}

	public void setAgTrxLane(String agTrxLane) {
		this.agTrxLane = agTrxLane;
	}

	public String getAgTrxLaneSeq() {
		return agTrxLaneSeq;
	}

	public void setAgTrxLaneSeq(String agTrxLaneSeq) {
		this.agTrxLaneSeq = agTrxLaneSeq;
	}

	public String getAgTagAgency() {
		return agTagAgency;
	}

	public void setAgTagAgency(String agTagAgency) {
		this.agTagAgency = agTagAgency;
	}

	public String getAgTagSerialNumber() {
		return agTagSerialNumber;
	}

	public void setAgTagSerialNumber(String agTagSerialNumber) {
		this.agTagSerialNumber = agTagSerialNumber;
	}

	public String getAgReadPerformance() {
		return agReadPerformance;
	}

	public void setAgReadPerformance(String agReadPerformance) {
		this.agReadPerformance = agReadPerformance;
	}

	public String getAgWritePerf() {
		return agWritePerf;
	}

	public void setAgWritePerf(String agWritePerf) {
		this.agWritePerf = agWritePerf;
	}

	public String getAgReadType() {
		return agReadType;
	}

	public void setAgReadType(String agReadType) {
		this.agReadType = agReadType;
	}

	public String getAgTagPgmStatus() {
		return agTagPgmStatus;
	}

	public void setAgTagPgmStatus(String agTagPgmStatus) {
		this.agTagPgmStatus = agTagPgmStatus;
	}

	public String getAgLaneMode() {
		return agLaneMode;
	}

	public void setAgLaneMode(String agLaneMode) {
		this.agLaneMode = agLaneMode;
	}

	public String getAgValidationStatus() {
		return agValidationStatus;
	}

	public void setAgValidationStatus(String agValidationStatus) {
		this.agValidationStatus = agValidationStatus;
	}

	public String getAgLicState() {
		return agLicState;
	}

	public void setAgLicState(String agLicState) {
		this.agLicState = agLicState;
	}

	public String getAgLicNumber() {
		return agLicNumber;
	}

	public void setAgLicNumber(String agLicNumber) {
		this.agLicNumber = agLicNumber;
	}

	public String getAgLicType() {
		return agLicType;
	}

	public void setAgLicType(String agLicType) {
		this.agLicType = agLicType;
	}

	public String getAgConfidence() {
		return agConfidence;
	}

	public void setAgConfidence(String agConfidence) {
		this.agConfidence = agConfidence;
	}

	public String getAgTagClass() {
		return agTagClass;
	}

	public void setAgTagClass(String agTagClass) {
		this.agTagClass = agTagClass;
	}

	public String getAgTagExtraAxles() {
		return agTagExtraAxles;
	}

	public void setAgTagExtraAxles(String agTagExtraAxles) {
		this.agTagExtraAxles = agTagExtraAxles;
	}

	public String getAgAvcClass() {
		return agAvcClass;
	}

	public void setAgAvcClass(String agAvcClass) {
		this.agAvcClass = agAvcClass;
	}

	public String getAgAvcExtraAxles() {
		return agAvcExtraAxles;
	}

	public void setAgAvcExtraAxles(String agAvcExtraAxles) {
		this.agAvcExtraAxles = agAvcExtraAxles;
	}

	public String getAgMismatch() {
		return agMismatch;
	}

	public void setAgMismatch(String agMismatch) {
		this.agMismatch = agMismatch;
	}

	public String getAgClassCharged() {
		return agClassCharged;
	}

	public void setAgClassCharged(String agClassCharged) {
		this.agClassCharged = agClassCharged;
	}

	public String getAgChargedExtraAxles() {
		return agChargedExtraAxles;
	}

	public void setAgChargedExtraAxles(String agChargedExtraAxles) {
		this.agChargedExtraAxles = agChargedExtraAxles;
	}

	public String getAgPlanCharged() {
		return agPlanCharged;
	}

	public void setAgPlanCharged(String agPlanCharged) {
		this.agPlanCharged = agPlanCharged;
	}

	public String getAgExitSpeed() {
		return agExitSpeed;
	}

	public void setAgExitSpeed(String agExitSpeed) {
		this.agExitSpeed = agExitSpeed;
	}

	public String getAgOverSpeed() {
		return agOverSpeed;
	}

	public void setAgOverSpeed(String agOverSpeed) {
		this.agOverSpeed = agOverSpeed;
	}

	public String getAgEzpassAmount() {
		return agEzpassAmount;
	}

	public void setAgEzpassAmount(String agEzpassAmount) {
		this.agEzpassAmount = agEzpassAmount;
	}

	public String getAgEzpassAmountDs1() {
		return agEzpassAmountDs1;
	}

	public void setAgEzpassAmountDs1(String agEzpassAmountDs1) {
		this.agEzpassAmountDs1 = agEzpassAmountDs1;
	}

	public String getAgEzpassAmountDs2() {
		return agEzpassAmountDs2;
	}

	public void setAgEzpassAmountDs2(String agEzpassAmountDs2) {
		this.agEzpassAmountDs2 = agEzpassAmountDs2;
	}
	
	public String getAgEzpassAmountDs3() {
		return agEzpassAmountDs3;
	}

	public void setAgEzpassAmountDs3(String agEzpassAmountDs3) {
		this.agEzpassAmountDs3 = agEzpassAmountDs3;
	}
	
	public String getAgEzpassAmountDs4() {
		return agEzpassAmountDs4;
	}

	public void setAgEzpassAmountDs4(String agEzpassAmountDs4) {
		this.agEzpassAmountDs4 = agEzpassAmountDs4;
	}

	public String getAgVideoAmount() {
		return agVideoAmount;
	}

	public void setAgVideoAmount(String agVideoAmount) {
		this.agVideoAmount = agVideoAmount;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Transaction getTransaction(MasterDataCache masterDataCache, XferControl xferControl, PaHeaderInfoVO headerVO, TQatpMappingDao tQatpMappingDao)
	{
		AgencyInfoVO agencyInfoVO = masterDataCache.getAgency(agTagAgency != null ? agTagAgency : null);
		Tplaza plaza = masterDataCache.getPlaza(agTrxPlaza != null ? agTrxPlaza.trim() : null,
				Convertor.toInteger(QATPConstants.PA_AGENCY_ID));
		Transaction obj = new Transaction();

		GenericValidation validation = new GenericValidation();

		obj.setAgTrxType(agTrxType);
		if (agTrxType.equals("E") && isDeviceValid(agTagAgency + agTagSerialNumber)) 
		{
			obj.setTrxType("E");
			obj.setTrxSubtype("Z");
			obj.setTollRevenueType(1);
			obj.setEtcViolObserved(0);
			
		} else if (agTrxType.equals("V")) 
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			obj.setTollRevenueType(19);
			obj.setEtcViolObserved(1);
			
		} else if (agTrxType.equals("I")) 
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			obj.setTollRevenueType(50);
			obj.setEtcViolObserved(1);
			
		} else 
		{
			logger.info("R/X - Invalid AG_TRX_TYPE : {}", agTrxType);
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		}
		
		if (agTrxType.equals("E") && !isDeviceValid(agTagAgency + agTagSerialNumber)) 
		 {
			logger.info("R/G - Null Device Number with agTrxType E");
			obj.setTrxType("R");
			obj.setTrxSubtype("G");
				
		 }
		
		obj.setTollSystemType("B");

		if (agTrxType != null && agTrxType.contentEquals("V")) {
			obj.setTollRevenueType(QATPConstants.TOLL_REVENUE_TOLL_EVADER); //19
		} else if (agTrxType.equals("I")) {
			obj.setTollRevenueType(QATPConstants.TOLL_REVENUE_VIDEO); //50
		} else {
			obj.setTollRevenueType(QATPConstants.TOLL_REVENUE_ETC); //1

		}
		obj.setEntryDate("********");
		obj.setEntryTime("******");
		obj.setEntryLaneSN((long) 0);
		obj.setEntryDataSource("*");
		obj.setEntryVehicleSpeed(0);
		obj.setDeviceAgencyId(agTagAgency);

		/*if ("**************".contains(agTagAgency+agTagSerialNumber) || "              ".contains(agTagAgency+agTagSerialNumber)
				|| "00000000000000".contains(agTagAgency+agTagSerialNumber)) */
		if(validate(agTagAgency+agTagSerialNumber))
		{
			obj.setDeviceNumber(agTagAgency+agTagSerialNumber); //bus resolved..added tag agency also	
		} 
		else 
		{
			if (agencyInfoVO != null) 
			{
				String dnumber = ((agencyInfoVO.getDevicePrefix()) + (agTagSerialNumber != null ? agTagSerialNumber : null));
				obj.setDeviceNumber(dnumber);
			} 
			else 
			{
				obj.setDeviceNumber(agTagAgency+agTagSerialNumber);
			}

		}

		if (agLaneMode != null && !"V".contains(agLaneMode)) 
		{
			logger.info("R/M - Invalid AG_LANE_MODE : {}", agLaneMode);
			obj.setTrxType("R");
			obj.setTrxSubtype("M");
		}	
		if (agLaneMode != null && "V".contains(agLaneMode)) 
		{
			obj.setLaneMode("1");
		} 
//		else if (agLaneMode != null && "MI".contains(agLaneMode)) 
//		{
//			obj.setLaneMode("7");
//		} 
		else 
		{
			obj.setLaneMode("0");
		}

		obj.setLaneState("0"); //*
		obj.setCollectorNumber((long) 0);
	
		VehicleClass vehicleClass = masterDataCache.getAgencyIdandExternalAgencyClass(plaza != null ? plaza.getAgencyId() : null,
				agClassCharged != null ? agClassCharged.trim() : null);
		if (vehicleClass == null) 
		{
			//logger.info("Vehicle Class {}",vehicleClass);
			logger.info("R/C - Invalid AG_CLASS_CHARGED : {}", agClassCharged);
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
		} 
		else 
		{
			obj.setActualClass(vehicleClass.getAgencyClass());
			obj.setExtraAxles(Convertor.toInteger(agChargedExtraAxles != null ? agChargedExtraAxles : null));
			obj.setActualAxles(vehicleClass.getBaseAxleCount()); 	//changed due to UAT Issue
			
//			if (obj.getExtraAxles() != null) 
//			{
//				Integer dummyAxles = (vehicleClass.getBaseAxleCount() + obj.getExtraAxles());
//				obj.setActualAxles(dummyAxles);
//
//			} 
//			else 
//			{
//				obj.setExtraAxles(0);
//				Integer dummyAxles = (vehicleClass.getBaseAxleCount());
//				obj.setActualAxles(dummyAxles);
//			}

		}

		
		if (!(agTagClass.trim().matches("[1-9|***]|11")) && agTagClass != null && !agTagClass.equals("***")) 
		{
			logger.info("agTagClass {}",agTagClass);
			logger.info("R/C - Invalid AG_TAG_CLASS : {}", agTagClass);
			obj.setTrxType("R");
			obj.setTrxSubtype("C");
		}
		
		obj.setTagClass(Convertor.toLong(agTagClass.trim()));
		obj.setTagAxles(Convertor.toInteger(getAgTagExtraAxles()));
		obj.setTagIAGClass((long) 0);
		obj.setAvcClass(Convertor.toLong(agAvcClass.trim()));
		obj.setAvcAxles(Convertor.toInteger(agAvcExtraAxles));
		obj.setEtcValidation(Convertor.toLong(agValidationStatus));// number
		
		if(agReadPerformance.equals("**") || agReadPerformance.equals("  "))
		{
			obj.setReadPerf(0);
		}
		else
		{
			obj.setReadPerf(Convertor.toInteger(agReadPerformance));
		}
		
		if(agWritePerf.equals("**") || agWritePerf.equals("  "))
		{
			obj.setWritePerf(0);
		}
		else
		{
			obj.setWritePerf(Convertor.toInteger(agWritePerf));
		}
		

		if (agTagPgmStatus != null && "SUF*".contains(agTagPgmStatus)) 
		{
			obj.setProgStat(agTagPgmStatus);
		}
		else
		{
			obj.setProgStat("*");
		}
		
		if (agLaneMode != null && agLaneMode.equals("V")) {
			obj.setImageCaptured("T");
		} else {
			obj.setImageCaptured("F");
		}
		obj.setVehicleSpeed(Convertor.toInteger(agExitSpeed));
		// obj.setSpeedViolation(agOverSpeed);
		if (agOverSpeed.equals("N")) {
			obj.setSpeedViolation(QATPConstants.SPEED_NOT_OVER_THRESHOLD);
		} else if (agOverSpeed.equals("Y")) {
			obj.setSpeedViolation(QATPConstants.SPEED_OVER_THRESHOLD);

		} else if (agOverSpeed.equals("S")) {
			obj.setSpeedViolation(QATPConstants.AUTO_SUSPEND);

		} else {
			obj.setSpeedViolation(QATPConstants.SPEED_NOT_OVER_THRESHOLD);
		}
//one function for all three
	
		if (agTrxDate != null && !validation.dateValidation(agTrxDate, "YYYYMMDD")) 
		{
			logger.info("R/D - Invalid AG_TRX_DATE : {}", agTrxDate);
			obj.setTrxType("R");
			obj.setTrxSubtype("D");
			obj.setTrxDate("18570101");	
		}
		else 
		{
			obj.setTrxDate(agTrxDate); 
		}
	
		if (agTrxTime != null && !validation.timeValidation(agTrxTime, "HHMMSS")) 
		{
			logger.info("R/D - Invalid AG_TRX_TIME : {}", agTrxTime);
			obj.setTrxType("R");
			obj.setTrxSubtype("D");
			obj.setTrxTime("000000");
		}
		else
		{
			obj.setTrxTime(agTrxTime);
		}
		
		String date = validation.dateValidation(agTrxDate,"YYYYMMDD") == true ? agTrxDate : "18570101";
		String time = validation.timeValidation(agTrxTime,"HHMMSS") == true ? agTrxTime : "000000";
		
		String txTimeStamp = date + time;
		obj.setTxTimeStamp(txTimeStamp);

//end
		
		obj.setRevenueDate(agRevenuDate);
		
		
		logger.debug("Checking for lane id present in master.T_LANE Table");
		int laneDto = masterDataCache.getLaneId(agTrxLane != null ? agTrxLane : null,
				plaza != null ? plaza.getPlazaId() : null);

		if (laneDto == 0) {
			logger.info("R/L - Invalid AG_TRX_LANE : {}", agTrxLane);
			obj.setTrxType("R");
			obj.setTrxSubtype("L");
		} else {
			obj.setTrxLaneId(laneDto);
		}
		
		Tplaza tplaza = masterDataCache.getPlaza(agTrxPlaza != null ? agTrxPlaza : null, Convertor.toInteger(QATPConstants.PA_AGENCY_ID));
		if (tplaza == null) 
		{
			logger.info("R/L - Invalid AG_TRX_PLAZA : {}", agTrxPlaza);
			obj.setTrxType("R");
			obj.setTrxSubtype("L");
		} 
		else 
		{
			obj.setExitPlaza(tplaza.getPlazaId());
			obj.setPlazaAgencyId(tplaza.getAgencyId());
			//obj.setExternPlazaId(String.valueOf(tplaza.getPlazaId()));
			obj.setExternPlazaId(String.valueOf(tplaza.getExternPlazaId()));
		}
		//Integer lprEnabled = masterDataCache.isLprEnabled(obj.getExitPlaza() == null ? null : obj.getExitPlaza());
		//setIsLprEnabled not required so that set as null
		//obj.setIsLprEnabled(null);
		obj.setExtFileId(xferControl.getXferControlId());

		obj.setTrxLaneSN(Convertor.toLong(agTrxLaneSeq));

		if (agReadType != null && agReadType.equals("B")) {
			obj.setBufRead("T");
		} else {
			obj.setBufRead("F"); 
		}
				
		
		if (agTrxSerialNum.matches("[a-zA-Z]+") || agTrxSerialNum.equals("            ") || agTrxSerialNum.contains(" ") ) //Bug 12012 ^[a-zA-Z0-9_@./#&+-]*$     
		{		
			logger.info("R/X - Invalid AG_TRX_SERIAL_NUM : {}", agTrxSerialNum);
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		} 
		else 
		{			

			logger.debug("Checking if TX_EXTERN_REF_NO is already used before or not.");
				
			String num = StringUtils.leftPad(String.valueOf(agTrxSerialNum), 20, "0");
				
			//Check in T_TRAN_DETAIL for TxExternRefNum for successfully posted transaction
			Boolean check1 = tQatpMappingDao.checkIfTxExternRefNoIsAlreadyUsed(num);
				
			//Check in T_TRAN_DETAIL for TxExternRefNum for Transaction with same ExtFileId as this file
			//Boolean check2 = tQatpMappingDao.checkIfTxExternRefNoIsAlreadyUsedWithSameExtFileId(num, obj.getExtFileId());
				
			//Check if TX_EXTERN_REF_NO is already used
			if( !check1 ) // !check1
			{		
				obj.setExtHostRefNum(Convertor.toLong(agTrxSerialNum));
			}
			else 
			{
				logger.info("R/X - Invalid AG_TRX_SERIAL_NUM : {}", agTrxSerialNum);
				obj.setTrxType("R");
				obj.setTrxSubtype("X"); // set plancharged 0 here
				obj.setExtHostRefNum(Convertor.toLong(agTrxSerialNum));		
			}			
		}
		
		obj.setReceivedDate(obj.getTrxDate());

		if ( obj.getDeviceNumber() !=null && ("**************".contains(obj.getDeviceNumber()) 
				|| "00000000000000".contains(obj.getDeviceNumber())
				|| "              ".contains(obj.getDeviceNumber()) ) )
		{
//			obj.setLicensePlate(agLicNumber.replaceAll("[//*]", " "));
//			obj.setLicenseState(agLicState.replaceAll("[//*]", " "));
//			obj.setLicPlateType(agLicType.replaceAll("[//*]", " "));
			obj.setLicensePlate(agLicNumber);
			obj.setLicenseState(agLicState);
			obj.setLicPlateType(agLicType);
			obj.setConfidenceLevel(agConfidence);
			obj.setDeviceAgencyId("0000");
		}
		else 
		{
			obj.setLicensePlate("**********");
			obj.setLicenseState("**");
			obj.setLicPlateType("0");
			obj.setConfidenceLevel("0");
		}

		obj.setLaneHealth("****");
		obj.setReaderId("****");
		obj.setCorrection("***");
		obj.setTourSegmentId((long) 0);
		obj.setDstFlag("*");
		obj.setLaneDataSource("F");
		obj.setPlateCountry("***");
		obj.setPlateCountry("***");
		obj.setPlateState("**");
		obj.setPlateType(0);
		obj.setPlateNumber("**********");

		obj.setFullFareAmt(Convertor.toDouble(agEzpassAmount));
		obj.setItolFareAmount(Convertor.toDouble(agEzpassAmount));
		obj.setDiscountedFareOne(Convertor.toDouble(agEzpassAmountDs1));
		obj.setDiscountedFareTwo(Convertor.toDouble(agEzpassAmountDs2));
		obj.setVideoFareAmount(Convertor.toDouble(agVideoAmount));
		obj.setTollAmount(obj.getVideoFareAmount());
		obj.setFileNum(Convertor.toInteger(headerVO.getAtrnFileNum()));

		if (obj.getTrxType().equals("E") || obj.getTrxType().equals("V")) 
		{
			if (!"NR".contains(agPlanCharged.trim())) 
			{
				if ("SC".contains(agPlanCharged.trim()) || "CP".contains(agPlanCharged.trim())) 
				{
					obj.setLaneMode("8");  //CARPOOL
				}

				if ( obj.getFullFareAmt() == 0 && obj.getDiscountedFareOne() == 0
							&& obj.getDiscountedFareTwo() == 0 && obj.getVideoFareAmount() == 0 )
				{
					//obj.setPlanCharged("888");   // Present in PB's logic
					obj.setPlanCharged("0");
					obj.setVideoFareAmount((double) 0);
					obj.setDiscountedFareOne((double) 0);
					obj.setDiscountedFareTwo((double) 0);
					obj.setExpectedReveneueAmount((double) 0);
					obj.setPostedFareAmount((double) 0);
					obj.setTollAmount((double) 0);
				}
				else 
				{		
					List<Double> tempList = Arrays.asList(obj.getFullFareAmt(),obj.getDiscountedFareOne(),obj.getDiscountedFareTwo(), obj.getVideoFareAmount());
					//System.out.println("List : "+tempList);
					Optional<Double> value = tempList.stream().filter(m -> m!=null && m.doubleValue() > 0).min(Comparator.comparing(Double::valueOf));
					
					logger.info("Minimum amount is : {}", value);
					
					if(value.get().equals(obj.getDiscountedFareOne()))
					{
						obj.setPlanCharged("102");
					}					
					else if(value.get().equals(obj.getDiscountedFareTwo()))
					{
						if(obj.getTrxType().equals("E"))
						{
							if ("SC".contains(agPlanCharged.trim()) || "CP".contains(agPlanCharged.trim())) 
							{
								obj.setPlanCharged("108");
							}
							else if("SG".contains(agPlanCharged.trim()) || "GRN".contains(agPlanCharged.trim()))  
							{
								obj.setPlanCharged("54");
							}
							else
							{
								//obj.setPlanCharged("888"); // Present in PB's logic
								obj.setPlanCharged("0");
							}
						}
						else if(obj.getTrxType().equals("V")) 
						{
							obj.setPlanCharged("54");
						}						
					}						 
					else
					{
						//obj.setPlanCharged("888");
						obj.setPlanCharged("0");
					}
					obj.setPostedFareAmount(value.get());
				}
					
			}
			else 
			{
				obj.setTollAmount(obj.getFullFareAmt());
				obj.setPlanCharged("999");
				
			}
		}
		else
		{
			obj.setPlanCharged("1"); 
			obj.setExpectedReveneueAmount(obj.getVideoFareAmount());
			obj.setPostedFareAmount(obj.getVideoFareAmount());
		}

		// obj.setAtrnPlanType(Convertor.toLong("0"));
		if (obj.getDeviceNumber() != null) 
		{
			AgencyInfoVO checkAwayAgency = masterDataCache.checkAwayAgency(obj.getDeviceNumber().substring(0, 3));
			if (checkAwayAgency != null && checkAwayAgency.getIsHomeAgency() != null) {

				obj.setReciprocityTrx(checkAwayAgency.getIsHomeAgency().equals("N") ? "T" : "F");
			} else {
				obj.setReciprocityTrx("F");
			}
		}
		obj.setAtrnPlanType(Convertor.toLong(obj.getPlanCharged().trim()));

		String aetFlag = getAetFlag(obj, masterDataCache);

		obj.setAetFlag(aetFlag);

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

	private boolean isDeviceValid(String agTagSerialNumber) 
	{
		if(agTagSerialNumber.equals("***********") || agTagSerialNumber.equals("           ") || agTagSerialNumber.equals("00000000000"))
		{
			return false;
		}
		return true;
	}

	private String getAetFlag(Transaction obj, MasterDataCache masterDataCache) 
	{
		String aetFlag = null;

		Tplaza plaza = masterDataCache.getPlaza(this.getAgTrxPlaza(),
				Convertor.toInteger(QATPConstants.PA_AGENCY_ID));

		TLaneDto lane = masterDataCache.getLane(this.getAgTrxLane(),
				plaza != null ? plaza.getPlazaId() : null);
		
		if(lane!=null)
		{
			//obj.setExternLaneId(String.valueOf(lane.getLaneId()));
			obj.setExternLaneId(String.valueOf(lane.getExternLaneId()));
		}

		if (lane != null && lane.getLprEnabled() != null) 
		{
			if (lane.getLprEnabled().equals("Y") || lane.getLprEnabled().equals("T")) 
			{
				aetFlag = "Y";
			}
			else 
			{
				aetFlag = "N";
			}
		} 
		else if (plaza != null && plaza.getLprEnabled() != null) 
		{
			if (plaza.getLprEnabled().equals("Y") || plaza.getLprEnabled().equals("T")) 
			{
				aetFlag = "Y";
			} 
			else 
			{
				aetFlag = "N";
			}

		} 
		else 
		{ 
			aetFlag = "N";
		}
		return aetFlag;
	}
}
