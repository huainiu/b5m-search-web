package com.b5m.search.bean.entity.filterAttr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;

@Table("t_attibute")
public class Attibute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4625565420162565196L;

	@Id
	private Long id;

	@Column
	private String name;

	@Column(name = "create_time", type = ColType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "update_time", type = ColType.TIMESTAMP)
	private Date updateTime;

	@Column
	private String ranks;//属性值排序
	
	@Column
	private Integer status;// 是否是整个过滤 1是 0否

	@Column(name = "keywords_id")
	private Long keywordsId;
	
	@Column(name = "display_name")
	private String displayName;

	private Set<String> values;
	
	private Keywords keyRes;
	
	private List<AttibuteRel> attibuteRels;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getKeywordsId() {
		return keywordsId;
	}

	public void setKeywordsId(Long keywordsId) {
		this.keywordsId = keywordsId;
	}

	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public Keywords getKeyRes() {
		return keyRes;
	}

	public void setKeyRes(Keywords keyRes) {
		this.keyRes = keyRes;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<AttibuteRel> getAttibuteRels() {
		return attibuteRels;
	}
	
	public String[] getRelByDisplayName(String displayName){
		if(attibuteRels == null || StringUtils.isEmpty(displayName)) return new String[0];
		for(AttibuteRel attibuteRel : attibuteRels){
			if(displayName.equals(attibuteRel.getDisplayName())){
				return StringUtils.split(attibuteRel.getMergeNames(), ",");
			}
		}
		return new String[0]; 
	}

	public void setAttibuteRels(List<AttibuteRel> attibuteRels) {
		this.attibuteRels = attibuteRels;
	}

	@Override
	public String toString() {
		return "Attibute [id=" + id + ", name=" + name + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", ranks="
				+ ranks + ", status=" + status + ", keywordsId=" + keywordsId
				+ ", displayName=" + displayName + ", values=" + values
				+ ", keyRes=" + keyRes + "]";
	}

}