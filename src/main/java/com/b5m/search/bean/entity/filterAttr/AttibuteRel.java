package com.b5m.search.bean.entity.filterAttr;

import java.io.Serializable;
import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

@Table("t_attibute_rel")
public class AttibuteRel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5420973341581350070L;

	@Id
	private Long id;
	
	@Column(name = "display_name")
	private String displayName;//显示名称
	
	@Column(name = "merge_names")
	private String mergeNames;//合并名称，多个名称以逗号分割
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "attibute_id")
	private Long attibuteId;
	
	@Column(name = "keywords_id")
	private Long keywordsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMergeNames() {
		return mergeNames;
	}

	public void setMergeNames(String mergeNames) {
		this.mergeNames = mergeNames;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getAttibuteId() {
		return attibuteId;
	}

	public void setAttibuteId(Long attibuteId) {
		this.attibuteId = attibuteId;
	}

	public Long getKeywordsId() {
		return keywordsId;
	}

	public void setKeywordsId(Long keywordsId) {
		this.keywordsId = keywordsId;
	}
	
}
