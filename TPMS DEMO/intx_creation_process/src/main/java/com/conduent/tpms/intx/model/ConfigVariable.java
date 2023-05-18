package com.conduent.tpms.intx.model;

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

	@Value("${config.dstdirpathintx}")
	private String dstDirPathIntx;
	
	@Value("${config.dstdirpathitxn}")
	private String dstDirPathItxn;
	
	@Value("${config.configfilepath}")
	private String configfilepath;

	@Value("${config.streamid}")
	private String streamId;

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public String getDstDirPathIntx() {
		return dstDirPathIntx;
	}

	public void setDstDirPathIntx(String dstDirPathIntx) {
		this.dstDirPathIntx = dstDirPathIntx;
	}

	public String getDstDirPathItxn() {
		return dstDirPathItxn;
	}

	public void setDstDirPathItxn(String dstDirPathItxn) {
		this.dstDirPathItxn = dstDirPathItxn;
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
		return "ConfigVariable [batchSize=" + batchSize + ", dstDirPathIntx=" + dstDirPathIntx + ", dstDirPathItxn="
				+ dstDirPathItxn + ", configfilepath=" + configfilepath + ", streamId=" + streamId + "]";
	}

}
