package com.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;


public class FileCreateDemo {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String fileName="002.ACK";
		String content="ACK 4000";
		/* try (FileOutputStream fos = new FileOutputStream(fileName)) {
			      fos.write(content.getBytes());
			  }*/
		StringBuilder sb = new StringBuilder();
		sb.append("ACK ").append("FROM_AGENCY_ID").append("TO_AGENCY").append(StringUtils.rightPad("mayuri", 10, "")).append(LocalDateTime.now());
		System.out.println(sb);
	}

}
