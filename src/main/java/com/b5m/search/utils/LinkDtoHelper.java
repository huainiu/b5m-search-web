package com.b5m.search.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.StringTools;
import com.b5m.dao.domain.page.PageView;
import com.b5m.search.bean.dto.FilterLinkDto;
import com.b5m.search.bean.dto.LinkDto;
import com.b5m.search.bean.dto.LinkTreeDto;
import com.b5m.search.bean.dto.SortType;
import com.b5m.search.sys.ContextUtils;
import com.b5m.sf1api.dto.res.GroupTree;

public class LinkDtoHelper {
	public static final String[] REQUEST_PARAMS = new String[]{"sort_field", "sort_type", "sources", "attrs", "sprice", "eprice","pageNo", "size", "free_delivery","cod","genuine","cat","key"};
	
	public static Map<String, String> initParameters(Map<String, String> parameters){
		parameters.put(REQUEST_PARAMS[6], "");
		return parameters;
	}
	
	/**
	 * 将请求中的请求参数clone到一个新的Map容器里。
	 * @param requestParams
	 * @return
	 */
	public static Map<String, String> cloneParameters(Map<String, String[]> requestParams){
		Map<String, String> newParams = new HashMap<String, String>();
		Set<String> keySet = requestParams.keySet();
		for(String key : keySet){
			if(requestParams.get(key).length > 0)
				newParams.put(key, requestParams.get(key)[0]);
		}
		return newParams;
	}
	
	public static String generateContextPathBase(HttpServletRequest request){
		StringBuilder sb = new StringBuilder(request.getContextPath());
		sb.append(request.getRequestURI());
		String path = sb.toString();
		if(path.indexOf("/") == 0){
			return path.substring(1);
		}
		return path;
	}
	
	/**
	 * 生成静态路径
	 * @param parameters 请求的参数
	 * @param contextPathBase 这个请求地址以及路径，但不包含最后的静态文件的名称
	 * @param response 这个必须是UrlRewriter包装后的Response类型
	 * @return
	 */
	public static String generateUrl(Map<String, String> parameters, String contextPathBase, HttpServletRequest request, HttpServletResponse response){
		boolean isCatUrl = isCatUrl(request);
		
		String category = (String) request.getAttribute(REQUEST_PARAMS[11]);
		String keywords = (String) request.getAttribute(REQUEST_PARAMS[12]);
		StringBuilder sb = new StringBuilder(100);
		sb.append(contextPathBase).append("/s/");
		String cat = parameters.get(REQUEST_PARAMS[11]);
		if(!StringUtils.isEmpty(cat)){
			category = cat; 
		}
		String pageNum = parameters.get(REQUEST_PARAMS[6]);
		boolean isfirst = true;
		boolean needUrlEncode = true;
		
		StringBuffer params = new StringBuffer();
		for(int index = 0; index < REQUEST_PARAMS.length; index++){
			if(index == 11 || index == 6 || index == 12) continue;
			String value = parameters.get(REQUEST_PARAMS[index]);
			if(StringTools.isEmpty(value)){
				continue;
			}
			needUrlEncode = false;
			params.append(REQUEST_PARAMS[index]).append("=").append(value).append("&");
		}
		if(!isCatUrl || !needUrlEncode){
			sb.append("Search");
		}
		if(isCatUrl && needUrlEncode){
			if(!StringUtils.isEmpty(category)){
				String categoryCode = ContextUtils.getCodeByCategory(category);
				sb.append(categoryCode);
			}
			sb.append("-").append(DataUtils.strEncode(keywords));
			if(!StringUtils.isEmpty(pageNum)){
				sb.append("-").append(pageNum);
			}
			sb.append(".html?");
		}else{
			isfirst = false;
			sb.append("?").append(REQUEST_PARAMS[12]).append("=").append(keywords);
			if(!StringUtils.isEmpty(category)){
				sb.append("&").append(REQUEST_PARAMS[11]).append("=").append(category);
			}
			if(!StringUtils.isEmpty(pageNum)){
				sb.append("&").append(REQUEST_PARAMS[6]).append("=").append(pageNum);
			}
		}
		if(params.length() > 0){
			if(!sb.toString().endsWith("&")){
				sb.append("&");
			}
			params.deleteCharAt(params.length() - 1);
			sb.append(params);
		}
		
		Enumeration<String> keys = request.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			if(contain(key)) continue;
			String value = request.getParameter(key);
			if(StringTools.isEmpty(value)){
				continue;
			}
			if(isfirst){
				sb.append("?");
				isfirst = false;
			}else{
				sb.append("&");
			}
			sb.append(key).append("=").append(value);
		}
		String url = sb.toString();
		if(url.endsWith("?") || url.endsWith("&")){
			url = url.substring(0, url.length() - 1);
		}
		//利用绝对路径
		return url;
	}
	
	//判断是否是类目url
	private static boolean isCatUrl(HttpServletRequest request){
		return request.getAttribute("_cat_url_") != null && request.getAttribute("_cat_url_").toString().length() > 0;
	}
	
	private static boolean contain(String key){
		for(int index = 0; index < REQUEST_PARAMS.length; index++){
			if(REQUEST_PARAMS[index].equals(key)) return true;
		}
		return false;
	}
	
	public static String getBasePath(HttpServletRequest request){
		String server = request.getServerName();
		//首页需要全局的资源引入, by holin
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(server).append(":").append(request.getServerPort()).append(request.getContextPath()).append("/");
		return sb.toString();
	}
	
	private static String mergeParameterValue(String originalValue, String appendValue){
		if(null == originalValue || "".equals(originalValue)){
			return appendValue;
		}
		return new StringBuilder(originalValue).append(",").append(appendValue).toString();
	}
	
	/**
	 * 去掉已选中的链接，一般{@link #generateBrandLinks(HttpServletRequest, HttpServletResponse, List)}和 {@link #generateSourceLinks(HttpServletRequest, HttpServletResponse, List)}
	 * 会使用到这个函数
	 * @param links
	 * @param selected
	 * @return
	 */
	private static List<LinkDto> filterSelectedLinks(List<LinkDto> links, String[] selectedValues){
		if(selectedValues == null) return links;
		int length = links.size();
		for(String selected : selectedValues){
			if(StringUtils.isEmpty(selected)) continue;
			for(int i = 0; i < length; i++){
				if(!links.isEmpty()){
					LinkDto link = links.get(i);
					if(link.getText().equals(selected)){
						length--;
						links.remove(link);
						break;
					}
				}
			}
		}
		return links;
	}
	
	private static String[] getParameterValues(HttpServletRequest request, int paramIndex){
		String[] values = (String[])request.getParameterValues(REQUEST_PARAMS[paramIndex]);
		if(values == null || values.length == 0)
			return values;
		//如果 手机屏幕:3.5英寸这种形式的，则先将 冒号前面的去掉 (手机屏幕:)
		String[] parameterValues = StringUtils.split(values[0], ",");
		for(int index = 0; index < parameterValues.length; index++){
			String parameterValue = parameterValues[index];
			if(parameterValue.indexOf(":") > 0){
				String value = parameterValue.split(":")[1];
				parameterValues[index] = value;
			}
		}
		return parameterValues;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto generateSourceLink(HttpServletRequest request, HttpServletResponse response, GroupTree tree, String basePath){
		LinkDto link = new LinkDto();
		link.setText(tree.getGroup().getGroupName());
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		parameters.put(REQUEST_PARAMS[2], mergeParameterValue(parameters.get(REQUEST_PARAMS[2]), tree.getGroup().getGroupName()));
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	/**
	 * description
	 * 创建 免运费 正品行货 货到付款 链接
	 * @param request
	 * @param response
	 * @return
	 * @author echo weng
	 * @since 2013-6-8
	 * @mail echo.weng@b5m.com
	 */
	@SuppressWarnings("unchecked")
	public static LinkDto[] generateCFGLink(HttpServletRequest request, HttpServletResponse response, String basePath){
	    LinkDto[] links = new LinkDto[3];
        Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
        String $8 = parameters.get(REQUEST_PARAMS[8]);
        String $9 = parameters.get(REQUEST_PARAMS[9]);
        links[0] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[8], basePath);
        parameters.put(REQUEST_PARAMS[8], $8);
        links[1] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[9], basePath);
        parameters.put(REQUEST_PARAMS[9], $9);
        links[2] = generateCFGCommmonLink(request, response, parameters, REQUEST_PARAMS[10], basePath);
        return links;
	}
	
	public static LinkDto generateCFGCommmonLink(HttpServletRequest request, HttpServletResponse response, Map<String, String> parameters, String key, String basePath){
	    LinkDto link = new LinkDto();
	    String value = parameters.get(key);
        if("1".equals(value)){
            parameters.put(key, "");
        }else{
            parameters.put(key, "1");
        }
        link.setUrl(generateUrl(parameters, basePath, request, response));
        return link;
	}
	
	public static List<LinkDto> generateSourceLinks(HttpServletRequest request, HttpServletResponse response, List<GroupTree> groupTrees, String basePath){
		List<LinkDto> linksAll = new LinkedList<LinkDto>();
		List<LinkDto> links = new LinkedList<LinkDto>();
		for(GroupTree groupTree : groupTrees){
			links.add(generateSourceLink(request,response, groupTree, basePath));
		}
		if(!"".equals(request.getParameter(REQUEST_PARAMS[2]))){
			LinkDto[] subLinks = generateFilterLink(request, response, 2, basePath);
			for(LinkDto subLink : subLinks){
				subLink.setText(subLink.getText());
				subLink.setClicked(true);
				linksAll.add(subLink);
			}
		}
		String[] sources = getParameterValues(request, 2);
		linksAll.addAll(filterSelectedLinks(links, sources));
		//现在不过滤
		return linksAll;	
	}
	
	public static List<LinkDto> generateAttrLinks(HttpServletRequest request, HttpServletResponse response, GroupTree attrGroupTree, String basePath){
		List<LinkDto> links = new LinkedList<LinkDto>();
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		String p3 = parameters.get(REQUEST_PARAMS[3]);
		LinkDto link = null;
		List<GroupTree> groups = attrGroupTree.getGroupTree();
		String attrGroupTreeName = attrGroupTree.getGroup().getGroupName();
		if(StringUtils.isEmpty(attrGroupTreeName)) return links;
		
		for(GroupTree groupTree : groups){
			String groupName = groupTree.getGroup().getGroupName();
			if(StringUtils.isEmpty(groupName)) continue;
			link = new LinkDto();
			initParameters(parameters);
			link.setText(groupName);
			String param = attrGroupTreeName + ":" + groupName;
			parameters.put(REQUEST_PARAMS[3], mergeParameterValue(p3,  param));
			link.setUrl(generateUrl(parameters, basePath, request, response));
			links.add(link);
		}
		return filterSelectedLinks(links, getParameterValues(request, 3));
    }
	
	@SuppressWarnings("unchecked")
	private static LinkDto generateCategoryLink(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, String basePath){
		LinkDto link = new LinkDto();
		link.setText(groupTree.getGroup().getGroupName());
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String category = (String)(request.getAttribute(REQUEST_PARAMS[11]));
		if(!StringTools.isEmpty(category) && category.indexOf(">") > 0){
			if(category.equals(groupTree.getGroup().getWholeGroupName()) || groupTree.getGroup().getWholeGroupName().equals(category.substring(0, category.lastIndexOf(">")))){
				link.setClicked(true);
			}
		}
		parameters.put(REQUEST_PARAMS[11], groupTree.getGroup().getWholeGroupName());
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	private static LinkTreeDto _generateCategoryTreeLinks(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, int layer, String basePath){
		LinkTreeDto tree = new LinkTreeDto();
		tree.setLink(generateCategoryLink(request, response, groupTree, basePath));
		if(layer <= 0)
			return tree;
		for(GroupTree childtree : groupTree.getGroupTree()){
			tree.getLinkTree().add(_generateCategoryTreeLinks(request, response, childtree, layer - 1, basePath));
		}
		return tree;
	}
	
	/**
	 * 生成分类链接集合，根表示的是所有分类
	 * @param request
	 * @param response
	 * @param groupTrees
	 * @return
	 */
	public static LinkTreeDto generateCategoryTreeLinks(HttpServletRequest request, HttpServletResponse response, GroupTree groupTree, String basePath){
		groupTree.getGroup().setGroupName("ALL");
		groupTree.getGroup().setWholeGroupName("ALL");
		LinkTreeDto tree = new LinkTreeDto();
		tree.setLink(generateCategoryLink(request, response, groupTree, basePath));
		String category = (String)(request.getAttribute(REQUEST_PARAMS[11]));
		int layer = 0;
		// 如果有层级关系
		// 只取根目录下的第一个
		if(!"".equals(category) && null != category){
			layer = StringTools.split(category, ">").length;
			request.setAttribute("categoryLayer", layer);
		}
		request.setAttribute("categoryLayer", layer);
		return _generateCategoryTreeLinks(request, response,  groupTree, 3, basePath);
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generatePageLinkDto(HttpServletRequest request, HttpServletResponse response, String pageNo, String basePath){
		LinkDto link = new LinkDto();
		link.setText(pageNo);
		Map<String, String> parameters = cloneParameters(request.getParameterMap());
		parameters.put(REQUEST_PARAMS[6], link.getText());
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	@SuppressWarnings("rawtypes")
	public static PageView getPageView(int currentPage, int pageSize, long totalRecord, int pageCode){
		PageView pageView = new PageView(pageSize, currentPage);
		pageView.setTotalRecord(totalRecord);
		if(pageView.getTotalPage() > 1000){//页面最大显示1000页
			pageView.setTotalPage(1000);
		}
		pageView.setPageCode(pageCode);
		return pageView;
	}
	
	public static String generatePageCodeLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		LinkDto link = generatePageLinkDto(request, response, "{pageCode}", basePath);
		return link.getUrl();
	}
	
	public static String generateFirstPageLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		LinkDto link = generatePageLinkDto(request, response, "", basePath);
		return link.getUrl();
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto generatePriceRangeLink(HttpServletRequest request, HttpServletResponse response, String priceRange, String basePath){
		LinkDto link = new LinkDto();
		String[] prices = priceRange.split("-");
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String priceFrom = prices[0];
		String priceTo = "";
		if(prices.length == 2){
			priceTo = prices[1];
		}
		parameters.put(REQUEST_PARAMS[4], priceFrom);
		parameters.put(REQUEST_PARAMS[5], priceTo);
		link.setUrl(generateUrl(parameters, basePath, request, response));
		link.setText(priceRange);
		return link;
	}
	
	/**
	 * 创建价格区间的链接DTO
	 * @param request
	 * @param response
	 * @return
	 */
	public static List<LinkDto> generatePriceRangeLinks(HttpServletRequest request, HttpServletResponse response, String basePath, String[] priceRanges){
		List<LinkDto> links = new ArrayList<LinkDto>();
		for(String priceRange : priceRanges){
			links.add(generatePriceRangeLink(request, response, priceRange, basePath));
		}
		return links;
	}
	
	private static String generateExcludeValueFromArray(List<String> list, String excluded){
		List<String> temp = new ArrayList<String>();
		for(String str : list){
		    if(!excluded.equals(str)){
		        temp.add(str);
		    }
		}
		String str = StringUtils.join(temp.iterator(), ",");
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkDto[] generateFilterLink(HttpServletRequest request, HttpServletResponse response, int paramIndex, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String value = Lang.toStrNotNull(parameters.get(REQUEST_PARAMS[paramIndex]));
		String[] array = value.split(",");
		List<String> tempList = new ArrayList<String>();
		for(int i = 0; i < array.length; i ++){
		    String str = array[i].trim();
		    if(!"".equals(str)){
		        tempList.add(str);
		    }
		}
		LinkDto[] links = new LinkDto[tempList.size()];
		int index = 0;
		for(String excluded : tempList){
			String excludedValue = generateExcludeValueFromArray(tempList, excluded);
			parameters.put(REQUEST_PARAMS[paramIndex], excludedValue);
			LinkDto link = new LinkDto();
			link.setText(excluded);
			link.setUrl(generateUrl(parameters, basePath, request, response));
			links[index++] = link;
		}
		return links;
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generatePriceFilterLink(HttpServletRequest request, HttpServletResponse response, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		String priceFrom = parameters.get(REQUEST_PARAMS[4]);
		String priceTo = parameters.get(REQUEST_PARAMS[5]);
		String text = "";
		if(!Lang.isEmpty(priceFrom) && !Lang.isEmpty(priceTo)){
			text = new StringBuilder(priceFrom).append("-").append(priceTo).toString();
		}else if(!Lang.isEmpty(priceFrom)){
			text = new StringBuilder(priceFrom).append("以上").toString();
		}else{
			text = new StringBuilder(priceTo).append("以下").toString();
		}
		// 设置priceFrom为""
		parameters.put(REQUEST_PARAMS[4], "");
		// 设置priceTo为""
		parameters.put(REQUEST_PARAMS[5], "");
		LinkDto link = new LinkDto();
		link.setText(text);
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	/**
	 * 通过请求参数获取过滤条件
	 * @param parameterName
	 * @return
	 */
	private static String findFilterType(String parameterName){
		if(parameterName.equals(REQUEST_PARAMS[2])){
			return "商家";
		}
		if(parameterName.equals(REQUEST_PARAMS[4]) || parameterName.equals(REQUEST_PARAMS[5]))
			return "价格";
		return "";
	}
	
	/**
	 * 生成过滤区域的链接DTO
	 * @param request
	 * @param response
	 * @return
	 */
	public static List<FilterLinkDto> generateFilterLinks(HttpServletRequest request, HttpServletResponse response, String basePath){
		List<FilterLinkDto> links = new ArrayList<FilterLinkDto>();
		//商家不在过滤列表中了
		if(!Lang.isEmpty(request.getParameter(REQUEST_PARAMS[3]))){
			LinkDto[] subLinks = generateFilterLink(request, response, 3, basePath);
			for(LinkDto subLink : subLinks){
				FilterLinkDto filterLink = new FilterLinkDto();
				String text = subLink.getText();
				//属性中出现@@的 则用_进行替换
				String[] attrFilterStrs = StringUtils.split(text, ":");
				subLink.setText(attrFilterStrs[1]);
				filterLink.setFilterType(attrFilterStrs[0]);
				filterLink.setLink(subLink);
				links.add(filterLink);
			}
		}
		if(!Lang.isEmpty(request.getParameter(REQUEST_PARAMS[4])) || !Lang.isEmpty(request.getParameter(REQUEST_PARAMS[5]))){
			FilterLinkDto filterLink = new FilterLinkDto();
			filterLink.setLink(generatePriceFilterLink(request, response, basePath));
			filterLink.setFilterType(findFilterType(REQUEST_PARAMS[4]));
			links.add(filterLink);
		}
		return links;
	}
	
	/**
	 * 获得排序的Text字符
	 * @param type
	 * @return
	 */
	private static String getSortTypeText(SortType type){
		if(type == SortType.SalesAmount) return "销量";
		if(type == SortType.Price) return "价格";
		if(type == SortType.Date) return "最新";
		return "默认排序";
	}
	
	@SuppressWarnings("unchecked")
	private static LinkDto generateSortLink(HttpServletRequest request, HttpServletResponse response, SortType sortType, String basePath){
		Map<String, String> parameters = initParameters(cloneParameters(request.getParameterMap()));
		LinkDto link = new LinkDto();
		link.setText(getSortTypeText(sortType));
		parameters.put(REQUEST_PARAMS[0], sortType.getValue());
		String sort = parameters.get(REQUEST_PARAMS[1]);
		if(sortType == SortType.Default){
			parameters.put(REQUEST_PARAMS[1], "");
		}else{
			parameters.put(REQUEST_PARAMS[1], "DESC");
		}
		if(sortType == SortType.Price){
			sort = Lang.toStrNotNull(sort).equalsIgnoreCase("ASC")?"DESC":"ASC";
			parameters.put(REQUEST_PARAMS[1], sort);
		}
		if(sortType == SortType.SalesAmount){
			sort = Lang.toStrNotNull(sort).equalsIgnoreCase("DESC")?"ASC":"DESC";
			parameters.put(REQUEST_PARAMS[1], sort);
		}
		link.setUrl(generateUrl(parameters, basePath, request, response));
		return link;
	}
	
	public static List<LinkDto> generateSortLinks(HttpServletRequest request, HttpServletResponse response, String basePath){
		List<LinkDto> links = new ArrayList<LinkDto>();
		links.add(generateSortLink(request, response, SortType.Default, basePath));
		links.add(generateSortLink(request, response, SortType.SalesAmount, basePath));
//		links.add(generateSortLink(request, response, SortType.Date, basePath));
		links.add(generateSortLink(request, response, SortType.Price, basePath));
		return links;
	}
	
	public static List<LinkDto> generateSortLinks(HttpServletRequest request, HttpServletResponse response, String basePath, SortType[] types){
		List<LinkDto> links = new ArrayList<LinkDto>();
		if (types != null & types.length > 0) {
			for (int i = 0; i < types.length; i++) {
				SortType type = types[i];
				links.add(generateSortLink(request, response, type, basePath));
			}
		}
		return links;
	}
	
	public static LinkDto generateRelatedQueryLinkDto(HttpServletRequest request, String relatedQuery, HttpServletResponse response, String basePath){
	    LinkDto link = new LinkDto();
	    if(relatedQuery.indexOf("/") > 0){
	    	relatedQuery = relatedQuery.substring(0, relatedQuery.indexOf("/"));
	    }
	    link.setText(relatedQuery);
	    StringBuilder sb = new StringBuilder(100);
		sb.append(basePath).append("/s/-").append(DataUtils.strEncode(relatedQuery)).append(".html");
		link.setUrl(sb.toString());
		return link;
	}
	
	public static String generateOrgUrl(HttpServletRequest request, HttpServletResponse response, String basePath){
		 Map<String, String> parameterMap = LinkDtoHelper.cloneParameters(request.getParameterMap());
		 return LinkDtoHelper.generateUrl(parameterMap, basePath, request, response);
	}
}