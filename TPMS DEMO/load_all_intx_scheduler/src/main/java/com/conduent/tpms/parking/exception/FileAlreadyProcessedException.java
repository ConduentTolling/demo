package com.conduent.tpms.parking.exception;

public class FileAlreadyProcessedException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3351065656266615336L;

	public FileAlreadyProcessedException(String msg)
	{
		super(msg);
	}
}