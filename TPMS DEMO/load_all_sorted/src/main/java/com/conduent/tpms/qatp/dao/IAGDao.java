package com.conduent.tpms.qatp.dao;

import java.sql.SQLException;
import java.util.List;
import com.conduent.tpms.qatp.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.dto.PlanPolicyInfoDto;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;

/**
 * Interface for DAO operations
 * 
 * @author Urvashi C
 *
 */
public interface IAGDao
{
	public List<TagStatusSortedRecord> getDeviceInformation(String startDeviceNo, String endDeviceNo, String loadType) throws SQLException;
	public List<TagStatusSortedRecord> getETCAccountInfo(List<TagStatusSortedRecord> listOfObj, String startDeviceNo, String endDeviceNo) throws SQLException;
	public PlanPolicyInfoDto getPlanPolicyInfo(long LC_ETC_ACCOUNT_ID, String LC_DEVICE_NO);
	public void insertIntoTTagAllSorted(List<TagStatusSortedRecord> recordslist)throws SQLException;
	public String getPlanString(long LC_ETC_ACCOUNT_ID, String LC_DEVICE_NO);
	public int delfromIncrAllsorted();
	public void insertintoTHTagAllSorted(List<TagStatusSortedRecord> activeTagsList);
	public List<TagStatusSortedRecord> getDeviceInfo(String DeviceNo) throws SQLException;
	public void updateTTagStatusallsorted(String deviceno , List<TagStatusSortedRecord> list);
	public boolean updateTIncrALlSortedforMTA(String deviceno , int agecny_id, List<TagStatusSortedRecord> list);
	public boolean updateTIncrALlSortedforRIO(String deviceno , int agecny_id, List<TagStatusSortedRecord> list);
	public boolean updateTIncrALlSortedforPA(String deviceno , int agecny_id, List<TagStatusSortedRecord> list);
//	public boolean updateTIncrALlSortedforFTAG(String deviceno, int agency_id, List<TagStatusSortedRecord> list, int plazaId);//for FTAG
	public void insertIntoTIncrTTagAllSortedforMTA(List<TagStatusSortedRecord> incrlist);
	public void insertIntoTIncrTTagAllSortedforRIO(List<TagStatusSortedRecord> incrlist);
	public void insertIntoTIncrTTagAllSortedforPA(List<TagStatusSortedRecord> incrlist);
//	public void insertIntoTIncrTTagAllSortedforFTAG(List<TagStatusSortedRecord> incrlist, int plazaId);//for FTAG
	public TagStatusSortedRecord checkifrecordexists(String deviceNo);
	public void updaterecordinTTagAllSorted(TagStatusSortedRecord record);
	public boolean checkIfRecordExistsforPlazaID(int plaza_id, String deviceNo);
	//public boolean checkifrecordexists1(String deviceNo);
	public boolean checkifrecordexists1(TagStatusSortedRecord tagstatussorted);
	
	public String getOptInOutFlag(TagStatusSortedRecord tagStatusSortedRecord); //CRM table dependency
	public AccountApiInfoDto getAccountInfoFromFPMS(String valueOf);

}
