package com.opdar.fullstack.framework.utils;

import com.opdar.fullstack.framework.utils.finders.ClassFinder;

public class ComponentUtils {
	
	public static void findRouters(final String packageName){
		ResourceUtils.find(new ClassFinder(packageName));
	}
}
