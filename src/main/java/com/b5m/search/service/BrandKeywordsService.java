package com.b5m.search.service;

import java.util.List;
import java.util.TreeMap;

import com.b5m.search.bean.entity.BrandKeywords;

/**
 * description 
 * 品牌关键词管理
 * @Company b5m
 * @author echo
 * @since 2014年1月13日
 */
public interface BrandKeywordsService {
	
	/**
	 * description
	 * 查询所有品牌关键词列表
	 * @return
	 * @author echo weng
	 * @since 2014年1月13日
	 * @mail echo.weng@b5m.com
	 */
	TreeMap<Integer, BrandKeywords> queryAllBrandKeywords();
	
	/**
	 * description
	 * 查询下一个品牌列表，可以设在size，如果为null或为0，则返回默认10个品牌关键词
	 * @param brands
	 * @param size
	 * @return
	 * @author echo weng
	 * @since 2014年1月13日
	 * @mail echo.weng@b5m.com
	 */
	List<BrandKeywords> findNextBrandKeywords(String brands, Integer size);
	
}
