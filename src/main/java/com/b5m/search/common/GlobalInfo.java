package com.b5m.search.common;

import java.util.Properties;

import com.b5m.base.common.spring.utils.ApplicationContextUtils;

public class GlobalInfo {
	private static Properties config;
	
	static{
		config = ApplicationContextUtils.getBean("config", Properties.class);
	}
	
	 public static String getStr(String key){
        Object o = config.get(key);
        if(null == o) return null;
        return o.toString();
    }
    
    public static Integer getInt(String key){
        Object o = config.get(key);
        if(null == o) return null;
        return Integer.parseInt(o.toString());
    }
    
    public static Long getLong(String key){
    	Object o = config.get(key);
        if(null == o) return null;
        return Long.parseLong(o.toString());
    }
}
