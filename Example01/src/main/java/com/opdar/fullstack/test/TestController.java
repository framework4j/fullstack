package com.opdar.fullstack.test;

import com.opdar.fullstack.framework.RequestObject;
import com.opdar.fullstack.framework.RouterMapping;

@RouterMapping(value = "router",suffix = ".html")
public class TestController {
	@RouterMapping(value = "index")
	public void index(RequestObject requestObject){
		System.out.println(requestObject);
	}
}
