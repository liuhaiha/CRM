package com.tydic.traffic.tafa.validation.support;

import com.tydic.traffic.tafa.validation.annotation.Mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MobileImpl implements ConstraintValidator<Mobile, String> {

	private static final Pattern pattern = Pattern.compile("^(1(([35][0-9])|(47)|[8][01236789]))\\d{8}$");

	private boolean required ;

	@Override
	public void initialize(Mobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (null == value || "".equals(value.trim()) ) {
			return !required;
		}
		return pattern.matcher(value).find();
	}

	public static void main(String[] args) {
		Matcher matcher = pattern.matcher("lyh");
		System.out.println(matcher.find());
	}

}
