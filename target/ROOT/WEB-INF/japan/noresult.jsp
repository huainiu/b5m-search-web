<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../common/meta.jsp"/>
    <title>日本馆-搜索无结果</title>
    <link rel="stylesheet" href="http://y.b5mcdn.com/css/common/common.css?t=${timeVersion}" />
    <link rel="stylesheet" href="http://y.b5mcdn.com/css/haiwai/style.css?t=${timeVersion}">
    <link rel="stylesheet" href="http://y.b5mcdn.com/css/america.min.css?t=${timeVersion}">
</head>
<body>
   <jsp:include page="./include/search.jsp"/>
   <div class="wraper cf">
        <div class="col-main">
            <div class="result-nothing">
                <h2>亲，木有搜索到结果</h2>
                <p class="back">糟糕，没有找到"
                    <strong>${orignKeyword}</strong>"相关商品
                    <br>
                    <br><a href="http://www.b5m.com">去首页看看?</a>
                </p>
            </div>
        </div>
  </div>
  <script type="text/javascript" src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js"></script>
  <jsp:include page="../commpage/footer.jsp"/>
  <script id="b5m-libs" type="text/javascript" src="http://y.b5mcdn.com/static/public/sea-modules/dist/libs.js" rootPath="http://y.b5mcdn.com/static/public/sea-modules/dist" version="20150421094218"></script>
  <script type="text/javascript" src="http://y.b5mcdn.com/static/scripts/om/om_min.js?t=${timeVersion}"></script>
  <script type="text/javascript" src="http://y.b5mcdn.com/scripts/sp/resultlist.js?t=${timeVersion}"></script>
  <script type="text/javascript">
  	var resultPage = new ResultPage();
	//商品推荐
	resultPage.showRecomand({keywords:"${orignKeyword}",container:$("#item-history-ul"), maxcontaier : $("#max-contaier")});
  </script>
</body>
</html>