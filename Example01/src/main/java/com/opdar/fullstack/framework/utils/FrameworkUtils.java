package com.opdar.fullstack.framework.utils;

public class FrameworkUtils {

	/**
	 * 裁剪路由名称
	 * @param router
	 * @return
	 */
    public static String trimRouter(String router) {
        int i = router.indexOf("/");
        if (i == -1) {
            return "/".concat(router);
        }
        StringBuilder builder = new StringBuilder();
        for (String r : router.split("/")) {
            if (!r.trim().equals("")) {
                builder.append(r).append("/");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
            builder.insert(0, "/");
        }
        return builder.toString();
    }
}
