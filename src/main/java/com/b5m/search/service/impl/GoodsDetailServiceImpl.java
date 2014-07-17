package com.b5m.search.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.b5m.search.bean.dto.GoodsDetailDto;
import com.b5m.search.service.GoodsDetailService;
import com.b5m.search.sys.Channel;
import com.b5m.search.sys.ContextUtils;

@Service
public class GoodsDetailServiceImpl implements GoodsDetailService{
	@Resource
	protected SF1NewQueryService sf1Search;
	
	public GoodsDetailDto queryItemDetailByDocId(Channel channel, String docId){
		GoodsDetailDto goodsDetailDto = new GoodsDetailDto();
		JSONObject produce = sf1Search.doGet(channel, docId);
		if(produce == null){
			goodsDetailDto.setResultPage(ContextUtils.get404Page());
			return goodsDetailDto;
		}
		setProduceAttr(goodsDetailDto, produce);
		setCates(goodsDetailDto, produce);
		setDetailInfo(goodsDetailDto, produce);
		goodsDetailDto.setResultPage(channel.getDetailPage());
		return goodsDetailDto;
	}
	
	//设置商品属性
	private void setProduceAttr(GoodsDetailDto goodsDetailDto, JSONObject produce) {
		Map<String, String> attrsMap = new HashMap<String, String>();// 品牌:Apple/苹果,MacBook
		String attrStr = produce.getString("Attribute");
		if (!StringUtils.isEmpty(attrStr)) {
			String[] attrs = attrStr.split(",|，");
			for (String attr : attrs) {
				String[] a = attr.split(":");
				if (a.length < 2) {
					continue;
				}
				attrsMap.put(a[0], a[1]);
			}
		}
		goodsDetailDto.setAttributes(attrsMap);
	}
	
	//设置类目
	private void setCates(GoodsDetailDto goodsDetailDto, JSONObject produce){
		String cate = produce.getString("Category");
		if(StringUtils.isEmpty(cate)) return;
		goodsDetailDto.setCates(StringUtils.split(cate, ">"));
	}
	
	//设置商品详情
	private void setDetailInfo(GoodsDetailDto goodsDetailDto, JSONObject produce){
		
	}
}
