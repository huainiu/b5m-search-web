<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<c:import url="../common/meta.jsp"/>
<%
String server = request.getServerName();
if(server.indexOf("b5m.com") > 0){server = "s.b5m.com";}
String basePath = request.getScheme() + "://"+ server + ":" + request.getServerPort()+ request.getContextPath() + "/";
request.setAttribute("basePath", basePath);
%>
<base href="${basePath}" />
<title>帮5买 - 轻松发现， 轻松选择！</title>
<meta name="title" content="帮5买 - 轻松发现， 轻松选择！" /> 
<link href="${basePath}css/header.css?t=${today}" type="text/css" rel="stylesheet" />
<link href="${basePath}css/error.css?t=${today}" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="http://y.b5mcdn.com/static/scripts/common/jquery-1.9.1.min.js?t=${today}"></script>
</head>
<body>

<div id="error" class="w980">
    <!--err-hd s-->
    <div class="err-hd">
    	<h1 class="logo"><a href="${b5mUrl}"><img src="http://y.b5mcdn.com/static/images/www/logo.png" /></a></h1>
    </div>
    <!--err-hd e-->
    
    <!--err-wrap s-->
    <div class="err-wrap">
    	<div class="m-logo"><img src="${basePath}images/images/m_300_235_logo.png" /></div>
        <div class="err-main">
        	<h2 class="err-result">矮油，服务器好像开小差了</h2>
            <div class="err-operating">
            	<h3>您可以尝试以下操作：</h3>
                <ul>
                    <li>检查刚才输入</li>
                    <li>
                        <%-- <p><span>看看我们的帮助</span><a href="${b5mUrl}sui/jsp/help/help.jsp">新手上路&gt;&gt;</a></p> --%>
                        <p><span>去其他地方逛逛</span><a href="http://s.b5m.com">购物&gt;&gt;</a><a href="http://www.b5m.com">帮5买&gt;&gt;</a></p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!--err-wrap e-->
</div>	
	    
	
<script type="text/javascript">
var currURL = document.location;
var _gaq = _gaq || [];
function googleAnalyticsInit(login) {
	if (typeof(login) == "undefined") {
		login = "false";
	} else {
		login = String(login);
	}
	_gaq.push(["_setAccount", "UA-27469593-1"]);
	_gaq.push(["_setDomainName", "b5m.com"]);
	_gaq.push(["_addOrganic", "so.360.cn", "q"]);
	_gaq.push(["_addOrganic", "so.com", "q"]);
	_gaq.push(["_addOrganic", "sogou", "query"]);
	_gaq.push(["_addOrganic", "soso", "w"]);
	_gaq.push(["_addOrganic", "youdao", "q"]);
	_gaq.push(["_setCustomVar", 1, "login", login, 2]);
	_gaq.push(["_trackPageview","/stats/error/URL"]);
	_gaq.push(["b5m._setAccount", "UA-38615374-1"]);
	_gaq.push(["b5m._setDomainName", "b5m.com"]);
	_gaq.push(["_trackPageview","/stats/error/500/" + currURL]);
	var ga = document.createElement("script");
	ga.type = "text/javascript";
	ga.async = true;
	ga.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js?t=${today}";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(ga, s);
}
function google_PushInfo(event, page, type, info, price, samePage) {
	var undefinedIndex = 0;
	if (typeof(info) == "undefined") {
		undefinedIndex++;
	}
	if (typeof(price) == "undefined") {
		undefinedIndex++;
	} else {
		price = String(price);
		var index = price.indexOf(".");
		if (index > 0) {
			price = price.substring(0, index);
		}
		try {
			price = Number(price);
		} catch (e) {
			price = 0;
		}
	}
	if (typeof(samePage) == "undefined") {
		undefinedIndex++;
	}
	if (undefinedIndex == 0) {
		_gaq.push([event, page, type, info, price, samePage]);
	} else {
		if (undefinedIndex == 1) {
			_gaq.push([event, page, type, info, price]);
		} else {
			if (undefinedIndex == 2) {
				_gaq.push([event, page, type, info]);
			} else {
				if (undefinedIndex == 3) {
					_gaq.push([event, page, type]);
				}
			}
		}
	}
}
</script>
<script type="text/javascript">
googleAnalyticsInit();//google登录
</script>
</body>
</html>