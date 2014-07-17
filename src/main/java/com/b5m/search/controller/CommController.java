package com.b5m.search.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.search.service.CommService;
import com.b5m.search.sys.ContextUtils;
import com.b5m.search.utils.SearchResultHelper;
/**
 * @description
 * 主要针对各页面异步调用
 * @author echo
 * @time 2014年5月28日
 * @mail wuming@b5m.com
 */
@Controller
public class CommController {
	
	@Autowired
	private CommService commService;
	
	//相关商品
	@RequestMapping(value = "/{channel}/s/relGoods")
	@ResponseBody
	public List<Map<String, String>> recommandProduces(@PathVariable("channel") String channel, String docId, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize) {
		return commService.getRecommandProduces(docId, title, pageSize, SearchResultHelper.getCollection(channel));
	}
	
	@RequestMapping(value = "/{channel}/s/token")
	@ResponseBody
	public String generatorToken(String token) throws Exception{
		if(StringUtils.isEmpty(token)) return "请输入token 名称";
		return ContextUtils.generatorToken(token);
	}
	
	@RequestMapping(value = "/token")
	@ResponseBody
	public String _generatorToken(String token) throws Exception{
		if(StringUtils.isEmpty(token)) return "请输入token 名称";
		return ContextUtils.generatorToken(token);
	}
	
}
