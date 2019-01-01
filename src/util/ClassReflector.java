package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClassReflector {
	/*
	 * 根据类名得到类对象
	 */
	public static Class<?> getClass(String className) throws ClassNotFoundException {
		Class<?> cls = Class.forName(className);
		return cls;
	}

	/*
	 * 根据类对象和方法对象，执行没有参数的方法
	 */
	public static String executeMethod(Class<?> cls, Method method)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		String result = null;
		Object object = method.invoke(cls.newInstance());
		if (object instanceof String) {
			result = (String) object;
		}
		return result;
	}

	/*
	 * 根据类名和方法名还有参数，执行带有参数的方法
	 */
	public static String executeMethod(String className, String methodName, Object...args)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		String result = null;
		Class<?> cls = getClass(className);
		Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		Object object = method.invoke(cls.newInstance(), args);
		if (object instanceof String) {
			result = (String) object;
		}
		return result;
	}

	/*
	 * 通过类对象和方法名，执行对应方法
	 */
	public static String executeMethod(Class<?> cls, String methodName) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Method method = cls.getMethod(methodName);
		return executeMethod(cls, method);
	}

	/*
	 * 根据类名和方法名，执行对应的类的方法
	 */
	public static String executeMethod(String className, String methodName)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> cls = getClass(className);
		return executeMethod(cls, methodName);
	}

}
