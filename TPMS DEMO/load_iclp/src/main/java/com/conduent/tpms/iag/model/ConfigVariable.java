package com.conduent.tpms.iag.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable {

	@Value("${config.filepathprefix}")
	private String filePathPrefix;

	@Value("${config.batchsize}")
	private Long batchSize;

	@Value("${config.configfilepath}")
	private String configfilepath;

	@Value("${config.streamid}")	
	private String streamId;

	@Value("${config.awaytag.new}")
	private String objectNameNew;
	
	@Value("${config.awaytag.old}")
	private String objectNameOld;
	
	@Value("${config.bucket.old}")
	private String bucketNameOld;
	
	@Value("${config.bucket.new}")
	private String bucketNameNew;
	
	@Value("${config.awaytagfile.path}")
	private String awaTagLocation;
	
	public String getAwaTagLocation() {
		return awaTagLocation;
	}

	public void setAwaTagLocation(String awaTagLocation) {
		this.awaTagLocation = awaTagLocation;
	}

	public String getFilePathPrefix() {
		return filePathPrefix;
	}

	public void setFilePathPrefix(String filePathPrefix) {
		this.filePathPrefix = filePathPrefix;
	}

	public Long getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Long batchSize) {
		this.batchSize = batchSize;
	}

	public String getConfigfilepath() {
		return configfilepath;
	}

	public void setConfigfilepath(String configfilepath) {
		this.configfilepath = configfilepath;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getObjectNameNew() {
		return objectNameNew;
	}

	public void setObjectNameNew(String objectNameNew) {
		this.objectNameNew = objectNameNew;
	}

	public String getObjectNameOld() {
		return objectNameOld;
	}

	public void setObjectNameOld(String objectNameOld) {
		this.objectNameOld = objectNameOld;
	}

	public String getBucketNameOld() {
		return bucketNameOld;
	}

	public void setBucketNameOld(String bucketNameOld) {
		this.bucketNameOld = bucketNameOld;
	}

	public String getBucketNameNew() {
		return bucketNameNew;
	}

	public void setBucketNameNew(String bucketNameNew) {
		this.bucketNameNew = bucketNameNew;
	}

	

}
