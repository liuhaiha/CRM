package com.tydic.traffic.tafa.validation.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 校验bean
 * @author acer
 * @since  2017.06.12
 */
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface BeanValid {
	
	public Class<?>[] groups() default {};

}
