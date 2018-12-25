package com.tydic.traffic.tafa.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.Set;


/**
 * global validation auto configuration
 *
 * @author acer
 * @since 2017.06.14
 */
@Configuration
public class ValidationAutoConfiguration {

    private static final String VALIDATOR_BEAN_NAME = "defaultValidator";

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean(name=VALIDATOR_BEAN_NAME)
    public LocalValidatorFactoryBean defaultValidator(ApplicationContext applicationContext){
        ResourceBundleMessageSource source=new ResourceBundleMessageSource();
        source.addBasenames("validationMessages"); //TODO:多jar冲突

        LocalValidatorFactoryBean factoryBean=new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setValidationMessageSource(source);
        return factoryBean;
    }

    @Bean
    public JVaildatorAdvisor jVaildatorAdvisor(Validator validator){
        JVaildatorAdvisor advisor=new JVaildatorAdvisor();
        advisor.setValidator(validator);
        return advisor;
    }

    @Bean
    public GlobalValidationPostProcessor globalValidationPostProcessor(Environment environment,
                JVaildatorAdvisor jVaildatorAdvisor){
        GlobalValidationPostProcessor processor=new GlobalValidationPostProcessor();
        processor.setProxyTargetClass(determineProxyTargetClass(environment));
        processor.setExpression(getPointcutExpression(environment));
        processor.setjVaildatorAdvisor(jVaildatorAdvisor);

        String pkgs=environment.getProperty("tafa.validation.excludePackages");
        if (!StringUtils.isEmpty(pkgs)){
            Set<String> packages=StringUtils.commaDelimitedListToSet(pkgs);
            processor.setExcludePackages(packages);
        }


        return processor;
    }

    private static String getPointcutExpression(Environment environment){
       return environment.getProperty("tafa.validation.expression");
    }
    private static boolean determineProxyTargetClass(Environment environment) {
        Boolean value = environment.getProperty("spring.aop.proxyTargetClass", Boolean.class);
        return (value != null ? value : true);
    }
}
