package com.conduent.tpms.iag.service;

import java.io.IOException;

public interface ICLPProcessService extends IAGLoadFileService{

	public String loadICLPfile() throws IOException;
}
