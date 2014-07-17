package com.b5m.search.bean.dto;
public class SortSearchBean {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private String name;
    private String type;
    
    public SortSearchBean(String name, String type){
    	this.name = name;
    	this.type = type;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}