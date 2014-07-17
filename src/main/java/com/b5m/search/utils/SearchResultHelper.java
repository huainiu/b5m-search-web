package com.b5m.search.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.dao.domain.page.PageView;
import com.b5m.search.bean.dto.AttrLinkDto;
import com.b5m.search.bean.dto.LinkDto;
import com.b5m.search.bean.dto.ShopListDto;
import com.b5m.search.bean.dto.SuiSearchDto;
import com.b5m.search.bean.entity.filterAttr.Attibute;
import com.b5m.search.sys.Channel;
import com.b5m.sf1api.dto.req.AttrFilterBean;
import com.b5m.sf1api.dto.req.GroupBean;
import com.b5m.sf1api.dto.req.SF1SearchBean;
import com.b5m.sf1api.dto.req.Select;
import com.b5m.sf1api.dto.req.SortSearchBean;
import com.b5m.sf1api.dto.res.DocResourceDto;
import com.b5m.sf1api.dto.res.Group;
import com.b5m.sf1api.dto.res.GroupTree;
import com.b5m.sf1api.dto.res.SearchDTO;
import com.b5m.sf1api.utils.GetCndBuilder;
import com.b5m.sf1api.utils.SearchCndBuilder;
import com.b5m.sf1api.utils.Sf1DataHelper;

public class SearchResultHelper {
	public static final String IS_FREE_DELIVERY = "1";
	public static final String IS_COD = "1";
	public static final String IS_GENUINE = "1";
	
	public static SF1SearchBean converTo4Get(Channel _channel, String docId){
		SF1SearchBean sf1SearchBean = new SF1SearchBean();
		GetCndBuilder builder = sf1SearchBean.builderGet();
		builder.collection(_channel.getCollection());
		builder.addCondition("DOCID", "=", docId);
		return sf1SearchBean;
	}

	public static SF1SearchBean convertTo4Search(SuiSearchDto searchDto) {
		SF1SearchBean sf1SearchBean = new SF1SearchBean();
		SearchCndBuilder builder = sf1SearchBean.builder();
		builder.collection(searchDto.getCollectionName());
		builder.page(searchDto.getPageSize(), (searchDto.getCurrPageNo() - 1) * searchDto.getPageSize());
		builder.getAttr(true, 20).sort(searchDto.getSortField(), searchDto.getSortType());
		builder.filterByPrice(searchDto.getPriceFrom(), searchDto.getPriceTo());
		builder.category(searchDto.getCategoryValue());
		builder.sources(searchDto.getSourceValue()).queryAbbreviation(true);
		builder.keywords(searchDto.getKeyword()).isRequireRelated(true);
		//设置过滤属性
		setAttrFilter(searchDto, builder);
		//设置group
		addGroup(searchDto, builder);
		if (searchDto.isCompare()) {
			builder.addCondition("itemcount", ">", "1");
		}
		if ("1".equals(searchDto.getIsLowPrice())) {
			builder.addCondition("isLowPrice", ">", "0f");
		}
		if (IS_FREE_DELIVERY.equals(searchDto.getIsFreeDelivery())) {
			builder.addCondition("isFreeDelivery", "=", IS_FREE_DELIVERY);
		}
		if (IS_GENUINE.equals(searchDto.getIsGenuine())) {
			builder.addCondition("isGenuine", "=", IS_GENUINE);
		}
		if(!StringUtils.isEmpty(searchDto.getCountry())){
			builder.addCondition("Country", "=", searchDto.getCountry());
		}
		return sf1SearchBean;
	}
	
	protected static void setSelects(SF1SearchBean sf1SearchBean){
		sf1SearchBean.addSelect(new Select("DOCID"));
	}
	
	protected static void addGroup(SuiSearchDto searchDto, SearchCndBuilder builder) {
		if(StringUtils.isEmpty(searchDto.getPriceFrom()) && StringUtils.isEmpty(searchDto.getPriceTo())){
			builder.addGroupBean(new GroupBean(true, "Price"));
		}
		builder.addGroupBean(new GroupBean(false, "Source"));
		builder.addGroupBean(new GroupBean(false, "Category"));
	}

	protected static void setAttrFilter(SuiSearchDto searchDto, SearchCndBuilder builder) {
		if (StringTools.isEmpty(searchDto.getAttrs())) {
			return;
		}
		String[] attrValueArray = StringTools.split(searchDto.getAttrs(), ",");
		if (attrValueArray.length == 0)
			return;
		Map<String, Attibute> attrMap = searchDto.getFilterMap();
		for (String attrValue : attrValueArray) {
			if (StringUtils.isEmpty(attrValue))
				continue;
			String[] attr = StringTools.split(attrValue, ":");
			if (attr.length < 2)
				continue;
			// 将品牌成改利用attr_label进行过滤
			String value = attr[1];
			Attibute attibute = null;
			if(attrMap != null){
				attibute = attrMap.get("ALL");
			}
			boolean haveMerge = false;
			if(attibute != null) {
				String[] attrs = attibute.getRelByDisplayName(value);
				if(attrs.length > 0){
					haveMerge = true;
					for(String a : attrs){
						builder.addAttrFilter(new AttrFilterBean(attr[0], a));
					}
				}
			}
			if(!haveMerge){
				builder.addAttrFilter(new AttrFilterBean(attr[0], value));
			}
		}
	}
	
	protected static void setSortFilter(SuiSearchDto searchDto, SF1SearchBean sf1SearchBean) {
		sf1SearchBean.addSortSearchBean(new SortSearchBean(searchDto.getSortField(), searchDto.getSortType()));
	}
	
	public static boolean needReSearch(SuiSearchDto dto) {
		return StringUtils.isEmpty(dto.getCategoryValue()) && StringUtils.isEmpty(dto.getAttrs()) && StringUtils.isEmpty(dto.getIsCOD()) && StringUtils.isEmpty(dto.getIsFreeDelivery()) && StringUtils.isEmpty(dto.getIsGenuine()) && (dto.getCurrPageNo() == null || (dto.getCurrPageNo() != null && dto.getCurrPageNo() == 1)) && StringUtils.isEmpty(dto.getPriceFrom()) && StringUtils.isEmpty(dto.getPriceTo()) && StringUtils.isEmpty(dto.getSourceValue());
	}

	public static boolean isNoResult(SearchDTO searchDTO) {
		return searchDTO == null || searchDTO.getTotalCount() == 0;
	}
	
	public static boolean needReSearchMore(SearchDTO searchDTO){
		return searchDTO != null && searchDTO.getTotalCount() <= 8;
	}
	
	// 获取o的docId SubResources为空 说明这个是o的商品
	public static String getPriceTrendDocIds(SearchDTO searchDto) {
		StringBuilder sb = new StringBuilder();
		for (DocResourceDto docResourceDto : searchDto.getDocResourceDtos()) {
			if (docResourceDto.getSubResources().isEmpty()) {
				String docId = docResourceDto.getRes().get("DOCID");
				sb.append(docId + ",");
			}
		}
		if (sb.length() < 1)
			return "";
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static void filterAttr(Map<String, Attibute> filterMap, SearchDTO searchDto) {
		List<GroupTree> attributeTrees = searchDto.getAttributeTrees();
		if (CollectionUtils.isEmpty(attributeTrees) || CollectionUtils.isEmpty(filterMap))
			return;
		String[] attrRanks = getAttrRanks(filterMap);
		if(attrRanks.length > 0){//如果排序值大于1 则进行排序
			attributeTrees = rankAttrValue(attributeTrees, attrRanks);
			searchDto.setAttributeTrees(attributeTrees);
		}
		int length = attributeTrees.size();
		for (int i = 0; i < length; i++) {
			GroupTree groupTree = attributeTrees.get(i);
			String name = groupTree.getGroup().getGroupName();
			if (groupTree == null || StringUtils.isEmpty(name) || !filterMap.containsKey(name))
				continue;
			Attibute attr = filterMap.get(name);
			//设置属性显示名称
			if (attr.getStatus() == 1) {
				attributeTrees.remove(groupTree);
				i--;
				length--;
				continue;
			} else {
				groupTree.getGroup().setDisPlayName(attr.getDisplayName());
				Set<String> set = attr.getValues();
				List<GroupTree> subGroupTrees = groupTree.getGroupTree();
				if (!CollectionUtils.isEmpty(set) && !CollectionUtils.isEmpty(subGroupTrees)){
					int sublength = subGroupTrees.size();
					for (int j = 0; j < sublength; j++) {
						GroupTree subGroupTree = subGroupTrees.get(j);
						String subName = subGroupTree.getGroup().getGroupName();
						if (StringUtils.isEmpty(subName) || !set.contains(subName))
							continue;
						subGroupTrees.remove(subGroupTree);
						j--;
						sublength--;
					}
				}
				String[] attrValueRanks = attr.getRankArray();
				if(attrValueRanks != null && attrValueRanks.length > 0){//如果排序值大于1 则进行排序
					groupTree.setGroupTree(rankAttrValue(subGroupTrees, attrValueRanks));
				}
			}
		}
	}
	
	public static String[] getAttrRanks(Map<String, Attibute> filterMap){
		Attibute attibute = filterMap.get("ALL");
		if(attibute == null) return new String[0];
		return attibute.getKeyRes().getRankArray();
	}
	
	public static List<GroupTree> rankAttrValue(List<GroupTree> subGroupTrees, String[] attrValueRanks){
		List<GroupTree> rankTop = new ArrayList<GroupTree>();
		for(String attrValue : attrValueRanks){
			if(attrValue == "ALL") continue;
			GroupTree contain = contain(subGroupTrees, attrValue);
			if(contain != null){
				subGroupTrees.remove(contain);
			}else{
				contain = new GroupTree();
				contain.setGroup(new Group(attrValue, 0, true, attrValue));
			}
			rankTop.add(contain);
		}
		rankTop.addAll(subGroupTrees);
		return rankTop;
	}
	
	public static GroupTree contain(List<GroupTree> subGroupTrees, String attrValue){
		for(GroupTree groupTree : subGroupTrees){
			if(attrValue.equals(groupTree.getGroup().getGroupName())){
				return groupTree;
			}
		}
		return null;
	}
	
	public boolean contain(String[] attrValueRanks, String subName){
		if(attrValueRanks.length < 1) return false;
		for(String attrValue : attrValueRanks){
			if(attrValue.equals(subName)) return true;
		}
		return false;
	}
	
	protected static List<LinkDto> createSourceLinks(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		if (!searchDto.getGroups().containsKey("Source")) {
			return CollectionTools.newListWithSize(0);
		}
		// 排序商家，将拥有商品数量最多的商家放到最前面
		GroupTree root = searchDto.getGroups().get("Source");
		if (root == null) {
			return CollectionTools.newListWithSize(0);
		}
		Sf1DataHelper.sortGroupTreeByDesc(root);
		return LinkDtoHelper.generateSourceLinks(request, response, root.getGroupTree(), basePath);
	}
	
	protected static List<AttrLinkDto> createAttrList(SearchDTO searchDto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		List<GroupTree> attributeTrees = searchDto.getAttributeTrees();
		List<AttrLinkDto> attrLinkDtos = CollectionTools.newListWithSize(5);
		AttrLinkDto brandAttrLink = null;
		for (GroupTree groupTree : attributeTrees) {
			if (groupTree == null || StringUtils.isEmpty(groupTree.getGroup().getGroupName()))
				continue;
			AttrLinkDto attrLinkDto = new AttrLinkDto();
			attrLinkDto.setName(groupTree.getGroup().getGroupName());
			String displayName = groupTree.getGroup().getDisPlayName();
			if(!StringTools.isEmpty(displayName)){
				attrLinkDto.setName(displayName);
			}
			groupTree.setGroupTree(CollectionTools.subList(groupTree.getGroupTree(), 0, 30));
			attrLinkDto.setAttrList(LinkDtoHelper.generateAttrLinks(request, response, groupTree, basePath));
			if (!attrLinkDto.getAttrList().isEmpty()) {
				if (!"品牌".equals(groupTree.getGroup().getGroupName())) {
					attrLinkDtos.add(attrLinkDto);
				} else {
					brandAttrLink = attrLinkDto;
				}
			}
		}
		if (brandAttrLink != null)
			attrLinkDtos.add(0, brandAttrLink);
		return attrLinkDtos;
	}
	
	private static List<LinkDto> createPriceLinks(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		if (!searchDto.getGroups().containsKey("Price")) {
			return CollectionTools.newListWithSize(0);
		}
		GroupTree grougRoot = searchDto.getGroups().get("Price");
		List<GroupTree> groupTrees = grougRoot.getGroupTree();
		List<String> priceRangs = CollectionTools.newListWithSize(groupTrees.size());
		for (GroupTree groupTree : groupTrees) {
			priceRangs.add(groupTree.getGroup().getGroupName());
		}
		return LinkDtoHelper.generatePriceRangeLinks(request, response, basePath, priceRangs.toArray(new String[] {}));
	}
	
	public static List<LinkDto> createRelatedQueryLinks(SearchDTO searchDto, HttpServletRequest request, HttpServletResponse response, String basePath) {
		List<LinkDto> linkDtos = new ArrayList<LinkDto>();
		List<String> relatedQueries = searchDto.getRelatedQueries();
		if (relatedQueries.isEmpty())
			return linkDtos;
		for (String relatedQuery : relatedQueries) {
			linkDtos.add(LinkDtoHelper.generateRelatedQueryLinkDto(request, relatedQuery, response, basePath));
		}
		return linkDtos;
	}
	
	public static ShopListDto createShopListDto(SearchDTO searchDto, SuiSearchDto dto, HttpServletRequest request, HttpServletResponse response) {
		ShopListDto shopList = new ShopListDto();
		String basePath = getServerpath(request);
		// 创建商家文本和超链接
		shopList.setSourceLinks(createSourceLinks(searchDto, dto, request, response, basePath));
        //类目排序
		Sf1DataHelper.sortCategoryTree(searchDto.getTopGroups(), searchDto.getCategoryTree());
		// 设置分类列表的链接
		shopList.setCategoryList(LinkDtoHelper.generateCategoryTreeLinks(request, response, searchDto.getCategoryTree(), basePath));
		// 过滤属性
		List<AttrLinkDto> attrLinkDtos = createAttrList(searchDto, request, response, basePath);
		shopList.setAttrLinkDtos(attrLinkDtos);
		//总商品数量
		shopList.setItemCount(searchDto.getTotalCount());
		//过滤属性链接
		shopList.setFilterList(LinkDtoHelper.generateFilterLinks(request, response, basePath));
		//价格区间链接
		shopList.setPriceList(createPriceLinks(searchDto, dto, request, response, basePath));
		//生成数据
		PageView<DocResourceDto> pageView = LinkDtoHelper.getPageView(dto.getCurrPageNo(), dto.getPageSize(), searchDto.getTotalCount(), 5);
		//分页 页码上的 模版链接
		pageView.setUrl(LinkDtoHelper.generatePageCodeLink(request, response, basePath));
		//分页数据
		pageView.setRecords(searchDto.getDocResourceDtos());
		shopList.setPageView(pageView);
		//替换路径替换，利用多域名进行显示，为了图片更快的加载
		ImageUtils.replaceImgUrl(searchDto.getResourcesList());
		//排序链接
		if (null != request.getAttribute("path"))
			shopList.setSortList(LinkDtoHelper.generateSortLinks(request, response, basePath));
		else
			shopList.setSortList(LinkDtoHelper.generateSortLinks(request, response, basePath));
		shopList.setCfgLinks(LinkDtoHelper.generateCFGLink(request, response, basePath));
        //商品相关联链接
		shopList.setRelatedQueryList(createRelatedQueryLinks(searchDto, request, response, basePath));
		//所需要显示价格趋势的 docids
		shopList.setPriceTrendDocIds(getPriceTrendDocIds(searchDto));
		//设置第一个页面的路径地址
		shopList.setFirstPageUrl(LinkDtoHelper.generateFirstPageLink(request, response, basePath));
		return shopList;
	}
	
	public static String getCollection(String channel){
		if("taosha".equals(channel)) return "taosha";
		return "b5mp";
	}
	
	private static String getServerpath(HttpServletRequest req){
		/*int index = requestUrl.indexOf("/s/");
		if (index > 0 && requestUrl.indexOf("WEB-INF") < 0) {//有个s渠道存在
			if(requestUrl.indexOf("/s/", index + 2) > 0){
				index = requestUrl.indexOf("/s/", index + 2);
			}
			return requestUrl.substring(0, index);
		}*/
		if(req.getServerPort() != 80){
			String requestUrl = req.getRequestURL().toString();
			int index = requestUrl.indexOf("/s/");
			if(index > 0 && requestUrl.indexOf("WEB-INF") < 0){
				if(requestUrl.indexOf("/s/", index + 2) > 0){
					index = requestUrl.indexOf("/s/", index + 2);
				}
				return requestUrl.substring(0, index);
			}
		}
		return req.getScheme() + "://" + req.getServerName();
	}
}
