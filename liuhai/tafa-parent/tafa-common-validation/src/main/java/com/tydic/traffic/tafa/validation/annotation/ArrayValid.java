package com.tydic.traffic.tafa.validation.annotation;

import com.tydic.traffic.tafa.validation.support.ArrayValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 校验数组
 *
 * @author acer
 * @since 2017.06.12
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { ArrayValidImpl.class })
public @interface ArrayValid {

	public String value(); // trace名称

	public boolean required() default true; // 是否必须

	public String message() default "";

	public String pattern() default "";

	public Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ArrayValid[] value();
	}

}



