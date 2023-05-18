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
@Constraint(validatedBy = EntryLaneIdValidator.class)
@Documented
public @interface EntryLaneId {

	String message() default "Entry Lane Id should not be null";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}