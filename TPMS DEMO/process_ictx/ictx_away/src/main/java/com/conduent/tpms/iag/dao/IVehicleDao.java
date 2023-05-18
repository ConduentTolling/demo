package com.conduent.tpms.iag.dao;

import com.conduent.tpms.iag.model.VehicleClass;

public interface IVehicleDao 
{
	public VehicleClass getVehicleClass(Integer agencyId,String externAgencyClass);

}
