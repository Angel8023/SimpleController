package util;

import java.lang.reflect.Field;
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
	public static String executeMethod(String className, String methodName, Object... args)
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

	/*
	 * 根据对象、属性名和属性值，给对应属性赋值
	 */
	public static void setField(Object object, String name, Object obj) {
		try {						
			Field field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取某个对象，某个属性的值（只知道object 对象和属性名）
	 * 通过调用该对象的相应get函数来获取属性值
	 * */
	public static Object getValueByName(Object obj, String name){
		 try {    
	           String firstLetter = name.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + name.substring(1);  	           
	           Method method = obj.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(obj, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	    	   e.printStackTrace();
	           return null;    
	       }    
	} 	
}
