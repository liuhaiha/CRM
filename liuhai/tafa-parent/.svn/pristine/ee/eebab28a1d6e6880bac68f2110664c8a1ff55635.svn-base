package com.tydic.traffic.tafa.validation.support;


import com.tydic.traffic.tafa.validation.ValidatorHelper;
import com.tydic.traffic.tafa.validation.annotation.ArrayValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;


public class ArrayValidImpl implements ConstraintValidator<ArrayValid, Object> {

	private String key;

	private Pattern pattern;

	private boolean required;

	@Override
	public void initialize(ArrayValid constraintAnnotation) {
		key = constraintAnnotation.value();
		required = constraintAnnotation.required();
		pattern = Pattern.compile(constraintAnnotation.pattern());
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (null == value) {
			return !required;
		}

		if (value.getClass().isArray()) {

			String clazzType = value.getClass().getComponentType().toString();
			
			if ("int".equals(clazzType)) {
				int[] args = (int[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
			} else if ("byte".equals(clazzType)) {
				byte[] args = (byte[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}

			} else if ("char".equals(clazzType)) {
				char[] args = (char[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}

			} else if ("short".equals(clazzType)) {
				short[] args = (short[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}

			} else if ("long".equals(clazzType)) {
				long[] args = (long[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
			} else if ("float".equals(clazzType)) {
				float[] args = (float[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
			} else if ("double".equals(clazzType)) {
				double[] args = (double[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
			} else if ("boolean".equals(clazzType)) {
				boolean[] args = (boolean[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
			}else{
				Object[] args = (Object[]) value;
				for (int i = 0; i < args.length; i++) {
					validObject(i + "_" + key, args[i], context);
				}
				
			}

		} else if (Collection.class.isInstance(value)) {

			Collection<?> collection = (Collection<?>) value;
			Object[] args = collection.toArray();
			for (int i = 0; i < args.length; i++) {
				validObject(i + "_" + key, args[i], context);
			}

		}
		return ValidatorHelper.getContext().size() <= 0;
	}

	private void validObject(String name, Object value,
			ConstraintValidatorContext context) {

		if (Number.class.isInstance(value)
				|| CharSequence.class.isInstance(value)
				|| Character.class.isInstance(value)) {

			if (!pattern.matcher(value.toString()).find()) {
				ValidatorHelper.getContext().put(name,
						context.getDefaultConstraintMessageTemplate());
			}

		} else {

			Set<ConstraintViolation<Object>> set = ValidatorHelper
					.getValidator().validate(value);
			if (null != set && set.size() > 0) {
				for (Object obj : set) {
					ConstraintViolation<?> constraintViolation = (ConstraintViolation<?>) obj;
					ValidatorHelper.getContext().put(
							name + "." + constraintViolation.getPropertyPath(),
							constraintViolation.getMessage());
				}
			}

		}

	}

}
