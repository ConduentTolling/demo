package com.conduent.tpms.qatp.dto;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.model.QatpBufferRecordType;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

public class MtaDetailInfoVO {
	private static final Logger log = LoggerFactory.getLogger(MtaDetailInfoVO.class);

	private String recordStart;
	private String recordNumber;
	private String tagSeialNum;
	private String agencyID;
	private String tranDetailSerail;
	private String aviClass;
	private String tagClass;
	private String vehicleClass;
	private String avcProfile;
	private String avcAxles;
	private String laneNum;
	private String transitDate;
	private String transitTime;
	private String transactionInfo;
	private String vehicleSpeed;
	private String frontOcrPlateCountry;
	private String frontOcrPlateState;
	private String frontOcrPlateType;
	private String frontOcrPlateNumber;
	private String frontOcrConfidence;
	private String frontOcrImageIndex;
	private String frontImageColor;
	private String rearOcrPlateCountry;
	private String rearOcrPlateState;
	private String rearOcrPlateType;
	private String rearOcrPlateNumber;
	private String rearOcrConfidence;
	private String rearOcrImageIndex;
	private String rearImageColor;
	private String event;
	private String hovFlag;
	private String presenceTime;
	private String lostPresenceTime;
	private String etx;
	private String collectorId;
	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getRecordStart() {
		return recordStart;
	}

	public void setRecordStart(String recordStart) {
		this.recordStart = recordStart;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getTagSeialNum() {
		return tagSeialNum;
	}

	public void setTagSeialNum(String tagSeialNum) {
		this.tagSeialNum = tagSeialNum;
	}

	public String getAgencyID() {
		return agencyID;
	}

	public void setAgencyID(String agencyID) {
		this.agencyID = agencyID;
	}

	public String getTranDetailSerail() {
		return tranDetailSerail;
	}

	public void setTranDetailSerail(String tranDetailSerail) {
		this.tranDetailSerail = tranDetailSerail;
	}

	public String getAviClass() {
		return aviClass;
	}

	public void setAviClass(String aviClass) {
		this.aviClass = aviClass;
	}

	public String getTagClass() {
		return tagClass;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	public String getAvcProfile() {
		return avcProfile;
	}

	public void setAvcProfile(String avcProfile) {
		this.avcProfile = avcProfile;
	}

	public String getAvcAxles() {
		return avcAxles;
	}

	public void setAvcAxles(String avcAxles) {
		this.avcAxles = avcAxles;
	}

	public String getLaneNum() {
		return laneNum;
	}

	public void setLaneNum(String laneNum) {
		this.laneNum = laneNum;
	}

	public String getTransitDate() {
		return transitDate;
	}

	public void setTransitDate(String transitDate) {
		this.transitDate = transitDate;
	}

	public String getTransitTime() {
		return transitTime;
	}

	public void setTransitTime(String transitTime) {
		this.transitTime = transitTime;
	}

	public String getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public String getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(String vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
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

	public String getFrontOcrPlateNum() {
		return frontOcrPlateNumber;
	}

	public void setFrontOcrPlateNum(String frontOcrPlateNum) {
		this.frontOcrPlateNumber = frontOcrPlateNum;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getHovFlag() {
		return hovFlag;
	}

	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}

	public String getPresenceTime() {
		return presenceTime;
	}

	public void setPresenceTime(String presenceTime) {
		this.presenceTime = presenceTime;
	}

	public String getLostPresenceTime() {
		return lostPresenceTime;
	}

	public void setLostPresenceTime(String lostPresenceTime) {
		this.lostPresenceTime = lostPresenceTime;
	}

	public String getEtx() {
		return etx;
	}

	public void setEtx(String etx) {
		this.etx = etx;
	}

	public QatpBufferRecordType getBufferRecordType(MtaHeaderinfoVO headerVO, MtaLaneHeaderInfoVO laneheaderVO,
			XferControl xferControl, MtaFileNameInfo fileNameVO, MasterDataCache masterDataCache) throws UnsupportedEncodingException {
		
		QatpBufferRecordType obj= new QatpBufferRecordType();
		obj.setTrxType("E");
		obj.setTrxSubtype("Z");
		obj.setReciprocityTrx("F");
		obj.setAgencyId(agencyID);
//		obj.setPlazaId(headerVO.getPlazaId().trim());
//		obj.setLaneId(laneheaderVO.getLaneNum().trim());
		obj.setExternPlazaId(headerVO.getPlazaId() != null ? headerVO.getPlazaId().trim() : "");
		obj.setExternLaneId(laneheaderVO.getLaneNum() != null ? laneheaderVO.getLaneNum().trim() : "");
		obj.setLaneConfig(laneheaderVO.getLaneConfig());
		obj.setOnTime(laneheaderVO.getOnTime());
		obj.setCollectorId(laneheaderVO.getVaultId()); 
		obj.setReaderId(laneheaderVO.getReaderid());
		obj.setRecordNumber(recordNumber);
		obj.setTagId(tagSeialNum);
		//obj.setAgencyId(agencyID);
		obj.setLcDeviceno(obj.getLcDeviceno()); 
		obj.setAviClass(aviClass);
		obj.setTagClass(tagClass);
		obj.setVehicleClass(vehicleClass);
		obj.setAvcProfile(avcProfile);
		obj.setAvcAxles(avcAxles);
		obj.setLaneNumber(laneNum);
		obj.setTransitDate(transitDate);
		obj.setTransitTime(transitTime);
		obj.setTransactionInfo(transactionInfo); 
		if (tranDetailSerail.trim().matches("[0-9]+")) {
			obj.setTranDetailSrno(tranDetailSerail);
			} else {
				obj.setTrxType("R");
				obj.setTrxSubtype("X");
			}
		//obj.setTranDetailSrno(tranDetailSerail); 
		obj.setVehicleSpeed(vehicleSpeed);
		obj.setrOcrImageColor(rearImageColor);
		obj.setrOcrImageIndex(rearOcrImageIndex);
		obj.setrOcrConfidence(rearOcrConfidence);
		obj.setrOcrPlateNumber(rearOcrPlateNumber);
		obj.setrOcrPlateType(rearOcrPlateType);
		obj.setrOcrPlateState(rearOcrPlateState);
		obj.setrOcrPlateCountry(rearOcrPlateCountry);
		obj.setfOcrImageColor(frontImageColor);
		obj.setfOcrImageIndex(frontOcrImageIndex);
		obj.setfOcrConfidence(frontOcrConfidence);
		obj.setfOcrPlateNumber(frontOcrPlateNumber);
		obj.setfOcrPlateType(frontOcrPlateType);
		obj.setfOcrPlateCountry(frontOcrPlateCountry);
		obj.setfOcrPlateState(frontOcrPlateState);
		if(xferControl.getXferControlId()!=null) {
		obj.setIcIagclass((long)xferControl.getXferControlId());
		}
		obj.setEvent("/0");
		obj.setEvent(event);
		obj.setHOV("N");
		obj.setPlazaAgencyId(Convertor.toInteger(obj.getAgencyId()));
		obj.setPresenceTime("18581117000000000");
		obj.setLostpresenceTime("18581117000000000");
		
		if(fileNameVO.getFileDate()!=null) {
			obj.setTagFlag(Convertor.toInteger(fileNameVO.getFileDate()));
		}
		if(transitDate!=null && transitTime!=null && !(transitDate.trim().isEmpty()) && !(transitTime.trim().isEmpty())) {
			String temp=transitDate+transitTime;
			obj.setTransitDateTime(temp);
			
		}
		
		String tran_info_first = getTransactionInfo();
		GenericValidation genericValidation = new GenericValidation();
		
		if (getTransitDate() !=null && (getTransitDate().trim().contains("") || getTransitDate().trim().matches("[0-9]+")) && !genericValidation.dateValidation(getTransitDate(), "YYYYMMDD")) {
			obj.setTrxSubtype("D");
			obj.setTrxType("R");
		}
		
		if (getTransitTime() !=null && (getTransitDate().trim().contains("") || getTransitDate().trim().matches("[0-9]+")) && !genericValidation.timeValidation(getTransitTime(), "HHMMSSTT")) {
			obj.setTrxSubtype("D");
			obj.setTrxType("R");
		}
		
	   if(getTranDetailSerail()!=null && !(getTranDetailSerail().matches("\\d{12}")))
	   {
		   obj.setTrxSubtype("N");
			obj.setTrxType("R");
	   }
	   
	   if(getRecordNumber()!=null && !(getRecordNumber().matches("\\d{9}")))
	   {
		   	obj.setTrxSubtype("N");
			obj.setTrxType("R");
	   }
	   
		Tplaza plazaInfo = masterDataCache.isValidPlaza(obj.getExternPlazaId(),
				Convertor.toInteger(QATPConstants.MTA_AGENCY_ID));
		
		if (plazaInfo != null) {
			obj.setPlazaId(plazaInfo.getPlazaId() != null ? plazaInfo.getPlazaId().toString() : null);
			obj.setPlazaAgencyId(plazaInfo.getAgencyId());
		} else {
			obj.setTrxSubtype("L");
			obj.setTrxType("R");
			obj.setPlazaId(null);
			obj.setPlazaAgencyId(null);
			obj.setExternPlazaId(null);
		}
	   
//		TLaneDto laneInfo = null;
//		
//		log.info("Checking lane exists in master.T_LANE Table or not");
//		if (obj.getPlazaId() != null) {
//			//laneInfo = masterDataCache.isValidLane(obj.getExternLaneId(), Convertor.toInteger(obj.getPlazaId()));
//			laneInfo = masterDataCache.isValidLane(obj.getExternLaneId(), Convertor.toInteger(obj.getPlazaId()));
//		}
//
//		if (laneInfo!=null) {
//			obj.setLaneId(laneInfo.getLaneId());
//		} else {
//			obj.setTrxSubtype("L");
//			obj.setTrxType("R");
//			obj.setLaneId(null);
//		}	
		
		int laneInfo = 0;
		
		log.info("Checking lane exists in master.T_LANE Table or not");
		if (obj.getPlazaId() != null) {
			laneInfo = masterDataCache.isValidLane(obj.getExternLaneId(), Convertor.toInteger(obj.getPlazaId()));
		}

		if (laneInfo>0) {
			obj.setLaneId(laneInfo);
		} else {
			obj.setTrxSubtype("L");
			obj.setTrxType("R");
			obj.setLaneId(null);
		}	
	   
	     if ( !(obj.getLaneConfig().matches("[EAM]{1}[0|1]{3}")) && obj.getLaneConfig()!=null) {
	           obj.setTrxSubtype("M");
				obj.setTrxType("R");
	     }
	     
	     if (obj.getLaneConfig().charAt(0) != 'E' && obj.getLaneConfig()!=null)
	     {
	             if(!(obj.getCollectorId().matches("\\d{4}")) && obj.getCollectorId() !=null)
	            obj.setTrxSubtype("N");
	 			obj.setTrxType("R");     
	      }
	     
	     if(!(getTagSeialNum()==null) && !(getTagSeialNum().trim().isEmpty()) && !(getTagSeialNum().contains("******")) && 
	    		 getTagSeialNum().trim().matches("^[0-9a-fA-F]+$")) {
				String tagSerialNumber = convertHexadecimalToDecimal(getTagSeialNum());
	     if ((Convertor.toInteger(tagSerialNumber) <0 || Convertor.toInteger(tagSerialNumber)>99999999)) {
	    	 	obj.setTrxSubtype("T");
	 			obj.setTrxType("R");
	    }
	     obj.setTagId(tagSerialNumber);
	     StringBuilder sbFileName = new StringBuilder();
	     sbFileName.append("0").append(obj.getAgencyId()+StringUtils.leftPad(String.valueOf(tagSerialNumber), 8, "0"));
	     obj.setLcDeviceno(sbFileName.toString());
	     
	   }
	     else {
				StringBuilder sbFileName = new StringBuilder();
				if(getTagSeialNum().contains("      ")) 
				{
				    //sbFileName.append("0").append(obj.getAgencyId()+getTagSeialNum()).append("  ");
				    //obj.setLcDeviceno(sbFileName.toString());
				    obj.setLcDeviceno(null);	//Pb told to add this condition
					obj.setTrxType("V");
				    obj.setTrxSubtype("F");
				}
				else if(getTagSeialNum().contains("******")) 
				{
//					sbFileName.append("0").append(obj.getAgencyId()+getTagSeialNum()).append("**");
//				    obj.setLcDeviceno(sbFileName.toString());
					obj.setLcDeviceno(null);	//Pb told to add this condition
					obj.setTrxType("V");
				    obj.setTrxSubtype("F");
				}
				else if(getTagSeialNum().contains("000000")) 
				{
//					sbFileName.append("0").append(obj.getAgencyId()+getTagSeialNum()).append("**");
//				    obj.setLcDeviceno(sbFileName.toString());
					obj.setLcDeviceno(null);	//Pb told to add this condition
					obj.setTrxType("V");
				    obj.setTrxSubtype("F");
				}
				obj.setTagId(getTagSeialNum()==null?null:getTagSeialNum());
	     }
	    
	     if (obj.getAgencyId()!=null && !(obj.getAgencyId().matches("\\d{2}")))  {
	    	 
				obj.setTrxSubtype("T");
				obj.setTrxType("R");
		}
  
		if (!(getAviClass() == null) && !(getAviClass().trim().isEmpty()) && !(getAviClass().contains("**"))
				&& getAviClass().trim().matches("^[0-9a-fA-F]+$")) {
			String AviClass = convertHexadecimalToDecimal(getAviClass());
			if (getTransactionInfo().charAt(0) == 'Y') {
				if ((Convertor.toInteger(AviClass) < 1 || Convertor.toInteger(AviClass) > 99)
						&& getAviClass() != null) {
					obj.setTrxSubtype("C");
					obj.setTrxType("R");
				}
				obj.setAviClass(AviClass);
			}

		} else {
			obj.setTrxSubtype("C");
			obj.setTrxType("R");
			obj.setAviClass(getAviClass() == null ? null : getAviClass());
		}
	     
		if (!(getTagClass() == null) && !(getTagClass().trim().isEmpty()) && !(getTagClass().contains("**"))
				&& getTagClass().trim().matches("^[0-9a-fA-F]+$")) {
			String tagClass = convertHexadecimalToDecimal(getTagClass());
			if (getTransactionInfo().charAt(0) == 'Y') {
				if ((Convertor.toInteger(tagClass) < 1 || Convertor.toInteger(tagClass) > 99)
						&& getTagClass() != null) {
					obj.setTrxSubtype("C");
					obj.setTrxType("R");
				}
				obj.setTagClass(tagClass);
			}

		} else {
			obj.setTrxSubtype("C");
			obj.setTrxType("R");
			obj.setTagClass(getTagClass() == null ? null : getTagClass());
		}
	    
	     
		if (getVehicleClass().trim().matches("^[0-9a-zA-Z]+$") && getVehicleClass() != null) {
			Integer ExtractVehicleClass = convertCharToAscii(getVehicleClass());
			Integer vehicleClass;
			if (ExtractVehicleClass < 64) {
				vehicleClass = ExtractVehicleClass;
			} else {
				vehicleClass = ExtractVehicleClass - 64;
			}
			if (vehicleClass < 1 || vehicleClass > 21) {
				obj.setTrxSubtype("C");
				obj.setTrxType("R");
			}
			obj.setVehicleClass(vehicleClass.toString());
		} else {

			obj.setTrxSubtype("C");
			obj.setTrxType("R");
			obj.setTagClass(getVehicleClass() == null ? null : getVehicleClass());

		}
         
		if (getAvcProfile().trim().matches("^[0-9]+$") || getAvcProfile().equals("H")
				|| getAvcProfile().equals("O") && getAvcProfile() != null) {
			Integer ExtractAvcProfile = convertCharToAscii(getAvcProfile());
			Integer AvcProfile;
			if (ExtractAvcProfile < 64) {
				AvcProfile = ExtractAvcProfile;
			} else {
				AvcProfile = ExtractAvcProfile - 64;
			}
			if (AvcProfile < 1 || AvcProfile > 21) {
				obj.setTrxSubtype("C");
				obj.setTrxType("R");
			}
			obj.setAvcProfile(AvcProfile.toString());
		} else {

			obj.setTrxSubtype("C");
			obj.setTrxType("R");
			obj.setTagClass(getAvcProfile() == null ? null : getAvcProfile());

		}
      
 		if (getAvcAxles().trim().matches("^[0-9a-zA-Z]+$") && getAvcAxles() != null) {
			Integer ExtractAvcAxles = convertCharToAscii(getAvcAxles());
			Integer avcAxles;
			if (ExtractAvcAxles < 64) {
				avcAxles = ExtractAvcAxles;
			} else {
				avcAxles = ExtractAvcAxles - 64;
			}
			if (avcAxles < 1 || avcAxles > 21) {
				obj.setTrxSubtype("C");
				obj.setTrxType("R");
			}
			obj.setAvcAxles(avcAxles.toString());
		} else {

			obj.setTrxSubtype("C");
			obj.setTrxType("R");
			obj.setTagClass(getAvcAxles() == null ? null : getAvcAxles());

		}

		char[] transactionInfo = tran_info_first.toCharArray();
		if ((transactionInfo[0] == 'Y') && (transactionInfo[4] != 'V')) {
			obj.setTrxSubtype("V");
			obj.setTrxType("R");
		} else if ((transactionInfo[0] == 'N') && ((transactionInfo[4] != 'x') && (transactionInfo[4] != 'X'))) {
			obj.setTrxSubtype("V");
			obj.setTrxType("R");
		} else if (transactionInfo[0] == 'N') {
			obj.setTrxSubtype("V");
			obj.setTrxType("R");
		}

		String aetFlag = getAetFlag(obj, masterDataCache);

		obj.setAetFlag(aetFlag);

		// V & F is set in classification
		// obj.setTrxType("V");
		// obj.setTrxSubtype("F");

		
		/*
		 * if (obj.getLaneId() != null && !(obj.getLaneId().trim().isEmpty()) &&
		 * obj.getLaneId().trim().matches("[0-9]+")) { TLaneDto laneDto =
		 * masterDataCache.findLprEnabled(Convertor.toLong(getLaneNum().trim()));
		 * 
		 * if (laneDto != null) { obj.setIsLprEnabled(laneDto.getLprEnabled()); } }
		 */

		
		return obj;		
	}
	
	private String getAetFlag(QatpBufferRecordType obj, MasterDataCache masterDataCache) {

		String aetFlag = null;
		TLaneDto lane = null;

		Tplaza plaza = masterDataCache.isValidPlaza(obj.getExternPlazaId(),
				Convertor.toInteger(QATPConstants.MTA_AGENCY_ID));

		if (plaza != null) {
			lane = masterDataCache.isValidLaneId(obj.getExternLaneId(), plaza.getPlazaId());
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
		}
		else {
			aetFlag = "N";
		}

		return aetFlag;
	}

	private String convertHexadecimalToDecimal(String value){
		if(value.trim().matches("^[0-9a-fA-F]+$")) {
		return String.valueOf(Integer.parseInt(value.trim(), 16));
		}else {
			return value;
		}
		
	}
	
	public String ConvertDecimalToHexadecimal(String value) throws UnsupportedEncodingException {
		
		byte[] myBytes = value.getBytes("UTF-8");

	    return DatatypeConverter.printHexBinary(myBytes);
	}
	
	private Integer convertCharToAscii(String value) {
		
		if(value.trim().matches("[0-9]+")) {
			if(value.trim().length()>1) {
				String[] str = value.trim().split("");
				int max = 0;
				for (int i = 0; i < str.length; i++)  {
					if (Integer.parseInt(str[i]) > max) {
				         max = Integer.parseInt(str[i]);
				     }
		        }
				return max;
			}
			return Convertor.toInteger(value);
		} else {
			if(value.trim().length()>1) {
				char[] ch = new char[value.trim().length()];
				int max = 0;
				for (int i = 0; i < value.trim().length(); i++) {
		            ch[i] = value.trim().charAt(i);
		            int ascii = (int) ch[i];
		            if (ascii > max)
		                 max = ascii;
		        }
				return max;
		}
		char character = value.charAt(0); 
		int ascii = (int) character;
		return ascii;
		}
	}
}
