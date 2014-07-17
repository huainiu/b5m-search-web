package com.b5m.search.bean.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinkTreeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6795187805887182823L;

	private LinkDto link;
	
	private List<LinkTreeDto> linkTree;

	public LinkDto getLink() {
		return link;
	}

	public void setLink(LinkDto link) {
		this.link = link;
	}

	public List<LinkTreeDto> getLinkTree() {
		if(null == linkTree)
			linkTree = new ArrayList<LinkTreeDto>();
		return linkTree;
	}

	public void setLinkTree(List<LinkTreeDto> linkTree) {
		this.linkTree = linkTree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((linkTree == null) ? 0 : linkTree.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkTreeDto other = (LinkTreeDto) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (linkTree == null) {
			if (other.linkTree != null)
				return false;
		} else if (!linkTree.equals(other.linkTree))
			return false;
		return true;
	}
	
}
