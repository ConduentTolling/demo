package com.conduent.tpms.qatp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.service.PlanStringService;

/**
 * Implementation class for PlanStringSevice
 * 
 * @author Urvashi C
 *
 */

@Service
public class PlanStringServiceImpl implements PlanStringService{
	
	private static final Logger log = LoggerFactory.getLogger(PlanStringServiceImpl.class);
	
	@Override
	public void buildAndSetPlanString(TagStatusSortedRecord tagStatusSortedRecord) {
		
		buildPaCtrlStr(tagStatusSortedRecord);
		buildmtaCtrlStr(tagStatusSortedRecord);
		if(tagStatusSortedRecord.getPlansStr() != null) {
			buildMtagPlanString(tagStatusSortedRecord);
		}
//		if(tagStatusSortedRecord.getPlansStr()!=null) {
//			buildFtagPlanString(tagStatusSortedRecord);
//		}
	}
	
	public void buildPaCtrlStr(TagStatusSortedRecord tagStatusSortedRecord) {
		String paCtrlStr;
		int rvkf_status = tagStatusSortedRecord.getAccountStatus();
		//if(Constants.PA_AGENCY_ID == tagStatusSortedRecord.getAgencyId())
	//{
		if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE || 
				tagStatusSortedRecord.getPrevalidationFlag() == Constants.YES)
	    {
	        if (tagStatusSortedRecord.getPaTagStatus() == Constants.TS_GOOD) { 
	        	paCtrlStr = Constants.PA_GOOD;
	        	tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
	        	log.info("For tag status good... {}",paCtrlStr);
	        }
	        else if (tagStatusSortedRecord.getPaTagStatus() == Constants.TS_LOW)
	        {
	        	paCtrlStr = Constants.PA_LOW;
	        	tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);	
	        	log.info("For tag status low... {}",paCtrlStr);
	        }
	        else if (tagStatusSortedRecord.getPaTagStatus() == Constants.FS_ZERO) {
	        	paCtrlStr = Constants.PA_ZERO;
	        	tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);	
	        	log.info("For tag status zero... {}",paCtrlStr);
	        }
	        else if(rvkf_status == Constants.RVKF)
	        {
	        	paCtrlStr = Constants.PA_RVKF;
				tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
				log.info("For rvkf status ... {}",paCtrlStr);
	        }
	        else {
	        	paCtrlStr = Constants.PA_DEFAULT;
	        	tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
	        	log.info("For else condition... {}",paCtrlStr);
	        }
	    }
		else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_LOST )   //RVKF & LOST same
	    {
			paCtrlStr = Constants.PA_DS_LOST;
			tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
			log.info("For tag status lost... {}",paCtrlStr);
	    }
		else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_STOLEN)
		{
			paCtrlStr = Constants.PA_DS_STOLEN;
			tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
			log.info("For tag status stolen... {}",paCtrlStr);
		}
		else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_INVENTORY) // NO maybe
		{
			paCtrlStr = Constants.PA_DS_INVENTORY;
			tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
			log.info("For else condition... {}",paCtrlStr);
		}
		else
		{
			paCtrlStr = Constants.PA_DEFAULT;
        	tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
        	log.info("For else condition... {}",paCtrlStr);
		}
		
		//Next if
		 if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE)
		   {
			 StringBuilder tsCtrlString = new StringBuilder(tagStatusSortedRecord.getTsCtrlStr());
		        if (tagStatusSortedRecord.getPaNonRevCount() > 0)
		        {
		        	tsCtrlString.setCharAt(4, '8');
		        }
		        else if ((tagStatusSortedRecord.getPaBridgesCount() > 0) && (tagStatusSortedRecord.getPaSiBridgesCount() > 0)
		                 && (tagStatusSortedRecord.getPaCarpoolCount() > 0))
		        {
		        	tsCtrlString.setCharAt(4, '7'); 
		        }
		        else if ((tagStatusSortedRecord.getPaNonRevCount() > 0) || (tagStatusSortedRecord.getPaSiBridgesCount() > 0 && tagStatusSortedRecord.getPaCarpoolCount() > 0))
		        {
		        	tsCtrlString.setCharAt(4,'6');
		        }
		        //TBD- not reachable block
		        else if((tagStatusSortedRecord.getPaBridgesCount() > 0)  && (tagStatusSortedRecord.getPaCarpoolCount() > 0))
		        {
		        	tsCtrlString.setCharAt(4,'5');
		        }
		        else if(tagStatusSortedRecord.getPaCarpoolCount() > 0)
		        {
		        	tsCtrlString.setCharAt(4,'4');
		        }
		        else if((tagStatusSortedRecord.getPaBridgesCount() > 0) && tagStatusSortedRecord.getPaSiBridgesCount() > 0)
		        {
		        	tsCtrlString.setCharAt(4,'3');
		        }
		        else if (tagStatusSortedRecord.getPaSiBridgesCount() > 0)
		        {
		        	tsCtrlString.setCharAt(4,'2');
		        }
		        else if (tagStatusSortedRecord.getPaBridgesCount() > 0)
		        {
		        	tsCtrlString.setCharAt(4,'1');
		        }
		        tagStatusSortedRecord.setTsCtrlStr(tsCtrlString.toString());
		   }
	//}		
		 
		// PA Speed Status
		 if(Constants.PA_AGENCY_ID == tagStatusSortedRecord.getAgencyId()
				 && (tagStatusSortedRecord.getSpeedAgency() > 0) && tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE)
		 {
			 paCtrlStr = Constants.PA_SPEED;
			 tagStatusSortedRecord.setTsCtrlStr(paCtrlStr);
		 } 
		 log.info("PA Ctrl string set as: {}",tagStatusSortedRecord.getTsCtrlStr());
}
	
	public void buildmtaCtrlStr(TagStatusSortedRecord tagStatusSortedRecord) 
	{
		 String mtaCtrlStr;
		 String rioCtrlStr;
		 StringBuilder mtCtrlString = null;
		 StringBuilder rioCtrlString = null;
		 String devicePrefix = tagStatusSortedRecord.getDeviceNo().substring(0,3);
		 int rvkf_status = tagStatusSortedRecord.getAccountStatus();

//	if(Constants.MTA_AGENCY_ID == tagStatusSortedRecord.getAgencyId())
	//{	 
		if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE || tagStatusSortedRecord.getPrevalidationFlag() == Constants.YES)
		 {
			 if (tagStatusSortedRecord.getMtaTagStatus() == Constants.TS_GOOD) 
			 { 
		        	mtaCtrlStr = Constants.MTA_GOOD;
		        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
		        	log.info("For tag status good... {}",mtaCtrlStr);
		        	
		        	rioCtrlStr = Constants.MTA_RIO_GOOD;
		        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
		        	log.info("For tag status good... {}",rioCtrlStr);
		     }
			 else if (tagStatusSortedRecord.getMtaTagStatus() == Constants.TS_LOW)
		     {
		        	mtaCtrlStr = Constants.MTA_LOW;
		        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
		        	log.info("For tag status low... {}",mtaCtrlStr);
		        	
		        	rioCtrlStr = Constants.MTA_RIO_LOW;
		        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
		        	log.info("For tag status low... {}",rioCtrlStr);
		     }
			 else if(rvkf_status == Constants.RVKF)
		     {
				 	mtaCtrlStr = Constants.MTA_RVKF;
		        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
		        	log.info("For tag status lost... {}",mtaCtrlStr);
		        	
		        	rioCtrlStr = Constants.MTA_RIO_RVKF;
		        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
		        	log.info("For tag status lost... {}",rioCtrlStr);
		     }
			 else
			 {
					mtaCtrlStr = Constants.MTA_DEFAULT;
					tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
					log.info("For else condition.. {}",mtaCtrlStr);
					
					rioCtrlStr = Constants.MTA_RIO_DEFAULT;
			        tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
			        log.info("For else condition... {}",rioCtrlStr);
			 }
			 
			 // Next If
			 mtCtrlString = new StringBuilder(tagStatusSortedRecord.getMtaCtrlStr());
			 rioCtrlString = new StringBuilder(tagStatusSortedRecord.getRioCtrlStr());
			 
			 if(!Constants.HOME_AGENCY_PREFIX.equals(devicePrefix))
			 {
				 mtCtrlString.setCharAt(4, 'C');
				 rioCtrlString.setCharAt(4, 'C');			 
			 }
			 else if (tagStatusSortedRecord.getBrCount() > 0)
			 {
				 mtCtrlString.setCharAt(4,'B');
				 rioCtrlString.setCharAt(4,'B');
			 }
			 else if(tagStatusSortedRecord.getQrCount() > 0)
			 {
				 mtCtrlString.setCharAt(4,'Q');
				 rioCtrlString.setCharAt(4,'Q');
			 }
			 else if(tagStatusSortedRecord.getSirCount() > 0)
			 {
				 mtCtrlString.setCharAt(4,'S');
				 rioCtrlString.setCharAt(4,'S');
			 }
			 else if (tagStatusSortedRecord.getRrCount() > 0) 
		        {
				 mtCtrlString.setCharAt(4,'R');
				 rioCtrlString.setCharAt(4,'R');
		        }
			 
			 tagStatusSortedRecord.setMtaCtrlStr(mtCtrlString.toString());
	         tagStatusSortedRecord.setRioCtrlStr(rioCtrlString.toString());
		 }
		 //else if (tagStatusSortedRecord.getDeviceStatus() == Constants.LOST)
		   else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_LOST)
		 { 
	        	mtaCtrlStr = Constants.MTA_DS_LOST;
	        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
	        	log.info("For tag status lost... {}",mtaCtrlStr);
	        	
	        	rioCtrlStr = Constants.MTA_RIO_DS_LOST;
	        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
	        	log.info("For tag status lost... {}",rioCtrlStr);
	     }
		 //else if (tagStatusSortedRecord.getDeviceStatus() == Constants.STOLEN) 
		 else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_STOLEN)
		 { 
	        	mtaCtrlStr = Constants.MTA_DS_STOLEN;
	        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
	        	log.info("For tag status stolen... {}",mtaCtrlStr);
	        	
	        	rioCtrlStr = Constants.MTA_RIO_DS_STOLEN;
	        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
	        	log.info("For tag status stolen... {}",rioCtrlStr);
	     }
		 else if (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_INVENTORY) 
		 { 
	        	mtaCtrlStr = Constants.MTA_DS_INVENTORY;
	        	tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
	        	log.info("For tag status inventory... {}",mtaCtrlStr);
	        	
	        	rioCtrlStr = Constants.MTA_RIO_DS_INVENTORY;
	        	tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
	        	log.info("For tag status inventory... {}",rioCtrlStr);
	     }
		 else
		 {
				mtaCtrlStr = Constants.MTA_DEFAULT;
				tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
				log.info("For else condition... {}",mtaCtrlStr);
				
				rioCtrlStr = Constants.MTA_RIO_DEFAULT;
		        tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
		        log.info("For else condition... {}",rioCtrlStr);
		 }
		 
		 mtCtrlString = new StringBuilder(tagStatusSortedRecord.getMtaCtrlStr());
		 rioCtrlString = new StringBuilder(tagStatusSortedRecord.getRioCtrlStr());
		
		 if(!Constants.HOME_AGENCY_PREFIX.equals(devicePrefix))  //TBD not reachable for mta if device pref n agency is same
		 {
			 mtCtrlString.setCharAt(4,'C');
			 rioCtrlString.setCharAt(4,'C');
			 tagStatusSortedRecord.setMtaCtrlStr(mtCtrlString.toString());
			 tagStatusSortedRecord.setRioCtrlStr(rioCtrlString.toString());
		 }
		 
	//}	 
		 // MTA RIO Speed Status
		 if(Constants.MTA_AGENCY_ID == Integer.valueOf((devicePrefix))
				 && (tagStatusSortedRecord.getSpeedAgency() > 0) && tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE)
		 {
			 mtaCtrlStr = Constants.MTA_SPEED;
			 tagStatusSortedRecord.setMtaCtrlStr(mtaCtrlStr);
			 rioCtrlStr = Constants.MTA_RIO_SPEED;
			 tagStatusSortedRecord.setRioCtrlStr(rioCtrlStr);
		 }
		 log.info("MTA Ctrl string set as: {}",tagStatusSortedRecord.getMtaCtrlStr());
		 log.info("MTA RIO Ctrl string set as: {}",tagStatusSortedRecord.getRioCtrlStr());
	}
	
	
	public void buildMtagPlanString(TagStatusSortedRecord tagStatusSortedRecord) {
		
		String planStr = tagStatusSortedRecord.getPlansStr(); 
		int planBit;
		int powBit;
		int bitValue;
		
		//Standard Plan
		planBit = Integer.valueOf(planStr.substring((int) (Math.floor(1 / 10) * 4),4)); 
		powBit = (int) Math.pow(2, Math.floorMod(1, 10));	
		bitValue = planBit & powBit; 
		
		if(bitValue > 0 && tagStatusSortedRecord.getAccountType()==Constants.PRIVATE) {
			tagStatusSortedRecord.setMtagPlanStr(tagStatusSortedRecord.getMtagPlanStr() + (4*16));  
		}
		
		//Thruway Grand Island Resident Plan 
		planBit = Integer.valueOf(planStr.substring((int) (Math.floor(3 / 10) * 4),4));
		powBit = (int) Math.pow(2, Math.floorMod(3, 10));
		bitValue = planBit & powBit;
		if (bitValue > 0) {
			tagStatusSortedRecord.setMtagPlanStr(tagStatusSortedRecord.getMtagPlanStr() + 2);
		}

		//Thruway Grand Island Commuter Plan
		planBit = Integer.valueOf(planStr.substring((int) (Math.floor(4 / 10) * 4),4)); 
		powBit = (int) Math.pow(2, Math.floorMod(4, 10));	
		bitValue = planBit & powBit; 

		  if (bitValue > 0) {
			  tagStatusSortedRecord.setMtagPlanStr(tagStatusSortedRecord.getMtagPlanStr() + 1);
		  }

		 //Thruway Tappan Zee Bridge Commuter Plan
		  planBit = Integer.valueOf(planStr.substring((int) (Math.floor(5 / 10) * 4),4)); 
		  powBit = (int) Math.pow(2, Math.floorMod(5, 10));	
		  bitValue = planBit & powBit; 
		  if (bitValue > 0) {
			  tagStatusSortedRecord.setMtagPlanStr(tagStatusSortedRecord.getMtagPlanStr() + (1 * 256));
		  } 
		log.info("MTAG Plan string set as: {}",tagStatusSortedRecord.getMtagPlanStr());
		
			}
	
//	public void buildFtagPlanString(TagStatusSortedRecord tagStatusSortedRecord) {
//		
//		String planStr = tagStatusSortedRecord.getPlansStr(); 
//		int planBit;
//		int powBit;
//		int bitValue;
//		
//		//Standard Plan
//		planBit = Integer.valueOf(planStr.substring((int) (Math.floor(1 / 10) * 4),4)); 
//		powBit = (int) Math.pow(2, Math.floorMod(1, 10));	
//		bitValue = planBit & powBit; 
//		
//		if(bitValue > 0 && tagStatusSortedRecord.getAccountType()==Constants.PRIVATE) {
//			tagStatusSortedRecord.setMtagPlanStr(tagStatusSortedRecord.getMtagPlanStr() + (4*16));  
//		}
//
//	}
}

