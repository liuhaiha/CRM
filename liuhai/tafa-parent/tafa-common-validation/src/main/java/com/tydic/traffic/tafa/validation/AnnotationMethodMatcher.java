package com.tydic.traffic.tafa.validation;

import com.tydic.traffic.tafa.validation.annotation.NoValid;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * @author acer
 * @since 2017-06-23
 */
 class AnnotationMethodMatcher extends StaticMethodMatcher {

    private final Class<? extends Annotation> annotationType;

    private final boolean checkInherited;

    public AnnotationMethodMatcher(Class<? extends Annotation> annotationType, boolean checkInherited) {
        this.annotationType = annotationType;
        this.checkInherited = checkInherited;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(!checkInherited){
            if (method.isAnnotationPresent(this.annotationType)) {
                return true;
            }
            // The method may be on an interface, so let's check on the target class as well.
            Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            return (specificMethod != method && specificMethod.isAnnotationPresent(this.annotationType));
        }

        if (AnnotationUtils.findAnnotation(method,this.annotationType)!=null) {
            return true;
        }

        // The method may be on an interface, so let's check on the target class as well.
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return (specificMethod != method && AnnotationUtils.findAnnotation(specificMethod,this.annotationType)!=null);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnotationMethodMatcher)) {
            return false;
        }
        AnnotationMethodMatcher otherMm = (AnnotationMethodMatcher) other;
        return this.annotationType.equals(otherMm.annotationType);
    }

    @Override
    public int hashCode() {
        return this.annotationType.hashCode();
    }

    @Override
    public String toString() {
        return  getClass().getName() + ": " + this.annotationType;
    }
}
