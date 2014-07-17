package com.b5m.search.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b5m.search.bean.dto.GoodsDetailDto;
import com.b5m.search.service.GoodsDetailService;
import com.b5m.search.sys.Channel;
import com.b5m.search.sys.ContextUtils;

@Controller
public class GoodsDetailController {
	@Resource
	private GoodsDetailService detailService;
	
	@RequestMapping("/{channel}/s/item/{docId}")
	public String getGoodsDetailItem(ModelMap model, @PathVariable("channel") String channel, @PathVariable("docId") String docId, HttpServletResponse res, HttpServletRequest request) throws IOException {
		Channel _channel = ContextUtils.getChannel(channel);
		GoodsDetailDto goodsDetailDto = detailService.queryItemDetailByDocId(_channel, docId);
		request.setAttribute("goodsDetailDto", goodsDetailDto);
		return goodsDetailDto.getResultPage();
	}
	
}
