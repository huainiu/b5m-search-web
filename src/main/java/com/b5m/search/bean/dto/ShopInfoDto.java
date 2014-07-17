package com.b5m.search.bean.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopInfoDto{

    private String shopName;

    private String logoImgUrl;

    private String shopId;
    
    private String url;
    
    private String cpsUrl;

    //商家标签，如：品牌官网,正品保障,正规发票,货到付款
    private String shopTag;

    //商家活动，返利活动，如：最高返90元
    private String shopActive;
    
    private String docId;
    
    //商家id
    private String sourceName;

    private List<Map<String, String>> shopList = new ArrayList<Map<String,String>>();
    
    private int shopListSize;

    public String getShopName(){
        return shopName;
    }

    public void setShopName(String shopName){
        this.shopName = shopName;
    }

    public List<Map<String, String>> getShopList(){
        return shopList;
    }

    public void setShopList(List<Map<String, String>> shopList)
    {
        this.shopList = shopList;
    }

    public String getLogoImgUrl()
    {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl)
    {
        this.logoImgUrl = logoImgUrl;
    }

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public String getShopTag()
    {
        return shopTag;
    }

    public void setShopTag(String shopTag)
    {
        this.shopTag = shopTag;
    }

    public String getShopActive()
    {
        return shopActive;
    }

    public void setShopActive(String shopActive){
        this.shopActive = shopActive;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getCpsUrl(){
        return cpsUrl;
    }

    public void setCpsUrl(String cpsUrl){
        this.cpsUrl = cpsUrl;
    }

    public String getDocId(){
        return docId;
    }

    public void setDocId(String docId){
        this.docId = docId;
    }

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getShopListSize() {
		return shopListSize;
	}

	public void setShopListSize(int shopListSize) {
		this.shopListSize = shopListSize;
	}

}
