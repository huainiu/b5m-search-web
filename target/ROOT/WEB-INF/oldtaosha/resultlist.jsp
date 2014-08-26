<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" uri="http://www.b5m.com/tag/page" %>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${key}正品价格_${lastcategory}正品_${key}官方旗舰专卖【店铺、图片、评价怎么样】-淘沙商城</title>
	<meta name="keywords" content="${key}价格, ${lastcategory}${lastcategory != '' ? '分类名正品,' : ''} ${key}官方旗舰店,${key}专卖店,淘沙商城" />
	<meta name="description" content="淘沙商城${key}专卖，并郑重承诺${key}均来自各大官方旗舰店的正品行货，经过品牌授权验证、产品质量验证，可放心购买。" />
	<link rel="stylesheet" type="text/css" href="http://tao.b5mcdn.com/static/css/om.min.css?t=${timeVersion}" />
  	<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/static/css/om/om_min.css?t=${timeVersion}">
  	<script type="text/javascript">
	var _basePath = "${serverPath}/";
	</script>
</head>
<body>
	<%@ include file="./include/search.jsp"%>
	<div class="row bread-crumb-navigation">
		<div class="container">
			<div class="grid_10">
				<ul>
					<li><a href="${serverPath}/s/Search?key=${orignKeyword}">全部</a></li>
					<li>&gt;</li>
					<c:if test="${cates != '' && cates != null}">
						<c:if test="${cateLength > 0}"><li><a href="${serverPath}/s/Search?key=${orignKeyword}&cat=${cates[0]}">${cates[0]}</a></li><li>&gt;</li></c:if>
						<c:if test="${cateLength > 1}"><li><a href="${serverPath}/s/Search?key=${orignKeyword}&cat=${cates[0]}>${cates[1]}">${cates[1]}</a><li>&gt;</li></c:if>
						<c:if test="${cateLength > 2}"><li><a href="${serverPath}/s/Search?key=${orignKeyword}&cat=${cates[0]}>${cates[1]}>${cates[2]}">${cates[2]}</a><li>&gt;</li></c:if>
					</c:if>
					<li>${orignKeyword}</li>
				</ul>
			</div>
		</div>
	</div>
	<%@ include file="./include/category_list_mid.jsp"%>
	<jsp:include page="./include/filterAttr.jsp"/>
	<%-- <div class="row">
	    <div class="container">
	      <div class="grid_10">
			<dl class="intelligent-recommend ">
				<dt>您是不是想找：</dt>
				<dd>
					<c:forEach items="${relatedQueryList}" var="relatedQuery"><a href="${relatedQuery.url}" style="margin-left: 10px;">${relatedQuery.text}</a></c:forEach>
				</dd>
			</dl>
			</div>
		</div>
	</div> --%>
	<div class="row">
	    <div class="container">
	      <div class="grid_10">
	        <div class="item-resize-box clearfix">
	        	<c:forEach items="${sortList}" var="sort" varStatus="stat">
	        		<c:if test="${sort.text == '默认排序'}">
						<c:set var="cur" value="${empty sortField}" />
					</c:if>	
					<c:if test="${sort.text=='销量'}">
						<c:set var="cur" value="${sortField == 'SalesAmount'}" />
						<c:set var="_sort" value="down"></c:set>
					</c:if>
					<c:if test="${sort.text=='最新'}">
						<c:set var="cur" value="${sortField=='Date'}" />
						<c:set var="_sort" value="down"></c:set>
					</c:if>
					<c:if test="${sort.text=='价格'}">
						<c:set var="cur" value="${sortField=='Price'}" />
						<c:set var="_sort" value="up"></c:set>
					</c:if>
					<c:if test="${cur}">
						<c:set var="_sort" value="${sortType == 'DESC'? 'down' : (sortType == 'ASC' ? 'up' : '')}"></c:set>
					</c:if>
					<a href="${sort.url}" class="btn-default-resize ${cur?'cur':''}">
						${sort.text}<c:if test="${sort.text!='默认排序'}"><i class="icon-resize-${_sort}"></i></c:if>
					</a>
	        	</c:forEach>
	          	<div class="item-resize-price">价格
	            	<input type="text" name="startPrice" id="startPrice" onchange=""><i>-</i>
	            	<input type="text" name="endPrice" id="endPrice"><a href="javascript:void(0)" id="inner-search-btn">确定</a>
	          	</div>
	        </div>
	      </div>
	    </div>
	</div>
	<%@ include file="./include/imageView.jsp"%>
	<div class="row margin-fix">
		<div class="container">
			<div class="grid_10">
				<div class="item-page"><page:page pageView="${pageView}" pageClass="search-page" firstPageUrl="${firstPageUrl}"/></div>
			</div>
		</div>
	</div>
   <div class="row" id="max-contaier">
    	<div class="container">
     		<div class="grid_10">
        		<div class="item-history">
          			<h4>大家都在看</h4>
          			<ul id="item-history-ul">
          			</ul>
        		</div>
      		</div>
   		 </div>
  	</div>
	<jsp:include page="./include/footer.jsp"/>
	<script type="text/javascript" src="${serverPath}/s/js/resultlist.js?t=${timeVersion}"></script>
	<script type="text/javascript">
	var resultPage = new ResultPage({currentUrl:'${firstPageUrl}'});
	resultPage.init();
	//商品推荐
	resultPage.showRecomand({keywords:"${orignKeyword}",container:$("#item-history-ul"), maxcontaier : $("#max-contaier")});
	</script>
</body>
</html>