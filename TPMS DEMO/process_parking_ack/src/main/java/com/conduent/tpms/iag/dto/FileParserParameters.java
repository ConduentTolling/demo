package com.conduent.tpms.iag.dto;

import java.util.List;

public class FileParserParameters
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean isFileNameValid;
	private String fileName;
	private String srcfilePath;
	private String agencyId = "";
	private String fileType = "";
	private FileInfoDto fileInfoDto;
	List<MappingInfoDto> detailRecMappingInfo;
	List<MappingInfoDto> fileNameMappingInfo;
	List<MappingInfoDto> longFileNameMappingInfo;
	List<MappingInfoDto> longdetailRecMappingInfo;

	public List<MappingInfoDto> getLongdetailRecMappingInfo() {
		return longdetailRecMappingInfo;
	}

	public void setLongdetailRecMappingInfo(List<MappingInfoDto> longdetailRecMappingInfo) {
		this.longdetailRecMappingInfo = longdetailRecMappingInfo;
	}

	public List<MappingInfoDto> getLongFileNameMappingInfo() {
		return longFileNameMappingInfo;
	}

	public void setLongFileNameMappingInfo(List<MappingInfoDto> longFileNameMappingInfo) {
		this.longFileNameMappingInfo = longFileNameMappingInfo;
	}

	public Boolean getIsFileNameValid() {
		return isFileNameValid;
	}

	public void setIsFileNameValid(Boolean isFileNameValid) {
		this.isFileNameValid = isFileNameValid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSrcfilePath() {
		return srcfilePath;
	}

	public void setSrcfilePath(String srcfilePath) {
		this.srcfilePath = srcfilePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<MappingInfoDto> getDetailRecMappingInfo() {
		return detailRecMappingInfo;
	}

	public void setDetailRecMappingInfo(List<MappingInfoDto> detailRecMappingInfo) {
		this.detailRecMappingInfo = detailRecMappingInfo;
	}

	public List<MappingInfoDto> getFileNameMappingInfo() {
		return fileNameMappingInfo;
	}

	public void setFileNameMappingInfo(List<MappingInfoDto> fileNameMappingInfo) {
		this.fileNameMappingInfo = fileNameMappingInfo;
	}

	public FileInfoDto getFileInfoDto() {
		return fileInfoDto;
	}

	public void setFileInfoDto(FileInfoDto fileInfoDto) {
		this.fileInfoDto = fileInfoDto;
	}
}