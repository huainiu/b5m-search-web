<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" uri="http://www.b5m.com/tag/page" %>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@ include file="../common/meta.jsp" %>
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/common/common.css?t=${timeVersion}" />
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result_common.css?t=${timeVersion}" />
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result_site.css?t=${timeVersion}" />
</head>
<body style="position: relative;">
	<%@ include file="./include/search.jsp"%>
	<div class="wp">
		<div class="breadcrumb cf mt20 mb20">
			<h2 class="l">
				<!-- <span>&gt;</span> -->
				<c:if test="${fn:length(navigationList.navigations) >= 1}">&nbsp;&nbsp;</c:if>
				<c:choose>
					<c:when test="${refineQuery != null && refineQuery != ''}">
						以下为您显示“<em class="search-key cl-ff78">${refineQuery}</em>”的搜索结果。 仍然搜索：“
						<a href="${serverPath}/s/Search?key=${orignKeyword}&refine=true"><em class="search-key cl-ff78">${orignKeyword}</em></a>”
					</c:when>
					<c:when test="${keyword != '*' || orignKeyword != '*'}">搜索“<em class="search-key search-key cl-ff78">${keyword != '*' ? keyword : orignKeyword}</em>”</c:when>
				</c:choose>
			</h2>
			<div class="r">
				在${includeSourceCount}个商家中，找到相关商品<strong class="product-nums cl-ff78">${itemCount}</strong>个
				<!-- <div class="feedback-box">
					<a href="javascript:void(0)" class="search-feedback popup-trigger" data-target="feed-popup">搜索不好？</a>
					<span class="give-bangdou">送帮钻</span>
				</div> -->
			</div>
		</div>
	</div>
	<div class="wp cf">
		<div class="main l">
			<div class="container">
				<!--横版的分类 s-->
				<div class="m-bord horizontal-category mb20" id="J_filter_cate">
					<c:set var="layer" value="${categoryLayer}"></c:set>
					<c:set var="hasMid" value="true"></c:set>
					<c:choose>
						<c:when test="${layer == 0 || layer == 1}">
							<c:if test="${fn:length(categoryList.linkTree) == 0}">
								<c:set var="hasMid" value="false"></c:set>
							</c:if>
						</c:when>
						<c:when test="${layer == 2 || layer == 3}">
							<c:forEach items="${categoryList.linkTree}" var="category" >
								<c:forEach items="${category.linkTree}" var="categoryLayer1">
									<c:if test="${categoryLayer1.link.clicked}">
										<c:if test="${fn:length(categoryLayer1.linkTree) == 0}">
											<c:set var="hasMid" value="false"></c:set>
										</c:if>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:when>
					</c:choose>
					<c:if test="${hasMid}">
						<div class="s-menu-list">
							<%@ include file="../common/snippet/category_list_mid.jsp"%>
						</div>
					</c:if>
					<%@ include file="../common/snippet/source_list_mid.jsp"%>
				</div>		
				<!--横版的分类 s-->
				<!--检索区 s-->
				<jsp:include page="../common/snippet/filterAttr.jsp"></jsp:include>
				<c:if test="${fn:length(relatedQueryList) > 0}">
					<dl class="intelligent-recommend ${reSearchMore ? 'mt20' : ''}">
						<dt>您是不是想找：</dt>
						<dd>
							<c:forEach items="${relatedQueryList}" var="relatedQuery"><a data-attr="1010" href="${relatedQuery.url}" style="margin-left: 10px;">${relatedQuery.text}</a></c:forEach>
						</dd>
					</dl>
				</c:if>
				<!--检索区 e-->
				<!--搜索条件 e-->
				<div class="sort-bar cf mt20">
					<ul class="sort-item l">
						<c:forEach items="${sortList}" var="sort" varStatus="stat">
							<c:if test="${sort.text=='默认'}">
								<c:set var="cur" value="${empty sortField}" />
							</c:if>
							<c:if test="${sort.text=='销量'}">
								<c:set var="cur" value="${sortField=='SalesAmount'}" />
							</c:if>
							<c:set var="priceCls" value=""></c:set>
							<c:if test="${sort.text=='价格'}">
								<c:set var="cur" value="${sortField=='Price'}" />
								<c:if test="${sortField=='Price'}">
									<c:set var="priceCls" value="${sortType == 'DESC'? 'down' : (sortType == 'ASC' ? 'up' : '')}"></c:set>
								</c:if>
							</c:if>
							<li class="${priceCls == '' ? (cur?'up':'') : priceCls}"><a rel="nofollow" data-attr='1007' class="${sort.text =='价格' ? 'price' : ''}"
								href="${sort.url}">${sort.text}</a></li>
						</c:forEach>
					</ul>
					<div class="price-input ml20 l ">
						自定义 <input type="text" class="txt" name="startPrice" id="startPrice"> - <input type="text" class="txt" name="endPrice" id="endPrice"><input type="button" value="确定" class="btn" id="inner-search-btn">
					</div>
					<ul class="sort-conditions ml20 l" id="ul">
						<li class="${isFreeDelivery == 1 ? 'cur' : '' }"><a rel="nofollow" href="${freeDeliveryLink}" data-attr='1007'>免运费</a></li>
						<li class="${isGenuine == 1 ? 'cur' : '' }"><a rel="nofollow" href="${genuineLink}" data-attr='1007'>正品行货</a></li>
					</ul>
				</div>				
				<!--搜索条件 e-->
				<div class="goods-list">
					<%@ include file="../common/snippet/imageView.jsp"%>
					<c:if test="${!reSearchMore}">
						<page:page pageView="${pageView}" pageClass="page cf mt20 mb20"/>
					</c:if>
					<!--page e-->
				</div>
			</div>			
		</div>
		<div class="side side-l l">
			<div class="m-bord">
				<c:if test="${fn:length(categoryList.linkTree)>0}">
					<div class="s-menu-list " id="J_category_nav">
						<!-- 全部商品分类 e -->
						<%@ include file="../common/snippet/category_list.jsp"%>
					</div>
				</c:if>
				<div class="goods-filter">		
					<!--商家筛选 s-->
					<h3 class="s-icon goods-f">商家筛选</h3>
					<%@ include file="../common/snippet/source_list.jsp"%>
					<!--商家筛选 e-->
				</div>
			</div>
			<div class="goods-history cf mt20">
				<h3 class="his-title">历史浏览商品</h3>
				<div class="scroll-bar scroll-history">
					<ul class="grid-view" id="show_his"></ul>
				</div>
			</div>	
			<div class="goods-history cf mt20" id="rel-goods" style="display: none;">
					<h3 class="his-title">其他用户还点击了</h3>
					<div class="scroll-bar scroll-recommend" >
						 <ul class="grid-view" id="show-goods-recommand"></ul>
					</div>
			</div>
		</div>
		<div class="side side-r l">
		</div>
	</div>
	<!--淘特价 e-->
	<input id="taoKeywords" type="hidden" value="${taoKeywords}">
	<input id="keyword" type="hidden" value="${keyword}">
	<input id="cookieId" type="hidden" value="${cookieId}">
	<input id="orignKeyWord" type="hidden" value="${orignKeyword}">
	<input id="category" type="hidden" value="${category}">
	<c:set var="sourceSize" value="${fn:length(docResourceDtos)}"></c:set>
	<fmt:formatNumber type="number" value="${sourceSize/4 + 1 + (sourceSize%4 == 0 ? 0 : 1)}" maxFractionDigits="0" var="adSize"/>
	<!--底部 s-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js"></script>
	<%@ include file="../commpage/footer.jsp"%>
	<!-- <a id="gotofushi" href="javascript:void(0);" class="gotofushi" style="margin-left: 780px;display: none;">进入服饰精品区</a> -->
	<div class="popup feed-popup">
        <div class="popup-in">
            <p class="feed-slogan"><a id='feedback-login'>登录</a></p>
            <form class="form-horizontal feed-form">
                <div class="control-group">
                    <label class="control-label" for="keywords"><em>*</em>您搜索的关键词是：</label>
                    <div class="controls">
                        <input id="keywords" type="text" maxlength="50" class="required" name="keyword">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="goods"><em>*</em>您遇到的问题是：</label>
                    <div class="controls">
                        <textarea name="content" id="goods" cols="30" rows="11" placeholder="可以填写商品的型号、价格或者其他商品的特性信息" maxlength="500" class="required"></textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="email">电子邮箱：</label>
                    <div class="controls">
                        <input id="email" name="email" type="text" placeholder="留下您的电子邮箱，方便我们提供您想要的商品">
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls btn-box">
                        <a href="javascript:void(0)" class="btn-submit btn-disable" id="J_submit">提交</a><a href="#"></a>
                    </div>
                </div>
            </form>
        </div>
        <span class="close">Χ</span>
        <i class="arrow"></i>
    </div>
	<!--底部 e-->
    <!--[if lt IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query_v3.js?t=${timeVersion}"></script>
	<![endif]-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${timeVersion}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/??handlebars.js,highcharts.js,b5mtrend.js,price-trend-common.min.js,PCASClass.js,search_v3.js?t=${timeVersion}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/sp/resultlist.js?t=${timeVersion}"></script>
    <!-- 价格趋势图 -->
	<div id="photo_price" style="display: none;position: absolute;">
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
	<input type="hidden" id="price-trend-type">
	<jsp:include page="../common/snippet/entry-template.jsp"/>
	<script type="text/javascript">
	$("img").imglazyload({fadeIn:true});
	var resultPage = new ResultPage({currentUrl:'${firstPageUrl}'});
	resultPage.init();
	//商品推荐keywords, cookId, recordUrl, adSize
	resultPage.showHistory();
	resultPage.showAd({keywords:"${orignKeyword}",cookId:'${cookieId}', recordUrl:'${adRecordUrl}', adSize: '${adSize}'});
	resultPage.showRecomand({container: $("#show-goods-recommand"), maxcontaier: $("#rel-goods"), keyword : '${orignKeyword}'});
	</script>
</body>
</html>