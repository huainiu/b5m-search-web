package com.b5m.search.bean.entity.filterAttr;

import java.io.Serializable;
import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;

@Table("t_attibute_value")
public class AttibuteValue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 542829150653307533L;

	@Id
	private Long id;
	
	@Column
	private String name;
	
	@Column(name = "create_time", type = ColType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "attibute_id")
	private Long attibuteId;

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

	public Long getAttibuteId() {
		return attibuteId;
	}

	public void setAttibuteId(Long attibuteId) {
		this.attibuteId = attibuteId;
	}

}
