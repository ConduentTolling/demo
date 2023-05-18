package com.conduent.parking.posting.constant;

public enum DeviceStatus 
{
	INVENTORY("INVENTORY",1),
	ACTIVE("ACTIVE",3),
	RETURNED("RETURNED",5),
	DAMAGED("DAMAGED",6),
	RETURNDEF("RETURNDEF",7),
	LOST("LOST",8),
	STOLEN("STOLEN",9),
	SHIPVEND("SHIPVEND",10),
	FOUND("FOUND",16),
	PENDING("PENDING",11),	    //:TODO
	CLOSE_PEND("CLOSE_PEND",0); //:TODO
	

	private final String name;
	private final int code;

	private DeviceStatus(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static DeviceStatus getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return DeviceStatus.ACTIVE;
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