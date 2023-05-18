package  com.conduent.tpms.iag.constants;

public enum AckStatusCode 
{
	SUCCESS("00"), HEADER_FAIL("08"), DETAIL_FAIL("02"),INVALID_RECORD_COUNT("01"), INVALID_FILE("07"),DATE_MISMATCH("03"),FILE_ALREADY_PROCESSED("05")
	,TRANSACTION_FILE_UNAVAILABLE("04"),GAP_IN_SEQ_NUM("06");


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
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
}