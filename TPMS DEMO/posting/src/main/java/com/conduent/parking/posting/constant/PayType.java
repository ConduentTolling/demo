package com.conduent.parking.posting.constant;

public enum PayType {
	CASH("CASH",1),
	MASTERCARD("MASTERCARD",2),
	CHECK("CHECK",6);

	private final String name;
	private final int code;

	private PayType(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static PayType getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return PayType.CASH;
		case 2:
			return PayType.MASTERCARD;
		case 6:
			return PayType.CHECK;
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
