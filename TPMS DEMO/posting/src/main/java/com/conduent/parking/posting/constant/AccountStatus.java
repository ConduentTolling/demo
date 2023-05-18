package com.conduent.parking.posting.constant;

public enum AccountStatus 
{
	PENDING("PENDING",0),
	ACTIVE("ACTIVE",3),
	CLOSE_PEND("CLOSE PEND",4),
	RVKW("RVKW",5),
	RVKF("RVKF",6),
	COLLECTION("COLLECTION",7),
	CLOSED("CLOSED",8);

	private final String name;
	private final int code;

	private AccountStatus(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static AccountStatus getByCode(int code)
	{
		switch(code)
		{
		case 0:
			return AccountStatus.PENDING;
		case 3:
			return AccountStatus.ACTIVE;
		case 4:
			return AccountStatus.CLOSE_PEND;
		case 5:
			return AccountStatus.RVKW;
		case 6:
			return AccountStatus.RVKF;
		case 7:
			return AccountStatus.COLLECTION;
		case 8:
			return AccountStatus.CLOSED;
		
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