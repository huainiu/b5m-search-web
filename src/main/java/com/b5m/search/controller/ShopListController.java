package com.b5m.search.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.base.common.utils.StringTools;
import com.b5m.search.bean.dto.LinkDto;
import com.b5m.search.bean.dto.ShopListDto;
import com.b5m.search.bean.dto.SuiSearchDto;
import com.b5m.search.bean.entity.filterAttr.Attibute;
import com.b5m.search.service.MallBrandInfoService;
import com.b5m.search.service.SearchResultService;
import com.b5m.search.service.impl.SF1NewQueryService;
import com.b5m.search.sys.Channel;
import com.b5m.search.sys.ContextUtils;
import com.b5m.search.utils.DataUtils;
import com.b5m.search.utils.LinkDtoHelper;
import com.b5m.search.utils.SearchResultHelper;
import com.b5m.search.utils.ShoplistHelper;
import com.b5m.sf1api.dto.res.SearchDTO;

@Controller
public class ShopListController{
	private static final Log LOG = LogFactory.getLog(ShopListController.class);
	
	@Resource
	protected SF1NewQueryService sf1Search;
	
	@Resource(name = "config")
	protected Properties config;
	
	@Resource(name = "searchResultService")
	protected SearchResultService searchResultService;
	
	@Resource(name = "mallBrandInfoService")
	protected MallBrandInfoService mallBrandInfoService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@RequestMapping("/{channel}/s/Search")
	public String search(@PathVariable("channel") String channel, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SuiSearchDto dto = new SuiSearchDto();
		ResultType resultType = searchGoodsList(channel, dto, model, request, response);
		return resultTo(channel, resultType);
	}
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 编码后的路径
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月4日 下午4:58:45
	 *
	 * @return
	 */
	@RequestMapping("/{channel}/s/-{keyword:[0-9A-Z]+}")
	public String enSearch(@PathVariable("channel") String channel, @PathVariable("keyword") String keyword, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return catSearchPageNum(channel, null, keyword, null, model, request, response);
	}
	
	@RequestMapping("/{channel}/s/-{keyword:[0-9A-Z]+}-{pageNum}")
	public String enSearchPageNum(@PathVariable("channel") String channel, @PathVariable("keyword") String keyword, @PathVariable("pageNum") Integer pageNum, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return catSearchPageNum(channel, null, keyword, pageNum, model, request, response);
	}
	
	@RequestMapping("/{channel}/s/{cat:[0-9]+}-{keyword:[0-9A-Z]+}")
	public String catSearch(@PathVariable("channel") String channel, @PathVariable("cat") String cat, @PathVariable("keyword") String keyword, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return catSearchPageNum(channel, cat, keyword, null, model, request, response);
	}
	
	@RequestMapping("/{channel}/s/{cat:[0-9]+}-{keyword:[0-9A-Z]+}-{pageNum}")
	public String catSearchPageNum(@PathVariable("channel") String channel, @PathVariable("cat") String cat, @PathVariable("keyword") String keyword, @PathVariable("pageNum") Integer pageNum, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		keyword = DataUtils.strDecode(keyword);
		SuiSearchDto dto = new SuiSearchDto();
		dto.setKeyword(keyword);
		dto.setCategoryValue(ContextUtils.getCategoryByCode(cat));
		dto.setCurrPageNo(pageNum);
		request.setAttribute("_cat_url_", true);
		ResultType resultType = searchGoodsList(channel, dto, model, request, response);
		return resultTo(channel, resultType);
	}
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 是否有结果
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月5日 上午11:23:29
	 *
	 * @param dto
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ResultType searchGoodsList(String channel, SuiSearchDto dto, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Channel _channel = ContextUtils.getChannel(channel);
		if(_channel == null){
			response.sendRedirect("http://www.b5m.com/404.html");
		}
		dto.setCollectionName(_channel.getCollection());
		//查询之前进行处理
		beforeSearch(model, dto, _channel, request);
		if (StringUtils.isEmpty(dto.getKeyword()) && StringUtils.isEmpty(dto.getCategoryValue())) {
			return ResultType.NO;
		}
		//title设置
		ShoplistHelper.setTitle(dto, model);
		//是否需要纠错查询
		queryRefineKeywords(dto, model, request);
		//过滤属性查询
		if(_channel.isFilterAttr()){//是否需要属性过滤
			queryFilterAttr(dto);
		}
		// 搜索
		SearchDTO searchDTO = sf1Search.search(dto);
		// 保存查询条件, 和查询业务逻辑无关,前端展示使用
		if (_channel.isNeedMoreResearch() && SearchResultHelper.isNoResult(searchDTO) && SearchResultHelper.needReSearch(dto)) {
			dto.setKeyword(searchDTO.getAnalyzerResult());
			SearchDTO[] searchDTOWraps = sf1Search.multiSearch(dto);
			if (searchDTOWraps.length >= 1 && searchDTOWraps[0] != null) {
				model.addAttribute("priceTrendDocIds", SearchResultHelper.getPriceTrendDocIds(searchDTOWraps[0]));
				model.addAttribute("searchDTOWraps", searchDTOWraps);
				return ResultType.MORE;
			}
		}
		if (SearchResultHelper.isNoResult(searchDTO)) {
			return ResultType.NO;
		}
		if(_channel.isNeedMoreResearch() && SearchResultHelper.needReSearchMore(searchDTO)){
			dto.setKeyword(searchDTO.getAnalyzerResult());
			SearchDTO[] searchDTOWraps = sf1Search.multiSearch(dto);
			if (searchDTOWraps.length >= 1 && searchDTOWraps[0] != null) {
				model.addAttribute("reSearchMore", true);
				model.addAttribute("searchDTOWraps", searchDTOWraps);
			}
		}
		// 过滤掉已经选择了的属性
		if(_channel.isFilterAttr()){
			needRemoveAttr(dto.getFilterMap(), dto.getAttrs());
			//过滤属性，属性排序，属性名显示
			SearchResultHelper.filterAttr(dto.getFilterMap(), searchDTO);
		}
		//组装页面显示的数据
		ShopListDto shopList = SearchResultHelper.createShopListDto(searchDTO, dto, request, response);
		//填充
		fillModeAttr(model, shopList, dto);
		return ResultType.HAVE;
	}
	
	/**
	 * @description 搜索前进行处理
	 * @param dto
	 * @author echo
	 * @since 2013-8-22
	 * @email echo.weng@b5m.com
	 */
	protected void beforeSearch(Model model, SuiSearchDto dto, Channel _channel, HttpServletRequest request) {
		request.setAttribute("autofillName", ContextUtils.getAutofillName(_channel.getCollection()));
		// 每页显示个数
		dto.setPageSize(_channel.getPageSize());
		String categoryValue = dto.getCategoryValue();
		String[] categorys = null;
		if (!StringUtils.isEmpty(categoryValue)) {
			categorys = StringUtils.split(categoryValue, ">");
		}
		String[] params = LinkDtoHelper.REQUEST_PARAMS;
		if(StringUtils.isEmpty(dto.getKeyword())){
			dto.setKeyword(request.getParameter(params[12]));
		}
		if(StringUtils.isEmpty(dto.getCategoryValue())){
			dto.setCategoryValue(request.getParameter(params[11]));
		}
		request.setAttribute("key", dto.getKeyword());//组装url用
		request.setAttribute("cat", dto.getCategoryValue());
		dto.setOrignKeyword(dto.getKeyword());
		//如果不用关键词进行查询 则将搜索的最后一个类目作为关键词进行页面显示
		if (StringUtils.isEmpty(dto.getKeyword())) {
			dto.setKeyword("*");
			if (!StringUtils.isEmpty(categoryValue)) {
				if (categorys != null && categorys.length > 0) {
					request.setAttribute("keywords", categorys[categorys.length - 1]);
					dto.setKeyword(categorys[categorys.length - 1]);
				}
			}
		}
		request.setAttribute("channel", _channel.getName());
		if (categorys != null && categorys.length > 0) {
			//最后一个类目
			request.setAttribute("lastcategory", categorys[categorys.length - 1]);
		}
		String pageNum = request.getParameter(params[6]);
		if(dto.getCurrPageNo() == null){
			dto.setCurrPageNo(1);
		}
		if(dto.getCurrPageNo() == 1){
			if(!StringUtils.isEmpty(pageNum)){
				Pattern pattern = Pattern.compile("(\\d)+"); 
				Matcher matcher = pattern.matcher(pageNum);
				if(matcher.matches()){
					dto.setCurrPageNo(Integer.valueOf(pageNum));
				}
			}
		}
		dto.setSortField(request.getParameter(params[0]));
		dto.setSortType(request.getParameter(params[1]));
		dto.setSourceValue(request.getParameter(params[2]));
		dto.setAttrs(request.getParameter(params[3]));
		dto.setPriceFrom(request.getParameter(params[4]));
		dto.setPriceTo(request.getParameter(params[5]));
		
		String freeDelivery = request.getParameter(params[8]);
		if("1".equals(freeDelivery) || "0".equals(freeDelivery)){
			dto.setIsFreeDelivery(freeDelivery);
		}
		String cod = request.getParameter(params[9]);
		if("1".equals(cod) || "0".equals(cod)){
			dto.setIsCOD(cod);
		}
		String genuine = request.getParameter(params[10]);
		if("1".equals(genuine) || "0".equals(genuine)){
			dto.setIsGenuine(genuine);
		}
		for(int index = 0; index < LinkDtoHelper.REQUEST_PARAMS.length; index++){
			String param = LinkDtoHelper.REQUEST_PARAMS[index];
			request.getParameter(param);
		}
		setConditionToModel(model, dto);
		dto.setCountry(_channel.getCountry());
		model.addAttribute("detailPath", detailPath(_channel));
	}
	
	public String detailPath(Channel _channel){
		String name = _channel.getName();
		if("japan".equals(name)) return "jp";
		if("haiwai".equals(name)) return "haiwaip";
		if("usa".equals(name)) return "usa";
		if("korea".equals(name)) return "korea";
		return "";
	}
	
	public void queryRefineKeywords(SuiSearchDto dto, Model model, HttpServletRequest request){
		String refine = request.getParameter("refine");
		String refineQuery = sf1Search.queryCorrect(dto.getKeyword());
		if (!noNeedSearchCorrect(refine, dto, refineQuery)) {
			model.addAttribute("refineQuery", refineQuery);
			dto.setKeyword(refineQuery);
		}
	}
	
	private boolean noNeedSearchCorrect(String refine, SuiSearchDto dto, String refineQuery) {
		if (!StringUtils.isEmpty(refine) || dto.getKeyword().equals(refineQuery) || StringUtils.isEmpty(refineQuery))
			return true;
		return false;
	}
	
	private void queryFilterAttr(SuiSearchDto dto){
		//先不进行过滤
		Map<String, Attibute> filterMap = searchResultService.queryAttrFilterList(dto.getKeyword());
		dto.setFilterMap(filterMap);
	}

	// 将查询的条件 设置到model中
	protected void setConditionToModel(Model model, SuiSearchDto dto) {
		// 关键字
		ShoplistHelper.setKeywords(model, dto.getKeyword(), dto);
		model.addAttribute("orignKeyword", dto.getOrignKeyword());
		model.addAttribute("sortField", dto.getSortField());
		model.addAttribute("sortType", dto.getSortType());
		// 货到付款
		model.addAttribute("isCOD", dto.getIsCOD());
		// 免运送
		model.addAttribute("isFreeDelivery", dto.getIsFreeDelivery());
		// 正版行货
		model.addAttribute("isGenuine", dto.getIsGenuine());
		// 比价
		model.addAttribute("isCompare", dto.isCompare());
		// 历史最低价
		model.addAttribute("isLowPrice", dto.getIsLowPrice());
	}
	
	// 过滤掉已经选择了的属性
	protected void needRemoveAttr(Map<String, Attibute> filterMap, String attrs){
		if(StringTools.isEmpty(attrs)) return;
		String[] strs = StringTools.split(attrs, ",");
		for(String str : strs){
			String name = StringTools.split(str, ":")[0];
			Attibute value = new Attibute();
			value.setStatus(0);
			value.setName(name);
			filterMap.put(name, value);
		}
	}
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 页面参数设置
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年3月11日 下午1:35:09
	 *
	 * @param model
	 * @param shopList
	 * @param dto
	 */
	protected void fillModeAttr(Model model, ShopListDto shopList, SuiSearchDto dto) {
		model.addAttribute("docResourceDtos", shopList.getPageView().getRecords());

		model.addAttribute("categoryList", shopList.getCategoryList());
		model.addAttribute("sourceLinks", shopList.getSourceLinks());
		// 属性链接
		model.addAttribute("attrsLinks", shopList.getAttrLinkDtos());
		model.addAttribute("itemCount", shopList.getItemCount());
		model.addAttribute("filterList", shopList.getFilterList());
		model.addAttribute("priceList", shopList.getPriceList());
		model.addAttribute("sortList", shopList.getSortList());

		model.addAttribute("pageView", shopList.getPageView());
		model.addAttribute("pageCodeLink", shopList.getPageCodeLink());

		LinkDto[] linkDtos = shopList.getCfgLinks();
		model.addAttribute("freeDeliveryLink", linkDtos[0].getUrl());
		model.addAttribute("codLink", linkDtos[1].getUrl());
		model.addAttribute("genuineLink", linkDtos[2].getUrl());

		model.addAttribute("relatedQueryList", shopList.getRelatedQueryList());
		model.addAttribute("priceTrendDocIds", shopList.getPriceTrendDocIds());
		model.addAttribute("firstPageUrl", shopList.getFirstPageUrl());
		if(!StringUtils.isEmpty(dto.getCategoryValue())){
			String[] cates = StringUtils.split(dto.getCategoryValue(), ">");
			model.addAttribute("cates", cates);
			model.addAttribute("cateLength", cates.length);
		}
	}
	
	public String resultTo(String channel, ResultType type){
		Channel _channel = ContextUtils.getChannel(channel);
		if(ResultType.NO.equals(type)) return _channel.getNoResultPage();
		return _channel.getResultPage();
	}
	
	@RequestMapping("/e")
	@ResponseBody
	public String encode(String name, HttpServletRequest request) throws UnsupportedEncodingException{
		return URLDecoder.decode(name.replace("|","%"), "GBK");
	}
}
