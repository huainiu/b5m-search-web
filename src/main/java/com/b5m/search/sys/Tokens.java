package com.b5m.search.sys;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.StringTools;

public class Tokens {
	
	public Boolean check(String token){
		String[] _token = StringUtils.split(token, "|");
		if(_token.length < 2) return false;
		String tokenKey = ContextUtils.getProp(_token[0]);
		String sign = StringTools.MD5(tokenKey + "_" + DateTools.formate(new Date(), "yyyyMMddHH"));
		return sign.equals(_token[1]);
	}
	
	public String generator(String token){
		String tokenKey = ContextUtils.getProp(token);
		return token + "|" + StringTools.MD5(tokenKey + "_" + DateTools.formate(new Date(), "yyyyMMddHH"));
	}
}
