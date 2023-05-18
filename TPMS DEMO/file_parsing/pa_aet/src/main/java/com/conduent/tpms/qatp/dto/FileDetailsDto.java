package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used for ITAG file details
 * 
 * @author MAYURIG1
 *
 */
public class FileDetailsDto implements Serializable {

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
	private String fileFormat = "";
	private Integer fileNameLength = null;

	private FileInfoDto fileInfoDto;
	private List<MappingInfoDto> headerMappingInfoList;
	private List<MappingInfoDto> trailerMappingInfoDto;
	private List<MappingInfoDto> detailRecMappingInfo;
	private List<MappingInfoDto> fileNameMappingInfo;

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

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Integer getFileNameLength() {
		return fileNameLength;
	}

	public void setFileNameLength(Integer fileNameLength) {
		this.fileNameLength = fileNameLength;
	}

	

}
