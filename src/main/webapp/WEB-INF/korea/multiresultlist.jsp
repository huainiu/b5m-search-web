<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<%@ include file="../common/meta.jsp" %>
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_result_v3.css?t=${today}">
</head>
<body>
	<%@ include file="./include/search.jsp"%>
	<div class="wp">
		<p class="search-tips breadcrumb mt20 mb20">
			抱歉，没有与“<span class="search-key cl-ff78">${orignKeyword}</span>”匹配的相关产品，但是我们帮你找到了
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap" varStatus="stat">
				“<span class="search-key cl-ff78">${searchDTOWrap.keywords}</span>”
				<c:if test="${stat.index == searchDTOWrapsSize - 2 && searchDTOWrapsSize > 1}">和</c:if>
				</c:forEach>
				的搜索结果。
		</p>
	</div>
	<div class="wp cf">
		<div class="main l">
			<div class="container">
				<c:forEach items="${searchDTOWraps}" var="searchDTOWrap">
					<div class="breadcrumb cf chaici-keywd">
						<div class="r"><a href="${serverPath}/s/Search?key=${searchDTOWrap.keywords}" target="_blank">更多</a></div>
						<c:set value="${fn:length(searchDTOWrap.keywords)}" var="keywordslength" />
						<c:set value="${fn:length(orignKeyword)}" var="orignKeywordlength" />
						<c:set value="${fn:indexOf(orignKeyword, searchDTOWrap.keywords)}" var="keywordsindex" />
						<c:set value="" var="delWords" />
						<h2 class="l">
							<c:if test="${keywordsindex > -1}">
								<c:set value="${fn:substring(orignKeyword, 0, keywordsindex)}" var="delWords" />
							</c:if>
								${searchDTOWrap.keywords}
							<c:if test="${keywordsindex < orignKeywordlength - 1}">
								<c:set value="${fn:substring(orignKeyword, keywordsindex + keywordslength, orignKeywordlength)}" var="delWords2" />
								<del>${delWords} ${delWords2}</del>
							</c:if>
						</h2>
					</div>
					<div class="goods-list">
					    <ul class="grid-view cf">
							<c:forEach items="${searchDTOWrap.docResourceDtos}" var="docResourceDto" begin="0" end="3">
								<c:set value="${docResourceDto.res}" var="res" />
								<c:set value="${docResourceDto.norms}" var="norms" />
								<c:set value="${docResourceDto.subResources}" var="subResources" />
				
								<c:url var="detailUrl" value="http://s.b5m.com/item/${res.DocId}.html"></c:url>
								<c:if test="${fn:length(subResources) >= 1}">
									<c:url var="detailUrl" value="http://s.b5m.com/compare/${res.DocId}.html"></c:url>
								</c:if>
				
								<li class="grid-ls">
									<div class="grid-mod">
										<div class="grid-in">
											<div class="pic-wrap">
												<a class="pic" href="${detailUrl}" target="_blank"><img data-attr="1008" class="grid-mod-pic" src="${serverPath}images/placeholder.png" lazy-src="${picturePath}" alt="${res.Title}"></a>
											</div>
											<c:if test="${norms != null }">
												<div class="etalon J_type">
													<c:forEach items="${norms }" var="item">
														<a href="javascript:void(0);" data-price="${item.price }">${item.name }</a>
													</c:forEach>
												</div>
											</c:if>
											<div class="summary">
												<a href="${detailUrl}" title="${res.Title}" target="_blank" data-docid="${res.DocId }">${res.Title}</a>
											</div>
											<div class="price">
												<strong><b>&yen;</b>${res.Price}</strong> <span style="display: none;" class="${fn:length(subResources) >= 1 ? '' : 'price-trend'}" data-docid="${res.DocId}" id="price-trend-${res.DocId}"></span> <input type="hidden" source="${res.Source}" id="price-trend-data-${res.DocId}" value="" />
												<c:if test="${not empty res.SalesAmount && res.SalesAmount >= 0 }">
													<span class="sell-count">已售${res.SalesAmount }件</span>
												</c:if>
											</div>
											<!-- star s-->
											<div class="start">
												<c:if test="${fn:length(subResources) >= 1}">
													<div class="nums-goods l">
														<em>${res.SourceCount}</em>家商城
													</div>
												</c:if>
												<c:choose>
													<c:when test="${fn:length(subResources) >= 1}">
														<div class="nums-goods l">
															<em>${res.itemCount}</em>个比价商品
														</div>
													</c:when>
													<c:otherwise>
														<div class="nums-goods l">
															<em>${res.Source}</em>
														</div>
													</c:otherwise>
												</c:choose>
											</div>
											<!-- star e-->
											<c:if test="${res.canDaigou == 'true'}">
												<div class="lowest_price"></div>
											</c:if>
										</div>
										<div class="grid-tail" data-attr="100800" lp="${res.canDaigou ? '1' : '0'}">
											<span data-attr="100800" lp="${res.canDaigou ? '1' : '0'}"></span>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>	
			</div>
		</div>		
		<c:set value="${searchDTOWraps[0].keywords}" var="firstKeyWords" />
		<div class="side side-r l mb20">
			<c:set var="sourceSize" value="${fn:length(searchDTOWraps)}"></c:set>
			<fmt:formatNumber type="number" value="${sourceSize/4 + 1 + (sourceSize%4 == 0 ? 0 : 1)}" maxFractionDigits="0" var="adSize"/>
			<iframe id="iFrame1" name="iFrame1" style="display:block;" src="http://click.simba.taobao.b5m.com/ad/vlb5m_${adSize}_0.html?keywords=${firstKeyWords}" frameborder="0" width="100%" scrolling="no"></iframe>
		</div>
	</div>

	<!--淘特价 e-->
	<input id="taoKeywords" type="hidden" value="${taoKeywords}">
	<input id="keyword" type="hidden" value="${keyword}">
	<input id="orignKeyWord" type="hidden" value="${orignKeyword}">
	<input id="category" type="hidden" value="${category}">
	<!--底部 s-->
	<%@ include file="../commpage/footer.jsp"%>
	<script src="http://y.b5mcdn.com/scripts/search/search_v3.js?t=${today}"></script>
    <!--[if lt IE 9]>
	<script src="http://y.b5mcdn.com/scripts/search/media_query_v3.js?t=${today}"></script>
	<![endif]-->
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/highcharts.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/b5mtrend.min.js?t=${today}"></script>
	
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/imglazyload.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/price-trend-common.min.js?t=${today}"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/resultlist.min.js?t=${today}"></script>
	
	<!-- 价格趋势图 -->
	<div id="photo_price" style="display: none;position: absolute;">
		<div id="photo_price_content" style="display: none"></div>
	</div>
	<input type="hidden" id="price-trend-docids" value="${priceTrendDocIds}">
	<input type="hidden" id="price-trend-type">
	<script type="text/javascript">
		$(function() {
			$("img").imglazyload({fadeIn:true});
			$.ajax({
				url : "http://click.simba.taobao.b5m.com/ad/data/vb5m_0.html",
				type : 'POST',
				data : {
					keywords : "${keyword}"
				},
				dataType : 'jsonp',
				jsonp : 'jsoncallback',
				success : function(result) {
					if (!result || result.val == 0) {
						$('#ad-right iframe').css('visibility', 'hidden');
					}
				}
			});
		});
		$("a").click(function() {
			var localhref = window.location.href;
			if (!localhref || localhref.indexOf("?") <= 0)
				return;
			var $this = $(this);
			var $thisHref = $this.attr("href");
			var params = localhref.split("?")[1];
			if ($thisHref && $thisHref.indexOf("?") < 0) {
				$this.attr("href", $thisHref + "?" + params);
			}
		});
	</script>
	
	
</body>
</html>