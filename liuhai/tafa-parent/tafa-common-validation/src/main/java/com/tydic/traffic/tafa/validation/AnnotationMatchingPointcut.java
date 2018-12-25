package com.tydic.traffic.tafa.validation;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 *
 * @author acer
 * @since 2017-06-23
 */
 class AnnotationMatchingPointcut implements Pointcut {

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    /**
     * 用于查找类和方法是否存在注解，用于创建AOP动态代理类
     * @param classAnnotationType   类的注解
     * @param checkClazzInherited   类的注解是否支持继承
     * @param methodAnnotationType  方法的注解
     * @param checkMethodInherited  方法的注解是否支持继承
     * @param excludePackages
     */
    public AnnotationMatchingPointcut(
            Class<? extends Annotation> classAnnotationType, boolean checkClazzInherited,
            Class<? extends Annotation> methodAnnotationType, boolean checkMethodInherited, Set<String> excludePackages) {

        Assert.isTrue((classAnnotationType != null || methodAnnotationType != null),
                "Either Class annotation type or Method annotation type needs to be specified (or both)");

        if (classAnnotationType != null) {
            this.classFilter = new NoValidAnnotationClassFilter(classAnnotationType,checkClazzInherited,excludePackages);
        } else {
            this.classFilter = ClassFilter.TRUE;
        }

        if (methodAnnotationType != null) {
            this.methodMatcher = new AnnotationMethodMatcher(methodAnnotationType,checkMethodInherited);
        } else {
            this.methodMatcher = MethodMatcher.TRUE;
        }
    }

    @Override
    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnotationMatchingPointcut)) {
            return false;
        }
        AnnotationMatchingPointcut that = (AnnotationMatchingPointcut) other;
        return ObjectUtils.nullSafeEquals(that.classFilter, this.classFilter) &&
                ObjectUtils.nullSafeEquals(that.methodMatcher, this.methodMatcher);
    }

    @Override
    public int hashCode() {
        int code = 17;
        if (this.classFilter != null) {
            code = 37 * code + this.classFilter.hashCode();
        }
        if (this.methodMatcher != null) {
            code = 37 * code + this.methodMatcher.hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        return "AnnotationMatchingPointcut: " + this.classFilter + ", " +this.methodMatcher;
    }
}
