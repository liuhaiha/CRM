package com.tydic.traffic.tafa.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 *  使用hibernate validator校验器来校验带有{@code @Controller}注解的类，该类中标注了
 *  {@code @RequestMapping}注解的方法
 *
 * @author acer
 * @since 2017-06-14
 */
public class GlobalValidationPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {
    private static final long serialVersionUID = 5227124829640294414L;

    private static final Logger logger= LoggerFactory.getLogger(GlobalValidationPostProcessor.class);

    private Class<? extends Annotation> classAnnotationType = Controller.class;

    private Class<? extends Annotation> methodAnnotationType= RequestMapping.class;

    private Set<String> excludePackages;

    private String expression;

    private JVaildatorAdvisor jVaildatorAdvisor;

    /**
     * 标识那些包不被通用注解扫描,避免对其进行校验
     * @param excludePackages
     */
    public void setExcludePackages(Set<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setjVaildatorAdvisor(JVaildatorAdvisor jVaildatorAdvisor) {
        this.jVaildatorAdvisor = jVaildatorAdvisor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isEmpty(this.expression)){
            if(logger.isDebugEnabled()){
                logger.debug("参数校验拦截器,切点表达式为空,将使用{}注解来表示切点",this.classAnnotationType.getClass());
            }
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(this.classAnnotationType, true,
                    this.methodAnnotationType,true,this.excludePackages);
            this.advisor = new DefaultPointcutAdvisor(pointcut, jVaildatorAdvisor);
            return;
        }
        if(logger.isDebugEnabled()){
            logger.debug("参数校验拦截器,切点表达式不为空,切点表达式为{}",this.expression);
        }
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        this.advisor = new DefaultPointcutAdvisor(pointcut, jVaildatorAdvisor);
    }
}
