package com.conduent.tollposting.constant;

public enum AgencyId 
{
	NY("NY",11);
	

	private final String name;
	private final int code;

	private AgencyId(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static AgencyId getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return AgencyId.NY;
		
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