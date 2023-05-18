package com.conduent.tpms.iag.service;

import java.io.IOException;

public interface ITAGProcessService extends IAGLoadFileService{

	public String loadITAGfile() throws IOException;
}
