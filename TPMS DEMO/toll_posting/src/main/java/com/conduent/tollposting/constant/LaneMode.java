package com.conduent.tollposting.constant;

public enum LaneMode 
{
	ETC("ETC",1),
	ACM("ACM",2),
	MANUAL("MANUAL",3),
	PASSTHRU("PASSTHRU",4),
	DETOUR("DETOUR",5),
	AVI("AVI",6),
	MIXED("MIXED",7),
	CARPOOL("CARPOOL",8);
	

	private final String name;
	private final int code;

	private LaneMode(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static LaneMode getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return LaneMode.ETC;
		case 2:
			return LaneMode.ACM;
		case 3:
			return LaneMode.MANUAL;
		case 4:
			return LaneMode.PASSTHRU;
		case 5:
			return LaneMode.DETOUR;
		case 6:
			return LaneMode.AVI;
		case 7:
			return LaneMode.MIXED;
		case 8:
			return LaneMode.CARPOOL;
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