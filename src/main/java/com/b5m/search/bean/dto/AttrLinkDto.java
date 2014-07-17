package com.b5m.search.bean.dto;

import java.util.ArrayList;
import java.util.List;
/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-8-5
 * @email echo.weng@b5m.com
 */
public class AttrLinkDto {
	private String name;
	
	private List<LinkDto> attrList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LinkDto> getAttrList() {
		if(attrList == null) attrList = new ArrayList<LinkDto>(0);
		return attrList;
	}

	public void setAttrList(List<LinkDto> attrList) {
		this.attrList = attrList;
	}
	
}
