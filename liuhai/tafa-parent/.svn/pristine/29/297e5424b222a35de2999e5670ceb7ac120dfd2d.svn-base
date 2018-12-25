package com.tydic.traffic.tafa.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import javax.validation.Constraint;
import javax.validation.Validator;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * 
 * ClassName:  <br/>
 * Function:  <br/>
 * date: 2013年9月12日 下午5:54:48 <br/>
 *
 * JVaildator
 * 支持JSR 303 参数校验
 *
 * @author acer
 * @version
 * @since 2017.06.12
 */
public class ValidatorHelper {

	private static ParameterNameDiscoverer paramNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<ClassLoader, ClassPool>(); // ClassLoader

	private static Validator validator = null; // ClassLoader

	private static final ThreadLocal<Map<String, String>> LOCAL = new ThreadLocal<Map<String, String>>() {

		@Override
		protected Map<String, String> initialValue() {
			return new HashMap<String, String>();
		}
	};

	public static Map<String, String> getContext() {
		return LOCAL.get();
	}

	public static void removeContext() {
		LOCAL.remove();
	}

	/**
	 * 
	 * getMethodParameterBean:(将方法中使用了JSR303 注解的参数封装成一个JavaBean). <br/>
	 * 
	 * @author acer
	 * @param clazz
	 * @param method
	 * @param args
	 * @return
	 * @since 1.0
	 */
	public static Object getMethodParameterBean(Class<?> clazz, Method method, Object[] args) {
		if (!hasConstraintParameter(method)) { // 判断方法参数中是否包含 JSR303 注解
			return null;
		}
		try {
			String[] paramNames = paramNameDiscoverer.getParameterNames(method);

			String upperName = toUpperMethoName(method.getName());
			String parameterSimpleName = upperName + "Parameter";
			String parameterClassName = clazz.getName() + "$" + parameterSimpleName;
			Class<?> parameterClass;
			try {
				parameterClass = (Class<?>) Class.forName(parameterClassName, true, clazz.getClassLoader());
			}
			catch (ClassNotFoundException e) {

				// 首次调用时parameterClass未生成
				// 使用javassist生成对应的class文件
				ClassPool pool = getClassPool(clazz.getClassLoader());
				CtClass ctClass = pool.makeClass(parameterClassName);
				ClassFile classFile = ctClass.getClassFile();
				classFile.setVersionToJava5();
				ctClass.addConstructor(CtNewConstructor.defaultConstructor(pool.getCtClass(parameterClassName)));
				// 获取所有的 parameter fields
				Class<?>[] parameterTypes = method.getParameterTypes();
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				for (int i = 0; i < parameterTypes.length; i++) {
					Class<?> type = parameterTypes[i];
					Annotation[] annotations = parameterAnnotations[i];
					AnnotationsAttribute attribute = new AnnotationsAttribute(classFile.getConstPool(),
							AnnotationsAttribute.visibleTag);
					for (Annotation annotation : annotations) {
						if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
							javassist.bytecode.annotation.Annotation ja = new javassist.bytecode.annotation.Annotation(
									annotation.annotationType().getName(), classFile.getConstPool());
							Method[] members = annotation.annotationType().getMethods();
							for (Method member : members) {
								if (Modifier.isPublic(member.getModifiers()) && member.getParameterTypes().length == 0
										&& member.getDeclaringClass() == annotation.annotationType()) {
									Object value = member.invoke(annotation, new Object[0]);
									if (value != null && !value.equals(member.getDefaultValue())) {
										MemberValue memberValue = createMemberValue(classFile.getConstPool(),
												pool.get(member.getReturnType().getName()), value);
										ja.addMemberValue(member.getName(), memberValue);
									}
								}
							}
							attribute.addAnnotation(ja);
						}
					}
					// 添加字段
					String fieldName = paramNames[i];
					CtField ctField = CtField.make("public " + type.getCanonicalName() + " " + fieldName + ";",
							pool.getCtClass(parameterClassName));

					ctField.getFieldInfo().addAttribute(attribute);
					ctClass.addField(ctField);
				}
				parameterClass = ctClass.toClass(); // 生成 parameterClass
			}
			Object parameterBean = parameterClass.newInstance();
			for (int i = 0; i < args.length; i++) {
				Field field = parameterClass.getField(paramNames[i]);
				field.set(parameterBean, args[i]);
			}
			return parameterBean;
		}
		catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean hasConstraintParameter(Method method) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (parameterAnnotations != null && parameterAnnotations.length > 0) {
			for (Annotation[] annotations : parameterAnnotations) {
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static String toUpperMethoName(String methodName) {
		return methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	}

	// Copy from
	// javassist.bytecode.annotation.Annotation.createMemberValue(ConstPool,
	// CtClass);
	private static MemberValue createMemberValue(ConstPool cp, CtClass type, Object value) throws NotFoundException {
		MemberValue memberValue = javassist.bytecode.annotation.Annotation.createMemberValue(cp, type);
		if (memberValue instanceof BooleanMemberValue)
			((BooleanMemberValue) memberValue).setValue((Boolean) value);
		else if (memberValue instanceof ByteMemberValue)
			((ByteMemberValue) memberValue).setValue((Byte) value);
		else if (memberValue instanceof CharMemberValue)
			((CharMemberValue) memberValue).setValue((Character) value);
		else if (memberValue instanceof ShortMemberValue)
			((ShortMemberValue) memberValue).setValue((Short) value);
		else if (memberValue instanceof IntegerMemberValue)
			((IntegerMemberValue) memberValue).setValue((Integer) value);
		else if (memberValue instanceof LongMemberValue)
			((LongMemberValue) memberValue).setValue((Long) value);
		else if (memberValue instanceof FloatMemberValue)
			((FloatMemberValue) memberValue).setValue((Float) value);
		else if (memberValue instanceof DoubleMemberValue)
			((DoubleMemberValue) memberValue).setValue((Double) value);
		else if (memberValue instanceof ClassMemberValue)
			((ClassMemberValue) memberValue).setValue(((Class<?>) value).getName());
		else if (memberValue instanceof StringMemberValue)
			((StringMemberValue) memberValue).setValue((String) value);
		else if (memberValue instanceof EnumMemberValue)
			((EnumMemberValue) memberValue).setValue(((Enum<?>) value).name());
		/* else if (memberValue instanceof AnnotationMemberValue) */
		else if (memberValue instanceof ArrayMemberValue) {
			CtClass arrayType = type.getComponentType();
			int len = Array.getLength(value);
			MemberValue[] members = new MemberValue[len];
			for (int i = 0; i < len; i++) {
				members[i] = createMemberValue(cp, arrayType, Array.get(value, i));
			}
			((ArrayMemberValue) memberValue).setValue(members);
		}
		return memberValue;
	}

	private static ClassPool getClassPool(ClassLoader loader) {
		if (loader == null)
			return ClassPool.getDefault();

		ClassPool pool = POOL_MAP.get(loader);
		if (pool == null) {
			pool = new ClassPool(true);
			pool.appendClassPath(new LoaderClassPath(loader));
			POOL_MAP.put(loader, pool);
		}
		return pool;
	}

	public static Validator getValidator() {
		return validator;
	}

	public static void setValidator(Validator validator) {
		ValidatorHelper.validator = validator;
	}

}
