package com.conduent.parking.posting.constant;

public enum AccountType 
{
	PRIVATE("PRIVATE",1),
	COMMERCIAL("COMMERCIAL",2),
	BUSINESS("BUSINESS",3),
	PVIDEOREG("PVIDEOREG",5),
	NONREGVIDEO("NONREGVIDEO",6),
	BVIDEOREG("BVIDEOREG",7),
	PVIOLATOR("PVIOLATOR",8),
	NONREVENUE("NONREVENUE",9),
	CVIOLATOR("CVIOLATOR",10),
	PVIDEOUNREG("PVIDEOUNREG",11),
	BVIDEOUNREG("BVIDEOUNREG",12),
	REGVIDEO("REGVIDEO",13),
	STVA("STVA",14);

	private final String name;
	private final int code;

	private AccountType(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static AccountType getByCode(int code)
	{
		switch(code)
		{
		case 2:
			return AccountType.COMMERCIAL;
		case 3:
			return AccountType.BUSINESS;
		case 14:
			return AccountType.STVA;
		
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