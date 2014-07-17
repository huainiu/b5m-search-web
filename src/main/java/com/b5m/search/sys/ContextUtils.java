package com.b5m.search.sys;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.BeanTools;
import com.b5m.base.common.utils.DateTools;

/**
 * @description
 * 初始化上下文
 * @author echo
 * @time 2014年6月9日
 * @mail wuming@b5m.com
 */
public class ContextUtils {
	private static Properties properties;
	private static String[] collections = new String[]{"b5mo", "hotel", "ticketp", "tourguide", "tourp", "tuanm", "zdm", "she", "haiwaip", "haiwaiinfo", "guang", "doctor", "usa", "korea", "koreainfo"};
	
	public static void init(JSONObject categoryRel, String xmlConfig, Properties properties){
		SearchContext.getInstance();
		setVersion();
		setChannels(xmlConfig);
		ContextUtils.properties = properties;
		SearchContext.getInstance().categoryRel = categoryRel;
	}
	
	public static void setChannels(String xmlConfig){
		List<Channel> channels = BeanTools.xmlToObject(xmlConfig, Channel.class);
		Map<String, Channel> channelsMap = new HashMap<String, Channel>();
		for(Channel channel : channels){
			channelsMap.put(channel.getName(), channel);
		}
		SearchContext.getInstance().setChannels(channelsMap);
	}
	
	public static String getCategoryByCode(String code){
		return SearchContext.getInstance().categoryRel.getString(code);
	}
	
	public static String getCodeByCategory(String category){
		return SearchContext.getInstance().categoryRel.getString(category);
	}
	
	public static void setVersion(){
		SearchContext.getInstance().setVersion(DateTools.formate(DateTools.now(), "yyyyMMddHHmmss"));
	}
	
	public static String getVersion(){
		return SearchContext.getInstance().getVersion();
	}
	
	public static Channel getChannel(String channel){
		return SearchContext.getInstance().getChannel(channel);
	}
	
	public static String get404Page(){
		return properties.getProperty("404page");
	}
	
	public static String get500Page(){
		return properties.getProperty("500page");
	}
	
	public static String getProp(String name){
		return properties.getProperty(name);
	}
	
	public static Integer getIntProp(String key){
        Object o = properties.get(key);
        if(null == o) return null;
        return Integer.parseInt(o.toString());
    }
    
    public static Long getLongProp(String key){
    	Object o = properties.get(key);
        if(null == o) return null;
        return Long.parseLong(o.toString());
    }
    
    public static boolean checkToken(String token) throws Exception{
    	if(StringUtils.isEmpty(token)) return false;
    	Class<?> cls = Class.forName("com.b5m.search.sys.Tokens");
    	Object o = cls.newInstance();
    	Method method = cls.getMethod("check", String.class);
    	Object flag = method.invoke(o, token);
    	return Boolean.valueOf(flag.toString());
    }
    
    public static String generatorToken(String token) throws Exception{
    	if(StringUtils.isEmpty(token)) return "";
    	Class<?> cls = Class.forName("com.b5m.search.sys.Tokens");
    	Object o = cls.newInstance();
    	Method method = cls.getMethod("generator", String.class);
    	Object tokenStr = method.invoke(o, token);
    	return tokenStr.toString();
    }
    
    public static String getAutofillName(String collection){
		if("b5mo".equals(collection)) return "b5mp";
		if("ticketp".equals(collection)) return "ticket";
		if("tourp".equals(collection)) return "tour";
		if("tuanm".equals(collection)) return "tuan";
		return collection;
	}
}
