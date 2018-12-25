package com.tydic.traffic.tafa.validation.annotation;

import com.tydic.traffic.tafa.validation.support.MobileImpl;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { MobileImpl.class })
public @interface Mobile {
	
	public boolean required() default true; //是否必须 

	public String message() default "{mobile}{illegal}";

	public Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
