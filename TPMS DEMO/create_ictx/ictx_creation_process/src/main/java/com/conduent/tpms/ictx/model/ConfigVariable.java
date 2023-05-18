package com.conduent.tpms.ictx.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for configuration value from properties file for different environment
 * 
 * @author deepeshb
 *
 */
@Component
public class ConfigVariable {

	@Value("${config.batchsize}")
	private Integer batchSize;

	@Value("${config.dstdirpath}")
	private String dstDirPath;
	
	@Value("${config.dstdirpathitxc}")
	private String dstDirPathItxc;
	
	@Value("${config.configfilepath}")
	private String configfilepath;

	@Value("${config.streamid}")
	private String streamId;

	@Value("${config.tollCalculationUri}")
	private String tollCalculationUri;
	
	
	public String getTollCalculationUri() {
		return tollCalculationUri;
	}

	public void setTollCalculationUri(String tollCalculationUri) {
		this.tollCalculationUri = tollCalculationUri;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public String getDstDirPath() {
		return dstDirPath;
	}

	public void setDstDirPath(String dstDirPath) {
		this.dstDirPath = dstDirPath;
	}

	public String getDstDirPathItxc() {
		return dstDirPathItxc;
	}

	public void setDstDirPathItxc(String dstDirPathItxc) {
		this.dstDirPathItxc = dstDirPathItxc;
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

	@Override
	public String toString() {
		return "ConfigVariable [batchSize=" + batchSize + ", dstDirPath=" + dstDirPath + ", configfilepath="
				+ configfilepath + ", streamId=" + streamId + "]";
	}

	

}
