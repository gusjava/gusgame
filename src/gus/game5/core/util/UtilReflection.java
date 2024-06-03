package gus.game5.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.t.T;

public class UtilReflection {
	
	/*
	 * FIND METHOD
	 */
	
	public static Method findMethod(Class<?> c, String methodName) {
		try {
			return c.getMethod(methodName);
		} catch (NoSuchMethodException e) {}
		return null;
	}
	
	public static Method findMethod(Class<?> c, String methodName, Class<?>[] paramTypes) {
		try {
			return c.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {}
		return null;
	}
	
	/*
	 * FIND METHOD LAZY
	 */
	
	public static Method findMethodLazy(Class<?> c, String methodName, Class<?>[] paramTypes) {
		try {
			return c.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {}
		
		return findMethodLazy(c, methodName, paramTypes.length);
	}
	
	public static Method findMethodLazy(Class<?> c, String methodName, int paramNumber) {
		Method[] mm = c.getMethods();
		List<Method> list = UtilList.findAll(mm, m -> m.getName().equals(methodName) && m.getParameterCount()==paramNumber);
		return list.size()==1 ? list.get(0) : null;
	}
	
	
	/*
	 * FIND GETTER / SETTER METHOD
	 */
	
	public static Method findGetterMethod(Class<?> c, String fieldName) {
		String fieldNameC0 = UtilString.upperC0(fieldName);
		Method m = findMethod(c, "get" + fieldNameC0);
		if(m!=null) return m;
		return findMethod(c, "is" + fieldNameC0);
	}
	
	public static Method findSetterMethod(Class<?> c, String fieldName) {
		String fieldNameC1 = UtilString.upperC1(fieldName);
		return findMethodLazy(c, "set" + fieldNameC1, 1);
	}
	
	
	/*
	 * FIND GETTER METHODS BY FIELD
	 */
	
	public static Map<String, Method> findGetterMethodsByField(Class<?> c) {
		Map<String, Method> map = new HashMap<>();
		Method[] mm = c.getMethods();
		for(Method m : mm) if(m.getParameterCount()==0) {
			String name = m.getName();
			if(name.matches("get[A-Z].*")) {
				String fieldName = UtilString.lowerC0(name.substring(3));
				map.put(fieldName, m);
			}
			else if(name.matches("is[A-Z].*")) {
				String fieldName = UtilString.lowerC0(name.substring(2));
				map.put(fieldName, m);
			}
		}
		return map;
	}
	
	/*
	 * FIND SETTER METHODS BY FIELD
	 */
	
	public static Map<String, Method> findSetterMethodsByField(Class<?> c) {
		Map<String, Method> map = new HashMap<>();
		Method[] mm = c.getMethods();
		for(Method m : mm) if(m.getParameterCount()==1) {
			String name = m.getName();
			if(name.matches("set[A-Z].*")) {
				String fieldName = UtilString.lowerC0(name.substring(3));
				map.put(fieldName, m);
			}
		}
		return map;
	}
	
	
	/*
	 * CALL METHOD
	 */
	
	public static Object callMethod(Object data, String methodName, List<Object> paramList) {
		int nb = paramList.size();
		Object[] paramArray = new Object[nb];
		Class<?>[] paramTypes = new Class<?>[nb];
		for(int i=0;i<nb;i++) {
			paramArray[i] = paramList.get(i);
			paramTypes[i] = paramList.get(i).getClass();
		}
		Method m_ = findMethodLazy(data.getClass(), methodName, paramTypes);
		if(m_==null) throw new TechnicalException("Method not found for name: "+methodName);
		
		try {
			return m_.invoke(data, paramArray);
		} catch (Exception e) {
			throw new TechnicalException("Failed to invoke method: "+m_.getName(), e);
		} 
	}
	
	public static Object callMethod(Object data, String methodName) {
		Method m_ = findMethod(data.getClass(), methodName);
		if(m_==null) throw new TechnicalException("Method not found for name: "+methodName);

		try {
			return m_.invoke(data);
		} catch (Exception e) {
			throw new TechnicalException("Failed to invoke method: "+m_.getName(), e);
		} 
	}
	
	
	/*
	 * GET FIELD VALUE
	 */
	
	public static Object getFieldValue(Object data, String fieldName) {
		Class<?> c = data.getClass();
		Method getterMethod_ = findGetterMethod(c, fieldName);
		if(getterMethod_==null) throw new TechnicalException("Getter method not found for field: "+fieldName);
		
		try {
			return getterMethod_.invoke(data);
		} catch (Exception e) {
			throw new TechnicalException("Failed to invoke getterMethod: "+getterMethod_.getName(), e);
		} 
	}
	
	/*
	 * SET FIELD VALUE
	 */
	
	public static void setFieldValue(Object data, String fieldName, Object value) {
		Class<?> c = data.getClass();
		Method setterMethod_ = findSetterMethod(c, fieldName);
		if(setterMethod_==null) throw new TechnicalException("Getter method not found for field: "+fieldName);
		
		try {
			setterMethod_.invoke(data, value);
		} catch (Exception e) {
			throw new TechnicalException("Failed to invoke setterMethod: "+setterMethod_.getName(), e);
		} 
	}
	
	
	
	
	/*
	 * BUILD DATA PROVIDER
	 */
	
	
	public static <D> T<D,?> buildDataProvider(Class<D> c, String fieldName) {
		Method getterMethod_ = findGetterMethod(c, fieldName);
		if(getterMethod_==null) throw new TechnicalException("Getter method not found for field: "+fieldName);
		
		return buildDataProvider(getterMethod_);
	}
	
	
	public static <D> T<D,?> buildDataProvider(final Method method) {
		return data -> {
			try {
				return method.invoke(data);
			} catch (Exception e) {
				throw new TechnicalException("Failed to invoke method: "+method.getName());
			} 
		};
	}
	
	public static <D> Map<String, T<D,?>> buildDataProviderByField(Class<D> c) {
		Map<String, Method> map0 = findGetterMethodsByField(c);
		Map<String, T<D,?>> map = new HashMap<>();
		Iterator<String> it = map0.keySet().iterator();
		while(it.hasNext()) {
			String fieldName = it.next();
			Method m = map0.get(fieldName);
			map.put(fieldName, buildDataProvider(m));
		}
		return map;
	}
	
	
	/*
	 * EXTRACT CONST
	 */
	
	public static List<String> extractConst(Class<?> c) {
		List<String> list = new ArrayList<>();
		for (Field f : c.getDeclaredFields()) {
			if (f.getType().equals(String.class)) list.add(f.getName());
		}
		return list;
	}
}
