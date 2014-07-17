package com.b5m.search.bean.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.b5m.dao.domain.page.PageView;

/**
 * 这个是整个/goodsSearchList需要使用到的所有DTO
 */
@SuppressWarnings("rawtypes")
public class ShopListDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -2988062066868406177L;
    //商品数量
	private long itemCount;
    //分类链接
	private LinkTreeDto categoryList;
    //商家链接
	private List<LinkDto> sourceLinks;
    //过滤链接
	private List<FilterLinkDto> filterList;
    //价格区间链接
	private List<LinkDto> priceList;
    //排序链接
	private List<LinkDto> sortList;
    //过滤属性链接
	private List<AttrLinkDto> attrLinkDtos;
    //每页的数据
	private PageView pageView;
    //分页中每个页码的链接 模版
	private String pageCodeLink;
	// 免运费 正品行货 货到付款 链接
	private LinkDto[] cfgLinks;
    //关键词相关搜索链接
	private List<LinkDto> relatedQueryList;
	//重新搜索词
	private String refineQuery;
	//需要显示价格趋势的 docId
	private String priceTrendDocIds;
	//第一个页面的路径地址
	private String firstPageUrl;
	
	public LinkDto[] getCfgLinks() {
		return cfgLinks;
	}

	public void setCfgLinks(LinkDto[] cfgLinks) {
		this.cfgLinks = cfgLinks;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	public LinkTreeDto getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(LinkTreeDto categoryList) {
		this.categoryList = categoryList;
	}

	public List<LinkDto> getSourceLinks() {
		return sourceLinks;
	}

	public void setSourceLinks(List<LinkDto> sourceLinks) {
		this.sourceLinks = sourceLinks;
	}

	public List<FilterLinkDto> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<FilterLinkDto> filterList) {
		this.filterList = filterList;
	}

	public List<LinkDto> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<LinkDto> priceList) {
		this.priceList = priceList;
	}

	public List<LinkDto> getSortList() {
		return sortList;
	}

	public void setSortList(List<LinkDto> sortList) {
		this.sortList = sortList;
	}

	public PageView getPageView() {
		return pageView;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}

	public String getPageCodeLink() {
		return pageCodeLink;
	}

	public void setPageCodeLink(String pageCodeLink) {
		this.pageCodeLink = pageCodeLink;
	}

	public List<AttrLinkDto> getAttrLinkDtos() {
		if (attrLinkDtos == null)
			attrLinkDtos = new ArrayList<AttrLinkDto>(0);
		return attrLinkDtos;
	}

	public void setAttrLinkDtos(List<AttrLinkDto> attrLinkDtos) {
		this.attrLinkDtos = attrLinkDtos;
	}

	public List<LinkDto> getRelatedQueryList() {
		if(relatedQueryList == null) relatedQueryList = new ArrayList<LinkDto>();
		return relatedQueryList;
	}

	public void setRelatedQueryList(List<LinkDto> relatedQueryList) {
		this.relatedQueryList = relatedQueryList;
	}

	public String getPriceTrendDocIds() {
		return priceTrendDocIds;
	}

	public void setPriceTrendDocIds(String priceTrendDocIds) {
		this.priceTrendDocIds = priceTrendDocIds;
	}

	public String getRefineQuery() {
		return refineQuery;
	}

	public void setRefineQuery(String refineQuery) {
		this.refineQuery = refineQuery;
	}

	public String getFirstPageUrl() {
		return firstPageUrl;
	}

	public void setFirstPageUrl(String firstPageUrl) {
		this.firstPageUrl = firstPageUrl;
	}

}
