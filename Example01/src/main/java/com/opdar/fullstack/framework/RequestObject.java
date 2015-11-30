package com.opdar.fullstack.framework;

import java.util.HashMap;
import java.util.Map;

public class RequestObject {

	private Map<String, String> values = new HashMap<String, String>();
	private String routerName = null;
	public void setValues(Map<String, String> values) {
		if(values != null)
			this.values.putAll(values);
	}

	public Map<String, String> getValues() {
		return values;
	}

	public Integer getIntValue(String key){
		String value = null;
		if(values.containsKey(key)){
			value = values.get(key);
		}
		if(value != null){
			return Integer.valueOf(value);
		}
		return null;
	}

	public Double getDoubleValue(String key){
		String value = null;
		if(values.containsKey(key)){
			value = values.get(key);
		}
		if(value != null){
			return Double.valueOf(value);
		}
		return null;
	}
	
	public String getStringValue(String key){
		String value = null;
		if(values.containsKey(key)){
			value = values.get(key);
		}
		return value;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	@Override
	public String toString() {
		return "RequestObject [values=" + values + ", routerName=" + routerName + "]";
	}

}
