package com.conduent.tpms.iag.pub;

public interface MessagePublisher {

	public boolean publishMessage(String streamId, byte[] msg);
}
