package com.b5m.search.utils;
import java.util.List;
import java.util.Map;

import com.b5m.base.common.utils.StringTools;

/**
 */
public class ImageUtils {
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 图片路径替换
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月5日 下午4:52:27
	 *
	 * @param result
	 */
	public static void replaceImgUrl(List<Map<String, String>> result){
    	/*String imgUrlStr = ConfigPropUtils.getValue("img.cdn.link");
    	for(Map<String, String> produce : result){
    		String pic = produce.get("Picture");
   		    produce.put("Picture", newPic(pic, imgUrlStr));
    	}*/
    }
	
	public static void replaceImgUrl(Map<String, String> produce){
    	/*String imgUrlStr = ConfigPropUtils.getValue("img.cdn.link");
		String pic = produce.get("Picture");
		produce.put("Picture", newPic(pic, imgUrlStr));*/
    }
	
    public static String newPic(String pic, String imgUrlStr){//替换链接地址为cdn的
    	String[] imgUrls = StringTools.split(imgUrlStr, ",");
    	if(StringTools.isEmpty(pic)) return pic;
    	int hashcode = pic.hashCode();
    	int index = hashcode % imgUrls.length;
    	if(index < 0) index = - index;
    	return StringTools.replace(pic, "img.b5m.com", imgUrls[index]);
    }
	
}
