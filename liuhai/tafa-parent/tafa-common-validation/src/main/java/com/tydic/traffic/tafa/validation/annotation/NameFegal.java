package com.tydic.traffic.tafa.validation.annotation;

import com.tydic.traffic.tafa.validation.support.NameFegalImpl;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { NameFegalImpl.class })
public @interface NameFegal {
	
	String message() default "";
	
	String regexp() default "";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

