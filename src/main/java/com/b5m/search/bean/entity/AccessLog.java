package com.b5m.search.bean.entity;

import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

//接口访问日志
@Table("t_access_log")
public class AccessLog {
    @Id	
	private long id;
    //访问的来源服务
    @Column	
	private String server;
    //访问路径
    @Column	
	private String path;
    //ip
    @Column	
	private String ip;
    //来源
    @Column	
	private String ref;
    //访问时间
    @Column(name = "access_date")	
    private Date accessDate;
    //参数
    @Column(name = "params")
    private String params;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
