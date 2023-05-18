package com.conduent.tollposting.constant;
public enum PlanType 
{
	STANDARD("STANDARD",1),
	BUSINESS("BUSINESS",2),
	MCC("MCC",5),
	MCLP("MCLP",6),
	NY12("NY12",8),
	NYNREM("NYNREM",9),
	NYNRL("NYNRL",11),
	NYNRTH("NYNRTH",12),
	NYCOML("NYCOML",13),
	SIR("SIR",51),
	RR("RR",52),
	PAGREEN("PAGREEN",54),
	TBNRFP("TBNRFP",66),
	BR("BR",71),
	QR("QR",72),
	PASI("PASI",102),
	PANRPL("PANRPL",107),
	RNT("RNT",184),
	RRT("RRT",185),
	VRT("VRT",187),
	VIDEOUNREG("VIDEOUNREG",217),
	STVA("STVA",290),
	PRVA("PRVA",402),
	BRVA("BRVA",403),
	VNB("VNB",0),
	DELDOT("DELDOT",0),
	PQR("PQR",999),
	//ABC("ABC",888);
	ABC("ABC",0),
	TVD("TVD",400),
	VNC("VNC",401);

	private final String name;
	private final int code;

	private PlanType(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public static PlanType getByCode(int code)
	{
		switch(code)
		{
		case 1:
			return PlanType.STANDARD;
		case 2:
			return PlanType.BUSINESS;
		case 5:
			return PlanType.MCC;
		case 6:
			return PlanType.MCLP;
		case 8:
			return PlanType.NY12;
		case 9:
			return PlanType.NYNREM;
		case 11:
			return PlanType.NYNRL;
		case 12:
			return PlanType.NYNRTH;
		case 13:
			return PlanType.NYCOML;
		case 51:
			return PlanType.SIR;
		case 52:
			return PlanType.RR;
		case 54:
			return PlanType.PAGREEN;
		case 66:
			return PlanType.TBNRFP;
		case 102:
			return PlanType.PASI;
		case 107:
			return PlanType.PANRPL;
		case 184:
			return PlanType.RNT;
		case 185:
			return PlanType.RRT;
		case 187:
			return PlanType.VRT;
		case 290:
			return PlanType.STVA;
		case 217:
			return PlanType.VIDEOUNREG;
		case 0:
			return PlanType.MCLP;
		case 400:
			return PlanType.TVD;
		case 401:
			return PlanType.VNC;
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