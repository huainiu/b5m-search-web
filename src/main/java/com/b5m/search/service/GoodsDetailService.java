package com.b5m.search.service;

import com.b5m.search.bean.dto.GoodsDetailDto;
import com.b5m.search.sys.Channel;
/**
 * @description
 * 商品详情服务类
 * @author echo
 * @time 2014年6月16日
 * @mail wuming@b5m.com
 */
public interface GoodsDetailService {
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 查询商品详情信息
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年6月16日 上午11:04:59
	 *
	 * @param channel
	 * @param docId
	 * @return
	 */
	GoodsDetailDto queryItemDetailByDocId(Channel channel, String docId);
	
}
