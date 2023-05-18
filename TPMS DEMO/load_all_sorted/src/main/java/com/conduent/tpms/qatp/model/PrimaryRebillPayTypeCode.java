package  com.conduent.tpms.qatp.model;

public enum PrimaryRebillPayTypeCode 
{
	CASH("1"), MASTERCARD("2"), VISA("4"), AMEX("7"), disc("3"), sach("3"), ACHCPPT("3"), ACHSPPT("3");

	private final String code;

	PrimaryRebillPayTypeCode(String code)
	{
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public static PrimaryRebillPayTypeCode getByCode(String code) {
		if (code != null && !code.isEmpty()) {
			String codeUp = code.toUpperCase();
			for (PrimaryRebillPayTypeCode cs : values()) {
				if (codeUp.equals(cs.code)) {
					return cs;
				}
			}
		}
		return null;
	}
	

/*	public static PrimaryRebillPayTypeCode getByType(PrimaryRebillPayTypeCode code) 
	{
		if (code != null) 
		{
			switch (code) 
			{
			case SUCCESS:
				return PrimaryRebillPayTypeCode.SUCCESS;
			case HEADER_FAIL:
				return PrimaryRebillPayTypeCode.HEADER_FAIL;
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
	*/
}