package com.conduent.tpms.iag.dto;

public class IagDetailInfoVO {

	private String fileExtension;
	private String version;
	private String fromAgencyId;
	private String toAgencyId;
	private String fileFromAgencyId;
	private String fileToAgencyId;
	private String fileName;
	private String dot;
	private String underscroe;
	private String fileType;
	private String fileDate;
	private String fileTime;
	private String fileDateTime;
	private String returnCode;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFileDateTime() {
		return fileDateTime;
	}
	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}
	public String getFileToAgencyId() {
		return fileToAgencyId;
	}
	public void setFileToAgencyId(String fileToAgencyId) {
		this.fileToAgencyId = fileToAgencyId;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFromAgencyId() {
		return fromAgencyId;
	}
	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}
	public String getToAgencyId() {
		return toAgencyId;
	}
	public void setToAgencyId(String toAgencyId) {
		this.toAgencyId = toAgencyId;
	}
	public String getFileFromAgencyId() {
		return fileFromAgencyId;
	}
	public void setFileFromAgencyId(String fileFromAgencyId) {
		this.fileFromAgencyId = fileFromAgencyId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDot() {
		return dot;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
	public String getUnderscroe() {
		return underscroe;
	}
	public void setUnderscroe(String underscroe) {
		this.underscroe = underscroe;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	

//	public QatpBufferRecordType getBufferRecordType(MtaHeaderinfoVO headerVO, MtaLaneHeaderInfoVO laneheaderVO,
//			XferControl xferControl, MtaFileNameInfo fileNameVO, MasterDataCache masterDataCache) throws UnsupportedEncodingException {
//		
//		QatpBufferRecordType obj= new QatpBufferRecordType();
//		obj.setTrxType("E");
//		obj.setTrxSubtype("Z");
//		obj.setReciprocityTrx("F");
//		
//		obj.setPlazaId(headerVO.getPlazaId());
//		obj.setLaneId(laneheaderVO.getLaneNum());
//		obj.setLaneConfig(laneheaderVO.getLaneConfig());
//		obj.setOnTime(laneheaderVO.getOnTime());
//		obj.setCollectorId(laneheaderVO.getVaultId()); 
//		obj.setReaderId(laneheaderVO.getReaderid());
//		obj.setRecordNumber(recordNumber);
//		obj.setTagId(tagSeialNum);
//		obj.setAgencyId(agencyID);
//		obj.setLcDeviceno(obj.getLcDeviceno()); 
//		obj.setAviClass(aviClass);
//		obj.setTagClass(tagClass);
//		obj.setVehicleClass(vehicleClass);
//		obj.setAvcProfile(avcProfile);
//		obj.setAvcAxles(avcAxles);
//		obj.setLaneNumber(laneNum);
//		obj.setTransitDate(transitDate);
//		obj.setTransitTime(transitTime);
//		obj.setTransactionInfo(transactionInfo); 
//		if (tranDetailSerail.trim().matches("[0-9]+")) {
//			obj.setTranDetailSrno(tranDetailSerail);
//			} else {
//				obj.setTrxType("R");
//				obj.setTrxSubtype("X");
//			}
//		//obj.setTranDetailSrno(tranDetailSerail); 
//		obj.setVehicleSpeed(vehicleSpeed);
//		obj.setrOcrImageColor(rearImageColor);
//		obj.setrOcrImageIndex(rearOcrImageIndex);
//		obj.setrOcrConfidence(rearOcrConfidence);
//		obj.setrOcrPlateNumber(rearOcrPlateNumber);
//		obj.setrOcrPlateType(rearOcrPlateType);
//		obj.setrOcrPlateState(rearOcrPlateState);
//		obj.setrOcrPlateCountry(rearOcrPlateCountry);
//		obj.setfOcrImageColor(frontImageColor);
//		obj.setfOcrImageIndex(frontOcrImageIndex);
//		obj.setfOcrConfidence(frontOcrConfidence);
//		obj.setfOcrPlateNumber(frontOcrPlateNumber);
//		obj.setfOcrPlateType(frontOcrPlateType);
//		obj.setfOcrPlateCountry(frontOcrPlateCountry);
//		obj.setfOcrPlateState(frontOcrPlateState);
//		if(xferControl.getXferControlId()!=null) {
//		obj.setIcIagclass((long)xferControl.getXferControlId());
//		}
//		obj.setEvent("/0");
//		obj.setEvent(event);
//		obj.setHOV("N");
//		if(obj.getPlazaId()!=null && !(obj.getPlazaId().trim().isEmpty()) && obj.getPlazaId().trim().matches("[0-9]+")) {
//		obj.setPlazaAgencyId((int) (long)masterDataCache.getPlaza(Convertor.toInteger(obj.getPlazaId())).getAgencyId());
//		}
//		if(obj.getLaneId()!=null && !(obj.getLaneId().trim().isEmpty()) && obj.getLaneId().trim().matches("[0-9]+")) {
//		obj.setIsLprEnabled(masterDataCache.findLprEnabled(Convertor.toLong(obj.getLaneId())).getLprEnabled());
//		}
//		obj.setPresenceTime("18581117000000000");
//		obj.setLostpresenceTime("18581117000000000");
//		if(obj.getPlazaId()!=null && !(obj.getPlazaId().trim().isEmpty()) && obj.getPlazaId().trim().matches("[0-9]+")) {
//		obj.setExternPlazaId(masterDataCache.getPlaza(Convertor.toInteger(obj.getPlazaId())).getExternPlazaId());
//		}
//		if(obj.getLaneId()!=null && !(obj.getLaneId().trim().isEmpty()) && obj.getLaneId().trim().matches("[0-9]+")) {
//		obj.setExternLaneId(masterDataCache.getExternLaneId(Convertor.toLong(obj.getLaneId())).getExternLaneId());
//		}
//		if(fileNameVO.getFileDate()!=null) {
//		obj.setTagFlag(Convertor.toInteger(fileNameVO.getFileDate()));
//		}
//		if(transitDate!=null && transitTime!=null && !(transitDate.trim().isEmpty()) && !(transitTime.trim().isEmpty())) {
//			String temp=transitDate+transitTime;
//			obj.setTransitDateTime(DateUtils.getTimeStamp(temp, "yyyyMMddHHmmssSS"));
//			
//		}
//		
//		String tran_info_first = getTransactionInfo();
//		GenericValidation genericValidation = new GenericValidation();
//		
//		if (getTransitDate() !=null && (getTransitDate().trim().contains("") || getTransitDate().trim().matches("[0-9]+")) && !genericValidation.dateValidation(getTransitDate(), "YYYYMMDD")) {
//			obj.setTrxSubtype("D");
//			obj.setTrxType("R");
//		}
//		
//		if (getTransitTime() !=null && (getTransitDate().trim().contains("") || getTransitDate().trim().matches("[0-9]+")) && !genericValidation.timeValidation(getTransitTime(), "HHMMSSTT")) {
//			obj.setTrxSubtype("D");
//			obj.setTrxType("R");
//		}
//		
//	   if(getTranDetailSerail()!=null && !(getTranDetailSerail().matches("\\d{12}")))
//	   {
//		   obj.setTrxSubtype("N");
//			obj.setTrxType("R");
//	   }
//	   
//	   if(getRecordNumber()!=null && !(getRecordNumber().matches("\\d{9}")))
//	   {
//		   	obj.setTrxSubtype("N");
//			obj.setTrxType("R");
//	   }
//	     
//	    if (!(obj.getPlazaId().trim().isEmpty()) &&  obj.getPlazaId().trim().matches("[0-9]+") &&
//	    		!(masterDataCache.isValidPlaza(Convertor.toInteger(obj.getPlazaId()))) && obj.getPlazaId()!=null)   {
//	    	obj.setTrxSubtype("L");
//			obj.setTrxType("R");
//	    }
//	   
//	   
//	    if (!(obj.getLaneId().trim().isEmpty()) && obj.getLaneId().trim().matches("[0-9]+") &&
//	    		!(masterDataCache.isValidLane(Convertor.toLong(obj.getLaneId()))) && obj.getLaneId()!=null)   {
//	    	obj.setTrxSubtype("L");
//			obj.setTrxType("R");
//	    }
//	   
//	     if ( !(obj.getLaneConfig().matches("[EAM]{1}[0|1]{3}")) && obj.getLaneConfig()!=null) {
//	           obj.setTrxSubtype("M");
//				obj.setTrxType("R");
//	     }
//	     
//	     if (obj.getLaneConfig().charAt(0) != 'E' && obj.getLaneConfig()!=null)
//	     {
//	             if(!(obj.getCollectorId().matches("\\d{4}")) && obj.getCollectorId() !=null)
//	            obj.setTrxSubtype("N");
//	 			obj.setTrxType("R");     
//	      }
//	     
//	     if(!(getTagSeialNum()==null) && !(getTagSeialNum().trim().isEmpty()) && !(getTagSeialNum().contains("******")) && 
//	    		 getTagSeialNum().trim().matches("^[0-9a-fA-F]+$")) {
//				String tagSerialNumber = convertHexadecimalToDecimal(getTagSeialNum());
//	     if ((Convertor.toInteger(tagSerialNumber) <0 || Convertor.toInteger(tagSerialNumber)>99999999)) {
//	    	 	obj.setTrxSubtype("T");
//	 			obj.setTrxType("R");
//	    }
//	     obj.setTagId(tagSerialNumber);
//	     StringBuilder sbFileName = new StringBuilder();
//	     sbFileName.append("0").append(obj.getAgencyId()+StringUtils.leftPad(String.valueOf(tagSerialNumber), 8, "0"));
//	     obj.setLcDeviceno(sbFileName.toString());
//	     
//	   }
//	     else {
//				StringBuilder sbFileName = new StringBuilder();
//				if(getTagSeialNum().contains("      ")) {
//			    sbFileName.append("0").append(obj.getAgencyId()+getTagSeialNum()).append("  ");
//			    obj.setLcDeviceno(sbFileName.toString());
//				}
//				else if(getTagSeialNum().contains("******")) {
//					sbFileName.append("0").append(obj.getAgencyId()+getTagSeialNum()).append("**");
//				    obj.setLcDeviceno(sbFileName.toString());
//				}
//				obj.setTagId(getTagSeialNum()==null?null:getTagSeialNum());
//	     }
//	    
//	     if (getAgencyID()!=null && !(getAgencyID().matches("\\d{2}")))  {
//				obj.setTrxSubtype("T");
//				obj.setTrxType("R");
//		}
//	     
//	     
//	     if(!(getAviClass()==null) && !(getAviClass().trim().isEmpty()) && !(getAviClass().contains("**")) && getAviClass().trim().matches("^[0-9a-fA-F]+$")) {
//	    	 String AviClass=convertHexadecimalToDecimal(getAviClass());
//	    	 if(getTransactionInfo().charAt(0) == 'Y')
//		     {
//		    	  if((Convertor.toInteger(AviClass)<1 || Convertor.toInteger(AviClass)>99) && getAviClass()!=null)
//		           {    obj.setTrxSubtype("C");
//						obj.setTrxType("R");  
//		           }
//		    	  obj.setAviClass(AviClass);
//		     }
//		     
//	 
//			}else {
//		    		 	obj.setTrxSubtype("C");
//						obj.setTrxType("R"); 
//						obj.setAviClass(getAviClass()==null?null:getAviClass());
//			}
//	    
//	     
//	     if(!(getTagClass()==null) && !(getTagClass().trim().isEmpty()) && !(getTagClass().contains("**")) &&
//	    		 getTagClass().trim().matches("^[0-9a-fA-F]+$")) {
//	    	 String tagClass=convertHexadecimalToDecimal(getTagClass());
//	    	 if(getTransactionInfo().charAt(0) == 'Y')
//		     {
//		    	  if((Convertor.toInteger(tagClass)<1 || Convertor.toInteger(tagClass)>99) && getTagClass()!=null)
//		           {    obj.setTrxSubtype("C");
//						obj.setTrxType("R");  
//		           }
//		    	  obj.setTagClass(tagClass);
//		     }
//		     
//	 
//			}else {
//		    		 	obj.setTrxSubtype("C");
//						obj.setTrxType("R"); 
//						obj.setTagClass(getTagClass()==null?null:getTagClass());
//			}
//	    
//	     
//	     if(getVehicleClass().trim().matches("^[0-9a-zA-Z]+$") && getVehicleClass()!=null) {
//	     Integer ExtractVehicleClass=convertCharToAscii(getVehicleClass());
//	     Integer vehicleClass;
//	     if(ExtractVehicleClass<64) {
//	    	 vehicleClass = ExtractVehicleClass;
//	         }else {
//	        	 vehicleClass = ExtractVehicleClass - 64;
//	         }
//         if (vehicleClass<1 || vehicleClass>21)
//         {    
//         	obj.setTrxSubtype("C");
// 			obj.setTrxType("R");      
//         }    
//         obj.setVehicleClass(vehicleClass.toString());
//	     }else {
//	    	 
//	    	 	obj.setTrxSubtype("C");
//				obj.setTrxType("R"); 
//				obj.setTagClass(getVehicleClass()==null?null:getVehicleClass());
//	    	 
//	     }
//         
//	     if(getAvcProfile().trim().matches("^[0-9]+$") || getAvcProfile().equals("H") || getAvcProfile().equals("O") && getAvcProfile()!=null) {
//         Integer ExtractAvcProfile=convertCharToAscii(getAvcProfile());
//         Integer AvcProfile;
//         if(ExtractAvcProfile<64) {
//        	 AvcProfile = ExtractAvcProfile;
//             }else {
//            	 AvcProfile = ExtractAvcProfile - 64;
//             }
//         if (AvcProfile<1 || AvcProfile>21)
//         {    
//         	obj.setTrxSubtype("C");
// 			obj.setTrxType("R");      
//         }    
//         obj.setAvcProfile(AvcProfile.toString());
//	     }else {
//	    	 
//	    	 	obj.setTrxSubtype("C");
//				obj.setTrxType("R"); 
//				obj.setTagClass(getAvcProfile()==null?null:getAvcProfile());
//	    	 
//	     }
//      
//         
//	     if(getAvcAxles().trim().matches("^[0-9a-zA-Z]+$") && getAvcAxles()!=null) {
//         Integer ExtractAvcAxles=convertCharToAscii(getAvcAxles());
//         Integer avcAxles;
//         if(ExtractAvcAxles<64) {
//         avcAxles = ExtractAvcAxles;
//         }else {
//         avcAxles = ExtractAvcAxles - 64;
//         }
//         if (avcAxles<1 || avcAxles>21)
//         {    
//         	obj.setTrxSubtype("C");
// 			obj.setTrxType("R");      
//         }    
//         obj.setAvcAxles(avcAxles.toString());
//	     }else {
//	    	 
//	    	 	obj.setTrxSubtype("C");
//				obj.setTrxType("R"); 
//				obj.setTagClass(getAvcAxles()==null?null:getAvcAxles());
//	    	 
//	     }
//	     
//	     
//	     char[] transactionInfo = tran_info_first.toCharArray();
//			if ((transactionInfo[0]=='Y')
//	                && (transactionInfo[4]!='V')) 
//	     {
//				obj.setTrxSubtype("V");
//				obj.setTrxType("R");  
//	     }
//	     else if ((transactionInfo[0]=='N')
//	                && ((transactionInfo[4]!='x') 
//	                && (transactionInfo[4]!='X'))) 
//	     {
//	    	 	obj.setTrxSubtype("V");
//				obj.setTrxType("R");  
//	     }
//	     else if(transactionInfo[0] == 'N')
//	     {
//	    	 obj.setTrxSubtype("V");
//	    	 obj.setTrxType("R");  
//	     }
//	                
////
////	     if (((transactionInfo[0] == 'Y') 
////	     && (transactionInfo[2] == 'N')   
////	     && (transactionInfo[3] == '0'))  
////	     || ((transactionInfo[0] == 'Y')    
////	     && (transactionInfo[2] == 'Y')   
////	     &&  (obj.getAviClass() == "14")
////	     && (transactionInfo[3] == '0'))) 
////	     {
////	    	 obj.setTrxType("O"); 
////	    	 obj.setTrxSubtype("T");
////	    	 obj.setReciprocityTrx("T");
////	     }
////	     else 
////	     {
////	    	obj.setTrxSubtype("V");
////	    	obj.setTrxType("V"); 
////	     if (obj.getLcIagclass()>0)
////	     {
////	    	 obj.setReciprocityTrx("T");
////	     }
////	     else
////	     {
////	    	 obj.setReciprocityTrx("F");
////	     }
////	     }
//	                
//		return obj;
//		
//	}
//
//	private String convertHexadecimalToDecimal(String value){
//		if(value.trim().matches("^[0-9a-fA-F]+$")) {
//		return String.valueOf(Integer.parseInt(value.trim(), 16));
//		}else {
//			return value;
//		}
//		
//	}
//	
//	public String ConvertDecimalToHexadecimal(String value) throws UnsupportedEncodingException {
//		
//		byte[] myBytes = value.getBytes("UTF-8");
//
//	    return DatatypeConverter.printHexBinary(myBytes);
//	}
//	
//	private Integer convertCharToAscii(String value) {
//		if(value.trim().matches("[0-9]+")) {
//			return Convertor.toInteger(value);
//		}else {
//		char character = value.charAt(0); 
//		int ascii = (int) character;
//		return ascii;
//		}
//	}
}
