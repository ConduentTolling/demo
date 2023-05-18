package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.ITAGDevice;


/**
 * Interface for DAO operations
 * 
 * @author Urvashi C
 *
 */
public interface IAGDao
{
	public List<ITAGDevice> getDevieListFromTTagAllSorted(int agencyId, String genType);
}
