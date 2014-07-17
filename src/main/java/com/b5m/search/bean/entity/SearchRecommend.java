package com.b5m.search.bean.entity;

import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;

@Table("t_search_recommend")
public class SearchRecommend extends DomainObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7964401489736138121L;

	@Column(name = "create_time")
	private Date createTime;
	
	@Column
	private String keyword;
	
	@Column(type = ColType.TEXT)
	private String content;
	
	@Column
	private String email;
	
	@Column
	private String ip;
	
	@Column(name = "username")
	private String username;
	
	private String uid;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getUid() {
		return uid;
	}

	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
