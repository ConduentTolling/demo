package com.conduent.tpms.icrx;

import java.io.File;

public class TestFile {

	
	public static void main(String[] args) {
		File tempFile = new File("D:\\TRANSACTION\\CREATE_ICRX\\008_022_20211125151123.TEMP");
		File fileWithNewName = new File("D:\\TRANSACTION\\CREATE_ICRX\\008_022_20211125151123.ICRX");
		boolean status = tempFile.renameTo(fileWithNewName);
		System.out.println(status);
	}
}
