package com.conduent.tpms.qatp.dao;

import com.conduent.tpms.qatp.model.VehicleClass;

public interface IVehicleClassDao 
{
	public VehicleClass getVehicleClass(Integer agencyId,String externAgencyClass);

}
