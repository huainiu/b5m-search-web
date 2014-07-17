package com.b5m.search.bean.dto;

import java.io.Serializable;

public class LinkDto implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4639864592190939278L;

	private String text;
	
	private String url;
	
	private String base64Text;
	
	/**
	 * 是否被选中
	 */
	private boolean clicked = false;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	@Override
	public String toString(){
		return new StringBuilder("{text:").append(text)
				.append(", url:").append(url)
				.append(", isClicked:").append(clicked).append("}")
				.toString();
	}

	public LinkDto clone()  {
		try {
			return (LinkDto) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}
	
	public String getBase64Text() {
		return base64Text;
	}

	public void setBase64Text(String base64Text) {
		this.base64Text = base64Text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (clicked ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		LinkDto other = (LinkDto) obj;
		if (clicked != other.clicked)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
