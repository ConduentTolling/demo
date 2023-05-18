package com.conduent.tpms.qatp.constants;

public enum FileStatusInd 
{
	START("S"), COMPLETE("C"), IN_PROGRESS("P");

	private final String code;

	FileStatusInd(String code)
	{
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public static FileStatusInd getByCode(String code) {
		if (code != null && !code.isEmpty()) {
			String codeUp = code.toUpperCase();
			for (FileStatusInd cs : values()) {
				if (codeUp.equals(cs.code)) {
					return cs;
				}
			}
		}
		return null;
	}

	public static FileStatusInd getByType(FileStatusInd code) 
	{
		if (code != null) 
		{
			switch (code) 
			{
			case START:
				return FileStatusInd.START;
			case COMPLETE:
				return FileStatusInd.COMPLETE;
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
}