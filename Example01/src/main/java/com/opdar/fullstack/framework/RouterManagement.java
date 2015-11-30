package com.opdar.fullstack.framework;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.opdar.fullstack.framework.utils.ComponentUtils;
import com.opdar.fullstack.framework.utils.FrameworkUtils;
import com.opdar.fullstack.framework.utils.URIUtils;

public class RouterManagement {
	private static Map<String, Router> routers = new HashMap<String, Router>();
	private static Set<String> defaultPages = new LinkedHashSet<String>();

	public static void addDefaultRouter(String page) {
		defaultPages.add(page);
	}

	public static void addRouter(Router... routers) {
		if (routers == null || routers.length == 0) {
			throw new RuntimeException("Router can not be null...");
		}
		StringBuilder builder = new StringBuilder();
		Router lastRouter = routers[routers.length - 1];
		String suffix = lastRouter.getSuffix();
		if (suffix == null) {
			suffix = routers[0].getSuffix();
		}
		for (Router router : routers) {
			builder.append(FrameworkUtils.trimRouter(router.getName()));
		}
		builder.append(suffix);
		lastRouter.setSuffix(suffix);
		RouterManagement.routers.put(builder.toString(), lastRouter);
	}

	public static Router getRouter(String uri) {
		String uri2 = FrameworkUtils.trimRouter(uri);
		if (uri.lastIndexOf("/") == uri.length() - 1 || !routers.containsKey(uri2)) {
			uri2 += "/";
			for (String page : defaultPages) {
				String _uri = uri2.concat(page);
				if (routers.containsKey(_uri)) {
					uri2 = _uri;
					break;
				}
			}
		}
		return routers.get(uri2);
	}

	public static void main(String[] args) {
		ComponentUtils.findRouters("com.opdar");
		RouterManagement.addDefaultRouter("index.html");
		RequestObject object = URIUtils.parse("router/index.html?p1=v2&p2=&p3=v4");
		Router router = RouterManagement.getRouter(object.getRouterName());
		router.invoke(object);
	}
}