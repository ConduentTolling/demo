package com.conduent.tpms.qatp.constants;

public enum AckStatusCode 
{
	SUCCESS("00"),INVALID_RECORD_COUNT("01"),INVALID_DETAIL_RECORD("02"),INVALID_HEADR_FORMAT("08"),INVALID_FILE_STRUCTURE("07"),DUPLICATE_FILE_NUM("05")
	, GAP_IN_SEQ_NUM("06");

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
			case INVALID_RECORD_COUNT:
				return AckStatusCode.INVALID_RECORD_COUNT;
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
}