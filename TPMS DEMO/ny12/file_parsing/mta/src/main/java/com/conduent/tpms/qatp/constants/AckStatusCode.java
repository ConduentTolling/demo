package com.conduent.tpms.qatp.constants;

public enum AckStatusCode 
{
	SUCCESS("00"), HEADER_FAIL("08"), DETAIL_FAIL("02"),INVALID_RECORD_COUNT("01"),INVALID_DATE_TIME("03"),INVALID_FILE_STRUCTURE("07"),DUPLICATE_FILE_NUM("05");

	private final String code;

	AckStatusCode(String code)
	{
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public static AckStatusCode getByCode(String code) {
		if (code != null && !code.isEmpty()) {
			String codeUp = code.toUpperCase();
			for (AckStatusCode cs : values()) {
				if (codeUp.equals(cs.code)) {
					return cs;
				}
			}
		}
		return null;
	}

	public static AckStatusCode getByType(AckStatusCode code) 
	{
		if (code != null) 
		{
			switch (code) 
			{
			case SUCCESS:
				return AckStatusCode.SUCCESS;
			case HEADER_FAIL:
				return AckStatusCode.HEADER_FAIL;
			case DETAIL_FAIL:
				return AckStatusCode.DETAIL_FAIL;
			case INVALID_RECORD_COUNT:
				return AckStatusCode.INVALID_RECORD_COUNT;
			case INVALID_DATE_TIME:
				return AckStatusCode.INVALID_DATE_TIME;
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
}