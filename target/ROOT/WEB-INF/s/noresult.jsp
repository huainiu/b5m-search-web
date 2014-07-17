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
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/??common/common.css,search/search_head_v3.css?t=${today}" />
	<link rel="stylesheet" href="http://y.b5mcdn.com/css/search/search_no_result.css?t=${timeVersion}">
</head>
<body>
	<jsp:include page="./include/search.jsp" />
	<div class="wp">
        <div class="breadcrumb-no-404">
            <i class="icon-exclamation"></i><span class="tips">抱歉，没有找到“<strong>${orignKeyword}</strong>”相关结果！</span>
        </div>
        <div id="J_prod_list">
            <h3 class="recommend-prod">热门商品</h3>
            <ul class="grid-view cf" id="recommand-container"></ul>
        </div>
    </div>
    <script src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js?t=${timeVersion}"></script>
    <%@ include file="../commpage/footer.jsp"%>
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/search/search_no_result_404.js?t=${timeVersion}"></script>
    <script type="text/javascript" src="http://y.b5mcdn.com/scripts/sp/resultlist.js?t=${timeVersion}"></script>
    <script type="text/javascript" src="http://y.b5mcdn.com/static/scripts/sp/common.js?t=${timeVersion}"></script>
    <script type="text/javascript">
	var resultPage = new ResultPage();
	//商品推荐
	resultPage.recommendProduct({col:"",url:"${searchApiRecommandUrl}"}, function(json){
		var products = json.val;
		for(var index = 0; index < products.length; index++){
			var product = products[index];
            var html = '<li class="grid-mod"><a class="pic" href="'+product.url+'"><img src="'+product.imageUrl+'" alt=""></a>' +
                       '<div class="grid-item"><a class="des" href="'+product.url+'">'+product.title+'</a></div>'+
                       '<div class="grid-item"><a href="'+product.url+'" class="go-buy r">去看看</a><strong class="price"><b>￥</b>'+product.price+'</strong></div></li>';
			$("#recommand-container").append(html);
		}
	});
	</script>
</body>
</html>