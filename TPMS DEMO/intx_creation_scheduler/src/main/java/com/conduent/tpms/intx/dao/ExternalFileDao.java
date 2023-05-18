package com.conduent.tpms.intx.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.conduent.tpms.intx.model.ExternalFile;

@Component
public interface ExternalFileDao {
	
	// Get ExternalFiles By Agency
	public List<ExternalFile> getExternalFilesByAgency(String devicePrefix);

	// Get Correction ExternalFiles By Agency
    public List<ExternalFile> getCorrExternalFilesByAgency(String devicePrefix);

}
