package com.conduent.tpms.parking.constants;

public enum FileProcessStatus 
{
	START("S"), COMPLETE("C"), IN_PROGRESS("P");

	private final String code;

	FileProcessStatus(String code)
	{
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public static FileProcessStatus getByCode(String code) {
		if (code != null && !code.isEmpty()) {
			String codeUp = code.toUpperCase();
			for (FileProcessStatus cs : values()) {
				if (codeUp.equals(cs.code)) {
					return cs;
				}
			}
		}
		return null;
	}

	public static FileProcessStatus getByType(FileProcessStatus code) 
	{
		if (code != null) 
		{
			switch (code) 
			{
			case START:
				return FileProcessStatus.START;
			case COMPLETE:
				return FileProcessStatus.COMPLETE;
			default:
				// unsupported type
				break;
			}
		}
		return null;
	}
}