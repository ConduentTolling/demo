package com.conduent.tpms.iag.constants;

/**
 * constants for Tag status
 * 1 – Valid
 * 2 – Low Balance
 * 3 – Invalid (tag is not valid for use and will not be honored by the Home Agency/CSC but the customer information is available in the Invalid Tag Customer File).
 * 4 – Lost/Stolen (also includes all tags which are not valid for use and will not be honored by the Home Agency/CSC and for which customer information is not available).
 * @author MAYURIG1
 *
 */
public enum TagEnum {

	VALID(1), LOW_BALANCE(2), INVALID(3), LOST_OR_STOLEN(4);

	private int value;

	private TagEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
