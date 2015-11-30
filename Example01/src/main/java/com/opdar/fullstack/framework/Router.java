package com.opdar.fullstack.framework;

import java.lang.reflect.Method;

public class Router {
	// 路由名称
	private String name;
	// 路由后缀
	private String suffix;
	// 对应方法
	private Method method;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		if (suffix != null && suffix.length() > 0) {
			this.suffix = suffix;
		}
	}
	
	public void invoke(RequestObject object){
		try {
			Object[] params = new Object[method.getParameterTypes().length];
			for(int i=0;i<method.getParameterTypes().length;i++){
				if(RequestObject.class.isAssignableFrom(method.getParameterTypes()[i])){
					params[i] = object;
				}else{
					params[i] = null;
				}
			}
			Object obj = method.getDeclaringClass().newInstance();
			method.invoke(obj, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Router [name=" + name + ", suffix=" + suffix + "]";
	}

}