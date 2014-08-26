<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<!--[if lt IE 9]>
	    <script src="http://tao.b5mcdn.com/static/js/common/html5.js"></script>
	<![endif]-->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>搜索无结果</title>
	<jsp:include page="../common/meta.jsp" />
	<link rel="stylesheet" type="text/css" href="http://tao.b5mcdn.com/static/css/om.min.css" />
	<link rel="stylesheet" type="text/css" href="http://y.b5mcdn.com/static/css/om/om_min.css">
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/taosha.css?v=${timeVersion}" />
</head>
<body>
	<div class="taosha">
		<span></span>
		<div class="tpbar"></div>
		<!-- search start -->
		<div class="row search">
			<div class="container" style="width:980px">
				<a href="http://tao.b5m.com" class="logo" target="_blank"><img src="http://img.b5m.com/image/T1MMdgBmYs1RCvBVdK" alt=""></a>
				<div class="logo-banner">
					<a href="http://www.b5m.com/qiang.html?mps=____.1001.21009.3.0"><img width="80" height="80" alt="" src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/9908ea152c12022293132.jpg"></a>
				</div>
				<div class="search-box" style="width:723px;">
					<i class="icon-search">搜索</i>
					<input type="text" id="search-text" value="${orignKeyword}" placeholder="快速搜索  全网比价 请输入您想买的商品" style="width:600px">
					<button onclick="_search()">搜索</button>
				</div>
				<div class="search-hd"><div class="tel_400">客服热线：<b>400-077-5037</b></div></div>
			</div>
		</div>
		<!-- nav start -->
		<div class="row nav">
			<div class="container" style="width:980px">
				<div class="grid_3">
					<div class="index-menu"><cite> 正品100%  淘的就是底价</cite><h4 class="index-menu-title"><a href="http://tao.b5m.com" target="_blank">首&nbsp;页</a></h4></div>
				</div>
			</div>
		</div>
		<!-- nav end -->
	</div>
	<div class="row">
	    <div class="container">
	      <div class="grid_10">
	        <div class="item-sreach-error-new">
	          <i class="icon-search-wanring">搜索无结果</i><span class="tips-word">抱歉，没找到“<b>${orignKeyword}</b>”相关结果!</span>
	        </div>
	        <!-- <div class="item-sreach-error-new">
	          <i class="icon-search-wanring">搜索无结果</i><span class="tips-word">抱歉，好像出了点问题。<b id="takeBack">10</b>秒后带您回到<a href="http://tao.b5m.com" class="link">首页</a></span>
	        </div> -->
	      </div>
	    </div>
	</div>
	<!-- history start -->
	<div class="row">
		<div class="container">
			<div class="grid_10">
				<div class="item-history-new clearfix">
					<h4>热门商品</h4>
					<ul id="recommand-container"></ul>
				</div>
			</div>
		</div>
	</div>
	<!-- history end -->
	<%@ include file="./include/footer.jsp"%>
	<!-- footer end -->
	<!-- <script type="text/javascript">
	var start = 10;
	var step = -1;
	function count() {
		document.getElementById("takeBack").innerHTML = start;
		start += step;
		if (start < 0) {
			start = 10;
			// window.history.back();
			window.location = 'http://tao.b5m.com';
		}
		setTimeout("count()", 1000);
	}
	window.onload = count; 
	</script> -->
	<!-- Live800在线客服图标:淘沙[固定图标] 开始-->
	<div style='display: none;'>
		<a href='http://www.live800.com'>客服系统</a>
	</div>
	<script language="javascript" src="http://chat10.live800.com/live800/chatClient/staticButton.js?jid=4420443476&companyID=385596&configID=191952&codeType=custom"></script>
	<div style='display: none;'><a href='http://en.live800.com'>live chat</a></div>
	<style type="text/css">
	#live800iconlink {
		position: fixed;
		top: 223px;
		right: 10px;
		z-index: 999;
	}
	</style>
	<!-- 在线客服图标:淘沙 结束-->
	<!-- Live800默认跟踪代码: 开始-->
	<script language="javascript" src="http://chat10.live800.com/live800/chatClient/monitor.js?jid=4420443476&companyID=385596&configID=190917&codeType=custom"></script>
	<!-- Live800默认跟踪代码: 结束-->
	<script type="text/javascript" src="http://tao.b5mcdn.com/static/js/common/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${serverPath}/s/js/resultlist.js"></script>
	<script type="text/javascript" src="http://y.b5mcdn.com/static/scripts/sp/common.js"></script>
	<script type="text/javascript">
	var resultPage = new ResultPage();
	//商品推荐
	resultPage.recommendProduct({col:"${channel}",url:"${searchApiRecommandUrl}"}, function(json){
		var products = json.val;
		for(var index = 0; index < products.length; index++){
			var product = products[index];
			var html = '<li><div class="box-cell"><a href="'+product.url+'" class="history-link"><img src="'+product.imageUrl+'" alt=""></a>' + 
			           '<p class="history-title"><a href="'+product.url+'">'+product.title+'</a></p><p class="history-price"><a href="'+product.url+'">去看看</a><i>￥'+product.price+'</i></p></div></li>';
			$("#recommand-container").append(html);
		}
	});
	</script>
	<script type="text/javascript" src="http://tao.b5mcdn.com/static/js/common/jquery.easing/jquery.easing.min.js"></script>
	<script id="b5m-libs" type="text/javascript" src="http://staticcdn.b5m.com/static/public/sea-modules/dist/libs.js"
		rootPath="http://staticcdn.b5m.com/static/public/sea-modules/dist" version="20150421094218"></script>
	<script type="text/javascript" src="http://staticcdn.b5m.com/static/scripts/om/om_min.js"></script>
	<script type="text/javascript" src="http://tao.b5mcdn.com/static/js/om.min.js"></script>
</body>
</html>