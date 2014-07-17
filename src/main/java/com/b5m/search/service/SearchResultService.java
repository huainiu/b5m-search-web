package com.b5m.search.service;

import java.util.List;
import java.util.Map;

import com.b5m.search.bean.entity.AdKeywords;
import com.b5m.search.bean.entity.filterAttr.Attibute;

public interface SearchResultService {
	/**
	 * 查询需要过滤的属性
	 * @param keyword
	 * @return
	 */
	Map<String, Attibute> queryAttrFilterList(String keyword);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 判断是否展示广告
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年4月2日 下午6:35:26
	 *
	 * @param keywords
	 * @param pageCode
	 * @return
	 */
	boolean needShowAd(String keywords, Integer pageCode, List<AdKeywords> adKeywordsList);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 查询所有需要显示广告的关键词
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年4月2日 下午6:46:41
	 *
	 * @return
	 */
	List<AdKeywords> queryAllAdKeywords();
}