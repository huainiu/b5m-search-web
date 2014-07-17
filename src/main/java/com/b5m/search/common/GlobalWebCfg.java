package com.b5m.search.common;

import javax.servlet.http.HttpServletRequest;

public class GlobalWebCfg {
	private static String local = "localhost";

	private static String stage = "ucenter.stage.bang5mai.com";

	private static String tiaoStage = "tiao.stage.bang5mai.com";

	private static String prod = "ucenter.prod.bang5mai.com";

	private static String tiaoProd = "tiao.prod.bang5mai.com";

	private static String b5m = "ucenter.b5m.com";

	private static String tiaoB5m = "tiao.b5m.com";

	public static String getTiaoUrl(String hostName) {
		if (isLocal(hostName)) {
			return local;
		}
		if (hostName.indexOf("stage.bang5mai.com") > -1) {
			return tiaoStage;
		}

		if (hostName.indexOf("prod.bang5mai.com") > -1) {
			return tiaoProd;
		}

		if (hostName.indexOf("b5m.com") > -1) {
			return tiaoB5m;
		}

		return hostName;
	}

	public static String getUcenterUrl(String hostName) {
		if (isLocal(hostName)) {
			return stage;
		}
		if (hostName.indexOf("stage.bang5mai.com") > -1) {
			return stage;
		}

		if (hostName.indexOf("prod.bang5mai.com") > -1) {
			return prod;
		}

		if (hostName.indexOf("b5m.com") > -1) {
			return b5m;
		}

		return hostName;
	}

	private static boolean isLocal(String hostName) {
		return hostName.startsWith("127.0.0.1")
				|| hostName.startsWith("localhost");
	}

	public static String getTiaoHttpUrl(HttpServletRequest request) {
		return request.getScheme() + "://"
				+ getTiaoUrl(request.getServerName()) + "/";
	}

	public static String getUcenterHttpUrl(HttpServletRequest request) {
		return request.getScheme() + "://"
				+ getUcenterUrl(request.getServerName()) + "/";
	}

}