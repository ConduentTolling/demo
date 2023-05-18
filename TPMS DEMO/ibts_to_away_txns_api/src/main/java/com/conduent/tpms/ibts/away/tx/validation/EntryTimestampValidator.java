package com.conduent.tpms.ibts.away.tx.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;



public class EntryTimestampValidator implements ConstraintValidator<EntryTimestamp, AwayTransaction> {

	@Override
	public boolean isValid(AwayTransaction value, ConstraintValidatorContext context) {
		String tempTollSystemType = value != null ? value.getTollSystemType() : null;
		if (null != tempTollSystemType && "C".equalsIgnoreCase(tempTollSystemType)) {
			String tempEntryTimestamp = value.getEntryTimestamp();
			return (null != tempEntryTimestamp);
		}
		return true;
	}

}
