package com.conduent.tpms.iag.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.model.VAddressDto;
import com.conduent.tpms.iag.model.VCustomerNameDto;
import com.conduent.tpms.iag.model.VVehicle;


/**
 * Interface for DAO operations
 * 
 * @author taniyan
 *
 */
public interface IAGDao
{

	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
	public int getetcaccountid(String deviceno);
	public List<VVehicle> getPlateInfo(int EtcAccountId);
	public VCustomerNameDto getCustomerName(int etc_account_id);
	public VAddressDto getAddressInfo(int etc_account_id);
	public VAddressDto getCompanyName(int etc_account_id);
	public Long getAgencyId(int etc_account_id);
	public String getInvalidStatusFlag();
}
