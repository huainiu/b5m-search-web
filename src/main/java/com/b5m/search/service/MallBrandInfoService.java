package com.b5m.search.service;
import java.util.List;

import com.b5m.dao.domain.page.PageView;
import com.b5m.search.bean.entity.MallBrandInfo;

/**
 * description 
 * 商城品牌服务类
 * @Company b5m
 * @author echo
 * @since 2014年1月16日
 */
public interface MallBrandInfoService {
	
	MallBrandInfo queryById(Long id);
	
	/**
	 * description
	 * 分页查询 
	 * @param currentPage
	 * @param pageSize
	 * @param type
	 * @param word 首字母
	 * @return
	 * @author echo weng
	 * @since 2014年2月12日
	 * @mail echo.weng@b5m.com
	 */
	PageView<MallBrandInfo> queryPage(Integer currentPage, Integer pageSize, Integer type, String word);
	
	/**
	 * description
	 *
	 * @param currentPage
	 * @param pageSize
	 * @param type
	 * @param word
	 * @return
	 * @author echo weng
	 * @since 2014年2月17日
	 * @mail echo.weng@b5m.com
	 */
	PageView<MallBrandInfo> queryAllDataPage(Integer currentPage, Integer pageSize, Integer type, String word);
	
	/**
	 * description
	 * 获取查询词
	 * @return
	 * @author echo weng
	 * @since 2014年2月12日
	 * @mail echo.weng@b5m.com
	 */
	String[] queryWords();
	
	/**
	 * description
	 * 随即获取
	 * @param pageSize
	 * @return
	 * @author echo weng
	 * @since 2014年2月12日
	 * @mail echo.weng@b5m.com
	 */
	List<MallBrandInfo> randonPage(Integer pageSize);
	
}
