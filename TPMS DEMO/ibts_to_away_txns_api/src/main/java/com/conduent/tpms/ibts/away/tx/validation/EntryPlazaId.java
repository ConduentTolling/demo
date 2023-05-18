package com.conduent.tpms.ibts.away.tx.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = EntryPlazaIdValidator.class)
@Documented
public @interface EntryPlazaId {

	String message() default "Entry Plaza Id should not be blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}