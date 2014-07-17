package com.b5m.search.bean.entity.filterAttr;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;
/**
 * @author echo
 */
@Table("t_keywords")
public class Keywords implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -181342403394947415L;

	@Id
	private Long id;
	
	@Column
	private String name;
	
	@Column(name = "create_time", type = ColType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "update_time", type = ColType.TIMESTAMP)
	private Date updateTime;
	
	@Column
	private Integer type;//1-属性维护，为了以后的扩展
	
	@Column
	private Integer status;//0-删除, 1-正常
	
	@Column
	private String ranks;//属性排序

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRanks() {
		return ranks;
	}
	
	public String[] getRankArray() {
		return StringUtils.split(ranks, ",");
	}

	public void setRanks(String ranks) {
		this.ranks = ranks;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
