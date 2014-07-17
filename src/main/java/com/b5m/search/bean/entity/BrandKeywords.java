package com.b5m.search.bean.entity;

import java.io.Serializable;
import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2014年1月13日
 */
@Table("t_brand_keywords")
public class BrandKeywords implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7519859300695350914L;

	@Id
	private Long id;
	
	@Column
	private String brands;//品牌
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "update_time")
	private Date updateTime;
	
	@Column
	private String ext;//扩展字段

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
