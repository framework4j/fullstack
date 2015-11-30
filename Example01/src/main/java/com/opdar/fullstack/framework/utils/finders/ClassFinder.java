package com.opdar.fullstack.framework.utils.finders;

import java.lang.reflect.Method;

import com.opdar.fullstack.framework.Router;
import com.opdar.fullstack.framework.RouterManagement;
import com.opdar.fullstack.framework.RouterMapping;
import com.opdar.fullstack.framework.utils.ResourceUtils.FileFinder;

public class ClassFinder implements FileFinder{

	String packageName = null;

	public ClassFinder(String packageName) {
		this.packageName = packageName;
	}

	public String suffix() {
		return ".class";
	}

	public String getPackageName() {
		return packageName;
	}

	public void call(String packageName, String file) throws RuntimeException {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> clz = loader.loadClass(packageName+file);
			RouterMapping mapping = clz.getAnnotation(RouterMapping.class);
			Router r1 = null;
			if(mapping != null){
				r1 = new Router();
				r1.setName(mapping.value());
				r1.setSuffix(mapping.suffix());
			}
			//获取类中所有方法
			for(Method method:clz.getDeclaredMethods()){
				RouterMapping methodMapping = method.getAnnotation(RouterMapping.class);
				if(methodMapping != null){
					Router r2 = new Router();
					r2.setName(methodMapping.value());
					r2.setSuffix(methodMapping.suffix());
					//如果方法上有路由映射，则生成路由
					if(r2 != null){
						method.setAccessible(true);
						r2.setMethod(method);
						if(r1 != null){
							RouterManagement.addRouter(r1,r2);
						}else{
							RouterManagement.addRouter(r2);
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
