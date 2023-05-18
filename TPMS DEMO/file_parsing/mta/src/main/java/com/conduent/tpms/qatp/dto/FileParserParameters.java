package com.conduent.tpms.qatp.dto;

import java.util.List;

public class FileParserParameters
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean isFileNameValid;
	private Boolean isHeaderValid;
	private Boolean isFileDownloaded;
	private String fileName;
	private String srcfilePath;
	private String agencyId = "";
	private String fileType = "";
	private FileInfoDto fileInfoDto;
	List<MappingInfoDto> headerMappingInfoList;
	List<MappingInfoDto> trailerMappingInfoDto;
	List<MappingInfoDto> detailRecMappingInfo;
	List<MappingInfoDto> fileNameMappingInfo;
	private Long fileNameSize;
	private Long headerSize;
	private Long detailSize;
	private Long trailerSize;
	

	public Long getFileNameSize() {
		return fileNameSize;
	}

	public void setFileNameSize(Long fileNameSize) {
		this.fileNameSize = fileNameSize;
	}

	public Long getHeaderSize() {
		return headerSize;
	}

	public void setHeaderSize(Long headerSize) {
		this.headerSize = headerSize;
	}

	public Long getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(Long detailSize) {
		this.detailSize = detailSize;
	}

	public Boolean getIsFileNameValid() {
		return isFileNameValid;
	}

	public void setIsFileNameValid(Boolean isFileNameValid) {
		this.isFileNameValid = isFileNameValid;
	}

	public Boolean getIsHeaderValid() {
		return isHeaderValid;
	}

	public void setIsHeaderValid(Boolean isHeaderValid) {
		this.isHeaderValid = isHeaderValid;
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

	public Boolean getIsFileDownloaded() {
		return isFileDownloaded;
	}

	public void setIsFileDownloaded(Boolean isFileDownloaded) {
		this.isFileDownloaded = isFileDownloaded;
	}

	public List<MappingInfoDto> getHeaderMappingInfoList() {
		return headerMappingInfoList;
	}

	public void setHeaderMappingInfoList(List<MappingInfoDto> headerMappingInfoList) {
		this.headerMappingInfoList = headerMappingInfoList;
	}

	public List<MappingInfoDto> getTrailerMappingInfoDto() {
		return trailerMappingInfoDto;
	}

	public void setTrailerMappingInfoDto(List<MappingInfoDto> trailerMappingInfoDto) {
		this.trailerMappingInfoDto = trailerMappingInfoDto;
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

	public Long getTrailerSize() {
		return trailerSize;
	}

	public void setTrailerSize(Long trailerSize) {
		this.trailerSize = trailerSize;
	}
}