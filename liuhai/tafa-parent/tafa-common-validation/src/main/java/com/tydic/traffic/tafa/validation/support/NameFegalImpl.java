package com.tydic.traffic.tafa.validation.support;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tydic.traffic.tafa.validation.annotation.NameFegal;
import org.springframework.util.StringUtils;


public class NameFegalImpl implements ConstraintValidator<NameFegal, String>{
	
	String regexp ="^[0-9a-zA-Z]+$";

	@Override
	public void initialize(NameFegal constraintAnnotation) {
		
		if(!StringUtils.isEmpty(constraintAnnotation.regexp())){
			regexp = constraintAnnotation.regexp();
		}
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(value)){
			return true;
		}
		return Pattern.compile(regexp).matcher(value).matches();
	}

	public static void main(String[] args) {
		String regexp = "^[0-9a-zA-Z]+$";
		
		System.out.println(Pattern.compile(regexp).matcher("!!!!").matches());
	}
}

