package com.conduent.tpms.qeval.dao;

import com.conduent.tpms.qeval.model.VehicleClass;

public interface IVehicleDao 
{
	public VehicleClass getVehicleClass(Integer agencyId,String externAgencyClass);

}
