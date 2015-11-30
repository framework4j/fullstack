package com.opdar.fullstack.framework.utils;

import java.util.HashMap;
import java.util.Map;

import com.opdar.fullstack.framework.RequestObject;

public class URIUtils {
	public static RequestObject parse(String uri){
		int start = uri.indexOf("?");
		String router = uri;
		String param = null;
		Map<String, String> values = new HashMap<String, String>();
		if(start > 0){
			router = uri.substring(0,start);
			if(uri.length() != start+1)
			param = uri.substring(start+1);
		}
		if(param != null){
			String[] params = param.split("&");
			for(String p:params){
				String[] kv = p.split("=");
				String key = kv[0];
				String value = null;
				if(kv.length == 2){
					value = kv[1];
				}
				values.put(key, value);
			}
		}
		RequestObject requestObject = new RequestObject();
		requestObject.setValues(values);
		requestObject.setRouterName(router);
		return requestObject;
	}

}
