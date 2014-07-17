package com.b5m.search.bean.dto;

/**
 * 用于SF1QueryHelper设置排序的枚举。
 * @author jacky
 *
 */
public enum SortType {
	
	/**
	 * 默认排序方式
	 */
	Default(""),
	
	/**
	 * 按时间排序
	 */
	Date("Date"),
	
	/**
	 * 按热门度排序
	 */
	Hot("ctl"),
	
	/**
	 * 按价格排序
	 */
	Price("Price"),
	
	/**
	 * 按好评度排序
	 */
	Score("Score"),
	
	/**
	 * 销售数据排序
	 */
	SalesAmount("SalesAmount");
	
	private final String value;
	
	private SortType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
