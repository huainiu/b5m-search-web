package com.b5m.search.bean.dto;

import java.io.Serializable;
import java.util.Map;

import com.b5m.search.bean.entity.filterAttr.Attibute;

public class SuiSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2657195802050570195L;
	// 集合名称
	private String collectionName;
	// 关键字
	private String keyword;
	// 排序字段
	private String sortField;
	// 排序类型
	private String sortType = SortSearchBean.ASC;
	// 分类值
	private String categoryValue;
	// 商家值
	private String sourceValue;
	// 品牌值
	private String attrs;
	// 价格下限
	private String priceFrom;
	// 价格上限
	private String priceTo;
	// 是否获得属性group
	private boolean isGetAttrTree = true;
	// 过滤条件
	private String filterField;
	// 0表示不免运费，1表示免运费
	private String isFreeDelivery;

	private String isCOD;
	// 0表示水货，1表示正品行货
	private String isGenuine;
	// 获取关键词相关联的
	private boolean isRequireRelated;
	// 拆分重新查询
	private Integer queryAbbreviation;
	// 页面记录数
	private Integer pageSize = 10;
	// 当前页号
	private Integer currPageNo = 1;
	private boolean isCompare = false;
	// 0表示非最低价, 1表示最低价
	private String isLowPrice;
	// 属性过滤 属性合并
	private Map<String, Attibute> filterMap;
	//原始关键词，再查询的过程中 关键词可能会变动
	private String orignKeyword;
	//给usa 和 korea用的
	private String country;
	//服务链接
	private String serverPath;
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public String getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(String priceTo) {
		this.priceTo = priceTo;
	}

	public boolean isGetAttrTree() {
		return isGetAttrTree;
	}

	public void setGetAttrTree(boolean isGetAttrTree) {
		this.isGetAttrTree = isGetAttrTree;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public String getIsFreeDelivery() {
		return isFreeDelivery;
	}

	public void setIsFreeDelivery(String isFreeDelivery) {
		this.isFreeDelivery = isFreeDelivery;
	}

	public String getIsCOD() {
		return isCOD;
	}

	public void setIsCOD(String isCOD) {
		this.isCOD = isCOD;
	}

	public String getIsGenuine() {
		return isGenuine;
	}

	public void setIsGenuine(String isGenuine) {
		this.isGenuine = isGenuine;
	}

	public boolean isRequireRelated() {
		return isRequireRelated;
	}

	public void setRequireRelated(boolean isRequireRelated) {
		this.isRequireRelated = isRequireRelated;
	}

	public Integer getQueryAbbreviation() {
		return queryAbbreviation;
	}

	public void setQueryAbbreviation(Integer queryAbbreviation) {
		this.queryAbbreviation = queryAbbreviation;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(Integer currPageNo) {
		this.currPageNo = currPageNo;
	}

	public boolean isCompare() {
		return isCompare;
	}

	public void setCompare(boolean isCompare) {
		this.isCompare = isCompare;
	}

	public String getIsLowPrice() {
		return isLowPrice;
	}

	public void setIsLowPrice(String isLowPrice) {
		this.isLowPrice = isLowPrice;
	}

	public Map<String, Attibute> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Attibute> filterMap) {
		this.filterMap = filterMap;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrignKeyword() {
		return orignKeyword;
	}

	public void setOrignKeyword(String orignKeyword) {
		this.orignKeyword = orignKeyword;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	
}
