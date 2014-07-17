package com.b5m.search.bean.dto;

import java.io.Serializable;


/**
 * 过滤条件需要使用到的DTO
 * @author jacky
 *
 */
public class FilterLinkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -390447843001390682L;

	private String filterType; 
	
	private LinkDto link;

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public LinkDto getLink() {
		return link;
	}

	public void setLink(LinkDto link) {
		this.link = link;
	}
}
