package com.b5m.search.utils;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 数据处理类
 * @author echo
 * @time 2014年5月5日
 * @mail wuming@b5m.com
 */
public class DataUtils {
	public static boolean containsChinese(String str){
		if(StringUtils.isEmpty(str)) return false;
		return str.getBytes().length != str.length();
	}
	
	public static String replace(String text, String replace, String replaceWith){
		if(text.indexOf(replace) < 0) return text;
		return StringUtils.replace(text, replace, replaceWith);
	}
	
	public static String base64Encode(String chinese){
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(chinese.getBytes());
	}
	
	public static String base64Decode(String str){
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try {
			return new String(base64Decoder.decodeBuffer(str));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String strEncode(String str){
		int length = str.length();
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < length; index++){
			int asciiNum = (int)str.charAt(index);
			String oneEncode = fourPlaceFill(Integer.toString(asciiNum, 35));//转化为35进制
			sb.append(oneEncode);
		}
		return sb.toString().toUpperCase();
	}
	
	public static String strDecode(String str){
		str = str.toLowerCase();
		int length = str.length();
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < length; index = index + 4){
			String oneEncode = str.substring(index, index + 4);
			int asciiNum = Integer.valueOf(oneEncode, 35);//35进制转化为10进制
			char c = (char)asciiNum;
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String fourPlaceFill(String str){
		int length = str.length();
		if(length >= 4) return str;
		if(length == 0) return "0000";
		if(length == 1) return "000" + str;
		if(length == 2) return "00" + str;
		if(length == 3) return "0" + str;
		return str;
	}
	
}