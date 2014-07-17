package com.b5m.search.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.spring.utils.ApplicationContextUtils;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.search.sys.ContextUtils;
import com.b5m.search.sys.SysUtils;

/**
 * @description
 * 系统controller
 * @author echo
 * @time 2014年6月16日
 * @mail wuming@b5m.com
 */
@Controller
public class SysController {
	
	@RequestMapping("/sys/log")
	@ResponseBody
	public void showLog(String name, String token, Integer limit, HttpServletRequest request, HttpServletResponse response) throws Exception{
		boolean flag = ContextUtils.checkToken(token);
		if(!flag) {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getOutputStream().write("token error".getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return;
		}
		String filePath = getLogPath(name, request);
		try {
			File file = new File(filePath);
			String logs = "";
			if(file.exists()){
				InputStream inputStream = new FileInputStream(filePath);
				StringBuilder sb = new StringBuilder();
				List<String> list = IOUtils.readLines(inputStream);
				int size = list.size();
				int num = 0;
				for(int index = size - 1; index >= 0; index--){
					if(limit != null && num >= limit) break;
					sb.append(list.get(index)).append("<br/>");
					num++;
				}
				logs = sb.toString();
			}
			if(StringUtils.isEmpty(logs)){
				logs = "没有日志内容";
			}
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.getOutputStream().write(logs.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/{channel}/s/logs")
	public String _showLogs(@PathVariable("channel") String channel, String token, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			boolean flag = ContextUtils.checkToken(token);
			if(!flag) {
				request.setAttribute("error", "token error");
				return "log";
			}
			List<String> ips = new ArrayList<String>(5); 
			String server = request.getServerName();
			if(server.indexOf("stage") >= 0 || server.indexOf("prod") >= 0 || "localhost".equals(server)){
				InetAddress addr = InetAddress.getLocalHost();
				String ip = addr.getHostAddress().toString();
				ips.add(ip + ":" + request.getLocalPort());
			}else{
				CollectionTools.add(ips, StringUtils.split(ContextUtils.getProp("server_ips"), ","));
			}
			request.setAttribute("ips", ips);
			String contextPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/logs/";
			File file = new File(contextPath);
			String[] strs = file.list();
			List<String> logs = CollectionTools.newList(strs);
			logs.add("catalina.out");
			request.setAttribute("logNames", logs);
			request.setAttribute("token", token);
		} catch (Exception e) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream s = new PrintStream(out, true);
			e.printStackTrace(s);
			request.setAttribute("error", new String(out.toByteArray(), "UTF-8"));
		}
		return "log";
	}
	
	@RequestMapping("/sys/logs")
	public String showLogs(HttpServletRequest request, String token, HttpServletResponse response) throws Exception{
		return _showLogs("", token, request, response);
	}
	
	private String getLogPath(String name, HttpServletRequest request){
		String contextPath = request.getSession().getServletContext().getRealPath("/");
	    if("catalina.out".equals(name)){
	    	return contextPath.substring(0, contextPath.indexOf("webapps")) + "logs/catalina.out";
	    }
	    return contextPath + "WEB-INF/logs/" + name;
	}
	
	@RequestMapping("/sys/invoke")
	@ResponseBody
	public String invoke(String className, String methodName, String params, String paramTypes, String token, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(StringUtils.isEmpty(token)) return "token error";
		boolean flag = ContextUtils.checkToken(token);
		if(!flag) {
			return "token error";
		}
		if(StringUtils.isEmpty(className)) return "类名不能为空";
		Class<?> cls = Class.forName(className);
		Method method = SysUtils.getMethod(cls, methodName, paramTypes);
		if(method == null){
			return "method no found";
		}
		Object[] values = SysUtils.getValues(params, paramTypes);
		if(values == null){
			return "params or paramTypes is illege";
		}
		Object bean = ApplicationContextUtils.getBean(cls);
		if(bean == null){
			return "bean no found for spring with[" + cls.getName() + "]";
		}
		Object result = method.invoke(bean, values);
		if(result != null){
			result = JSONObject.toJSONString(result);
		}
		return "operator success for class[" + cls.getName() + "], method[" + method.getName() + "] and result[" + result + "]";
	}
	
}
