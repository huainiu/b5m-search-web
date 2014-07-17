package com.b5m.search.bean.dto;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class GoodsDetailDto {
	//商品详情信息
	private JSONObject res;
	// 商品属性
	private Map<String, String> attributes;
	//商品类目 为了面包屑展示
	private String[] cates;
	//详情信息
	private String detailInfo;
	//结果显示页面
	private String resultPage;

	public JSONObject getRes() {
		return res;
	}

	public void setRes(JSONObject res) {
		this.res = res;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String[] getCates() {
		return cates;
	}

	public void setCates(String[] cates) {
		this.cates = cates;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getResultPage() {
		return resultPage;
	}

	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}
	
}
