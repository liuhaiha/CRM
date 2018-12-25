package com.tydic.traffic.tafa.validation;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.tydic.traffic.tafa.validation.annotation.NoValid;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 判断某个类是使用了{@code @NoValid}注解,如果是的话则该类不使用tafa框架中的通用校验组进行校验
 * 某个类是的包名称是否存在{@code excludePackages}之中,如果存在的话,则对该类不用tafa框架中的通用校验组进行校验
 * @author acer
 * @since 2017-06-23
 */
public class NoValidAnnotationClassFilter extends AnnotationClassFilter {

    private final Class<? extends Annotation> annotationType= NoValid.class;
    private  Set<String> excludePackages;

    public NoValidAnnotationClassFilter(Class<? extends Annotation> annotationType, boolean checkInherited, Set<String> excludePackages) {
        super(annotationType,checkInherited);
        this.excludePackages=excludePackages;
    }

    @Override
    public boolean matches(final Class<?> clazz) {
        if(clazz.isAnnotationPresent(annotationType)){
            return false;
        }

        if(!CollectionUtils.isEmpty(excludePackages)){
            Set<String> ret=Sets.filter(excludePackages, new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return StringUtils.startsWithIgnoreCase(clazz.getName(),input);
                }
            });

            if(!CollectionUtils.isEmpty(ret)){
                return false;
            }
        }

        return super.matches(clazz);
    }
}
