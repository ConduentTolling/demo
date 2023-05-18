package com.conduent.tpms.ibts.away.tx.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;



public class EntryPlazaIdValidator implements ConstraintValidator<EntryPlazaId, AwayTransaction> {

	@Override
	public boolean isValid(AwayTransaction value, ConstraintValidatorContext context) {
		String tempTollSystemType = value != null ? value.getTollSystemType() : null;
		if (null != tempTollSystemType && "C".equalsIgnoreCase(tempTollSystemType)) {
			Integer tempEntryPlazaId = value.getEntryPlazaId();
			return (null != tempEntryPlazaId);
		}
		return true;
	}

	

}
