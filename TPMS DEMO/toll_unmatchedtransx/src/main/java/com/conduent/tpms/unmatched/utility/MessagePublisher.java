package com.conduent.tpms.unmatched.utility;

import java.util.List;

import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

/**
 * 
 * Publish Message Inerface
 * 
 * @author deepeshb
 *
 */
public interface MessagePublisher {

	/**
	 * Publish Message to stream
	 * 
	 * @param laneTxId  Key of the msg
	 * @param msg Value of the msg 
	 * @return boolean Message publsihed successfully or not
	 */
	public boolean publishMessage(String laneTxId, byte[] msg,String streamId);

	/**
	 * Publish Messages to stream
	 * 
	 * @param Messages  
	 * @return long Number of messages published successfully
	 */
	public long publishMessages(List<PutMessagesDetailsEntry> messages);
}
