package com.conduent.tpms.qeval.utility;

import java.util.List;

import com.conduent.tpms.qeval.model.TranDetail;
import com.google.gson.Gson;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

/**
 * 
 * Publish Message Inerface
 * 
 * @author Urvashic
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
	public static boolean publishMessage(String laneTxId, byte[] msg) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Publish Messages to stream
	 * 
	 * @param Messages  
	 * @return long Number of messages published successfully
	 */
	public long publishMessages(List<PutMessagesDetailsEntry> messages);

	/**
	 * 
	 * @param tranDetailList
	 * @param gson
	 * @return 
	 */
	public long publishMessagesList(List<TranDetail> tranDetailList, Gson gson);
}
