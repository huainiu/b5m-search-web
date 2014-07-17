package com.b5m.search.bean.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

@Table("t_mall_brand_info")
public class MallBrandInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2033043790073700067L;

	@Id
	private Long id;
	
	@Column(name = "keywords")
	private String keywords;//搜索关键词
	
	@Column(name = "category")
	private String category;//搜索分类
	
	@Column(name = "name")
	private String name;//商城 或者 品牌 关键词
	
	@Column(name = "type")
	private Integer type;//0-商城,1-品牌,2-keyword
	
	@Column
	private String description;//描述
	
	@Column
	private String img;
	
	@Column
	private Integer status;//0-禁用,1-启用
	
	@Column
	private Date createTime;
	
	@Column
	private Date updateTime;
	
	@Column
	private String operater;//操作人
	
	@Column(name = "first_up_char")
	private String firstUpChar;
	
	@Column(name = "keywords_en")
	private String keywordEn;//编码后的关键词
	
	@Column
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFirstUpChar() {
		return firstUpChar;
	}

	public void setFirstUpChar(String firstUpChar) {
		this.firstUpChar = firstUpChar;
	}
	
	public String getKeywordEn() {
		return keywordEn;
	}

	public void setKeywordEn(String keywordEn) {
		this.keywordEn = keywordEn;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
