package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.VehicleClass;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.PaFixlengthParser;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

public class PaDetailInfoVO implements Serializable {

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

	public Transaction getTransaction(MasterDataCache masterDataCache, XferControl xferControl, PaHeaderInfoVO headerVO) {
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
			
		} else if (agTrxType.equals("V")) 
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			obj.setTollRevenueType(9);
			
		} else if (agTrxType.equals("I")) 
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
			obj.setTollRevenueType(60);
			
		} else 
		{
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		}
		
		if (agTrxType.equals("E") && !isDeviceValid(agTagAgency + agTagSerialNumber)) 
		 {
			obj.setTrxType("R");
			obj.setTrxSubtype("G");
				
		 }
		
		obj.setTollSystemType("B");

		if (agTrxType != null && agTrxType.contentEquals("V")) {
			obj.setTollRevenueType(QATPConstants.Toll_REVENUE_TOLL_EVADER);
		} else if (agTrxType.equals("I")) {
			obj.setTollRevenueType(QATPConstants.Toll_REVENUE_60);
		} else {
			obj.setTollRevenueType(QATPConstants.Toll_REVENUE_ETC);

		}
		obj.setEntryDate("********");
		obj.setEntryTime("******");
		obj.setEntryLaneSN((long) 0);
		obj.setEntryDataSource("*");
		obj.setEntryVehicleSpeed(0);
		obj.setDeviceAgencyId(agTagAgency);

		if ("***********".contains(agTagAgency+agTagSerialNumber) || "           ".contains(agTagAgency+agTagSerialNumber)
				|| "00000000000".contains(agTagAgency+agTagSerialNumber)) {

			obj.setDeviceNumber(agTagAgency+agTagSerialNumber); //bus resolved..added tag agency also
			//obj.setDeviceNumber(null);   //Uat issue solved
		} else {
			if (agencyInfoVO != null) {
				String dnumber = ((agencyInfoVO.getDevicePrefix())
						+ (agTagSerialNumber != null ? agTagSerialNumber : null));
				obj.setDeviceNumber(dnumber);
			} else {
				obj.setDeviceNumber(agTagSerialNumber);
			}

		}

		if (agLaneMode != null && !"EMVI".contains(agLaneMode)) {
			obj.setTrxType("R");
			obj.setTrxSubtype("M");
		}
		if (agLaneMode != null && "EV".contains(agLaneMode)) {
			obj.setLaneMode("1");
		} else if (agLaneMode != null && "MI".contains(agLaneMode)) {
			obj.setLaneMode("7");
		} else {
			obj.setLaneMode("0");

		}

		obj.setLaneState("0");
		obj.setCollectorNumber((long) 0);
		/*
		 * VehicleClass vehicleClass = masterDataCache.findVehicleClass( agencyInfoVO !=
		 * null ? agencyInfoVO.getAgencyId() : null, agClassCharged != null ?
		 * agClassCharged.trim() : null);
		 */

		VehicleClass vehicleClass = masterDataCache.getAgencyIdandExternalAgencyClass(plaza != null ? plaza.getAgencyId() : null,
				agClassCharged != null ? agClassCharged.trim() : null);
		if (vehicleClass == null) 
		{
			logger.info("Vehicle Class {}",vehicleClass);
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

		
		if (!(agTagClass.trim().matches("[1-9|***]|11")) && agTagClass != null && !agTagClass.equals("***")) {
			logger.info("agTagClass {}",agTagClass);
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
		

		if (agTagPgmStatus != null && "SUF*".contains(agTagPgmStatus)) {
			obj.setProgStat(agTagPgmStatus);
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

		if (agTrxDate != null && !validation.dateValidation(agTrxDate, "YYYYMMDD")) {
			obj.setTrxType("R");
			obj.setTrxSubtype("D");
		}
//		} else {
//			obj.setTrxDate(agTrxDate);
//
//		}

		obj.setTrxDate(agTrxDate);

		if (agTrxTime != null && !validation.timeValidation(agTrxTime, "HHMMSS")) {
			obj.setTrxType("R");
			obj.setTrxSubtype("D");
		}
//		} else {
//			obj.setTrxTime(agTrxTime);
//
//		}

		obj.setTrxTime(agTrxTime);

		String temp2 = agTrxDate + agTrxTime;

		obj.setTxTimeStamp(temp2);

		/*
		 * TLaneDto laneDto = masterDataCache.getLane(agTrxLane != null ?
		 * agTrxLane.replaceFirst("0", "").trim() : null, plaza != null ?
		 * plaza.getPlazaId() : null);
		 */

		logger.debug("Checking for lane id present in master.T_LANE Table");
		int laneDto = masterDataCache.getLaneId(agTrxLane != null ? agTrxLane : null,
				plaza != null ? plaza.getPlazaId() : null);

		if (laneDto == 0) {
			obj.setTrxType("R");
			obj.setTrxSubtype("L");
		} else {
			obj.setTrxLaneId(laneDto);
		}
		
		Tplaza tplaza = masterDataCache.getPlaza(agTrxPlaza != null ? agTrxPlaza : null,
				Convertor.toInteger(QATPConstants.PA_AGENCY_ID));
		if (tplaza == null) {
			obj.setTrxType("R");
			obj.setTrxSubtype("L");
		} else {
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

		if (agTrxSerialNum.matches("[a-zA-Z]+")) {
			obj.setTrxType("R");
			obj.setTrxSubtype("X");
		} else {
			obj.setExtHostRefNum(Convertor.toLong(agTrxSerialNum));
		}

		obj.setReceivedDate(obj.getTrxDate());

		if ("********".contains(obj.getDeviceNumber()) || "00000000".contains(obj.getDeviceNumber())
				|| "        ".contains(obj.getDeviceNumber())) {
			obj.setLicensePlate(agLicNumber.replaceAll("[//*]", " "));
			obj.setLicenseState(agLicState.replaceAll("[//*]", " "));
			obj.setLicPlateType(agLicType.replaceAll("[//*]", " "));
			obj.setConfidenceLevel(agConfidence);
			obj.setDeviceAgencyId("000");
			//obj.setDeviceNumber(null);
		} else {
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
		obj.setDiscountedFareOne(Convertor.toDouble(agEzpassAmountDs1));
		obj.setDiscountedFareTwo(Convertor.toDouble(agEzpassAmountDs2));
		obj.setVideoFareAmount(Convertor.toDouble(agVideoAmount));
		obj.setTollAmount(obj.getVideoFareAmount());
		obj.setFileNum(Convertor.toInteger(headerVO.getAtrnFileNum()));

		if (obj.getTrxType().equals("E")) 
		{
			if (!"NR".contains(agPlanCharged.trim())) 
			{
				if ("SC".contains(agPlanCharged.trim()) || "CP".contains(agPlanCharged.trim())) 
				{
					obj.setLaneMode("8");
				}
//				else
//				{
					if ((obj.getFullFareAmt().equals("0") && obj.getDiscountedFareOne().equals("0"))
							&& obj.getDiscountedFareTwo().equals("0") && obj.getVideoFareAmount().equals("0")) 
					{
						//obj.setPlanCharged("888");
						obj.setPlanCharged("0");
						obj.setVideoFareAmount((double) 0);
						obj.setDiscountedFareOne((double) 0);
						obj.setDiscountedFareTwo((double) 0);
						obj.setExpectedReveneueAmount((double) 0);
						obj.setPostedFareAmount((double) 0);
						obj.setTollAmount((double) 0);
					}

					List<Double> tempList = Arrays.asList(obj.getFullFareAmt(),obj.getDiscountedFareOne(),obj.getDiscountedFareTwo(), obj.getVideoFareAmount());
					//System.out.println("List : "+tempList);
					Optional<Double> value = tempList.stream().filter(m -> m!=null && m.doubleValue() > 0).min(Comparator.comparing(Double::valueOf));
					
						if(value.get().equals(obj.getDiscountedFareTwo()))
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
								//obj.setPlanCharged("888");
								obj.setPlanCharged("0");
							}
						}
						else if(value.get().equals(obj.getDiscountedFareOne()))
						{
							obj.setPlanCharged("102");
						}
						else
						{
							//obj.setPlanCharged("888");
							obj.setPlanCharged("0");
							//TODO: E txns should not charged as video_fare_amount
						}
					//}
					obj.setPostedFareAmount(value.get());
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
		if (obj.getDeviceNumber() != null) {
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
	
	private boolean isDeviceValid(String agTagSerialNumber) 
	{
		if(agTagSerialNumber.equals("***********") || agTagSerialNumber.equals("           ") || agTagSerialNumber.equals("00000000000"))
		{
			return false;
		}
		return true;
	}

	private String getAetFlag(Transaction obj, MasterDataCache masterDataCache) {

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

		if (lane != null && lane.getLprEnabled() != null) {
			if (lane.getLprEnabled().equals("Y")) {
				aetFlag = "Y";
			} else {
				aetFlag = "N";
			}
		} else if (plaza != null && plaza.getLprEnabled() != null) {
			if (plaza.getLprEnabled().equals("Y")) {
				aetFlag = "Y";
			} else {
				aetFlag = "N";
			}

		} else { 
			aetFlag = "N";
		}
		return aetFlag;
	}
}
