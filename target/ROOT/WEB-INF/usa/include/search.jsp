<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="header">
    <div class="wp cf">
        <h1 class="header-logo fl"><a title="美国馆" href="http://usa.b5m.com"><img src="http://staticcdn.b5m.com/static/images/haiwai/america/logo.png" alt="美国馆"></a></h1>
        <div class="header-tools-search">
            <form action="${serverPath}/s/Search" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="get" name="scbar_form" id="scbar_form">
                <span class="header-search-content">
                    <input data-attr="1005" name="key" type="text" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" id="scbar_txt" placeholder="快速搜索全网最新商品与资讯" value="${keyword}" class="header-search-key J_autofill af-input act">
                    <input type="submit" class="header-rearch-submit" onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="帮5搜">
                </span>
            </form>
        </div>
        <!-- country html -->
        <div class="top-hot-key on">
            <div class="key-cont cf">
                <strong class="iconfont">뀈</strong>
                <div>
                    <span style="display: block;">
                        <a target="_blank" href="#">比基尼</a><em>|</em> 
                        <a target="_blank" href="#">豆浆机</a>
                    </span>
                </div>
            </div>
            <div class="cf">
                <b class="btn-next"><i class="iconfont">뀐</i></b>
                <b class="btn-prev"><i class="iconfont">뀉</i></b>
            </div>
        </div>
        <!-- country html -->
    </div>
    <div class="header-nav-box">
        <div class="wp">
            <div class="header-nav-back"><a data-attr="1004" href="http://usa.b5m.com/" title="美国馆首页" class="icon"><u>美国馆首页</u></a><s class="header-nav-back-s2"></s><s></s></div>
            <ul id="menu">
                <li class="index cur"><a data-attr="1004" href="http://usa.b5m.com" target="_self">首页</a></li>
                <li><a data-attr="1004" href="http://usa.b5m.com/brand.html" target="_self">品牌库</a></li>
            </ul>
        </div>

    </div>
</div>