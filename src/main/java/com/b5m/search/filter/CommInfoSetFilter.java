package com.b5m.search.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.b5m.search.common.GlobalWebCfg;
import com.b5m.search.sys.ContextUtils;

/**
 * @description
 * 
 * @author echo
 * @time 2014年7月9日
 * @mail wuming@b5m.com
 */
public class CommInfoSetFilter implements Filter {
	private static final Log LOG = LogFactory.getLog(CommInfoSetFilter.class);
	private static final String DEFAULT_COMMINFO_PATH = "config.properties";
	private static final String DEFAULT_CHANNEL_CONFIG = "classpath:channels.xml";
	private static final String CATEGORY_REL_PATH = "search-category.json";
	private static String[] nocludeExt;
	private static Properties properties = null;

	public void destroy(){
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if(!filter(req)){
			setUcenterHttpUrl(req);
			
			setCommInfo(req);
		}
		filterChain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException{
		try {
			initNocludeExt(arg0);
			initSearchContext(arg0);
		} catch (Exception e) {
			if (LOG.isErrorEnabled())
				LOG.debug("---------------------init exception exception type is[" + e.getClass().getSimpleName()
						+ "], error message is [" + e.getMessage() + "]-------------------------");
		}
	}
	
	private boolean filter(HttpServletRequest req){
		String requestUrl = req.getRequestURL().toString();
		for(String ext : nocludeExt){
			if(requestUrl.endsWith(ext)){
				return true;
			}
		}
		return false;
	}
	
	private void initNocludeExt(FilterConfig arg0){
		String nocludeExtStr = arg0.getInitParameter("nocludeExt");
		if(!StringUtils.isEmpty(nocludeExtStr)){
			nocludeExt = StringUtils.split(nocludeExtStr, ",");
		}else{
			nocludeExt = new String[0];
		}
	}

	private void initSearchContext(FilterConfig arg0) throws ServletException, IOException{
		String channelsPath = arg0.getInitParameter("ChannelPath");
		if (StringUtils.isEmpty(channelsPath)) {
			channelsPath = DEFAULT_CHANNEL_CONFIG;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("---------------------start init channels file[" + channelsPath + "]------------------");
		}
		InputStream inputStream = null;
		if (channelsPath.startsWith("classpath:")) {
			inputStream = CommInfoSetFilter.class.getClassLoader().getResourceAsStream(channelsPath.substring("classpath:".length()));
		} else {
			String filePath = arg0.getServletContext().getRealPath("/") + channelsPath;
			try {
				inputStream = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				throw new ServletException("file:[" + channelsPath + "] not exists");
			}
		}
		initSysProp();
		ContextUtils.init(getCategoryRel(), IOUtils.toString(inputStream), properties);
	}

	private void initSysProp() throws IOException {
		String commInfoProPath = DEFAULT_COMMINFO_PATH;
		InputStream inputStream = CommInfoSetFilter.class.getClassLoader().getResourceAsStream(commInfoProPath);
		properties = new Properties();
		properties.load(inputStream);
	}

	private void setCommInfo(HttpServletRequest req) {
		req.setAttribute("adRecordUrl", ContextUtils.getProp("adRecordUrl"));
		String serverPath = getServerpath(req);
		req.setAttribute("serverPath", serverPath);
		req.setAttribute("timeVersion", ContextUtils.getVersion());
		req.setAttribute("min", ContextUtils.getProp("min"));
		req.setAttribute("searchApiRecommandUrl", "http://" + ContextUtils.getProp("search.domain") + ContextUtils.getProp("search.api.recommand.url"));
	}

	private JSONObject getCategoryRel() throws IOException {
		InputStream inputStream = CommInfoSetFilter.class.getClassLoader().getResourceAsStream(CATEGORY_REL_PATH);
		String categoryRel = IOUtils.toString(inputStream);
		JSONObject categoryRelJson = JSONObject.parseObject(categoryRel);
		return categoryRelJson;
	}

	private String getServerpath(HttpServletRequest req) {
		if (req.getServerPort() != 80) {
			String requestUrl = req.getRequestURL().toString();
			int index = requestUrl.indexOf("/s/");
			if (index > 0 && requestUrl.indexOf("WEB-INF") < 0) {//有个s渠道存在
				if(requestUrl.indexOf("/s/", index + 2) > 0){
					index = requestUrl.indexOf("/s/", index + 2);
				}
				return requestUrl.substring(0, index);
			}
			if (requestUrl.indexOf("jsp") < 0) {
				return "";
			}
			String channel = "";
			int indexWebinf = requestUrl.indexOf("WEB-INF") + "/WEB-INF".length();
			if (indexWebinf > 0) {
				int lastWebIndex = requestUrl.indexOf("/", indexWebinf);
				if (lastWebIndex < 1)
					return "";
				channel = requestUrl.substring(indexWebinf, requestUrl.indexOf("/", indexWebinf));
			}
			req.setAttribute("channel", channel);
			return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/" + channel;
		}
		return req.getScheme() + "://" + req.getServerName();
	}

	private void setUcenterHttpUrl(HttpServletRequest req) {
		req.setAttribute("loginAndRegisterURL", GlobalWebCfg.getUcenterHttpUrl(req));
	}
}