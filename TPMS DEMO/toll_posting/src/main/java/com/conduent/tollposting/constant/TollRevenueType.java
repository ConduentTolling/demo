package com.conduent.tollposting.constant;

public enum TollRevenueType 
{
	ETC("ETC",1);

	private final String name;
	private final int code;

	private TollRevenueType(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static TollRevenueType getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return TollRevenueType.ETC;
		default:
			return null;
		}
	}
	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
}