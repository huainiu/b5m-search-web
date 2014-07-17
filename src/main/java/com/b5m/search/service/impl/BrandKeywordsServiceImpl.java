package com.b5m.search.service.impl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.base.common.spring.aop.LocalCache;
import com.b5m.dao.Dao;
import com.b5m.search.bean.entity.BrandKeywords;
import com.b5m.search.service.BrandKeywordsService;

@Service("brandKeywordsService")
public class BrandKeywordsServiceImpl implements BrandKeywordsService{
	
	@Autowired
	private Dao dao;
	
	private static LocalCache localCache = new LocalCache();
	
	@Override
	public TreeMap<Integer, BrandKeywords> queryAllBrandKeywords(){
		Object o = localCache.get("all_brand_keywords");
		if(o == null){
			synchronized ("all_brand_keywords") {
				List<BrandKeywords> brandKeywords = dao.queryAll(BrandKeywords.class);
				TreeMap<Integer, BrandKeywords> treeMap = newTreeMap();
				for(BrandKeywords brandKeyword : brandKeywords){
					Integer key = getHashCode(brandKeyword.getBrands());
					treeMap.put(key, brandKeyword);
				}
				localCache.put("all_brand_keywords", treeMap, 432000);
			}
		}
		return (TreeMap<Integer, BrandKeywords>) localCache.get("all_brand_keywords");
	}
	
	protected TreeMap<Integer, BrandKeywords> newTreeMap(){
		return new TreeMap<Integer, BrandKeywords>(new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
			
		});
	}
	
	@Override
	public List<BrandKeywords> findNextBrandKeywords(String brands, Integer size){
		size = ((size == null || size <= 0)  ? 10 : size);
		TreeMap<Integer, BrandKeywords> brandKeywords = queryAllBrandKeywords();
		int index = 0;
		List<BrandKeywords> rtnList = new ArrayList<BrandKeywords>(); 
		Integer key = getHashCode(brands);
		BrandKeywords brandKeyword = brandKeywords.get(key);
		if(brandKeyword != null){
			SortedMap<Integer, BrandKeywords> map = brandKeywords.tailMap(key);
			for(Entry<Integer, BrandKeywords> entry : map.entrySet()){
				if(index < size){
					rtnList.add(entry.getValue());
					index++;
				}
			}
		}
		return rtnList;
	}
	
	protected static Integer getHashCode(String brands){
		Integer key = brands.hashCode();
		if(key < 0) return -key;
		return key;
	}
	
}