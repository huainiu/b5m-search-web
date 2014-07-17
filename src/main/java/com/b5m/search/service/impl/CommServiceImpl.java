package com.b5m.search.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.base.common.utils.StringTools;
import com.b5m.search.service.CommService;
import com.b5m.sf1api.dto.req.SF1SearchBean;
import com.b5m.sf1api.dto.req.search.SuffixMode;
import com.b5m.sf1api.dto.res.SearchDTO;
import com.b5m.sf1api.service.Sf1Query;
import com.b5m.sf1api.utils.SearchCndBuilder;

@Service
public class CommServiceImpl implements CommService{
	@Autowired
	private Sf1Query sf1Query;
	
	@Override
	public List<Map<String, String>> getRecommandProduces(String docid, String keyword, Integer pageSize, String collection) {
		SF1SearchBean sf1rBean = createRecommandProduceSF1SearchBean(keyword, pageSize, collection);
		SearchDTO searchDTO = sf1Query.doSearch(sf1rBean);
		List<Map<String, String>> result = searchDTO.getResourcesList();
		removeSameProduce(docid, result);
		return result;
	}

	private SF1SearchBean createRecommandProduceSF1SearchBean(String keyword, Integer pageSize, String collection) {
		SF1SearchBean sf1rBean = new SF1SearchBean();
		if (StringUtils.isBlank(keyword)) {
			keyword = "*";
		}
		SearchCndBuilder builder = sf1rBean.builder();
		builder.collection(collection).page(pageSize, 0).getAttr(false, 20).keywords(keyword);
		changeSearchMode(builder, collection);
		// 清除分组
		return sf1rBean;
	}
	
	private void changeSearchMode(SearchCndBuilder builder, String collection){
		if(!"b5mp".equals(collection)){ return; }
		SuffixMode searchingMode = new SuffixMode();
		searchingMode.setFuzzyThreshold(0.1f);
		searchingMode.setTokensThreshold(1f);
		builder.collection("b5mo").addMode(searchingMode);
	}
	
	// 移除与大图相同的产品,如果不存在与大图相同的产品，移除最后一个产品
	private void removeSameProduce(String docid, List<Map<String, String>> result) {
		if(StringTools.isEmpty(docid)) return;
		int resultLength = result.size();
		for (int i = 0; i < resultLength; i++) {
			String resultDocid = result.get(i).get("DocId");
			if (docid.equals(resultDocid) || (i == result.size() - 1)) {
				result.remove(i);
				return;
			}
		}
	}
}
