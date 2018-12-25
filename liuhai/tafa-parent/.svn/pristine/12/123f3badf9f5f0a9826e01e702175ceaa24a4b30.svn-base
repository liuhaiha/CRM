package com.tydic.traffic.tafa.validation;

import com.tydic.traffic.tafa.validation.annotation.BeanValid;
import com.tydic.traffic.tafa.validation.annotation.NoValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


/**
 * 参数校验Advisor
 * @author acer
 * @since 2017.06.14
 */
public class JVaildatorAdvisor implements MethodBeforeAdvice {
	private static final Logger logger= LoggerFactory.getLogger(JVaildatorAdvisor.class);

	private final Class<? extends Annotation> annotationType= NoValid.class;

	private Validator validator; // JSR303 校验器

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
		ValidatorHelper.setValidator(validator);
	}

	/**
	 * 方法参数校验.
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {

		if (logger.isDebugEnabled()) {
			logger.debug("start to validate method => {} args", method.toString());
		}
		if (args != null && args.length == 0) {
			return;
		}
		if (method.isAnnotationPresent(annotationType)) {
			return;
		}

		Set<ConstraintViolation<Object>> constraintViolations = new HashSet<ConstraintViolation<Object>>();

		try {
			// 获取参数Bean
			Object parameterBean = ValidatorHelper.getMethodParameterBean(target.getClass(), method, args);
			if (parameterBean != null) {
				Set<ConstraintViolation<Object>> errorSet = validator.validate(parameterBean, Default.class);
				if (null != errorSet && errorSet.size() > 0) {
					constraintViolations.addAll(errorSet);
				}
			}
			// hasParamError(set); //判断是否有错
			// 校验@BeanValid 标注的JavaBean
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			if (parameterAnnotations == null || parameterAnnotations.length == 0) {
				return;
			}

			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] annotations = parameterAnnotations[i];
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().equals(BeanValid.class) && args[i] != null) {
						BeanValid beanValid = (BeanValid) annotation;
						Set<ConstraintViolation<Object>> errorSet = validator.validate(args[i], beanValid.groups());
						if (null != errorSet && errorSet.size() > 0) {
							constraintViolations.addAll(errorSet);
						}
					}
				}
			}

			if(!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			}

		} finally {
			ValidatorHelper.removeContext();
		}

	}

}
