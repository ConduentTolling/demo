package com.conduent.parking.posting.constant;

public enum AccountFinStatus {
	GOOD("GOOD",0),
	LOW("LOW",3),
	ZERO("ZERO",4),
	AUTOPAY("AUTOPAY",5);

	private final String name;
	private final int code;

	private AccountFinStatus(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static AccountFinStatus getByCode(int code)
	{
		switch(code)
		{
		case 0:
			return AccountFinStatus.GOOD;
		case 3:
			return AccountFinStatus.LOW;
		case 4:
			return AccountFinStatus.ZERO;
		case 5:
			return AccountFinStatus.AUTOPAY;
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
