package com.tydic.traffic.tafa.validation.annotation;

import java.lang.annotation.*;

/**
 * Created by acer on 2017-06-23.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoValid {

}