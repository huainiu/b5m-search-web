package com.b5m.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.ThreadTools;
import com.b5m.search.bean.dto.SuiSearchDto;
import com.b5m.search.sys.Channel;
import com.b5m.search.utils.SearchResultHelper;
import com.b5m.sf1api.dto.req.SF1SearchBean;
import com.b5m.sf1api.dto.res.SearchDTO;
import com.b5m.sf1api.service.Sf1Query;

/**
 * @Company B5M.com
 * @description sf1 查询
 * @author echo
 * @since 2013-8-22
 * @email wuming@b5m.com
 */
public class SF1NewQueryService {
	private ExecutorService threadPool;
	private static final Log LOG = LogFactory.getLog(SF1NewQueryService.class);
	
	@Autowired
	private Sf1Query sf1Query;
	
	public SF1NewQueryService() {}

	public SF1NewQueryService(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
	
	public SearchDTO search(SuiSearchDto dto) {
		if(StringUtils.isEmpty(dto.getCollectionName())){
			dto.setCollectionName("b5mp");
		}
		dto.setRequireRelated(true);
		SF1SearchBean searchBean = SearchResultHelper.convertTo4Search(dto);
		SearchDTO searchDTO = sf1Query.doSearch(searchBean);
		return searchDTO;
	}
	
	public SearchDTO search(SuiSearchDto dto, String collectionName) {
		dto.setCollectionName(collectionName);
		dto.setRequireRelated(true);
		SF1SearchBean searchBean = SearchResultHelper.convertTo4Search(dto);
		SearchDTO searchDTO = sf1Query.doSearch(searchBean);
		return searchDTO;
	}
	
	public SearchDTO[] multiSearch(SuiSearchDto dto) {
		// sf1 返回的 分词关键词
		String newKeyword = dto.getKeyword();// searchDTO.getAnalyzerResult();
		if (StringUtils.isEmpty(newKeyword)) {
			return new SearchDTO[0];
		}
		if (newKeyword.indexOf(" ") < 0 && newKeyword.equals(dto.getKeyword()))
			return new SearchDTO[0];
		String[] keywords = newKeyword.split(" ");
		SearchDTO[] searchDTOs = reQuery(keywords);
		List<SearchDTO> searchDTOList = CollectionTools.newListWithSize(3);
		for (SearchDTO searchDtoWrap : searchDTOs) {
			if (searchDtoWrap == null || searchDtoWrap.getDocResourceDtos().isEmpty())
				continue;
			searchDTOList.add(searchDtoWrap);
		}
		return searchDTOList.toArray(new SearchDTO[] {});
	}
	
	public String queryCorrect(String keywords) {
		List<String> refinedQuery = sf1Query.getRefined_query("b5mp", keywords);
		if (refinedQuery.isEmpty())
			return keywords;
		String refine = refinedQuery.get(0);
		if (StringUtils.isEmpty(refine))
			return keywords;
		return refine;
	}
	
	public JSONObject doGet(Channel channel, String docId){
		SF1SearchBean searchBean = SearchResultHelper.converTo4Get(channel, docId);
		return sf1Query.doGetForJson(searchBean);
	}

	protected SearchDTO[] reQuery(String[] keywords) {
		List<String> keywordList = needSearchKeywords(keywords);
		List<Callable<SearchDTO>> queryTasks = new ArrayList<Callable<SearchDTO>>();
		for (final String keyword : keywordList) {
			queryTasks.add(new Callable<SearchDTO>() {

				@Override
				public SearchDTO call() throws Exception {
					SuiSearchDto dto = new SuiSearchDto();
					dto.setKeyword(keyword);
					dto.setPageSize(5);
					dto.setCurrPageNo(1);
					SearchDTO searchDto = search(dto);
					searchDto.setKeywords(keyword);
					return searchDto;
				}

			});
		}
		return ThreadTools.executor(queryTasks, SearchDTO.class, threadPool);
	}

	public boolean needReSearch(SuiSearchDto dto) {
		return StringUtils.isEmpty(dto.getCategoryValue())
				&& StringUtils.isEmpty(dto.getIsCOD())
				&& StringUtils.isEmpty(dto.getIsFreeDelivery())
				&& StringUtils.isEmpty(dto.getIsGenuine())
				&& (dto.getCurrPageNo() == null || (dto.getCurrPageNo() != null && dto
						.getCurrPageNo() == 1))
				&& StringUtils.isEmpty(dto.getPriceFrom())
				&& StringUtils.isEmpty(dto.getPriceTo())
				&& StringUtils.isEmpty(dto.getSourceValue());
	}

	/**
	 * @description 最多用三个 keywords 进行查询
	 * @param keywords
	 * @return
	 * @author echo
	 * @since 2013-9-6
	 * @email echo.weng@b5m.com
	 */
	protected List<String> needSearchKeywords(String[] keywords) {
		List<String> keywordList = new ArrayList<String>(3);
		if (keywords.length < 3) {
			for (String word : keywords) {
				keywordList.add(word);
			}
		} else {
			for (int i = 0; i <= 2; i++) {
				keywordList.add(keywords[i]);
			}
		}
		return keywordList;
	}
	
}
